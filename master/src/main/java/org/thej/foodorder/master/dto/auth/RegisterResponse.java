package org.thej.foodorder.master.dto.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.thej.foodorder.master.dao.Privilege;
import org.thej.foodorder.master.dao.Role;
import org.thej.foodorder.master.dao.User;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class RegisterResponse {
    private Long userId;
    private String username;
    private String token;
    private Collection<Role> roles;
    private Collection<Privilege> privileges;

    public RegisterResponse(User user, String token) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.token = token;
        this.roles = user.getRoles();
        this.privileges = user.getPrivileges();
    }
}
