package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.User;

public interface IUserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User usuario);

    void deleteById(Long id);
}
    

