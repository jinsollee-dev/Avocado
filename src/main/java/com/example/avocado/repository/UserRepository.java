package com.example.avocado.repository;

import com.example.avocado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String username);
}
