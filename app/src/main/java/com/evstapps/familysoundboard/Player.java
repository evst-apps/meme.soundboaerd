package com.evstapps.familysoundboard;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class Player {

    public static MediaPlayer mp = new MediaPlayer();

    public static void Play (Context c, String p){
        try {
            mp.reset();
            AssetFileDescriptor afd = c.getAssets().openFd(p);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            afd.close();
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
