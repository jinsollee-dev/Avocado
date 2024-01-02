package com.example.avocado.repository;

import com.example.avocado.domain.User;
import com.example.avocado.domain.UserImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<UserImage, Long> {

    UserImage findByUser(User user);
}
