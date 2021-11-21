package com.evstapps.familysoundboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Test extends AppCompatActivity {

    public static ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setContentView(R.layout.layout_test);

        new EVSTRingtoneManager(this);

        viewPager2 = findViewById(R.id.pager);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(App.assetFolders.get(position).tabView)).attach();
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }
}