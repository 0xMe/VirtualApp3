/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.lody.virtual.client.stub;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.AppLauncherCallback;
import com.lody.virtual.client.ipc.VActivityManager;
import java.net.URISyntaxException;

public class ShortcutHandleActivity
extends Activity
implements AppLauncherCallback {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.finish();
        Intent intent = this.getIntent();
        if (intent == null) {
            return;
        }
        int userId = intent.getIntExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9mASg/IzxfMWk2NFo=")), 0);
        String splashUri = intent.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9hJyQoLwgqMmMFSFo=")));
        String targetUri = intent.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9mASwzJi5SVg==")));
        Intent splashIntent = null;
        Intent targetIntent = null;
        if (splashUri != null) {
            try {
                splashIntent = Intent.parseUri((String)splashUri, (int)0);
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (targetUri != null) {
            try {
                targetIntent = Intent.parseUri((String)targetUri, (int)0);
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (targetIntent == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 15) {
            targetIntent.setSelector(null);
        }
        if (splashIntent == null) {
            try {
                VActivityManager.get().startActivity(targetIntent, userId);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            splashIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmsIRVRmDB4T")), (Parcelable)targetIntent);
            splashIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmUmNFo=")), userId);
            this.startActivity(splashIntent);
        }
    }

    @Override
    public boolean checkVerify() {
        return true;
    }

    @Override
    public String currentActivity() {
        return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojLDdhNFE1IykYP28FPCNlJx0bKhgXKn0KND9vATgiIz01KmgzJCVoVhpMJAgqIG4zBh9uDhowKT4YLGkVSFo="));
    }
}

