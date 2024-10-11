package com.blps.security;

public enum UserPermission {
    CREATE_ADVERT("advert:create"),
    VIEW_ADVERT("advert.view"),
    CREATE_BOOKING("booking:create"),
    VIEW_BOOKING("booking:view"),
    APPROVE_BOOKING("booking:approve"),
    REJECT_BOOKING("booking:reject"),
    PROVIDE_BOOKING("booking:provide");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
