package com.evstapps.familysoundboard;

import android.app.Dialog;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class PopupDialog extends Dialog {

    AssetItem assetItem;

    public PopupDialog(@NonNull Context context, AssetItem assetItem) {
        super(context);
        this.assetItem = assetItem;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        View faveBtn = findViewById(R.id.favBtn);
        TextView faveBtnText = faveBtn.findViewById(R.id.btnText);
        faveBtnText.setText("Add to Favorites");

        View ringtoneBtn = findViewById(R.id.ringtoneBtn);
        TextView ringtoneBtnText = ringtoneBtn.findViewById(R.id.btnText);
        ringtoneBtnText.setText("Set as Ringtone");

        View notificationBtn = findViewById(R.id.notificationBtn);
        TextView notificationBtnText = notificationBtn.findViewById(R.id.btnText);
        notificationBtnText.setText("Set as Notification");

        if (App.assetFolders.get(0).assetItems.contains(assetItem)){
            faveBtnText.setText("Remove from Favorites");
        } else {
            faveBtnText.setText("Add to Favorites");
        }
        faveBtn.setOnClickListener(view -> {
            if (App.assetFolders.get(0).assetItems.contains(assetItem)){
                App.assetFolders.get(0).assetItems.remove(assetItem);
            } else {
                App.assetFolders.get(0).assetItems.add(assetItem);
            }
            MainActivity.viewPager2.setAdapter(MainActivity.viewPager2.getAdapter());
            App.saveFavorites();
            this.dismiss();
        });
        ringtoneBtn.setOnClickListener(view -> {
            MainActivity.evstRingtoneManager.SetAsRingtoneOrNotification(assetItem.filePath, RingtoneManager.TYPE_RINGTONE);
            this.dismiss();
        });
        notificationBtn.setOnClickListener(view -> {
            MainActivity.evstRingtoneManager.SetAsRingtoneOrNotification(assetItem.filePath, RingtoneManager.TYPE_NOTIFICATION);
            this.dismiss();
        });
    }

}
