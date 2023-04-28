package com.evstapps.memesoundboard;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

public class AdManager {
    private final ConsentInformation consentInformation;
    private InterstitialAd mInterstitialAd;
    private int counter = 0;
    private final MainActivity mainActivity;
    private AdView mAdView;

    public AdManager (MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(mainActivity);

        // consentInformation.reset();

        consentInformation.requestConsentInfoUpdate(
                mainActivity,
                params,
                () -> {
                    if (consentInformation.isConsentFormAvailable()) {
                        loadForm();
                    }
                },
                formError -> {
                    // Handle the error.
                });

        MobileAds.initialize(mainActivity, initializationStatus -> {
            mAdView = mainActivity.findViewById(R.id.adView);
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
                counter = 0;
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            LoadAd();
        }
    }
    @SuppressLint("VisibleForTests")
    public AdRequest getAdRequest(){
        return new AdRequest.Builder().build();
    }

    public void loadForm() {
        UserMessagingPlatform.loadConsentForm(
                mainActivity,
                consentForm -> {
                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                mainActivity,
                                formError -> {
                                });
                    }
                },
                formError -> {
                }
        );
    }
}
