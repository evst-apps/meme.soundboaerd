package com.evstapps.familysoundboard;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class AssetList {
    public ArrayList<AssetFolder> assetFolders;

      AssetList(Test test){
        assetFolders = new ArrayList<>();
        try {
            for(String tab : test.getAssets().list("Tabs")){
                AssetFolder assetFolder = new AssetFolder();
                assetFolder.name = tab;
                assetFolder.assetItems = new ArrayList<>();

                InputStream icon_is = test.getAssets().open("Tabs/" + tab + "/" + "icon.png");
                View tabView = test.getLayoutInflater().inflate(R.layout.layout_tab, null);
                ImageView imageView = tabView.findViewById(R.id.tabImage);
                TextView textView = tabView.findViewById(R.id.tabText);
                imageView.setImageBitmap(BitmapFactory.decodeStream(icon_is));
                textView.setText(assetFolder.name);
                assetFolder.tabView = tabView;
                icon_is.close();

                InputStream bg_is = test.getAssets().open("Tabs/" + tab + "/" + "bg.png");
                assetFolder.bgImage = Drawable.createFromStream(bg_is, null);
                bg_is.close();

                for(String name : test.getAssets().list("Tabs/" + tab)){
                    if (name.contains(".mp3")) {
                        AssetItem assetItem = new AssetItem();
                        assetItem.name = name;
                        assetFolder.assetItems.add(assetItem);
                    }
                }
                Collections.sort(assetFolder.assetItems);
                assetFolders.add(assetFolder);
            }
            Collections.sort(assetFolders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
