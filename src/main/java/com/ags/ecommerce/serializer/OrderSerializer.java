package com.ags.ecommerce.serializer;

import com.ags.ecommerce.model.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class OrderSerializer implements Serializer<Order> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Order order) {
        try {
            return objectMapper.writeValueAsBytes(order);
        } catch (Exception e) {
            log.error("Error serializing order: ", e);
            throw new RuntimeException("Error serializing order", e);
        }

    }
}
