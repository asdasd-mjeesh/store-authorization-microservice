package com.access_security.service;

import com.access_security.model.Account;
import com.access_security.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCommunicationService implements AccountService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final List<Account> accounts = new ArrayList<>();

    @PostConstruct
    private void init() {
        accounts.add(new Account(1L, "asdasd",
                "201300@nuos.edu.ua",
                passwordEncoder.encode("asdasd-mjeesh"),
                new ArrayList<>(List.of(new Role(1L, "ROLE_ADMIN")))
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accounts.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .get();
        if(account == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            account.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
        }
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
}
