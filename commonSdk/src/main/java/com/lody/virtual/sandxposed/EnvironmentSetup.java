/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.lody.virtual.sandxposed;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.swift.sandhook.xposedcompat.utils.FileUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.io.File;
import java.util.Arrays;

public class EnvironmentSetup {
    public static void init(Context context, String packageName, String processName) {
        EnvironmentSetup.initSystemProp(context);
        EnvironmentSetup.initForWeChat(context, processName);
    }

    private static void initSystemProp(Context context) {
        System.setProperty(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT1fKA==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OghSVg==")));
        System.setProperty(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT1fKGYwNANiASxAKBccKA==")), new File(context.getApplicationInfo().dataDir).getParent());
        System.setProperty(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4+CGgKODBhEVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OghSVg==")));
    }

    private static void initForWeChat(Context context, String processName) {
        if (!TextUtils.equals((CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPFo=")), (CharSequence)processName)) {
            return;
        }
        File dataDir = new File(context.getApplicationInfo().dataDir);
        File tinker = new File(dataDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgYCGUzNAQ=")));
        File tinker_temp = new File(dataDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgYCGUzNARsJwo/KggmVg==")));
        File tinker_server = new File(dataDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgYCGUzNARsJyg/Iz4+PWoVSFo=")));
        try {
            FileUtils.delete(tinker);
            FileUtils.delete(tinker_temp);
            FileUtils.delete(tinker_server);
        }
        catch (Exception exception) {
            // empty catch block
        }
        final int mainProcessId = Process.myPid();
        XposedHelpers.findAndHookMethod(Process.class, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC4YDmoIIARgJCg/Iy4qVg==")), Integer.TYPE, new XC_MethodHook(){

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int pid = (Integer)param.args[0];
                if (pid != mainProcessId) {
                    return;
                }
                Object[] stackTrace = Thread.currentThread().getStackTrace();
                if (stackTrace == null) {
                    return;
                }
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (!stackTraceElement.getClassName().contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZoDjw7")))) continue;
                    XposedBridge.log(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgfOGojGgZLESgvKQcqMWkzBSZ1MxpF")) + Arrays.toString(stackTrace));
                    param.setResult(null);
                    break;
                }
            }
        });
    }
}

