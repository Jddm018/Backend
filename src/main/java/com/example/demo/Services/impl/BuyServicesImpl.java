package com.example.demo.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Services.IBuyService;
import com.example.demo.entities.Buy;
import com.example.demo.persistence.BuyDAO;

@Service
public class BuyServicesImpl implements IBuyService {

    @Autowired
    private BuyDAO buyDAO;


    @Override
    public List<Buy> findAll() {
        return buyDAO.findAll();
    }

    @Override
    public Optional<Buy> findById(Long id) {
        return buyDAO.findById(id);
    }

    @Override
    public Buy save(Buy buy) {
        return buyDAO.save(buy);


    }

    @Override
    public void deleteById(Long id) {
        buyDAO.deleteById(id);
    }

    @Override
    public List<Buy> saveAllBuys(List<Buy> buys) {
        return buyDAO.saveAllBuys(buys);
    }

    
    

}
