package org.sale.project.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    Recipient to;

    String subject;
    String htmlContent;

}
