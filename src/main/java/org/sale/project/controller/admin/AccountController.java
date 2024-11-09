package org.sale.project.controller.admin;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/account")
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AccountController {



    AccountService accountService;


    @GetMapping
    public String getPageAccount(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;

        try{
            if(pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Account> pageAccount = accountService.findAll(pageable);

        List<Account> accounts = pageAccount.getContent();


        model.addAttribute("accounts", accounts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageAccount.getTotalPages());

        return "/admin/user/show";
    }


}
