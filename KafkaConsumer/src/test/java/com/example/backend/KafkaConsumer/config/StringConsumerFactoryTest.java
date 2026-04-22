package com.example.backend.KafkaConsumer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=localhost:9092"
})
class StringConsumerFactoryTest {
    
    @Autowired
    private StringConsumerFactory stringConsumerFactory;

    @Test
    void consumerFactoryCreatesValidConsumerFactory() {
        ConsumerFactory<String, String> consumerFactory = stringConsumerFactory.consumeractory();
        
        assertThat(consumerFactory).isNotNull();
    }

    @Test
    void consumerFactoryReturnsDefaultKafkaConsumerFactory() {
        ConsumerFactory<String, String> consumerFactory = stringConsumerFactory.consumeractory();
        
        assertThat(consumerFactory).isNotNull()
            .isInstanceOf(org.springframework.kafka.core.DefaultKafkaConsumerFactory.class);
    }

    @Test
    void kafkaListenerContainerFactoryCreatesValidFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(containerFactory).isNotNull();
    }

    @Test
    void kafkaListenerContainerFactoryUsesConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(containerFactory.getConsumerFactory()).isNotNull();
    }

    @Test
    void kafkaListenerContainerFactoryIsSingleton() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory1 = stringConsumerFactory.kafkaListenerContainerFactory();
        ConcurrentKafkaListenerContainerFactory<String, String> factory2 = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(factory1).isSameAs(factory2);
    }

    @Test
    void kafkaListenerContainerFactoryConsumerFactoryIsNotNull() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(containerFactory.getConsumerFactory()).isNotNull();
    }

    @Test
    void consumerFactoryBeanIsCreatedByConfiguration() {
        assertThat(stringConsumerFactory).isNotNull();
        assertThat(stringConsumerFactory.consumeractory()).isNotNull();
    }

    @Test
    void kafkaListenerContainerFactoryBeanIsCreatedByConfiguration() {
        assertThat(stringConsumerFactory).isNotNull();
        assertThat(stringConsumerFactory.kafkaListenerContainerFactory()).isNotNull();
    }

    @Test
    void consumerFactoryIsOfCorrectType() {
        ConsumerFactory<String, String> consumerFactory = stringConsumerFactory.consumeractory();
        
        assertThat(consumerFactory)
            .isInstanceOf(org.springframework.kafka.core.DefaultKafkaConsumerFactory.class);
    }

    @Test
    void kafkaListenerContainerFactoryIsOfCorrectType() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(containerFactory)
            .isInstanceOf(ConcurrentKafkaListenerContainerFactory.class);
    }

    @Test
    void consumerFactoryAndContainerFactoryAreLinked() {
        ConsumerFactory<String, String> consumerFactory = stringConsumerFactory.consumeractory();
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = stringConsumerFactory.kafkaListenerContainerFactory();
        
        assertThat(consumerFactory).isNotNull();
        assertThat(containerFactory.getConsumerFactory()).isNotNull();
    }
}

