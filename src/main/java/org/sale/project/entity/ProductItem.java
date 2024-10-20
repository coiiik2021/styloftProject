package org.sale.project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;


    double price;
    int quantity;

    boolean status;

    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;
//
    @ManyToOne
    @JoinColumn(name = "color_id")
    Color color;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @OneToMany(mappedBy = "productItem", fetch = FetchType.EAGER)
    List<CartItem> cartItem;

    @OneToMany(mappedBy = "productItem")
    List<OrderDetail> orderDetails;

    String image;

}
