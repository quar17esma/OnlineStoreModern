package com.quar17esma.dao;

import com.quar17esma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    void deleteByEmail(String email);
}
