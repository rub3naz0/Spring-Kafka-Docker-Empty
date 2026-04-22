package com.example.backend.KafkaProducer.controller;

import com.example.backend.KafkaProducer.services.StringProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StringProducerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private StringProducerService stringProducerService;

    @Test
    void sendMessageReturnsOkResponse() throws Exception {
        String topic = "test-topic";
        String message = "Test message";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk())
            .andExpect(content().string("Message sent to topic: " + topic));
    }

    @Test
    void sendMessageCallsServiceWithCorrectParameters() throws Exception {
        String topic = "test-topic";
        String message = "Test message";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, message);
    }

    @Test
    void sendMessageWithEmptyMessage() throws Exception {
        String topic = "test-topic";
        String emptyMessage = " ";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.TEXT_PLAIN)
                .content(emptyMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(eq(topic), anyString());
    }

    @Test
    void sendMessageWithSpecialCharacters() throws Exception {
        String topic = "test-topic";
        String specialMessage = "!@#$%^&*()_+-=[]{}|;:',.<>?/";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(specialMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, specialMessage);
    }

    @Test
    void sendMessageWithUnicodeCharacters() throws Exception {
        String topic = "test-topic";
        String unicodeMessage = "你好世界";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(unicodeMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, unicodeMessage);
    }

    @Test
    void sendMessageWithLargeMessage() throws Exception {
        String topic = "test-topic";
        String largeMessage = "x".repeat(10000);
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(largeMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, largeMessage);
    }

    @Test
    void sendMessageToDifferentTopics() throws Exception {
        String message = "Test message";
        
        mockMvc.perform(post("/api/producer/topic1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk());
        
        mockMvc.perform(post("/api/producer/topic2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk());
        
        mockMvc.perform(post("/api/producer/topic3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(3)).sendMessage(anyString(), eq(message));
    }

    @Test
    void sendMessageMultipleTimes() throws Exception {
        String topic = "test-topic";
        String message = "Test message";
        
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/api/producer/" + topic)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(message))
                .andExpect(status().isOk());
        }
        
        verify(stringProducerService, times(5)).sendMessage(topic, message);
    }

    @Test
    void isOKEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/producer"))
            .andExpect(status().isOk())
            .andExpect(content().string("IS OK"));
    }

    @Test
    void sendMessageWithJsonContent() throws Exception {
        String topic = "test-topic";
        String jsonMessage = "{\"key\": \"value\"}";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, jsonMessage);
    }

    @Test
    void sendMessageWithLineBreaks() throws Exception {
        String topic = "test-topic";
        String messageWithLineBreaks = "Line 1\nLine 2\nLine 3";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageWithLineBreaks))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, messageWithLineBreaks);
    }

    @Test
    void sendMessageResponseContainsTopic() throws Exception {
        String topic = "my-custom-topic";
        String message = "Test message";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("my-custom-topic")));
    }

    @Test
    void sendMessageWithWhitespaceOnlyMessage() throws Exception {
        String topic = "test-topic";
        String whitespaceMessage = "   \t  ";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(whitespaceMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, whitespaceMessage);
    }

    @Test
    void sendMessageWithNumericContent() throws Exception {
        String topic = "test-topic";
        String numericMessage = "12345678";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(numericMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, numericMessage);
    }

    @Test
    void sendMessageWithXmlContent() throws Exception {
        String topic = "test-topic";
        String xmlMessage = "<?xml version=\"1.0\"?><root><element>value</element></root>";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(xmlMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, xmlMessage);
    }

    @Test
    void sendMessageWithCsvContent() throws Exception {
        String topic = "test-topic";
        String csvMessage = "name,age,city\nJohn,30,NYC";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(csvMessage))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, csvMessage);
    }

    @Test
    void sendMessageWithLeadingSpaces() throws Exception {
        String topic = "test-topic";
        String messageWithLeadingSpaces = "    Important message";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageWithLeadingSpaces))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, messageWithLeadingSpaces);
    }

    @Test
    void sendMessageWithTrailingSpaces() throws Exception {
        String topic = "test-topic";
        String messageWithTrailingSpaces = "Important message    ";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageWithTrailingSpaces))
            .andExpect(status().isOk());
        
        verify(stringProducerService, times(1)).sendMessage(topic, messageWithTrailingSpaces);
    }

    @Test
    void isOKEndpointDoesNotCallService() throws Exception {
        mockMvc.perform(get("/api/producer"))
            .andExpect(status().isOk());
        
        verify(stringProducerService, never()).sendMessage(anyString(), anyString());
    }

    @Test
    void sendMessageResponseFormat() throws Exception {
        String topic = "my-topic";
        String message = "Test message";
        
        mockMvc.perform(post("/api/producer/" + topic)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
            .andExpect(content().string("Message sent to topic: my-topic"));
    }
}


