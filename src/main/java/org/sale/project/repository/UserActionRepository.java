package org.sale.project.repository;


import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.entity.UserAction;
import org.sale.project.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActionRepository  extends JpaRepository<UserAction, String> {

    List<UserAction> findByUser(User user);

    UserAction findByUserAndProductAndActionType(User user, Product product, ActionType actionType);
}
