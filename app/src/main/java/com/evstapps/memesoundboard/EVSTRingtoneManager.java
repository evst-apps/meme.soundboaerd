package com.evstapps.memesoundboard;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class EVSTRingtoneManager {

    private final MainActivity mainActivity;

    EVSTRingtoneManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void SetAsRingtoneOrNotification(String assetFilePath, int type) {
        try {
            if (!Settings.System.canWrite(mainActivity)) {
                if (type == RingtoneManager.TYPE_RINGTONE) {
                    Toast.makeText(mainActivity, "To set a ringtone, please allow access to system settings", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mainActivity, "To set a notification sound, please allow access to system settings", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + mainActivity.getPackageName()));
                mainActivity.startActivity(intent);
                return;
            }

            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_MUSIC, false);
            if (type == RingtoneManager.TYPE_RINGTONE) {
                values.put(MediaStore.MediaColumns.TITLE, "EVST_Ringtone");
                values.put(MediaStore.Audio.Media.ARTIST, "EVST_Ringtone");
                values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            } else {
                values.put(MediaStore.MediaColumns.TITLE, "EVST_Notification");
                values.put(MediaStore.Audio.Media.ARTIST, "EVST_Notification");
                values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
                values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri newUri = mainActivity.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);

                InputStream is = mainActivity.getAssets().open(assetFilePath);
                OutputStream os = mainActivity.getContentResolver().openOutputStream(newUri);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
                os.close();
                is.close();

                RingtoneManager.setActualDefaultRingtoneUri(mainActivity, type, newUri);
            } else {
                if (!hasReadPermissions() && !hasWritePermissions()) {
                    ActivityCompat.requestPermissions(mainActivity,
                            new String[] {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 51998); // your request code
                    return;
                }

                File externalFile;
                if (type == RingtoneManager.TYPE_RINGTONE) {
                    externalFile = new File(Environment.getExternalStorageDirectory(), "EVST.Ringtone.mp3");
                } else {
                    externalFile = new File(Environment.getExternalStorageDirectory(), "EVST.Notification.mp3");
                }
                InputStream is = mainActivity.getAssets().open(assetFilePath);
                @SuppressWarnings("IOStreamConstructor") OutputStream os = new FileOutputStream(externalFile);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
                os.close();
                is.close();

                values.put(MediaStore.MediaColumns.DATA, externalFile.getAbsolutePath());
                values.put(MediaStore.MediaColumns.TITLE, externalFile.getName());
                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
                values.put("_size", externalFile.length());

                Uri uri = MediaStore.Audio.Media.getContentUriForPath(externalFile.getAbsolutePath());
                mainActivity.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + externalFile.getAbsolutePath() + "\"", null);
                Uri newUri = mainActivity.getContentResolver().insert(uri, values);

                if (type == RingtoneManager.TYPE_RINGTONE) {
                    RingtoneManager.setActualDefaultRingtoneUri(mainActivity, RingtoneManager.TYPE_RINGTONE, newUri);
                } else {
                    RingtoneManager.setActualDefaultRingtoneUri(mainActivity, RingtoneManager.TYPE_NOTIFICATION, newUri);
                }
            }

            if (type == RingtoneManager.TYPE_RINGTONE) {
                Toast.makeText(mainActivity, "Ringtone set successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mainActivity, "Notification sound set successfully", Toast.LENGTH_LONG).show();
            }

            mainActivity.adManager.ShowAdd(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(mainActivity.getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(mainActivity.getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

}
