package com.example.backend.KafkaProducer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StringProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private StringProducerService stringProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stringProducerService = new StringProducerService();

        try {
            java.lang.reflect.Field field = StringProducerService.class.getDeclaredField("kafkaTemplate");
            field.setAccessible(true);
            field.set(stringProducerService, kafkaTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendMessageSendsMessageToKafkaTemplate() {
        String topic = "test-topic";
        String message = "Test message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        stringProducerService.sendMessage(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, message);
    }

    @Test
    void sendMessageWithDifferentTopics() {
        String message = "Test message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(anyString(), eq(message))).thenReturn(future);

        stringProducerService.sendMessage("topic1", message);
        stringProducerService.sendMessage("topic2", message);
        stringProducerService.sendMessage("topic3", message);

        verify(kafkaTemplate, times(3)).send(anyString(), eq(message));
    }

    @Test
    void sendMessageWithEmptyMessage() {
        String topic = "test-topic";
        String emptyMessage = "";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, emptyMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, emptyMessage);

        verify(kafkaTemplate, times(1)).send(topic, emptyMessage);
    }

    @Test
    void sendMessageWithNullMessage() {
        String topic = "test-topic";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, null)).thenReturn(future);

        stringProducerService.sendMessage(topic, null);

        verify(kafkaTemplate, times(1)).send(topic, null);
    }

    @Test
    void sendMessageWithLargeMessage() {
        String topic = "test-topic";
        String largeMessage = "x".repeat(10000);

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, largeMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, largeMessage);

        verify(kafkaTemplate, times(1)).send(topic, largeMessage);
    }

    @Test
    void sendMessageWithSpecialCharacters() {
        String topic = "test-topic";
        String specialMessage = "!@#$%^&*()_+-=[]{}|;:',.<>?/";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, specialMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, specialMessage);

        verify(kafkaTemplate, times(1)).send(topic, specialMessage);
    }

    @Test
    void sendMessageWithUnicodeCharacters() {
        String topic = "test-topic";
        String unicodeMessage = "你好世界 🌍 Привет мир";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, unicodeMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, unicodeMessage);

        verify(kafkaTemplate, times(1)).send(topic, unicodeMessage);
    }

    @Test
    void sendMessageMultipleTimes() {
        String topic = "test-topic";
        String message = "Test message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        for (int i = 0; i < 5; i++) {
            stringProducerService.sendMessage(topic, message);
        }

        verify(kafkaTemplate, times(5)).send(topic, message);
    }

    @Test
    void sendMessageWithDifferentMessages() {
        String topic = "test-topic";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        stringProducerService.sendMessage(topic, "Message 1");
        stringProducerService.sendMessage(topic, "Message 2");
        stringProducerService.sendMessage(topic, "Message 3");

        verify(kafkaTemplate, times(3)).send(eq(topic), anyString());
    }

    @Test
    void sendMessageWithJsonContent() {
        String topic = "test-topic";
        String jsonMessage = "{\"key\": \"value\", \"number\": 123}";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, jsonMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, jsonMessage);

        verify(kafkaTemplate, times(1)).send(topic, jsonMessage);
    }

    @Test
    void sendMessageWithXmlContent() {
        String topic = "test-topic";
        String xmlMessage = "<?xml version=\"1.0\"?><root><element>value</element></root>";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, xmlMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, xmlMessage);

        verify(kafkaTemplate, times(1)).send(topic, xmlMessage);
    }

    @Test
    void sendMessageWithWhitespaceOnlyMessage() {
        String topic = "test-topic";
        String whitespaceMessage = "   \t\n  ";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, whitespaceMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, whitespaceMessage);

        verify(kafkaTemplate, times(1)).send(topic, whitespaceMessage);
    }

    @Test
    void sendMessageWithLineBreaks() {
        String topic = "test-topic";
        String messageWithLineBreaks = "Line 1\nLine 2\nLine 3";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, messageWithLineBreaks)).thenReturn(future);

        stringProducerService.sendMessage(topic, messageWithLineBreaks);

        verify(kafkaTemplate, times(1)).send(topic, messageWithLineBreaks);
    }

    @Test
    void sendMessagePreservesMessageContent() {
        String topic = "test-topic";
        String message = "Original message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        stringProducerService.sendMessage(topic, message);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(eq(topic), messageCaptor.capture());

        assertThat(messageCaptor.getValue()).isEqualTo(message);
    }

    @Test
    void sendMessagePreservesTopic() {
        String topic = "specific-topic";
        String message = "Test message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        stringProducerService.sendMessage(topic, message);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), anyString());

        assertThat(topicCaptor.getValue()).isEqualTo(topic);
    }

    @Test
    void sendMessageDoesNotThrowExceptionWithValidInput() {
        String topic = "test-topic";
        String message = "Test message";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        assertThatNoException().isThrownBy(() ->
                stringProducerService.sendMessage(topic, message)
        );
    }

    @Test
    void sendMessageWithNumericContent() {
        String topic = "test-topic";
        String numericMessage = "12345678";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, numericMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, numericMessage);

        verify(kafkaTemplate, times(1)).send(topic, numericMessage);
    }

    @Test
    void sendMessageWithCsvContent() {
        String topic = "test-topic";
        String csvMessage = "name,age,city\nJohn,30,NYC\nJane,25,LA";

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(topic, csvMessage)).thenReturn(future);

        stringProducerService.sendMessage(topic, csvMessage);

        verify(kafkaTemplate, times(1)).send(topic, csvMessage);
    }
}

