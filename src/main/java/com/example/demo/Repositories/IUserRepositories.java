package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;



@Repository
public interface IUserRepositories extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
