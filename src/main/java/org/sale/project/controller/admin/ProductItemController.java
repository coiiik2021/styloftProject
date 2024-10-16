package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.ProductItem;
import org.sale.project.service.ColorService;
import org.sale.project.service.ProductItemService;
import org.sale.project.service.ProductService;
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
@RequestMapping("/admin/item")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductItemController {

    ProductService productService;
    ColorService colorService;
    SizeService sizeService;
    ProductItemService productItemService;

    @GetMapping
    public String getPageItem(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<ProductItem> pItem = productItemService.findAll(pageable);
        List<ProductItem> items = pItem.getContent();
        model.addAttribute("productItems", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pItem.getTotalPages());
        return "/admin/item/show";
    }

    @GetMapping("/create")
    public String getPageCreate(Model model) {
        model.addAttribute("newItem", new ProductItem());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("products", productService.findAll());
        return "/admin/item/create";
    }

    @PostMapping("/create")
    public String createProductItem(@ModelAttribute("newItem") ProductItem productItem) {

        productItem.setColor(colorService.findByName(productItem.getColor().getName()));
        productItem.setSize(sizeService.findByName(productItem.getSize().getName()));
        productItem.setProduct(productService.findByProductName(productItem.getProduct().getName()));

        productItemService.saveProductItem(productItem);

        return "redirect:/admin/item";

    }

    @GetMapping("/delete/{id}")
    public String deleteProductItem(@PathVariable("id") String id) {
        productItemService.deleteProductItem(id);
        return "redirect:/admin/item";
    }

    @GetMapping("/update/{id}")
    public String updateProductItem(@PathVariable("id") String id, Model model) {
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("item", productItemService.findById(id));
        return "/admin/item/update";
    }

    @PostMapping("/update")
    public String updateProductItem(@ModelAttribute("item") ProductItem productItem) {

        productItem.setColor(colorService.findByName(productItem.getColor().getName()));
        productItem.setSize(sizeService.findByName(productItem.getSize().getName()));
        productItem.setProduct(productService.findByProductName(productItem.getProduct().getName()));
        productItemService.saveProductItem(productItem);
        return "redirect:/admin/item";
    }


}
