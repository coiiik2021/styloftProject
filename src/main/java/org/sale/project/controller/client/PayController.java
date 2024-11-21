package org.sale.project.controller.client;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.*;
import org.sale.project.enums.StatusOrder;
import org.sale.project.service.*;
import org.sale.project.service.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pay")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PayController {
    UserService userService;
    CartService cartService;
    OrderService orderService;
    EmailService emailService;
    PaymentService paymentService;

    UserActionService userActionService;
    VoucherService voucherService;

    PayOS payOS;


    @GetMapping
    public String getPagePay(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);

        model.addAttribute("items", user.getCart().getCartItems());
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", cartService.totalPriceInCart(user.getCart()));

        return "/client/pay/show";
    }

    @GetMapping("/cancel")
    public String checkOut(Model model, @RequestParam("idOrder") String id) {
        System.out.println(">>>id: "+ id);

        orderService.updateCancelPayment(id);



        model.addAttribute("contentError", "Đơn hàng bạn chưa được thanh toán!!!");

        return "/client/thank/show";
    }


//    http://localhost:8080/payment/vn-pay?amount=237000&bankCode=NCB
//    private void setUpOnline(String totalPrice, HttpServletResponse response) {
//
//
//    }





    @PostMapping("/complete")
    public String complete(Model model, HttpServletRequest request,
                           @ModelAttribute("user") @Valid User userPay, BindingResult bindingResult,
                           @ModelAttribute("note") String note,
                           @ModelAttribute("paymentMethod") String paymentMethod,
                           @ModelAttribute("totalPrice") String totalPrice,
                           @ModelAttribute("totalPriceFinal") String totalPriceFinal,

                           @RequestParam(value = "voucherCode", required = false) String voucherCode,
                           HttpServletResponse response
                           ) throws MessagingException {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user = userService.findUserByEmail(email);



        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>>pay" +fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("items", user.getCart().getCartItems());
            model.addAttribute("totalPrice", cartService.totalPriceInCart(user.getCart()));
            model.addAttribute("voucherCode", voucherCode);

            return "/client/pay/show";
        }


        user.setName(userPay.getName());
        user.setPhoneNumber(userPay.getPhoneNumber());
        user.setAddress(userPay.getAddress());

        userService.saveUser(user);


        double total = Double.parseDouble(totalPriceFinal);

        System.out.println( ">>>total: " + total);
        Voucher voucher = voucherService.findByCode(voucherCode);

        Order order = orderService.complete(user, total, voucher);



        if(paymentMethod.equals("online")){
//            setUpOnline(totalPrice, response);

//            session.setAttribute("idOrder", order.getId());
//            int totalInt = ((Double)total).intValue();
//
//            String url = "http://localhost:8080/pay/vn-pay?amount=" + totalInt + "&bankCode=NCB";
//            try{
//                response.sendRedirect(url);
//            } catch (IOException e) {
//
//                System.out.println(e);
//            }


            try {
                String baseUrl = getBaseUrl(request);
                System.out.println(">>> base url: " + baseUrl);


                List<ItemData> items = new ArrayList<>();
                for (OrderDetail orderDetail : order.getDetails()) {
                    items.add(ItemData.builder().name(orderDetail.getProductVariant().getProduct().getName())
                                    .price((int) orderDetail.getPrice())
                                    .quantity(orderDetail.getQuantity())
                            .build());
                }


                final String description = "Thanh toán đơn hàng: #" + order.getId().substring(0, 5);
                final String returnUrl = baseUrl + "/pay/thank";
                final String cancelUrl = baseUrl + "/pay/cancel?idOrder="+ order.getId();
                // Gen order code
                String currentTimeString = String.valueOf(new Date().getTime());
                long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

                PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(10000).description(description)
                        .returnUrl(returnUrl).cancelUrl(cancelUrl).items(items).build();
                CheckoutResponseData data = payOS.createPaymentLink(paymentData);

                String checkoutUrl = data.getCheckoutUrl();

                response.setHeader("Location", checkoutUrl);
                response.setStatus(302);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }









        session.setAttribute("sum", 0);





        sendCompleteEmailOrder(order);

        return "/client/thank/show";
    }

    @GetMapping("/thank")
    public String getPageThank(){
        return "/client/thank/show";

    }



    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }


    private void sendCompleteEmailOrder(Order order) throws MessagingException {
        String header = "Cảm ơn bạn đã đặt hàng bên cửa hàng vào ngày: " + order.getDate().toString();


        List<String> itemOrders = new ArrayList<>();

        List<OrderDetail> details = order.getDetails();
        System.out.println(">>>send email: size " +  details.size());
        int count = 0;

        for (OrderDetail detail : details) {
            itemOrders.add( "<p class=\"fs-1\"> "+
                    "<img class=\"d-block\" src=\"http://product.hstatic.net/1000003969/product/kem_cg05143_1_20241118110403_4a865883c7ef4790bb9af57e5251563d_master.jpeg\" width=\"10%\" height=\"auto\" style=\"border-radius: 15px;\"> <br>"+
                    + ++count +
                    ". Name: <strong>" +  detail.getProductVariant().getProduct().getName() + "</strong> || " +
                    " color: <strong>" + detail.getProductVariant().getColor().getName() + "</strong> || " +
                    " size: <strong>" + detail.getProductVariant().getSize().getName() + "</strong> || " +
                    "Số lượng: <strong>" + detail.getQuantity() + "</strong> " +
//                   " <img class=\"d-block w-100\" src=\"http://localhost:8080/images/product/"+detail.getProductVariant().getProduct().getName()+ "/"+ detail.getProductVariant().getImage()+"\"> "+
                    "</p>");
        }

//        emailService.sendEmail(
//                SendEmailRequest
//                        .builder()
//                        .subject("Đặt hàng #" + order.getId().substring(0, 5))
//                        .to(Recipient.builder()
//                                .name(order.getUser().getName())
//                                .email(order.getUser().getAccount().getEmail())
//                                .build())
//                        .htmlContent("<p>" + header + "</p>" +
//                                        String.join("\n", itemOrder)
//                                        + "<p> Giá: " + order.getTotal() + " VND </p>"
////                                + "<p>" + footer + "</p>"
//                        )
//                        .build()
//        );
        emailService.sendHtmlEmail(order.getUser().getAccount().getEmail(),"Đặt hàng #" + order.getId().substring(0, 5),"<p>" + header + "</p>" +
                String.join("\n", itemOrders)
                + "<p> Giá: " + order.getTotal() + " VND </p>");


    }


    @GetMapping("/vn-pay")
    public void pay(HttpServletRequest request, HttpServletResponse response) {

        String url = paymentService.createVnPayPayment(request);

        try{
            response.sendRedirect(url);
        } catch (Exception e){

        }

//        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }


    @GetMapping("/vn-pay-callback")
    public String payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws MessagingException {

        HttpSession session = request.getSession();
        String idOrder = (String) session.getAttribute("idOrder");
        Order order = orderService.findById(idOrder);
        String url;


        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            order.setStatus(StatusOrder.PAYMENT_FAILED);
            url = "/client/thank/show";
        } else {
            order.setStatus(StatusOrder.PROCESSING);
            url = "/client/error/show";


        }
        sendCompleteEmailOrder(order);


        orderService.saveOrder(order);
        return url;
    }






}
