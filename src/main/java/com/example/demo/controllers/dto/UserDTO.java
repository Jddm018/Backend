package com.example.demo.controllers.dto;





import java.util.List;

import com.example.demo.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

   
    private Long id;  
    private String name;
    private String last_name;
    private String email;
    private String password;
    private List<Role> roles;
    
    

}
