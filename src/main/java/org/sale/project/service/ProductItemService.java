package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductItem;
import org.sale.project.repository.ColorRepository;
import org.sale.project.repository.ProductItemRepository;
import org.sale.project.repository.ProductRepository;
import org.sale.project.service.spec.ProductItemSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductItemService {

    ProductItemRepository productItemRepository;
    ProductRepository productRepository;
    ColorRepository colorRepository;

    public List<ProductItem> getAllProductItem(){
        return productItemRepository.findAll();
    }

    public ProductItem findById(String id){
        return productItemRepository.findById(id).orElse(null);
    }


    public ProductItem saveProductItem(ProductItem productItem){
        productItem.getProduct().setStatus(true);

        productRepository.save(productItem.getProduct());
        return productItemRepository.save(productItem);
    }

    public void deleteProductItem(String id){
        productItemRepository.deleteById(id);
    }

    public Page<ProductItem> findAll(Pageable pageable){
        return productItemRepository.findAll(pageable);
    }

    public void deleteAllByProductId(String productId){

        List<ProductItem> productItems = productItemRepository.findByProductId(productId);
        if(productItems.size() > 0){
            productItemRepository.deleteAll(productItems);

        }

    }

    public void updateAllImage(ProductItem productItem, String image){

        List<ProductItem> items = productItemRepository.findAllByColorAndProduct(
                colorRepository.findByName(productItem.getColor().getName()),
                productRepository.findByName(productItem.getProduct().getName()));

        for(ProductItem item : items){
            item.setImage(image);
            productItemRepository.save(item);
        }
    }

//    public Page<ProductItem> findByProductId(String productId, Pageable pageable){
//        return productItemRepository.findAll(ProductItemSpec.findAllByProductId(productId), pageable);
//    }

    public Page<ProductItem> findByProduct(List<Product> products, Pageable pageable){


        return productItemRepository.findAllByProductIn(products, pageable);
    }

    public List<ProductItem> filterProductItems(List<String> categories, List<String> colors, List<String> sizes, Pageable pageable) {
        Specification<ProductItem> specification = Specification
                .where(ProductItemSpecification.hasCategory(categories))
                .and(ProductItemSpecification.hasColor(colors))
                .and(ProductItemSpecification.hasSize(sizes));

        return productItemRepository.findAll(specification);
    }


    public void updateProductItem(ProductItem productItem, int quantity){
        Optional<ProductItem> itemOptional =productItemRepository.findById(productItem.getId());
        ProductItem item = new ProductItem();
        if(itemOptional.isPresent()){
            item = itemOptional.get();
        } else{
            return;
        }
        item.setQuantity(Math.max(item.getQuantity() - productItem.getQuantity(), 0));

        productItemRepository.save(item);

    }


}
