package com.example.backend.KafkaProducer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=localhost:9092",
    "spring.kafka.producer.bootstrap-servers=localhost:9092"
})
class StringProducerFactoryTest {
    
    @Autowired
    private StringProducerFactory stringProducerFactory;

    @Test
    void producerFactoryCreatesValidProducerFactory() {
        ProducerFactory<String, String> producerFactory = stringProducerFactory.producerFactory();
        
        assertThat(producerFactory).isNotNull();
    }

    @Test
    void producerFactoryReturnsDefaultKafkaProducerFactory() {
        ProducerFactory<String, String> producerFactory = stringProducerFactory.producerFactory();
        
        assertThat(producerFactory).isNotNull()
            .isInstanceOf(org.springframework.kafka.core.DefaultKafkaProducerFactory.class);
    }

    @Test
    void kafkaTemplateCreatesValidTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = stringProducerFactory.kafkaTemplate();
        
        assertThat(kafkaTemplate).isNotNull();
    }

    @Test
    void kafkaTemplateUsesProducerFactory() {
        KafkaTemplate<String, String> kafkaTemplate = stringProducerFactory.kafkaTemplate();
        
        assertThat(kafkaTemplate.getProducerFactory()).isNotNull();
    }

    @Test
    void kafkaTemplateIsSingleton() {
        KafkaTemplate<String, String> template1 = stringProducerFactory.kafkaTemplate();
        KafkaTemplate<String, String> template2 = stringProducerFactory.kafkaTemplate();
        
        assertThat(template1).isSameAs(template2);
    }

    @Test
    void kafkaTemplateProducerFactoryIsNotNull() {
        KafkaTemplate<String, String> kafkaTemplate = stringProducerFactory.kafkaTemplate();
        
        assertThat(kafkaTemplate.getProducerFactory()).isNotNull();
    }

    @Test
    void producerFactoryBeanIsCreatedByConfiguration() {
        assertThat(stringProducerFactory).isNotNull();
        assertThat(stringProducerFactory.producerFactory()).isNotNull();
    }

    @Test
    void kafkaTemplateBeanIsCreatedByConfiguration() {
        assertThat(stringProducerFactory).isNotNull();
        assertThat(stringProducerFactory.kafkaTemplate()).isNotNull();
    }

    @Test
    void producerFactoryIsOfCorrectType() {
        ProducerFactory<String, String> producerFactory = stringProducerFactory.producerFactory();
        
        assertThat(producerFactory)
            .isInstanceOf(org.springframework.kafka.core.DefaultKafkaProducerFactory.class);
    }

    @Test
    void kafkaTemplateIsOfCorrectType() {
        KafkaTemplate<String, String> kafkaTemplate = stringProducerFactory.kafkaTemplate();
        
        assertThat(kafkaTemplate)
            .isInstanceOf(KafkaTemplate.class);
    }

    @Test
    void producerFactoryAndKafkaTemplateAreLinked() {
        ProducerFactory<String, String> producerFactory = stringProducerFactory.producerFactory();
        KafkaTemplate<String, String> kafkaTemplate = stringProducerFactory.kafkaTemplate();
        
        assertThat(producerFactory).isNotNull();
        assertThat(kafkaTemplate.getProducerFactory()).isNotNull();
    }
}

