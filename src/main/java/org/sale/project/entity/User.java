package org.sale.project.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String Id;

    String email;
    String password;
    String name;

    String phoneNumber;
    String address;

    int sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthDay;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToOne
    Cart cart;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

}
