package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Role;



@Repository
public interface IRoleRepositories extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
    

}
