package org.sale.project.controller.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.sale.project.entity.*;
import org.sale.project.service.*;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    CategoryService categoryService;

    @GetMapping
    public String getPageProducts(Model model, @RequestParam("name") Optional<String> nameOptional,
            @RequestParam("page") Optional<String> pageOptional) {


        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {

        }

        Pageable pageable = PageRequest.of(page - 1, 12);

        Page<Product> productPage;
        if(nameOptional.isPresent()) {
            productPage = productService.findAll(nameOptional.get(), pageable);

        } else{
            productPage = productService.findAll(pageable);
        }

        List<Product> products = productPage.getContent();

        List<Category> categories = categoryService.findAll();
        List<Color> colors = colorService.findAll();
        List<Size> sizes = sizeService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);

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

        for (ProductItem items : product.getProductItem()) {
            sizes.add(items.getSize());
            if (size.isEmpty() || items.getSize().getName().equals(size)) {
                colors.add(items.getColor());
            }
        }

        ProductItem selectedItem = null;
        for (ProductItem item : product.getProductItem()) {
            boolean sizeMatches = size.isEmpty() || item.getSize().getName().equals(size);
            boolean colorMatches = color.isEmpty() || item.getColor().getName().equals(color);

            if (sizeMatches && colorMatches) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            selectedItem = product.getProductItem().getLast();
        }

        List<Color> sortedColors = new ArrayList<>(colors);
        sortedColors.sort(Comparator.comparing(Color::getName));

        List<Size> sortedSizes = new ArrayList<>(sizes);
        sortedSizes.sort(Comparator.comparing(Size::getName));

        model.addAttribute("item", selectedItem);
        model.addAttribute("colors", sortedColors);
        model.addAttribute("sizes", sortedSizes);

        return "/client/product/detail";
    }

    @GetMapping("/filter")
    public String filterProducts(
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam(value = "colors", required = false) String colors,
            @RequestParam(value = "sizes", required = false) String sizes,
            @RequestParam("minPrice") Optional<String> minPriceOptional,
            @RequestParam("maxPrice") Optional<String> maxPriceOptional,
            Model model) {

        double minPrice = Double.parseDouble(minPriceOptional.orElse("0"));
        double maxPrice = Double.parseDouble(maxPriceOptional.orElse("1000000000000.0"));

        List<String> categoryList = categories != null ? Arrays.asList(categories.split(",")) : Collections.emptyList();

        List<String> colorList = colors != null ? Arrays.asList(colors.split(",")) : Collections.emptyList();

        List<String> sizeList = sizes != null ? Arrays.asList(sizes.split(",")) : Collections.emptyList();

        Pageable pageable = PageRequest.of(0, 12);

        Page<Product> productPage = productService.filterProducts(categoryList, colorList, sizeList, minPrice, maxPrice,
                pageable);

        List<Product> products = productPage.getContent();

        List<Category> category = categoryService.findAll();
        List<Color> color = colorService.findAll();
        List<Size> size = sizeService.findAll();

        model.addAttribute("categories", category);
        model.addAttribute("colors", color);
        model.addAttribute("sizes", size);

        model.addAttribute("products", products);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", 1);

        return "client/product/show";
    }
}
