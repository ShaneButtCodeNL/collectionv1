package com.collection.collectionv1.services;

import com.collection.collectionv1.models.User;
import com.collection.collectionv1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Find a User by username and password
     * @param userName Username
     * @param password Password
     * @return The user with these credentials
     */
    public User login(String userName, String password){
        User user= userRepository.findUserByUserNameAndPasswordIgnoreCase(userName,password);
        if(user==null)return null;
        updateLastAccess(user);
        return user;
    }

    /**
     * Updates last access value in DB
     * @param user The user to be updated
     */
    private void updateLastAccess(User user){
        user.setLastAccess(new Date());
        mongoTemplate.save(user);
    }
}
