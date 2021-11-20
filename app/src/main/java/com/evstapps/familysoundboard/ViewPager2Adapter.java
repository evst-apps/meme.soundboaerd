package com.evstapps.familysoundboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.List;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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

    @Override
    public long getItemId(int position) {
        return App.assetFolders.get(position).name.hashCode();
    }

}
