package com.access_security.model.request.cart;

import com.access_security.model.common.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyItemProperties {
    private Long itemId;
    private Integer count;
    private SizeEnum size;
}
