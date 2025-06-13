/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Parcelable
 *  android.provider.MediaStore$Images$Media
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.carlos.common.imagepicker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.kook.librelease.StringFog;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageCaptureManager {
    private static final String CAPTURED_PHOTO_PATH_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwY2I28gFitgNwoCKRdfLm8ITTdvEVlF"));
    public static final String PHOTO_PATH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhhfD2wFGh9hHiAgKRhSVg=="));
    private String mCurrentPhotoPath;
    private Context mContext;

    public ImageCaptureManager(Context mContext) {
        this.mContext = mContext;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KAcYJ2kbEg1iHgpAIRUAD28gAgM=")), Locale.ENGLISH).format(new Date());
        String imageFileName = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JDs6WGA2Glo=")) + timeStamp + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5XKGgzSFo="));
        File storageDir = Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists() && !storageDir.mkdir()) {
            Log.e((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRY+Wg==")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRhfKmowPC9gNDs8IAguKG8KRQN1Mx0bPC5SVg==")));
            throw new IOException();
        }
        File image = new File(storageDir, imageFileName);
        this.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public Intent dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1k3KAc2MW4nMDdoJCwaLD4bKmsIQQ5mIgoOIAZbQGcYNABgEVRF")));
        if (takePictureIntent.resolveActivity(this.mContext.getPackageManager()) != null) {
            File file = this.createImageFile();
            Uri photoFile = null;
            if (Build.VERSION.SDK_INT >= 24) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJFo=")), file.getAbsolutePath());
                Uri uri = this.mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                takePictureIntent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy0uLG8KNAY=")), (Parcelable)uri);
            } else {
                photoFile = Uri.fromFile((File)file);
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy0uLG8KNAY=")), (Parcelable)photoFile);
            }
            takePictureIntent.putExtra(PHOTO_PATH, file.getAbsolutePath());
        }
        return takePictureIntent;
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42QQpmHBoAJQUYH2ALBl9gHAoALysuAmQxRVVkJSQK")));
        if (TextUtils.isEmpty((CharSequence)this.mCurrentPhotoPath)) {
            return;
        }
        File f = new File(this.mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile((File)f);
        mediaScanIntent.setData(contentUri);
        this.mContext.sendBroadcast(mediaScanIntent);
    }

    public String getCurrentPhotoPath() {
        return this.mCurrentPhotoPath;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && this.mCurrentPhotoPath != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, this.mCurrentPhotoPath);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
            this.mCurrentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY);
        }
    }
}

