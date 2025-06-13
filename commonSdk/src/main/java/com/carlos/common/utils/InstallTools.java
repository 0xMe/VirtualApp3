/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  androidx.core.content.FileProvider
 */
package com.carlos.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import com.kook.librelease.StringFog;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstallTools {
    public static String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ITw9DX0VBgNmHiAoKhY2DW8FOAM="));

    public static long getInstallTimeByApk(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;
            return packageInfo.firstInstallTime;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static boolean isInstallAppByPackageName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List packageInfos = packageManager.getInstalledPackages(0);
        ArrayList<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); ++i) {
                String packName = ((PackageInfo)packageInfos.get((int)i)).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    public long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0L;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KAcYJ2lSEg1oCl0wKBhSVg==")));
            d = sf.parse(timers);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    public static int checkAPKProcess(File packageFile) {
        return 0;
    }

    public static void install(Context context, File file) {
        try {
            Intent intent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xIBZmDzxF")));
            intent.addFlags(0x10000000);
            intent.addFlags(1);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri uri = FileProvider.getUriForFile((Context)context, (String)context.getPackageName().concat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06KmowOC9iHjAq"))), (File)file);
                intent.setDataAndType(uri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWUVMCx1NzgbLgcMKWMKESllHiQsKghbIWsJEjNvJzA/JQg6LA==")));
            } else {
                Uri uri = Uri.fromFile((File)file);
                intent.setDataAndType(uri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWUVMCx1NzgbLgcMKWMKESllHiQsKghbIWsJEjNvJzA/JQg6LA==")));
            }
            context.startActivity(intent);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

