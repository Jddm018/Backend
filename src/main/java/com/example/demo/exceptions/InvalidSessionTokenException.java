package com.example.demo.exceptions;

public class InvalidSessionTokenException extends RuntimeException {
    public InvalidSessionTokenException(String message){
        super(message);
    }

}
