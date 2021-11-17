package com.evstapps.familysoundboard;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;

public class AssetFolder implements Comparable<AssetFolder>{
    public String name;
    public View tabView;
    public Drawable bgImage;
    public ArrayList<AssetItem> assetItems;

    @Override
    public int compareTo(AssetFolder assetFolder) {
        return this.name.compareTo(assetFolder.name);
    }
}
