package org.sale.project.controller.admin;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.FeedBackReview;
import org.sale.project.entity.Review;
import org.sale.project.service.FeedBackReviewService;
import org.sale.project.service.OrderService;
import org.sale.project.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/feedBack")
public class FeedBackReviewController {


    FeedBackReviewService feedBackReviewService;

    ReviewService reviewService;



    @PostMapping("/create")
    public String creteFeedBackReview(@ModelAttribute("newFeedBackReview") FeedBackReview newFeedBackReview,
                                      @RequestParam("idReview") String idReview,
                                      @RequestParam("idProduct") String idProduct
                                      ) {

        Review review = reviewService.findById(idReview);

        newFeedBackReview.setReview(review);
        newFeedBackReview.setDate(LocalDate.now());

        newFeedBackReview = feedBackReviewService.saveFeedBackReview(newFeedBackReview);

        review.setFeedBackReview(newFeedBackReview);

        reviewService.createReview(review);



        return "redirect:/product/detail/" + idProduct ;
    }

    @GetMapping
    public String getPage(){
        return "redirect:/";
    }



}
