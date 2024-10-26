package org.sale.project.dto.request;

import lombok.Builder;
import lombok.Data;

public abstract class PaymentDTO {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}