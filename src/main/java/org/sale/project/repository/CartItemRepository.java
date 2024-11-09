package org.sale.project.repository;

import org.sale.project.entity.Cart;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findByCartAndProductVariant(Cart cart, ProductVariant productVariant);
}
