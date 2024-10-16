package org.sale.project.service.spec;

import org.sale.project.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service

public class ProductSpec {
    public static Specification<Product> nameLike(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }
}
