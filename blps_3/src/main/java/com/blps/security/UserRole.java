package com.blps.security;

import java.util.Set;

public enum UserRole {
    OWNER(Set.of(UserPermission.CREATE_ADVERT, UserPermission.VIEW_BOOKING,
            UserPermission.APPROVE_BOOKING, UserPermission.REJECT_BOOKING)),
    BUYER(Set.of(UserPermission.VIEW_ADVERT, UserPermission.CREATE_BOOKING,
            UserPermission.REJECT_BOOKING));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
