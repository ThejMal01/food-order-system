package org.thej.foodorder.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thej.foodorder.master.dao.User;

@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);
}
