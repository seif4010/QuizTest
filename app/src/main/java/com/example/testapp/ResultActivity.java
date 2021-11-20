package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewScore;
    private TextView textViewName;
    private TextView textViewId;
    private TextView textViewIncorrect;
    private TextView textViewAttempted;
    private TextView textViewNotAttempted;

    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int s = getIntent().getIntExtra("myscore", 0);
        String name = getIntent().getStringExtra("newName");
        String Id = getIntent().getStringExtra("newId");
        int f =  getIntent().getIntExtra("failed", 0);
        int att = getIntent().getIntExtra("attempt", 0);
        int notatt =  getIntent().getIntExtra("unanswered", 0);

        textViewScore = findViewById(R.id.text_view_score);
        textViewId = findViewById(R.id.text_view_Id);
        textViewName = findViewById(R.id.text_view_name);
        textViewIncorrect = findViewById(R.id.text_view_questions_failed);
        btnHome = findViewById(R.id.button_home);
        textViewAttempted = findViewById(R.id.text_view_questions_attempted);
        textViewNotAttempted = findViewById(R.id.text_view_questions_not_attempted);

        textViewScore.setText("Student's Score: " + s + " / 5");
        textViewName.setText("Student's Name: " + name);
        textViewId.setText("Student's ID: " + Id);
        textViewIncorrect.setText("Questions Failed: " + (f) +" / 5");
        textViewAttempted.setText("Questions Attempted: " + (att) +" / 5");
        textViewNotAttempted.setText("Questions Not Attempted: " + (notatt) +" / 5");

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(ResultActivity.this, HomepageActivity.class);
               startActivity(i);
               finishAffinity();
            }
        });
    }
}