package org.thej.foodorder.master.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String roleName;
    private String roleDescription;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;
    @ManyToMany
    @JoinTable(
        name = "t_role_privilege",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    @JsonIgnore
    private Collection<Privilege> privileges;
}
