package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.User;
import org.sale.project.service.CartService;
import org.sale.project.service.OrderService;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pay")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PayController {
    UserService userService;
    CartService cartService;
    private final OrderService orderService;

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
                           @ModelAttribute("name") String name,
                           @ModelAttribute("phoneNumber") String phoneNumber,
                           @ModelAttribute("address") String address,
                           @ModelAttribute("note") String note) {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user = userService.findUserByEmail(email);


        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);

        userService.saveUser(user);


        double total = cartService.totalPriceInCart(user.getCart());

        orderService.complete(user, total);

        session.setAttribute("sum", 0);
        return "redirect:/";

    }




}
