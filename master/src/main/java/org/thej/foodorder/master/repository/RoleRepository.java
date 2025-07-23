package org.thej.foodorder.master.repository;

import org.springframework.stereotype.Repository;
import org.thej.foodorder.master.dao.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository <Role>{
    Optional<Role> findByRoleName(String roleUser);
}
