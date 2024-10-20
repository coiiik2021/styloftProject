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
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @jakarta.validation.constraints.Size(min = 1, message = "Tên kích thước không được để trống")
    String name;
    String description;

    @OneToMany(mappedBy = "size")
    List<ProductItem> productItems;


}
