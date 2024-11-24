package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.*;
import org.sale.project.enums.ActionType;
import org.sale.project.enums.StatusOrder;
import org.sale.project.mapper.OrderMapper;
import org.sale.project.repository.*;
import org.sale.project.service.spec.OrderSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    OrderMapper orderMapper;
    CartItemRepository cartItemRepository;

    ProductVariantRepository productVariantRepository;

    UserActionService userActionService;

    private final VoucherRepository voucherRepository;

    public void updateCancelPayment(String id){
        Order order = findById(id);
        if(order != null){
            order.setStatus(StatusOrder.PAYMENT_FAILED);
            orderRepository.save(order);
        }
    }


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(String id) {

        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


    public void cancelOrder(String id){
        Order order = findById(id);
        if(order != null){
            order.setStatus(StatusOrder.CANCEL);
            order.setAnnounceOrder(true);
            orderRepository.save(order);

            for(OrderDetail detail : order.getDetails()){
                Optional<ProductVariant> itemOptional = productVariantRepository.findById(detail.getProductVariant().getId());
                if(itemOptional.isPresent()){
                    ProductVariant item = itemOptional.get();
                    item.setQuantity(item.getQuantity() + detail.getQuantity());
                    productVariantRepository.save(item);
                }

            }
        }
    }



    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public Page<Order> findAll(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return orderRepository.findAll(pageable);
    }



    public double totalRevenue(){
        List<Order> orders = orderRepository.findAll();
        double total = 0;
        for (Order order : orders) {
            if(order.getStatus().equals(StatusOrder.COMPLETED)){
                total += order.getTotal() - 30000;
            }

        }
        return total;
    }
    public List<Double> totalRevenueInMonth() {
        // Tạo danh sách 12 tháng với giá trị ban đầu là 0.0
        List<Double> revenues = Arrays.asList(new Double[12]);
        Collections.fill(revenues, 0.0);

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (order.getStatus().equals(StatusOrder.COMPLETED)) {
                // Sử dụng LocalDate để lấy tháng (tháng trong LocalDate bắt đầu từ 1 nên cần trừ đi 1)
                int month = order.getDate().getMonthValue() - 1; // Lấy giá trị tháng từ LocalDate
                double currentRevenue = revenues.get(month) - 30000;
                revenues.set(month, currentRevenue + order.getTotal());
            }
        }

        return revenues;
    }



    public Order complete(User user, double totalPrice, Voucher voucher){



        Order order = new Order();
        order.setStatus(StatusOrder.PROCESSING);
        order.setUser(user);
        order.setDate(LocalDate.now());
        order.setTotal(totalPrice);
        orderRepository.save(order);

        List<OrderDetail> detail = new ArrayList<>();
        System.out.println(user.getCart().getCartItems().size());
        for(CartItem item : user.getCart().getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderMapper.updateOrder(orderDetail, item);
            orderDetail.setOrder(order);
            orderDetail.setPrice(item.getQuantity() * item.getProductVariant().getPrice());

            orderDetail = orderDetailRepository.save(orderDetail);

            item.getProductVariant().setQuantity(Math.max(item.getProductVariant().getQuantity() - item.getQuantity(), 0));

            productVariantRepository.save(item.getProductVariant());
            cartItemRepository.delete(item);
            detail.add(orderDetail);

            // action
            userActionService.save(
                    UserAction.builder()
                            .user(user)
                            .actionType(ActionType.PURCHASE)
                            .product(item.getProductVariant().getProduct())
                            .build()
            );

        }
        System.out.println(">>>size detail"+detail.size());

        order.setDetails(detail);
        System.out.println(order.getDetails().size());


        order.setAnnounceOrder(true);

        order = orderRepository.save(order);

        if(voucher.getCode().charAt(0) == 'P'){
            voucher.setActive(false);
            voucherRepository.save(voucher);
        }

        return order;

    }


    public int countOrder(){
        return orderRepository.findAll().size();
    }


    public Page<Order> findAllByStartId(String id, Pageable pageable) {
        return orderRepository.findAll(OrderSpec.findOrderById(id), pageable);
    }


    public int totalAnnounce(){
        List<Order> orders = orderRepository.findAll();
        int total = 0;
        for (Order order : orders) {
            if(order.isAnnounceOrder())
                ++total;
        }
        return total;
    }

}
