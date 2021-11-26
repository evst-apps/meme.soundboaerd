package com.evstapps.familysoundboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private final MainActivity mainActivity;

    public ViewPager2Adapter(MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FolderFragment folderFragment = new FolderFragment(mainActivity);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        folderFragment.setArguments(bundle);
        return folderFragment;
    }

    @Override
    public int getItemCount() {
        return App.assetFolders.size();
    }

    @Override
    public long getItemId(int position) {
        return App.assetFolders.get(position).name.hashCode();
    }

}
