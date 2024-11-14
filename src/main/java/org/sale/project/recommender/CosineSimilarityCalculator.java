package org.sale.project.recommender;

import org.sale.project.entity.Product;

import java.util.*;

public class CosineSimilarityCalculator {

    public static double calculateCosineSimilarity(Product p1, Product p2) {
        List<String> tokens1 = p1.getTokens();
        List<String> tokens2 = p2.getTokens();

        // Lấy tập hợp tất cả các từ (tokens) duy nhất trong cả hai sản phẩm
        Set<String> uniqueTokens = new HashSet<>(tokens1);
        uniqueTokens.addAll(tokens2);

        // Đếm tần suất của mỗi từ trong cả hai sản phẩm
        Map<String, Integer> frequency1 = getTokenFrequency(tokens1, uniqueTokens);
        Map<String, Integer> frequency2 = getTokenFrequency(tokens2, uniqueTokens);

        // Tính toán dot product
        double dotProduct = 0.0;
        for (String token : uniqueTokens) {
            dotProduct += frequency1.get(token) * frequency2.get(token);
        }

        // Tính toán norm (độ dài vector) của mỗi sản phẩm
        double norm1 = calculateNorm(frequency1);
        // Trả về cosine similarity
        double norm2 = calculateNorm(frequency2);

        return (norm1 != 0 && norm2 != 0) ? dotProduct / (norm1 * norm2) : 0.0;
    }

    private static Map<String, Integer> getTokenFrequency(List<String> tokens, Set<String> uniqueTokens) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String token : uniqueTokens) {
            frequencyMap.put(token, 0);
        }
        for (String token : tokens) {
            frequencyMap.put(token, frequencyMap.get(token) + 1);
        }
        return frequencyMap;
    }

    private static double calculateNorm(Map<String, Integer> frequencyMap) {
        double sum = 0.0;
        for (int freq : frequencyMap.values()) {
            sum += freq * freq;
        }
        return Math.sqrt(sum);
    }
}
