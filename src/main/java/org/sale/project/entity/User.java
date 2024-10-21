package org.sale.project.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

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

    @Email(message = "Email is in valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;

    @Size(min = 5, message = "password phải dài hơn 5 kí tự")
    String password;


    @Size(min = 1, message = "Tên không được để trống")
    String name;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải gồm 10 chữ số")
    String phoneNumber;

    @Size(min=1, message = "Địa chỉ không được để trống")
    String address;

    int sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    Date birthDay;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToOne
    Cart cart;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

}
