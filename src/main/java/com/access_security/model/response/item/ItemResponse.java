package com.access_security.model.response.item;

import com.access_security.model.common.ItemType;
import com.access_security.model.common.SizeEnum;
import com.access_security.model.response.producer.ProducerResponse;
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
public class ItemResponse {
    private Long id;
    private String title;
    private ItemType type;
    private BigDecimal cost;
    private ProducerResponse producer;
    private List<SizeEnum> sizes;
}
