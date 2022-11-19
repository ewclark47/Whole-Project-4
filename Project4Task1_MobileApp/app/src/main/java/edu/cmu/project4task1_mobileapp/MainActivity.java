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

public class MainActivity extends AppCompatActivity {

    MainActivity me = this;
    EditText amount;
    Spinner category, difficulty, type;
    TextView questions;
    Button submitButton;
    String a,c,d,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity ma = this;

        // I think we need a GetCategories class that runs in background (similar to GetQuestions)
        // and that class has a call here to get the categories to populate the spinner
        GetCategories gc = new GetCategories();
        gc.getCategories(me, ma);

        amount = findViewById(R.id.editTextNumberOfQuestions);
        category = findViewById(R.id.spinnerCategory);
        // location of finding how to add items to a static spinner:
        // https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        difficulty = findViewById(R.id.spinnerDifficulty);
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

        public void categoriesReady(ArrayList<String> categoryList, ArrayList<String> categoryNumberList){
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(categoryAdapter);
            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    c = categoryNumberList.get(i);
                    // Verify selection is actually registering
                    //Toast.makeText(adapterView.getContext(), "Selected: " + c, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        public void questionsReady(String q){
            questions.setText(q);
            questions.setVisibility(View.VISIBLE);
        }

}