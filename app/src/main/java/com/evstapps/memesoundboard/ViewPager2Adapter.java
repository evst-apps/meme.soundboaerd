package com.evstapps.memesoundboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(MainActivity mainActivity) {
        super(mainActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FolderFragment folderFragment = new FolderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        folderFragment.setArguments(bundle);
        return folderFragment;
    }

    @Override
    public int getItemCount() {
        return App.assetFolders.size();
    }

}
