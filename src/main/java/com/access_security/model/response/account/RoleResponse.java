package com.access_security.model.response.account;

import com.access_security.model.common.PermissionEnum;
import com.access_security.model.common.RoleName;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class RoleResponse {
    private RoleName name;
    private Set<PermissionEnum> permissions;
}
