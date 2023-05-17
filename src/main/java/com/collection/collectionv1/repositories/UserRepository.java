package com.collection.collectionv1.repositories;

import com.collection.collectionv1.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <User, ObjectId> {
    User findUserByUserNameAndPasswordIgnoreCase(String userName,String password);
}
