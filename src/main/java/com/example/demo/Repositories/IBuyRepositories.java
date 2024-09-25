package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Buy;

@Repository
public interface IBuyRepositories extends CrudRepository<Buy, Long> {
    
}
