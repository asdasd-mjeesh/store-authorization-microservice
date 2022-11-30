package com.access_security.service;

import com.access_security.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> create(Account account);

    Optional<Account> getById(Long id);
}
