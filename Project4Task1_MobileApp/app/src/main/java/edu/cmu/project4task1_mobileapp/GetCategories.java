/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
GetCategories.java
 */

package edu.cmu.project4task1_mobileapp;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetCategories {
    MainActivity main = null;

    public void getCategories(Activity activity, MainActivity main){
        this.main = main;
        new BackgroundTask(activity).execute();
    }

    private class BackgroundTask{
        private Activity activity; // The UI thread
        private ArrayList<String> categories = new ArrayList<>();
        private ArrayList<String> categoryNumbers = new ArrayList<>();

        public BackgroundTask(Activity activity){
            this.activity = activity;
        }

        private void startBackground(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doInBackground();
                    } catch (IOException e) {
                        System.out.println("Error in doInBackground for GetCategories");
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
            }).start();
        }

        private void execute(){
            startBackground();
        }

        private void doInBackground() throws IOException {
                categories = getCategories();
                categoryNumbers = getCategoryNumbers();
        }

        public void onPostExecute(){
            main.categoriesReady(categories, categoryNumbers);
        }

        private ArrayList<String> getCategoryNumbers() throws IOException {
            ArrayList<String> categoryNumberList = new ArrayList<>();
            URL url = new URL("http://10.0.2.2:9090/Project4Task2-1.0-SNAPSHOT/getCategory"); // this will change to heroku url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();
            System.out.println("GET Response code :: " + responseCode);
            if(responseCode == urlConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inLine;
                while((inLine = in.readLine()) != null){
                    response.append(inLine);
                }
                in.close();
                String[] str = response.toString().split(",");
                for(int i=0; i< str.length;i++){
                    if(i==0){
                        //System.out.println(str[i].substring(15, str[i].length()));
                        categoryNumberList.add(str[i].substring(15, str[i].length()));
                    }else if (i%2==0){
                        categoryNumberList.add(str[i].substring(12, str[i].length()));
                        //System.out.println(str[i].substring(12, str[i].length()));
                    }
                }
            }
            return categoryNumberList;
        }

        private ArrayList<String> getCategories() throws IOException {
            ArrayList<String> categoryList = new ArrayList<>();
            URL url = new URL("http://10.0.2.2:9090/Project4Task2-1.0-SNAPSHOT/getCategory"); // this will change to heroku url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == urlConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inLine;
                while ((inLine = in.readLine()) != null) {
                    response.append(inLine);
                }
                in.close();
                System.out.println("Category JSON received for use in Write Up: ");
                System.out.println(response.toString());
                String[] str = response.toString().split(",");
                for (int i = 0; i < str.length; i++) {
                    if (i % 2 != 0) {
                        categoryList.add(str[i].substring(17, str[i].length() - 5));
                    }
                }
            }
            return categoryList;
        }
    }
}
