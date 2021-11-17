package com.evstapps.familysoundboard;


import android.app.Application;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class App extends Application {

    public static ArrayList<AssetFolder> assetFolders;

    @Override
    public void onCreate() {
        super.onCreate();
        assetFolders = new ArrayList<>();
        try {
            for (String tabName : getAssets().list("Tabs")) {
                AssetFolder assetFolder = new AssetFolder();
                assetFolder.name = tabName;
                assetFolder.assetItems = new ArrayList<>();

                InputStream icon_is = getAssets().open("Tabs/" + tabName + "/" + "icon.png");
                assetFolder.tabIcon = BitmapFactory.decodeStream(icon_is);
                icon_is.close();

                InputStream bg_is = getAssets().open("Tabs/" + tabName + "/" + "bg.png");
                assetFolder.bgImage = Drawable.createFromStream(bg_is, null);
                bg_is.close();

                for (String name : getAssets().list("Tabs/" + tabName)) {
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
