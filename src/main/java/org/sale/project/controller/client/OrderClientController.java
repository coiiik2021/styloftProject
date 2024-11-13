package org.sale.project.controller.client;


import lombok.AllArgsConstructor;
import org.sale.project.entity.Order;
import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.ProductVariant;
import org.sale.project.entity.Review;
import org.sale.project.service.OrderDetailService;
import org.sale.project.service.OrderService;
import org.sale.project.service.ProductVariantService;
import org.sale.project.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderClientController {

    OrderService orderService;
    OrderDetailService orderDetailService;

    ProductVariantService productVariantService;
    ReviewService reviewService;

    @GetMapping("/{id}")
    public String getPageOrder(@PathVariable("id") String id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);

        return "/client/order/show";
    }

    @GetMapping("/detail/review/{id}")
    public String getPageReview(@PathVariable("id") String idOrderDetail, Model model) {

        model.addAttribute("detail", orderDetailService.findById(idOrderDetail));


        model.addAttribute("newReview", new Review());

        return "/client/order/review";
    }

    @PostMapping("/detail/review")
    public String createReview(@ModelAttribute("newReview") Review newReview, @RequestParam("idOrderDetail") String idDetail) {


        OrderDetail orderDetail = orderDetailService.findById(idDetail);


        newReview.setDateReview(LocalDate.now());
        newReview.setOrderDetail(orderDetail);
        newReview = reviewService.createReview(newReview);

        orderDetail.setReview(newReview);

        orderDetailService.saveOrderDetail(orderDetail);

        return "redirect:/order/" + orderDetail.getOrder().getId();
    }

}
