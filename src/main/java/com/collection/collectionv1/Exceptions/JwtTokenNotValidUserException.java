package com.collection.collectionv1.Exceptions;

public class JwtTokenNotValidUserException extends JwtTokenException{
    public JwtTokenNotValidUserException(String msg){
        super(msg);
    }
}
