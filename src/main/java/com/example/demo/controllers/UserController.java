package com.example.demo.controllers;

import java.util.Optional;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repositories.IRoleRepositories;
import com.example.demo.Repositories.IUserRepositories;
import com.example.demo.Services.IUserService;
import com.example.demo.Services.impl.SessionTokenService;
import com.example.demo.controllers.dto.UserDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InvalidSessionTokenException;
import com.example.demo.exceptions.UnauthorizedAccessException;
import com.example.demo.exceptions.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    private IUserRepositories iUserRepositories;

    @Autowired
    private IRoleRepositories roleRepositories;
    @Autowired
    private IUserService usuarioService;

    @Autowired
    private SessionTokenService sessionTokenService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token Invalido");

        }
        try {
            Optional<User> usuarioOptional = usuarioService.findById(id);

            String email = sessionTokenService.getUserEmailFromToken(token);
            User user = iUserRepositories.findByEmail(email);

            boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
            if (!isAdmin) {
                throw new UnauthorizedAccessException("No puedes realizar esta accion");
            }

            if (usuarioOptional.isPresent()) {
                User usuario = usuarioOptional.get();

                UserDTO usuarioDTO = UserDTO.builder()
                        .id(usuario.getId())
                        .name(usuario.getName())
                        .last_name(usuario.getLast_name())
                        .email(usuario.getEmail())
                        .password(usuario.getPassword())
                        .build();

                return ResponseEntity.ok(usuarioDTO);
            }

            else {
                throw new UserNotFoundException("Usuario no encontrado");
            }

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token Invalido");
        }
        String email = sessionTokenService.getUserEmailFromToken(token);
        User user = iUserRepositories.findByEmail(email);

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedAccessException("No puedes realizar esta accion");
        }

        List<User> usuarios = usuarioService.findAll();
        List<UserDTO> usuariolist = new ArrayList<>();

        for (User usuario : usuarios) {
            UserDTO usuarioDTO = new UserDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setName(usuario.getName());
            usuarioDTO.setLast_name(usuario.getLast_name());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setPassword(usuario.getPassword());
            usuariolist.add(usuarioDTO);
        }

        return ResponseEntity.ok(usuariolist);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserDTO userDTO) throws URISyntaxException {

        if (userDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Role> optionalRoleUser = roleRepositories.findByName("USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);

        userDTO.setRoles(roles);
        User user = usuarioService.save(User.builder()
                .name(userDTO.getName())
                .last_name(userDTO.getLast_name())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(userDTO.getRoles())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String token) {
        if (sessionTokenService.isValidSessionToken(token)) {
            String email = sessionTokenService.getUserEmailFromToken(token);
            User user1 = iUserRepositories.findByEmail(email);

            boolean isAdmin = user1.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
            if (user1.getId().equals(id) || isAdmin) {
                Optional<User> usuarioOptional = usuarioService.findById(id);

                if (usuarioOptional.isPresent()) {
                    User user = usuarioOptional.get();
                    user.setName(userDTO.getName());
                    user.setLast_name(userDTO.getLast_name());
                    user.setEmail(userDTO.getEmail());
                    user.setPassword(userDTO.getPassword());
                    usuarioService.save(user);
                    return ResponseEntity.ok("Registro Actualizado");

                }

                return ResponseEntity.notFound().build();
            } else {
                throw new UnauthorizedAccessException("No puedes realizar esta accion");
            }

        } else {
            throw new InvalidSessionTokenException("Token invalido");
        }

    }

    @GetMapping("/findByEmail")
    public ResponseEntity<User> getUserByToken (@RequestHeader ("Authorization") String token){
        if (!sessionTokenService.isValidSessionToken(token)){
            throw new InvalidSessionTokenException("Token invalido");
        }
        String email = sessionTokenService.getUserEmailFromToken(token);
        User user = iUserRepositories.findByEmail(email);

        return ResponseEntity.ok(user);
    }
       
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (sessionTokenService.isValidSessionToken(token)) {
            if (id != null) {

                String email = sessionTokenService.getUserEmailFromToken(token);
                User user1 = iUserRepositories.findByEmail(email);

                boolean isAdmin = user1.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
                if (user1.getId().equals(id) || isAdmin) {
                    usuarioService.deleteById(id);
                    return ResponseEntity.ok("Usuario eliminado correctamente");
                } else {
                    throw new UnauthorizedAccessException("No tienes permiso para realizar esta acción");
                }

            }
            return ResponseEntity.badRequest().build();
        } else {
            throw new InvalidSessionTokenException("Token invalido");
        }

    }
}
