package com.example.demo.exceptions;

public class FileFormatException extends RuntimeException {
    public FileFormatException(String message){
        super(message);
    }
}
