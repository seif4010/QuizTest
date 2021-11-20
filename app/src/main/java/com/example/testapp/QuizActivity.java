package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private TextView textViewQuestionNum;
    private RadioGroup group;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button btnnext;

    private List<Question> questionList;
    private int questioncounter;
    private int questioncounttotal;
    private Question currentQuestion;

    private int score;
    private int answNum;
    private int skipped = 0;
    private int attempted = 0;
    private int failed = 0;
    private boolean answered;
    private String name;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        name = getIntent().getStringExtra("myname");
        Id = getIntent().getStringExtra("myId");

        Button btn_home = findViewById(R.id.button_home);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestionNum = findViewById(R.id.text_view_question_num);
        group = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        btnnext = findViewById(R.id.button_next);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();

        questioncounttotal = questionList.size();

        showNextQuestion();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked()){
                        attempted++;
                        checkAnswer();
                        showNextQuestion();
                    }else {
                        answNum = 5;
                        skipped++;
                        checkAnswer();
                        showNextQuestion();
                    }
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    private void showNextQuestion(){
        group.clearCheck(); // make sure unselected radio buttons
        if(questioncounter < questioncounttotal){
            currentQuestion = questionList.get(questioncounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questioncounter++;
            textViewQuestionNum.setText("Question: " +questioncounter);
            answered = false; // is the question answered
        }else{
            openResults();
        }
    }

    private void checkAnswer(){
        answered=true;
        RadioButton selected = findViewById(group.getCheckedRadioButtonId()); // save picked choice
        answNum = group.indexOfChild(selected) + 1;

        if(answNum == currentQuestion.getAnswerNum()){
            score++;
            //make textview to save score.
        }else{
            failed++;
        }
    }
    private void openResults(){

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("myscore", score);
        intent.putExtra("newName", name);
        intent.putExtra("newId", Id);
        intent.putExtra("unanswered", skipped);
        intent.putExtra("failed", failed);
        intent.putExtra("attempt", attempted);


        sendTextMessage();
        startActivity(intent);
    }
    private void sendTextMessage(){
        try{
            SmsManager txtmsg = SmsManager.getDefault();
            String txt = "Student Name is " +name
                    +"\nRegistration number " +Id +"\nTotal Questions Attempted: " +attempted + " / 5"
                    + "\nStudent score: " +score +" / 5"
                    +"\n Questions Failed " +failed + " / 5"
                    +"\nQuestions not Attempted:" +skipped + " / 5";
            txtmsg.sendTextMessage("+254701699145", null, txt, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}