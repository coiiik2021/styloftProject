package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Category;
import org.sale.project.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CategoryController {

    CategoryService categoryService;

    @GetMapping
    public String getPageCate(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Category> pCate = categoryService.findAll(pageable);


        List<Category> categories = pCate.getContent();
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pCate.getTotalPages());
        return "/admin/category/show";
    }

    @GetMapping("/create")
    public String getPageCreate(Model model) {
        model.addAttribute("newCategory", new Category());
        return "/admin/category/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("newCategory") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable String id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "/admin/category/update";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("category") Category category) {
        Category oldCategory = categoryService.findById(category.getId());
        oldCategory.setDescription(category.getDescription());
        categoryService.saveCategory(oldCategory);
        return "redirect:/admin/category";
    }


}
