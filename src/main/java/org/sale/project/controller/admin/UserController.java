package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.User;
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

    @GetMapping
    public String getPageUser(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;

       try{
           if(pageOptional.isPresent()) {
               page = Integer.parseInt(pageOptional.get());
           }
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<User> pageUser = userService.findAll(pageable);

        List<User> users = pageUser.getContent();


        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageUser.getTotalPages());

        return "/admin/user/show";
    }

    @GetMapping("/{id}")
    public String getPageDetail(Model model, @PathVariable String id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/admin/user/detail";
    }


}
