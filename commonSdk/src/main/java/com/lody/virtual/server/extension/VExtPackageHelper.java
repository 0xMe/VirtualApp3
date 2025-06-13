/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RecentTaskInfo
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.ActivityManager$RunningTaskInfo
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.IBinder
 *  android.os.Process
 *  android.os.RemoteException
 */
package com.lody.virtual.server.extension;

import android.app.ActivityManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.lody.virtual.IExtHelperInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.ipc.FileTransfer;
import com.lody.virtual.helper.DexOptimizer;
import com.lody.virtual.helper.PackageCleaner;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class VExtPackageHelper
extends ContentProvider {
    private static final String TAG = VExtPackageHelper.class.getSimpleName();
    private final Binder mExtHelperInterface = new IExtHelperInterface.Stub(){

        public void copyPackage(InstalledAppInfo appInfo) {
            String packageName = appInfo.packageName;
            VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4AKGkYIDd9JA47KC0LIH4zSFo=")) + packageName);
            FileUtils.ensureDirCreate(VEnvironment.getDataAppPackageDirectoryExt(packageName), VEnvironment.getDataAppLibDirectoryExt(packageName));
            FileTransfer fileTransfer = FileTransfer.get();
            fileTransfer.copyFile(VEnvironment.getPackageFile(packageName), VEnvironment.getPackageFileExt(packageName));
            for (String splitName : appInfo.getSplitNames()) {
                fileTransfer.copyFile(VEnvironment.getSplitPackageFile(packageName, splitName), VEnvironment.getSplitPackageFileExt(packageName, splitName));
            }
            fileTransfer.copyDirectory(VEnvironment.getDataAppLibDirectory(packageName), VEnvironment.getDataAppLibDirectoryExt(packageName));
            if (VirtualCore.get().isRunInExtProcess(packageName)) {
                String instructionSet = VirtualRuntime.getCurrentInstructionSet();
                try {
                    DexOptimizer.dex2oat(VEnvironment.getPackageFileExt(packageName).getPath(), VEnvironment.getOatFileExt(packageName, instructionSet).getPath());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int syncPackages() {
            if (VirtualCore.get().isSharedUserId()) {
                return 0;
            }
            1 var1_1 = this;
            synchronized (var1_1) {
                PackageCleaner.cleanUsers(VEnvironment.getDataUserDirectoryExt());
                PackageCleaner.cleanUninstalledPackages();
                for (InstalledAppInfo info : VirtualCore.get().getInstalledApps(0)) {
                    File appDir;
                    List<String> splitNames = info.getSplitNames();
                    boolean splitChanged = false;
                    if (!splitNames.isEmpty()) {
                        for (String splitName : splitNames) {
                            if (VEnvironment.getSplitPackageFileExt(info.packageName, splitName).exists()) continue;
                            splitChanged = true;
                        }
                    }
                    if ((appDir = VEnvironment.getDataAppPackageDirectoryExt(info.packageName)).exists() && !splitChanged) continue;
                    FileUtils.ensureDirCreate(appDir);
                    if (info.dynamic) continue;
                    this.copyPackage(info);
                }
            }
            return 0;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cleanPackageData(int[] userIds, String packageName) {
            1 var3_3 = this;
            synchronized (var3_3) {
                if (packageName == null || userIds == null) {
                    return;
                }
                for (int userId : userIds) {
                    FileUtils.deleteDir(VEnvironment.getDataUserPackageDirectoryExt(userId, packageName));
                    FileUtils.deleteDir(VEnvironment.getDeDataUserPackageDirectoryExt(userId, packageName));
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forceStop(int[] pids) {
            1 var2_2 = this;
            synchronized (var2_2) {
                for (int pid : pids) {
                    Process.killProcess((int)pid);
                }
            }
        }

        @Override
        public List<ActivityManager.RunningTaskInfo> getRunningTasks(int maxNum) {
            Context context = VExtPackageHelper.this.getContext();
            if (context == null) {
                return Collections.emptyList();
            }
            ActivityManager am = (ActivityManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
            if (am == null) {
                return Collections.emptyList();
            }
            return am.getRunningTasks(maxNum);
        }

        @Override
        public List<ActivityManager.RecentTaskInfo> getRecentTasks(int maxNum, int flags) {
            Context context = VExtPackageHelper.this.getContext();
            if (context == null) {
                return Collections.emptyList();
            }
            ActivityManager am = (ActivityManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
            if (am == null) {
                return Collections.emptyList();
            }
            return am.getRecentTasks(maxNum, flags);
        }

        @Override
        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() {
            Context context = VExtPackageHelper.this.getContext();
            if (context == null) {
                return Collections.emptyList();
            }
            ActivityManager am = (ActivityManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
            if (am == null) {
                return Collections.emptyList();
            }
            return am.getRunningAppProcesses();
        }

        @Override
        public boolean isExternalStorageManager() throws RemoteException {
            if (Build.VERSION.SDK_INT >= 30) {
                return Environment.isExternalStorageManager();
            }
            return true;
        }

        @Override
        public void startActivity(Intent intent, Bundle options) {
            if (!VirtualCore.get().isSharedUserId()) {
                Context context = VExtPackageHelper.this.getContext();
                if (context == null) {
                    VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITw+Vg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGozAShhJwo7Iz42E24KBi9vNx4/L186In0KGjduDjMqOCpXJWUjBiBoHlkZDRcAOnkYAgtnNSRF")));
                } else {
                    context.startActivity(intent, options);
                }
            }
        }
    };

    public boolean onCreate() {
        return true;
    }

    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public Bundle call(String method, String arg, Bundle extras) {
        if (method.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGojNClmEVRF")))) {
            Bundle reply = new Bundle();
            BundleCompat.putBinder(reply, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh99NAY2KBcMKGMFSFo=")), (IBinder)this.mExtHelperInterface);
            return reply;
        }
        return null;
    }
}

