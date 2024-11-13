package org.sale.project.repository;


import org.sale.project.entity.FeedBackReview;
import org.sale.project.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackReviewRepository extends JpaRepository<FeedBackReview, String> {
    FeedBackReview findByReview(Review review);
}


