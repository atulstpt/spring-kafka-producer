package com.ags.ecommerce.service;

import com.ags.ecommerce.config.KafkaConfig;
import com.ags.ecommerce.model.Order;
import com.ags.ecommerce.model.OrderItem;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Service
public class OrderService {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "orders";

    public void processOrder() throws Exception {
        Properties props = KafkaConfig.getProducerProperties();

        try (OrderProducer orderProducer = new OrderProducer(
                new KafkaProducer<>(props), TOPIC)) {

            Order order = createSampleOrder();

            try {
                orderProducer.sendOrderSync(order);
            } catch (Exception e) {
                System.err.println("Failed to send order: " + e.getMessage());
            }
        }
    }

    private Order createSampleOrder() {
        OrderItem item = OrderItem.builder()
                .productId("PROD-001")
                .quantity(2)
                .unitPrice(new BigDecimal("99.99"))
                .subtotal(new BigDecimal("199.98"))
                .build();

        return Order.builder()
                .orderId(UUID.randomUUID().toString())
                .customerId("CUST-001")
                .items(Collections.singletonList(item))
                .totalAmount(new BigDecimal("199.98"))
                .status(Order.OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
