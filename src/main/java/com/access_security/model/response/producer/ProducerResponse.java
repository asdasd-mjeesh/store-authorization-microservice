package com.access_security.model.response.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerResponse {
    private Long id;
    private String name;
    private List<EmailResponse> emails;
    private List<ContactResponse> contacts;
}
