package com.collection.collectionv1.Exceptions;

public class UserExistsException extends Exception{
    public UserExistsException(String message){
        super(message);
    }
}
