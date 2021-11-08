package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ViewPager2 viewPager2 = findViewById(R.id.pager);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        try {
            for(String tab : getAssets().list("Tabs")){
                ItemContainer ic = new ItemContainer(tab, this);
                for(String name : getAssets().list("Tabs/" + tab)){
                    if (name.equals("icon.png")) {
                        InputStream is = getAssets().open("Tabs/" + tab + "/" + name);
                        ic.SetIcon(BitmapFactory.decodeStream(is));
                        is.close();
                    } else {
                        Item item = new Item(this, name, "Tabs/" + tab);
                        ic.items.add(item);
                    }
                }
                Collections.sort(ic.items);
                viewPager2Adapter.itemContainers.add(ic);
            }
        } catch (IOException e) {
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
        //new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(viewPager2Adapter.itemContainers.get(position).title)).attach();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(viewPager2Adapter.itemContainers.get(position).tab)).attach();
    }
}