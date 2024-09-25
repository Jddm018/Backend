package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")

public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "price", nullable = false)
    private int price;

    @Column (name = "category", nullable = false)
    private String category;

    @Column (name = "images", nullable = false)
    private String images;

    

    


}
