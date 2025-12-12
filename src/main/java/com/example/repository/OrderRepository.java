package com.example.repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUsername(String username);
    boolean existsByUsernameAndItemsProductId(String username, Long productId);

}
