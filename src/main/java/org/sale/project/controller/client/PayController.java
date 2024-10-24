package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
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
import org.sale.project.service.UserService;
import org.sale.project.service.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pay")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PayController {
    UserService userService;
    CartService cartService;
    OrderService orderService;
    EmailService emailService;


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

    @PostMapping("/complete")
    public String complete(Model model, HttpServletRequest request,
                           @ModelAttribute("user") @Valid User userPay, BindingResult bindingResult,
                           @ModelAttribute("note") String note) {
        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        userPay.setEmail(email);
        User user = userService.findUserByEmail(email);


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


        double total = cartService.totalPriceInCart(user.getCart());

        Order order = orderService.complete(user, total);

        session.setAttribute("sum", 0);


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

//        LocalDate day = order.getDate();

//        int date = day.getDay();
//        int month = day.getMonth();
//        int year = day.getYear();
        String header = "Cảm ơn bạn đã đặt hàng bên cửa hàng vào ngày: " + order.getDate().toString();

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

        emailService.sendEmail(
                SendEmailRequest
                        .builder()
                        .subject("Đặt hàng #" + order.getId().substring(0, 5))
                        .to(Recipient.builder()
                                .name(user.getName())
                                .email(user.getEmail())
                                .build())
                        .htmlContent("<p>" + header + "</p>" +
                                String.join("\n", itemOrder)
                                        + "<p> Giá: " + order.getTotal() + " VND </p>"
//                                + "<p>" + footer + "</p>"
                        )
                        .build()
        );

        return "/client/thank/show";
    }




}
