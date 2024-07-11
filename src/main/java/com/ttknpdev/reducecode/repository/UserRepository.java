package com.ttknpdev.reducecode.repository;

import com.ttknpdev.reducecode.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
