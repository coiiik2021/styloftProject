package org.sale.project.controller.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Color;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductItem;
import org.sale.project.entity.Size;
import org.sale.project.service.ColorService;
import org.sale.project.service.ProductItemService;
import org.sale.project.service.ProductService;
import org.sale.project.service.SizeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.*;

@Controller
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ItemController {

    ProductService productService;
    ProductItemService productItemService;
    ColorService colorService;
    SizeService sizeService;

    @GetMapping
    public String getPageProducts(Model model, @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if(pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {

        }

        Pageable pageable = PageRequest.of(page-1, 12);
        Page<Product> productPage = productService.findAll(pageable);
        List<Product> products = productPage.getContent();

        model.addAttribute("products", products);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "/client/product/show";
    }

    @GetMapping("/detail/{id}")
    public String getPageDetailProduct(@PathVariable("id") String id, Model model,
                                       @RequestParam("size") Optional<String> sizeOptional,
                                       @RequestParam("color") Optional<String> colorOptional) {

        String size = sizeOptional.orElse("");
        String color = colorOptional.orElse("");


        Product product = productService.findById(id);
        Set<Color> colors = new HashSet<>();
        Set<Size> sizes = new HashSet<>();
        for(ProductItem items : product.getProductItem()){
            colors.add(items.getColor());
            sizes.add(items.getSize());
        }

        if(size.isEmpty() && color.isEmpty()) {


            model.addAttribute("item", product.getProductItem().getLast());
        } else if(size.isEmpty()){
            for(ProductItem items : product.getProductItem()){
                if(items.getColor().getName().equals(color)){
                    model.addAttribute("item", items);
                    break;
                }
            }
        } else {
            for(ProductItem items : product.getProductItem()){
                if(items.getSize().getName().equals(size)){
                    model.addAttribute("item", items);
                    break;
                }
            }
        }


        model.addAttribute("product", product);

        model.addAttribute("colors", (colors.stream().toList()));
        model.addAttribute("sizes", (sizes.stream().toList()));



        return "/client/product/detail";
    }

}
