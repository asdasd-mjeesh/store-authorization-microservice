package com.access_security.util;

import com.access_security.model.deprecated__.Permission;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class DefaultPermissionsFactory {
    private static final Set<Permission> USER_DEFAULT_PERMISSIONS;
    private static final Set<Permission> EMPLOYEE_DEFAULT_PERMISSIONS;
    private static final Set<Permission> ADMIN_DEFAULT_PERMISSIONS;

    static {
        USER_DEFAULT_PERMISSIONS = new HashSet<>(Set.of(
                Permission.ACCOUNT_READ, Permission.ACCOUNT_DELETE,
                Permission.ORDER_SAVE, Permission.ORDER_READ,
                Permission.CART_READ, Permission.CART_EDIT,
                Permission.ITEM_READ,
                Permission.PRODUCER_READ
        ));

        EMPLOYEE_DEFAULT_PERMISSIONS = new HashSet<>(USER_DEFAULT_PERMISSIONS);
        EMPLOYEE_DEFAULT_PERMISSIONS.add(Permission.ORDER_UPDATE);

        ADMIN_DEFAULT_PERMISSIONS = new HashSet<>(EMPLOYEE_DEFAULT_PERMISSIONS);
        ADMIN_DEFAULT_PERMISSIONS.addAll(Set.of(
                Permission.ACCOUNT_SAVE,
                Permission.ORDER_DELETE,
                Permission.ITEM_SAVE,  Permission.ITEM_DELETE,
                Permission.PRODUCER_SAVE, Permission.PRODUCER_DELETE
        ));
    }

    private DefaultPermissionsFactory() {  }

    public static Set<Permission> getUserDefaultPermissions() {
        return USER_DEFAULT_PERMISSIONS;
    }

    public static Set<Permission> getEmployeeDefaultPermissions() {
        return EMPLOYEE_DEFAULT_PERMISSIONS;
    }

    public static Set<Permission> getAdminDefaultPermissions() {
        return ADMIN_DEFAULT_PERMISSIONS;
    }
}
