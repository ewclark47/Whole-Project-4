/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
MainActivity.java
 */

package edu.cmu.project4task1_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    MainActivity me = this;
    EditText amount;
    Spinner category, difficulty, type;
    TextView questions;
    Button submitButton;
    String a,c,d,t;

    // main activity structure based on the Android Lab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // I think we need a GetCategories class that runs in background (similar to GetQuestions)
        // and that class has a call here to get the categories to populate the spinner
        //GetCategories gc = new GetCategories();
        //gc.getCategories(me, ma); // this became an issue when deployed to the cloud.
        // It was running fine locally and automatically populating the Category dropdown menu
        // but when deployed to heroku the layout was loading before the completion of the
        // connection to the heroku webpage to get it to call the API

        ArrayList<String> categoryList = new ArrayList<>();
        String categoryString = "General Knowledge,Entertainment: Books,Entertainment: Film,Entertainment: Musicals & Theatres,Entertainment: Television,Entertainment: Video Games,Entertainment: Board Games,Science & Nature,Science: Computers,Science: Mathematics,Mythology,Sports,Geography,History,Politics,Art,Celebrities,Animals,Vehicles,Entertainment: Comics,Science: Gadgets,Entertainment: Japanese Anime & Manga,Entertainment: Cartoon & Animations";
        ArrayList<String> categoryNumberList = new ArrayList<>();
        String categoryNumberString = "9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32";
        categoryList.addAll(Arrays.asList(categoryString.split(",")));
        categoryNumberList.addAll(Arrays.asList(categoryNumberString.split(",")));

        final MainActivity ma = this;

        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editTextNumberOfQuestions);
        category = findViewById(R.id.spinnerCategory);
        // location of finding how to add items to a static spinner:
        // https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        difficulty = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                c = categoryNumberList.get(i);
                // Verify selection is actually registering
                //Toast.makeText(adapterView.getContext(), "Selected: " + c, Toast.LENGTH_SHORT).show();
            };

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            };
        });
        ArrayList<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Medium");
        difficultyList.add("Hard");
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficultyList);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);
        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                d = adapterView.getItemAtPosition(i).toString().toLowerCase();
                // Verify selection is actually registering
                //Toast.makeText(adapterView.getContext(), "Selected: " + d, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        type = findViewById(R.id.spinnerQuestionType);
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Multiple Choice");
        typeList.add("True/False");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() == "Multiple Choice") {
                    t = "multiple";
                }else{
                    t = "boolean";
                }
                // Verify selection is actually registering
                //Toast.makeText(adapterView.getContext(), "Selected: " + t, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        questions = findViewById(R.id.questionsView);
        submitButton = findViewById(R.id.buttonSubmit);

        // used the following video tutorial for figuring out how to verify inputs are registering:
        // https://www.youtube.com/watch?v=xPi-z3nOcn8
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                a = amount.getText().toString();
                // Verify selection is actually registering
                //Toast.makeText(MainActivity.this, "You have chose: " + d + " " + t + " " + a, Toast.LENGTH_LONG).show();
                GetQuestions gq = new GetQuestions();
                gq.search(a, c, d, t, me, ma);

            }
        });
    }

        /*public void categoriesReady(ArrayList<String> categoryList, ArrayList<String> categoryNumberList){
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(categoryAdapter);
            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    c = categoryNumberList.get(i);
                    // Verify selection is actually registering
                    //Toast.makeText(adapterView.getContext(), "Selected: " + c, Toast.LENGTH_SHORT).show();
                };

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                };
            });
        }*/

        public void questionsReady(String q){
            questions.setText(q);
            questions.setVisibility(View.VISIBLE);
        }

}