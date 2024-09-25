package com.example.demo.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Services.IUserService;
import com.example.demo.entities.User;
import com.example.demo.persistence.UserDAO;

@Service
public class UserServicesImpl implements IUserService {

    @Autowired
    private UserDAO usuarioDAO;
    
    @Override
    public List<User> findAll() {
        return usuarioDAO.findAll();
       
    }

    @Override
    public Optional<User> findById(Long id) {
        return usuarioDAO.findById(id);
    }

    @Override
    public User save(User usuario) {
        return usuarioDAO.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDAO.deleteById(id);
    }




}
