package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.entity.UserAction;
import org.sale.project.enums.ActionType;
import org.sale.project.repository.UserActionRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserActionService {

    UserActionRepository userActionRepository;

    public UserAction save(UserAction userAction) {
        if(!findByUserAndProductAndUserAction(userAction.getUser(), userAction.getProduct(), userAction.getActionType())){
            System.out.println("save");
            return userActionRepository.save(userAction);
        }

        return null;

    }

    public boolean findByUserAndProductAndUserAction(User user, Product product, ActionType action) {

        boolean flag = userActionRepository.findByUserAndProductAndActionType(user, product, action) != null;
        System.out.println(flag ? "YES" : "NO");
        return flag;
    }

    public void deleteUserAction(String id){
        userActionRepository.deleteById(id);
    }

}
