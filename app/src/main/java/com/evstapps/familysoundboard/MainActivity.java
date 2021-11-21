package com.evstapps.familysoundboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static ViewPager2 viewPager2;
    public static AdManager adManager;
    public static EVSTRingtoneManager evstRingtoneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setContentView(R.layout.layout_test);

        adManager = new AdManager(this);
        evstRingtoneManager = new EVSTRingtoneManager(this);

        viewPager2 = findViewById(R.id.pager);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(App.assetFolders.get(position).tabView)).attach();
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }

}