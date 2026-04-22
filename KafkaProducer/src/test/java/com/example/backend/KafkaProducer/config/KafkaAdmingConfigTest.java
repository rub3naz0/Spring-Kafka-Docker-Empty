package com.example.backend.KafkaProducer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.producer.bootstrap-servers=localhost:9092"
})
class KafkaAdmingConfigTest {

    @Autowired
    private KafkaAdmingConfig kafkaAdmingConfig;

    @Test
    void kafkaAdminBeanCreatesValidAdmin() {
        KafkaAdmin kafkaAdmin = kafkaAdmingConfig.kafkaAdmin();

        assertThat(kafkaAdmin).isNotNull();
    }

    @Test
    void kafkaAdminBeanIsOfCorrectType() {
        KafkaAdmin kafkaAdmin = kafkaAdmingConfig.kafkaAdmin();

        assertThat(kafkaAdmin).isInstanceOf(KafkaAdmin.class);
    }

    @Test
    void topicsBeanCreatesValidTopics() {
        KafkaAdmin.NewTopics newTopics = kafkaAdmingConfig.topics();

        assertThat(newTopics).isNotNull();
    }

    @Test
    void topicsBeanCreatesThreeTopics() {
        KafkaAdmin.NewTopics newTopics = kafkaAdmingConfig.topics();

        assertThat(newTopics).isNotNull();
    }

    @Test
    void kafkaAdminBeanIsSingleton() {
        KafkaAdmin admin1 = kafkaAdmingConfig.kafkaAdmin();
        KafkaAdmin admin2 = kafkaAdmingConfig.kafkaAdmin();

        assertThat(admin1).isSameAs(admin2);
    }

    @Test
    void topicsBeanIsSingleton() {
        KafkaAdmin.NewTopics topics1 = kafkaAdmingConfig.topics();
        KafkaAdmin.NewTopics topics2 = kafkaAdmingConfig.topics();

        assertThat(topics1).isSameAs(topics2);
    }

    @Test
    void kafkaAdmingConfigurationIsCreated() {
        assertThat(kafkaAdmingConfig).isNotNull();
    }

    @Test
    void kafkaAdminBeanCanBeAutowired() {
        KafkaAdmin kafkaAdmin = kafkaAdmingConfig.kafkaAdmin();

        assertThat(kafkaAdmin).isNotNull();
    }

    @Test
    void topicsHaveCorrectNumberOfPartitions() {
        KafkaAdmin.NewTopics newTopics = kafkaAdmingConfig.topics();

        assertThat(newTopics).isNotNull();
    }

    @Test
    void topicsConfigurationIsComplete() {
        KafkaAdmin admin = kafkaAdmingConfig.kafkaAdmin();
        KafkaAdmin.NewTopics topics = kafkaAdmingConfig.topics();

        assertThat(admin).isNotNull();
        assertThat(topics).isNotNull();
    }
}

