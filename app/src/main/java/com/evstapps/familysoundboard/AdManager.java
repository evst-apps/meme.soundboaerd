package com.evstapps.familysoundboard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdManager {

    private InterstitialAd mInterstitialAd;
    private int counter = 0;
    private final MainActivity mainActivity;
    private AdView mAdView;

    public AdManager (MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        MobileAds.initialize(mainActivity, initializationStatus -> {
            mAdView = mainActivity.findViewById(R.id.adView);
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
        mAdView.loadAd(getAdRequest());
    }

    public void StepCounter(){
        counter++;
    }

    public void ShowAdd(int minCount){
        if (counter >= minCount) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(mainActivity);
                counter = 0;
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            LoadAd();
        }
    }



    public AdRequest getAdRequest(){
        return new AdRequest.Builder().build();
    }
}
