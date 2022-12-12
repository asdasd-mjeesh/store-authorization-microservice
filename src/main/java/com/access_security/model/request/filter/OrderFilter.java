package com.access_security.model.request.filter;

import com.access_security.model.common.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter extends BaseFilter {
    private String accountName;
    private BigDecimal minTotalPrice;
    private BigDecimal maxTotalPrice;
    private LocalDateTime minBuyDate;
    private LocalDateTime maxBuyDate;
    private OrderStatus status;
}
