package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    String amount; // number from 1 - however many questions you want to see
    String category; // numbered 9-32 by topic
    String difficulty; // easy, medium, hard
    String type; // boolean for True/False and multiple for Multiple Choice
    MongoDatabase database;

    public static void main(String[] args) {
        System.out.println( "Hello Quiz Game!" );
        Main main = new Main();
        System.out.println("HTTP Connect Method:");
        try {
            main.apiGet();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        System.out.println("Categories: ");
        try {
            main.apiGetCategories();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        System.out.println("JSOUP Method:");
        try {
            main.apiConnect();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        System.out.println("Categories:");
        try {
            main.getCategories();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        System.out.println();
        System.out.println("DB Testing: ");
        main.database = main.mongoDBConnect();
        main.project4Task1();
        /*main.displayDocs( "Test1" );
        main.displayDocs( "Test2" );*/
    }

    public void apiConnect() throws IOException {
        String baseURL = "https://opentdb.com/api.php";
        //maxed out url below if very specific
        //String url = "https://opentdb.com/api.php" + "?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=" + type;
        String url = "https://opentdb.com/api.php?amount=10";
        Document document = Jsoup.connect(url).ignoreContentType(true).get();
        String element = document.text();
        System.out.println(element);
    }
    public String getCategories() throws IOException {
        String url = "https://opentdb.com/api_category.php";
        Document document = Jsoup.connect( url ).ignoreContentType( true ).get();
        String categories = document.text();
        System.out.println(categories);
        return categories;
    }

    // this methodology below found at: https://www.delftstack.com/howto/java/call-rest-api-in-java/
    public void apiGet() throws IOException {
        URL url = new URL("https://opentdb.com/api.php?amount=10");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod( "GET" );
        int responseCode = urlConnection.getResponseCode();
        System.out.println("GET Response code :: " + responseCode);
        if(responseCode == urlConnection.HTTP_OK){
            BufferedReader in = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine = in.readLine())!=null){
                response.append( inLine );
            }
            in.close();
            System.out.println(response.toString());
        }else{
            System.out.println("Error found");
        }
    }
    public String apiGetCategories() throws IOException {
        URL url = new URL("https://opentdb.com/api_category.php");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod( "GET" );
        int responseCode = urlConnection.getResponseCode();
        System.out.println("GET Response code :: " + responseCode);
        if (responseCode == urlConnection.HTTP_OK){
            BufferedReader in = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine=in.readLine())!=null){
                response.append( inLine );
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }else{
            System.out.println("Error found");
            return "Error";
        }
    }


    // below code walkthrough found at: https://www.geeksforgeeks.org/mongodb-tutorial-in-java/
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
        //database = mongoDBConnect();
        database.createCollection( collectionName );
        System.out.println("Collection created.");
    }

    public MongoCollection<org.bson.Document> getDBCollection(String collectionName){
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
        FindIterable<org.bson.Document> iterable = getDBCollection( collectionName ).find();
        Iterator itr = iterable.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }

    public void project4Task1(){
        Scanner scanner = new Scanner( System.in );
        System.out.println("Please pick a collection name: ");
        String collectionName = scanner.nextLine();
        createDBCollection( collectionName );
        System.out.println("Please pick a Document Title: ");
        String docTitle = scanner.nextLine();
        System.out.println("What is your name: ");
        String name = scanner.nextLine();
        addDocToCollection( docTitle, name, collectionName );
        displayDocs( collectionName );
    }
}