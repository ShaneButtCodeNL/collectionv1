package com.collection.collectionv1.controllers;


import com.collection.collectionv1.models.CollectionItem;
import com.collection.collectionv1.services.ItemService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/collection")
public class ItemController {

    private final String DEFAULT_IMG_PATH="https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%2Fimages%3Fk%3Dno%2Bimage%2Bavailable&psig=AOvVaw14AvVzU68vDXO2ZNjPlYPL&ust=1682387483620000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCOjup_azwf4CFQAAAAAdAAAAABAH";
    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<List<CollectionItem>> getAllItems(){
        return new ResponseEntity<List<CollectionItem>>(itemService.allItems(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CollectionItem>> getItemsWithName(@PathVariable String name){
        return new ResponseEntity<List<CollectionItem>>(itemService.itemsWithName(name),HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CollectionItem>> getItemsByType(@PathVariable String type){
        return new ResponseEntity<List<CollectionItem>>(itemService.itemsByType(type),HttpStatus.OK);
    }

    @GetMapping("/type/{type}/name/{name}")
    public ResponseEntity<List<CollectionItem>> getItemsByTypeAndWithName(@PathVariable String type,@PathVariable String name){
        ResponseEntity<List<CollectionItem>> re=  new ResponseEntity<List<CollectionItem>>(itemService.itemsByTypeWithName(type,name),HttpStatus.OK);
        return re;
    }

    @PostMapping("/anime")
    public ResponseEntity<CollectionItem> addAnimeItem(@org.jetbrains.annotations.NotNull @RequestBody Map<String,Object> payload){
        String name=payload.get("name").toString();
        List<String> genres = payload.containsKey("genres")?(List<String>)payload.get("genres"):new ArrayList<String>();
        String mediaType = payload.containsKey("mediaType")?payload.get("mediaType").toString():null;
        String publisher = payload.containsKey("publisher")?payload.get("publisher").toString():null;
        String condition = payload.containsKey("condition")?payload.get("condition").toString():null;
        String releaseDate = payload.containsKey("releaseDate")?payload.get("releaseDate").toString():null;

        HashMap<String,Object> details = new HashMap<String,Object>();
        details.put("name",name);
        details.put("genres",genres);
        if(mediaType!=null)details.put("mediaType",mediaType);
        if(publisher!=null)details.put("publisher",publisher);
        if(condition!=null)details.put("condition",condition);
        if(releaseDate!=null)details.put("releaseDate",releaseDate);
        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setType("Anime");
        collectionItem.setImgPath((payload.containsKey("imgPath") && (payload.get("imgPath") != null)) ? payload.get("imgPath").toString() : DEFAULT_IMG_PATH);
        collectionItem.setDetails(details);
        return new ResponseEntity<CollectionItem>(itemService.addCollectionItem(collectionItem),HttpStatus.OK);
    }

    @PostMapping("/manga")
    public ResponseEntity<CollectionItem> addMangaItem(@org.jetbrains.annotations.NotNull @RequestBody Map<String,Object> payload){
        String name=payload.get("name").toString();
        String publisher = payload.containsKey("publisher")?payload.get("publisher").toString():null;
        String condition = payload.containsKey("condition")?payload.get("condition").toString():null;
        String author = payload.containsKey("author")?payload.get("author").toString():null;
        Integer volume = payload.containsKey("volume")?(int)payload.get("volume"):null;

        HashMap<String,Object> details = new HashMap<String,Object>();
        details.put("name",name);
        if(author!=null)details.put("author",author);
        if(publisher!=null)details.put("publisher",publisher);
        if(condition!=null)details.put("condition",condition);
        if(volume!=null)details.put("volume",volume);
        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setType("Manga");
        collectionItem.setImgPath((payload.containsKey("imgPath") && (payload.get("imgPath") != null)) ? payload.get("imgPath").toString() : DEFAULT_IMG_PATH);
        collectionItem.setDetails(details);
        return new ResponseEntity<CollectionItem>(itemService.addCollectionItem(collectionItem),HttpStatus.OK);
    }

    @PostMapping("/figure")
    public ResponseEntity<CollectionItem> addFigureItem(@org.jetbrains.annotations.NotNull @RequestBody Map<String,Object> payload){
        String name=payload.get("name").toString();
        String condition = payload.containsKey("condition")?payload.get("condition").toString():null;
        Boolean ageRestricted = payload.containsKey("ageRestricted")?(Boolean)payload.get("ageRestricted"):null;
        String type = payload.containsKey("type")?payload.get("type").toString():null;
        Boolean sealed = payload.containsKey("sealed")?(Boolean) payload.get("sealed"):null;
        String series = payload.containsKey("series")?payload.get("series").toString():null;
        String from = payload.containsKey("from")?payload.get("from").toString():null;

        HashMap<String,Object> details = new HashMap<String,Object>();
        details.put("name",name);
        if(condition!=null)details.put("condition",condition);
        if(ageRestricted!=null)details.put("ageRestricted",ageRestricted);
        if(type!=null)details.put("type",type);
        if(sealed!=null)details.put("sealed",sealed);
        if(series!=null)details.put("series",series);
        if(from!=null)details.put("from",from);

        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setType("Figure");
        collectionItem.setImgPath((payload.containsKey("imgPath") && (payload.get("imgPath") != null)) ? payload.get("imgPath").toString() : DEFAULT_IMG_PATH);
        collectionItem.setDetails(details);
        return new ResponseEntity<CollectionItem>(itemService.addCollectionItem(collectionItem),HttpStatus.OK);
    }

    @PostMapping("/video-game")
    public ResponseEntity<CollectionItem> addVideoGameItem(@org.jetbrains.annotations.NotNull @RequestBody Map<String,Object> payload){
        String name=payload.get("name").toString();
        String platform = payload.get("platform").toString();
        List<String> genres = payload.containsKey("genres")?(List<String>)payload.get("genres"):new ArrayList<String>();
        String mediaType = payload.containsKey("mediaType")?payload.get("mediaType").toString():null;
        String publisher = payload.containsKey("publisher")?payload.get("publisher").toString():null;
        String condition = payload.containsKey("condition")?payload.get("condition").toString():null;
        String releaseDate = payload.containsKey("releaseDate")?payload.get("releaseDate").toString():null;
        Boolean sealed = payload.containsKey("sealed")?(Boolean) payload.get("sealed"):null;
        Boolean hasCase = payload.containsKey("hasCase")?(Boolean) payload.get("hasCase"):null;


        HashMap<String,Object> details = new HashMap<String,Object>();
        details.put("name",name);
        details.put("platform",platform);
        details.put("genres",genres);
        if(mediaType!=null)details.put("mediaType",mediaType);
        if(publisher!=null)details.put("publisher",publisher);
        if(condition!=null)details.put("condition",condition);
        if(releaseDate!=null)details.put("releaseDate",releaseDate);
        if(sealed!=null)details.put("sealed",sealed);
        if(hasCase!=null)details.put("hasCase",hasCase);


        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setType("VideoGame");
        collectionItem.setImgPath((payload.containsKey("imgPath") && (payload.get("imgPath") != null)) ? payload.get("imgPath").toString() : DEFAULT_IMG_PATH);
        collectionItem.setDetails(details);
        return new ResponseEntity<CollectionItem>(itemService.addCollectionItem(collectionItem),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CollectionItem> updateAnimeItem(@RequestBody Map<String,Object> payload){
        CollectionItem collectionItem = itemService.updateOneById(new ObjectId(payload.get("id").toString()),payload);
        if(collectionItem==null)return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<CollectionItem>(collectionItem,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CollectionItem> deleteItemById(@RequestBody Map<String,Object> payload){
        ObjectId id = new ObjectId(payload.get("id").toString());
        CollectionItem item = itemService.deleteOneById(id);
        return new ResponseEntity<CollectionItem>(item,HttpStatus.OK);
    }
}
