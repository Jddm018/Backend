package com.example.demo.persistence.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.Repositories.IUserRepositories;
import com.example.demo.entities.User;
import com.example.demo.persistence.UserDAO;


@Component
public class UserDAOimpl implements UserDAO {

    @Autowired
    private IUserRepositories usuarioRepositories;

    @Override
    public void deleteById(Long id) {
        usuarioRepositories.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) usuarioRepositories.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return usuarioRepositories.findById(id);
    }

    @Override
    public User save(User usuario) {
        return usuarioRepositories.save(usuario);
    }
    
}
