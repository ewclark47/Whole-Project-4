package edu.cmu.project4task1_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    MainActivity me = this;
    EditText amount;
    Spinner category, difficulty, type;
    TextView questions;
    Button submitButton;
    String a,c,d,t;
    String myURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity ma = this;

        // I think we need a GetCategories class that runs in background (similar to GetQuestions)
        // and that class has a call here to get the categories to populate the spinner
        GetCategories gc = new GetCategories();
        gc.getCategories(me, ma);

        amount = (EditText) findViewById(R.id.editTextNumberOfQuestions);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        // location of finding how to add items to a static spinner:
        // https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        difficulty = (Spinner) findViewById(R.id.spinnerDifficulty);
        ArrayList<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Medium");
        difficultyList.add("Hard");
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, difficultyList);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);
        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                d = adapterView.getItemAtPosition(i).toString();
                // Verify selection is actually registering
                Toast.makeText(adapterView.getContext(), "Selected: " + d, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        type = (Spinner) findViewById(R.id.spinnerQuestionType);
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Multiple Choice");
        typeList.add("True/False");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t = adapterView.getItemAtPosition(i).toString();
                // Verify selection is actually registering
                Toast.makeText(adapterView.getContext(), "Selected: " + t, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        questions = (TextView) findViewById(R.id.questionsView);
        submitButton = (Button) findViewById(R.id.buttonSubmit);

        // used the following video tutorial for figuring out how to verify inputs are registering:
        // https://www.youtube.com/watch?v=xPi-z3nOcn8
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                a = amount.getText().toString();
                // Verify selection is actually registering
                Toast.makeText(MainActivity.this, "You have chose: " + d + " " + t + " " + a, Toast.LENGTH_LONG).show();
                GetQuestions gq = new GetQuestions();
                gq.search(/*params will go here*/ me, ma);

            }
        });
    }

        public void categoriesReady(ArrayList<String> categoryList){
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(categoryAdapter);
            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    c = adapterView.getItemAtPosition(i).toString();
                    // Verify selection is actually registering
                    Toast.makeText(adapterView.getContext(), "Selected: " + c, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        public void questionsReady(){
            questions.setText("This will be questions");
            questions.setVisibility(View.VISIBLE);
        }

}