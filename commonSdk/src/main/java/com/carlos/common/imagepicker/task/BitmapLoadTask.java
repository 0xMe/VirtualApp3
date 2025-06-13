/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build$VERSION
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.util.Log
 *  androidx.annotation.NonNull
 *  androidx.annotation.Nullable
 *  androidx.core.content.ContextCompat
 */
package com.carlos.common.imagepicker.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.carlos.common.imagepicker.callback.BitmapLoadCallback;
import com.carlos.common.imagepicker.entity.ExifInfo;
import com.carlos.common.imagepicker.util.BitmapLoadUtils;
import com.carlos.common.imagepicker.util.FileUtils;
import com.kook.librelease.StringFog;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapLoadTask
extends AsyncTask<Void, Void, BitmapWorkerResult> {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jj4YLGoVJAJuJB4qKS0MKH0zQQNqJ1RF"));
    private final Context mContext;
    private Uri mInputUri;
    private Uri mOutputUri;
    private final int mRequiredWidth;
    private final int mRequiredHeight;
    private final BitmapLoadCallback mBitmapLoadCallback;

    public BitmapLoadTask(@NonNull Context context, @NonNull Uri inputUri, @Nullable Uri outputUri, int requiredWidth, int requiredHeight, BitmapLoadCallback loadCallback) {
        this.mContext = context;
        this.mInputUri = inputUri;
        this.mOutputUri = outputUri;
        this.mRequiredWidth = requiredWidth;
        this.mRequiredHeight = requiredHeight;
        this.mBitmapLoadCallback = loadCallback;
    }

    @NonNull
    protected BitmapWorkerResult doInBackground(Void ... params) {
        ParcelFileDescriptor parcelFileDescriptor;
        if (this.mInputUri == null) {
            return new BitmapWorkerResult(new NullPointerException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcKGwaMyhuASwzPxcqOW8VMCVvVjwpLl86KmYKTTc="))));
        }
        try {
            this.processInputUri();
        }
        catch (IOException | NullPointerException e) {
            return new BitmapWorkerResult(e);
        }
        try {
            parcelFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(this.mInputUri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj5SVg==")));
        }
        catch (FileNotFoundException e) {
            return new BitmapWorkerResult(e);
        }
        if (parcelFileDescriptor == null) {
            return new BitmapWorkerResult(new NullPointerException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ihg+KmszNCRqNAYoKAU2PWoFAgRqDjw/LD0LJGYgPDZ5Hh4+KT4hJGszGiZ7ASAwID02JXkbFiRsID83IC5SVg==")) + this.mInputUri + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))));
        }
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor((FileDescriptor)fileDescriptor, null, (BitmapFactory.Options)options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            return new BitmapWorkerResult(new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jj4AI2ojMANLHjw1IzkmOGwgBiNoDj8rLT4ACWAaESNsNFk9OD5fJ3gaFj9sDgowJAg6LG5TPDBqESg8PhcqCmhSIFBhNBkiPxYEVg==")) + this.mInputUri + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))));
        }
        options.inSampleSize = BitmapLoadUtils.calculateInSampleSize(options, this.mRequiredWidth, this.mRequiredHeight);
        options.inJustDecodeBounds = false;
        Bitmap decodeSampledBitmap = null;
        boolean decodeAttemptSuccess = false;
        while (!decodeAttemptSuccess) {
            try {
                decodeSampledBitmap = BitmapFactory.decodeFileDescriptor((FileDescriptor)fileDescriptor, null, (BitmapFactory.Options)options);
                decodeAttemptSuccess = true;
            }
            catch (OutOfMemoryError error) {
                Log.e((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgAXGohFjd9JA49Iz1fLW8VATJ4HwoaKggIO2EYICRpJzAeLBc1KmsVNDVqNyw0LD0AO24ILDFqAR4bLAc6LGowETJLEVRF")), (Throwable)error);
                options.inSampleSize *= 2;
            }
        }
        if (decodeSampledBitmap == null) {
            return new BitmapWorkerResult(new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jj4YLGoVJAJLHig1LAdbPn4zMCVvVjwpLl86IGIKNCpuHgotOD4ECGUjHTRsAVk0DRY2OW8JHTZgAVRF")) + this.mInputUri + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))));
        }
        if (Build.VERSION.SDK_INT >= 16) {
            BitmapLoadUtils.close((Closeable)parcelFileDescriptor);
        }
        int exifOrientation = BitmapLoadUtils.getExifOrientation(this.mContext, this.mInputUri);
        int exifDegrees = BitmapLoadUtils.exifToDegrees(exifOrientation);
        int exifTranslation = BitmapLoadUtils.exifToTranslation(exifOrientation);
        ExifInfo exifInfo = new ExifInfo(exifOrientation, exifDegrees, exifTranslation);
        Matrix matrix = new Matrix();
        if (exifDegrees != 0) {
            matrix.preRotate((float)exifDegrees);
        }
        if (exifTranslation != 1) {
            matrix.postScale((float)exifTranslation, 1.0f);
        }
        if (!matrix.isIdentity()) {
            return new BitmapWorkerResult(BitmapLoadUtils.transformBitmap(decodeSampledBitmap, matrix), exifInfo);
        }
        return new BitmapWorkerResult(decodeSampledBitmap, exifInfo);
    }

    private void processInputUri() throws NullPointerException, IOException {
        String inputUriScheme = this.mInputUri.getScheme();
        Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQcMCXsKLCljHjA3KAQHOg==")) + inputUriScheme));
        if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8FSFo=")).equals(inputUriScheme) || com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLFo=")).equals(inputUriScheme)) {
            try {
                this.downloadFile(this.mInputUri, this.mOutputUri);
            }
            catch (IOException | NullPointerException e) {
                Log.e((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRgALWojHiV9DgozKj05OmkVQS9lESgv")), (Throwable)e);
                throw e;
            }
        } else if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmEVRF")).equals(inputUriScheme)) {
            String path = this.getFilePath();
            if (!TextUtils.isEmpty((CharSequence)path) && new File(path).exists()) {
                this.mInputUri = Uri.fromFile((File)new File(path));
            } else {
                try {
                    this.copyFile(this.mInputUri, this.mOutputUri);
                }
                catch (IOException | NullPointerException e) {
                    Log.e((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4AKGkVAiZiICQ+LwccCGkjBlo=")), (Throwable)e);
                    throw e;
                }
            }
        } else if (!com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgVSFo=")).equals(inputUriScheme)) {
            Log.e((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcLmsVHi9iVyRKIz0bOmoFAiBrAQ4gPQhSVg==")) + inputUriScheme));
            throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcLmsVHi9iVyRKIz0bOmoFAiBrAQ4g")) + inputUriScheme);
        }
    }

    private String getFilePath() {
        if (ContextCompat.checkSelfPermission((Context)this.mContext, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl9mDwYTJytfDGALHhNnMiwQLzsmAGYFSFo="))) == 0) {
            return FileUtils.getPath(this.mContext, this.mInputUri);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void copyFile(@NonNull Uri inputUri, @Nullable Uri outputUri) throws NullPointerException, IOException {
        Log.d((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4AKGkbOC9gHjBF")));
        if (outputUri == null) {
            throw new NullPointerException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oy0uLG8KNAZLHzAqKQMmMWoJTSZvAQIdPQQHJH0gPClsNFk9OD4YKWwaDTRlEQ4oJC02Vg==")));
        }
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            int length;
            inputStream = this.mContext.getContentResolver().openInputStream(inputUri);
            outputStream = new FileOutputStream(new File(outputUri.getPath()));
            if (inputStream == null) {
                throw new NullPointerException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcKGwaMF5mESw/LwdWOmkVNAR4ESAaKi4uKksaGillEQo9ODsACGoJIAVvMzw5IAdXOw==")));
            }
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) > 0) {
                ((OutputStream)outputStream).write(buffer, 0, length);
            }
        }
        catch (Throwable throwable) {
            BitmapLoadUtils.close(outputStream);
            BitmapLoadUtils.close(inputStream);
            this.mInputUri = this.mOutputUri;
            throw throwable;
        }
        BitmapLoadUtils.close(outputStream);
        BitmapLoadUtils.close(inputStream);
        this.mInputUri = this.mOutputUri;
    }

    private void downloadFile(@NonNull Uri inputUri, @Nullable Uri outputUri) throws NullPointerException, IOException {
    }

    protected void onPostExecute(@NonNull BitmapWorkerResult result) {
        if (result.mBitmapWorkerException == null) {
            this.mBitmapLoadCallback.onBitmapLoaded(result.mBitmapResult, result.mExifInfo, this.mInputUri.getPath(), this.mOutputUri == null ? null : this.mOutputUri.getPath());
        } else {
            this.mBitmapLoadCallback.onFailure(result.mBitmapWorkerException);
        }
    }

    public static class BitmapWorkerResult {
        Bitmap mBitmapResult;
        ExifInfo mExifInfo;
        Exception mBitmapWorkerException;

        public BitmapWorkerResult(@NonNull Bitmap bitmapResult, @NonNull ExifInfo exifInfo) {
            this.mBitmapResult = bitmapResult;
            this.mExifInfo = exifInfo;
        }

        public BitmapWorkerResult(@NonNull Exception bitmapWorkerException) {
            this.mBitmapWorkerException = bitmapWorkerException;
        }
    }
}

