package org.sale.project.repository;

import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    boolean existsByOrderDetail(OrderDetail orderDetail);



}
