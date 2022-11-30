package com.access_security.model.cart;

import com.access_security.model.account.Account;
import com.access_security.model.order.OrderItem;
import com.access_security.model.order.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Cart {
    private Long id;
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
