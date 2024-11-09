package org.sale.project.service;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Cart;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.ProductVariant;
import org.sale.project.entity.User;
import org.sale.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CartService {

    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    UserRepository userRepository;
    ProductVariantRepository productVariantRepository;
    AccountRepository accountRepository;

    public Cart findCartByUser(User user){
        return cartRepository.findByUser(user);
    }


    public void addProductToCart(String email, String product_item_id, int quantity, HttpSession session){

        Optional<User> user = userRepository.findByAccount(accountRepository.findByEmail(email).get());
        if(user.isPresent()){
            Cart cart = findCartByUser(user.get());
            if(cart == null){
                cart = new Cart();
                cart.setUser(user.get());
                cart.setCartItems(new ArrayList<>());
                cart = cartRepository.save(cart);
                user.get().setCart(cart);
                userRepository.save(user.get());
            }

            Optional<ProductVariant> items = productVariantRepository.findById(product_item_id);
            if(items.isPresent()){
                CartItem cartItem = cartItemRepository.findByCartAndProductVariant(cart, items.get());
                if(cartItem == null){
                    cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProductVariant(items.get());
                    cartItem.setQuantity(quantity);
                    session.setAttribute("sum", (Integer)session.getAttribute("sum") + 1);
                } else{
                    cartItem.setQuantity(quantity + cartItem.getQuantity());
                }


                cartItemRepository.save(cartItem);

            }
        }




    }

    public void removeCartItem(String cart_detail_id){
        cartItemRepository.deleteById(cart_detail_id);
    }

    public double totalPriceInCart(Cart cart){
        List<CartItem> items = cart == null ? null : cart.getCartItems();
        if(items == null){
            return 0;
        }
        double total = 0;
        for(CartItem item : items){
            total += item.getProductVariant().getPrice() * item.getQuantity();
        }
        return total;
    }


}
