package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.Order;
import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.User;
import org.sale.project.mapper.OrderMapper;
import org.sale.project.repository.CartItemRepository;
import org.sale.project.repository.OrderDetailRepository;
import org.sale.project.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    OrderMapper orderMapper;
    CartItemRepository cartItemRepository;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }



    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public double totalRevenue(){
        List<Order> orders = orderRepository.findAll();
        double total = 0;
        for (Order order : orders) {
            if(order.getStatus().equals("COMPLITE")){
                total += order.getTotal();
            }

        }
        return total;
    }
    public List<Double> totalRevenueInMonth() {
        List<Double> revenues = Arrays.asList(new Double[12]);
        Collections.fill(revenues, 0.0);

        Calendar calendar = Calendar.getInstance();

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (order.getStatus().equals("COMPLITE")) {
                calendar.setTime(order.getDate());
                int month = calendar.get(Calendar.MONTH);
                double currentRevenue = revenues.get(month);
                revenues.set(month, currentRevenue + order.getTotal());
            }
        }

        return revenues;
    }


    public void complete(User user, double totalPrice){

        Order order = new Order();
        order.setStatus("PAID");
        order.setUser(user);
        order.setDate(new Date());
        order.setTotal(totalPrice);
        orderRepository.save(order);

        List<OrderDetail> detail = new ArrayList<>();
        for(CartItem item : user.getCart().getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderMapper.updateOrder(orderDetail, item);
            orderDetail.setOrder(order);
            orderDetail.setPrice(item.getQuantity() * item.getProductItem().getPrice());



            orderDetailRepository.save(orderDetail);
            cartItemRepository.delete(item);



            detail.add(orderDetail);

        }

        order.setDetails(detail);

        orderRepository.save(order);


    }


    public int countOrder(){
        return orderRepository.findAll().size();
    }

}
