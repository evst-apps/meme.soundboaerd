package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;

public class FolderFragment extends Fragment {

    public FolderFragment() {
        super(R.layout.activity_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        AssetFolder assetFolder = App.assetFolders.get(this.getArguments().getInt("position"));
        FlexboxLayout flexboxLayout = view.findViewById(R.id.flex);
        for (AssetItem assetItem : assetFolder.assetItems) {
            @SuppressLint("InflateParams") View buttonView = getActivity().getLayoutInflater().inflate(R.layout.activity_button, null);
            //buttonView.setBackground(assetFolder.bgImage);
            TextView textView = buttonView.findViewById(R.id.textView);
            textView.setText(assetItem.name);
            flexboxLayout.addView(buttonView);
        }
    }

}
