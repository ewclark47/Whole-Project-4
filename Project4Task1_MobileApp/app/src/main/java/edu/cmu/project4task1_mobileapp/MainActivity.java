package edu.cmu.project4task1_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    MainActivity ma = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity me = this;

        Button submitButton = (Button)findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam){
                //insert the stuff here to show the actual questions
                //getQuestions gq = new getQuestions();
            }
        });

        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerCategory);

    }

}