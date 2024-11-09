package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.service.AccountService;
import org.sale.project.service.OrderService;
import org.sale.project.service.ProductService;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class DashBoardController {

    ProductService productService;
    OrderService orderService;
    AccountService accountService;


    @GetMapping
    public String getPageAdmin(Model model) {
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");



        model.addAttribute("months", months);
        model.addAttribute("revenues", orderService.totalRevenueInMonth());
        model.addAttribute("totalRevenue", orderService.totalRevenue());
        model.addAttribute("totalAccount", accountService.countAccount());
        model.addAttribute("totalProduct", productService.countProduct());
        model.addAttribute("totalOrder", orderService.countOrder());


        return "/admin/dashboard/show";
    }

}
