package com.collection.collectionv1.Exceptions;

public class NoUserNameException extends InvalidUserCredentialsException{
    public NoUserNameException(String msg){
        super(msg);
    }
}
