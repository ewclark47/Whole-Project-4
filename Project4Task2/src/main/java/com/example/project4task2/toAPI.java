package com.example.project4task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class toAPI {
    String amount; // number from 1 - however many questions you want to see
    String category; // numbered 9-32 by topic
    String difficulty; // easy, medium, hard
    String type; // boolean for True/False and multiple for Multiple Choice

    public void getQuestions(String amount, String category, String difficulty, String type) throws IOException {
        URL url = new URL(
                "https://opentdb.com/api.php" + "?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=" + type
        );
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

    public String getAPICategories() throws IOException {
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
}
