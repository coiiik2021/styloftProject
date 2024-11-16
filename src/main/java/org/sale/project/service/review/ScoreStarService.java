package org.sale.project.service.review;

import org.sale.project.dto.request.StarReview;
import org.sale.project.entity.OrderDetail;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.springframework.stereotype.Service;

@Service
public class ScoreStarService {
    public StarReview score(Product p) {

        double scoreProduct = 0.0;
        int countReview = 0;

        for (ProductVariant item : p.getProductVariant()) {
            for (OrderDetail o : item.getOrderDetails()) {
                if(o.getReview() != null) {
                    ++countReview;

                    scoreProduct += o.getReview().getStar();
                }
            }
        }

        double averageScore = countReview > 0 ? (scoreProduct / countReview) : 0.0;
        return new StarReview(averageScore, countReview);
    }
}
