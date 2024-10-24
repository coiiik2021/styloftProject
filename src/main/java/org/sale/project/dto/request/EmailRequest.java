package org.sale.project.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    Sender sender;
    List<Recipient> to;

    String subject;
    String htmlContent;

}
