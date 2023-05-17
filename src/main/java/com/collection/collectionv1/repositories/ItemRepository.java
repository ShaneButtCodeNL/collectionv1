package com.collection.collectionv1.repositories;


import com.collection.collectionv1.models.CollectionItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<CollectionItem, ObjectId> {
    List<CollectionItem> findItemsByTypeIgnoreCase(String type);

}
