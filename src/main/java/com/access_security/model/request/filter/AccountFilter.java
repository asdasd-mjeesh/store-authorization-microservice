package com.access_security.model.request.filter;

import com.access_security.model.common.AccountStatus;
import com.access_security.model.common.RoleName;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilter extends BaseFilter {
    private String name;
    private String contact;
    private String email;
    private LocalDateTime minimalRegistrationDate;
    private LocalDateTime maximalRegistrationDate;
    private AccountStatus status;
    private RoleName role;
}
