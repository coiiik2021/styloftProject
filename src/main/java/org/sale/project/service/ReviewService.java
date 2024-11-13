package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.Review;
import org.sale.project.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public boolean existReview(OrderDetail orderDetail) {
        return reviewRepository.existsByOrderDetail(orderDetail);
    }

    public Review findById(String id){
        return reviewRepository.findById(id).orElse(null);
    }




}
