package org.sale.project.repository;

import org.sale.project.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, String>, JpaSpecificationExecutor<Size> {
    Size findByName(String name);
    Page<Size> findAll(Pageable pageable);
//    Size findByProducVariantsId(String productId);

}
