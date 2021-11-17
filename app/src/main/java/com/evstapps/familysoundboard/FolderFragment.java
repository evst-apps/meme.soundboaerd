package com.evstapps.familysoundboard;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;

public class FolderFragment extends Fragment {

    private AssetFolder assetFolder;
    private Test test;

    public FolderFragment(AssetFolder assetFolder, Test test) {
        super(R.layout.activity_fragment);
        this.assetFolder = assetFolder;
        this.test = test;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FlexboxLayout flexboxLayout = view.findViewById(R.id.flex);
        for (AssetItem assetItem : assetFolder.assetItems) {
            View buttonView = test.getLayoutInflater().inflate(R.layout.activity_button, null);
            //buttonView.setBackground(assetFolder.bgImage);
            TextView textView = buttonView.findViewById(R.id.textView);
            textView.setText(assetItem.name);
            flexboxLayout.addView(buttonView);
        }
    }
}
