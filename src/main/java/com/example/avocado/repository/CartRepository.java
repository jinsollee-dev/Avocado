package com.example.avocado.repository;


import com.example.avocado.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUser_Id(Long userId);
}
