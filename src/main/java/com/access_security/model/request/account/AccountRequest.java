package com.access_security.model.request.account;

import com.access_security.model.common.AccountStatus;
import com.access_security.model.common.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private Long id;
    private String name;
    private String contact;
    private String email;
    private String password;
    private RoleName roleName;
    private LocalDateTime registrationDate;
    private AccountStatus status;
}
