package com.collection.collectionv1.Exceptions;

public class NoPasswordException extends InvalidUserCredentialsException{
    public NoPasswordException(String msg){
        super(msg);
    }
}
