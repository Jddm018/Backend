package com.example.demo.Services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Services.IProductService;
import com.example.demo.entities.Product;
import com.example.demo.persistence.ProductDAO;


@Service
public class ProductServicesImpl implements IProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<Product> findAll() {
       return productDAO.findAll();
    
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDAO.findById(id);
    
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productDAO.findByPriceInRange(minPrice, maxPrice);
    }

    @Override
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }

        
    
}
