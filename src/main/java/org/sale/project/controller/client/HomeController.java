package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.service.ProductService;
import org.sale.project.service.RoleService;
import org.sale.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

//    @GetMapping("/login")
//    public String getPageLogin(Model model){
//        model.addAttribute("user", new User());
//        return "/client/auth/login";
//    }

    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("newUser", new User());
        return "/client/auth/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("newUser") User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("USER"));
        user.setSex(-1);
        userService.saveUser(user);
        return "redirect:/client/home/show";
    }


    @GetMapping
    public String getPageHome(Model model, HttpServletRequest request,@RequestParam("page") Optional<String> pageOptional){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);

        // page
        int page = 1;

        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e){

        }

//        int totalProductNotHasItem = 0;
//        List<Product> prd = productService.findAll();
//        for(Product p : prd){
//            if(p.getProductItem().isEmpty()) ++totalProductNotHasItem;
//        }
//
//        page = page - totalProductNotHasItem/8;

        Pageable pageable = PageRequest.of(page-1, 8);
        Page<Product> productPage = productService.findAll(pageable);

        List<Product> products = productPage.getContent();
//        for(Product product : products){
//            if(product.getProductItem().isEmpty())
//                products.remove(product);
//        }
//        page = products.size()/8;

        model.addAttribute("products", products);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

//        session.setAttribute("sum", user.getCart().getCartItems());




        return "/client/home/show";
    }





    @GetMapping("/login")
    public String login(Model model){
        return "/client/auth/login";
    }

    @GetMapping("/404")
    public String notFound(Model model){
        return "/client/error/404";
    }





}
