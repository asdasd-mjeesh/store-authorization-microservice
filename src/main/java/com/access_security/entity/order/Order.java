package com.access_security.entity.order;

import com.access_security.entity.BaseEntity;
import com.access_security.entity.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
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
