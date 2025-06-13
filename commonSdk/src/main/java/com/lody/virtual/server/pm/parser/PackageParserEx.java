/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ComponentInfo
 *  android.content.pm.ConfigurationInfo
 *  android.content.pm.FeatureInfo
 *  android.content.pm.InstrumentationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.PermissionGroupInfo
 *  android.content.pm.PermissionInfo
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ServiceInfo
 *  android.content.pm.Signature
 *  android.os.Binder
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.SparseArray
 */
package com.lody.virtual.server.pm.parser;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.PackageParserCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.RefObjUtil;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.server.pm.ComponentStateManager;
import com.lody.virtual.server.pm.PackageCacheManager;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.PackageUserState;
import com.lody.virtual.server.pm.VAppManagerService;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mirror.android.content.pm.ApplicationInfoL;
import mirror.android.content.pm.ApplicationInfoN;
import mirror.android.content.pm.ApplicationInfoO;
import mirror.android.content.pm.PackageInfoPie;
import mirror.android.content.pm.PackageParser;
import mirror.android.content.pm.SigningInfo;
import mirror.android.content.pm.SigningInfoT;

public class PackageParserEx {
    public static final int GET_SIGNING_CERTIFICATES = 0x8000000;
    public static final String ORG_APACHE_HTTP_LEGACY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0MPXojJAJ9Dig0KAMYMmUwBgJ1NwIgLj4+JWcFSFo="));
    private static final String TAG = PackageParserEx.class.getSimpleName();
    static SparseArray sparseArray = new SparseArray();

    public static VPackage parsePackage(File packageFile) throws Throwable {
        PackageParser parser = PackageParserCompat.createParser(packageFile);
        if (BuildCompat.isQ()) {
            parser.setCallback(new PackageParser.CallbackImpl(VirtualCore.getPM()));
        }
        PackageParser.Package p = PackageParserCompat.parsePackage(parser, packageFile, 0);
        if (p.requestedPermissions.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCwiHWsmLF99HCQCIgZbG2MIGg99HyAfIwYMWX02Flo="))) && p.mAppMetaData != null && p.mAppMetaData.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+MWhSEgNjDjg2Lwg2LWoVGlo=")))) {
            String sig = p.mAppMetaData.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+MWhSEgNjDjg2Lwg2LWoVGlo=")));
            PackageParserEx.buildSignature(p, new Signature[]{new Signature(sig)});
            VLog.d(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQc2CWojPyhiNCAxKANXL2wjEiZoDiwwKS4tJGIwLCRqEQo7LypXKWU3Iy57AVRF")) + p.packageName, new Object[0]);
        } else {
            try {
                int flag = 0;
                flag = BuildCompat.isPie() ? (flag |= 0x10) : (flag |= 1);
                PackageParserCompat.collectCertificates(parser, p, flag);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return PackageParserEx.buildPackageCache(p);
    }

    private static void buildSignature(PackageParser.Package p, Signature[] signatures) {
        if (BuildCompat.isPie()) {
            Object signingDetails = PackageParser.Package.mSigningDetails.get(p);
            PackageParser.SigningDetails.pastSigningCertificates.set(signingDetails, signatures);
            PackageParser.SigningDetails.signatures.set(signingDetails, signatures);
            return;
        }
        p.mSignatures = signatures;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static VPackage readPackageCache(String packageName) {
        Parcel p = Parcel.obtain();
        try {
            File cacheFile = VEnvironment.getPackageCacheFile(packageName);
            FileInputStream is = new FileInputStream(cacheFile);
            byte[] bytes = FileUtils.toByteArray(is);
            is.close();
            p.unmarshall(bytes, 0, bytes.length);
            p.setDataPosition(0);
            if (p.readInt() == 4) {
                VPackage pkg = new VPackage(p);
                PackageParserEx.addOwner(pkg);
                VPackage vPackage = pkg;
                return vPackage;
            }
            try {
                throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLmsVHi9iVyQuKAguL2wjNCZ1N1RF")));
            }
            catch (Exception e) {
                e.printStackTrace();
                p.recycle();
                VPackage vPackage = null;
                return vPackage;
            }
        }
        finally {
            p.recycle();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void readSignature(VPackage pkg) {
        File signatureFile = VEnvironment.getSignatureFile(pkg.packageName);
        if (!signatureFile.exists()) {
            return;
        }
        Parcel p = Parcel.obtain();
        try {
            try {
                FileInputStream fis = new FileInputStream(signatureFile);
                byte[] bytes = FileUtils.toByteArray(fis);
                fis.close();
                p.unmarshall(bytes, 0, bytes.length);
                p.setDataPosition(0);
                if (BuildCompat.isPie()) {
                    try {
                        PackageParser.SigningDetails sigDetail;
                        pkg.mSigningDetails = sigDetail = (PackageParser.SigningDetails)PackageParser.SigningDetails.CREATOR.get().createFromParcel(p);
                        if (sigDetail.pastSigningCertificates != null && sigDetail.pastSigningCertificates.length > 0) {
                            pkg.mSignatures = new Signature[1];
                            pkg.mSignatures[0] = sigDetail.pastSigningCertificates[0];
                        } else {
                            pkg.mSignatures = sigDetail.signatures;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (pkg.mSigningDetails == null || pkg.mSignatures == null) {
                    p.setDataPosition(0);
                    pkg.mSignatures = (Signature[])p.createTypedArray(Signature.CREATOR);
                    pkg.mSigningDetails = null;
                }
            }
            finally {
                p.recycle();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void savePackageCache(VPackage pkg) {
        Object sig;
        File signatureFile;
        String packageName = pkg.packageName;
        File cacheFile = VEnvironment.getPackageCacheFile(packageName);
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
        if ((signatureFile = VEnvironment.getSignatureFile(packageName)).exists()) {
            signatureFile.delete();
        }
        Parcel p = Parcel.obtain();
        try {
            p.writeInt(4);
            pkg.writeToParcel(p, 0);
            FileOutputStream fos = new FileOutputStream(cacheFile);
            fos.write(p.marshall());
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        p.recycle();
        Signature[] signatures = pkg.mSignatures;
        PackageParser.SigningDetails obj = pkg.mSigningDetails;
        Object object = sig = obj == null ? signatures : obj;
        if (sig != null) {
            if (signatureFile.exists() && !signatureFile.delete()) {
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcP2sjHitLEQo1Pxc2PW8zGgZrDTw/IwgtJGEgGiJsNCQ9Iy1fJ2wnIANoIzxF")) + packageName, new Object[0]);
            }
            p = Parcel.obtain();
            try {
                if (sig instanceof PackageParser.SigningDetails) {
                    PackageParser.SigningDetails.writeToParcel.call(obj, p, 0);
                } else {
                    p.writeTypedArray((Parcelable[])signatures, 0);
                }
                FileUtils.writeParcelToFile(p, signatureFile);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static VPackage buildPackageCache(PackageParser.Package p) {
        List<String> protectedBroadcasts;
        VPackage cache = new VPackage();
        cache.activities = new ArrayList(p.activities.size());
        cache.services = new ArrayList(p.services.size());
        cache.receivers = new ArrayList(p.receivers.size());
        cache.providers = new ArrayList(p.providers.size());
        cache.instrumentation = new ArrayList(p.instrumentation.size());
        cache.permissions = new ArrayList(p.permissions.size());
        cache.permissionGroups = new ArrayList(p.permissionGroups.size());
        for (PackageParser.Activity activity : p.activities) {
            cache.activities.add(new VPackage.ActivityComponent(activity));
        }
        for (PackageParser.Service service : p.services) {
            cache.services.add(new VPackage.ServiceComponent(service));
        }
        for (PackageParser.Activity receiver : p.receivers) {
            cache.receivers.add(new VPackage.ActivityComponent(receiver));
        }
        for (PackageParser.Provider provider : p.providers) {
            cache.providers.add(new VPackage.ProviderComponent(provider));
        }
        for (PackageParser.Instrumentation instrumentation : p.instrumentation) {
            cache.instrumentation.add(new VPackage.InstrumentationComponent(instrumentation));
        }
        for (PackageParser.Permission permission2 : p.permissions) {
            cache.permissions.add(new VPackage.PermissionComponent(permission2));
        }
        for (PackageParser.PermissionGroup permissionGroup : p.permissionGroups) {
            cache.permissionGroups.add(new VPackage.PermissionGroupComponent(permissionGroup));
        }
        cache.requestedPermissions = new ArrayList(p.requestedPermissions.size());
        cache.requestedPermissions.addAll(p.requestedPermissions);
        if (PackageParser.Package.protectedBroadcasts != null && (protectedBroadcasts = PackageParser.Package.protectedBroadcasts.get(p)) != null) {
            cache.protectedBroadcasts = new ArrayList<String>(protectedBroadcasts);
            cache.protectedBroadcasts.addAll(protectedBroadcasts);
        }
        cache.applicationInfo = p.applicationInfo;
        if (BuildCompat.isPie()) {
            cache.mSigningDetails = p.mSigningDetails;
            if (p.mSigningDetails.pastSigningCertificates != null && p.mSigningDetails.pastSigningCertificates.length > 0) {
                cache.mSignatures = new Signature[1];
                cache.mSignatures[0] = p.mSigningDetails.pastSigningCertificates[0];
            } else {
                cache.mSignatures = p.mSigningDetails.signatures;
            }
        } else {
            cache.mSignatures = p.mSignatures;
        }
        cache.mAppMetaData = p.mAppMetaData;
        cache.packageName = p.packageName;
        cache.mPreferredOrder = p.mPreferredOrder;
        cache.mVersionName = p.mVersionName;
        cache.mSharedUserId = p.mSharedUserId;
        cache.mSharedUserLabel = p.mSharedUserLabel;
        cache.usesLibraries = p.usesLibraries;
        cache.usesOptionalLibraries = p.usesOptionalLibraries;
        cache.mVersionCode = p.mVersionCode;
        cache.configPreferences = p.configPreferences;
        cache.reqFeatures = p.reqFeatures;
        PackageParserEx.fixSignatureAsSystem(cache);
        PackageParserEx.addOwner(cache);
        PackageParserEx.injectXposedModuleInfo(cache);
        PackageParserEx.updatePackageApache(cache);
        return cache;
    }

    private static void injectXposedModuleInfo(VPackage vPackage) {
        if (vPackage.mAppMetaData == null || !vPackage.mAppMetaData.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KBc6D28zNCxgDh4wLAdbPQ==")))) {
            return;
        }
        VPackage.XposedModule module = new VPackage.XposedModule();
        Object descriptionRaw = vPackage.mAppMetaData.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KBc6D28zNCxiHjApLy4uMWowBi9lJxpF")));
        String descriptionTmp = null;
        if (descriptionRaw instanceof String) {
            descriptionTmp = ((String)descriptionRaw).trim();
        } else if (descriptionRaw instanceof Integer) {
            try {
                int resId = (Integer)descriptionRaw;
                if (resId != 0) {
                    descriptionTmp = VirtualCore.getPM().getResourcesForApplication(vPackage.applicationInfo).getString(resId).trim();
                }
            }
            catch (Exception resId) {
                // empty catch block
            }
        }
        module.desc = descriptionTmp != null ? descriptionTmp : "";
        Object minVersionRaw = vPackage.mAppMetaData.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KBc6D28zNCxgDgY2LD0MKGoFLCVlN1RF")));
        module.minVersion = minVersionRaw instanceof Integer ? (Integer)minVersionRaw : (minVersionRaw instanceof String ? PackageParserEx.extractIntPart((String)minVersionRaw) : 0);
        vPackage.xposedModule = module;
    }

    private static int extractIntPart(String str) {
        char c;
        int result = 0;
        int length = str.length();
        for (int offset = 0; offset < length && '0' <= (c = str.charAt(offset)) && c <= '9'; ++offset) {
            result = result * 10 + (c - 48);
        }
        return result;
    }

    public static void initApplicationInfoBase(PackageSetting ps, VPackage p) {
        ApplicationInfo ai = p.applicationInfo;
        if (TextUtils.isEmpty((CharSequence)ai.processName)) {
            ai.processName = ai.packageName;
        }
        ai.enabled = true;
        ai.uid = ps.appId;
        ai.name = ComponentFixer.fixComponentClassName(ps.packageName, ai.name);
    }

    private static void initApplicationAsUser(ApplicationInfo ai, int userId, boolean isExt) {
        PackageSetting ps = PackageCacheManager.getSetting(ai.packageName);
        VPackage pkg = PackageCacheManager.get(ai.packageName);
        if (ps == null) {
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1Pxc6PWU2AitvHiwaLC4lJGIwAjV7N1RF")) + ai.packageName);
        }
        ApplicationInfo outsideAppInfo = null;
        try {
            outsideAppInfo = VirtualCore.get().getPackageManager().getApplicationInfo(ai.packageName, 0);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        SettingConfig config = VirtualCore.getConfig();
        if (isExt && !ps.dynamic) {
            ai.publicSourceDir = ai.sourceDir = VEnvironment.getPackageFileExt(ai.packageName).getPath();
            File nativeLibraryRootDir = VEnvironment.getDataAppLibDirectoryExt(ai.packageName);
            File nativeLibraryDir = new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(ps.primaryCpuAbi));
            ai.nativeLibraryDir = nativeLibraryDir.getPath();
            if (ps.secondaryCpuAbi != null) {
                File secondaryNativeLibraryDir = new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(ps.secondaryCpuAbi));
                ApplicationInfoL.secondaryNativeLibraryDir.set(ai, secondaryNativeLibraryDir.getPath());
            }
            String scanSourcePath = VEnvironment.getDataAppPackageDirectoryExt(ai.packageName).getAbsolutePath();
            ApplicationInfoL.scanSourceDir.set(ai, scanSourcePath);
            ApplicationInfoL.scanPublicSourceDir.set(ai, scanSourcePath);
        }
        if (!ps.dynamic && pkg.splitNames != null && !pkg.splitNames.isEmpty()) {
            if (Build.VERSION.SDK_INT >= 26) {
                ai.splitNames = pkg.splitNames.toArray(new String[0]);
            }
            ArrayList<String> splitSourceDirs = new ArrayList<String>();
            for (String splitName : pkg.splitNames) {
                File file = isExt ? VEnvironment.getSplitPackageFileExt(ai.packageName, splitName) : VEnvironment.getSplitPackageFile(ai.packageName, splitName);
                splitSourceDirs.add(file.getPath());
            }
            pkg.applicationInfo.splitSourceDirs = splitSourceDirs.toArray(new String[0]);
            pkg.applicationInfo.splitPublicSourceDirs = splitSourceDirs.toArray(new String[0]);
        }
        if (Build.VERSION.SDK_INT >= 26 && outsideAppInfo != null && ps.dynamic) {
            ai.splitNames = outsideAppInfo.splitNames;
            SparseArray splitDependencies = (SparseArray)RefObjUtil.getRefObjectValue(ApplicationInfoO.splitDependencies, outsideAppInfo);
            SparseArray splitDependencies2 = (SparseArray)RefObjUtil.getRefObjectValue(ApplicationInfoO.splitDependencies, ai);
            if (splitDependencies != null && splitDependencies2 == null) {
                RefObjUtil.setRefObjectValue(ApplicationInfoO.splitDependencies, ai, splitDependencies);
            }
        }
        String path = isExt ? VEnvironment.getDataUserPackageDirectoryExt(userId, ai.packageName).getPath() : VEnvironment.getDataUserPackageDirectory(userId, ai.packageName).getPath();
        ai.dataDir = path;
        if (Build.VERSION.SDK_INT >= 24) {
            String deDataDir = isExt ? VEnvironment.getDeDataUserPackageDirectoryExt(userId, ai.packageName).getPath() : VEnvironment.getDeDataUserPackageDirectory(userId, ai.packageName).getPath();
            if (ApplicationInfoN.deviceEncryptedDataDir != null) {
                ApplicationInfoN.deviceEncryptedDataDir.set(ai, deDataDir);
            }
            if (ApplicationInfoN.credentialEncryptedDataDir != null) {
                ApplicationInfoN.credentialEncryptedDataDir.set(ai, ai.dataDir);
            }
            if (ApplicationInfoN.deviceProtectedDataDir != null) {
                ApplicationInfoN.deviceProtectedDataDir.set(ai, deDataDir);
            }
            if (ApplicationInfoN.credentialProtectedDataDir != null) {
                ApplicationInfoN.credentialProtectedDataDir.set(ai, ai.dataDir);
            }
        }
        if (config.isEnableIORedirect()) {
            if (config.isUseRealDataDir(ai.packageName)) {
                ai.dataDir = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4qP2wFJyViHiAgLwNfVg==")) + ai.packageName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="));
            }
            if (config.isUseRealLibDir(ai.packageName)) {
                String secondaryNativeLibraryDir;
                if (isExt) {
                    ai.nativeLibraryDir = outsideAppInfo.nativeLibraryDir;
                    String primaryCpuAbi = ApplicationInfoL.primaryCpuAbi.get(outsideAppInfo);
                    if (!TextUtils.isEmpty((CharSequence)primaryCpuAbi)) {
                        ApplicationInfoL.primaryCpuAbi.set(ai, primaryCpuAbi);
                    }
                } else {
                    String nativeLibraryDir = outsideAppInfo.nativeLibraryDir;
                    if (nativeLibraryDir != null) {
                        ai.nativeLibraryDir = nativeLibraryDir;
                    }
                }
                String secondaryCpuAbi = (String)RefObjUtil.getRefObjectValue(ApplicationInfoL.secondaryCpuAbi, outsideAppInfo);
                if (!TextUtils.isEmpty((CharSequence)secondaryCpuAbi)) {
                    RefObjUtil.setRefObjectValue(ApplicationInfoL.secondaryCpuAbi, ai, secondaryCpuAbi);
                }
                if (!TextUtils.isEmpty((CharSequence)(secondaryNativeLibraryDir = (String)RefObjUtil.getRefObjectValue(ApplicationInfoL.secondaryNativeLibraryDir, outsideAppInfo)))) {
                    RefObjUtil.setRefObjectValue(ApplicationInfoL.secondaryNativeLibraryDir, ai, secondaryNativeLibraryDir);
                }
            }
        }
    }

    private static void addOwner(VPackage p) {
        for (VPackage.ActivityComponent activity : p.activities) {
            activity.owner = p;
            for (VPackage.ActivityIntentInfo info : activity.intents) {
                info.activity = activity;
            }
        }
        for (VPackage.ServiceComponent service : p.services) {
            service.owner = p;
            for (VPackage.ServiceIntentInfo info2 : service.intents) {
                info2.service = service;
            }
        }
        for (VPackage.ActivityComponent receiver : p.receivers) {
            receiver.owner = p;
            for (VPackage.ActivityIntentInfo info3 : receiver.intents) {
                info3.activity = receiver;
            }
        }
        for (VPackage.ProviderComponent provider : p.providers) {
            provider.owner = p;
            for (VPackage.ProviderIntentInfo info4 : provider.intents) {
                info4.provider = provider;
            }
        }
        for (VPackage.InstrumentationComponent instrumentation : p.instrumentation) {
            instrumentation.owner = p;
        }
        for (VPackage.PermissionComponent permission2 : p.permissions) {
            permission2.owner = p;
        }
        for (VPackage.PermissionGroupComponent group : p.permissionGroups) {
            group.owner = p;
        }
        int flags = 4;
        if (GmsSupport.isGoogleService(p.packageName)) {
            flags = 12;
        }
        p.applicationInfo.flags |= flags;
    }

    public static PackageInfo generatePackageInfo(VPackage p, PackageSetting ps, int flags, long firstInstallTime, long lastUpdateTime, PackageUserState state, int userId, boolean isExt) {
        int i8;
        int N14;
        int N;
        int N13;
        int N2;
        int N12;
        int N3;
        int N11;
        int N4;
        int N10;
        int N5;
        int N8;
        if (!PackageParserEx.checkUseInstalledOrHidden(state, flags)) {
            return null;
        }
        if (p.mSignatures == null && p.mSigningDetails == null) {
            PackageParserEx.readSignature(p);
        }
        PackageInfo pi = new PackageInfo();
        pi.packageName = p.packageName;
        pi.versionCode = p.mVersionCode;
        pi.sharedUserLabel = p.mSharedUserLabel;
        pi.versionName = p.mVersionName;
        pi.sharedUserId = p.mSharedUserId;
        pi.applicationInfo = PackageParserEx.generateApplicationInfo(p, flags, state, userId, isExt);
        pi.firstInstallTime = firstInstallTime;
        pi.lastUpdateTime = lastUpdateTime;
        if (p.requestedPermissions != null && !p.requestedPermissions.isEmpty()) {
            String[] requestedPermissions = new String[p.requestedPermissions.size()];
            p.requestedPermissions.toArray(requestedPermissions);
            pi.requestedPermissions = requestedPermissions;
        }
        if ((flags & 0x100) != 0) {
            pi.gids = PackageParserCompat.GIDS;
        }
        if ((flags & 0x4000) != 0) {
            int N7;
            int N6;
            int n = N6 = p.configPreferences != null ? p.configPreferences.size() : 0;
            if (N6 > 0) {
                pi.configPreferences = new ConfigurationInfo[N6];
                p.configPreferences.toArray(pi.configPreferences);
            }
            int n2 = N7 = p.reqFeatures != null ? p.reqFeatures.size() : 0;
            if (N7 > 0) {
                pi.reqFeatures = new FeatureInfo[N7];
                p.reqFeatures.toArray(pi.reqFeatures);
            }
        }
        if ((N8 = flags & 1) != 0 && (N5 = p.activities.size()) > 0) {
            int num = 0;
            ActivityInfo[] res = new ActivityInfo[N5];
            for (int i = 0; i < N5; ++i) {
                VPackage.ActivityComponent a = p.activities.get(i);
                int N9 = N5;
                if (ps.isEnabledAndMatchLPr((ComponentInfo)a.info, flags, userId)) {
                    ActivityInfo activityInfo = PackageParserEx.generateActivityInfo(a, flags, state, userId, isExt);
                    activityInfo.applicationInfo = pi.applicationInfo;
                    res[num] = activityInfo;
                    ++num;
                }
                N5 = N9;
            }
            pi.activities = ArrayUtils.trimToSize(res, num);
        }
        if ((N10 = flags & 2) != 0 && (N4 = p.receivers.size()) > 0) {
            int num2 = 0;
            ActivityInfo[] res2 = new ActivityInfo[N4];
            for (int i2 = 0; i2 < N4; ++i2) {
                VPackage.ActivityComponent a2 = p.receivers.get(i2);
                if (!ps.isEnabledAndMatchLPr((ComponentInfo)a2.info, flags, userId)) continue;
                ActivityInfo activityInfo2 = PackageParserEx.generateActivityInfo(a2, flags, state, userId, isExt);
                activityInfo2.applicationInfo = pi.applicationInfo;
                res2[num2] = activityInfo2;
                ++num2;
            }
            pi.receivers = ArrayUtils.trimToSize(res2, num2);
        }
        if ((N11 = flags & 4) != 0 && (N3 = p.services.size()) > 0) {
            int num3 = 0;
            ServiceInfo[] res3 = new ServiceInfo[N3];
            for (int i3 = 0; i3 < N3; ++i3) {
                VPackage.ServiceComponent s = p.services.get(i3);
                if (!ps.isEnabledAndMatchLPr((ComponentInfo)s.info, flags, userId)) continue;
                ServiceInfo serviceInfo = PackageParserEx.generateServiceInfo(s, flags, state, userId, isExt);
                serviceInfo.applicationInfo = pi.applicationInfo;
                res3[num3] = serviceInfo;
                ++num3;
            }
            pi.services = ArrayUtils.trimToSize(res3, num3);
        }
        if ((N12 = flags & 8) != 0 && (N2 = p.providers.size()) > 0) {
            int num4 = 0;
            ProviderInfo[] res4 = new ProviderInfo[N2];
            for (int i4 = 0; i4 < N2; ++i4) {
                VPackage.ProviderComponent pr = p.providers.get(i4);
                if (!ps.isEnabledAndMatchLPr((ComponentInfo)pr.info, flags, userId)) continue;
                ProviderInfo providerInfo = PackageParserEx.generateProviderInfo(pr, flags, state, userId, isExt);
                providerInfo.applicationInfo = pi.applicationInfo;
                res4[num4] = providerInfo;
                ++num4;
            }
            pi.providers = ArrayUtils.trimToSize(res4, num4);
        }
        if ((N13 = flags & 0x10) != 0 && (N = p.instrumentation.size()) > 0) {
            pi.instrumentation = new InstrumentationInfo[N];
            for (int i5 = 0; i5 < N; ++i5) {
                pi.instrumentation[i5] = PackageParserEx.generateInstrumentationInfo(p.instrumentation.get(i5), flags);
            }
        }
        if ((N14 = flags & 0x1000) != 0) {
            int N16;
            int N15 = p.permissions.size();
            if (N15 > 0) {
                pi.permissions = new PermissionInfo[N15];
                for (int i6 = 0; i6 < N15; ++i6) {
                    pi.permissions[i6] = PackageParserEx.generatePermissionInfo(p.permissions.get(i6), flags);
                }
            }
            int n = N16 = p.requestedPermissions == null ? 0 : p.requestedPermissions.size();
            if (N16 > 0) {
                pi.requestedPermissions = new String[N16];
                pi.requestedPermissionsFlags = new int[N16];
                List<String> hostRequestedPermissions = null;
                int[] hostRequestedPermissionsFlags = null;
                try {
                    VAppManagerService.get().isRunInExtProcess(p.packageName);
                    String hostPkg = StubManifest.EXT_PACKAGE_NAME;
                    PackageInfo hostInfo = VirtualCore.get().getContext().getPackageManager().getPackageInfo(hostPkg, 4096);
                    hostRequestedPermissions = Arrays.asList(hostInfo.requestedPermissions);
                    hostRequestedPermissionsFlags = hostInfo.requestedPermissionsFlags;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i7 = 0; i7 < N16; ++i7) {
                    String perm;
                    pi.requestedPermissions[i7] = perm = p.requestedPermissions.get(i7);
                    if (hostRequestedPermissions == null) continue;
                    int permissionIndex = hostRequestedPermissions.indexOf(perm);
                    pi.requestedPermissionsFlags[i7] = permissionIndex >= 0 ? hostRequestedPermissionsFlags[permissionIndex] : -1;
                }
            }
        }
        if ((i8 = flags & 0x40) != 0) {
            int N17;
            int n = N17 = p.mSignatures != null ? p.mSignatures.length : 0;
            if (N17 <= 0) {
                try {
                    PackageInfo outInfo = VirtualCore.get().getHostPackageManager().getPackageInfo(p.packageName, 64L);
                    pi.signatures = outInfo.signatures;
                }
                catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
            } else {
                pi.signatures = new Signature[N17];
                System.arraycopy(p.mSignatures, 0, pi.signatures, 0, N17);
            }
        }
        if (BuildCompat.isPie() && (0x8000000 & flags) != 0) {
            if (p.mSigningDetails != null) {
                if (BuildCompat.isTiramisu()) {
                    Object signingInfo = SigningInfoT.createSigningInfo(p.mSigningDetails);
                    PackageInfoPie.signingInfo.set(pi, signingInfo);
                } else {
                    PackageInfoPie.signingInfo.set(pi, SigningInfo.ctor.newInstance(p.mSigningDetails));
                }
                pi.signatures = p.mSigningDetails.signatures;
            } else if (p.mSignatures != null) {
                PackageParser.SigningDetails details = new PackageParser.SigningDetails();
                PackageParser.SigningDetails.pastSigningCertificates.set(details, p.mSignatures);
                PackageParser.SigningDetails.signatures.set(details, p.mSignatures);
                PackageInfoPie.signingInfo.set(pi, SigningInfo.ctor.newInstance(details));
                pi.signatures = p.mSigningDetails.signatures;
            }
        }
        VPackage vPackage = new VPackage();
        vPackage.packageName = pi.packageName;
        PackageParserEx.fixSignatureAsSystem(vPackage);
        if (vPackage.mSignatures != null) {
            pi.signatures = vPackage.mSignatures;
        }
        return pi;
    }

    public static void fixSignatureAsSystem(VPackage vPackage) {
        try {
            PackageInfo outsideInfo = VirtualCore.get().getPackageManager().getPackageInfo(vPackage.packageName, 0x8000040);
            vPackage.mSignatures = outsideInfo.signatures;
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static ApplicationInfo generateApplicationInfo(VPackage p, int flags, PackageUserState state, int userId, boolean isExt) {
        if (p == null || !PackageParserEx.checkUseInstalledOrHidden(state, flags)) {
            return null;
        }
        ApplicationInfo ai = new ApplicationInfo(p.applicationInfo);
        if ((flags & 0x80) != 0) {
            ai.metaData = p.mAppMetaData;
        }
        PackageParserEx.initApplicationAsUser(ai, userId, isExt);
        return ai;
    }

    public static ActivityInfo generateActivityInfo(VPackage.ActivityComponent a, int flags, PackageUserState state, int userId, boolean isExt) {
        if (a == null || !PackageParserEx.checkUseInstalledOrHidden(state, flags)) {
            return null;
        }
        ActivityInfo ai = new ActivityInfo(a.info);
        if ((flags & 0x80) != 0 && a.metaData != null) {
            ai.metaData = a.metaData;
        }
        ai.enabled = PackageParserEx.isEnabledLPr((ComponentInfo)a.info, 0, userId);
        ai.applicationInfo = PackageParserEx.generateApplicationInfo(a.owner, flags, state, userId, isExt);
        return ai;
    }

    public static boolean isEnabledLPr(ComponentInfo componentInfo, int flags, int userId) {
        ComponentName component = ComponentUtils.toComponentName(componentInfo);
        int state = ComponentStateManager.user(userId).get(component);
        if (state == 0) {
            return componentInfo.enabled;
        }
        return state != 2 && state != 4 && state != 3;
    }

    public static ServiceInfo generateServiceInfo(VPackage.ServiceComponent s, int flags, PackageUserState state, int userId, boolean isExt) {
        if (s == null || !PackageParserEx.checkUseInstalledOrHidden(state, flags)) {
            return null;
        }
        ServiceInfo si = new ServiceInfo(s.info);
        if ((flags & 0x80) != 0 && s.metaData != null) {
            si.metaData = s.metaData;
        }
        si.enabled = PackageParserEx.isEnabledLPr((ComponentInfo)s.info, 0, userId);
        si.applicationInfo = PackageParserEx.generateApplicationInfo(s.owner, flags, state, userId, isExt);
        return si;
    }

    public static ProviderInfo generateProviderInfo(VPackage.ProviderComponent p, int flags, PackageUserState state, int userId, boolean isExt) {
        if (p == null || !PackageParserEx.checkUseInstalledOrHidden(state, flags)) {
            return null;
        }
        ProviderInfo pi = new ProviderInfo(p.info);
        if ((flags & 0x80) != 0 && p.metaData != null) {
            pi.metaData = p.metaData;
        }
        if ((flags & 0x800) == 0) {
            pi.uriPermissionPatterns = null;
        }
        pi.enabled = PackageParserEx.isEnabledLPr((ComponentInfo)p.info, 0, userId);
        pi.applicationInfo = PackageParserEx.generateApplicationInfo(p.owner, flags, state, userId, isExt);
        return pi;
    }

    public static InstrumentationInfo generateInstrumentationInfo(VPackage.InstrumentationComponent i, int flags) {
        if (i == null) {
            return null;
        }
        if ((flags & 0x80) == 0) {
            return i.info;
        }
        InstrumentationInfo ii = new InstrumentationInfo(i.info);
        ii.metaData = i.metaData;
        return ii;
    }

    public static PermissionInfo generatePermissionInfo(VPackage.PermissionComponent p, int flags) {
        if (p == null) {
            return null;
        }
        if ((flags & 0x80) == 0) {
            return p.info;
        }
        PermissionInfo pi = new PermissionInfo(p.info);
        pi.metaData = p.metaData;
        return pi;
    }

    public static PermissionGroupInfo generatePermissionGroupInfo(VPackage.PermissionGroupComponent pg, int flags) {
        if (pg == null) {
            return null;
        }
        if ((flags & 0x80) == 0) {
            return pg.info;
        }
        PermissionGroupInfo pgi = new PermissionGroupInfo(pg.info);
        pgi.metaData = pg.metaData;
        return pgi;
    }

    private static boolean checkUseInstalledOrHidden(PackageUserState state, int flags) {
        return state.installed && !state.hidden || (flags & 0x2000) != 0;
    }

    private static void updatePackageApache(VPackage pkg) {
        ArrayList<String> usesOptionalLibraries2;
        ArrayList<String> usesLibraries2;
        boolean alreadyPresent2;
        ArrayList<String> usesOptionalLibraries;
        ArrayList<String> usesLibraries;
        boolean alreadyPresent;
        if (pkg.usesLibraries == null) {
            pkg.usesLibraries = new ArrayList();
        }
        if (pkg.usesOptionalLibraries == null) {
            pkg.usesOptionalLibraries = new ArrayList();
        }
        if (pkg.applicationInfo != null && pkg.applicationInfo.targetSdkVersion < 28 && !(alreadyPresent = PackageParserEx.isLibraryPresent(usesLibraries = pkg.usesLibraries, usesOptionalLibraries = pkg.usesOptionalLibraries, ORG_APACHE_HTTP_LEGACY))) {
            pkg.usesLibraries.add(0, ORG_APACHE_HTTP_LEGACY);
        }
        if (pkg.applicationInfo != null && !(alreadyPresent2 = PackageParserEx.isLibraryPresent(usesLibraries2 = pkg.usesLibraries, usesOptionalLibraries2 = pkg.usesOptionalLibraries, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKAgqLn8VRTdsJyhF"))))) {
            pkg.usesLibraries.add(0, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKAgqLn8VRTdsJyhF")));
        }
    }

    private static boolean isLibraryPresent(List<String> usesLibraries, List<String> usesOptionalLibraries, String apacheHttpLegacy) {
        if (usesLibraries != null) {
            for (String libName : usesLibraries) {
                if (!libName.equals(apacheHttpLegacy)) continue;
                return true;
            }
        }
        if (usesOptionalLibraries != null) {
            for (String libName2 : usesOptionalLibraries) {
                if (!libName2.equals(apacheHttpLegacy)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static void changeApplicationInfoPathToReal(ApplicationInfo applicationInfo) {
        String vaPath = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")) + VirtualCore.get().getContext().getPackageName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My0iCW8gMAV9DlFF"));
        for (Field field : applicationInfo.getClass().getDeclaredFields()) {
            try {
                String content;
                field.setAccessible(true);
                Object value = field.get(applicationInfo);
                if (value == null || !(value instanceof String) || !(content = (String)value).contains(vaPath)) continue;
                String newContent = content.substring(content.indexOf(vaPath) + vaPath.length());
                field.set(applicationInfo, newContent);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isVAppCalling(Context context) {
        String mainProcessName = context.getApplicationInfo().processName;
        int pid = Binder.getCallingPid();
        String callingProcessName = null;
        ActivityManager am = (ActivityManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
        for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
            if (info.pid != pid) continue;
            callingProcessName = info.processName;
            break;
        }
        return !callingProcessName.equals(mainProcessName) && !callingProcessName.endsWith(Constants.SERVER_PROCESS_NAME) && !callingProcessName.endsWith(Constants.HELPER_PROCESS_NAME) && VActivityManager.get().isAppProcess(callingProcessName);
    }
}

