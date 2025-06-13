/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.pm.ComponentInfo
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 */
package com.lody.virtual.server.pm;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.server.pm.IntentResolver;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.VPackageManagerService;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

final class ProviderIntentResolver
extends IntentResolver<VPackage.ProviderIntentInfo, ResolveInfo> {
    private final HashMap<ComponentName, VPackage.ProviderComponent> mProviders = new HashMap();
    private int mFlags;

    ProviderIntentResolver() {
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

    public List<ResolveInfo> queryIntentForPackage(Intent intent, String resolvedType, int flags, ArrayList<VPackage.ProviderComponent> packageProviders, int userId) {
        if (packageProviders == null) {
            return null;
        }
        this.mFlags = flags;
        boolean defaultOnly = (flags & 0x10000) != 0;
        int N = packageProviders.size();
        ArrayList<F[]> listCut = new ArrayList<F[]>(N);
        for (int i = 0; i < N; ++i) {
            ArrayList intentFilters = packageProviders.get((int)i).intents;
            if (intentFilters == null || intentFilters.size() <= 0) continue;
            VPackage.ProviderIntentInfo[] array2 = new VPackage.ProviderIntentInfo[intentFilters.size()];
            intentFilters.toArray(array2);
            listCut.add(array2);
        }
        return super.queryIntentFromList(intent, resolvedType, defaultOnly, listCut, userId);
    }

    public final void addProvider(VPackage.ProviderComponent p) {
        if (this.mProviders.containsKey(p.getComponentName())) {
            VLog.w(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ihg+OWUzJC1iDF07Kj0iM2kgRVo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhcMD2wjAixiAS88")) + p.getComponentName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg+Dm8jNDdiERk8KBcMPGwjMCtrV1wrIxgmKmAjMCxsNDxF")), new Object[0]);
            return;
        }
        this.mProviders.put(p.getComponentName(), p);
        int NI = p.intents.size();
        for (int j = 0; j < NI; ++j) {
            VPackage.ProviderIntentInfo intent = (VPackage.ProviderIntentInfo)p.intents.get(j);
            this.addFilter(intent);
        }
    }

    public final void removeProvider(VPackage.ProviderComponent p) {
        this.mProviders.remove(p.getComponentName());
        int NI = p.intents.size();
        for (int j = 0; j < NI; ++j) {
            VPackage.ProviderIntentInfo intent = (VPackage.ProviderIntentInfo)p.intents.get(j);
            this.removeFilter(intent);
        }
    }

    @Override
    @TargetApi(value=19)
    protected boolean allowFilterResult(VPackage.ProviderIntentInfo filter, List<ResolveInfo> dest) {
        ProviderInfo filterPi = filter.provider.info;
        for (int i = dest.size() - 1; i >= 0; --i) {
            ProviderInfo destPi = dest.get((int)i).providerInfo;
            if (!ObjectsCompat.equals(destPi.name, filterPi.name) || !ObjectsCompat.equals(destPi.packageName, filterPi.packageName)) continue;
            return false;
        }
        return true;
    }

    protected VPackage.ProviderIntentInfo[] newArray(int size) {
        return new VPackage.ProviderIntentInfo[size];
    }

    @Override
    protected boolean isFilterStopped(VPackage.ProviderIntentInfo filter) {
        return false;
    }

    @Override
    protected boolean isPackageForFilter(String packageName, VPackage.ProviderIntentInfo info) {
        return packageName.equals(info.provider.owner.packageName);
    }

    @Override
    @TargetApi(value=19)
    protected ResolveInfo newResult(VPackage.ProviderIntentInfo filter, int match, int userId) {
        VPackage.ProviderComponent provider = filter.provider;
        if (!PackageParserEx.isEnabledLPr((ComponentInfo)provider.info, this.mFlags, userId)) {
            return null;
        }
        PackageSetting ps = (PackageSetting)provider.owner.mExtras;
        ProviderInfo pi = PackageParserEx.generateProviderInfo(provider, this.mFlags, ps.readUserState(userId), userId, ps.isRunInExtProcess());
        if (pi == null) {
            return null;
        }
        ResolveInfo res = new ResolveInfo();
        res.providerInfo = pi;
        if ((this.mFlags & 0x40) != 0) {
            res.filter = filter.filter;
        }
        res.priority = filter.filter.getPriority();
        res.preferredOrder = provider.owner.mPreferredOrder;
        res.match = match;
        res.isDefault = filter.hasDefault;
        res.labelRes = filter.labelRes;
        res.nonLocalizedLabel = filter.nonLocalizedLabel;
        res.icon = filter.icon;
        return res;
    }

    @Override
    protected void sortResults(List<ResolveInfo> results) {
        Collections.sort(results, VPackageManagerService.sResolvePrioritySorter);
    }

    @Override
    protected void dumpFilter(PrintWriter out, String prefix, VPackage.ProviderIntentInfo filter) {
    }

    @Override
    protected Object filterToLabel(VPackage.ProviderIntentInfo filter) {
        return filter.provider;
    }

    @Override
    protected void dumpFilterLabel(PrintWriter out, String prefix, Object label, int count) {
    }
}

