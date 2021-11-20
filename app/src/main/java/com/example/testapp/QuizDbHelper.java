package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.testapp.QuizConstants.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ExamQuestions";
    private static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE "+
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COULUMN_QUESTION + " TEXT, " +
                QuestionsTable.COULUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COULUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COULUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COULUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COULUMN_ANSWER_NUM + " INTEGER, " +
                QuestionsTable.COULUMN_EMPTY + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }

    private void fillQuestionTable(){
        Question q1 = new Question("The location on a windows operating system where all deleted files are stored is", "A. my computer", "B. my documents", "C. the recycle bin", "D. the task bar", 3, 0);
        addQuestion(q1);
        Question q2 = new Question("When you draw a plan for a house, you would make use of the following software", "A. database", "B. computer aided design", "C. computer graphics", "D. spreadsheet", 2, 0);
        addQuestion(q2);
        Question q3 = new Question("In which of the following is batch processing used?", "A. booking a holiday", "B. checking stolen vehicles", "C. controlling of traffic lights", "D. producing a company payroll", 4, 0);
        addQuestion(q3);
        Question q4 = new Question("Virtual memory is", "A. an extremely large main memory", "B. an extremely large secondary memory", "C. an illusion of extremely large main memory", "D. a type of memory used in super computers", 3, 0);
        addQuestion(q4);
        Question q5 = new Question("Multiprogramming systems", "A. are easier to develop than single programming systems", "B. are used only on large main frame computers", "C. execute each job faster", "D. run several programs at the same time", 4, 0);
        addQuestion(q5);
    }
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COULUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COULUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COULUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COULUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COULUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COULUMN_ANSWER_NUM, question.getAnswerNum());
        cv.put(QuestionsTable.COULUMN_EMPTY, question.getAnswerEmpty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COULUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COULUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COULUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COULUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COULUMN_OPTION4)));
                question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COULUMN_ANSWER_NUM)));
                question.setAnswerEmpty(c.getInt(c.getColumnIndex(QuestionsTable.COULUMN_EMPTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
