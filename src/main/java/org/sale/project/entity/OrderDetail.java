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
        @JoinColumn(name = "productItem_Id")
        ProductItem productItem;

        @ManyToOne
        @JoinColumn(name = "order_Id")
        Order order;
        int quantity;
        double price;


    }
