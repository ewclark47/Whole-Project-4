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
import java.net.URL;
import java.util.ArrayList;

public class GetCategories {
    MainActivity main = null;
    ArrayList<String> categories;

    public void getCategories(Activity activity, MainActivity main){
        this.main = main;
        new BackgroundTask(activity).execute();
    }

    private class BackgroundTask{
        private Activity activity; // The UI thread

        public BackgroundTask(Activity activity){
            this.activity = activity;
        }

        private void startBackground(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doInBackground();
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

        private void doInBackground(){
            try {
                categories = getCategories();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onPostExecute(){
            main.categoriesReady(categories);
        }

        private ArrayList<String> getCategories() throws IOException {
            ArrayList<String> categoryList = new ArrayList<>();
            URL url = new URL("http://localhost:9090/getCategories");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == urlConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inLine;
                while((inLine = in.readLine()) != null){
                    response.append(inLine);
                }
                in.close();
                System.out.println("Received from Web Service: ");
                System.out.println(response.toString());
            }
            categoryList.add("This is test 1");
            categoryList.add("This is test 2");
            categoryList.add("This is test 3");
            categoryList.add("This is test 4");
            categoryList.add("This is test 5");
            return categoryList;
        }
    }
}
