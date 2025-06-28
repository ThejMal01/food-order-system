package org.thej.foodorder.webcommons.dto.auth;

import lombok.Data;
import org.thej.foodorder.master.dao.Privilege;
import org.thej.foodorder.master.dao.Role;

import java.util.Collection;

@Data
public class LoginResponse {
    private Long userId;
    private String username;
    private String token;
    private Collection<Role> roles;
    private Collection<Privilege> privileges;
}
