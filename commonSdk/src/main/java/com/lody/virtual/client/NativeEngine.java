/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Binder
 *  android.os.Build$VERSION
 *  android.os.Process
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.lody.virtual.client;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.DexOverride;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.hiddenapibypass.HiddenApiBypass;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.natives.NativeMethods;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NativeEngine {
    private static final String TAG;
    private static final List<DexOverride> sDexOverrides;
    private static boolean sFlag;
    private static boolean sEnabled;
    private static final String LIB_NAME;
    private static boolean EnablePidInfoCache;
    private static HashMap<Integer, PidCacheInfo> pidCache;
    private static int MaxCachePidInfoTime;
    private static int MaxCachePidInfoZise;
    private static final List<Pair<String, String>> REDIRECT_LISTS;
    public static Field artMethodField;

    public static void startDexOverride() {
        List<InstalledAppInfo> installedApps = VirtualCore.get().getInstalledApps(0);
        for (InstalledAppInfo info : installedApps) {
            if (info.dynamic) continue;
            String originDexPath = NativeEngine.getCanonicalPath(info.getApkPath());
            DexOverride override = new DexOverride(originDexPath, null, null, info.getOatPath());
            NativeEngine.addDexOverride(override);
        }
    }

    public static void addDexOverride(DexOverride dexOverride) {
        sDexOverrides.add(dexOverride);
    }

    public static String getRedirectedPath(String origPath) {
        VirtualCore.getConfig();
        try {
            return NativeEngine.nativeGetRedirectedPath(origPath);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
            return origPath;
        }
    }

    public static String reverseRedirectedPath(String origPath) {
        VirtualCore.getConfig();
        try {
            return NativeEngine.nativeReverseRedirectedPath(origPath);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
            return origPath;
        }
    }

    public static void redirectDirectory(String origPath, String newPath) {
        VirtualCore.getConfig();
        if (!origPath.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            origPath = origPath + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        if (!newPath.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            newPath = newPath + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        REDIRECT_LISTS.add((Pair<String, String>)new Pair((Object)origPath, (Object)newPath));
    }

    public static void HideSu() {
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh47IxglDWIKGgJrDgowKT4uCE4wPDNvJ1RF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh47IxglDWIKGgJrDgowKT4uCE4wPDNvIFEvKC4+Jw==")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02OmUVASVhJzBF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02OmUVASVhJzM3KD0iCWkjSFo=")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh46KQcXDWoKGlo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh46KQcXDWoKBSNrNzgiLhhSVg==")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh4aLz0cDn8KAgU=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh4aLz0cDn8KAgV1ASQsIz4uVg==")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDWgzRS9lMwY6KhhSVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDWgzRS9lMwY6Kl8IIn0KEiA=")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDW4VLCZ1JDAw")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDW4VLCZ1JDAwPBgiO2MgLFo=")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh4pKF5fIm4VLCZ1JDAw")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh4pKF5fIm4VLCZ1JDAwPBgiO2MgLFo=")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh46KQcXDWkVQS9lHjAsLi4tKWEjLFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02J28wMCtgCh46KQcXDWkVQS9lHjAsLi4tKWEjLyhuNCQaLy5SVg==")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDWoKGlo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyVgHh45LwdaDWoKBSNrNzgiLhhSVg==")));
        NativeEngine.redirectFile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02I3ozFi9gMB4pLAhSVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My02I3ozFi9gMB4pLANXPG4jJCs=")));
    }

    public static void redirectFile(String origPath, String newPath) {
        VirtualCore.getConfig();
        if (origPath.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            origPath = origPath.substring(0, origPath.length() - 1);
        }
        if (newPath.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            newPath = newPath.substring(0, newPath.length() - 1);
        }
        REDIRECT_LISTS.add((Pair<String, String>)new Pair((Object)origPath, (Object)newPath));
    }

    public static void readOnlyFile(String path) {
        VirtualCore.getConfig();
        if (SettingConfig.isUseNativeEngine2(VClient.get().getCurrentPackage())) {
            return;
        }
        try {
            NativeEngine.nativeIOReadOnly(path);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
    }

    public static void readOnly(String path) {
        VirtualCore.getConfig();
        if (SettingConfig.isUseNativeEngine2(VClient.get().getCurrentPackage())) {
            return;
        }
        if (!path.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            path = path + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        try {
            NativeEngine.nativeIOReadOnly(path);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
    }

    public static void whitelistFile(String path) {
        VirtualCore.getConfig();
        try {
            NativeEngine.nativeIOWhitelist(path);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
    }

    public static void whitelist(String path) {
        VirtualCore.getConfig();
        if (!path.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            path = path + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        try {
            NativeEngine.nativeIOWhitelist(path);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
    }

    public static void forbid(String path, boolean file) {
        VirtualCore.getConfig();
        if (!file && !path.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            path = path + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        try {
            NativeEngine.nativeIOForbid(path);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
    }

    public static String pathCat(String path1, String path2) {
        if (!TextUtils.isEmpty((CharSequence)path2) && !path1.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))) {
            path1 = path1 + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
        }
        path1 = path1 + path2;
        return path1;
    }

    public static void enableIORedirect(InstalledAppInfo appInfo) {
        ApplicationInfo coreAppInfo;
        VirtualCore.getConfig();
        if (sEnabled) {
            return;
        }
        try {
            coreAppInfo = VirtualCore.get().getHostPackageManager().getApplicationInfo(VirtualCore.getConfig().getMainPackageName(), 0L);
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(REDIRECT_LISTS, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                String a = (String)o1.first;
                String b = (String)o2.first;
                return this.compare(b.length(), a.length());
            }

            private int compare(int x, int y) {
                return Integer.compare(x, y);
            }
        });

        for (Pair<String, String> pair : REDIRECT_LISTS) {
            try {
                NativeEngine.nativeIORedirect((String)pair.first, (String)pair.second);
            }
            catch (Throwable e) {
                VLog.e(TAG, VLog.getStackTraceString(e));
            }
        }
        try {
            String extSoPath;
            String soPath = new File(coreAppInfo.nativeLibraryDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYOmwjFiVnV1kpKi5SVg=="))).getAbsolutePath();
            String soPath32 = extSoPath = new File(coreAppInfo.nativeLibraryDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYOmwjFiVnHx4/LRg1DmoFNFo="))).getAbsolutePath();
            String soPath64 = soPath;
            String nativePath = VEnvironment.getNativeCacheDir(VirtualCore.get().isExtPackage()).getPath();
            NativeEngine.nativeEnableIORedirect(soPath32, soPath64, nativePath, Build.VERSION.SDK_INT, appInfo.packageName, VirtualCore.get().getHostPkg());
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
        sEnabled = true;
    }

    public static void launchEngine(Context context, String packageName) {
        VirtualCore.getConfig();
        if (sFlag) {
            return;
        }
        Object[] methods = new Object[]{NativeMethods.gNativeMask, NativeMethods.gOpenDexFileNative, NativeMethods.gCameraNativeSetup, NativeMethods.gAudioRecordNativeCheckPermission, NativeMethods.gMediaRecorderNativeSetup, NativeMethods.gAudioRecordNativeSetup, NativeMethods.gNativeLoad};
        try {
            NativeEngine.nativeLaunchEngine(context, methods, VirtualCore.get().getHostPkg(), packageName, VirtualRuntime.isArt(), BuildCompat.isR() ? 30 : Build.VERSION.SDK_INT, NativeMethods.gCameraMethodType, NativeMethods.gAudioRecordMethodType);
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
        sFlag = true;
    }

    public static void bypassHiddenAPIEnforcementPolicyIfNeeded() {
        if (BuildCompat.isR()) {
            HiddenApiBypass.setHiddenApiExemptions(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxhSVg==")));
        } else if (BuildCompat.isPie()) {
            try {
                Method forNameMethod = Class.class.getDeclaredMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4AKmIjJCNiAVRF")), String.class);
                Class clazz = (Class)forNameMethod.invoke(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRg+DmwjAiFONygZIy42PW8nMFN9DAowLC0qI2AKLFo=")));
                Method getMethodMethod = Class.class.getDeclaredMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFNClgHiAqKAc2UmkgBiBlJyxF")), String.class, Class[].class);
                Method getRuntime = (Method)getMethodMethod.invoke(clazz, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcgNCZmHgY3KAhSVg==")), new Class[0]);
                Method setHiddenApiExemptions = (Method)getMethodMethod.invoke(clazz, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLH0FAixiHjA2JwgmMWEgFitlDjw/IxgAKmEjSFo=")), new Class[]{String[].class});
                Object runtime = getRuntime.invoke(null, new Object[0]);
                setHiddenApiExemptions.invoke(runtime, new Object[]{new String[]{StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxhSVg=="))}});
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean onKillProcess(int pid, int signal) {
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC4YDmoIIARgJCg/Iy4pIH4wTS9rVj8oPQQuIE5TODZvDjwdKC4hJHkJIz9oDRpF")), pid, signal);
        if (pid == Process.myPid()) {
            VLog.e(TAG, VLog.getStackTraceString(new Throwable()));
        }
        return true;
    }

    public static int onGetCallingUid(int originUid) {
        try {
            return NativeEngine.onGetCallingUid0(originUid);
        }
        catch (Throwable e) {
            VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITw+Vg==")), e);
            return originUid;
        }
    }

    public static int onGetCallingUid0(int originUid) {
        int uidRet;
        if (VClient.get().getClientConfig() == null) {
            return originUid;
        }
        if (originUid != VirtualCore.get().myUid() && originUid != VirtualCore.get().remoteUid()) {
            return originUid;
        }
        int callingPid = Binder.getCallingPid();
        if (callingPid == 0) {
            if (BuildCompat.isS()) {
                return VClient.get().getBaseVUid();
            }
            return 9001;
        }
        if (callingPid == Process.myPid()) {
            return VClient.get().getBaseVUid();
        }
        if (callingPid == VClient.get().getCorePid()) {
            return VEnvironment.SYSTEM_UID;
        }
        if (EnablePidInfoCache) {
            long curTime = System.currentTimeMillis();
            PidCacheInfo pidCacheInfo = pidCache.get(callingPid);
            if (pidCacheInfo != null) {
                if (curTime - pidCacheInfo.lastTime > (long)MaxCachePidInfoTime) {
                    pidCache.remove(callingPid);
                } else {
                    if (pidCacheInfo.uid == -1) {
                        return originUid;
                    }
                    pidCacheInfo.lastTime = curTime;
                    return pidCacheInfo.uid;
                }
            }
            pidCache.put(callingPid, new PidCacheInfo(callingPid, -1, curTime));
        }
        if ((uidRet = VActivityManager.get().getUidByPid(callingPid)) == 9000) {
            uidRet = 1000;
        }
        if (EnablePidInfoCache) {
            long curTime2 = System.currentTimeMillis();
            if (pidCache.size() >= MaxCachePidInfoZise) {
                Iterator<Map.Entry<Integer, PidCacheInfo>> iterator = pidCache.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, PidCacheInfo> entry = iterator.next();
                    if (curTime2 - entry.getValue().lastTime <= (long)MaxCachePidInfoTime) continue;
                    iterator.remove();
                }
            }
            pidCache.put(callingPid, new PidCacheInfo(callingPid, uidRet, curTime2));
        }
        return uidRet;
    }

    private static Field getField(Class topClass, String fieldName) throws NoSuchFieldException {
        while (topClass != null && topClass != Object.class) {
            try {
                Field field = topClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            }
            catch (Exception exception) {
                topClass = topClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    public static long getArtMethod(Member member) {
        if (artMethodField == null) {
            try {
                artMethodField = NativeEngine.getField(Method.class, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMLGIVNAZjHh4w")));
            }
            catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
        if (artMethodField == null) {
            return 0L;
        }
        try {
            return (Long)artMethodField.get(member);
        }
        catch (IllegalAccessException e) {
            return 0L;
        }
    }

    private static DexOverride findDexOverride(String originDexPath) {
        for (DexOverride dexOverride : sDexOverrides) {
            if (!dexOverride.originDexPath.equals(originDexPath)) continue;
            return dexOverride;
        }
        return null;
    }

    public static void onOpenDexFileNative(String[] params) {
        String dexCanonicalPath;
        DexOverride override;
        String dexPath = params[0];
        if (dexPath != null && (override = NativeEngine.findDexOverride(dexCanonicalPath = NativeEngine.getCanonicalPath(dexPath))) != null) {
            VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0iM28gFi9iHjMiPxhSVg==")) + override.newOdexPath);
            if (override.newDexPath != null) {
                params[0] = override.newDexPath;
            }
            String oatPath = override.newDexPath;
            if (override.originOdexPath != null) {
                String oatCanonicalPath = NativeEngine.getCanonicalPath(oatPath);
                if (oatCanonicalPath.equals(override.originOdexPath)) {
                    params[1] = override.newOdexPath;
                }
            } else {
                params[1] = override.newOdexPath;
            }
        }
        VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oy06M2ohMCtnHDwzKhcMQG4gBi9vNysZPSouD0s0TCN5MAo8OF82Vg==")), params[0], params[1]);
    }

    private static String getCanonicalPath(String path) {
        File file = new File(path);
        try {
            return file.getCanonicalPath();
        }
        catch (IOException e) {
            e.printStackTrace();
            return file.getAbsolutePath();
        }
    }

    private static native void nativeLaunchEngine(Context var0, Object[] var1, String var2, String var3, boolean var4, int var5, int var6, int var7);

    private static native void nativeMark();

    private static native String nativeReverseRedirectedPath(String var0);

    private static native String nativeGetRedirectedPath(String var0);

    private static native void nativeIORedirect(String var0, String var1);

    private static native void nativeIOWhitelist(String var0);

    private static native void nativeIOForbid(String var0);

    private static native void nativeIOReadOnly(String var0);

    private static native void nativeEnableIORedirect(String var0, String var1, String var2, int var3, String var4, String var5);

    public static int onGetUid(int uid) {
        if (VClient.get().getClientConfig() == null) {
            return uid;
        }
        return VClient.get().getBaseVUid();
    }

    static {
        LIB_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4MD2kFSFo="));
        TAG = NativeEngine.class.getSimpleName();
        sDexOverrides = new ArrayList<DexOverride>();
        sFlag = false;
        sEnabled = false;
        EnablePidInfoCache = false;
        try {
            System.loadLibrary(VirtualRuntime.adjustLibName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4MD2kFSFo="))));
        }
        catch (Throwable e) {
            VLog.e(TAG, VLog.getStackTraceString(e));
        }
        EnablePidInfoCache = true;
        pidCache = new HashMap();
        MaxCachePidInfoTime = 10000;
        MaxCachePidInfoZise = 64;
        REDIRECT_LISTS = new LinkedList<Pair<String, String>>();
    }

    static class PidCacheInfo {
        long lastTime;
        int pid;
        int uid;

        public PidCacheInfo(int pid, int uid, long lastTime) {
            this.pid = pid;
            this.uid = uid;
            this.lastTime = lastTime;
        }

        public String toString() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhgYPGMzJCljHjAJKj0+DWgKTS9rVw5F")) + this.pid + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186I2UVMzM=")) + this.uid + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186DmsaLAZuHgY3KARXVg==")) + this.lastTime + '}';
        }
    }
}

