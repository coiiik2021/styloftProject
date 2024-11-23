package org.sale.project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.sale.project.enums.StatusOrder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;


    @ManyToOne
    @JoinColumn(name ="user_id")
    User user;

    double total;
    LocalDate date;

    String note;

    @Enumerated(EnumType.STRING)
    StatusOrder status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDetail> details;


    @ManyToOne
    @JoinColumn(name = "voucher_id")
    Voucher voucher;

    boolean announceOrder;

}
