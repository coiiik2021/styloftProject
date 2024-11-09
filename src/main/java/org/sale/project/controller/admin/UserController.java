package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.entity.User;
import org.sale.project.service.AccountService;
import org.sale.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class UserController {
    UserService userService;


    @GetMapping("/{id}")
    public String getPageDetail(Model model, @PathVariable String id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/admin/user/detail";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }


}
