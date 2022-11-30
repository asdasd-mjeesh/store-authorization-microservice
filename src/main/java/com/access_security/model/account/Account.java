package com.access_security.model.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private Long id;
    private String initials;
    private String email;
    private String contact;
    private String password;
    private Role role;
}
