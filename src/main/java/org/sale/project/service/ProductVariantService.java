package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Color;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.sale.project.mapper.ProductVariantMapper;
import org.sale.project.repository.ColorRepository;
import org.sale.project.repository.ProductVariantRepository;
import org.sale.project.repository.ProductRepository;
import org.sale.project.repository.SizeRepository;
import org.sale.project.service.spec.ProductVariantSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {

    ProductVariantRepository productVariantRepository;
    ProductRepository productRepository;
    ColorRepository colorRepository;

    ProductVariantMapper productVariantMapper;
    private final SizeRepository sizeRepository;

    public List<ProductVariant> getAllProductItem(){
        return productVariantRepository.findAll();
    }

    public ProductVariant findById(String id){
        return productVariantRepository.findById(id).orElse(null);
    }

    public boolean checkExistsByColorAndProduct(ProductVariant productVariant){
        return !productVariantRepository.findAllByColorAndProduct(
                colorRepository.findByName(productVariant.getColor().getName()),
                productRepository.findByName(productVariant.getProduct().getName())
        ).isEmpty();
    }

    public boolean checkExistsByColorAndProductAndSize(ProductVariant productVariant){
        return
                productVariantRepository.findByColorAndProductAndSize(
                colorRepository.findByName(productVariant.getColor().getName()),
                productRepository.findByName(productVariant.getProduct().getName()),
                sizeRepository.findByName(productVariant.getSize().getName())
        ) != null;

    }

    public String getImage(ProductVariant productVariant){
        return productVariantRepository.findAllByColorAndProduct(
                colorRepository.findByName(productVariant.getColor().getName()),
                productRepository.findByName(productVariant.getProduct().getName())
        ).get(0).getImage();
    }


    public ProductVariant saveProductItem(ProductVariant productVariant){
        productVariant.getProduct().setStatus(true);

        productRepository.save(productVariant.getProduct());
        return productVariantRepository.save(productVariant);
    }

    public void deleteProductItem(String id){

        ProductVariant productVariant = productVariantRepository.findById(id).orElse(null);
        if(productVariant != null){
            productVariant.setQuantity(0);
            productVariantRepository.save(productVariant);
            productVariant.getProduct().setStatus(false);
            productRepository.save(productVariant.getProduct());
        }


//        productVariantRepository.deleteById(id);
    }

    public Page<ProductVariant> findAll(Pageable pageable){
        return productVariantRepository.findAll(pageable);
    }

    public void deleteAllByProductId(String productId){

        List<ProductVariant> productVariants = productVariantRepository.findByProductId(productId);
        if(productVariants.size() > 0){
            productVariantRepository.deleteAll(productVariants);

        }

    }

    public void updateAllImage(ProductVariant productVariant, String image){

        List<ProductVariant> items = productVariantRepository.findAllByColorAndProduct(
                colorRepository.findByName(productVariant.getColor().getName()),
                productRepository.findByName(productVariant.getProduct().getName()));

        for(ProductVariant item : items){
            item.setImage(image);
            productVariantRepository.save(item);
        }
    }

//    public Page<ProductItem> findByProductId(String productId, Pageable pageable){
//        return productItemRepository.findAll(ProductItemSpec.findAllByProductId(productId), pageable);
//    }

    public Page<ProductVariant> findByProduct(List<Product> products, Pageable pageable){
        return productVariantRepository.findAllByProductIn(products, pageable);
    }

    public List<ProductVariant> filterProductItems(List<String> categories, List<String> colors, List<String> sizes, Pageable pageable) {
        Specification<ProductVariant> specification = Specification
                .where(ProductVariantSpecification.hasCategory(categories))
                .and(ProductVariantSpecification.hasColor(colors))
                .and(ProductVariantSpecification.hasSize(sizes));

        return productVariantRepository.findAll(specification);
    }

    public void updateProductVariant(ProductVariant productVariantUpdate){

        ProductVariant productVariant = productVariantRepository.findById(productVariantUpdate.getId()).orElse(null);
        if(productVariant != null){
            productVariantMapper.updateProduct(productVariant, productVariantUpdate);
            productVariantRepository.save(productVariant);
        }

    }


    public void updateProductItem(ProductVariant productVariant, int quantity){
        Optional<ProductVariant> itemOptional = productVariantRepository.findById(productVariant.getId());
        ProductVariant item = new ProductVariant();
        if(itemOptional.isPresent()){
            item = itemOptional.get();
        } else{
            return;
        }
        item.setQuantity(Math.max(item.getQuantity() - productVariant.getQuantity(), 0));

        productVariantRepository.save(item);

    }


}
