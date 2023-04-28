package com.evstapps.memesoundboard;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class App extends Application {

    public static ArrayList<AssetFolder> assetFolders;
    private static File filesDir;

    @Override
    public void onCreate() {
        super.onCreate();
        assetFolders = new ArrayList<>();
        filesDir = this.getFilesDir();
        AssetFolder assetFolderF = new AssetFolder();
        assetFolderF.tabName = "0.Favorites";
        assetFolderF.name = "Favorites";
        assetFolderF.assetItems = new ArrayList<>();

        assetFolderF.tabIcon = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        @SuppressLint("InflateParams") View tabViewF = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
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
                @SuppressLint("InflateParams") View tabView = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
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

        for (int a = 1; a < assetFolders.size(); a++) {
            for (int b = 0; b < assetFolders.get(a).assetItems.size(); b++) {
                assetFolders.get(a).assetItems.get(b).tabPos = a;
                assetFolders.get(a).assetItems.get(b).itemPos = b;
            }
        }

        try {
            File dir = new File(this.getFilesDir(), "EVSTApps");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, "favorites.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream is;
            BufferedReader reader;
            File dir = new File(this.getFilesDir(), "EVSTApps");
            File file = new File(dir, "favorites.txt");
            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String l1 = reader.readLine();
                String l2 = reader.readLine();
                while (l1 != null && l2 != null) {
                    AssetItem assetItem1 = App.assetFolders.get(Integer.parseInt(l1)).assetItems.get(Integer.parseInt(l2));
                    AssetItem assetItem2 = new AssetItem();
                    assetItem2.name = assetItem1.name;
                    assetItem2.filePath = assetItem1.filePath;
                    assetItem2.tabIcon = assetItem1.tabIcon;
                    assetItem2.tabPos = assetItem1.tabPos;
                    assetItem2.itemPos = assetItem1.itemPos;
                    App.assetFolders.get(0).assetItems.add(assetItem2);
                    l1 = reader.readLine();
                    l2 = reader.readLine();
                }
                reader.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveFavorites(){
        try {
            File dir = new File(filesDir, "EVSTApps");
            File file = new File(dir, "favorites.txt");
            FileOutputStream fOut = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fOut));
            for (AssetItem assetItem : App.assetFolders.get(0).assetItems){
                writer.write(String.valueOf(assetItem.tabPos));
                writer.newLine();
                writer.append(String.valueOf(assetItem.itemPos));
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
