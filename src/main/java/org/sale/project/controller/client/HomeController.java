package org.sale.project.controller.client;

import com.google.gson.Gson;
import jakarta.mail.MessagingException;
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
import org.sale.project.dto.request.StarReview;
import org.sale.project.entity.*;
import org.sale.project.service.*;
import org.sale.project.service.email.EmailService;
import org.sale.project.service.review.ScoreStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.*;

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
    RecommendationService recommendationService;
    ScoreStarService scoreStarService;





    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        model.addAttribute("newAccount", new Account());
        return "/client/auth/register";
    }

    @GetMapping("/forgot")
    public String getPageForgot(Model model) {
        model.addAttribute("email", "");
        return "/client/auth/forgot";
    }

    @PostMapping("/forgot")
    public String forget(@RequestParam("email") String email, Model model) {

        String password = accountService.forgotPassword(email);

        if(password == null) {
            model.addAttribute("errorForget", "Email không tồn tại trong hệ thống");
            model.addAttribute("email", email);
            return "/client/auth/forgot";
        }

        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newAccount") @Valid Account account, BindingResult bindingResult, Model model, HttpServletRequest request) throws MessagingException {
        // Kiểm tra lỗi
        if (bindingResult.hasErrors()) {
            return "/client/auth/register";
        }
    System.out.println("KKKK");
        // Gửi email chào mừng
//        emailService.sendEmail(
//                SendEmailRequest.builder()
//                        .subject("REGISTER CORRECT")
//                        .htmlContent("<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
//                                "<h2 style='color: #ff6600;'>Welcome to our service!</h2>" +
//                                "<p>AnhDungShop</p>" +
//                                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + account.getEmail() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
//                                "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
//                                "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi</p>" +
//                                "<a href='http://localhost:8080' style='color: #0066cc;'>Visit our website</a>" +
//                                "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>")
//                        .to(Recipient.builder().email(account.getEmail()).build())
//                        .build()
//        );



        if(accountService.findByEmail(account.getEmail()) != null) {
            model.addAttribute("errorRegister", "Email đã tồn tại!!!!");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";
        }
        String email = account.getEmail();

        // Mã hóa mật khẩu và lưu người dùng
        String temppass = account.getPassword();
        if(temppass.length()<=5)
        {
            model.addAttribute("errorRegister", "Mật khẩu phải dài hơn 5 chữ số");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(roleService.findByName("USER"));
        accountService.saveAccount(account);


        // Chuyển hướng đến trang chính
        String content="<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                "<h2 style='color: #ff6600;'>Welcome to our service!</h2>" +
                "<p>AnhDungShop</p>" +
                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + account.getEmail() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
                "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi</p>" +
                "<a href='http://localhost:8080' style='color: #0066cc;'>Visit our website</a>" +
                "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>";
        emailService.sendHtmlEmail(account.getEmail(),"REGISTER CORRECT",content);
        if(accountService.findByEmail(email) == null) {

            accountService.saveAccount(Account.builder().email(email).password(passwordEncoder.encode(temppass)).role(roleService.findByName("USER")).build());
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Tạo token xác thực
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, temppass, userDetails.getAuthorities());

        // Thực hiện xác thực tự động
//        authenticationManager.authenticate(authToken);

//        SecurityContextHolder.getContext().setAuthentication(authToken);

//        securityContextHolderFilter.doFilter();

        //Xác thực
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
//        return "redirect:/client/home/show";
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
    public String getPageHome(Model model, HttpServletRequest request

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








//        Pageable pageable = PageRequest.of(page - 1, 8);
//        Page<Product> productPage = productService.findAll(pageable, false);

        List<Product> products = email != null && !email.equals("admin@gmail.com") ? productService.findAll(recommendationService.getTopNRecommendations(userService.findUserByEmail(email), 12))
                : productService.findAll() ;


        if(email != null) {
            products = products.size() == 12 ? products : fullProduct(products);
        }
        Map<Product, StarReview> mapProductAndScore = new HashMap<>();
        for(Product p : products) {
            mapProductAndScore.put(p, scoreStarService.score(p));
        }





        model.addAttribute("products", mapProductAndScore);
//        model.addAttribute("user", user);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productPage.getTotalPages());

        // session.setAttribute("sum", user.getCart().getCartItems());

        return "/client/home/show";
    }



    private List<Product> fullProduct(List<Product> products) {
        // Tạo một bản sao chỉnh sửa được từ danh sách `products`
        List<Product> editableProducts = new ArrayList<>(products);

        List<Product> fullProducts = productService.findAll();
        for (Product product : fullProducts) {
            if (editableProducts.size() == 12) {
                break;
            }
            if (!editableProducts.contains(product)) {
                editableProducts.add(product);
            }
        }
        return editableProducts;
    }



    @GetMapping("/login")
    public String login(Model model) {
        return "/client/auth/login";
    }

    @GetMapping("/404")
    public String notFound(Model model) {
        return "/client/error/404";
    }


     private <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> sublist = list.subList(start, end);
        return new PageImpl<>(sublist, pageable, list.size());
    }

}
