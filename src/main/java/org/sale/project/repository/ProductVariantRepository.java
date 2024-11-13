package org.sale.project.repository;

import org.sale.project.entity.Color;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.sale.project.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String>, JpaSpecificationExecutor<ProductVariant> {
    Page<ProductVariant> findAll(Pageable pageable);

    List<ProductVariant> findByProductId(String productId);

    List<ProductVariant> findAllByColorAndProduct(Color color, Product product);

    Page<ProductVariant> findAllByProductIn(List<Product> products, Pageable pageable);

    Page<ProductVariant> findAll(Specification<ProductVariant> spec, Pageable pageable);

    ProductVariant findByColorAndProductAndSize(Color color, Product product, Size size);

}
