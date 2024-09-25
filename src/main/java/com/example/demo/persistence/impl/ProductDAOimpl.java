package com.example.demo.persistence.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repositories.IProductRepositories;
import com.example.demo.entities.Product;
import com.example.demo.persistence.ProductDAO;

@Component
public class ProductDAOimpl implements ProductDAO {

    @Autowired
    private IProductRepositories productRepositories;

    @Override
    public void deleteById(Long id) {
          productRepositories.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepositories.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepositories.findById(id);
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepositories.findProductByPrinceInRange(minPrice, maxPrice);
    }

    @Override
    public Product save(Product product) {
        return productRepositories.save(product);
    }

}
