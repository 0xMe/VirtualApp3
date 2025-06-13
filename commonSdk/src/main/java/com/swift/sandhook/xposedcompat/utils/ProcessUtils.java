/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.swift.sandhook.xposedcompat.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Process;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
    private static volatile String processName = null;

    public static String getProcessName(Context context) {
        if (!TextUtils.isEmpty((CharSequence)processName)) {
            return processName;
        }
        processName = ProcessUtils.doGetProcessName(context);
        return processName;
    }

    private static String doGetProcessName(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService("activity");
        List runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid != Process.myPid() || proInfo.processName == null) continue;
            return proInfo.processName;
        }
        return context.getPackageName();
    }

    public static boolean isMainProcess(Context context) {
        String processName = ProcessUtils.getProcessName(context);
        String pkgName = context.getPackageName();
        return TextUtils.isEmpty((CharSequence)processName) || TextUtils.equals((CharSequence)processName, (CharSequence)pkgName);
    }

    public static List<ResolveInfo> findActivitiesForPackage(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        mainIntent.setPackage(packageName);
        List apps = packageManager.queryIntentActivities(mainIntent, 0);
        return apps != null ? apps : new ArrayList();
    }
}

