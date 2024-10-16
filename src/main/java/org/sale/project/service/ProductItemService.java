package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.ProductItem;
import org.sale.project.repository.ProductItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductItemService {

    ProductItemRepository productItemRepository;

    public List<ProductItem> getAllProductItem(){
        return productItemRepository.findAll();
    }

    public ProductItem findById(String id){
        return productItemRepository.findById(id).orElse(null);
    }


    public ProductItem saveProductItem(ProductItem productItem){
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


}
