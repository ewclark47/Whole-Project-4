package edu.cmu.project4task1_mobileapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

public class TriviaModel {
    Gson gson = new Gson();
    HashMap<String, Integer> catMap = new HashMap<>();

    public String getAPICategories() throws IOException {
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


    public String getQuestions(String amount, String category, String difficulty, String type) throws IOException {
        URL url = new URL(
                "https://opentdb.com/api.php" + "?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=" + type
        );
        String questions = null;
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
            System.out.println(response.toString());
            questions = response.toString();
        } else {
            System.out.println("Error found");
        }
    return questions;
    }

    private String interestingPictureSize(String pictureURL, String picSize) {
        int finalDot = pictureURL.lastIndexOf(".");
        /*
         * From the flickr online documentation, an underscore and a letter
         * before the final "." and file extension is a size indicator.
         * "_m" for small and "-z" for big.
         */
        String sizeLetter = (picSize.equals("mobile")) ? "m" : "z";
        if (pictureURL.indexOf("_", finalDot-2) == -1) {
            // If the URL currently did not have a _? size indicator, add it.
            return (pictureURL.substring(0, finalDot) + "_" + sizeLetter
                    + pictureURL.substring(finalDot));
        } else {
            // Else just change it
            return (pictureURL.substring(0, finalDot - 1) + sizeLetter
                    + pictureURL.substring(finalDot));
        }
    }

    /*
     * Make an HTTP request to a given URL
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }
}
