package com.voting.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.voting.votingsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}