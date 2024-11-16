package org.sale.project.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String code; // Mã voucher

    @Column(nullable = false)
    @NotNull(message = "Không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount value must be at least 0.")
    private Double discountValue; // Giá trị giảm giá

    @Column(nullable = false)
    @NotNull(message = "Không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // Ngày bắt đầu

    @Column(nullable = false)
    @NotNull(message = "Không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // Ngày kết thúc

    @Column(nullable = false)
    private Boolean active = true; // Trạng thái hoạt động

    @Column(nullable = false)
    @NotNull(message = "Không được để trống")
    private Integer quantity;

//    @ManyToOne
//    private Order order;
}