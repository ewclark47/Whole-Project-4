/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
DatabaseHandling.java
 */

package com.example.project4task2;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Sorts;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Aggregates.sort;

public class DatabaseHandling {
    MongoDatabase database;

    // connect to the DB
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

    // create a collection
    public void createDBCollection(String collectionName){
        //database = mongoDBConnect();
        database.createCollection( collectionName );
        System.out.println("Collection created.");
    }

    // get a specified collection from the Mongo DB
    public MongoCollection<Document> getDBCollection(String collectionName){
        //database = mongoDBConnect();
        MongoCollection<org.bson.Document> collection = database.getCollection( collectionName );
        return collection;
    }

    // add a new document to the specified collection in the Mongo DB
    public void addDocToCollection(String title, String name, String collectionName){
        org.bson.Document document = new org.bson.Document("Title", title).append( "Name", name );
        getDBCollection( collectionName ).insertOne( document );
        System.out.println("Document added to Collection: " + collectionName);
    }

    // display all the documents in the specified collection in the Mongo DB
    public String displayDocs(String collectionName){
        String doc="";
        FindIterable<Document> iterable = getDBCollection( collectionName ).find();
        Iterator itr = iterable.iterator();
        while (itr.hasNext()){
            doc += itr.next() + " \n";
        }
        return doc;
    }

    // add data from the user input into the specified collection for the Mongo DB for the android app
    public void addDataToDatabase(String category, int numOfQuestions, String type, String difficulty, long timeTakenTo, String connectMethod) throws IOException {
        mongoDBConnect();
        // make sure there is a collection for the android app, if not make one
        if (getDBCollection( "Trivia App Info" ) == null){
            //System.out.println("here");
            createDBCollection( "Trivia App Info" );
        }
        //System.out.println("past if statement");
        // get all the categories so that the user input category number can be mapped to the actual name
        ArrayList<String> catList = new ArrayList<>();
        catList = castCategories();
        //System.out.println("made category name list");
        // make the document based on user input and insert it into the collection
        // *note: subtracting 9 from the category number to index the list of names because the categories are 9-32 from the API
        org.bson.Document document = new org.bson.Document("Category", catList.get( Integer.parseInt( category )-9 ) ).append( "Number of Questions Requested", numOfQuestions ).append(
                "Type of Questions", type).append( "Question Difficulty", difficulty ).append( "Time Needed to Retrieve Questions", timeTakenTo ).append( "" +
                "App Connection Method", connectMethod );
        getDBCollection( "Trivia App Info" ).insertOne( document );
        System.out.println("Document added to Collection: " + "Trivia App Info");
    }

    // get the most popular category that users choose
    public String mostPopularCategory(){
        mongoDBConnect();
        MongoCollection<org.bson.Document> collection = getDBCollection( "Trivia App Info" );
        // sorting and counting for categorical fields found at:
        // https://stackoverflow.com/questions/67776486/java-mongodb-aggregation-count-occurrences
        AggregateIterable<org.bson.Document> aggregate = collection.aggregate( Arrays.asList( Aggregates.group("$Category", Accumulators.sum("count", 1)),
                sort( Sorts.descending("count"))));
        Document result = aggregate.first();
        System.out.println(result.toString());
        String mostPopular = result.getString( "_id" );
        return mostPopular;
    }

    // get the average number of questions users request
    // Below methodology for getting average of an integer in mongo db found at:
    // https://stackoverflow.com/questions/40307659/get-average-from-mongo-collection-using-aggrerate
    public String averageNumQuestions(){
        mongoDBConnect();
        MongoCollection<org.bson.Document> collection = getDBCollection( "Trivia App Info" );
        AggregateIterable<org.bson.Document> aggregate = collection.aggregate( Arrays.asList( Aggregates.group("_id", new BsonField("averageQuestions", new BsonDocument("$avg", new BsonString("$Number of Questions Requested"))))));
        Document result = aggregate.first();
        double avgQuestions = result.getDouble("averageQuestions");
        String avgQs = String.valueOf( avgQuestions );
        return avgQs;
    }

    // get the average time it takes for the api and web service to respond back to the app
    public String averageTime(){
        mongoDBConnect();
        MongoCollection<org.bson.Document> collection = getDBCollection( "Trivia App Info" );
        AggregateIterable<org.bson.Document> aggregate = collection.aggregate( Arrays.asList( Aggregates.group("_id", new BsonField("averageTime", new BsonDocument("$avg", new BsonString("$Time Needed to Retrieve Questions"))))));
        Document result = aggregate.first();
        double avgTime = result.getDouble("averageTime");
        String avgT = String.valueOf( avgTime );
        return avgT;
    }

    // uses the model's method to get the categories and break the responding JSON up into the actual category names
    // since the user input is the category number (9-32)
    public ArrayList<String> castCategories() throws IOException {
        ArrayList<String> categoryNames = new ArrayList<>();
        TriviaModel tm = new TriviaModel();
        String categoryNumbers = tm.getAPICategories();
        //System.out.println(categoryNumbers);
        String[] str = categoryNumbers.toString().split(",");
        for (int i = 0; i < str.length; i++) {
            if (i % 2 != 0) {
                System.out.println(str[i].substring( 8, str[i].length()-2 ));
                categoryNames.add( str[i].substring( 8, str[i].length()-2 ) );
            }
        }
        //System.out.println(categoryNames);
        return categoryNames;
    }

}
