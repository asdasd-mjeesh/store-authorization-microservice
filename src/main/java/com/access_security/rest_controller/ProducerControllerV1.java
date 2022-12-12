package com.access_security.rest_controller;

import com.access_security.model.request.filter.ProducerFilter;
import com.access_security.model.request.producer.ProducerRequest;
import com.access_security.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/producers")
public class ProducerControllerV1 {
    private final ProducerService producerService;

    public ProducerControllerV1(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createProducer(@RequestBody ProducerRequest producer) {
        var savedProducer = producerService.create(producer);
        if (savedProducer.isPresent()) {
            return ResponseEntity.ok(savedProducer.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        var producerResponse = producerService.getById(id);
        if (producerResponse.isPresent()) {
            return ResponseEntity.ok(producerResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PutMapping("/filter")
    public ResponseEntity<?> getByFilter(@RequestBody ProducerFilter filter) {
        var producersResponse = producerService.getByFilter(filter);
        return ResponseEntity.ok(producersResponse);
    }

    @PatchMapping("/")
    public ResponseEntity<?> update(@RequestBody ProducerRequest producer) {
        var updatedProducer = producerService.update(producer);
        if (updatedProducer.isPresent()) {
            return ResponseEntity.ok(updatedProducer.get());
        }
        return new ResponseEntity<>("Updating failed", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long id) {
        boolean isDeleted = producerService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted successfully");
        }
        return new ResponseEntity<>("Deleting failed", HttpStatus.CONFLICT);
    }
}
