package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.FeedBackReview;
import org.sale.project.entity.Review;
import org.sale.project.repository.FeedBackReviewRepository;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class FeedBackReviewService {

    FeedBackReviewRepository feedBackReviewRepository;

    public FeedBackReview saveFeedBackReview(FeedBackReview feedBackReview) {

        return feedBackReviewRepository.save(feedBackReview);
    }


    public FeedBackReview findByReview(Review review) {

        return feedBackReviewRepository.findByReview(review);

    }
}
