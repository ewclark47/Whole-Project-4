package com.example.project4task2;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.Iterator;

public class toDB {
    MongoDatabase database;

    public MongoDatabase mongoDBConnect(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://user1234:password1234@cluster0.ywll23i.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi( ServerApi.builder()
                        .version( ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        return database;
    }

    public void createDBCollection(String collectionName){
        database.createCollection( collectionName );
        System.out.println("Collection created.");
    }

    public MongoCollection<Document> getDBCollection(String collectionName){
        MongoCollection<org.bson.Document> collection = database.getCollection( collectionName );
        return collection;
    }

    public void addDocToCollection(String title, String name, String collectionName){
        org.bson.Document document = new org.bson.Document("Title", title).append( "Name", name );
        getDBCollection( collectionName ).insertOne( document );
        System.out.println("Document added to Collection: " + collectionName);
    }

    public void displayDocs(String collectionName){
        FindIterable<Document> iterable = getDBCollection( collectionName ).find();
        Iterator itr = iterable.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
