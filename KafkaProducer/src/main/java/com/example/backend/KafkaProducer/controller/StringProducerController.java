package com.example.backend.KafkaProducer.controller;

import com.example.backend.KafkaProducer.services.StringProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StringProducerController {
    @Autowired
    private StringProducerService stringProducerService;

    @PostMapping("/api/producer/{topic}")
    // se pasa topic por query param
    public ResponseEntity<String> sendMessage(@PathVariable String topic, @RequestBody String message) {
        stringProducerService.sendMessage(topic, message);
        return ResponseEntity.ok("Message sent to topic: " + topic);
    }

    @GetMapping("/api/producer")
    public ResponseEntity<String> isOK() {
        return ResponseEntity.ok("IS OK");
    }
}
