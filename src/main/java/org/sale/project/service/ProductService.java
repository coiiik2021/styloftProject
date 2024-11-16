package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.sale.project.repository.CategoryRepository;
import org.sale.project.repository.ProductVariantRepository;
import org.sale.project.repository.ProductRepository;
import org.sale.project.service.spec.ProductVariantSpecification;
import org.sale.project.service.spec.ProductSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
    CategoryRepository categoryRepository;
    UploadService uploadService;

    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        if(product.getId() == null && productRepository.findByName(product.getName()) == null) {
            return productRepository.save(product);

        }
        return null;

    }

    public List<Product> findAll(List<String> productIds) {
        return productRepository.findAllById(productIds);
    }



    public void deleteProduct(String id) {
        Product product = findById(id);
        product.setStatus(false);
        productRepository.save(product);
    }

    public Product findByProductName(String productName) {

        return productRepository.findByName(productName);
    }

    public Page<Product> findAll(Pageable pageable, boolean isAdmin) {
//        return productRepository.findAll(pageable);
        return isAdmin ? productRepository.findAll(pageable) : productRepository.findAllByStatus(pageable,true);
    }

    public Page<Product> findAll(String name, Pageable pageable) {
        return productRepository.findAll(ProductSpec.nameLike(name), pageable);
    }

    public int countProduct() {
        return productRepository.findAll().size();
    }

    public List<Product> findAll(String name) {
        return productRepository.findAll(ProductSpec.nameLike(name));
    }

    public Page<Product> filterProducts(List<String> categories, List<String> colors, List<String> sizes, double min,
            double max, Pageable pageable) {
        Specification<ProductVariant> specification = Specification
                .where(ProductVariantSpecification.hasCategory(categories))
                .and(ProductVariantSpecification.hasColor(colors))
                .and(ProductVariantSpecification.hasSize(sizes))
                .and(ProductVariantSpecification.hasPriceBetween(min, max));

        Page<ProductVariant> productItemsPage = productVariantRepository.findAll(specification, pageable);

        Set<Product> uniqueProducts = new HashSet<>();
        productItemsPage.forEach(productItem -> uniqueProducts.add(productItem.getProduct()));

        List<Product> productList = new ArrayList<>(uniqueProducts);

        return new PageImpl<>(productList, pageable, uniqueProducts.size());
    }

    public void updateProduct(Product productUpdate) {
        Optional<Product> oldProductOptional = productRepository.findById(productUpdate.getId());
        Product oldProduct = new Product();

        if (oldProductOptional.isPresent()) {
            oldProduct = oldProductOptional.get();
        } else {
            return;
        }

        uploadService.updateNameFileProduct(oldProduct.getName(), productUpdate.getName());
        oldProduct.setName(productUpdate.getName());
        oldProduct.setDescription(productUpdate.getDescription());
        oldProduct.setCategory(categoryRepository.findByName(productUpdate.getCategory().getName()));

        oldProduct.setStatus(true);
        productRepository.save(oldProduct);

    }

}
