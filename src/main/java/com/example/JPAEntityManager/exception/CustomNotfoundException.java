package com.example.JPAEntityManager.exception;

public class CustomNotfoundException extends  RuntimeException{
    public CustomNotfoundException(String message){
        super(message);
    }
}
