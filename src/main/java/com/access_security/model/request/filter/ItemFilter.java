package com.access_security.model.request.filter;

import com.access_security.model.common.ItemType;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFilter extends BaseFilter {
    private String title;
    private ItemType type;
    private BigDecimal minCost;
    private BigDecimal maxCost;
    private String producerName;
}
