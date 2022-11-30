package com.access_security.model.order;

import com.access_security.model.item.Item;
import com.access_security.model.item.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "order")
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Item item;
    private Order order;
    private Size size;
    private Integer count;
}
