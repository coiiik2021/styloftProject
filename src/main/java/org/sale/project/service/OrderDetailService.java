package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.OrderDetail;
import org.sale.project.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class OrderDetailService {

    OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> findAllOrderDetailByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }




    public OrderDetail findById(String id) {
        return orderDetailRepository.findById(id).orElse(null);

    }
}
