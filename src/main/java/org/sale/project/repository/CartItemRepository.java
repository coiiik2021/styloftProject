package org.sale.project.repository;

import org.sale.project.entity.Cart;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findByCartAndProductItem(Cart cart, ProductItem productItem);
}
