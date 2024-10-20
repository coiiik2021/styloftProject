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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;



    @Size(min = 1, message = "Tên của danh mục không được bỏ trống")
    String name;
    String description;

    @OneToMany(mappedBy = "category")
    List<Product> products;
}
