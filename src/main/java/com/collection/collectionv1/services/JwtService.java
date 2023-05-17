package com.collection.collectionv1.services;

import com.collection.collectionv1.Exceptions.*;
import com.collection.collectionv1.JWT.JwtTokenGenerator;
import com.collection.collectionv1.JWT.JwtTokenReader;
import com.collection.collectionv1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    JwtTokenReader jwtTokenReader;

    /**
     * Generates a JWT.
     * @param user The user to get a token for
     * @return The JWT String for this user if valid.
     * @throws InvalidUserCredentialsException If user is null or if "username" or "password" are empty strings
     */
    public String generateToken(User user) throws InvalidUserCredentialsException {
        if(user==null) throw new EmptyUserException("User Doesn't Exist.");
        if(user.getUserName().equalsIgnoreCase(""))throw new NoUserNameException("Username is empty.");
        if(user.getPassword().equalsIgnoreCase(""))throw new NoPasswordException("Password is empty.");
        String token= jwtTokenGenerator.createToken(user);
        return token;
    }

    /**
     * Checks if JWT is valid. Sent by proper user and token is not expired
     * @param token A string representation of a JWT
     * @return Is the token valid
     */
    public boolean validateToken(String token){
        boolean isValid=false;
        try{
            isValid=jwtTokenReader.verifyJwtString(token);
        }catch (JwtTokenException e){
            System.out.println(e);
            return false;
        }
        return isValid;
    }

    public String getUsernameFromToken(String token){
        String name=null;
        try {
            name=jwtTokenReader.getUserNameFromToken(token);
        }catch(JwtTokenException e){
            System.out.println(e.getMessage());
            return null;
        }
        return name;
    }
}
