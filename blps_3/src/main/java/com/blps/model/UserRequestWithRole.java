package com.blps.model;

import com.blps.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestWithRole {
    private String username;
    private String password;
    private Role role;
}
