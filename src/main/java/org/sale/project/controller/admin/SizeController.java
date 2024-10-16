package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Size;
import org.sale.project.service.SizeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/size")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SizeController {

    SizeService sizeService;

    @GetMapping
    public String getPageSize(Model model, @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Size> pSize = sizeService.findAll(pageable);

        List<Size> sizes = pSize.getContent();


        model.addAttribute("sizes", sizes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pSize.getTotalPages());
        return "/admin/size/show";
    }

    @GetMapping("/create")
    public String getPageCreateSize(Model model) {
        model.addAttribute("newSize", new Size());
        return "/admin/size/create";
    }

    @PostMapping("/create")
    public String createSize(Model model, Size size) {
        sizeService.saveSize(size);
        return "redirect:/admin/size";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSize(@PathVariable String id) {
        sizeService.deleteById(id);
        return "redirect:/admin/size";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdateSize(@PathVariable String id, Model model) {
        model.addAttribute("size", sizeService.findById(id));
        return "/admin/size/update";
    }

    @PostMapping("/update")
    public String updateSize(Model model,@ModelAttribute("size") Size size) {
        Size oldSize = sizeService.findById(size.getId());
        oldSize.setDescription(size.getDescription());

        sizeService.saveSize(oldSize);
        return "redirect:/admin/size";
    }
}
