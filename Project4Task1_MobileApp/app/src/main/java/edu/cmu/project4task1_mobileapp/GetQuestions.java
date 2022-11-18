/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
GetQuestions.java
 */

package edu.cmu.project4task1_mobileapp;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class GetQuestions {
    MainActivity main = null;
    String questions; // maybe this is a JSON, not 100% sure yet
    String amount, category, difficulty, type;

    public void search(String amount, String category, String difficulty, String type, Activity activity, MainActivity main){
        this.main = main;
        // construct the other parameters the same as above
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        new BackgroundTask(activity).execute();


    }

    private class BackgroundTask{
        private Activity activity; // The UI thread

        public BackgroundTask(Activity activity) { this.activity = activity;}

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
                questions = search(amount, category, difficulty, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onPostExecute(){
            main.questionsReady(/* the final output we want back in app goes here */);
        }

        private String search(String amount, String category, String difficulty, String type) throws IOException {
            String questions="";
            // do the calling to the api in here
            URL url = new URL("http://localhost:9090/getQuestions");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("amount", amount);
            urlConnection.addRequestProperty("category", category);
            urlConnection.addRequestProperty("difficulty", difficulty);
            urlConnection.addRequestProperty("type", type);
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == urlConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inLine;
                while((inLine = in.readLine()) != null){
                    response.append(inLine);
                }
                in.close();
                System.out.println("Received Questions: ");
                System.out.println(response.toString());
            }
            return questions;
        }
    }
}
