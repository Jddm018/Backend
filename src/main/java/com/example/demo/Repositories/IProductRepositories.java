package com.example.demo.Repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Product;

@Repository
public interface IProductRepositories extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findProductByPrinceInRange(BigDecimal minPrice, BigDecimal maxPrice);

}
