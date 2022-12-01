package com.example.projet_ni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {
    int score= -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Bundle dataReceiver= getIntent().getExtras();
        if (dataReceiver != null) {
            score= dataReceiver.getInt("SCORE");
        }
        TextView scoreTV= (TextView) findViewById(R.id.scoreTV);
        scoreTV.setText(String.valueOf(score) + " / 20");
    }
}