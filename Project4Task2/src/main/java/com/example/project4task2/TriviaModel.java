package com.example.project4task2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

public class TriviaModel {
    Gson gson = new Gson();

    public String getAPICategories() throws IOException {
        String respString;
        URL url = new URL( "https://opentdb.com/api_category.php" );
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod( "GET" );
        int responseCode = urlConnection.getResponseCode();
        //System.out.println("GET Response code :: " + responseCode);
        if (responseCode == urlConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine = in.readLine()) != null) {
                response.append( inLine );
            }
            in.close();
            //System.out.println(response);
            response = response.replace( 0, 22, "" );
            response = response.replace( response.length() - 2, response.length(), "" );
            return response.toString();
        } else {
            System.out.println( "Error found in Categories" );
            return "Error";
        }
    }


    public String getQuestions(String amount, String category, String difficulty, String type) throws IOException {
        URL url = new URL(
                "https://opentdb.com/api.php" + "?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=" + type
        );
        /*System.out.println(amount);
        System.out.println(category);
        System.out.println(difficulty);
        System.out.println(type);*/

        String questions = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod( "GET" );
        int responseCode = urlConnection.getResponseCode();
        //System.out.println("GET Response code :: " + responseCode);
        if (responseCode == urlConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine = in.readLine()) != null) {
                response.append( inLine );
            }
            in.close();
            //System.out.println(response.toString());
            questions = response.toString();
        } else {
            System.out.println( "Error found in Questions" );
        }
        return questions;
    }
}

