package com.evstapps.familysoundboard;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.List;

public class AdManager {

    private InterstitialAd mInterstitialAd;
    private int counter = 0;
    private Activity activity;

    public AdManager (Activity activity) {
        this.activity = activity;

        List<String> testDeviceIds = Arrays.asList("FE3DB78168856A22FE19B79204F3D59A");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                AdView mAdView = activity.findViewById(R.id.adView);
                mAdView.loadAd(getAdRequest());
                LoadAd();
            }
        });
    }

    public void LoadAd() {
        InterstitialAd.load(activity, "ca-app-pub-7640237869653935/7048678096", getAdRequest(),
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

    public void ShowAdd(){
        if (counter < 5) {
            counter++;
        } else {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(activity);
                LoadAd();
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            counter = 0;
        }
    }

    public AdRequest getAdRequest(){
        return new AdRequest.Builder().build();
    }
}
