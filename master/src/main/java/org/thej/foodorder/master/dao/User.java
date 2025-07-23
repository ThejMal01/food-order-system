package org.thej.foodorder.master.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "t_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    private boolean isActive;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> {
                    Stream<GrantedAuthority> roleAuthority = Stream.of(
                            (GrantedAuthority) () -> "ROLE_" + role.getRoleName()
                    );
                    Stream<GrantedAuthority> privilegeAuthorities = role.getPrivileges().stream()
                            .map(privilege -> (GrantedAuthority) privilege::getPrivilegeName);
                    return Stream.concat(roleAuthority, privilegeAuthorities);
                })
                .toList();
    }

    public Collection<Privilege> getPrivileges() {
        return roles.stream()
                .flatMap(role -> role.getPrivileges().stream())
                .distinct()
                .toList();
    }
}
