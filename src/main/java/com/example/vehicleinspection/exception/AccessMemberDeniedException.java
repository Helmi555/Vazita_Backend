package com.example.vehicleinspection.exception;

public class AccessMemberDeniedException extends RuntimeException{
    public AccessMemberDeniedException(String msg){
        super(msg);
    }
}
