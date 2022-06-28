package com.example.ASMapi.exceptions.custom;


public class UnsuccessfulPasswordUpdateException extends RuntimeException{
    public UnsuccessfulPasswordUpdateException(String message) {
        super(message);
    }
}
