package com.access_security.security;

import com.access_security.model.account.Account;
import com.access_security.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getByEmail(username).orElseThrow(
                () -> new RuntimeException(String.format("user with email=%s is not presented", username))
        );
        System.out.println(account);
        return mapUser(account);
    }

    private User mapUser(Account account) {
        return new User(
                account.getEmail(),
                account.getPassword(),
                account.getRole().getAuthorities()
        );
    }
}
