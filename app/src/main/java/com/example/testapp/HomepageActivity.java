package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity {

    private String studname;
    private String studId;
    private EditText Student_name;
    private EditText Student_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_submit = findViewById(R.id.button_ok);
        Student_name = findViewById(R.id.text_view_name);
        Student_id = findViewById(R.id.text_view_id);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Student_name.length() == 0 || Student_id.length() == 0){
                   Toast.makeText(HomepageActivity.this,"Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    studname = Student_name.getText().toString();
                    studId = Student_id.getText().toString();
                    startQuiz();
                }


            }
        });
    }

    private void startQuiz(){
        Intent intent = new Intent(HomepageActivity.this, QuizActivity.class);
        intent.putExtra("myname", studname);
        intent.putExtra("myId", studId);
        startActivity(intent);
    }
}