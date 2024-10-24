package org.sale.project.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.User;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccountController {

    UserService userService;

    @GetMapping
    public String getPageInformation(Model model, HttpServletRequest request) {
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
    public String updateInformation(Model model, HttpServletRequest request,
            @ModelAttribute("user") @Valid User userUpdate, BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>> user: " + fieldError.getField() + fieldError.getDefaultMessage());

        }
        if (bindingResult.hasErrors()) {
            return "/client/home/information";
        }

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        userService.updateUser(email, userUpdate);

        return "redirect:/";
    }
}
