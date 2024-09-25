package com.example.demo.controllers.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BuyDTO {

    private Long id;
    private LocalDateTime date;
    private UserDTO user;
    private ProductDTO product;
    private int quantity;
    
}