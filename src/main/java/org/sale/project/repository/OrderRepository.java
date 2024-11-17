package org.sale.project.repository;

import org.sale.project.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    Page<Order> findAll(Pageable pageable);

//    Page<Order> findAllBeginToFinish(Specification<Order> spec);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

}
