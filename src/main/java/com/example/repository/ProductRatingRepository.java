package com.example.repository;

import com.example.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    Optional<ProductRating> findByProductIdAndUserId(Long productId, Long userId);
    List<ProductRating> findByProductId(Long productId);
}
