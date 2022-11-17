package edu.cmu.project4task1_mobileapp;

import android.app.Activity;

public class GetQuestions {
    MainActivity main = null;
    String questions; // maybe this is a JSON, not 100% sure yet

    public void search(/*parameters for calling the API for questions will go here*/ Activity activity, MainActivity main){
        this.main = main;
        // construct the other parameters the same as above
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
            questions = search();
        }

        public void onPostExecute(){
            main.questionsReady(/* the final output we want back in app goes here */);
        }

        private String search(/* input the API search parameters here*/){
            String questions="";
            // do the calling to the api in here
            return questions;
        }
    }
}
