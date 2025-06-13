/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.lody.virtual.sandxposed;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.OSUtils;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.sandxposed.EnvironmentSetup;
import com.lody.virtual.sandxposed.SandHookHelper;
import com.swift.sandhook.HookLog;
import com.swift.sandhook.PendingHookHandler;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.utils.ReflectionUtils;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.utils.DexMakerUtils;
import de.robv.android.xposed.XposedBridge;
import java.io.File;
import java.util.List;
import mirror.dalvik.system.VMRuntime;

public class SandXposed {
    public static void init() {
        Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4+CGgLRSVgJA5F")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhguCGgFAiZiICQKKi1fCX4xPCVrESss")));
        if (Build.VERSION.SDK_INT >= 28) {
            ReflectionUtils.passApiCheck();
        }
        HookLog.DEBUG = SandHookConfig.DEBUG = VMRuntime.isJavaDebuggable == null ? false : VMRuntime.isJavaDebuggable.call(VMRuntime.getRuntime.call(new Object[0]), new Object[0]);
        SandHookConfig.SDK_INT = OSUtils.getInstance().isAndroidQ() ? 29 : Build.VERSION.SDK_INT;
        boolean bl = SandHookConfig.compiler = SandHookConfig.SDK_INT < 26;
        if (PendingHookHandler.canWork()) {
            Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4+CGgLRSVgJA5F")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhguCGgFAiZiICQKKi1fCX4xPCVrESss")));
        }
    }

    public static void injectXposedModule(Context context, String packageName, String processName) {
        Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PwMHO35THTN0DVwdPgRWJXskPzNqARohLhg2Cm8VOCplJAotIS4uIG8FHj97Cj8sMgRaDngJTCl7ICMsPwMHO35THTN0DVwdPghSVg==")));
        Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PwMHO35THTN0DVwdPgRWJXskPzNqARohLhg2Cm8VOCplJAotIS4uIG8FHj97DT8eMgRaDngJTCl7ICMsPwMHO35THTN0DVwdPgRXVg==")));
        List<InstalledAppInfo> appInfos = VirtualCore.get().getInstalledApps(0x50000000);
        ClassLoader classLoader = context.getClassLoader();
        Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IBU6UmcxNBY=")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMyh9ASQsIQcYPG8KDTI=")) + appInfos.size()));
        for (InstalledAppInfo module : appInfos) {
            if (TextUtils.equals((CharSequence)packageName, (CharSequence)module.packageName)) {
                Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcMmgVLAZvESQ1Iy0MPmcjNCxvAQIg")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcMmgVLAZpJDAoKDklIH4zSFo=")) + processName));
            }
            Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IBU6UmcxNBY=")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAP2gOTVo=")) + module.packageName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MwhSVg==")) + module.libPath));
            XposedCompat.loadModule(module.getApkPath(), module.getOatFile().getParent(), module.libPath, XposedBridge.class.getClassLoader());
        }
        XposedCompat.context = context;
        XposedCompat.packageName = packageName;
        XposedCompat.processName = processName;
        XposedCompat.cacheDir = new File(context.getCacheDir(), DexMakerUtils.MD5(processName));
        XposedCompat.classLoader = XposedCompat.getSandHookXposedClassLoader(classLoader, XposedBridge.class.getClassLoader());
        XposedCompat.isFirstApplication = true;
        SandHookHelper.initHookPolicy();
        EnvironmentSetup.init(context, packageName, processName);
        try {
            XposedCompat.callXposedModuleInit();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IBU6UmcxNBY=")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKnkjSFo=")) + throwable.getStackTrace().toString()));
        }
    }
}

