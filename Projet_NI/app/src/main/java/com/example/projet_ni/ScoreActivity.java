package com.example.projet_ni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {
    int score= -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Bundle dataReceiver= getIntent().getExtras();
        TextView scoreTV= (TextView) findViewById(R.id.scoreTV);
        Button returnButton= (Button) findViewById(R.id.returnButton);
        if (dataReceiver != null) {
            score= dataReceiver.getInt("SCORE");
        }
        if (score != -1) {
            if(score <= 3) {
                scoreTV.setText("Il semblerait que tu ne soit pas assez sensibilisé à ce sujet \n tu devrais aller sur le site https://www.sida-info-service.org/ pour apprendre les notions que tu ignore");
            } else if ((score > 3) && (score <= 6)) {
                scoreTV.setText("C'est bien, tu devrais tu devrais aller sur le site https://www.sida-info-service.org/ pour connaître encore plus de chose");
            } else {
                scoreTV.setText("C'est très bien, tu sembles être bien sensibilisé à ce sujet, BRAVO!!");
            }
        }
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= scoreTV.getContext();
                Intent intentQuiz = new Intent(context, MainActivity.class);
                startActivity(intentQuiz);
            }
        });
    }
}