package com.evstapps.familysoundboard;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AssetFolder implements Comparable<AssetFolder>{
    public String name;
    public Bitmap tabIcon;
    public View tabView;
    public FolderFragment folderFragment;
    public Drawable bgImage;
    public ArrayList<AssetItem> assetItems;

    @Override
    public int compareTo(AssetFolder assetFolder) {
        return this.name.compareTo(assetFolder.name);
    }
}
