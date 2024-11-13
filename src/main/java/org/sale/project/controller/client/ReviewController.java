package org.sale.project.controller.client;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    @GetMapping
    public String getPageReview(){
        return "/client/review/show";
    }
}
