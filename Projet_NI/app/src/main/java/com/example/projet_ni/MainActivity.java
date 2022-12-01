package com.example.projet_ni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import  android.view.View;

public class MainActivity extends AppCompatActivity {
    RadioGroup answerGroup;
    Button validButton;
    int score;
    int responseIndex= -1;
    int numQuestion=0;
    SwipeManager swipeLeft;
    ConstraintLayout clQuiz;
    TextView feedBackTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView questionTV = (TextView) findViewById(R.id.questionTV);
        feedBackTV= (TextView) findViewById(R.id.fdbckTV);
        validButton= (Button) findViewById(R.id.validButon);
        answerGroup= (RadioGroup) findViewById(R.id.answer_group);
        clQuiz= (ConstraintLayout) findViewById(R.id.clQuiz);
        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View answerButton = answerGroup.findViewById(checkedId);
                responseIndex = answerGroup.indexOfChild(answerButton);
                System.out.println(responseIndex);
            }
        });
        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseIndex != -1) {
                    numQuestion++;
                    if (responseIndex == 0) {
                        feedBackTV.setText("Bonne Réponse");
                        score++;
                    } else {
                        feedBackTV.setText("Mauvaise Réponse");
                    }
                    answerGroup.clearCheck();
                    swipeLeft = new SwipeManager(clQuiz);
                    swipeLeft.setSwipeOrientation("left");
                    responseIndex= -1;
                    if (numQuestion >= 5) {
                        Intent intentScore = new Intent(MainActivity.this, ScoreActivity.class);
                        intentScore.putExtra("SCORE", score);
                        startActivity(intentScore);
                    }
                }
            }
        });

    }
}