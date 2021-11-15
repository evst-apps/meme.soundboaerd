package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test);

        ViewPager2 viewPager2 = findViewById(R.id.pager);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        try {
            for(String tab : getAssets().list("Tabs")){
                ItemContainer ic = new ItemContainer(tab, this);

                InputStream icon_is = getAssets().open("Tabs/" + tab + "/" + "icon.png");
                ic.SetIcon(BitmapFactory.decodeStream(icon_is));
                icon_is.close();

                InputStream bg_is = getAssets().open("Tabs/" + tab + "/" + "bg.png");
                ic.bg = Drawable.createFromStream(bg_is, null);
                bg_is.close();

                for(String name : getAssets().list("Tabs/" + tab)){
                    if (name.contains(".mp3")) {
                        Item item = new Item(this, name, "Tabs/" + tab);
                        item.view.setBackground(ic.bg);
                        ic.items.add(item);
                    }
                }

                Collections.sort(ic.items);
                viewPager2Adapter.itemContainers.add(ic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager2.setAdapter(viewPager2Adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(viewPager2Adapter.itemContainers.get(position).tab)).attach();
    }
}