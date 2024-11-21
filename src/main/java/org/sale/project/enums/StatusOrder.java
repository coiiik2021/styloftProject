package org.sale.project.enums;


import lombok.Getter;

@Getter
public enum StatusOrder {
    PROCESSING,
    PAYMENT_FAILED,
    SHIPPING,
    COMPLETED,
    RETURNED,
    CANCEL
}
