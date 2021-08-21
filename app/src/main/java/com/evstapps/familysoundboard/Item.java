package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;


public class Item {
    public final Button btn;
    private final String path;
    @SuppressLint("InflateParams")
    public Item(Context context, String n, String p) {
        String name = n.substring(0, n.lastIndexOf('.'));
        path = p + "/" + n;
        btn = (Button) LayoutInflater.from(context).inflate(R.layout.button, null);
        btn.setText(name);
        btn.setOnClickListener(view -> Player.Play(context, path));
    }
}
