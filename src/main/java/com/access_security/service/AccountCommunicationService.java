package com.access_security.service;

import com.access_security.model.account.Account;
import com.access_security.model.account.Role;
import com.access_security.model.account.RoleName;
import com.access_security.util.DefaultPermissionsFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCommunicationService implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final List<Account> accounts = new ArrayList<>();

    @PostConstruct
    private void init() {
        accounts.add(new Account(1L,
                "asdasd",
                "201300@nuos.edu.ua",
                "contact",
                passwordEncoder.encode("asdasd-mjeesh"),
                new Role(1L, RoleName.ADMIN, DefaultPermissionsFactory.getAdminDefaultPermissions())));
    }

    @Override
    public Optional<Account> create(Account account) {
        Long accountId = Long.getLong(String.valueOf(accounts.size() + 1));
        account.setId(accountId);
        this.accounts.add(account);
        return Optional.ofNullable(account);
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return accounts.stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst();
    }
}
