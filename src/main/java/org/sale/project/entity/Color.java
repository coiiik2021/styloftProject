package org.sale.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Size(min = 1, message = "Tên của màu sắc không được để trống")
    String name;

    @Size(min = 1, message = "Tên của mô tả không được để trống")
    String description;

    @OneToMany(mappedBy = "color")
    List<ProductVariant> productVariants;


}
