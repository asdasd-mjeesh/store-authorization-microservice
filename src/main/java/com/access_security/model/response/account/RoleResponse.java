package com.access_security.model.response.account;

import com.access_security.model.common.PermissionEnum;
import com.access_security.model.common.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private RoleName name;
    private Set<PermissionEnum> permissions;
}
