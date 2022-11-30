package com.access_security.model.order;

import com.access_security.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Account account;
    private Status status;
    private LocalDate date;
    private boolean isCart;
    private List<OrderItem> items;

    public Order(Account account, List<OrderItem> items) {
        this.account = account;
        this.items = items;
        this.isCart = false;
        this.date = LocalDate.now();
        this.status = Status.IN_PROCESS;
    }
}
