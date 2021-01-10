package com.company;

public class ReachedMaxOperationsException extends RuntimeException{
    public ReachedMaxOperationsException(String errorMessage){
        super(errorMessage);
    }
}
