package com.access_security.model.item;

import com.access_security.model.producer.Producer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class Item {
    private Long id;
    private String title;
    private BigDecimal cost;
    private Type type;
    private Producer producer;
    private List<Size> sizes;
}
