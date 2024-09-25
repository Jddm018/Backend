package com.example.demo.persistence;

import java.util.List;
import java.util.Optional;


import com.example.demo.entities.Buy;

public interface BuyDAO {
    List<Buy> findAll();

    Optional<Buy> findById(Long id);

    List<Buy> saveAllBuys (List<Buy> buys);
    
    Buy save (Buy buy);

    void deleteById(Long id);

}
