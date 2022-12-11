package com.access_security.model.response.item;

import com.access_security.model.common.ItemType;
import com.access_security.model.common.SizeEnum;
import com.access_security.model.response.producer.ProducerResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class ItemResponse {
    private Long id;
    private String title;
    private ItemType type;
    private BigDecimal cost;
    private ProducerResponse producer;
    private List<SizeEnum> sizes;
}
