package com.access_security.entity.producer;

import com.access_security.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Producer extends BaseEntity {
    private String name;
    private List<Contact> contacts;
    private List<Email> emails;
}
