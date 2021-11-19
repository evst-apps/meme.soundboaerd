package com.evstapps.familysoundboard;

import android.graphics.Bitmap;

public class AssetItem implements Comparable<AssetItem>{
    public Bitmap tabIcon;
    public String name;
    public String filePath;
    public int itemPos;
    public int tabPos;

    @Override
    public int compareTo(AssetItem assetItem) {
        return this.name.compareTo(assetItem.name);
    }
}
