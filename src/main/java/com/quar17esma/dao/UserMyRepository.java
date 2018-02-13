package com.quar17esma.dao;

import com.quar17esma.model.UserMy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMyRepository extends JpaRepository<UserMy, Long> {
    Optional<UserMy> findByEmail(String email);

//    boolean existsByEmail(String email);
}
