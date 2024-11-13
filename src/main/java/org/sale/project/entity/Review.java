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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    int star;
    String description;
    LocalDate dateReview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="orderDetail_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    OrderDetail orderDetail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="feedBackReview_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    FeedBackReview feedBackReview;




}
