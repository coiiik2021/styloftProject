package org.sale.project.controller.client;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.sale.project.controller.auth.GgSTant;
import org.sale.project.controller.auth.GoogleLogin;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.GoogleAccount;
import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.service.ProductService;
import org.sale.project.service.RoleService;
import org.sale.project.service.UserService;
import org.sale.project.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
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





    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        model.addAttribute("newUser", new User());
        return "/client/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        // Kiểm tra lỗi
        if (bindingResult.hasErrors()) {
            return "/client/auth/register";
        }

        // Gửi email chào mừng
        emailService.sendEmail(
                SendEmailRequest.builder()
                        .subject("REGISTER CORRECT")
                        .htmlContent("<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                                "<h2 style='color: #ff6600;'>Welcome to our service!</h2>" +
                                "<p>AnhDungShop</p>" +
                                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + user.getEmail() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                                "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
                                "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi</p>" +
                                "<a href='http://localhost:8080' style='color: #0066cc;'>Visit our website</a>" +
                                "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>")
                        .to(Recipient.builder().email(user.getEmail()).name(user.getName()).build())
                        .build()
        );

        // Mã hóa mật khẩu và lưu người dùng
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("USER"));
        user.setSex(-1);
        userService.saveUser(user);

        // Đăng nhập người dùng
//        System.out.println(">>>user: " + user.getEmail() + ", name: " + user.getPassword());
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), user.getPassword(), userDetails.getAuthorities());
//
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//        // Thiết lập SecurityContext để đăng nhập người dùng
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Chuyển hướng đến trang chính
        return "redirect:/client/home/show";
    }

    @GetMapping("/google")
    public String accessLogin(Model model, HttpServletRequest request) throws Exception {

        String code = request.getParameter("code");

        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);

        System.out.println(">>>>accessToken: " + accessToken);

        GoogleAccount ggAc = gg.getUserInfo(accessToken);
        System.out.println(ggAc.toString());


        String email = ggAc.getEmail();

        String password = ggAc.getEmail().substring(0, ggAc.getEmail().indexOf("@"));

        System.out.println(">>>>email: " + email + ", password: " + password);


        userService.saveUser(User.builder().email(email).password(passwordEncoder.encode(password)).build());


//        return googlePojo;

        return "redirect:/client/home/show";
    }

    @GetMapping
    public String getPageHome(Model model, HttpServletRequest request,
            @RequestParam("page") Optional<String> pageOptional
                              ) throws IOException {


        HttpSession session = request.getSession();

//        if(codeOptional.isPresent()) {
//            System.out.println(codeOptional.get());
//        }

//        String code = request.getParameter("code");

        String email = (String) session.getAttribute("email");

        User user = userService.findUserByEmail(email);


//        System.out.println(">>>code" + code );

//        if(code != null) {
//
//        } else{
//            email = (String) session.getAttribute("email");
//            user = userService.findUserByEmail(email);
//
//        }

//        if(codeOptional.isPresent()) {
//
//
//
//        }




        // page
        int page = 1;

        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {

        }


        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<Product> productPage = productService.findAll(pageable, false);

        List<Product> products = productPage.getContent();


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
