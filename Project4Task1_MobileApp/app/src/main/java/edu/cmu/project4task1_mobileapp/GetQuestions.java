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
import java.util.ArrayList;

public class GetQuestions {
    MainActivity main = null;
    String questions;
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
            main.questionsReady(questions);
        }

        private String search(String amount, String category, String difficulty, String type) throws IOException {
            /*System.out.println("Amount: ");
            System.out.println(amount);
            System.out.println("Category: ");
            System.out.println(category);
            System.out.println("Difficulty: ");
            System.out.println(difficulty);
            System.out.println("Type: ");
            System.out.println(type);*/

            String questions = "";
            String preQ= "";
            // do the calling to the api in here
            URL url = new URL("https://limitless-plains-67143.herokuapp.com/getQuestions?amount="+amount+"&category="+
                    category+"&difficulty="+difficulty+"&type="+type); // Category needs to be a NUMBER
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();
            String message = urlConnection.getResponseMessage();
            if(responseCode == urlConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inLine;
                while((inLine = in.readLine()) != null){
                    response.append(inLine);
                }
                in.close();
                System.out.println("Questions JSON received for use in Write Up: ");
                preQ = response.toString();
                preQ = preQ.replace("\\","").replace("\"\"{\"response_code\":0,\"results\":[", "");
                System.out.println(preQ);
                String [] sepQs = preQ.split("[\\,}]");
                for (String q : sepQs){
                    //System.out.println(q.replace("{", "").replace("}",""));
                    questions += q.replace("{", "").replace("}","") + "\n";
                }
                //System.out.println(questions);
            }
            return questions;
        }
    }
}
