package org.sale.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotNull
    @Size(min = 1, message = "Tên của sản phẩm không được để trống")
    String name;

    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product")
    List<ProductVariant> productVariant;

    boolean status;

    @OneToMany(mappedBy = "product")
    List<UserAction> userActions;

//    String image;
//    List<String> images;

    public List<String> getTokens() {
        return Arrays.asList((category.getName() + " " + name + " " + description).toLowerCase().split(" "));
    }

}
