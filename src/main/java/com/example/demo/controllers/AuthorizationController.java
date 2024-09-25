package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repositories.IRoleRepositories;
import com.example.demo.Repositories.IUserRepositories;
import com.example.demo.Services.IUserService;
import com.example.demo.Services.impl.SessionTokenService;
import com.example.demo.controllers.dto.UserDTO;
import com.example.demo.entities.Login;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InvalidSessionTokenException;


@RestController
@RequestMapping("/api/auth")

    public class AuthorizationController {

    @Autowired
    private IUserService usuarioService;

    @Autowired
    private SessionTokenService sessionTokenService;

    @Autowired
    private IUserRepositories userRepositories;

    @Autowired
    private IRoleRepositories roleRepositories;

    @PostMapping("/register")
    public ResponseEntity <?> register (@RequestBody UserDTO userDTO){
        if(userDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
    }
    Optional<Role> optionalRoleUser = roleRepositories.findByName("USER");
    List<Role> roles = new ArrayList<>();

    optionalRoleUser.ifPresent(roles::add);
        
    userDTO.setRoles(roles);

    User userRegister = usuarioService.save(User.builder()
        .name(userDTO.getName())
        .last_name(userDTO.getLast_name()) 
        .email(userDTO.getEmail())
        .password(userDTO.getPassword())
        .roles(userDTO.getRoles())
        .build());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
    }

    @PostMapping("/login")
    public ResponseEntity <?> login (@RequestBody Login login){
        
        User user = userRepositories.findByEmail(login.getEmail());
        Map <String, String> response = new HashMap<>();
        if(user!=null&& user.getPassword().equals(login.getPassword())){
            String token = sessionTokenService.generateSessionToken(login.getEmail());
            response.put("message", token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
    }

    @PostMapping("/logout")
    public ResponseEntity <?> logout (@RequestHeader ("Authorization") String token){
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token Invalido");
        }
        Map<String, String> response = new HashMap<>();
        sessionTokenService.deleteSessionToken(token);
        response.put("message", "Cierre de sesión exitoso");
        return ResponseEntity.ok(response);

    }

    
}
