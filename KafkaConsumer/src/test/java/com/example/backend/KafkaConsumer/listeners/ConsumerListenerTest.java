package com.example.backend.KafkaConsumer.listeners;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {
    "listeners=PLAINTEXT://localhost:9091",
    "port=9091"
})
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=localhost:9091",
    "spring.kafka.consumer.bootstrap-servers=localhost:9091"
})
class ConsumerListenerTest {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_1 = "topicmio1";
    private static final String TOPIC_2 = "topicmio2";
    private static final String TOPIC_3 = "topicmio3";
    private static final int TIMEOUT_SECONDS = 10;

    @Test
    void listener1ReceivesMessageFromTopic1() {
        String message = "Test message for topic 1";
        kafkaTemplate.send(TOPIC_1, message);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ReceivesMessageFromTopic2() {
        String message = "Test message for topic 2";
        kafkaTemplate.send(TOPIC_2, message);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener3ReceivesMessageFromTopic3() {
        String message = "Test message for topic 3";
        kafkaTemplate.send(TOPIC_3, message);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesEmptyMessage() {
        String emptyMessage = "";
        kafkaTemplate.send(TOPIC_1, emptyMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMessageWithNullKey() {
        kafkaTemplate.send(TOPIC_1, null, "Message with null key");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesLargeMessage() {
        String largeMessage = "x".repeat(10000);
        kafkaTemplate.send(TOPIC_1, largeMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMessageWithSpecialCharacters() {
        String specialMessage = "!@#$%^&*()_+-=[]{}|;:',.<>?/~`";
        kafkaTemplate.send(TOPIC_1, specialMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesUnicodeMessage() {
        String unicodeMessage = "你好世界 🌍 Привет мир";
        kafkaTemplate.send(TOPIC_1, unicodeMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void multipleListenersProcessMessagesSimultaneously() {
        kafkaTemplate.send(TOPIC_1, "Message for listener 1");
        kafkaTemplate.send(TOPIC_2, "Message for listener 2");
        kafkaTemplate.send(TOPIC_3, "Message for listener 3");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMultipleConsecutiveMessages() {
        for (int i = 0; i < 5; i++) {
            kafkaTemplate.send(TOPIC_1, "Message " + i);
        }
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ProcessesMessagesWithPartitionKey() {
        kafkaTemplate.send(TOPIC_2, "key1", "Value with key");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener3ProcessesMessageWithDifferentKeys() {
        kafkaTemplate.send(TOPIC_3, "key1", "Message with key1");
        kafkaTemplate.send(TOPIC_3, "key2", "Message with key2");
        kafkaTemplate.send(TOPIC_3, "key1", "Another message with key1");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesWhitespaceOnlyMessage() {
        String whitespaceMessage = "   \t\n  ";
        kafkaTemplate.send(TOPIC_1, whitespaceMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ProcessesJsonFormattedMessage() {
        String jsonMessage = "{\"key\": \"value\", \"number\": 123}";
        kafkaTemplate.send(TOPIC_2, jsonMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener3ProcessesXmlFormattedMessage() {
        String xmlMessage = "<?xml version=\"1.0\"?><root><element>value</element></root>";
        kafkaTemplate.send(TOPIC_3, xmlMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesNumericMessage() {
        String numericMessage = "12345678";
        kafkaTemplate.send(TOPIC_1, numericMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMessageWithLineBreaks() {
        String messageWithLineBreaks = "Line 1\nLine 2\nLine 3";
        kafkaTemplate.send(TOPIC_1, messageWithLineBreaks);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ProcessesCsvFormattedMessage() {
        String csvMessage = "name,age,city\nJohn,30,NYC\nJane,25,LA";
        kafkaTemplate.send(TOPIC_2, csvMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener3ProcessesMessageWithLeadingSpaces() {
        String messageWithLeadingSpaces = "    Important message";
        kafkaTemplate.send(TOPIC_3, messageWithLeadingSpaces);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMessageWithTrailingSpaces() {
        String messageWithTrailingSpaces = "Important message    ";
        kafkaTemplate.send(TOPIC_1, messageWithTrailingSpaces);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ProcessesRepeatedMessage() {
        String repeatedMessage = "Hello";
        kafkaTemplate.send(TOPIC_2, repeatedMessage);
        kafkaTemplate.send(TOPIC_2, repeatedMessage);
        kafkaTemplate.send(TOPIC_2, repeatedMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener3ProcessesMessageWithTabCharacters() {
        String messageWithTabs = "Column1\tColumn2\tColumn3";
        kafkaTemplate.send(TOPIC_3, messageWithTabs);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesSingleCharacterMessage() {
        String singleCharMessage = "A";
        kafkaTemplate.send(TOPIC_1, singleCharMessage);
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void multipleListenersProcessMessagesInOrder() {
        for (int i = 0; i < 3; i++) {
            kafkaTemplate.send(TOPIC_1, "Message T1 " + i);
            kafkaTemplate.send(TOPIC_2, "Message T2 " + i);
            kafkaTemplate.send(TOPIC_3, "Message T3 " + i);
        }
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener1ProcessesMessageWithEmptyKey() {
        kafkaTemplate.send(TOPIC_1, "", "Message with empty key");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }

    @Test
    void listener2ProcessesMessageWithLongKey() {
        String longKey = "k".repeat(1000);
        kafkaTemplate.send(TOPIC_2, longKey, "Message with long key");
        
        await()
            .atMost(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .until(() -> true);
    }
}

