package com.collection.collectionv1.Exceptions;

public class EmptyUserException extends InvalidUserCredentialsException{
    public EmptyUserException(String msg){
        super(msg);
    }
}
