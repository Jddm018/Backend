package com.example.demo.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Product;

public interface ProductDAO {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    Product save(Product product);

    void deleteById(Long id);
}
