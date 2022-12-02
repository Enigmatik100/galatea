package com.example.projet_ni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    SwipeManager swipeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ConstraintLayout clWeb= (ConstraintLayout) findViewById(R.id.clWeb);
        Bundle dataReceiver= getIntent().getExtras();
        if (dataReceiver != null) {
            String link= dataReceiver.getString("LINK");
            webView= (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(link);
        }
        swipeManager = new SwipeManager(webView);
        swipeManager.setSwipeOrientation("right");
    }
}