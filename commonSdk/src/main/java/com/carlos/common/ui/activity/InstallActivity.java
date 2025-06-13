/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.pm.PackageInstaller
 *  android.content.pm.PackageInstaller$Session
 *  android.content.pm.PackageInstaller$SessionParams
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 *  android.widget.Toast
 *  androidx.annotation.Nullable
 *  androidx.appcompat.app.AppCompatActivity
 *  com.kook.librelease.R$layout
 */
package com.carlos.common.ui.activity;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.carlos.common.utils.xapk.XAPKUtils;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstallActivity
extends AppCompatActivity {
    private static final String TAG;
    private static final String PACKAGE_INSTALLED_ACTION;
    public static final String KEY_XAPK_PATH;
    public static final String KEY_APK_PATHS;
    private String xapkPath;
    private List<String> apkPaths;
    private ExecutorService mExecutorService;
    private PackageInstaller.Session mSession;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_xapk_install);
        this.xapkPath = this.getIntent().getStringExtra(KEY_XAPK_PATH);
        this.apkPaths = this.getIntent().getStringArrayListExtra(KEY_APK_PATHS);
        this.installXapk();
    }

    private void installXapk() {
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText((Context)this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEYyOT5YXh8NAgoNDUdJJRFBFR9PBRwNGU5XAyofLD0KWDYBX1pWMVFVDzg5JBgqJm8KLzZ1Iy8dBxodX0YWQitYXh8IAj8ZX0dNJSQ=")), (int)0).show();
            this.finish();
        }
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mExecutorService.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    InstallActivity.this.mSession = InstallActivity.this.initSession();
                    for (String apkPath : InstallActivity.this.apkPaths) {
                        InstallActivity.this.addApkToInstallSession(apkPath, InstallActivity.this.mSession);
                    }
                    InstallActivity.this.commitSession(InstallActivity.this.mSession);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    InstallActivity.this.abandonSession();
                }
            }
        });
    }

    @TargetApi(value=21)
    private PackageInstaller.Session initSession() throws IOException {
        PackageInstaller.Session session = null;
        PackageInstaller packageInstaller = this.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(1);
        int sessionId = 0;
        sessionId = packageInstaller.createSession(params);
        session = packageInstaller.openSession(sessionId);
        return session;
    }

    @TargetApi(value=21)
    private void addApkToInstallSession(String filePath, PackageInstaller.Session session) throws IOException {
        try (OutputStream packageInSession = session.openWrite(XAPKUtils.getFileName(filePath), 0L, new File(filePath).length());
             BufferedInputStream is = new BufferedInputStream(new FileInputStream(filePath));){
            int n;
            byte[] buffer = new byte[16384];
            while ((n = ((InputStream)is).read(buffer)) >= 0) {
                packageInSession.write(buffer, 0, n);
            }
        }
    }

    @TargetApi(value=21)
    private void commitSession(PackageInstaller.Session session) {
        Intent intent = new Intent((Context)this, InstallActivity.class);
        intent.setAction(PACKAGE_INSTALLED_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this, (int)0, (Intent)intent, (int)0);
        IntentSender statusReceiver = pendingIntent.getIntentSender();
        session.commit(statusReceiver);
    }

    @TargetApi(value=21)
    private void abandonSession() {
        if (this.mSession != null) {
            this.mSession.abandon();
            this.mSession.close();
        }
    }

    @TargetApi(value=21)
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (PACKAGE_INSTALLED_ACTION.equals(intent.getAction())) {
            int status = -100;
            String message = "";
            if (extras != null) {
                status = extras.getInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDwePC4uPGYVMCR8NSwTICscXGQjSFo=")));
                message = extras.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDwePC4uPGYVMCR8NSwTICscXGQmGkhgHDBBIwU+Bg==")));
            }
            switch (status) {
                case -1: {
                    Intent confirmIntent = (Intent)extras.get(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmsIRVRmDB4T")));
                    this.startActivity(confirmIntent);
                    break;
                }
                case 0: {
                    Toast.makeText((Context)this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwZXEMRFxVYAB8CAhkFHX4jSFo=")), (int)0).show();
                    this.finish();
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    Toast.makeText((Context)this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwZXEMRFxVYEg8rA1cNPX87EyVVLxNJQFtcKVQFSFo=")), (int)0).show();
                    this.finish();
                    Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcKWwFJCRgVyQ+LwccCGkjATd4EVRF")) + status + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186Vg==")) + message));
                    break;
                }
                default: {
                    Toast.makeText((Context)this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwZXEMRFxVYEg8rA1cNPX87Ey0dLy1ARDY/Xx8oEz4fPA9XEwAJH1cNMSFVLyEvRDUNIx4SIVMCUgcSXCYrHQY7BzQdUx81WSUZWBgrEyUYAVRF")), (int)0).show();
                    this.finish();
                }
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mExecutorService != null && !this.mExecutorService.isShutdown()) {
            this.mExecutorService.shutdown();
        }
        this.abandonSession();
    }

    static {
        PACKAGE_INSTALLED_ACTION = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xBg59HAIOJDxbH2IhJFZgHAYWLjwuWGEIQV9hJRpF"));
        KEY_XAPK_PATH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KBg+KGU2GgJ9AQo0"));
        KEY_APK_PATHS = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6MWYwIDdmHhpF"));
        TAG = InstallActivity.class.getSimpleName();
    }
}

