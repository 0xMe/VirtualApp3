/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.PermissionGroupInfo
 *  android.content.pm.PermissionInfo
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.lody.virtual.server.pm;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.helper.compat.PermissionCompat;
import com.lody.virtual.helper.utils.SignaturesUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.ReceiverInfo;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.interfaces.IPackageManager;
import com.lody.virtual.server.pm.ComponentStateManager;
import com.lody.virtual.server.pm.IntentResolver;
import com.lody.virtual.server.pm.PackageCacheManager;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.ProviderIntentResolver;
import com.lody.virtual.server.pm.VUserManagerService;
import com.lody.virtual.server.pm.installer.VPackageInstallerService;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VPackageManagerService
extends IPackageManager.Stub {
    static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ihg+OWUzJC1iDF07Kj0iM2kgRVo="));
    static final Comparator<ResolveInfo> sResolvePrioritySorter = new Comparator<ResolveInfo>(){

        @Override
        public int compare(ResolveInfo r1, ResolveInfo r2) {
            int v1 = r1.priority;
            int v2 = r2.priority;
            if (v1 != v2) {
                return v1 > v2 ? -1 : 1;
            }
            v1 = r1.preferredOrder;
            v2 = r2.preferredOrder;
            if (v1 != v2) {
                return v1 > v2 ? -1 : 1;
            }
            if (r1.isDefault != r2.isDefault) {
                return r1.isDefault ? -1 : 1;
            }
            v1 = r1.match;
            v2 = r2.match;
            if (v1 != v2) {
                return v1 > v2 ? -1 : 1;
            }
            return 0;
        }
    };
    private static final Singleton<VPackageManagerService> gService = new Singleton<VPackageManagerService>(){

        @Override
        protected VPackageManagerService create() {
            return new VPackageManagerService();
        }
    };
    private static final Comparator<ProviderInfo> sProviderInitOrderSorter = new Comparator<ProviderInfo>(){

        @Override
        public int compare(ProviderInfo p1, ProviderInfo p2) {
            int v1 = p1.initOrder;
            int v2 = p2.initOrder;
            return Integer.compare(v2, v1);
        }
    };
    private final ActivityIntentResolver mActivities = new ActivityIntentResolver();
    private final ServiceIntentResolver mServices = new ServiceIntentResolver();
    private final ActivityIntentResolver mReceivers = new ActivityIntentResolver();
    private final ProviderIntentResolver mProviders = new ProviderIntentResolver();
    private final HashMap<ComponentName, VPackage.ProviderComponent> mProvidersByComponent = new HashMap();
    private final HashMap<String, VPackage.PermissionComponent> mPermissions = new HashMap();
    private final HashMap<String, VPackage.PermissionGroupComponent> mPermissionGroups = new HashMap();
    private final HashMap<String, VPackage.ProviderComponent> mProvidersByAuthority = new HashMap();
    private final Map<String, VPackage> mPackages = PackageCacheManager.PACKAGE_CACHE;
    private final Map<String, String[]> mDangerousPermissions = new HashMap<String, String[]>();

    private VPackageManagerService() {
    }

    public static void systemReady() {
        new VUserManagerService(VirtualCore.get().getContext(), VPackageManagerService.get(), new char[0], VPackageManagerService.get().mPackages);
    }

    public static VPackageManagerService get() {
        return gService.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void analyzePackageLocked(VPackage pkg) {
        VPackage.Component a;
        int i;
        int N = pkg.activities.size();
        for (i = 0; i < N; ++i) {
            a = pkg.activities.get(i);
            if (a.info.processName == null) {
                a.info.processName = a.info.packageName;
            }
            this.mActivities.addActivity((VPackage.ActivityComponent)a, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
        }
        N = pkg.services.size();
        for (i = 0; i < N; ++i) {
            a = pkg.services.get(i);
            if (((VPackage.ServiceComponent)a).info.processName == null) {
                ((VPackage.ServiceComponent)a).info.processName = ((VPackage.ServiceComponent)a).info.packageName;
            }
            this.mServices.addService((VPackage.ServiceComponent)a);
        }
        N = pkg.receivers.size();
        for (i = 0; i < N; ++i) {
            a = pkg.receivers.get(i);
            if (a.info.processName == null) {
                a.info.processName = a.info.packageName;
            }
            this.mReceivers.addActivity((VPackage.ActivityComponent)a, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uOWgVAj5iASxF")));
        }
        N = pkg.providers.size();
        for (i = 0; i < N; ++i) {
            VPackage.ProviderComponent p = pkg.providers.get(i);
            if (p.info.processName == null) {
                p.info.processName = p.info.packageName;
            }
            this.mProviders.addProvider(p);
            String[] names = p.info.authority.split(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OC5SVg==")));
            HashMap<String, VPackage.ProviderComponent> hashMap = this.mProvidersByAuthority;
            synchronized (hashMap) {
                for (String name : names) {
                    if (this.mProvidersByAuthority.containsKey(name)) continue;
                    this.mProvidersByAuthority.put(name, p);
                }
            }
            this.mProvidersByComponent.put(p.getComponentName(), p);
        }
        N = pkg.permissions.size();
        for (i = 0; i < N; ++i) {
            VPackage.PermissionComponent permission2 = pkg.permissions.get(i);
            this.mPermissions.put(permission2.info.name, permission2);
        }
        N = pkg.permissionGroups.size();
        for (i = 0; i < N; ++i) {
            VPackage.PermissionGroupComponent group = pkg.permissionGroups.get(i);
            this.mPermissionGroups.put(group.className, group);
        }
        Map<String, String[]> map = this.mDangerousPermissions;
        synchronized (map) {
            this.mDangerousPermissions.put(pkg.packageName, PermissionCompat.findDangerousPermissions(pkg.requestedPermissions));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String[] getDangerousPermissions(String packageName) {
        Map<String, String[]> map = this.mDangerousPermissions;
        synchronized (map) {
            return this.mDangerousPermissions.get(packageName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void deletePackageLocked(VPackage pkg) {
        VPackage.Component a;
        int i;
        if (pkg == null) {
            return;
        }
        int N = pkg.activities.size();
        for (i = 0; i < N; ++i) {
            a = pkg.activities.get(i);
            this.mActivities.removeActivity((VPackage.ActivityComponent)a, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
        }
        N = pkg.services.size();
        for (i = 0; i < N; ++i) {
            a = pkg.services.get(i);
            this.mServices.removeService((VPackage.ServiceComponent)a);
        }
        N = pkg.receivers.size();
        for (i = 0; i < N; ++i) {
            a = pkg.receivers.get(i);
            this.mReceivers.removeActivity((VPackage.ActivityComponent)a, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uOWgVAj5iASxF")));
        }
        N = pkg.providers.size();
        for (i = 0; i < N; ++i) {
            VPackage.ProviderComponent p = pkg.providers.get(i);
            this.mProviders.removeProvider(p);
            String[] names = p.info.authority.split(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OC5SVg==")));
            HashMap<String, VPackage.ProviderComponent> hashMap = this.mProvidersByAuthority;
            synchronized (hashMap) {
                for (String name : names) {
                    this.mProvidersByAuthority.remove(name);
                }
            }
            this.mProvidersByComponent.remove(p.getComponentName());
        }
        N = pkg.permissions.size();
        for (i = 0; i < N; ++i) {
            VPackage.PermissionComponent permission2 = pkg.permissions.get(i);
            this.mPermissions.remove(permission2.className);
        }
        N = pkg.permissionGroups.size();
        for (i = 0; i < N; ++i) {
            VPackage.PermissionGroupComponent group = pkg.permissionGroups.get(i);
            this.mPermissionGroups.remove(group.className);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getSharedLibraries(String packageName) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p != null) {
                return p.usesLibraries;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PackageInfo getPackageInfo(String packageName, int flags, int userId) {
        this.checkUserId(userId);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                return this.generatePackageInfo(p, ps, flags, userId);
            }
        }
        return null;
    }

    private PackageInfo generatePackageInfo(VPackage p, PackageSetting ps, int flags, int userId) {
        flags = this.updateFlagsNought(flags);
        return PackageParserEx.generatePackageInfo(p, ps, flags, ps.firstInstallTime, ps.lastUpdateTime, ps.readUserState(userId), userId, ps.isRunInExtProcess());
    }

    private int updateFlagsNought(int flags) {
        if (Build.VERSION.SDK_INT < 24) {
            return flags;
        }
        if ((flags & 0xC0000) == 0) {
            flags |= 0xC0000;
        }
        return flags;
    }

    private void checkUserId(int userId) {
        if (!VUserManagerService.get().exists(userId)) {
            throw new SecurityException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLmsVHi9iVyQvIy0MKGQjASg=")) + userId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ActivityInfo getActivityInfo(ComponentName component, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(component.getPackageName());
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                VPackage.ActivityComponent a = (VPackage.ActivityComponent)this.mActivities.mActivities.get(component);
                if (a != null) {
                    ActivityInfo activityInfo = PackageParserEx.generateActivityInfo(a, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
                    ComponentFixer.fixComponentInfo((ComponentInfo)activityInfo);
                    return activityInfo;
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean activitySupportsIntent(ComponentName component, Intent intent, String resolvedType) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage.ActivityComponent a = (VPackage.ActivityComponent)this.mActivities.mActivities.get(component);
            if (a == null) {
                return false;
            }
            for (int i = 0; i < a.intents.size(); ++i) {
                if (((VPackage.ActivityIntentInfo)a.intents.get((int)i)).filter.match(intent.getAction(), resolvedType, intent.getScheme(), intent.getData(), intent.getCategories(), TAG) < 0) continue;
                return true;
            }
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ActivityInfo getReceiverInfo(ComponentName component, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(component.getPackageName());
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                VPackage.ActivityComponent a = (VPackage.ActivityComponent)this.mReceivers.mActivities.get(component);
                if (a != null && ps.isEnabledAndMatchLPr((ComponentInfo)a.info, flags, userId)) {
                    ActivityInfo receiverInfo = PackageParserEx.generateActivityInfo(a, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
                    ComponentFixer.fixComponentInfo((ComponentInfo)receiverInfo);
                    return receiverInfo;
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServiceInfo getServiceInfo(ComponentName component, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(component.getPackageName());
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                VPackage.ServiceComponent s = (VPackage.ServiceComponent)this.mServices.mServices.get(component);
                if (s != null) {
                    ServiceInfo serviceInfo = PackageParserEx.generateServiceInfo(s, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
                    ComponentFixer.fixComponentInfo((ComponentInfo)serviceInfo);
                    return serviceInfo;
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ProviderInfo getProviderInfo(ComponentName component, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(component.getPackageName());
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                VPackage.ProviderComponent provider = this.mProvidersByComponent.get(component);
                if (provider != null && ps.isEnabledAndMatchLPr((ComponentInfo)provider.info, flags, userId)) {
                    ProviderInfo providerInfo = PackageParserEx.generateProviderInfo(provider, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
                    ComponentFixer.fixComponentInfo((ComponentInfo)providerInfo);
                    return providerInfo;
                }
            }
        }
        return null;
    }

    @Override
    public ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        List<ResolveInfo> query = this.queryIntentActivities(intent, resolvedType, flags, userId);
        return this.chooseBestActivity(intent, resolvedType, flags, query);
    }

    private ResolveInfo chooseBestActivity(Intent intent, String resolvedType, int flags, List<ResolveInfo> query) {
        if (query != null) {
            int N = query.size();
            if (N == 1) {
                return query.get(0);
            }
            if (N > 1) {
                ResolveInfo r0 = query.get(0);
                ResolveInfo r1 = query.get(1);
                if (r0.priority != r1.priority || r0.preferredOrder != r1.preferredOrder || r0.isDefault != r1.isDefault) {
                    return query.get(0);
                }
                ResolveInfo ri = this.findPreferredActivity(intent, resolvedType, flags, query, r0.priority);
                if (ri != null) {
                    return ri;
                }
                return query.get(0);
            }
        }
        return null;
    }

    private ResolveInfo findPreferredActivity(Intent intent, String resolvedType, int flags, List<ResolveInfo> query, int priority) {
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ResolveInfo> queryIntentActivities(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ComponentName comp = intent.getComponent();
        if (comp == null && intent.getSelector() != null) {
            intent = intent.getSelector();
            comp = intent.getComponent();
        }
        if (comp != null) {
            ArrayList<ResolveInfo> list = new ArrayList<ResolveInfo>(1);
            ActivityInfo ai = this.getActivityInfo(comp, flags, userId);
            if (ai != null) {
                ResolveInfo ri = new ResolveInfo();
                ri.activityInfo = ai;
                list.add(ri);
            }
            return list;
        }
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            String pkgName = intent.getPackage();
            if (pkgName == null) {
                return this.mActivities.queryIntent(intent, resolvedType, flags, userId);
            }
            VPackage pkg = this.mPackages.get(pkgName);
            if (pkg != null) {
                return this.mActivities.queryIntentForPackage(intent, resolvedType, flags, pkg.activities, userId);
            }
            return Collections.emptyList();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ResolveInfo> queryIntentReceivers(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ComponentName comp = intent.getComponent();
        if (comp == null && intent.getSelector() != null) {
            intent = intent.getSelector();
            comp = intent.getComponent();
        }
        if (comp != null) {
            ArrayList<ResolveInfo> list = new ArrayList<ResolveInfo>(1);
            ActivityInfo ai = this.getReceiverInfo(comp, flags, userId);
            if (ai != null) {
                ResolveInfo ri = new ResolveInfo();
                ri.activityInfo = ai;
                list.add(ri);
            }
            return list;
        }
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            String pkgName = intent.getPackage();
            if (pkgName == null) {
                return this.mReceivers.queryIntent(intent, resolvedType, flags, userId);
            }
            VPackage pkg = this.mPackages.get(pkgName);
            if (pkg != null) {
                return this.mReceivers.queryIntentForPackage(intent, resolvedType, flags, pkg.receivers, userId);
            }
            return Collections.emptyList();
        }
    }

    @Override
    public ResolveInfo resolveService(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        List<ResolveInfo> query = this.queryIntentServices(intent, resolvedType, flags, userId);
        if (query != null && query.size() >= 1) {
            return query.get(0);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ResolveInfo> queryIntentServices(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ComponentName comp = intent.getComponent();
        if (comp == null && intent.getSelector() != null) {
            intent = intent.getSelector();
            comp = intent.getComponent();
        }
        if (comp != null) {
            ArrayList<ResolveInfo> list = new ArrayList<ResolveInfo>(1);
            ServiceInfo si = this.getServiceInfo(comp, flags, userId);
            if (si != null) {
                ResolveInfo ri = new ResolveInfo();
                ri.serviceInfo = si;
                list.add(ri);
            }
            return list;
        }
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            String pkgName = intent.getPackage();
            if (pkgName == null) {
                return this.mServices.queryIntent(intent, resolvedType, flags, userId);
            }
            VPackage pkg = this.mPackages.get(pkgName);
            if (pkg != null) {
                return this.mServices.queryIntentForPackage(intent, resolvedType, flags, pkg.services, userId);
            }
            return Collections.emptyList();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @TargetApi(value=19)
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, String resolvedType, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ComponentName comp = intent.getComponent();
        if (comp == null && intent.getSelector() != null) {
            intent = intent.getSelector();
            comp = intent.getComponent();
        }
        if (comp != null) {
            ArrayList<ResolveInfo> list = new ArrayList<ResolveInfo>(1);
            ProviderInfo pi = this.getProviderInfo(comp, flags, userId);
            if (pi != null) {
                ResolveInfo ri = new ResolveInfo();
                ri.providerInfo = pi;
                list.add(ri);
            }
            return list;
        }
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            String pkgName = intent.getPackage();
            if (pkgName == null) {
                return this.mProviders.queryIntent(intent, resolvedType, flags, userId);
            }
            VPackage pkg = this.mPackages.get(pkgName);
            if (pkg != null) {
                return this.mProviders.queryIntentForPackage(intent, resolvedType, flags, pkg.providers, userId);
            }
            return Collections.emptyList();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public VParceledListSlice<ProviderInfo> queryContentProviders(String processName, int vuid, int flags) {
        int userId = VUserHandle.getUserId(vuid);
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ArrayList<ProviderInfo> finalList = new ArrayList<ProviderInfo>(3);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            for (VPackage.ProviderComponent p : this.mProvidersByAuthority.values()) {
                PackageSetting ps = (PackageSetting)p.owner.mExtras;
                if (!ps.isEnabledAndMatchLPr((ComponentInfo)p.info, flags, userId) || processName != null && (ps.appId != VUserHandle.getAppId(vuid) || !p.info.processName.equals(processName))) continue;
                ProviderInfo providerInfo = PackageParserEx.generateProviderInfo(p, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
                finalList.add(providerInfo);
            }
        }
        if (!finalList.isEmpty()) {
            Collections.sort(finalList, sProviderInitOrderSorter);
        }
        return new VParceledListSlice<ProviderInfo>(finalList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public VParceledListSlice<PackageInfo> getInstalledPackages(int flags, int userId) {
        this.checkUserId(userId);
        ArrayList<PackageInfo> pkgList = new ArrayList<PackageInfo>(this.mPackages.size());
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            for (VPackage p : this.mPackages.values()) {
                PackageSetting ps;
                PackageInfo info = this.generatePackageInfo(p, ps = (PackageSetting)p.mExtras, flags, userId);
                if (info == null) continue;
                pkgList.add(info);
            }
        }
        return new VParceledListSlice<PackageInfo>(pkgList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public VParceledListSlice<ApplicationInfo> getInstalledApplications(int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        ArrayList<ApplicationInfo> list = new ArrayList<ApplicationInfo>(this.mPackages.size());
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            for (VPackage p : this.mPackages.values()) {
                PackageSetting ps;
                ApplicationInfo info = PackageParserEx.generateApplicationInfo(p, flags, (ps = (PackageSetting)p.mExtras).readUserState(userId), userId, ps.isRunInExtProcess());
                if (info == null) continue;
                list.add(info);
            }
        }
        return new VParceledListSlice<ApplicationInfo>(list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ReceiverInfo> getReceiverInfos(String packageName, String processName, int userId) {
        ArrayList<ReceiverInfo> list = new ArrayList<ReceiverInfo>();
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p == null) {
                return Collections.emptyList();
            }
            PackageSetting ps = (PackageSetting)p.mExtras;
            for (VPackage.ActivityComponent receiver : p.receivers) {
                if (!ps.isEnabledAndMatchLPr((ComponentInfo)receiver.info, 0, userId) || !receiver.info.processName.equals(processName)) continue;
                ArrayList<IntentFilter> filters = new ArrayList<IntentFilter>();
                for (VPackage.ActivityIntentInfo intentInfo : receiver.intents) {
                    filters.add(intentInfo.filter);
                }
                list.add(new ReceiverInfo(receiver.info, filters));
            }
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PermissionInfo getPermissionInfo(String name, int flags) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage.PermissionComponent p = this.mPermissions.get(name);
            if (p != null) {
                return new PermissionInfo(p.info);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) {
        ArrayList<PermissionInfo> infos = new ArrayList<PermissionInfo>();
        if (group != null) {
            Map<String, VPackage> map = this.mPackages;
            synchronized (map) {
                for (VPackage.PermissionComponent p : this.mPermissions.values()) {
                    if (!p.info.group.equals(group)) continue;
                    infos.add(p.info);
                }
            }
        }
        return infos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage.PermissionGroupComponent p = this.mPermissionGroups.get(name);
            if (p != null) {
                return new PermissionGroupInfo(p.info);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            int N = this.mPermissionGroups.size();
            ArrayList<PermissionGroupInfo> out = new ArrayList<PermissionGroupInfo>(N);
            for (VPackage.PermissionGroupComponent pg : this.mPermissionGroups.values()) {
                out.add(new PermissionGroupInfo(pg.info));
            }
            return out;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ProviderInfo resolveContentProvider(String name, int flags, int userId) {
        PackageSetting ps;
        ProviderInfo providerInfo;
        VPackage.ProviderComponent provider;
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        HashMap<String, VPackage.ProviderComponent> hashMap = this.mProvidersByAuthority;
        synchronized (hashMap) {
            provider = this.mProvidersByAuthority.get(name);
        }
        if (provider != null && (providerInfo = PackageParserEx.generateProviderInfo(provider, flags, (ps = (PackageSetting)provider.owner.mExtras).readUserState(userId), userId, ps.isRunInExtProcess())) != null) {
            if (!ps.isEnabledAndMatchLPr((ComponentInfo)providerInfo, flags, userId)) {
                return null;
            }
            ComponentFixer.fixComponentInfo((ComponentInfo)providerInfo);
            return providerInfo;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ApplicationInfo getApplicationInfo(String packageName, int flags, int userId) {
        this.checkUserId(userId);
        flags = this.updateFlagsNought(flags);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                return PackageParserEx.generateApplicationInfo(p, flags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String[] getPackagesForUid(int uid) {
        int userId = VUserHandle.getUserId(uid);
        this.checkUserId(userId);
        VPackageManagerService vPackageManagerService = this;
        synchronized (vPackageManagerService) {
            ArrayList<String> pkgList = new ArrayList<String>(2);
            for (VPackage p : this.mPackages.values()) {
                PackageSetting settings = (PackageSetting)p.mExtras;
                if (VUserHandle.getUid(userId, settings.appId) != uid && uid != 9001) continue;
                pkgList.add(p.packageName);
            }
            if (pkgList.isEmpty()) {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFJCljJCA9KAgqWW8KRVBqAS8rKS4uCmYFMCl5HiQdOD4AL2waMCt7Dgo0Jy42O2pTAlo=")));
                return null;
            }
            return pkgList.toArray(new String[0]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getPackageUid(String packageName, int userId) {
        this.checkUserId(userId);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p != null) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                return VUserHandle.getUid(userId, ps.appId);
            }
            return -1;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getNameForUid(int uid) {
        int appId = VUserHandle.getAppId(uid);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            for (VPackage p : this.mPackages.values()) {
                PackageSetting ps = (PackageSetting)p.mExtras;
                if (ps.appId != appId) continue;
                return ps.packageName;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> querySharedPackages(String packageName) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            VPackage p = this.mPackages.get(packageName);
            if (p == null || p.mSharedUserId == null) {
                return Collections.EMPTY_LIST;
            }
            ArrayList<String> list = new ArrayList<String>();
            for (VPackage one : this.mPackages.values()) {
                if (!TextUtils.equals((CharSequence)one.mSharedUserId, (CharSequence)p.mSharedUserId)) continue;
                list.add(one.packageName);
            }
            return list;
        }
    }

    @Override
    public IBinder getPackageInstaller() {
        return VPackageInstallerService.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setComponentEnabledSetting(ComponentName component, int newState, int flags, int userId) {
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMzGiNhHh42KAcYLmEjMDdoNwIgLgU2J2YVFixsND8p")) + component + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgcM2w2LAZ9AQo/PTkmVg==")) + newState + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgiDmsVPAN3MCRF")) + flags);
        if (component == null) {
            return;
        }
        this.checkUserId(userId);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            ComponentStateManager.user(userId).set(component, newState);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getComponentEnabledSetting(ComponentName component, int userId) {
        if (component == null) {
            return 0;
        }
        this.checkUserId(userId);
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            return ComponentStateManager.user(userId).get(component);
        }
    }

    void createNewUser(int userId, File userPath) {
        for (VPackage p : this.mPackages.values()) {
            PackageSetting setting = (PackageSetting)p.mExtras;
            setting.modifyUserState(userId);
        }
    }

    void cleanUpUser(int userId) {
        for (VPackage p : this.mPackages.values()) {
            PackageSetting ps = (PackageSetting)p.mExtras;
            ps.removeUser(userId);
        }
        ComponentStateManager.user(userId).clearAll();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PermissionInfo findPermission(String permission2) {
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            for (VPackage pkg : this.mPackages.values()) {
                ArrayList<VPackage.PermissionComponent> permissions = pkg.permissions;
                if (permissions == null) continue;
                for (VPackage.PermissionComponent component : permissions) {
                    if (component.info == null || !TextUtils.equals((CharSequence)permission2, (CharSequence)component.info.name)) continue;
                    return component.info;
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean hasRequestedPermission(String permission2, String packageName) {
        VPackage pkg;
        Map<String, VPackage> map = this.mPackages;
        synchronized (map) {
            pkg = this.mPackages.get(packageName);
        }
        if (pkg != null && pkg.requestedPermissions != null) {
            return pkg.requestedPermissions.contains(permission2);
        }
        return false;
    }

    @Override
    public int checkPermission(boolean isExt, String permission2, String pkgName, int userId) {
        if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCwYDG4YLB9hDCwTJQZbH2QxGg9nMgZPLys2E30jSFo=")).equals(permission2) || StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCwYDG4YLB9hDCwTJQZbH2QxGg9nMgZPLys2E30hRVV9JSQR")).equals(permission2)) {
            return -1;
        }
        PermissionInfo permissionInfo = this.getPermissionInfo(permission2, 0);
        if (permissionInfo != null) {
            return 0;
        }
        return VirtualCore.getPM().checkPermission(permission2, StubManifest.getStubPackageName(isExt));
    }

    @Override
    public int checkSignatures(String pkg1, String pkg2) {
        if (TextUtils.equals((CharSequence)pkg1, (CharSequence)pkg2)) {
            return 0;
        }
        PackageInfo pkgOne = this.getPackageInfo(pkg1, 64, 0);
        PackageInfo pkgTwo = this.getPackageInfo(pkg2, 64, 0);
        if (pkgOne == null) {
            try {
                pkgOne = VirtualCore.get().getHostPackageManager().getPackageInfo(pkg1, 64L);
            }
            catch (PackageManager.NameNotFoundException e) {
                return -4;
            }
        }
        if (pkgTwo == null) {
            try {
                pkgTwo = VirtualCore.get().getHostPackageManager().getPackageInfo(pkg2, 64L);
            }
            catch (PackageManager.NameNotFoundException e) {
                return -4;
            }
        }
        return SignaturesUtils.compareSignatures(pkgOne.signatures, pkgTwo.signatures);
    }

    public int checkUidPermission(boolean isExt, String permission2, int uid) {
        PermissionInfo info = this.getPermissionInfo(permission2, 0);
        if (info != null) {
            return 0;
        }
        return VirtualCore.getPM().checkPermission(permission2, StubManifest.getStubPackageName(isExt));
    }

    private static final class ServiceIntentResolver
    extends IntentResolver<VPackage.ServiceIntentInfo, ResolveInfo> {
        private final HashMap<ComponentName, VPackage.ServiceComponent> mServices = new HashMap();
        private int mFlags;

        private ServiceIntentResolver() {
        }

        @Override
        public List<ResolveInfo> queryIntent(Intent intent, String resolvedType, boolean defaultOnly, int userId) {
            this.mFlags = defaultOnly ? 65536 : 0;
            return super.queryIntent(intent, resolvedType, defaultOnly, userId);
        }

        public List<ResolveInfo> queryIntent(Intent intent, String resolvedType, int flags, int userId) {
            this.mFlags = flags;
            return super.queryIntent(intent, resolvedType, (flags & 0x10000) != 0, userId);
        }

        public List<ResolveInfo> queryIntentForPackage(Intent intent, String resolvedType, int flags, ArrayList<VPackage.ServiceComponent> packageServices, int userId) {
            if (packageServices == null) {
                return null;
            }
            this.mFlags = flags;
            boolean defaultOnly = (flags & 0x10000) != 0;
            int N = packageServices.size();
            ArrayList<F[]> listCut = new ArrayList<F[]>(N);
            for (int i = 0; i < N; ++i) {
                ArrayList intentFilters = packageServices.get((int)i).intents;
                if (intentFilters == null || intentFilters.size() <= 0) continue;
                VPackage.ServiceIntentInfo[] array2 = new VPackage.ServiceIntentInfo[intentFilters.size()];
                intentFilters.toArray(array2);
                listCut.add(array2);
            }
            return super.queryIntentFromList(intent, resolvedType, defaultOnly, listCut, userId);
        }

        public final void addService(VPackage.ServiceComponent s) {
            this.mServices.put(s.getComponentName(), s);
            int NI = s.intents.size();
            for (int j = 0; j < NI; ++j) {
                VPackage.ServiceIntentInfo intent = (VPackage.ServiceIntentInfo)s.intents.get(j);
                this.addFilter(intent);
            }
        }

        public final void removeService(VPackage.ServiceComponent s) {
            this.mServices.remove(s.getComponentName());
            int NI = s.intents.size();
            for (int j = 0; j < NI; ++j) {
                VPackage.ServiceIntentInfo intent = (VPackage.ServiceIntentInfo)s.intents.get(j);
                this.removeFilter(intent);
            }
        }

        @Override
        protected boolean allowFilterResult(VPackage.ServiceIntentInfo filter, List<ResolveInfo> dest) {
            ServiceInfo filterSi = filter.service.info;
            for (int i = dest.size() - 1; i >= 0; --i) {
                ServiceInfo destAi = dest.get((int)i).serviceInfo;
                if (!ObjectsCompat.equals(destAi.name, filterSi.name) || !ObjectsCompat.equals(destAi.packageName, filterSi.packageName)) continue;
                return false;
            }
            return true;
        }

        protected VPackage.ServiceIntentInfo[] newArray(int size) {
            return new VPackage.ServiceIntentInfo[size];
        }

        @Override
        protected boolean isFilterStopped(VPackage.ServiceIntentInfo filter) {
            return false;
        }

        @Override
        protected boolean isPackageForFilter(String packageName, VPackage.ServiceIntentInfo info) {
            return packageName.equals(info.service.owner.packageName);
        }

        @Override
        protected ResolveInfo newResult(VPackage.ServiceIntentInfo filter, int match, int userId) {
            VPackage.ServiceComponent service = filter.service;
            PackageSetting ps = (PackageSetting)service.owner.mExtras;
            if (!ps.isEnabledAndMatchLPr((ComponentInfo)service.info, this.mFlags, userId)) {
                return null;
            }
            ServiceInfo si = PackageParserEx.generateServiceInfo(service, this.mFlags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
            if (si == null) {
                return null;
            }
            ResolveInfo res = new ResolveInfo();
            res.serviceInfo = si;
            if ((this.mFlags & 0x40) != 0) {
                res.filter = filter.filter;
            }
            res.priority = filter.filter.getPriority();
            res.preferredOrder = service.owner.mPreferredOrder;
            res.match = match;
            res.isDefault = filter.hasDefault;
            res.labelRes = filter.labelRes;
            res.nonLocalizedLabel = filter.nonLocalizedLabel;
            res.icon = filter.icon;
            return res;
        }

        @Override
        protected void sortResults(List<ResolveInfo> results) {
            Collections.sort(results, sResolvePrioritySorter);
        }

        @Override
        protected void dumpFilter(PrintWriter out, String prefix, VPackage.ServiceIntentInfo filter) {
        }

        @Override
        protected Object filterToLabel(VPackage.ServiceIntentInfo filter) {
            return filter.service;
        }

        @Override
        protected void dumpFilterLabel(PrintWriter out, String prefix, Object label, int count) {
        }
    }

    private static final class ActivityIntentResolver
    extends IntentResolver<VPackage.ActivityIntentInfo, ResolveInfo> {
        private final HashMap<ComponentName, VPackage.ActivityComponent> mActivities = new HashMap();
        private int mFlags;

        private ActivityIntentResolver() {
        }

        @Override
        public List<ResolveInfo> queryIntent(Intent intent, String resolvedType, boolean defaultOnly, int userId) {
            this.mFlags = defaultOnly ? 65536 : 0;
            return super.queryIntent(intent, resolvedType, defaultOnly, userId);
        }

        List<ResolveInfo> queryIntent(Intent intent, String resolvedType, int flags, int userId) {
            this.mFlags = flags;
            return super.queryIntent(intent, resolvedType, (flags & 0x10000) != 0, userId);
        }

        List<ResolveInfo> queryIntentForPackage(Intent intent, String resolvedType, int flags, ArrayList<VPackage.ActivityComponent> packageActivities, int userId) {
            if (packageActivities == null) {
                return null;
            }
            this.mFlags = flags;
            boolean defaultOnly = (flags & 0x10000) != 0;
            int N = packageActivities.size();
            ArrayList<F[]> listCut = new ArrayList<F[]>(N);
            for (int i = 0; i < N; ++i) {
                ArrayList intentFilters = packageActivities.get((int)i).intents;
                if (intentFilters == null || intentFilters.size() <= 0) continue;
                VPackage.ActivityIntentInfo[] array2 = new VPackage.ActivityIntentInfo[intentFilters.size()];
                intentFilters.toArray(array2);
                listCut.add(array2);
            }
            return super.queryIntentFromList(intent, resolvedType, defaultOnly, listCut, userId);
        }

        public final void addActivity(VPackage.ActivityComponent a, String type) {
            this.mActivities.put(a.getComponentName(), a);
            int NI = a.intents.size();
            for (int j = 0; j < NI; ++j) {
                VPackage.ActivityIntentInfo intent = (VPackage.ActivityIntentInfo)a.intents.get(j);
                if (intent.filter.getPriority() > 0 && StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")).equals(type)) {
                    intent.filter.setPriority(0);
                    Log.w((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ihg+OWUzJC1iDF07Kj0iM2kgRVo=")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ihg+OWUzJC1iCiRF")) + a.info.applicationInfo.packageName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhhfP283IDd9JwozLD0cLmgnTVo=")) + a.className + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcmCWwFRChhESwzKi4uMWUwLyh5Mz87PAQ6ImAjMCZvDh4gOD0cKXhTIFo="))));
                }
                this.addFilter(intent);
            }
        }

        public final void removeActivity(VPackage.ActivityComponent a, String type) {
            this.mActivities.remove(a.getComponentName());
            int NI = a.intents.size();
            for (int j = 0; j < NI; ++j) {
                VPackage.ActivityIntentInfo intent = (VPackage.ActivityIntentInfo)a.intents.get(j);
                this.removeFilter(intent);
            }
        }

        @Override
        protected boolean allowFilterResult(VPackage.ActivityIntentInfo filter, List<ResolveInfo> dest) {
            ActivityInfo filterAi = filter.activity.info;
            for (int i = dest.size() - 1; i >= 0; --i) {
                ActivityInfo destAi = dest.get((int)i).activityInfo;
                if (!ObjectsCompat.equals(destAi.name, filterAi.name) || !ObjectsCompat.equals(destAi.packageName, filterAi.packageName)) continue;
                return false;
            }
            return true;
        }

        protected VPackage.ActivityIntentInfo[] newArray(int size) {
            return new VPackage.ActivityIntentInfo[size];
        }

        @Override
        protected boolean isFilterStopped(VPackage.ActivityIntentInfo filter) {
            return false;
        }

        @Override
        protected boolean isPackageForFilter(String packageName, VPackage.ActivityIntentInfo info) {
            return packageName.equals(info.activity.owner.packageName);
        }

        @Override
        protected ResolveInfo newResult(VPackage.ActivityIntentInfo info, int match, int userId) {
            VPackage.ActivityComponent activity = info.activity;
            PackageSetting ps = (PackageSetting)activity.owner.mExtras;
            if (!ps.isEnabledAndMatchLPr((ComponentInfo)activity.info, this.mFlags, userId)) {
                return null;
            }
            ActivityInfo ai = PackageParserEx.generateActivityInfo(activity, this.mFlags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
            if (ai == null) {
                return null;
            }
            ResolveInfo res = new ResolveInfo();
            res.activityInfo = ai;
            if ((this.mFlags & 0x40) != 0) {
                res.filter = info.filter;
            }
            res.priority = info.filter.getPriority();
            res.preferredOrder = activity.owner.mPreferredOrder;
            res.match = match;
            res.isDefault = info.hasDefault;
            res.labelRes = info.labelRes;
            res.nonLocalizedLabel = info.nonLocalizedLabel;
            res.icon = info.icon;
            return res;
        }

        @Override
        protected void sortResults(List<ResolveInfo> results) {
            Collections.sort(results, sResolvePrioritySorter);
        }

        @Override
        protected void dumpFilter(PrintWriter out, String prefix, VPackage.ActivityIntentInfo filter) {
        }

        @Override
        protected Object filterToLabel(VPackage.ActivityIntentInfo filter) {
            return filter.activity;
        }

        @Override
        protected void dumpFilterLabel(PrintWriter out, String prefix, Object label, int count) {
        }
    }
}

