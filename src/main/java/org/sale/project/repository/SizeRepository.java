package org.sale.project.repository;

import org.sale.project.entity.Product;
import org.sale.project.entity.ProductItem;
import org.sale.project.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, String>, JpaSpecificationExecutor<Size> {
    Size findByName(String name);
    Page<Size> findAll(Pageable pageable);
    Size findByProductItemsId(String productId);

}
