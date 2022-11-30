package com.access_security.service.account;

import com.access_security.model.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account create(Account account);

    boolean update(Account account);

    Optional<Account> getById(Long id);

    Optional<Account> getByEmail(String email);

    List<Account> getAll();

    boolean deleteById(Long id);
}
