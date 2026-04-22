package com.example.backend.KafkaProducer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message).whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Error sending message: " + ex.getMessage());
            } else {
                System.out.println("Message sent successfully to topic: " + topic + " " + result.getProducerRecord().value());
                System.out.println("Partition: " + result.getRecordMetadata().partition() + ", Offset: " + result.getRecordMetadata().offset());
            }
        });
    }
}
