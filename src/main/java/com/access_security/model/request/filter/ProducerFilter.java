package com.access_security.model.request.filter;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerFilter extends BaseFilter {
    private String name;
}
