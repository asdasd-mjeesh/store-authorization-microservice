package com.access_security.util;

import com.access_security.model.common.PermissionEnum;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class DefaultPermissionsFactory {
    private static final Set<PermissionEnum> USER_DEFAULT_PERMISSIONS;
    private static final Set<PermissionEnum> EMPLOYEE_DEFAULT_PERMISSIONS;
    private static final Set<PermissionEnum> ADMIN_DEFAULT_PERMISSIONS;

    static {
        USER_DEFAULT_PERMISSIONS = new HashSet<>(Set.of(
                PermissionEnum.ACCOUNT_READ, PermissionEnum.ACCOUNT_DELETE,
                PermissionEnum.ORDER_SAVE, PermissionEnum.ORDER_READ,
                PermissionEnum.CART_READ, PermissionEnum.CART_EDIT,
                PermissionEnum.ITEM_READ,
                PermissionEnum.PRODUCER_READ
        ));

        EMPLOYEE_DEFAULT_PERMISSIONS = new HashSet<>(USER_DEFAULT_PERMISSIONS);
        EMPLOYEE_DEFAULT_PERMISSIONS.add(PermissionEnum.ORDER_UPDATE);

        ADMIN_DEFAULT_PERMISSIONS = new HashSet<>(EMPLOYEE_DEFAULT_PERMISSIONS);
        ADMIN_DEFAULT_PERMISSIONS.addAll(Set.of(
                PermissionEnum.ACCOUNT_SAVE,
                PermissionEnum.ORDER_DELETE,
                PermissionEnum.ITEM_SAVE,  PermissionEnum.ITEM_DELETE,
                PermissionEnum.PRODUCER_SAVE, PermissionEnum.PRODUCER_DELETE
        ));
    }

    private DefaultPermissionsFactory() {  }

    public static Set<PermissionEnum> getUserDefaultPermissions() {
        return USER_DEFAULT_PERMISSIONS;
    }

    public static Set<PermissionEnum> getEmployeeDefaultPermissions() {
        return EMPLOYEE_DEFAULT_PERMISSIONS;
    }

    public static Set<PermissionEnum> getAdminDefaultPermissions() {
        return ADMIN_DEFAULT_PERMISSIONS;
    }
}
