package org.sale.project.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Cart;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.User;
import org.sale.project.repository.CartItemRepository;
import org.sale.project.service.CartService;
import org.sale.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;
    UserService userService;
    private final CartItemRepository cartItemRepository;


    @GetMapping
    public String getPageCart(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        Cart cart = cartService.findCartByUser(user);
        List<CartItem> items = cart == null ? new ArrayList<>() : cart.getCartItems();

        model.addAttribute("items", items);

        model.addAttribute("totalPrice", cartService.totalPriceInCart(cart));

        return "/client/cart/show";
    }

    @GetMapping("/add-product-item-in-cart/{id}")
    public String addProductItemInCart(@PathVariable("id") String id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        cartService.addProductToCart(email, id, 1);

        User user = userService.findUserByEmail(email);
        session.setAttribute("sum", user.getCart().getCartItems().size() + 1);
        return "redirect:/";
    }

    @GetMapping("/delete-item-in-cart/{id}")
    public String removeProductItemInCart(@PathVariable("id") String id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        cartService.removeCartItem(id);

        User user = userService.findUserByEmail(email);
        session.setAttribute("sum", user.getCart().getCartItems().size() - 1);
        return "redirect:/cart";


    }


    @GetMapping("/updateUp/{id}")
    public String updateUp(@PathVariable("id") String id){
        Optional<CartItem> item = cartItemRepository.findById(id);
        if(item.isPresent()){
            item.get().setQuantity(item.get().getQuantity() + 1);
            cartItemRepository.save(item.get());
        }

        return "redirect:/cart";

    }

    @GetMapping("updateDown/{id}")
    public String updateDown(@PathVariable("id") String id){
        Optional<CartItem> item = cartItemRepository.findById(id);
        if(item.isPresent()){
            if(item.get().getQuantity() == 1){
                cartItemRepository.delete(item.get());
            }else{
                item.get().setQuantity(item.get().getQuantity() - 1);
                cartItemRepository.save(item.get());
            }
        }
        return "redirect:/cart";
    }







}
