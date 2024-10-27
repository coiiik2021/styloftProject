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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    User user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    List<CartItem> cartItems;


}
