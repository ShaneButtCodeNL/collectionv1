package com.collection.collectionv1.services;


import com.collection.collectionv1.models.CollectionItem;
import com.collection.collectionv1.repositories.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * Returns a list of all items in collection
     * @return A List of items
     */
    public List<CollectionItem> allItems(){ return itemRepository.findAll(); }


    /**
     * Return a List of items matching a type
     * @param type The type of item
     * @return List of items
     */
    public List<CollectionItem> itemsByType(String type){
        return itemRepository.findItemsByTypeIgnoreCase((type));
    }

    /**
     * Return a list of Collection items that contain the given pattern in their name.
     * @param name A partial or complete name
     * @return List of items
     */
    public List<CollectionItem> itemsWithName(String name){
        List<CollectionItem> collectionItems= allItems();
        collectionItems.removeIf(item->( !item.getDetails().containsKey("name") || !item.getDetails().get("name").toString().toLowerCase().contains((CharSequence) name.toLowerCase()) ));
        return collectionItems;
    }

    /**
     * Return a list of CollectionItems that of are a selected type and contain a string in their name.
     * @param type Type of Item
     * @param name String contained in the items name
     * @return List Of Items
     */
    public List<CollectionItem> itemsByTypeWithName(String type,String name){
        List<CollectionItem> collectionItems = itemsByType(type);
        collectionItems.removeIf(item->( !item.getDetails().containsKey("name") || !item.getDetails().get("name").toString().toLowerCase().contains((CharSequence) name.toLowerCase()) ));
        return collectionItems;
    }

    /**
     * Adds an item to the collection
     * @param item A collection Item to add
     * @return The added item
     */
    public CollectionItem addCollectionItem(CollectionItem item){
        CollectionItem collectionItem = itemRepository.insert(item);
        return collectionItem;
    }

    /**
     * Update a detail of an item
     * @param collectionItem The item to be updated
     * @param str The key of detail to be updated
     * @param map a map containing updated values
     */
    private void updateDetailOfItem(CollectionItem collectionItem, String str, Map<String,Object> map){
        if(map==null)return;
        if(map.containsKey(str)){
            collectionItem.getDetails().put(str,map.get(str));
        }
    }

    /**
     * Updates details of an item
     * @param collectionItem The item to be updated
     * @param map The map of values to be updated
     */
    private void updateAllDetailsOnItem(CollectionItem collectionItem,Map<String,Object> map){
        updateDetailOfItem(collectionItem,"genres",map);
        updateDetailOfItem(collectionItem,"hasCase",map);
        updateDetailOfItem(collectionItem,"name",map);
        updateDetailOfItem(collectionItem,"platform",map);
        updateDetailOfItem(collectionItem,"publisher",map);
        updateDetailOfItem(collectionItem,"condition",map);
        updateDetailOfItem(collectionItem,"releaseDate",map);
        updateDetailOfItem(collectionItem,"sealed",map);
        updateDetailOfItem(collectionItem,"volume",map);
        updateDetailOfItem(collectionItem,"author",map);
        updateDetailOfItem(collectionItem,"from",map);
        updateDetailOfItem(collectionItem,"ageRestricted",map);
        updateDetailOfItem(collectionItem,"type",map);
        updateDetailOfItem(collectionItem,"limitedEdition",map);
        updateDetailOfItem(collectionItem,"mediaType",map);
    }

    /**
     * Updates an item in collection
     * @param id the id of the item to be updated
     * @param map A map of values to be updated {key,newValue}
     * @return the updated item
     */
    public CollectionItem updateOneById(ObjectId id, Map<String,Object> map){
        if(map==null)return null;
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        CollectionItem collectionItem= mongoTemplate.findOne(query,CollectionItem.class);
        //Update "Type" if Given
        if(map.containsKey("type")){
            collectionItem.setType(map.get("type").toString());
        }
        //Update "imgPath" if Given
        if(map.containsKey("imgPath")){
            collectionItem.setType(map.get("imgPath").toString());
        }

        //Update details if given
        updateAllDetailsOnItem(collectionItem,map);

        mongoTemplate.save(collectionItem);

        return collectionItem;
    }

    /**
     * Removes an item from collection
     * @param id The id of item to be removed
     * @return The removed item
     */
    public CollectionItem deleteOneById(ObjectId id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        CollectionItem collectionItem=mongoTemplate.findOne(query,CollectionItem.class);
        mongoTemplate.remove(collectionItem);
        return collectionItem;
    }
}
