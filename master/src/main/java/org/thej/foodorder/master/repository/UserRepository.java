package org.thej.foodorder.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thej.foodorder.master.dao.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsername(String username);
}
