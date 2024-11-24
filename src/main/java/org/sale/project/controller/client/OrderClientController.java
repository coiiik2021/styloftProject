package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.sale.project.entity.*;
import org.sale.project.enums.StatusOrder;
import org.sale.project.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderClientController {

    OrderService orderService;
    OrderDetailService orderDetailService;

    UserService userService;

    ProductVariantService productVariantService;
    ReviewService reviewService;

    PayOS payOS;

    @GetMapping("/{id}")
    public String getPageOrder(@PathVariable("id") String id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);

        return "/client/order/show";
    }

    @GetMapping("/detail/review/{id}")
    public String getPageReview(@PathVariable("id") String idOrderDetail, Model model,HttpServletRequest request) {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user = userService.findUserByEmail(email);

        model.addAttribute("detail", orderDetailService.findById(idOrderDetail));
        model.addAttribute("user", user);


        model.addAttribute("newReview", new Review());

        return "/client/order/review";
    }

    @PostMapping("/returnPayment/{id}")
    public void returnPayment(@PathVariable("id") String id,
                              HttpServletResponse response,
                              HttpServletRequest request) {
        Order order = orderService.findById(id);
        order.setStatus(StatusOrder.PROCESSING);
        orderService.saveOrder(order);
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
            final String cancelUrl = baseUrl + "/pay/cancel?idOrder=" + order.getId();
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

    @PostMapping("/detail/review")
    public String createReview(@ModelAttribute("newReview") Review newReview, @RequestParam("idOrderDetail") String idDetail) {


        OrderDetail orderDetail = orderDetailService.findById(idDetail);


        newReview.setDateReview(LocalDate.now());
        newReview.setOrderDetail(orderDetail);
        newReview = reviewService.createReview(newReview);

        orderDetail.setReview(newReview);

        orderDetailService.saveOrderDetail(orderDetail);

        return "redirect:/order/" + orderDetail.getOrder().getId();
    }

    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") String id){
        orderService.cancelOrder(id);

        return "redirect:/account";
    }

}
