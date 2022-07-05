package com.evstapps.memesoundboard;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class EVSTMediaPlayer extends MediaPlayer {

    private final MainActivity mainActivity;

    EVSTMediaPlayer (MainActivity mainActivity){
        this.mainActivity = mainActivity;
        setOnCompletionListener(mediaPlayer -> mainActivity.adManager.ShowAdd(8));
    }


    public void Play (String p){
        try {
            reset();
            AssetFileDescriptor afd = mainActivity.getAssets().openFd(p);
            setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            afd.close();
            prepare();
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
