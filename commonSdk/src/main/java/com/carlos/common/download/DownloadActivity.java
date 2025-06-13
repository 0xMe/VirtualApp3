/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.Button
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.core.app.ActivityCompat
 *  androidx.core.content.ContextCompat
 *  com.kook.librelease.R$layout
 */
package com.carlos.common.download;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.carlos.common.download.DownloadListner;
import com.carlos.common.download.DownloadManager;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;

public class DownloadActivity
extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    TextView tv_file_name1;
    TextView tv_progress1;
    TextView tv_file_name2;
    TextView tv_progress2;
    Button btn_download1;
    Button btn_download2;
    Button btn_download_all;
    ProgressBar pb_progress1;
    ProgressBar pb_progress2;
    DownloadManager mDownloadManager;
    String wechatUrl = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8OTCVOJAooKBccKHonMAFsDRoqLD4HKWYgLCxrHhodOQhbKmsaFgNlES86IC02IGsaBgJ1ClAcLggcPG8jGi9iViMgM18lDm4gTSE="));
    String qqUrl = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4rKF4YD2gjQQJsVhoqLD4HKWAFGiRlESMeLC1bCmsFJAF6NTg5JBgqJm8KLA9iIyg8Iy4MCWoFNAFhDx47Kj02KG8FLCx1Nzg7Iz5SVg=="));
    Button btn_cancel2;
    Button btn_cancel1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.download_demo);
        this.initDownloads();
    }

    private void initDownloads() {
        this.mDownloadManager = DownloadManager.getInstance();
        this.mDownloadManager.add((Context)this, this.wechatUrl, new DownloadListner(){

            @Override
            public void onFinished() {
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYEloOAgkdDH4jSFo=")), (int)0).show();
            }

            @Override
            public void onProgress(float progress) {
                DownloadActivity.this.pb_progress1.setProgress((int)(progress * 100.0f));
                DownloadActivity.this.tv_progress1.setText((CharSequence)(String.format(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQQbKmgjSFo=")), Float.valueOf(progress * 100.0f)) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQhSVg=="))));
            }

            @Override
            public void onPause() {
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR5YXgcUPwhSVg==")), (int)0).show();
            }

            @Override
            public void onCancel() {
                DownloadActivity.this.tv_progress1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ol8uVg==")));
                DownloadActivity.this.pb_progress1.setProgress(0);
                DownloadActivity.this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYFT0qAhlcBkdbGwp4AVRF")), (int)0).show();
            }
        });
        this.mDownloadManager.add((Context)this, this.qqUrl, new DownloadListner(){

            @Override
            public void onFinished() {
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYEloOAgkdDH4jSFo=")), (int)0).show();
            }

            @Override
            public void onProgress(float progress) {
                DownloadActivity.this.pb_progress2.setProgress((int)(progress * 100.0f));
                DownloadActivity.this.tv_progress2.setText((CharSequence)(String.format(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQQbKmgjSFo=")), Float.valueOf(progress * 100.0f)) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQhSVg=="))));
            }

            @Override
            public void onPause() {
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR5YXgcUPwhSVg==")), (int)0).show();
            }

            @Override
            public void onCancel() {
                DownloadActivity.this.tv_progress2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ol8uVg==")));
                DownloadActivity.this.pb_progress2.setProgress(0);
                DownloadActivity.this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
                Toast.makeText((Context)DownloadActivity.this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYFT0qAhlcBkdbGwp4AVRF")), (int)0).show();
            }
        });
    }

    public void downloadOrPause(View view) {
        if (view == this.btn_download1) {
            if (!this.mDownloadManager.isDownloading(this.wechatUrl)) {
                this.mDownloadManager.download(this.wechatUrl);
                this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR4=")));
            } else {
                this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
                this.mDownloadManager.pause(this.wechatUrl);
            }
        } else if (view == this.btn_download2) {
            if (!this.mDownloadManager.isDownloading(this.qqUrl)) {
                this.mDownloadManager.download(this.qqUrl);
                this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR4=")));
            } else {
                this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
                this.mDownloadManager.pause(this.qqUrl);
            }
        }
    }

    public void downloadOrPauseAll(View view) {
        if (!this.mDownloadManager.isDownloading(this.wechatUrl, this.qqUrl)) {
            this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR4=")));
            this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR4=")));
            this.btn_download_all.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxorCkNJFyBYAwcQAhk/GA==")));
            this.mDownloadManager.download(this.wechatUrl, this.qqUrl);
        } else {
            this.mDownloadManager.pause(this.wechatUrl, this.qqUrl);
            this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
            this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
            this.btn_download_all.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxorCkNJFyBYXh8XA1dAJQ==")));
        }
    }

    public void cancel(View view) {
        if (view == this.btn_cancel1) {
            this.mDownloadManager.cancel(this.wechatUrl);
        } else if (view == this.btn_cancel2) {
            this.mDownloadManager.cancel(this.qqUrl);
        }
    }

    public void cancelAll(View view) {
        this.mDownloadManager.cancel(this.wechatUrl, this.qqUrl);
        this.btn_download1.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
        this.btn_download2.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzM=")));
        this.btn_download_all.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxorCkNJFyBYXh8XA1dAJQ==")));
    }

    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        String permission2 = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIgoXOzwAU30xJExmMjBOLiwqAmYmFlo="));
        if (!this.checkPermission(permission2)) {
            if (this.shouldShowRationale(permission2)) {
                this.showMessage(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhkBEkMRJRFYA14fAxoZDEoGH0hrESgeLDYrUgY0RCl8N1RF")));
            }
            ActivityCompat.requestPermissions((Activity)this, (String[])new String[]{permission2}, (int)1);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.cancelAll(null);
    }

    private void showMessage(String msg) {
        Toast.makeText((Context)this, (CharSequence)msg, (int)0).show();
    }

    protected boolean checkPermission(String permission2) {
        return ContextCompat.checkSelfPermission((Context)this, (String)permission2) == 0;
    }

    protected boolean shouldShowRationale(String permission2) {
        return ActivityCompat.shouldShowRequestPermissionRationale((Activity)this, (String)permission2);
    }
}

