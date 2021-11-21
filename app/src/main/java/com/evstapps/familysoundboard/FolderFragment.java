package com.evstapps.familysoundboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;

@SuppressWarnings("ConstantConditions")
@SuppressLint("InflateParams")
public class FolderFragment extends Fragment {

    public FolderFragment() {
        super(R.layout.layout_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        assert this.getArguments() != null;
        AssetFolder assetFolder = App.assetFolders.get(this.getArguments().getInt("position"));
        FlexboxLayout flexboxLayout = view.findViewById(R.id.flex);
        for (AssetItem assetItem : assetFolder.assetItems) {
            View btnView = getActivity().getLayoutInflater().inflate(R.layout.layout_item_button, null);
            //btnView.setBackground(assetFolder.bgImage);
            TextView btnText = btnView.findViewById(R.id.btnText);
            ImageView btnIcon = btnView.findViewById(R.id.btnIcon);
            btnIcon.setImageBitmap(assetItem.tabIcon);
            btnText.setText(assetItem.name);
            btnView.setOnClickListener(view1 -> {
                Player.Play(getActivity(), assetItem.filePath);
                MainActivity.adManager.ShowAdd();
            });
            btnView.setOnLongClickListener(view12 -> {
                new PopupDialog(getActivity(), assetItem).show();
                return false;
            });
            flexboxLayout.addView(btnView);
        }
    }

}
