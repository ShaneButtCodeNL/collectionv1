package com.collection.collectionv1.Exceptions;

public class JwtTokenExpiredException extends JwtTokenException{
    public JwtTokenExpiredException(String msg){
        super(msg);
    }
}
