package com.example.repository;


import com.example.model.Product;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	  List<Product> findByNameContainingIgnoreCase(String name);
	  List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}

