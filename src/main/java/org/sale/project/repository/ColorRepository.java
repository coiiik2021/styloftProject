package org.sale.project.repository;


import org.sale.project.entity.Color;
import org.sale.project.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, String>, JpaSpecificationExecutor<Color> {
    Color findByName(String name);
    Page<Color> findAll(Pageable pageable);

    List<Color> findAllByProductVariants(ProductVariant productVariant);

}
