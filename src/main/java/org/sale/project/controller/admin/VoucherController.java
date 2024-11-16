package org.sale.project.controller.admin;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sale.project.dto.response.CustomDoubleEditor;
import org.sale.project.entity.Voucher;
import org.sale.project.service.VoucherService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/voucher")
@AllArgsConstructor
public class VoucherController {

    VoucherService voucherService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new CustomDoubleEditor());
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping
    public String getPageVoucher(@RequestParam("page") Optional<Integer> pageOptional, Model model){

        int page = pageOptional.orElse(1);

        Pageable pageable = PageRequest.of(page-1, 10);

        Page<Voucher> pageVoucher = voucherService.getAllVoucher(pageable);

        List<Voucher> vouchers = pageVoucher.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("totalPages", pageVoucher.getTotalPages());

        return "/admin/voucher/show";
    }


    @GetMapping("/create")
    public String getPageCreate(Model model){
        model.addAttribute("newVoucher", new Voucher());
        return "/admin/voucher/create";
    }


    @PostMapping("/create")
    public String createVoucher(@ModelAttribute("newVoucher") @Valid Voucher voucher, BindingResult bindingResult, Model model){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for(FieldError fieldError : fieldErrors){
            System.out.println(fieldError.getDefaultMessage());
        }

        if(bindingResult.hasErrors() || voucherService.findByCode(voucher.getCode()) != null){
            model.addAttribute("newVoucher", voucher);
            if(!bindingResult.hasErrors()){
                model.addAttribute("voucherExists", "Voucher đã tồn tại vui lòng bạn nhập lại");
            }
            return "/admin/voucher/create";
        }



        voucherService.saveVoucher(voucher);

        return "redirect:/admin/voucher";

    }

    @GetMapping("/active/{id}")
    public String deleteVoucher(@PathVariable("id") String id, Model model){

        voucherService.active(id);
        return "redirect:/admin/voucher";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable("id") String id, Model model){
        model.addAttribute("voucher", voucherService.findById(id) != null ? voucherService.findById(id) : new Voucher());

        return "/admin/voucher/update";
    }


    @PostMapping("/update")
    public String updateVoucher(@ModelAttribute("voucher") @Valid Voucher voucherUpdate, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("voucher", voucherUpdate);

            return "/admin/voucher/update";
        }

        System.out.println( ">>>voucher id: " + voucherUpdate.getId());

        voucherService.updateQuantity(voucherUpdate);

        return "redirect:/admin/voucher";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleConversionError(IllegalArgumentException ex, Model model) {
        // Đảm bảo thông báo lỗi sẽ được đưa vào model
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("newVoucher", new Voucher()); // Để lại đối tượng voucher mới
        return "/admin/voucher/create"; // Quay lại trang tạo voucher
    }




}
