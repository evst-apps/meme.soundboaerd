package com.evstapps.familysoundboard;

import android.graphics.Bitmap;

public class AssetItem implements Comparable<AssetItem>{
    public Bitmap tabIcon;
    public String name;
    public String filePath;

    @Override
    public int compareTo(AssetItem assetItem) {
        return this.name.compareTo(assetItem.name);
    }
}
