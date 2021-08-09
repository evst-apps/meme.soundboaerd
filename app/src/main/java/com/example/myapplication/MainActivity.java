package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.le.AdvertisingSetCallback;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

        LoadAd();

        FlexboxLayout flex = findViewById(R.id.flex);
        flex.removeAllViews();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        try {
            for(String s : getAssets().list("Tabs")){
                TabLayout.Tab t = tabs.newTab();
                t.setText(s.substring(s.indexOf(".") +1, s.length()));
                t.setTag(s);
                tabs.addTab(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void Select (TabLayout.Tab tab){
                try {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                        LoadAd();
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }

                    FlexboxLayout flex = findViewById(R.id.flex);
                    flex.removeAllViews();
                    for(String s : getAssets().list("Tabs/" + tab.getTag())){
                        Item item = new Item(flex.getContext(), s, "Tabs/" + tab.getTag());
                        flex.addView(item.btn);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Select(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Select(tab);
            }
        });

        TabLayout.Tab tab = tabs.getTabAt(0);
        tab.select();
    }

    private void LoadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("Ad", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("Ad", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

}
