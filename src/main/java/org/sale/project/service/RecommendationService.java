package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.User;
import org.sale.project.entity.UserAction;
import org.sale.project.repository.ProductRepository;
import org.sale.project.repository.UserActionRepository;
import org.sale.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
public class RecommendationService {

     UserActionRepository userActionRepository;
     UserRepository userRepository;


    // Tính độ tương đồng cosine giữa hai người dùng
    private double cosineSimilarity(Map<String, Double> actionsA, Map<String, Double> actionsB) {
        double dotProduct = 0.0, normA = 0.0, normB = 0.0;

        for (String productId : actionsA.keySet()) {
            if (actionsB.containsKey(productId)) {
                dotProduct += actionsA.get(productId) * actionsB.get(productId);
            }
            normA += Math.pow(actionsA.get(productId), 2);
        }

        for (double score : actionsB.values()) {
            normB += Math.pow(score, 2);
        }

        return (normA != 0 && normB != 0) ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }
    private Map<String, Double> getUserActionScores(User user) {
        List<UserAction> userActions = userActionRepository.findByUser(user);
        return userActions.stream().collect(
                Collectors.toMap(
                        action -> action.getProduct().getId(), // key mapper
                        UserAction::getWeight, // value mapper
                        (existingWeight, newWeight) -> existingWeight // hoặc newWeight tùy theo nhu cầu của bạn
                )
        );
    }

    // Gợi ý sản phẩm cho người dùng
    public Map<String, Double> recommendProduct(User user) {
        Map<String, Double> scores = new HashMap<>();

        // Lấy danh sách hành động của người dùng hiện tại
        Map<String, Double> currentUserActions = getUserActionScores(user);

        // Tính độ tương đồng với những người dùng khác
        for (User otherUser : userRepository.findAll()) {
            if (!otherUser.getId().equals(user.getId())) {
                Map<String, Double> otherUserActions = getUserActionScores(otherUser);
                double similarity = cosineSimilarity(currentUserActions, otherUserActions);

                if (similarity > 0) {
                    for (Map.Entry<String, Double> entry : otherUserActions.entrySet()) {
                        String productId = entry.getKey();
                        double score = entry.getValue();

                        // Chỉ gợi ý những sản phẩm mà người dùng hiện tại chưa có hành động
                        if (!currentUserActions.containsKey(productId)) {
                            scores.put(productId, scores.getOrDefault(productId, 0.0) + similarity * score);
                        }
                    }
                }
            }
        }
        return scores;
    }

    // Lấy top N gợi ý
    public List<String> getTopNRecommendations(User user, int n) {
        Map<String, Double> recommendations = recommendProduct(user);

        return recommendations.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

