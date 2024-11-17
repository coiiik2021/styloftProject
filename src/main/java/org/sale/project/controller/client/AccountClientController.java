package org.sale.project.controller.client;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.service.UploadService;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccountClientController {

    UserService userService;
    UploadService uploadService;

    @GetMapping
    public String getPageInformation(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);

        Optional<User> userOptional = userService.findByEmail(email);
        model.addAttribute("email", email);
//        User user = userOptional.get();

        model.addAttribute("user", userOptional.orElseGet(User::new));
        model.addAttribute("orders", userOptional.isEmpty()
                ? new ArrayList<Order>()
                : userOptional.get().getOrders().stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .collect(Collectors.toList()));

        return "/client/home/information";
    }

    @PostMapping("/update-information")
    public String updateInformation(Model model, HttpServletRequest request,
                                    @ModelAttribute("user") @Valid User userUpdate, BindingResult bindingResult,
                                    @RequestParam("imageAvatar") MultipartFile imageAvatar) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>> user: " + fieldError.getField() + fieldError.getDefaultMessage());

        }
        if (bindingResult.hasErrors()) {
            return "/client/home/information";
        }



        if(!imageAvatar.isEmpty()){
            String img = uploadService.uploadImage(imageAvatar, "/avatar");
            userUpdate.setImage(img);
        }


        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        userService.updateUser(email, userUpdate);

        return "redirect:/account";
    }
}
