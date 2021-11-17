package com.evstapps.familysoundboard;


import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unity3d.scar.adapter.common.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class App extends Application {

    public static ArrayList<AssetFolder> assetFolders;

    @SuppressLint("InflateParams")
    @Override
    public void onCreate() {
        super.onCreate();
        assetFolders = new ArrayList<>();
        AssetFolder assetFolderF = new AssetFolder();
        assetFolderF.tabName = "0.Favorites";
        assetFolderF.name = "Favorites";
        assetFolderF.assetItems = new ArrayList<>();

        assetFolderF.tabIcon = BitmapFactory.decodeResource(getResources(),R.drawable.star);
        View tabViewF = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView tabTextF = tabViewF.findViewById(R.id.tabText);
        ImageView tabIconF = tabViewF.findViewById(R.id.tabImage);
        tabTextF.setText(assetFolderF.name);
        tabIconF.setImageBitmap(assetFolderF.tabIcon);
        assetFolderF.tabView = tabViewF;
        assetFolders.add(assetFolderF);


        try {
            for (String tabName : getAssets().list("Tabs")) {
                AssetFolder assetFolder = new AssetFolder();
                assetFolder.tabName = tabName;
                assetFolder.name = tabName.substring(tabName.indexOf(".") + 1);
                assetFolder.assetItems = new ArrayList<>();

                InputStream icon_is = getAssets().open("Tabs/" + tabName + "/" + "icon.png");
                assetFolder.tabIcon = BitmapFactory.decodeStream(icon_is);
                View tabView = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
                TextView tabText = tabView.findViewById(R.id.tabText);
                ImageView tabIcon = tabView.findViewById(R.id.tabImage);
                tabText.setText(assetFolder.name);
                tabIcon.setImageBitmap(assetFolder.tabIcon);
                assetFolder.tabView = tabView;
                icon_is.close();

                for (String name : getAssets().list("Tabs/" + tabName)) {
                    if (name.contains(".mp3")) {
                        AssetItem assetItem = new AssetItem();
                        assetItem.name = name.substring(0, name.indexOf("."));
                        assetItem.filePath = "Tabs/" + tabName + "/" + name;
                        assetItem.tabIcon = assetFolder.tabIcon;
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
        try{
            File dir = new File(this.getFilesDir(), "EVSTApps");
            if(!dir.exists()){
                dir.mkdir();
                File file = new File(dir, "favorites.txt");
                Log.i("test", file.getAbsolutePath());
                FileWriter writer = new FileWriter(file);
                writer.append("1-1");
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
        FileInputStream is;
        BufferedReader reader;
        File dir = new File(this.getFilesDir(), "EVSTApps");
        File file = new File(dir, "favorites.txt");
        if (file.exists()) {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while(line != null){
                Log.d("StackOverflow", line);
                line = reader.readLine();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
