package com.example.avocado.repository;

import com.example.avocado.domain.Product;
import com.example.avocado.domain.User;
import com.example.avocado.repository.search.ProductRepositoryCustom;
import com.example.avocado.repository.search.ProductRepositoryCustomImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
  Product findByUser(User user);

  Product findByPno(Long pno);
}
