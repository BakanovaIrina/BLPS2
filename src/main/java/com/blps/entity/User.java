package com.blps.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Setter
    @Getter
    private Role role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "USER");
    }
}
