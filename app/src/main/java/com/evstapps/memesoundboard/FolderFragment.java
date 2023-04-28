package com.evstapps.memesoundboard;

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
        MainActivity mainActivity = (MainActivity) getActivity();
        AssetFolder assetFolder = App.assetFolders.get(this.getArguments().getInt("position"));
        FlexboxLayout flexboxLayout = view.findViewById(R.id.flex);
        for (AssetItem assetItem : assetFolder.assetItems) {
            assetItem.view = getActivity().getLayoutInflater().inflate(R.layout.layout_item_button, null);
            TextView btnText = assetItem.view.findViewById(R.id.btnText);
            ImageView btnIcon = assetItem.view.findViewById(R.id.btnIcon);
            btnIcon.setImageBitmap(assetItem.tabIcon);
            btnText.setText(assetItem.name);
            assetItem.view.setOnClickListener(view1 -> mainActivity.evstMediaPlayer.Play(assetItem));
            assetItem.view.setOnLongClickListener(view12 -> {
                new PopupDialog(getActivity(), assetItem).show();
                return false;
            });
            flexboxLayout.addView(assetItem.view);
        }
    }

}
