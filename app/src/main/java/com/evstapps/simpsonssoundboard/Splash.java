package com.evstapps.simpsonssoundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setContentView(R.layout.layout_splash);

        View playBtn = findViewById(R.id.playBtn);
        TextView playBtnText = playBtn.findViewById(R.id.btnText);
        playBtnText.setText(R.string.Play);
        playBtn.setOnClickListener(view -> {
            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
        });

        View policyBtn = findViewById(R.id.policyBtn);
        TextView policyBtnText = policyBtn.findViewById(R.id.btnText);
        policyBtnText.setText(R.string.PrivacyPolicy);
        policyBtn.setOnClickListener(view -> {
            Intent i = new Intent(Splash.this, Policy.class);
            startActivity(i);
        });

        View shareBtn = findViewById(R.id.shareBtn);
        TextView shareBtnText = shareBtn.findViewById(R.id.btnText);
        shareBtnText.setText(R.string.Share);
        shareBtn.setOnClickListener(view -> {
            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            intent.putExtra(Intent.EXTRA_TEXT,"Check out this cool app https://play.google.com/store/apps/details?id=" + getPackageName());
            intent.setType("text/plain");
            startActivity(intent);
        });

    }
}