package com.access_security.model.response.producer;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailResponse {
    private Long id;
    private String address;
}
