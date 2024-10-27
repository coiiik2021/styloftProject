package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.Order;
import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.User;
import org.sale.project.service.CartService;
import org.sale.project.service.OrderService;
import org.sale.project.service.PaymentService;
import org.sale.project.service.UserService;
import org.sale.project.service.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/check-out")
    public String checkOut(Model model, HttpServletRequest request) {

        return "redirect:/";
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
                           HttpServletResponse response) {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        userPay.setEmail(email);
        User user = userService.findUserByEmail(email);



//        System.out.println(">>> method: " + paymentMethod);

//        System.out.println(">>> method: " + Double.parseDouble(totalOptional.get()));





        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>>pay" +fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("items", user.getCart().getCartItems());
            model.addAttribute("totalPrice", cartService.totalPriceInCart(user.getCart()));
            return "/client/pay/show";
        }


        user.setName(userPay.getName());
        user.setPhoneNumber(userPay.getPhoneNumber());
        user.setAddress(userPay.getAddress());

        userService.saveUser(user);


        double total = cartService.totalPriceInCart(user.getCart()) + 30000;

        Order order = orderService.complete(user, total);



        if(paymentMethod.equals("online")){
//            setUpOnline(totalPrice, response);

            session.setAttribute("idOrder", order.getId());
            int totalInt = ((Double)total).intValue();

            String url = "http://localhost:8080/pay/vn-pay?amount=" + totalInt + "&bankCode=NCB";
            try{
                response.sendRedirect(url);
            } catch (IOException e) {

            }

        }




        session.setAttribute("sum", 0);




//        LocalDate day = order.getDate();

//        int date = day.getDay();
//        int month = day.getMonth();
//        int year = day.getYear();
        sendCompleteEmailOrder(order);

//        if(date <= 27){
//            date = 2;
//            if(month == 12){
//                month = 1;
//                year +=1;
//
//            } else{
//                month++;
//            }
//        } else{
//            date += 2;
//        }

//        String footer = "Đơn hàng dự kiến sẽ tới tay bạn vào: " + date + "/" + month + "/" + year;


        return "/client/thank/show";
    }


    private void sendCompleteEmailOrder(Order order) {
        String header = "Cảm ơn bạn đã đặt hàng bên cửa hàng vào ngày: " + order.getDate().toString();


        List<String> itemOrder = new ArrayList<>();

        List<OrderDetail> details = order.getDetails();
        int count = 0;

        for (OrderDetail detail : details) {
            itemOrder.add( "<p> " + ++count +
                    ". Name: <strong>" +  detail.getProductItem().getProduct().getName() + "</strong> || " +
                    " color: <strong>" + detail.getProductItem().getColor().getName() + "</strong> || " +
                    " size: <strong>" + detail.getProductItem().getSize().getName() + "</strong> || " +
                    "Số lượng: <strong>" + detail.getQuantity() + "</strong> " +
                    "</p>");

        }

        emailService.sendEmail(
                SendEmailRequest
                        .builder()
                        .subject("Đặt hàng #" + order.getId().substring(0, 5))
                        .to(Recipient.builder()
                                .name(order.getUser().getName())
                                .email(order.getUser().getEmail())
                                .build())
                        .htmlContent("<p>" + header + "</p>" +
                                        String.join("\n", itemOrder)
                                        + "<p> Giá: " + order.getTotal() + " VND </p>"
//                                + "<p>" + footer + "</p>"
                        )
                        .build()
        );


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
    public String payCallbackHandler(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String idOrder = (String) session.getAttribute("idOrder");
        Order order = orderService.findById(idOrder);
        String url;


        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            order.setStatus("PREPARING");
            url = "/client/thank/show";

        } else {

            order.setStatus("PROCESSING");
            url = "/client/error/show";


        }
        sendCompleteEmailOrder(order);


        orderService.saveOrder(order);
        return url;
    }




}
