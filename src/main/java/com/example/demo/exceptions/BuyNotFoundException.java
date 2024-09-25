package com.example.demo.exceptions;

public class BuyNotFoundException extends RuntimeException {
    public BuyNotFoundException(String message){
        super(message);
    }
}
