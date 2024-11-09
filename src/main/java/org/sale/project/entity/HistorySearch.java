package org.sale.project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistorySearch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;
    LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
