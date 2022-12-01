package com.example.projet_ni;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView questionTV = (TextView) findViewById(R.id.questionTV);
        validButton= (Button) findViewById(R.id.validButon);
        answerGroup= (RadioGroup) findViewById(R.id.answer_group);

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View answerButton = answerGroup.findViewById(checkedId);
                responseIndex = answerGroup.indexOfChild(answerButton);
            }
        });
        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseIndex != -1) {
                    numQuestion++;
                    if (responseIndex == 0) {
                        System.out.println("Succes");
                        score++;
                    } else {
                        System.out.println("Not Good");
                    }
                    int count = answerGroup.getChildCount();
                    for (int i=0;i<count;i++) {
                        RadioButton rdButton = (RadioButton) answerGroup.getChildAt(i);
                        rdButton.setChecked(false);
                    }
                    responseIndex= -1;
                    if (numQuestion >= 5) {
                        Intent intentScore = new Intent(MainActivity.this, ScoreActivity.class);
                        startActivity(intentScore);
                        //intentScore.putExtra("SCORE", score);
                        //startActivity(intentScore);
                    }
                }
            }
        });

    }
}