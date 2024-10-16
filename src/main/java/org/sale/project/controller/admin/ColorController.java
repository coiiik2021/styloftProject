package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Color;
import org.sale.project.service.ColorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/color")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ColorController {

    ColorService colorService;



    @GetMapping
    public String getPageColor(Model model, @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Color> pColor = colorService.findAll(pageable);


        List<Color> colors = pColor.getContent();
        model.addAttribute("colors", colors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pColor.getTotalPages());
        return "/admin/color/show";
    }

    @GetMapping("/create")
    public String getPageCreate(Model model) {
        model.addAttribute("newColor", new Color());
        return "/admin/color/create";
    }

    @PostMapping("/create")
    public String createColor(@ModelAttribute Color color) {
        colorService.saveColor(color);
        return "redirect:/admin/color";
    }

    @GetMapping("/delete/{id}")
    public String deleteColor(@PathVariable String id) {
        colorService.deleteById(id);
        return "redirect:/admin/color";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable String id, Model model) {
        model.addAttribute("color", colorService.findById(id));
        return "/admin/color/update";
    }

    @PostMapping("/update")
    public String updateColor(@ModelAttribute Color color) {

        Color colorUpdate = colorService.findById(color.getId());
        colorUpdate.setDescription(color.getDescription());

        colorService.saveColor(colorUpdate);
        return "redirect:/admin/color";
    }


}
