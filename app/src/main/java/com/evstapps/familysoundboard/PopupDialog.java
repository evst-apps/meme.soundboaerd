package com.evstapps.familysoundboard;

import android.app.Dialog;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class PopupDialog extends Dialog {

    private final AssetItem assetItem;
    private final MainActivity mainActivity;

    public PopupDialog(Context context, AssetItem assetItem) {
        super(context);
        this.assetItem = assetItem;
        mainActivity = (MainActivity) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        View faveBtn = findViewById(R.id.favBtn);
        TextView faveBtnText = faveBtn.findViewById(R.id.btnText);
        faveBtnText.setText(R.string.AddtoFavorites);

        View ringtoneBtn = findViewById(R.id.ringtoneBtn);
        TextView ringtoneBtnText = ringtoneBtn.findViewById(R.id.btnText);
        ringtoneBtnText.setText(R.string.SetasRingtone);

        View notificationBtn = findViewById(R.id.notificationBtn);
        TextView notificationBtnText = notificationBtn.findViewById(R.id.btnText);
        notificationBtnText.setText(R.string.SetasNotification);

        if (App.assetFolders.get(0).assetItems.contains(assetItem)){
            faveBtnText.setText(R.string.RemovefromFavorites);
        } else {
            faveBtnText.setText(R.string.AddtoFavorites);
        }
        faveBtn.setOnClickListener(view -> {
            if (App.assetFolders.get(0).assetItems.contains(assetItem)){
                App.assetFolders.get(0).assetItems.remove(assetItem);
            } else {
                App.assetFolders.get(0).assetItems.add(assetItem);
            }
            mainActivity.viewPager2.setAdapter(mainActivity.viewPager2.getAdapter());
            App.saveFavorites();
            this.dismiss();
        });
        ringtoneBtn.setOnClickListener(view -> {
            mainActivity.evstRingtoneManager.SetAsRingtoneOrNotification(assetItem.filePath, RingtoneManager.TYPE_RINGTONE);
            mainActivity.adManager.ShowAdd(0);
            this.dismiss();
        });
        notificationBtn.setOnClickListener(view -> {
            mainActivity.evstRingtoneManager.SetAsRingtoneOrNotification(assetItem.filePath, RingtoneManager.TYPE_NOTIFICATION);
            mainActivity.adManager.ShowAdd(0);
            this.dismiss();
        });
    }

}
