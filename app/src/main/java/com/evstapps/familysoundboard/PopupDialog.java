package com.evstapps.familysoundboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;


public class PopupDialog extends Dialog {

    AssetItem assetItem;

    public PopupDialog(@NonNull Context context, AssetItem assetItem) {
        super(context);
        this.assetItem = assetItem;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        Button button = findViewById(R.id.dialogBtn1);
        if (App.assetFolders.get(0).assetItems.contains(assetItem)){
            button.setText("Remove from Favorites");
        } else {
            button.setText("Add to Favorites");
        }
        button.setOnClickListener(view -> {
            if (App.assetFolders.get(0).assetItems.contains(assetItem)){
                App.assetFolders.get(0).assetItems.remove(assetItem);
            } else {
                App.assetFolders.get(0).assetItems.add(assetItem);
            }
            Test.viewPager2.setAdapter(Test.viewPager2.getAdapter());
            App.saveFavorites();
            this.dismiss();
        });
    }

}
