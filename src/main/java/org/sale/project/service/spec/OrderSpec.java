package org.sale.project.service.spec;

import org.sale.project.entity.Order;
import org.sale.project.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class OrderSpec {

    public static Specification<Order> findOrderById(String startId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), startId+"%");
    }
}
