package org.sale.project.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String description;
    LocalDate date;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="review_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Review review;
}
