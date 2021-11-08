package com.evstapps.familysoundboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import com.google.android.gms.ads.MobileAds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                assert status != null;
                Log.d("MyApp", String.format(
                        "Adapter name: %s, Description: %s, Latency: %d",
                        adapterClass, status.getDescription(), status.getLatency()));
            }

            AdView mAdView = findViewById(R.id.adView);

            AdRequest adRequest1 = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest1);

            LoadAd();
        });



        FlexboxLayout flex = findViewById(R.id.flex);
        flex.removeAllViews();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        try {
            for(String s : getAssets().list("Tabs")){
                TabLayout.Tab t = tabs.newTab();
                t.setText(s.substring(s.indexOf(".") +1));
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
                    ArrayList<Item> items = new ArrayList<>();
                    for(String s : getAssets().list("Tabs/" + tab.getTag())){
                        Item item = new Item(flex.getContext(), s, "Tabs/" + tab.getTag());
                        items.add(item);
                    }
                    Collections.sort(items);
                    for (Item i : items) {
                        flex.addView(i.view);
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
        if (tab != null) {
            tab.select();
        }
    }

    private void LoadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-7640237869653935/7048678096", adRequest,
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

    @SuppressWarnings("SameReturnValue")
    public boolean SetRingtone(String in){
        if(!Settings.System.canWrite(this)) {
            // If do not have write settings permission then open the Can modify system settings panel.
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(intent);
        } else {
            try {
                String out = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES) + "/EVST_Ringtone.mp3";
                AssetFileDescriptor afd = getAssets().openFd(in);
                FileChannel inChannel = afd.createInputStream().getChannel();
                FileChannel outChannel = new FileOutputStream(out).getChannel();
                inChannel.transferTo(afd.getStartOffset(), afd.getLength(), outChannel);
                inChannel.close();
                outChannel.close();
                SetAsRingtoneOrNotification(new File(out));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void SetAsRingtoneOrNotification(File k) {


        ContentValues values = new ContentValues();

        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri newUri = this.getContentResolver()
                    .insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            try (OutputStream os = getContentResolver().openOutputStream(newUri)) {

                int size = (int) k.length();
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(k));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();

                    os.write(bytes);
                    os.close();
                    os.flush();
                } catch (IOException e) {
                    return;
                }
            } catch (Exception ignored) {
                return;
            }
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE,
                    newUri);

        } else {
            values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());

            Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
                    .getAbsolutePath());

            getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + k.getAbsolutePath() + "\"", null);


            Uri newUri = this.getContentResolver().insert(uri, values);
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE,
                    newUri);

            this.getContentResolver()
                    .insert(MediaStore.Audio.Media.getContentUriForPath(k
                            .getAbsolutePath()), values);

        }


    }

}
