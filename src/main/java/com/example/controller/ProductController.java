package com.example.controller;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/products")

public class ProductController {
	@Autowired
	  private ProductRepository productRepository;

	  @Autowired
	  private Cloudinary cloudinary;

  @PostMapping

  public ResponseEntity<?> addProduct(
      @RequestParam String name,
      @RequestParam Double price,
      @RequestParam String category,
      @RequestParam MultipartFile image) throws IOException {

   
	  Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
		        ObjectUtils.asMap("folder", "ecommerce_products"));

    String imageUrl = (String) uploadResult.get("secure_url");

  
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setCategory(category);
    product.setImageUrl(imageUrl);

    productRepository.save(product);

    return ResponseEntity.ok("Product added successfully with Cloudinary image!");
  }

  @GetMapping
  
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
  

  
}
