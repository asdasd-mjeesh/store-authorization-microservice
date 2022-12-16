package com.access_security.security;

import com.access_security.model.response.account.AccountResponse;
import com.access_security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AccountResponse> maybeAccount = accountService.getByEmail(email);
        if(maybeAccount.isEmpty()) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            AccountResponse account = maybeAccount.get();
            log.info("User found in the database: {}", email);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            account.getRole().getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getPermissionName())));
            return new User(account.getEmail(), account.getPassword(), authorities);
        }
    }
}
