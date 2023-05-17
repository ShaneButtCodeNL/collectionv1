package com.collection.collectionv1.controllers;

import com.collection.collectionv1.Exceptions.InvalidUserCredentialsException;
import com.collection.collectionv1.models.User;
import com.collection.collectionv1.services.JwtService;
import com.collection.collectionv1.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody @NotNull Map<String,String> payload){
        String userName = payload.get("username");
        String password = payload.get("password");
        User user = userService.login(userName,password);
        String jwtString;
        try{
            jwtString=jwtService.generateToken(user);
        }catch (InvalidUserCredentialsException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(jwtString, HttpStatus.OK);
    }
}
