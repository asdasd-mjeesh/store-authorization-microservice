package com.access_security.service;

import com.access_security.model.account.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> create(Account account);

    Optional<Account> getById(Long id);

    Optional<Account> getByEmail(String email);
}
