package org.sale.project.repository;

import org.sale.project.entity.Cart;
import org.sale.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Cart findByUser(User user);

}
