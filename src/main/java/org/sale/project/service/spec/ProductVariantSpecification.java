package org.sale.project.service.spec;


import org.sale.project.entity.ProductVariant;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductVariantSpecification {

    public static Specification<ProductVariant> hasCategory(List<String> categoryNames) {
        return (root, query, criteriaBuilder) -> {
            if (categoryNames == null || categoryNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("product").get("category").get("name").in(categoryNames);
        };
    }


    public static Specification<ProductVariant> hasColor(List<String> colors) {
        return (root, query, criteriaBuilder) -> {
            if (colors == null || colors.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("color").get("name").in(colors);
        };
    }

    public static Specification<ProductVariant> hasSize(List<String> sizes) {
        return (root, query, criteriaBuilder) -> {
            if (sizes == null || sizes.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("size").get("name").in(sizes);
        };
    }

    public static Specification<ProductVariant> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query,builder) -> {
            if (minPrice != null && maxPrice != null) {
                return builder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return builder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return builder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return null;
        };
    }
}
