/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.ContentUris
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.provider.DocumentsContract
 *  android.provider.MediaStore$Audio$Media
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Video$Media
 *  android.text.TextUtils
 *  android.util.Log
 *  androidx.annotation.NonNull
 */
package com.carlos.common.imagepicker.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.kook.librelease.StringFog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.Locale;

public class FileUtils {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JT4YDmgYNAZjDlEp"));

    private FileUtils() {
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmkgFgZrDgobLRgED2YaAjVpDjwuORgcKWggNAFoERoZJy5SVg==")).equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmowRSVvNx4vLhcMD04wFipqJB4bKQhbIGwnBjBqNzAaJgc2JWoVMFo=")).equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmowRSVvNx4vLhcMD04wQSBuHhoqORgcKWggNAFoERoZJy5SVg==")).equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojPCVgJDgoKAMYOW8VBgRlJx4vPC4+DmEVNyllHgYeIz4uD3UzLANqJCw0Jj4MVg==")).equals(uri.getAuthority());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJFo="));
        String[] projection = new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJFo="))};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJFo=")));
                String string2 = cursor.getString(column_index);
                return string2;
            }
        }
        catch (IllegalArgumentException ex) {
            Log.i((String)TAG, (String)String.format(Locale.getDefault(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGAFJAZ9DCg1KhgMD29TIyhhJywsKgg9JE4OOFN+ASwM")), ex.getMessage()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @SuppressLint(value={"NewApi"})
    public static String getPath(Context context, Uri uri) {
        boolean isKitKat;
        boolean bl = isKitKat = Build.VERSION.SDK_INT >= 19;
        if (isKitKat && DocumentsContract.isDocumentUri((Context)context, (Uri)uri)) {
            if (FileUtils.isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId((Uri)uri);
                String[] split = docId.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OD5SVg==")));
                String type = split[0];
                if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhcMCWoVJARnAVRF")).equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + split[1];
                }
            } else if (FileUtils.isDownloadsDocument(uri)) {
                String id2 = DocumentsContract.getDocumentId((Uri)uri);
                if (!TextUtils.isEmpty((CharSequence)id2)) {
                    try {
                        Uri contentUri = ContentUris.withAppendedId((Uri)Uri.parse((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmVgU1Oi02DWUFMCRlJzgvKToADmYKMDdvDiwOLz4uMWUzHgNrESwc"))), (long)Long.valueOf(id2));
                        return FileUtils.getDataColumn(context, contentUri, null, null);
                    }
                    catch (NumberFormatException e) {
                        Log.i((String)TAG, (String)e.getMessage());
                        return null;
                    }
                }
            } else if (FileUtils.isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId((Uri)uri);
                String[] split = docId.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OD5SVg==")));
                String type = split[0];
                Uri contentUri = null;
                if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgIP2gzNFo=")).equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YPGgVGlo=")).equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LgcuPGUVGlo=")).equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4YPH5TGlo="));
                String[] selectionArgs = new String[]{split[1]};
                return FileUtils.getDataColumn(context, contentUri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4YPH5TGlo=")), selectionArgs);
            }
        } else {
            if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmEVRF")).equalsIgnoreCase(uri.getScheme())) {
                if (FileUtils.isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return FileUtils.getDataColumn(context, uri, null, null);
            }
            if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgVSFo=")).equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyFile(@NonNull String pathFrom, @NonNull String pathTo) throws IOException {
        if (pathFrom.equalsIgnoreCase(pathTo)) {
            return;
        }
        AbstractInterruptibleChannel outputChannel = null;
        AbstractInterruptibleChannel inputChannel = null;
        try {
            inputChannel = new FileInputStream(new File(pathFrom)).getChannel();
            outputChannel = new FileOutputStream(new File(pathTo)).getChannel();
            ((FileChannel)inputChannel).transferTo(0L, ((FileChannel)inputChannel).size(), (WritableByteChannel)((Object)outputChannel));
            inputChannel.close();
        }
        finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        }
    }
}

