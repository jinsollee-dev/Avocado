package com.example.avocado.repository;

import com.example.avocado.domain.Product;
import com.example.avocado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findByUser(User user);
}
