package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class Item {
    public Button btn;
    private String name;
    private String path;
    public Item(Context context, String n, String p) {
        name = n.substring(0, n.lastIndexOf('.'));
        path = p + "/" + n;
        btn = (Button) LayoutInflater.from(context).inflate(R.layout.button, null);
        btn.setText(name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.Play(context, path);
            }
        });
    }
}
