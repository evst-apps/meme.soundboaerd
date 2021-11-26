package com.evstapps.familysoundboard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Collections;
import java.util.List;

public class AdManager {

    private InterstitialAd mInterstitialAd;
    private int counter = 0;
    private final MainActivity mainActivity;

    public AdManager (MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        List<String> testDeviceIds = Collections.singletonList("FE3DB78168856A22FE19B79204F3D59A");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(mainActivity, initializationStatus -> {
            AdView mAdView = mainActivity.findViewById(R.id.adView);
            mAdView.loadAd(getAdRequest());
            LoadAd();
        });
    }

    public void LoadAd() {
        InterstitialAd.load(mainActivity, mainActivity.getResources().getString(R.string.Ad_Interstitial_ID), getAdRequest(),
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

    public void StepCounter(){
        counter++;
    }

    public void ShowAdd(int minCount){
        if (counter >= minCount) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(mainActivity);
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
