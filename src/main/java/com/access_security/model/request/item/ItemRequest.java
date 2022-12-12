package com.access_security.model.request.item;

import com.access_security.model.common.ItemType;
import com.access_security.model.common.SizeEnum;
import com.access_security.model.request.producer.ProducerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private Long id;
    private String title;
    private ItemType type;
    private BigDecimal cost;
    private ProducerRequest producer;
    private List<SizeEnum> sizes;
}
