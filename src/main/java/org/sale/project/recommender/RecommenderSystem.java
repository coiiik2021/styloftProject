package org.sale.project.recommender;

import org.sale.project.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class RecommenderSystem {

    public static List<Product> recommendProducts(Product targetProduct, List<Product> allProducts, int topN) {
        List<Product> recommendedProducts = new ArrayList<>();

        for (Product product : allProducts) {
            if (!product.equals(targetProduct)) {
                double similarity = CosineSimilarityCalculator.calculateCosineSimilarity(targetProduct, product);
                if (similarity > 0) {  // Thêm vào danh sách nếu có độ tương đồng dương
                    recommendedProducts.add(product);
                }
            }
        }

        // Sắp xếp danh sách gợi ý theo độ tương đồng (từ cao đến thấp)
        recommendedProducts.sort((p1, p2) ->
                Double.compare(CosineSimilarityCalculator.calculateCosineSimilarity(targetProduct, p2),
                        CosineSimilarityCalculator.calculateCosineSimilarity(targetProduct, p1))
        );

        // Lấy ra top N sản phẩm có độ tương đồng cao nhất
        return recommendedProducts.size() > topN ? recommendedProducts.subList(0, topN) : recommendedProducts;
    }
}
