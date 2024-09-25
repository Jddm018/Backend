package com.example.demo.Services;

import java.util.List;
import java.util.Optional;


import com.example.demo.entities.Buy;

public interface IBuyService {

    List<Buy> findAll();
    
    Optional<Buy> findById(Long id);

    Buy save(Buy buy);

    void deleteById(Long id);

    List<Buy> saveAllBuys (List<Buy> buys);

}
