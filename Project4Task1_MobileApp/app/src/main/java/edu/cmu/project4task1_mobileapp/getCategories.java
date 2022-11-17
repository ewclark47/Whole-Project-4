package edu.cmu.project4task1_mobileapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;

public class getCategories {
    MainActivity ma = null;
    HashMap<String, Integer> catMap = new HashMap<>();

    public HashMap<String, Integer> doGetCategories() throws IOException {
        URL url = new URL("http://localhost:9090/Project4Task2-1.0-SNAPSHOT/getCategories");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == urlConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine = in.readLine()) != null) {
                response.append(inLine);
            }
            in.close();
            System.out.println("Response: ");
            System.out.println(response);
        }
        return catMap;
    }

    /*public String getAPICategories() throws IOException {
        URL url = new URL("https://opentdb.com/api_category.php");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();
        System.out.println("GET Response code :: " + responseCode);
        if (responseCode == urlConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inLine;
            while ((inLine = in.readLine()) != null) {
                response.append(inLine);
            }
            in.close();
            System.out.println(response);
            response = response.replace( 0,22, "");
            response = response.replace( response.length()-2,response.length(), "" );
            return response.toString();
        } else {
            System.out.println("Error found");
            return "Error";
        }
    }

    public TreeMap<String, Integer> mapCategories(String str){
        String[] splitString = str.split( "}," );
        String longId;
        int id=0;
        String name;
        String trash;
        for(String line: splitString){
            line = line.replace( "{","" ).replace( "}","" );
            longId = line.split(",")[0];
            name = line.split(",")[1];
            id = Integer.parseInt( longId.split( ":" )[1] );
            name = name.split( ":{1}" )[1];
            catMap.put(name.replace( "\"","" ),id);
            String[] parts = line.split( "," );
        }
        return catMap;
    }*/
}
