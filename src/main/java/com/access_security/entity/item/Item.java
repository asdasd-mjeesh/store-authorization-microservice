package com.access_security.entity.item;

import com.access_security.entity.BaseEntity;
import com.access_security.entity.producer.Producer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Item extends BaseEntity {
    private String title;
    private BigDecimal cost;
    private Type type;
    private Producer producer;
    private List<Size> sizes;
}
