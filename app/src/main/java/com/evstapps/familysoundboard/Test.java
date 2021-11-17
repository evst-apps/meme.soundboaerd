package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test);

        for (AssetFolder assetFolder : App.assetFolders) {
            if (assetFolder.tabView == null) {
            }
        }

        ViewPager2 viewPager2 = findViewById(R.id.pager);
        ViewPager2Adapter viewPager2AdapterTest = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2AdapterTest);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(App.assetFolders.get(position).tabView)).attach();

    }
}