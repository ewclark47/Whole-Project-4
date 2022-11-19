package com.example.project4task2;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseHandling {
    int counter=0;
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
        database = mongoClient.getDatabase("Project4");
        System.out.println("Connected to Database");
        return database;
    }

    public void createDBCollection(String collectionName){
        //database = mongoDBConnect();
        database.createCollection( collectionName );
        System.out.println("Collection created.");
    }

    public MongoCollection<Document> getDBCollection(String collectionName){
        //database = mongoDBConnect();
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

    public void addDataToDatabase(String category, String numOfQuestions, String type, String difficulty, String timeTakenTo, String connectMethod) throws IOException {
        mongoDBConnect();
        if (getDBCollection( "Trivia App Info" ) == null){
            System.out.println("here");
            createDBCollection( "Trivia App Info" );
        }
        System.out.println("past if statement");
        ArrayList<String> catList = new ArrayList<>();
        catList = castCategories();
        System.out.println("made category name list");
        org.bson.Document document = new org.bson.Document("Category", catList.get( Integer.parseInt( category ) ) ).append( "Number of Questions Requested", numOfQuestions ).append(
                "Type of Questions", type).append( "Question Difficulty", difficulty ).append( "Time Needed to Retrieve Questions", timeTakenTo ).append( "" +
                "App Connection Method", connectMethod );
        getDBCollection( "Trivia App Info" ).insertOne( document );
        System.out.println("Document added to Collection: " + "Trivia App Info");
    }

    public ArrayList<String> castCategories() throws IOException {
        ArrayList<String> categoryNames = new ArrayList<>();
        TriviaModel tm = new TriviaModel();
        String categoryNumbers = tm.getAPICategories();
        String[] str = categoryNumbers.toString().split(",");
        for (int i = 0; i < str.length; i++) {
            if (i % 2 != 0) {
                System.out.println(str[i].substring( 8, str[i].length()-2 ));
                categoryNames.add( str[i].substring( 8, str[i].length()-2 ) );
            }
        }
        return categoryNames;
    }

}
