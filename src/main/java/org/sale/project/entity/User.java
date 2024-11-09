package org.sale.project.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.*;
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

    @Size(min = 1, message = "Tên không được để trống")
    String name;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải gồm 10 chữ số")
    String phoneNumber;

    @Size(min = 1, message = "Địa chỉ không được để trống")
    String address;
    int sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    LocalDate birthDay;



    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Cart cart;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Account account;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<HistorySearch> historySearches;

}
