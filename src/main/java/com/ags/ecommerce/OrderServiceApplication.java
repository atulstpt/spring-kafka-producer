package com.ags.ecommerce;

import com.ags.ecommerce.config.KafkaConfig;
import com.ags.ecommerce.model.Order;
import com.ags.ecommerce.model.OrderItem;
import com.ags.ecommerce.service.OrderProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
public class OrderServiceApplication {

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final String TOPIC = "orders";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
