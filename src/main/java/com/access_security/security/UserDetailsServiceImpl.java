package com.access_security.security;

import com.access_security.entity.account.Account;
import com.access_security.exception.EntityNotFoundException;
import com.access_security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.getByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException(Account.class, "email=" + email)
        );
        return UserDetailsMapper.fromAccount(account);
    }
}
