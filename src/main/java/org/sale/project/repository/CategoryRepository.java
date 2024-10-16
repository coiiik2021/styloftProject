package org.sale.project.repository;


import org.sale.project.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> , JpaSpecificationExecutor<Category> {
    Category findByName(String name);

    Page<Category> findAll(Pageable pageable);
//    Page<Category> findAll(Specification<Category> specification, Pageable pageable);
}
