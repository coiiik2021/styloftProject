package org.sale.project.repository;

import org.sale.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Product findByName(String name);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByStatus(Pageable pageable, boolean status);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    List<Product> findAll(Specification<Product> spec);


}
