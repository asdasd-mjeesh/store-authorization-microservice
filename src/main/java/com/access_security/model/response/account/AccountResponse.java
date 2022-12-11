package com.access_security.model.response.account;

import com.access_security.model.common.AccountStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AccountResponse {
    private Long id;
    private String name;
    private String contact;
    private String email;
    private String password;
    private RoleResponse role;
    private LocalDateTime registrationDate;
    private AccountStatus status;
}
