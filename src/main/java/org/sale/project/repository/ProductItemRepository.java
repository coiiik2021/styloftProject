package org.sale.project.repository;

import org.sale.project.entity.ProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, String>, JpaSpecificationExecutor<ProductItem> {
    Page<ProductItem> findAll(Pageable pageable);

    List<ProductItem> findByProductId(String productId);
}
