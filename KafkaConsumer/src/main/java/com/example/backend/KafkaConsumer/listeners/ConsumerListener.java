package com.example.backend.KafkaConsumer.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {

    // poner el topic que va a consumir, el groupId y el containerFactory que se va a usar en este caso el que hemos definido en config
    @KafkaListener(topicPartitions = @TopicPartition(topic = "topicmio1", partitions = {"0"}), groupId = "group-1", containerFactory = "kafkaListenerContainerFactory")
    public void listener1(String message) {
        System.out.println("Received message 1: " + message);
    }

    // poner el topic que va a consumir, el groupId y el containerFactory que se va a usar en este caso el que hemos definido en config
    @KafkaListener(topicPartitions =@TopicPartition(topic = "topicmio1", partitions = {"1"}), groupId = "group-1", containerFactory = "kafkaListenerContainerFactory")
    public void listener1b(String message) {
        System.out.println("Received message 1b: " + message);
    }

    @KafkaListener(topics = "topicmio2", groupId = "group-2", containerFactory = "kafkaListenerContainerFactory")
    public void listener2(String message) {
        System.out.println("Received message 2: " + message);
    }

    @KafkaListener(topics = "topicmio3", groupId = "group-3", containerFactory = "kafkaListenerContainerFactory")
    public void listener3(String message) {
        System.out.println("Received message 3: " + message);
    }
}
