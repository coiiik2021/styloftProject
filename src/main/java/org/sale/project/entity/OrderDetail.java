    package org.sale.project.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    @Entity
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class OrderDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @ManyToOne
        @JoinColumn(name = "product_variant_id")
        ProductVariant productVariant;

        @ManyToOne
        @JoinColumn(name = "order_Id")
        Order order;
        int quantity;
        double price;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name="review_id")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        Review review;



    }
