package com.access_security.security;

import com.access_security.entity.account.Account;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class UserDetailsMapper {

    public static UserDetails fromAccount(Account account) {
        return new User(
                account.getEmail(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                account.getRole().getAuthorities()
        );
    }
}
