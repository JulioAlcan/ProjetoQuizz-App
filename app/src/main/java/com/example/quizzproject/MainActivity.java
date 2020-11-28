package com.example.quizzproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel, questionLabel;
    private Button btn1, btn2, btn3, btn4;

    private int progressBar = 0;
    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"país", "certa resposta", "questao1", "questao2", "questao3"}
                {"China", "Beijing", "Jakarta", "Manila", "Estocolmo"},
            {"India", "Nova Delhi", "Beijing", "Bangkok", "Seoul"},
            {"Indonésia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"},
            {"Japão", "Tóquio", "Bangkok", "Taipei", "Jakarta"},
            {"Tailândia", "Bangkok", "Berlim", "Havana", "Kingston"},
            {"Brasil", "Brasília", "Havana", "Bangkok", "Copenhagen"},
            {"Canadá", "Ottawa", "Berna", "Copenhage", "Jakarta"},
            {"Cuba", "Havana", "Berna", "Londres", "Mexico City"},
            {"México", "Cidade do México", "Ottawa", "Berlim", "Santiago"},
            {"Estados Unidos", "Washington D.C.", "San Jose", "Buenos Aires", "Kuala Lumpur"},
            {"França", "Paris", "Ottawa", "Copenhage", "Tóquio"},
            {"Alemanha", "Berlin", "Copenhage", "Bangkok", "Santiago"},
            {"Itália", "Roma", "Londres", "Paris", "Atenas"},
            {"Espanha", "Madrid", "Cidade do México", "Jakarta", "Havana"},
            {"Inglaterra", "Londres", "Roma", "Paris", "Singapura"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        //recebe categoria
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY",0);
    Log.v("CATEGORY_TAG",quizCategory + "");

        // cria quizArray
        for (int i = 0; i < quizData.length; i++) {

            // Prepare array.
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); // Country
            tmpArray.add(quizData[i][1]); // Right Answer
            tmpArray.add(quizData[i][2]); // Choice1
            tmpArray.add(quizData[i][3]); // Choice2
            tmpArray.add(quizData[i][4]); // Choice3

            // Adiciona tmpArray
            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }

    public void showNextQuiz() {


        // atualiza quizCountLabel.
        countLabel.setText("Capital " + quizCount);

        // Generate random number between 0 and 14 (quizArray's size - 1)
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // pega um
        ArrayList<String> quiz = quizArray.get(randomNum);

        // define resposta certa
        // Array format: {"Country", "Right Answer", "Choice1", "Choice2", "Choice3"}
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        // remove o quiz feito e altera posições
        quiz.remove(0);
        Collections.shuffle(quiz);

        btn1.setText(quiz.get(0));
        btn2.setText(quiz.get(1));
        btn3.setText(quiz.get(2));
        btn4.setText(quiz.get(3));

        // Remove this quiz from quizArray.
        quizArray.remove(randomNum);

        progressBar += 1;
    }

    public void checkAnswer(View view) {

        // Get pushed button.
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // Correct
            alertTitle = "Correto!";
            rightAnswerCount++;

        } else {
            alertTitle = "Errado: ";
        }

        // Create AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Resposta: " + rightAnswer);
        builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // Show Result.
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}