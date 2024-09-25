package com.example.demo.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private int price;
    private String category;
    private String images;
   
   


}
