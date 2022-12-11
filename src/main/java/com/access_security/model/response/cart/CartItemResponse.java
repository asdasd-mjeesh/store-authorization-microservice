package com.access_security.model.response.cart;

import com.access_security.model.common.SizeEnum;
import com.access_security.model.response.item.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private ItemResponse itemResponse;
    private SizeEnum size;
    private Integer count;
}
