package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.playBtn).setOnClickListener(view -> {
            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
        });
        findViewById(R.id.policyBtn).setOnClickListener(view -> {
            Intent i = new Intent(Splash.this, PrivacyPolicy.class);
            startActivity(i);
        });
        findViewById(R.id.shareBtn).setOnClickListener(view -> {
            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Family Soundboard");
            intent.putExtra(Intent.EXTRA_TEXT,"Check out this cool app https://play.google.com/store/apps/details?id=com.evstapps.familysoundboard");
            intent.setType("text/plain");
            startActivity(intent);
        });

    }
}