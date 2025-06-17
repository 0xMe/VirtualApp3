/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageInstaller$SessionParams
 *  android.content.pm.PermissionGroupInfo
 *  android.content.pm.PermissionInfo
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build$VERSION
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.proxies.pm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IInterface;
import android.os.RemoteException;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.IPackageInstaller;
import com.lody.virtual.server.pm.installer.SessionParams;
import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mirror.android.content.pm.ParceledListSlice;

class MethodProxies {
    private static final int MATCH_FACTORY_ONLY = 0x200000;
    private static final int MATCH_ANY_USER = 0x400000;
    private static String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OwguLGUFGixpESw1LRccPWoFSFo="));

    MethodProxies() {
    }

    static class GetApplicationBlockedSettingAsUser
    extends MethodProxy {
        GetApplicationBlockedSettingAsUser() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJgHgY5Lwg2MW8FMBRlEQYqIz4uIGkgLD9qHhodLwZbD2cKLD9vJ1RF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            GetApplicationBlockedSettingAsUser.replaceLastUserId(args);
            return method.invoke(who, args);
        }
    }

    @TargetApi(19)
    static class QueryIntentContentProviders extends MethodProxy {
        QueryIntentContentProviders() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAglgNwo/Kj42EW8FMAZrARo/IQcMKWYwGi9uASg8"));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int userId = VUserHandle.myUserId();
            int flags = (int)this.getIntOrLongValue(args[2]);
            List<ResolveInfo> appResult = VPackageManager.get().queryIntentContentProviders((Intent)args[0], (String)args[1], flags, userId);
            replaceLastUserId(args);
            Object _hostResult = method.invoke(who, args);
            List<ResolveInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);
            if (hostResult != null) {
                Iterator<ResolveInfo> iterator = hostResult.iterator();

                while(true) {
                    while(iterator.hasNext()) {
                        ResolveInfo info = (ResolveInfo)iterator.next();
                        if (info != null && info.providerInfo != null && isOutsidePackage(info.providerInfo.packageName)) {
                            ComponentFixer.fixOutsideComponentInfo(info.providerInfo);
                        } else {
                            iterator.remove();
                        }
                    }

                    appResult.addAll(hostResult);
                    break;
                }
            }

            return ParceledListSliceCompat.isReturnParceledListSlice(method) ? ParceledListSliceCompat.create(appResult) : appResult;
        }

        public boolean isEnable() {
            return isAppProcess();
        }
    }

    static class SetPackageStoppedState
    extends MethodProxy {
        SetPackageStoppedState() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGcFJCljJCA9KAYqLm8KTQJrASwQKgg+CmIFSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            SetPackageStoppedState.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return SetPackageStoppedState.isAppProcess();
        }
    }

    @TargetApi(value=17)
    static class GetPermissionFlags
    extends MethodProxy {
        GetPermissionFlags() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARgDgYpIy0cDW8bHiRoASA6"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String name = (String)args[0];
            String packageName = (String)args[1];
            int userId = (Integer)args[2];
            PermissionInfo info = VPackageManager.get().getPermissionInfo(name, 0);
            if (info != null) {
                return 0;
            }
            args[2] = GetPermissionFlags.getRealUserId();
            return method.invoke(who, args);
        }
    }

    static class GetReceiverInfo
    extends MethodProxy {
        GetReceiverInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcjNCliDgYuKAguXm8VHiU="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName componentName = (ComponentName)args[0];
            if (GetReceiverInfo.getHostPkg().equals(componentName.getPackageName())) {
                return method.invoke(who, args);
            }
            int flags = (int)this.getIntOrLongValue(args[1]);
            ActivityInfo info = VPackageManager.get().getReceiverInfo(componentName, flags, 0);
            if (info == null) {
                GetReceiverInfo.replaceLastUserId(args);
                info = (ActivityInfo)method.invoke(who, args);
                if (info == null || !GetReceiverInfo.isOutsidePackage(info.packageName)) {
                    return null;
                }
                ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info);
            }
            return info;
        }

        @Override
        public boolean isEnable() {
            return GetReceiverInfo.isAppProcess();
        }
    }


    static class QueryIntentReceivers extends MethodProxy {
        QueryIntentReceivers() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAglgNwo/Kj42AmkjAitqDiQgKS02Vg=="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int userId = VUserHandle.myUserId();
            int flags = (int)this.getIntOrLongValue(args[2]);
            List<ResolveInfo> appResult = VPackageManager.get().queryIntentReceivers((Intent)args[0], (String)args[1], flags, userId);
            Object _hostResult = method.invoke(who, args);
            List<ResolveInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);
            if (hostResult != null) {
                Iterator<ResolveInfo> iterator = hostResult.iterator();

                while(true) {
                    while(iterator.hasNext()) {
                        ResolveInfo info = (ResolveInfo)iterator.next();
                        if (info != null && info.activityInfo != null && !this.isAppPkg(info.activityInfo.packageName) && isOutsidePackage(info.activityInfo.packageName)) {
                            ComponentFixer.fixOutsideComponentInfo(info.activityInfo);
                        } else {
                            iterator.remove();
                        }
                    }

                    appResult.addAll(hostResult);
                    break;
                }
            }

            return slice ? ParceledListSliceCompat.create(appResult) : appResult;
        }
    }


    static class GetInstalledPackages extends MethodProxy {
        GetInstalledPackages() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgNmHiAoKhcMPmIzQSlqJzguLhc2Vg=="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int flags = (int)this.getIntOrLongValue(args[0]);
            int userId = VUserHandle.myUserId();
            List<PackageInfo> packageInfos = VPackageManager.get().getInstalledPackages(flags, userId);
            replaceLastUserId(args);
            Object _hostResult = method.invoke(who, args);
            List<PackageInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);

            PackageInfo info;
            for(Iterator<PackageInfo> it = hostResult.iterator(); it.hasNext(); ComponentFixer.fixOutsideApplicationInfo(info.applicationInfo)) {
                info = (PackageInfo)it.next();
                if (VirtualCore.get().isAppInstalled(info.packageName) || !isOutsidePackage(info.packageName)) {
                    it.remove();
                }
            }

            packageInfos.addAll(hostResult);
            if (ParceledListSliceCompat.isReturnParceledListSlice(method)) {
                return ParceledListSliceCompat.create(packageInfos);
            } else {
                return packageInfos;
            }
        }

        public boolean isEnable() {
            return isAppProcess();
        }
    }

    static class GetInstalledApplications extends MethodProxy {
        GetInstalledApplications() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgNmHiAoKhcMPmYgTQJlER4qLRcqI2AgRTY="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int flags = (int)this.getIntOrLongValue(args[0]);
            int userId = VUserHandle.myUserId();
            List<ApplicationInfo> appInfos = VPackageManager.get().getInstalledApplications(flags, userId);
            Object _hostResult = method.invoke(who, args);
            List<ApplicationInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);

            ApplicationInfo info;
            for(Iterator<ApplicationInfo> it = hostResult.iterator(); it.hasNext(); ComponentFixer.fixOutsideApplicationInfo(info)) {
                info = (ApplicationInfo)it.next();
                if (VirtualCore.get().isAppInstalled(info.packageName) || !isOutsidePackage(info.packageName)) {
                    it.remove();
                }
            }

            appInfos.addAll(hostResult);
            if (slice) {
                return ParceledListSliceCompat.create(appInfos);
            } else {
                return appInfos;
            }
        }
    }

    static class SetComponentEnabledSetting
    extends MethodProxy {
        SetComponentEnabledSetting() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMzGiNhHh42KAcYLmEjMDdoNwIgLgU2J2YVFixsNDxF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName componentName = (ComponentName)args[0];
            int newState = (Integer)args[1];
            int flags = (Integer)args[2];
            VPackageManager.get().setComponentEnabledSetting(componentName, newState, flags, SetComponentEnabledSetting.getAppUserId());
            return 0;
        }

        @Override
        public boolean isEnable() {
            return SetComponentEnabledSetting.isAppProcess();
        }
    }

    static class GetProviderInfo
    extends MethodProxy {
        GetProviderInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFiVmNAYwKAguXm8VHiU="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName componentName = (ComponentName)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            if (GetProviderInfo.getHostPkg().equals(componentName.getPackageName())) {
                GetProviderInfo.replaceLastUserId(args);
                return method.invoke(who, args);
            }
            int userId = VUserHandle.myUserId();
            ProviderInfo info = VPackageManager.get().getProviderInfo(componentName, flags, userId);
            if (info == null) {
                GetProviderInfo.replaceLastUserId(args);
                info = (ProviderInfo)method.invoke(who, args);
                if (info == null || !GetProviderInfo.isOutsidePackage(info.packageName)) {
                    return null;
                }
                ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info);
            }
            return info;
        }
    }

    static class GetApplicationInfo
    extends MethodProxy {
        GetApplicationInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJgHgY5Lwg2MW8FMAllNyQc"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkg = (String)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            int userId = VUserHandle.myUserId();
            if (pkg.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmkzGi5oJwYbKgg+I2AwLDU=")))) {
                return VPackageManager.get().getApplicationInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmowRSVvNx4vLhcMD04wFipqJB4bKQhbIGwjSFo=")), flags, userId);
            }
            if (GetApplicationInfo.getHostPkg().equals(pkg)) {
                GetApplicationInfo.replaceLastUserId(args);
                return method.invoke(who, args);
            }
            ApplicationInfo info = VPackageManager.get().getApplicationInfo(pkg, flags, userId);
            if (info != null) {
                return info;
            }
            GetApplicationInfo.replaceLastUserId(args);
            info = (ApplicationInfo)method.invoke(who, args);
            if (info == null || !GetApplicationInfo.isOutsidePackage(info.packageName)) {
                return null;
            }
            ComponentFixer.fixOutsideApplicationInfo(info);
            return info;
        }

        @Override
        public boolean isEnable() {
            return GetApplicationInfo.isAppProcess();
        }
    }

    static class ResolveIntent
    extends MethodProxy {
        ResolveIntent() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKWozHj5iDAY2LBcMDmUzSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            Intent intent = (Intent)args[0];
            String resolvedType = (String)args[1];
            int flags = (int)this.getIntOrLongValue(args[2]);
            int userId = VUserHandle.myUserId();
            ResolveInfo resolveInfo = VPackageManager.get().resolveIntent(intent, resolvedType, flags, userId);
            if (resolveInfo == null) {
                ResolveIntent.replaceLastUserId(args);
                ResolveInfo info = (ResolveInfo)method.invoke(who, args);
                if (info != null && ResolveIntent.isOutsidePackage(info.activityInfo.packageName)) {
                    ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info.activityInfo);
                    return info;
                }
            }
            return resolveInfo;
        }
    }

    static class ActivitySupportsIntent
    extends MethodProxy {
        ActivitySupportsIntent() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQYPLAgmKm8KRQZsJR4bKgguKmYVSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName component = (ComponentName)args[0];
            Intent intent = (Intent)args[1];
            String resolvedType = (String)args[2];
            return VPackageManager.get().activitySupportsIntent(component, intent, resolvedType);
        }
    }

    static class DeletePackage
    extends MethodProxy {
        DeletePackage() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguDmgaMCtpHiA5KS0iM2kjSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkgName = (String)args[0];
            try {
                VirtualCore.get().uninstallPackage(pkgName);
                IPackageDeleteObserver2 observer = (IPackageDeleteObserver2)args[1];
                if (observer != null) {
                    observer.onPackageDeleted(pkgName, 0, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgACGhSBlo=")));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return 0;
        }
    }

    static class getNameForUid
    extends MethodProxy {
        getNameForUid() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIjJCNiDDw1IzwMMWkzSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            int uid = (Integer)args[0];
            if (uid == 9000) {
                uid = getNameForUid.getVUid();
            }
            return VPackageManager.get().getNameForUid(uid);
        }

        @Override
        public boolean isEnable() {
            return getNameForUid.isAppProcess();
        }
    }

    static class checkUidSignatures
    extends MethodProxy {
        checkUidSignatures() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQVBjDgoPKQc6Dm4gBgVsNyg6"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            int uid2;
            int uid1 = (Integer)args[0];
            if (uid1 == (uid2 = ((Integer)args[1]).intValue()) || uid1 == VEnvironment.OUTSIDE_APP_UID || uid2 == VEnvironment.OUTSIDE_APP_UID) {
                return 0;
            }
            String[] pkgs1 = VirtualCore.getPM().getPackagesForUid(uid1);
            String[] pkgs2 = VirtualCore.getPM().getPackagesForUid(uid2);
            if (pkgs1 == null || pkgs1.length == 0) {
                return -4;
            }
            if (pkgs2 == null || pkgs2.length == 0) {
                return -4;
            }
            return VPackageManager.get().checkSignatures(pkgs1[0], pkgs2[0]);
        }
    }

    @SuppressLint(value={"PackageManagerGetSignatures"})
    static class CheckSignatures
    extends MethodProxy {
        CheckSignatures() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQV5jDjg2Lwg2LWoVGgM="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (args.length == 2 && args[0] instanceof String && args[1] instanceof String) {
                String pkgNameOne = (String)args[0];
                String pkgNameTwo = (String)args[1];
                if (TextUtils.equals((CharSequence)pkgNameOne, (CharSequence)pkgNameTwo)) {
                    return 0;
                }
                return VPackageManager.get().checkSignatures(pkgNameOne, pkgNameTwo);
            }
            return method.invoke(who, args);
        }
    }

    static class SetApplicationEnabledSetting
    extends MethodProxy {
        SetApplicationEnabledSetting() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaIAJgHgY5Lwg2MW8FMBVlNzgpLAguIGkgLD9qHhodLwhSVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            SetApplicationEnabledSetting.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return SetApplicationEnabledSetting.isAppProcess();
        }
    }

    static class QueryContentProviders extends MethodProxy {
        QueryContentProviders() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAhNgJFkgKAcYLmIwRSVvNx4vLhcMDw=="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            String processName = (String)args[0];
            int vuid = (Integer)args[1];
            int flags = (int)this.getIntOrLongValue(args[2]);
            List<ProviderInfo> infos = VPackageManager.get().queryContentProviders(processName, vuid, 0);
            Object _hostResult = method.invoke(who, args);
            if (_hostResult != null) {
                List<ProviderInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);

                ProviderInfo info;
                for(Iterator<ProviderInfo> it = hostResult.iterator(); it.hasNext(); ComponentFixer.fixOutsideComponentInfo(info)) {
                    info = (ProviderInfo)it.next();
                    if (this.isAppPkg(info.packageName) || !isOutsidePackage(info.packageName)) {
                        it.remove();
                    }
                }

                infos.addAll(hostResult);
            }

            return slice ? ParceledListSliceCompat.create(infos) : infos;
        }
    }

    static class GetPersistentApplications
    extends MethodProxy {
        GetPersistentApplications() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARhJAYpLBcMDmUxQQJsEQIaLT4+CmMKAillJ1RF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (ParceledListSliceCompat.isReturnParceledListSlice(method)) {
                return ParceledListSliceCompat.create(new ArrayList(0));
            }
            return new ArrayList(0);
        }
    }

    static class QuerySliceContentProviders
    extends QueryContentProviders {
        QuerySliceContentProviders() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAl5gHgY5KAUqDW8aBitlNCwRKS4AMmMKFiBlNyxF"));
        }
    }

    static class GetPackagesForUid
    extends MethodProxy {
        GetPackagesForUid() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAgqWW8KRVBqASxF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String[] pkgs;
            if (VClient.get().getClientConfig() == null) {
                return method.invoke(who, args);
            }
            int uid = (Integer)args[0];
            if (uid == 1000) {
                return method.invoke(who, args);
            }
            if (uid == GetPackagesForUid.getRealUid()) {
                uid = VClient.get().getVUid();
            }
            if ((pkgs = VPackageManager.get().getPackagesForUid(uid)) == null) {
                return null;
            }
            return pkgs;
        }

        @Override
        public boolean isEnable() {
            return GetPackagesForUid.isAppProcess();
        }
    }

    static class CheckPermission
    extends MethodProxy {
        CheckPermission() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQUxiASw3KQgqL2wjNCY="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String permName = (String)args[0];
            String pkgName = (String)args[1];
            int userId = VUserHandle.myUserId();
            return VPackageManager.get().checkPermission(permName, pkgName, userId);
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            return super.afterCall(who, method, args, result);
        }

        @Override
        public boolean isEnable() {
            return CheckPermission.isAppProcess();
        }
    }

    static class AddPackageToPreferred
    extends MethodProxy {
        AddPackageToPreferred() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGcFJCljJCA9KAY2DWIwRStrNyg5KS4uIA=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return 0;
        }
    }

    static class CanRequestPackageInstalls
    extends MethodProxy {
        CanRequestPackageInstalls() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGcjNAFmDjApLBYmOW4FJDdrJyhPLC02Cn0KTTdlJ1RF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (VirtualCore.get().getAppCallback() != null) {
                return true;
            }
            MethodParameterUtils.replaceFirstAppPkg(args);
            CanRequestPackageInstalls.replaceLastUserId(args);
            return super.call(who, method, args);
        }
    }

    static class GetApplicationEnabledSetting
    extends MethodProxy {
        GetApplicationEnabledSetting() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJgHgY5Lwg2MW8FMBVlNzgpLAguIGkgLD9qHhodLwhSVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkg = (String)args[0];
            if (this.isAppPkg(pkg)) {
                return 1;
            }
            if (GetApplicationEnabledSetting.isOutsidePackage(pkg)) {
                args[1] = 0;
                return method.invoke(who, args);
            }
            return 2;
        }
    }

    static class SetApplicationBlockedSettingAsUser
    extends MethodProxy {
        SetApplicationBlockedSettingAsUser() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaIAJgHgY5Lwg2MW8FMBRlEQYqIz4uIGkgLD9qHhodLwZbD2cKLD9vJ1RF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            SetApplicationBlockedSettingAsUser.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return SetApplicationBlockedSettingAsUser.isAppProcess();
        }
    }

    static class DeleteApplicationCacheFiles
    extends MethodProxy {
        DeleteApplicationCacheFiles() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguDmgaMCtlASQsKhccP24gBi9lJxoALRg2LGIIICxsHgo8"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ApplicationInfo info;
            String pkg = (String)args[0];
            IPackageDataObserver observer = (IPackageDataObserver)args[1];
            if (pkg.equals(DeleteApplicationCacheFiles.getAppPkg()) && (info = VPackageManager.get().getApplicationInfo(pkg, 0, DeleteApplicationCacheFiles.getAppUserId())) != null) {
                File dir = new File(info.dataDir);
                FileUtils.deleteDir(dir);
                dir.mkdirs();
                if (Build.VERSION.SDK_INT >= 24) {
                    dir = new File(info.deviceProtectedDataDir);
                    FileUtils.deleteDir(dir);
                    dir.mkdirs();
                }
                if (observer != null) {
                    observer.onRemoveCompleted(pkg, true);
                }
                return 0;
            }
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return DeleteApplicationCacheFiles.isAppProcess();
        }
    }

    static final class GetPackageInfo
    extends MethodProxy {
        GetPackageInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAUcDmkVNFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkg = (String)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            int userId = VUserHandle.myUserId();
            if ((flags & 0x400000) != 0) {
                args[1] = flags &= 0xFFBFFFFF;
            }
            if ((flags & 0x200000) != 0) {
                GetPackageInfo.replaceLastUserId(args);
                return method.invoke(who, args);
            }
            PackageInfo packageInfo = VPackageManager.get().getPackageInfo(pkg, flags, userId);
            if (packageInfo != null) {
                return packageInfo;
            }
            GetPackageInfo.replaceLastUserId(args);
            packageInfo = (PackageInfo)method.invoke(who, args);
            if (packageInfo != null && GetPackageInfo.isOutsidePackage(packageInfo.packageName)) {
                ComponentFixer.fixOutsideApplicationInfo(packageInfo.applicationInfo);
                return packageInfo;
            }
            return null;
        }
    }

    static class GetPermissionInfo
    extends MethodProxy {
        GetPermissionInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARgDgYpIy0cDW8bLCZrNwZF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String name = (String)args[0];
            int flags = (Integer)args[args.length - 1];
            PermissionInfo info = VPackageManager.get().getPermissionInfo(name, flags);
            if (info != null) {
                return info;
            }
            return super.call(who, method, args);
        }

        @Override
        public boolean isEnable() {
            return GetPermissionInfo.isAppProcess();
        }
    }

    static class GetPermissionGroupInfo
    extends MethodProxy {
        GetPermissionGroupInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARgDgYpIy0cDW8bEgRlJCg7OxgcImAjSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String name = (String)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            PermissionGroupInfo info = VPackageManager.get().getPermissionGroupInfo(name, flags);
            if (info != null) {
                return info;
            }
            return super.call(who, method, args);
        }

        @Override
        public boolean isEnable() {
            return GetPermissionGroupInfo.isAppProcess();
        }
    }

    static class ClearPackagePersistentPreferredActivities
    extends MethodProxy {
        ClearPackagePersistentPreferredActivities() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFkx9DigxLwc6PWIzGgRsJx46KgguKmYbODVuDjguLBdfJ2sbJDVsAR4hJQgMIG4FMFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            ClearPackagePersistentPreferredActivities.replaceLastUserId(args);
            return method.invoke(who, args);
        }
    }

    static class ResolveService
    extends MethodProxy {
        ResolveService() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKWozHj5iDyg/Iz4+MW4FGlo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            Intent intent = (Intent)args[0];
            String resolvedType = (String)args[1];
            int flags = (int)this.getIntOrLongValue(args[2]);
            int userId = VUserHandle.myUserId();
            ResolveInfo resolveInfo = VPackageManager.get().resolveService(intent, resolvedType, flags, userId);
            if (resolveInfo != null) {
                return resolveInfo;
            }
            ResolveService.replaceLastUserId(args);
            ResolveInfo info = (ResolveInfo)method.invoke(who, args);
            if (info != null && ResolveService.isOutsidePackage(info.serviceInfo.packageName)) {
                ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info.serviceInfo);
                return info;
            }
            return null;
        }
    }

    static class QueryIntentActivities extends MethodProxy {
        QueryIntentActivities() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAglgNwo/Kj42E24KBi9vNx4/IxguDw=="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int userId = VUserHandle.myUserId();
            List<ResolveInfo> appResult = VPackageManager.get().queryIntentActivities((Intent)args[0], (String)args[1], (int)this.getIntOrLongValue(args[2]), userId);
            replaceLastUserId(args);
            Object _hostResult = method.invoke(who, args);
            if (_hostResult != null) {
                List<ResolveInfo> hostResult = (List)(slice ? ParceledListSlice.getList.call(_hostResult, new Object[0]) : _hostResult);
                if (hostResult != null) {
                    Iterator<ResolveInfo> iterator = hostResult.iterator();

                    while(true) {
                        while(iterator.hasNext()) {
                            ResolveInfo info = (ResolveInfo)iterator.next();
                            if (info != null && info.activityInfo != null && isOutsidePackage(info.activityInfo.packageName)) {
                                ComponentFixer.fixOutsideComponentInfo(info.activityInfo);
                            } else {
                                iterator.remove();
                            }
                        }

                        appResult.addAll(hostResult);
                        break;
                    }
                }
            }

            return slice ? ParceledListSliceCompat.create(appResult) : appResult;
        }

        public boolean isEnable() {
            return isAppProcess();
        }
    }

    static class GetPackageGidsEtc
    extends GetPackageGids {
        GetPackageGidsEtc() {
        }

        @Override
        public String getMethodName() {
            return super.getMethodName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcqOQ=="));
        }
    }

    static class IsPackageForzen
    extends MethodProxy {
        IsPackageForzen() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2AmsVLCF9Djg/ID1fKGgVGiY="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return false;
        }

        @Override
        public boolean isEnable() {
            return IsPackageForzen.isAppProcess();
        }
    }

    static class GetPermissions
    extends MethodProxy {
        GetPermissions() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARgDgYpIy0cDW8aAlo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            GetPermissions.replaceLastUserId(args);
            return method.invoke(who, args);
        }
    }

    static class QueryIntentServices extends MethodProxy {
        QueryIntentServices() {
        }

        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gAglgNwo/Kj42AWkgRT5qATAgKT5SVg=="));
        }

        public Object call(Object who, Method method, Object... args) throws Throwable {
            boolean slice = ParceledListSliceCompat.isReturnParceledListSlice(method);
            int userId = VUserHandle.myUserId();
            Intent intent = (Intent)args[0];
            List<ResolveInfo> appResult = VPackageManager.get().queryIntentServices(intent, (String)args[1], (int)this.getIntOrLongValue(args[2]), userId);
            replaceLastUserId(args);
            Object _hostResult = method.invoke(who, args);
            if (_hostResult != null) {
                Object obj;
                if (slice) {
                    obj = ParceledListSlice.getList.call(_hostResult, new Object[0]);
                } else {
                    obj = _hostResult;
                }

                List<ResolveInfo> hostResult = (List)obj;
                if (hostResult != null) {
                    Iterator<ResolveInfo> iterator = hostResult.iterator();

                    label38:
                    while(true) {
                        ResolveInfo info;
                        do {
                            if (!iterator.hasNext()) {
                                break label38;
                            }

                            info = (ResolveInfo)iterator.next();
                            if (isHostIntent(intent)) {
                                break label38;
                            }
                        } while(info != null && info.serviceInfo != null && isOutsidePackage(info.serviceInfo.packageName));

                        iterator.remove();
                    }

                    appResult.addAll(hostResult);
                }
            }

            return slice ? ParceledListSliceCompat.create(appResult) : appResult;
        }

        public boolean isEnable() {
            return isAppProcess();
        }
    }

    static class ResolveContentProvider
    extends MethodProxy {
        ResolveContentProvider() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKWozHj5iDCg1Kj42PW8aBkxsNwY9IxgqJ2EzSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String name = (String)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            int userId = VUserHandle.myUserId();
            ProviderInfo info = VPackageManager.get().resolveContentProvider(name, flags, userId);
            if (info == null) {
                ResolveContentProvider.replaceLastUserId(args);
                info = (ProviderInfo)method.invoke(who, args);
                if (info != null && ResolveContentProvider.isOutsidePackage(info.packageName)) {
                    return info;
                }
            }
            return info;
        }
    }

    static class ClearPackagePreferredActivities
    extends MethodProxy {
        ClearPackagePreferredActivities() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFkx9DigxLwc6PWIwRStrNyg5KS4uIGUKND9vATgiIz42J2wjSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            return method.invoke(who, args);
        }
    }

    static class RevokeRuntimePermission
    extends MethodProxy {
        RevokeRuntimePermission() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uLmozQStpNzA2LBccD2kmTStsNw4aKT02I2AgRVo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            RevokeRuntimePermission.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return RevokeRuntimePermission.isAppProcess();
        }
    }

    static class GetPackageGids
    extends MethodProxy {
        GetPackageGids() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAU6MWkwAlo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            GetPackageGids.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return GetPackageGids.isAppProcess();
        }
    }

    static class GetPackageInstaller
    extends MethodProxy {
        GetPackageInstaller() {
        }

        @Override
        public boolean isEnable() {
            return GetPackageInstaller.isAppProcess();
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAUcDmoKBjdlEQIgKS5SVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            IInterface installer = (IInterface)method.invoke(who, args);
            final IPackageInstaller vInstaller = VPackageManager.get().getPackageInstaller();
            return Proxy.newProxyInstance(installer.getClass().getClassLoader(), installer.getClass().getInterfaces(), new InvocationHandler(){

                @TargetApi(value=21)
                private Object createSession(Object proxy, Method method, Object[] args) throws RemoteException {
                    SessionParams params = SessionParams.create((PackageInstaller.SessionParams)args[0]);
                    String installerPackageName = (String)args[1];
                    return vInstaller.createSession(params, installerPackageName, VUserHandle.myUserId());
                }

                /*
                 * Exception decompiling
                 */
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    /*
                     * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                     * 
                     * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                     *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                     *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
                     *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                     *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                     *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                     *     at org.benf.cfr.reader.Main.main(Main.java:54)
                     */
                    throw new IllegalStateException("Decompilation failed");
                }
            });
        }
    }

    static class GetPackageUidEtc
    extends GetPackageUid {
        GetPackageUidEtc() {
        }

        @Override
        public String getMethodName() {
            return super.getMethodName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcqOQ=="));
        }
    }

    static class GetActivityInfo
    extends MethodProxy {
        GetActivityInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVLAZjATwzLBgcXm8VHiU="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName componentName = (ComponentName)args[0];
            if (GetActivityInfo.getHostPkg().equals(componentName.getPackageName())) {
                return method.invoke(who, args);
            }
            int userId = VUserHandle.myUserId();
            int flags = (int)this.getIntOrLongValue(args[1]);
            ActivityInfo info = VPackageManager.get().getActivityInfo(componentName, flags, userId);
            if (info == null) {
                GetActivityInfo.replaceLastUserId(args);
                info = (ActivityInfo)method.invoke(who, args);
                if (info == null || !GetActivityInfo.isOutsidePackage(info.packageName)) {
                    return null;
                }
                ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info);
            }
            return info;
        }

        @Override
        public boolean isEnable() {
            return GetActivityInfo.isAppProcess();
        }
    }

    static class GetPackageUid
    extends MethodProxy {
        GetPackageUid() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAYMMWkzSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkgName = (String)args[0];
            MethodParameterUtils.replaceFirstAppPkg(args);
            GetPackageUid.replaceLastUserId(args);
            if (this.isAppPkg(pkgName) || GetPackageUid.isOutsidePackage(pkgName)) {
                return method.invoke(who, args);
            }
            return -1;
        }

        @Override
        public boolean isEnable() {
            return GetPackageUid.isAppProcess();
        }
    }

    static class GetServiceInfo
    extends MethodProxy {
        GetServiceInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGczNARmNAY5KAUcDmkVNFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName componentName = (ComponentName)args[0];
            int flags = (int)this.getIntOrLongValue(args[1]);
            int userId = VUserHandle.myUserId();
            ServiceInfo info = VPackageManager.get().getServiceInfo(componentName, flags, userId);
            if (info != null) {
                return info;
            }
            GetServiceInfo.replaceLastUserId(args);
            info = (ServiceInfo)method.invoke(who, args);
            if (info == null || !GetServiceInfo.isOutsidePackage(info.packageName)) {
                return null;
            }
            ComponentFixer.fixOutsideComponentInfo((ComponentInfo)info);
            return info;
        }

        @Override
        public boolean isEnable() {
            return GetServiceInfo.isAppProcess();
        }
    }

    static class RemovePackageFromPreferred
    extends MethodProxy {
        RemovePackageFromPreferred() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtpHiA5KS0iM2khHgRlJw4RKS4uImIFMDVuDjBF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            return method.invoke(who, args);
        }
    }

    static class GetComponentEnabledSetting
    extends MethodProxy {
        GetComponentEnabledSetting() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMzGiNhHh42KAcYLmEjMDdoNwIgLgU2J2YVFixsNDxF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            ComponentName component = (ComponentName)args[0];
            return VPackageManager.get().getComponentEnabledSetting(component, GetComponentEnabledSetting.getAppUserId());
        }
    }

    static class GetPreferredActivities
    extends MethodProxy {
        GetPreferredActivities() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFitiNDAqIz0MPmYjAgZqDiQaKggYJ2EjSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceLastAppPkg(args);
            return method.invoke(who, args);
        }
    }

    static class GetInstallerPackageName
    extends MethodProxy {
        GetInstallerPackageName() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgNmHiAoKhcMKGIzQSlqJzguLhYcO2AKLFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmUVGiZrER4bLj5SVg=="));
        }

        @Override
        public boolean isEnable() {
            return GetInstallerPackageName.isAppProcess();
        }
    }

    static class IsPackageAvailable
    extends MethodProxy {
        IsPackageAvailable() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2AmsVLCF9Djg/Jwg+OWwjODdoNwIg"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkgName = (String)args[0];
            if (this.isAppPkg(pkgName)) {
                return true;
            }
            IsPackageAvailable.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return IsPackageAvailable.isAppProcess();
        }
    }

    static class CanForwardTo
    extends MethodProxy {
        CanForwardTo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGAjGgRmJCAqKBY2DQ=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            int targetUserId;
            int sourceUserId = (Integer)args[2];
            return sourceUserId == (targetUserId = ((Integer)args[3]).intValue());
        }

        @Override
        public boolean isEnable() {
            return CanForwardTo.isAppProcess();
        }
    }

    static class GetUidForSharedUser
    extends MethodProxy {
        GetUidForSharedUser() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQVAixqNB4qOy0AOWoVGixnDjAgKS5SVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String sharedUserName = (String)args[0];
            return VirtualCore.get().getUidForSharedUser(sharedUserName);
        }

        @Override
        public boolean isEnable() {
            return GetUidForSharedUser.isAppProcess();
        }
    }

    static class GetSharedLibraries
    extends MethodProxy {
        GetSharedLibraries() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGczRTdhNDAwIhccOGoVQQRqASg6"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            int flags = (int)this.getIntOrLongValue(args[1]);
            if ((flags & 0x400000) != 0) {
                args[1] = flags &= 0xFFBFFFFF;
            }
            args[0] = GetSharedLibraries.getHostPkg();
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return GetSharedLibraries.isAppProcess();
        }
    }

    static class CheckPackageStartable
    extends MethodProxy {
        CheckPackageStartable() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQUx9DigxLwc6PWIKBjdsNCwsLS4EJw=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String pkg = (String)args[0];
            if (this.isAppPkg(pkg)) {
                return 0;
            }
            CheckPackageStartable.replaceLastUserId(args);
            return method.invoke(who, args);
        }

        @Override
        public boolean isEnable() {
            return CheckPackageStartable.isAppProcess();
        }
    }

    static class FreeStorageAndNotify
    extends MethodProxy {
        FreeStorageAndNotify() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT0MM2gYLAZgJyw7KC0ME28VBgBlJCwaLi0YVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            IPackageDataObserver observer = (IPackageDataObserver)args[args.length - 1];
            if (observer != null) {
                observer.onRemoveCompleted(FreeStorageAndNotify.getAppPkg(), true);
            }
            return 0;
        }

        @Override
        public boolean isEnable() {
            return FreeStorageAndNotify.isAppProcess();
        }
    }

    static class FreeStorage
    extends MethodProxy {
        FreeStorage() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT0MM2gYLAZgJyw7KC0MVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            IntentSender sender = (IntentSender)ArrayUtils.getFirst(args, IntentSender.class);
            if (sender != null) {
                sender.sendIntent(FreeStorage.getHostContext(), 0, null, null, null);
            }
            return 0;
        }

        @Override
        public boolean isEnable() {
            return FreeStorage.isAppProcess();
        }
    }
}

