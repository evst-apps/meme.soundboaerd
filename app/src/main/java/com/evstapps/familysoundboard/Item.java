package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class Item implements Comparable<Item> {
    public final View view;
    private final String path;
    private final TextView textView;
    @SuppressLint("InflateParams")
    public Item(Context context, String n, String p) {
        String name = n.substring(0, n.lastIndexOf('.'));
        path = p + "/" + n;
        view = (View) LayoutInflater.from(context).inflate(R.layout.activity_button, null);
        textView = view.findViewById(R.id.textView);
        textView.setText(name);
        view.setOnClickListener(view -> Player.Play(context, path));
    }

    @Override
    public int compareTo(Item item) {
        return (int) (this.textView.getText().toString().compareTo(item.textView.getText().toString()));
    }
}
