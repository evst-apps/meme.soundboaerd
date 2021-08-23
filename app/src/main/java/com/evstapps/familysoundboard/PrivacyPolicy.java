package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        WebView wv = findViewById(R.id.web);
        WebSettings webSettings = wv.getSettings();
        webSettings.setDomStorageEnabled(true);
        wv.loadUrl("http://evstapps.infinityfreeapp.com/Family-Soundboard-Privacy-Policy.html");
    }
}