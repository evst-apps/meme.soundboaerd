package com.evstapps.familysoundboard;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AssetFolder implements Comparable<AssetFolder>{
    public String name;
    public String tabName;
    public Bitmap tabIcon;
    public View tabView;
    public ArrayList<AssetItem> assetItems;

    @Override
    public int compareTo(AssetFolder assetFolder) {
        return this.tabName.compareTo(assetFolder.tabName);
    }
}