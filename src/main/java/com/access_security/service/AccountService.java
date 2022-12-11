package com.access_security.service;

import com.access_security.model.response.account.AccountResponse;

import java.util.Optional;

public interface AccountService {
    Optional<AccountResponse> create(AccountResponse account);

    Optional<AccountResponse> getById(Long id);

    Optional<AccountResponse> getByEmail(String email);
}
