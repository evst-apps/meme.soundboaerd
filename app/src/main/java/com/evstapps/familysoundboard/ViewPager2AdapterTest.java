package com.evstapps.familysoundboard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class ViewPager2AdapterTest extends FragmentStateAdapter {

    private Test test;

    public ViewPager2AdapterTest(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.test = (Test) fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FolderFragment folderFragment = new FolderFragment(test.assetList.assetFolders.get(position), test);
        return folderFragment;
    }

    @Override
    public int getItemCount() {
        return test.assetList.assetFolders.size();
    }

}
