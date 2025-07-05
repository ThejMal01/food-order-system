package org.thej.foodorder.master.dto.auth;

import lombok.Data;
import org.thej.foodorder.master.dao.Privilege;
import org.thej.foodorder.master.dao.Role;
import org.thej.foodorder.master.dao.User;

import java.util.Collection;

@Data
public class LoginResponse {
    private Long userId;
    private String username;
    private String token;
    private Collection<Role> roles;
    private Collection<Privilege> privileges;

    public LoginResponse(User user, String token) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.token = token;
        this.roles = user.getRoles();
        this.privileges = user.getPrivileges();
    }
}
