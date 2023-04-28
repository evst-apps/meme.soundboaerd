package com.evstapps.memesoundboard;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class EVSTMediaPlayer extends MediaPlayer {

    private final MainActivity mainActivity;
    private AssetItem playingAssetItem = null;

    EVSTMediaPlayer (MainActivity mainActivity){
        this.mainActivity = mainActivity;
        setOnCompletionListener(mediaPlayer -> Stop());
    }

    private void Stop(){
        if (playingAssetItem != null) {
            playingAssetItem.view.findViewById(R.id.stop).setAlpha(0);
            playingAssetItem = null;
        }
        mainActivity.adManager.ShowAdd(10);
        if (isPlaying()) {
            stop();
        }
    }

    public void Play (AssetItem assetItem){
        if (playingAssetItem == assetItem) {
            Stop();
        } else {
            mainActivity.adManager.StepCounter();
            if (playingAssetItem != null) {
                playingAssetItem.view.findViewById(R.id.stop).setAlpha(0);
            }
            playingAssetItem = assetItem;
            playingAssetItem.view.findViewById(R.id.stop).setAlpha(1);
            try {
                reset();
                AssetFileDescriptor afd = mainActivity.getAssets().openFd(assetItem.filePath);
                setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                prepare();
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}