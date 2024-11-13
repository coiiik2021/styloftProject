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
import org.sale.project.controller.auth.FacebookLogin;
import org.sale.project.controller.auth.GgSTant;
import org.sale.project.controller.auth.GoogleLogin;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.*;
import org.sale.project.service.*;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
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
    AccountService accountService;





    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        model.addAttribute("newUser", new Account());
        return "/client/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newAccount") @Valid Account account, BindingResult bindingResult) {
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
                                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + account.getEmail() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                                "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
                                "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi</p>" +
                                "<a href='http://localhost:8080' style='color: #0066cc;'>Visit our website</a>" +
                                "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>")
                        .to(Recipient.builder().email(account.getEmail()).build())
                        .build()
        );

        // Mã hóa mật khẩu và lưu người dùng
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(roleService.findByName("USER"));
        accountService.saveAccount(account);

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

//    SecurityContextHolderFilter securityContextHolderFilter;
    CustomUserDetailsService customUserDetailsService;

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


        if(accountService.findByEmail(email) == null) {

            accountService.saveAccount(Account.builder().email(email).password(passwordEncoder.encode(password)).role(roleService.findByName("USER")).build());
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Tạo token xác thực
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        // Thực hiện xác thực tự động
//        authenticationManager.authenticate(authToken);

//        SecurityContextHolder.getContext().setAuthentication(authToken);

//        securityContextHolderFilter.doFilter();

        SecurityContextHolder.getContext().setAuthentication(authToken);
        User user = userService.findUserByEmail(email);

        HttpSession session = request.getSession(true);


        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        session.setAttribute("email", email);
        if(user != null) {
            session.setAttribute("id", user.getId());
            session.setAttribute("sum", user.getCart() == null || user.getCart().getCartItems() == null ? 0
                    : user.getCart().getCartItems().size());

        }


        // Đặt Authentication vào SecurityContextHolder để đăng nhập
//        if (authToken.isAuthenticated()) {
//        }


//        return googlePojo;

        return "redirect:/";
    }

    @GetMapping("/facebook")
    public String accessLoginFacebook(Model model, HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        System.out.println(">>>>code: " + code);


        String accessToken = FacebookLogin.getToken(code);

        System.out.println(">>>>accessToken: " + accessToken);

        FacebookAccount fbAccount = FacebookLogin.getUserInfo(accessToken);
        System.out.println(fbAccount.toString());


        String email = fbAccount.getEmail();

        String password = fbAccount.getEmail().substring(0, fbAccount.getEmail().indexOf("@"));

        System.out.println(">>>>email: " + email + ", password: " + password);

        return "/client/thank/show";
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
//        System.out.println(email);


        session.setAttribute("nameSearch" , "");
//        User user = userService.findUserByEmail(email);


//        System.out.println(">>>code" + code );

//        if(code != null) {
//
//        } else{
//            email = (String) session.get Attribute("email");
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
//        model.addAttribute("user", user);
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
