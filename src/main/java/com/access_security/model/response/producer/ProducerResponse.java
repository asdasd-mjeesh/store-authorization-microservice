package com.access_security.model.response.producer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProducerResponse {
    private Long id;
    private String name;
    private List<EmailResponse> emails;
    private List<ContactResponse> contacts;
}
