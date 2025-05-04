package com.ags.ecommerce.service;

import com.ags.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OrderProducer implements AutoCloseable
{
    private final KafkaProducer<String, Order> producer;
    private final String topic;
    private static final int TIMEOUT_SECONDS = 10;

    public OrderProducer(KafkaProducer<String, Order> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    public Future<RecordMetadata> sendOrder(Order order) {
        ProducerRecord<String, Order> record = new ProducerRecord<>(topic, order.getOrderId(), order);

        return producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Error sending order {}: {}", order.getOrderId(), exception.getMessage());
            } else {
                log.info("Order {} sent successfully to partition {} with offset {}",
                        order.getOrderId(), metadata.partition(), metadata.offset());
            }
        });
    }

    public void sendOrderSync(Order order) throws Exception {
        Future<RecordMetadata> future = sendOrder(order);
        try {
            future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error sending order {} synchronously", order.getOrderId(), e);
            throw e;
        }
    }


    @Override
    public void close() throws Exception {
        if (producer != null) {
            producer.flush();
            producer.close();
        }
    }
}
