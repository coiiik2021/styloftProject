package org.sale.project.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.service.ProductService;
import org.sale.project.service.RoleService;
import org.sale.project.service.UserService;
import org.sale.project.service.email.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    RoleService roleService;
    ProductService productService;
    EmailService emailService;

    // @GetMapping("/login")
    // public String getPageLogin(Model model){
    // model.addAttribute("user", new User());
    // return "/client/auth/login";
    // }

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        model.addAttribute("newUser", new User());
        return "/client/auth/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">> user" + fieldError.getField() + fieldError.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "/client/auth/register";
        }

        emailService.sendEmail(
                SendEmailRequest.builder()
                        .subject("REGISTER CORRECT")
                        .htmlContent("<html>" +
                                "<body>" +
                                "<div style='background-color: #f0f0f0; padding: 20px;'>" +
                                "<h2 style='color: #ff6600;'>Welcome to our service!</h2>" +
                                "<p>AnhDungShop</p>" +
                                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong> " +user.getEmail() +"</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                                "<p style='color: #333;'>Chúc  <strong> bạn  </strong> có thời gian mua sắm vui vẻ!!!</p>"  +
                                "<p style='color: #333;'>Nếu có thắc mắc gì mong  <strong> bạn </strong> phản hồi lại sớm cho bên chúng tôi</p>"  +


                                "<a href='http://localhost:8080' style='color: #0066cc;'>Visit our website</a>" +
                                "<br><br><p>Best regards,<br>AnhDungShop</p>" +
                                "</div>" +
                                "</body>" +
                                "</html>")

                        .to(
                                Recipient.builder()
                                        .email(user.getEmail())
                                        .name(user.getName())

                                        .build()
                        )
                        .build()
        );


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("USER"));
        user.setSex(-1);
        userService.saveUser(user);
        return "redirect:/client/home/show";
    }

    @GetMapping
    public String getPageHome(Model model, HttpServletRequest request,
            @RequestParam("page") Optional<String> pageOptional) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);

        // page
        int page = 1;

        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {

        }

        // int totalProductNotHasItem = 0;
        // List<Product> prd = productService.findAll();
        // for(Product p : prd){
        // if(p.getProductItem().isEmpty()) ++totalProductNotHasItem;
        // }
        //
        // page = page - totalProductNotHasItem/8;

        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<Product> productPage = productService.findAll(pageable);

        List<Product> products = productPage.getContent();
        // for(Product product : products){
        // if(product.getProductItem().isEmpty())
        // products.remove(product);
        // }
        // page = products.size()/8;

        model.addAttribute("products", products);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        // session.setAttribute("sum", user.getCart().getCartItems());

        return "/client/home/show";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "/client/auth/login";
    }

    @GetMapping("/404")
    public String notFound(Model model) {
        return "/client/error/404";
    }

}
