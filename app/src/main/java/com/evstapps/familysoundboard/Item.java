package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class Item implements Comparable<Item> {
    public final View view;
    private final TextView textView;
    @SuppressWarnings("CommentedOutCode")
    @SuppressLint("InflateParams")
    public Item(Context ctx, String n, String p) {
        String name = n.substring(0, n.lastIndexOf('.'));
        @SuppressWarnings("unused") String path = p + "/" + n;
        view = LayoutInflater.from(ctx).inflate(R.layout.activity_button, null);
        textView = view.findViewById(R.id.textView);
        textView.setText(name);
        //MainActivity ma = (MainActivity) ctx;
        //view.setOnClickListener(view -> Player.Play(ctx, path));
        //view.setOnLongClickListener(view -> ma.SetRingtone(path));
    }

    @Override
    public int compareTo(Item item) {
        return this.textView.getText().toString().compareTo(item.textView.getText().toString());
    }
}
