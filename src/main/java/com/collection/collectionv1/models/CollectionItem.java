package com.collection.collectionv1.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection="collectionitems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionItem {
    @Id
    private ObjectId id;
    private String type;
    private String imgPath;
    private HashMap<String,Object> details;
    @Override
    public String toString(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("CollectionItem:\n\tName : "+getDetails().get("name").toString());
        stringBuilder.append("\n\tType : " + getType());
        return stringBuilder.toString();
    }
}
