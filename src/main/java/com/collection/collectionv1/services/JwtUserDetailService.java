package com.collection.collectionv1.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${spring.security.user.password}")
    private String password;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equalsIgnoreCase(adminUsername)){
            return new User(username,password,new ArrayList<>());
        }else throw new UsernameNotFoundException("The Username "+username+" is not Valid.");
    }
}
