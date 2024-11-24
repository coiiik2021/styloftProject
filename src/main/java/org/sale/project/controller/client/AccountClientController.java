package org.sale.project.controller.client;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.service.AccountService;
import org.sale.project.service.UploadService;
import org.sale.project.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

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
                                    @RequestParam("imageAvatar") MultipartFile imageAvatar,@RequestParam("idform") Optional<String> idform){

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
        session.setAttribute("checkid", idform.get());
        return "redirect:/account";
    }
    @PostMapping("/update-adress")
    public String updateAddress(Model model, HttpServletRequest request,
                                @ModelAttribute("user") @Valid User userUpdate, BindingResult bindingResult,
                                @RequestParam("idform") Optional<String> idform) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>> user: " + fieldError.getField() + fieldError.getDefaultMessage());

        }
        if (bindingResult.hasErrors()) {
            return "/client/home/information";
        }

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        userService.updateUserAddress(email, userUpdate.getAddress());
        session.setAttribute("checkid", idform.get());
        return "redirect:/account";
    }
    @PostMapping("/pass-update")
    public String updatePass(HttpServletRequest request,
                             @RequestParam("pass") String pass,
                             @RequestParam("newpass") String newpass,
                             @RequestParam ("confirmpass") String confirmpass,
                             @RequestParam("idform") Optional<String> idform,
                             Model model) {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        Optional<User> userOptional = userService.findByEmail(email);
        model.addAttribute("email", email);
//        User user = userOptional.get();

        model.addAttribute("user", userOptional.orElseGet(User::new));
        model.addAttribute("orders", userOptional.isEmpty()
                ? new ArrayList<Order>()
                : userOptional.get().getOrders().stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .collect(Collectors.toList()));

        session.setAttribute("checkid", idform.get());
        model.addAttribute("pass", pass);
        model.addAttribute("newpass", newpass);
        model.addAttribute("confirmpass", confirmpass);
        Account account = accountService.findByEmail(email);
        boolean check= passwordEncoder.matches(pass, account.getPassword());
        if(check){
            System.out.println("Dan Test"+pass);
            if(!newpass.equals(confirmpass)){
                model.addAttribute("errorPassUpdate", "Mật khẩu mới và mật khẩu xác nhận không trùng nhau");
                return "/client/home/information";

            }
            else
            {
                account.setPassword(passwordEncoder.encode(newpass));
                accountService.updateAccount(account);
            }
        }
        else {
            System.out.println("Danh Test"+pass);
            System.out.println(account.getPassword());
            System.out.println(passwordEncoder.encode(pass));
            model.addAttribute("errorPassUpdate", "Mật khẩu tài khoản không đúng");
            return "/client/home/information";
        }

        return "redirect:/account";
    }
}

