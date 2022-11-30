package com.access_security.model.producer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Producer {
    private Long id;
    private String name;
    private List<Contact> contacts;
    private List<Email> emails;
}
