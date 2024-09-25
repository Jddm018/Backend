package com.example.demo.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repositories.IBuyRepositories;
import com.example.demo.entities.Buy;
import com.example.demo.persistence.BuyDAO;


@Component
public class BuyDAOimpl implements BuyDAO {

    @Autowired
    private IBuyRepositories buyRepositories;
    @Override
    public List<Buy> findAll() {
       return (List<Buy>) buyRepositories.findAll();
    }



    @Override
    public Optional<Buy> findById(Long id) {
       return buyRepositories.findById(id);
    }

    @Override
    public Buy save(Buy buy) {
        return buyRepositories.save(buy);
       
    }

    @Override
    public void deleteById(Long id) {
        buyRepositories.deleteById(id);
       
    }



    @Override
    public List<Buy> saveAllBuys(List<Buy> buys) {
        return (List<Buy>) buyRepositories.saveAll(buys);
       
    }

    
}
