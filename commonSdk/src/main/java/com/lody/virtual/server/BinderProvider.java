/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Process
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.lody.virtual.server;

import android.app.ActivityManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.KeepAliveService;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.compat.NotificationChannelCompat;
import com.lody.virtual.server.ServiceCache;
import com.lody.virtual.server.accounts.VAccountManagerService;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.content.VContentService;
import com.lody.virtual.server.device.VDeviceManagerService;
import com.lody.virtual.server.fs.FileTransfer;
import com.lody.virtual.server.interfaces.IServiceFetcher;
import com.lody.virtual.server.job.VJobSchedulerService;
import com.lody.virtual.server.location.VirtualLocationService;
import com.lody.virtual.server.notification.VNotificationManagerService;
import com.lody.virtual.server.pm.VAppManagerService;
import com.lody.virtual.server.pm.VPackageManagerService;
import com.lody.virtual.server.pm.VUserManagerService;
import com.lody.virtual.server.vs.VirtualStorageService;

public final class BinderProvider
extends ContentProvider {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jj4YCGgFNARpESw1LD0cPmkgRVo="));
    private final ServiceFetcher mServiceFetcher = new ServiceFetcher();
    private static boolean sInitialized = false;
    public static boolean scanApps = true;

    public boolean onCreate() {
        return this.init();
    }

    private boolean init() {
        if (sInitialized) {
            return false;
        }
        Context context = this.getContext();
        if (context != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannelCompat.checkOrCreateChannel(context, NotificationChannelCompat.DAEMON_ID, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRg+M2oVGiY=")));
                NotificationChannelCompat.checkOrCreateChannel(context, NotificationChannelCompat.DEFAULT_ID, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguPmsaNCRmEVRF")));
            }
            try {
                context.startService(new Intent(context, KeepAliveService.class));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!VirtualCore.get().isStartup()) {
            return false;
        }
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YDmhSEgZhNCA2Iy0+PWoVSFo=")), (IBinder)FileTransfer.get());
        VPackageManagerService.systemReady();
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")), (IBinder)VPackageManagerService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")), (IBinder)VActivityManagerService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo=")), (IBinder)VUserManagerService.get());
        VAppManagerService.systemReady();
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgc6KA==")), (IBinder)VAppManagerService.get());
        if (Build.VERSION.SDK_INT >= 21) {
            this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOg==")), (IBinder)VJobSchedulerService.get());
        }
        VNotificationManagerService.systemReady(context);
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOC99JCAgKQdfDg==")), (IBinder)VNotificationManagerService.get());
        VContentService.systemReady();
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")), (IBinder)VAccountManagerService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGwFNCZmEVRF")), (IBinder)VContentService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT02Vg==")), (IBinder)VirtualStorageService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguLmUVLCs=")), (IBinder)VDeviceManagerService.get());
        this.addService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV10oKi0qVg==")), (IBinder)VirtualLocationService.get());
        this.killAllProcess();
        sInitialized = true;
        if (scanApps) {
            VAppManagerService.get().scanApps();
        }
        VAccountManagerService.systemReady();
        return true;
    }

    private void addService(String name, IBinder service) {
        ServiceCache.addService(name, service);
    }

    public Bundle call(String method, String arg, Bundle extras) {
        if (!sInitialized) {
            this.init();
        }
        if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JhhSVg==")).equals(method)) {
            Bundle bundle = new Bundle();
            BundleCompat.putBinder(bundle, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh99NAY2KBcMKGMFSFo=")), (IBinder)this.mServiceFetcher);
            return bundle;
        }
        return null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void killAllProcess() {
        try {
            int uid = this.getContext().getPackageManager().getApplicationInfo((String)this.getContext().getPackageName(), (int)0).uid;
            String str = this.getContext().getPackageName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OD06Vg=="));
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager)this.getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")))).getRunningAppProcesses()) {
                if (runningAppProcessInfo.uid != uid || !runningAppProcessInfo.processName.startsWith(str)) continue;
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggiLGgaEShhESw1LD0cPmkgRChsJCwsKS0pKGMgGjdsVyMpLD1fKWgjNCVvMFFF")) + runningAppProcessInfo.processName));
                Process.killProcess((int)runningAppProcessInfo.pid);
            }
        }
        catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static class ServiceFetcher
    extends IServiceFetcher.Stub {
        private ServiceFetcher() {
        }

        @Override
        public IBinder getService(String name) throws RemoteException {
            if (name != null) {
                return ServiceCache.getService(name);
            }
            return null;
        }

        @Override
        public void addService(String name, IBinder service) throws RemoteException {
            if (name != null && service != null) {
                ServiceCache.addService(name, service);
            }
        }

        @Override
        public void removeService(String name) throws RemoteException {
            if (name != null) {
                ServiceCache.removeService(name);
            }
        }
    }
}

