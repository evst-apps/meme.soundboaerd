package com.evstapps.memesoundboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("SetJavaScriptEnabled")
public class More extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setContentView(R.layout.layout_policy);
        WebView wv = findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadUrl("https://play.google.com/store/apps/dev?id=8541077610189854253");
    }
}