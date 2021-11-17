package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemContainer {
    public final String title;
    public final ArrayList<Item> items;
    public final View tab;
    public Drawable bg;

    @SuppressLint("InflateParams")
    public ItemContainer(String t, Context ctx) {
        tab = LayoutInflater.from(ctx).inflate(R.layout.layout_tab, null);
        title = t.substring(t.indexOf('.') + 1);
        items = new ArrayList<>();
        TextView text = tab.findViewById(R.id.tabText);
        text.setText(title);
    }

    public void SetIcon(Bitmap i){
        ImageView image = tab.findViewById(R.id.icon);
        image.setImageBitmap(i);
    }
}
