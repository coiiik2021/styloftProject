package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.User;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccountController {

    UserService userService;

    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);

        Optional<User> userOptional = userService.findByEmail(email);
        User user = userOptional.get();

        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());

        return "/client/home/information";
    }

    @PostMapping("/update-information")
    public String updateInformation(Model model, HttpServletRequest request, @ModelAttribute("user") User userUpdate) {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        user.setName(userUpdate.getName());
//        user.setSex();
        user.setAddress(userUpdate.getAddress());
        user.setBirthDay(userUpdate.getBirthDay());
        user.setPhoneNumber(userUpdate.getPhoneNumber());
        userService.saveUser(user);
        return "redirect:/";
    }
}
