package com.access_security.entity.order;

import com.access_security.entity.BaseEntity;
import com.access_security.entity.item.Item;
import com.access_security.entity.item.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "order", callSuper = true)
@ToString(exclude = "order")
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    private Item item;
    private Order order;
    private Size size;
    private Integer count;
}
