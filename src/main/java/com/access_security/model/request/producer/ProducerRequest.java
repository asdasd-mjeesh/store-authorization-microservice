package com.access_security.model.request.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerRequest {
    private Long id;
    private String name;
    private List<ContactRequest> contacts;
    private List<EmailRequest> emails;
}
