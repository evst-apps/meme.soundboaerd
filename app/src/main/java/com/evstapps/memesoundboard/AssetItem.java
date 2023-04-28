package com.evstapps.memesoundboard;

import android.graphics.Bitmap;
import android.view.View;

public class AssetItem implements Comparable<AssetItem>{
    public Bitmap tabIcon;
    public String name;
    public String filePath;
    public int itemPos;
    public int tabPos;
    public View view;

    @Override
    public int compareTo(AssetItem assetItem) {
        return this.name.compareTo(assetItem.name);
    }
}

