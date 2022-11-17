package edu.cmu.project4task1_mobileapp;

import android.app.Activity;

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
            categories = getCategories();
        }

        public void onPostExecute(){
            main.categoriesReady(categories);
        }

        private ArrayList<String> getCategories(){
            ArrayList<String> categoryList = new ArrayList<String>();
            categoryList.add("This is test 1");
            categoryList.add("This is test 2");
            categoryList.add("This is test 3");
            categoryList.add("This is test 4");
            categoryList.add("This is test 5");
            return categoryList;
        }
    }
}
