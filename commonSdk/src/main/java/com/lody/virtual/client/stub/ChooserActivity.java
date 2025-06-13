/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  com.kook.librelease.R$string
 */
package com.lody.virtual.client.stub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.kook.librelease.R;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.stub.ResolverActivity;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;

public class ChooserActivity
extends ResolverActivity {
    public static final String EXTRA_DATA = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYwGjVqEQoqKToqIGgKMDM="));
    public static final String EXTRA_WHO = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYwGjVqEQoqKToqMWoVGlo="));
    public static final String EXTRA_INTENT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYwGjVqEQoqKToqI2UwMD9qJCxF"));
    public static final String EXTRA_REQUEST_CODE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYwGjVqEQoqKToqCGsKJC9oHjAZKi0uJm4aFlo="));
    public static final String ACTION;
    public static final String EXTRA_RESULTTO;

    public static boolean check(Intent intent) {
        try {
            return TextUtils.equals((CharSequence)ACTION, (CharSequence)intent.getAction()) || TextUtils.equals((CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42NBVkIlkSJytfVg==")), (CharSequence)intent.getAction());
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @SuppressLint(value={"MissingSuperCall"})
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = this.getIntent().getExtras();
        Intent intent = this.getIntent();
        int userId = extras.getInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), VUserHandle.getCallingUserId());
        this.mOptions = (Bundle)extras.getParcelable(EXTRA_DATA);
        this.mResultWho = extras.getString(EXTRA_WHO);
        this.mRequestCode = extras.getInt(EXTRA_REQUEST_CODE, 0);
        this.mResultTo = BundleCompat.getBinder(extras, EXTRA_RESULTTO);
        Parcelable targetParcelable = intent.getParcelableExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmsIRVRmDB4T")));
        if (!(targetParcelable instanceof Intent)) {
            VLog.w(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtlDiggKQg+MWUwLFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IRg+KmgzNAZLHgYpPxcYDWU3TTdlMzwaLC0qJ2AzET15Vwo8")), targetParcelable);
            this.finish();
            return;
        }
        Intent target = (Intent)targetParcelable;
        CharSequence title = intent.getCharSequenceExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49Km4YGlRkHApF")));
        if (title == null) {
            title = this.getString(R.string.choose);
        }
        Parcelable[] pa = intent.getParcelableArrayExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmsIRRZiHBoAITsuBX02MFRiIixB")));
        Intent[] initialIntents = null;
        if (pa != null) {
            initialIntents = new Intent[pa.length];
            for (int i = 0; i < pa.length; ++i) {
                if (!(pa[i] instanceof Intent)) {
                    VLog.w(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtlDiggKQg+MWUwLFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcCWwFAjdgVyQzKj42PW8aASh4J1RF")) + i + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgcD2wJIDdgMCQJKj42PW8aATJ4Vig6")), pa[i]);
                    this.finish();
                    return;
                }
                initialIntents[i] = (Intent)pa[i];
            }
        }
        super.onCreate(savedInstanceState, target, title, initialIntents, null, false, userId);
    }

    static {
        EXTRA_RESULTTO = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy0iP24FAipjDlkwKAguJmoVGgNvAQI/IggAVg=="));
        Intent target = new Intent();
        Intent intent = Intent.createChooser((Intent)target, (CharSequence)"");
        ACTION = intent.getAction();
    }
}

