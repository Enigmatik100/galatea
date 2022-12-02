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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    RadioGroup answerGroup;
    Button validButton, nextQuestionButton;
    int score;
    int responseIndex= -1;
    int numQuestion=0;
    int trueIndex= 0;
    SwipeManager swipeLeft;
    ConstraintLayout clQuiz;
    TextView feedBackTV, questionTV;
    InputStream txtReader;
    StringTokenizer stringTokenizer, quizTokenizer;
    ArrayList<String> questionsArray= new ArrayList<>();
    ArrayList<ArrayList<String>> responsesBank= new ArrayList<>();
    ArrayList<String> linkList= new ArrayList<>();
    ArrayList<RadioButton> rdButtonArray= new ArrayList<>();
    ArrayList<Integer> dataReceived= new ArrayList<>(Arrays.asList(-1, -1));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTV = (TextView) findViewById(R.id.scoreTV);
        feedBackTV= (TextView) findViewById(R.id.fdbckTV);
        validButton= (Button) findViewById(R.id.validButon);
        answerGroup= (RadioGroup) findViewById(R.id.answer_group);
        clQuiz= (ConstraintLayout) findViewById(R.id.clQuiz);
        nextQuestionButton= (Button) findViewById(R.id.nextQuestionButton);
        Bundle dataReceiver= getIntent().getExtras();
        Bundle bundle= getIntent().getBundleExtra("BUNDLE");
        if (dataReceiver != null) {
            score= dataReceiver.getInt("SCORE");
        }
        if (bundle != null) {
            dataReceived= (ArrayList<Integer>) bundle.getSerializable("ARRAYLIST");
            score= dataReceived.get(0);
            numQuestion= dataReceived.get(1);
            System.out.print("SCORE= ");
            System.out.println(score);
            System.out.print("NUM QUESTION= ");
            System.out.println(numQuestion);
        }
        try {
            System.out.println("TEST");
            txtReader = getAssets().open("Question_App_NI.txt");
            int size= txtReader.available();
            byte[] buffer = new byte[size];
            txtReader.read(buffer);
            txtReader.close();
            String myOutput = new String(buffer);
            stringTokenizer= new StringTokenizer(myOutput, "\r\n");
            while (stringTokenizer.hasMoreElements()) {
                boolean isQuestion= true;
                ArrayList<String> responsesArray= new ArrayList<>();
                quizTokenizer= new StringTokenizer(stringTokenizer.nextToken(), "|");
                while (quizTokenizer.hasMoreElements()) {
                    if (isQuestion == true) {
                        questionsArray.add(quizTokenizer.nextToken());
                        isQuestion= false;
                    } else {
                        responsesArray.add(quizTokenizer.nextToken());
                    }
                }
                String link= responsesArray.get(responsesArray.size()-1);
                responsesArray.remove(responsesArray.size()-1);
                link= link.replace(" ", "");
                linkList.add(link);
                responsesBank.add(responsesArray);
            }
        } catch (IOException e) {
            // Exception
            e.printStackTrace();
        }
        displayQuestion(numQuestion);
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
                    if (responseIndex == trueIndex) {
                        feedBackTV.setText("Bonne Réponse");
                        score++;
                    } else {
                        feedBackTV.setText("Mauvaise Réponse");
                    }
                    answerGroup.clearCheck();
                    swipeLeft = new SwipeManager(clQuiz);
                    dataReceived.set(0, score);
                    dataReceived.set(1, numQuestion);
                    swipeLeft.setDataToSend(dataReceived);
                    swipeLeft.setURLToOpen(linkList.get(numQuestion-1));
                    swipeLeft.setSwipeOrientation("left");
                    responseIndex= -1;
                    if (numQuestion >= 10) {
                        Intent intentScore = new Intent(MainActivity.this, ScoreActivity.class);
                        intentScore.putExtra("SCORE", score);
                        startActivity(intentScore);
                    }
                }
            }

        });
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayQuestion(numQuestion);
            }
        });

    }

    public void displayQuestion(int questionIndex) {
        questionTV.setText(questionsArray.get(questionIndex));
        String val= "";
        answerGroup.removeAllViews();
        for (int i = 0; i < responsesBank.get(questionIndex).size(); i++) {
            RadioButton rdVal = new RadioButton(this);
            if (responsesBank.get(questionIndex).get(i).contains(".")) {
                trueIndex= i;
                val= responsesBank.get(questionIndex).get(i).replace(".", "");
            } else  {
                val= responsesBank.get(questionIndex).get(i);
            }
            rdVal.setText(val);
            rdVal.setId(i);
            answerGroup.addView(rdVal);
            rdButtonArray.add(rdVal);
        }
    }

}