package com.example.backend.KafkaProducer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaAdmingConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    //    Servidor de Arranque
    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    //    Generar Topics
    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("topicmio1")
                        .partitions(2)
                        .replicas(1)
                        .build(),
                TopicBuilder.name("topicmio2")
                        .partitions(2)
                        .replicas(1)
                        .build(),
                TopicBuilder.name("topicmio3")
                        .partitions(2)
                        .replicas(1)
                        .build()
        );
    }
}
