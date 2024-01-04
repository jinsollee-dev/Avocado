package com.example.avocado.repository;

import com.example.avocado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u where u.nickname = :writer")
    User findByNickname(String writer);
    boolean existsByNickname(String nickname);
    boolean existsByUsername(String username);
}
