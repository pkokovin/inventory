package ru.spbu.inventory.model;

import org.springframework.security.core.GrantedAuthority;
// ROLE_ADMIN - allowed to create or modify users, ROLE_USER - allowed only modify themselves and devices
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
