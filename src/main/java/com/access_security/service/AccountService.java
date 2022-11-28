package com.access_security.service;

import com.access_security.entity.account.Account;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    User create(Account account);

    boolean update(Account account);

    Optional<User> getById(Long id);

    Optional<Account> getByEmail(String email);

    List<Account> getAll();

    boolean deleteById(Long id);
}
