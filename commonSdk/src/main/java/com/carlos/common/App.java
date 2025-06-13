/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.widget.Toast
 *  androidx.annotation.RequiresApi
 *  androidx.appcompat.app.AppCompatDelegate
 *  com.kook.librelease.R$layout
 */
package com.carlos.common;

import android.app.MultiApplication;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import com.carlos.common.AppComponentDelegate;
import com.carlos.common.ui.delegate.MyAppRequestListener;
import com.carlos.common.ui.delegate.MyTaskDescDelegate;
import com.carlos.common.utils.SPTools;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.core.AppCallback;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;

public class App
extends MultiApplication {
    private static App gApp;
    AppComponentDelegate mAppComponentDelegate;
    public final SettingConfig mConfig = new SettingConfig(){
        public SettingConfig.FakeWifiStatus fakeWifiStatus = new SettingConfig.FakeWifiStatus();

        @Override
        public String getMainPackageName() {
            return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQhSVg=="));
        }

        @Override
        public String getExtPackageName() {
            return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcJ2cVFlo="));
        }

        @Override
        public boolean isEnableIORedirect() {
            return true;
        }

        @Override
        public Intent onHandleLauncherIntent(Intent originIntent) {
            Intent intent = new Intent();
            ComponentName component = new ComponentName(this.getMainPackageName(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgfCGsgNANgNAY/Iy4pDmUVQCZqEQYeLl8cGH0KNC5nHlkcLyxbJW8VAiJlHiwg")));
            intent.setComponent(component);
            intent.addFlags(0x10000000);
            return intent;
        }

        @Override
        public boolean isUseRealDataDir(String packageName) {
            return false;
        }

        @Override
        public boolean isOutsidePackage(String packageName) {
            return false;
        }

        @Override
        public boolean isAllowCreateShortcut() {
            return false;
        }

        @Override
        public boolean isHostIntent(Intent intent) {
            return intent.getData() != null && com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+KmUzNAY=")).equals(intent.getData().getScheme());
        }

        @Override
        public boolean isUseRealApkPath(String packageName) {
            return packageName.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogLCtiAQY1KjkYP28gTVo=")));
        }

        @Override
        public boolean isEnableVirtualSdcardAndroidData() {
            return BuildCompat.isQ();
        }

        @Override
        public boolean resumeInstrumentationInMakeApplication(String packageName) {
            if (packageName.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogLANONCA2KBguDWwjASZvASAqPC4+MWIKQSB8NF0iIz4AVg==")))) {
                return true;
            }
            return super.resumeInstrumentationInMakeApplication(packageName);
        }

        @Override
        public boolean isUnProtectAction(String action) {
            if (action.startsWith(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ITw+H2MgFiV9Dgo5LwgqLn0zGgNvHAZF")))) {
                return true;
            }
            return super.isUnProtectAction(action);
        }

        @Override
        public SettingConfig.FakeWifiStatus getFakeWifiStatus(String packageName, int userId) {
            String SSID_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki02CWgIGiFiAQZF")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + userId;
            String MAC_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+OWYzQStnAVRF")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + userId;
            String ssid = SPTools.getString(VirtualCore.get().getContext(), SSID_KEY);
            String mac = SPTools.getString(VirtualCore.get().getContext(), MAC_KEY);
            if (TextUtils.isEmpty((CharSequence)ssid) || TextUtils.isEmpty((CharSequence)mac)) {
                return null;
            }
            this.fakeWifiStatus.setSSID(ssid);
            this.fakeWifiStatus.setDefaultMac(mac);
            return this.fakeWifiStatus;
        }
    };
    private BroadcastReceiver mGmsInitializeReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            View view = LayoutInflater.from((Context)context).inflate(R.layout.content_gms_init_layout, null);
            Toast toast = new Toast(context);
            toast.setGravity(81, 0, 0);
            toast.setDuration(0);
            toast.setView(view);
            try {
                toast.show();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    };

    public static App getApp() {
        return gApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        gApp = this;
        VLog.OPEN_LOG = true;
        try {
            VirtualCore.get().startup(this, base, this.mConfig);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void en(byte en) {
        boolean abc = en == 1;
    }

    @Override
    public AppCallback getAppCallback() {
        return this.mAppComponentDelegate;
    }

    @Override
    @RequiresApi(api=21)
    public void onCreate() {
        super.onCreate();
        this.lazyInjectInit();
        if (this.mAppComponentDelegate == null) {
            this.mAppComponentDelegate = new AppComponentDelegate((Context)this);
        }
        final VirtualCore virtualCore = VirtualCore.get();
        virtualCore.setAppCallback(this.mAppComponentDelegate);
        virtualCore.initialize(new VirtualCore.VirtualInitializer(){

            @Override
            public void onMainProcess() {
                AppCompatDelegate.setCompatVectorFromResourcesEnabled((boolean)true);
                App.this.mAppComponentDelegate.setMainProcess((Context)gApp, true);
                App.this.registerReceiver(App.this.mGmsInitializeReceiver, new IntentFilter(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42JBJ9JVlNIRY2XWILJEx9HFEKLBhSVg=="))));
            }

            @Override
            @RequiresApi(api=17)
            public void onVirtualProcess() {
                App.this.mAppComponentDelegate.setMainProcess(null, false);
                virtualCore.setTaskDescriptionDelegate(new MyTaskDescDelegate());
                virtualCore.setTaskDescriptionDelegate(new MyTaskDescDelegate());
                virtualCore.setAppRequestListener(new MyAppRequestListener((Context)App.this));
            }

            @Override
            public void onServerProcess() {
            }
        });
    }

    private void lazyInjectInit() {
    }
}

