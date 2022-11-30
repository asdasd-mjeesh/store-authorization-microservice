package com.access_security.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;
    private RoleName roleName;
    private Set<Permission> permissions = new HashSet<>();
}
