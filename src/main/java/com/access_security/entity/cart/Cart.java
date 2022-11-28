package com.access_security.entity.cart;

import com.access_security.entity.BaseEntity;
import com.access_security.entity.account.Account;
import com.access_security.entity.order.OrderItem;
import com.access_security.entity.order.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cart extends BaseEntity {
    private Account account;
    private Status status;
    private LocalDate date;
    private boolean isCart;
    private List<OrderItem> items;

    public Cart(Account account) {
        this.account = account;
        this.isCart = true;
        this.status = Status.IS_CART;
        this.date = LocalDate.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void removeItem(Long itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    public BigDecimal getPrice() {
        BigDecimal price = BigDecimal.ZERO;
        for (OrderItem item : items) {
            price = price.add(item.getItem().getCost().multiply(BigDecimal.valueOf(item.getCount())));
        }
        return price;
    }
}
