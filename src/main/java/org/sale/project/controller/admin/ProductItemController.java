package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductItem;
import org.sale.project.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    UploadService uploadService;

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

        Pageable pageable = PageRequest.of(page-1, 4);
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
    public String createProductItem(@ModelAttribute("newItem") ProductItem productItem,
                                    @RequestParam("imageItem") MultipartFile imageItem)  {
        String img = uploadService.uploadImage(imageItem, "/product/" + productItem.getProduct().getName());

        productItem.setImage(img);

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
    public String updateProductItem(@PathVariable("id") String id, Model model
                                    ) {

        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("item", productItemService.findById(id));
        return "/admin/item/update";
    }

    @PostMapping("/update")
    public String updateProductItem(@ModelAttribute("item") ProductItem productItem,
                                    @RequestParam("imageItem") MultipartFile imageItem) {

        ProductItem prd = productItemService.findById(productItem.getId());


            if(!imageItem.isEmpty() && (prd.getImage() == null || !prd.getImage().contains(imageItem.getOriginalFilename())) ){
                String img = uploadService.uploadImage(imageItem, "/product/" + productItem.getProduct().getName());

                productItemService.updateAllImage(productItem, img);
            }

        prd.setColor(colorService.findByName(productItem.getColor().getName()));
        prd.setSize(sizeService.findByName(productItem.getSize().getName()));
        prd.setQuantity(productItem.getQuantity());
        prd.setPrice(productItem.getPrice());
        prd.setProduct(productService.findByProductName(productItem.getProduct().getName()));
        productItemService.saveProductItem(prd);
        return "redirect:/admin/item";
    }

    @GetMapping("/search")
    public String findAllByNameProduct(@RequestParam("query") String nameProduct, Model model){

        if(nameProduct.isEmpty()){
            return "redirect:/admin/item";
        }
        List<Product> products = productService.findAll(nameProduct);

        Pageable pageable = PageRequest.of(0, 4);

        Page<ProductItem> itemPage = productItemService.findByProduct(products, pageable);
        List<ProductItem> items = itemPage.getContent();


        model.addAttribute("productItems", items);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", itemPage.getTotalPages());
        return "/admin/item/show";
    }


}
