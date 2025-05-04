package com.ags.ecommerce.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {

    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public enum OrderStatus {
        CREATED, PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

}
