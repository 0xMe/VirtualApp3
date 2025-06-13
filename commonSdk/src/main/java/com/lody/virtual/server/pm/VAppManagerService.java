/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.VersionedPackage
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IInterface
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.util.ArrayMap
 */
package com.lody.virtual.server.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.net.Uri;
import android.os.Build;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.HostPackageManager;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.DexOptimizer;
import com.lody.virtual.helper.PackageCleaner;
import com.lody.virtual.helper.collection.IntArray;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.NativeLibraryHelperCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import com.lody.virtual.sandxposed.XposedModuleProfile;
import com.lody.virtual.server.accounts.VAccountManagerService;
import com.lody.virtual.server.am.UidSystem;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.interfaces.IAppManager;
import com.lody.virtual.server.interfaces.IPackageObserver;
import com.lody.virtual.server.notification.VNotificationManagerService;
import com.lody.virtual.server.pm.ComponentStateManager;
import com.lody.virtual.server.pm.PackageCacheManager;
import com.lody.virtual.server.pm.PackagePersistenceLayer;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.SystemConfig;
import com.lody.virtual.server.pm.VUserManagerService;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mirror.android.content.pm.ApplicationInfoL;
import mirror.android.content.pm.ApplicationInfoP;

public class VAppManagerService
extends IAppManager.Stub {
    private final String ANDROID_TEST_BASE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKAgqLn8VRTdsJyhF"));
    private final String ANDROID_TEST_RUNNER = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKAgqLn8aRQVlNxogKS5SVg=="));
    private final String ORG_APACHE_HTTP_LEGACY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0MPXojJAJ9Dig0KAMYMmUwBgJ1NwIgLj4+JWcFSFo="));
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")) + VAppManagerService.class.getSimpleName();
    private static final Singleton<VAppManagerService> sService = new Singleton<VAppManagerService>(){

        @Override
        protected VAppManagerService create() {
            return new VAppManagerService();
        }
    };
    private final UidSystem mUidSystem = new UidSystem();
    private final SystemConfig mSystemConfig = new SystemConfig();
    private final PackagePersistenceLayer mPersistenceLayer = new PackagePersistenceLayer(this);
    private volatile boolean mScanning;
    private RemoteCallbackList<IPackageObserver> mRemoteCallbackList = new RemoteCallbackList();
    private BroadcastReceiver appEventReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            PackageSetting ps;
            if (VAppManagerService.this.mScanning) {
                return;
            }
            BroadcastReceiver.PendingResult result = this.goAsync();
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            Uri data = intent.getData();
            if (data == null) {
                return;
            }
            String pkg = data.getSchemeSpecificPart();
            if (pkg == null) {
                return;
            }
            if (pkg.equals(StubManifest.EXT_PACKAGE_NAME)) {
                VExtPackageAccessor.syncPackages();
            }
            if ((ps = PackageCacheManager.getSetting(pkg)) == null || !ps.dynamic) {
                return;
            }
            VActivityManagerService.get().killAppByPkg(pkg, -1);
            if (action.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2QxNA5iDzgMLAUMVg==")))) {
                ApplicationInfo outInfo = null;
                try {
                    outInfo = VirtualCore.getPM().getApplicationInfo(pkg, 0);
                }
                catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (outInfo == null) {
                    return;
                }
                VAppInstallerParams params = new VAppInstallerParams(2, 1);
                VAppInstallerResult res = VAppManagerService.this.installPackageInternal(Uri.parse((String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDQJF")) + pkg)), params);
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQc6PGsaMCtLESQ7Ly0EOWkFBSh7DjMrKT0qO2YVLDZ7MCMuLz5SVg==")), res.packageName, res.status);
            } else if (action.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2QxNEhiMiQKLBhSVg=="))) && intent.getBooleanExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmoYPFRhD1kRJywmA2cxNFU=")), false)) {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uDWowOC9gNDs8IxciP2wFQS1rDT8gKT5SVg==")), ps.packageName);
                VAppManagerService.this.uninstallPackageFully(ps, true);
            }
            result.finish();
        }
    };

    public static VAppManagerService get() {
        return sService.get();
    }

    public static void systemReady() {
        VEnvironment.systemReady();
        if (BuildCompat.isPie() && !BuildCompat.isQ()) {
            VAppManagerService.get().extractApacheFrameworksForPie();
        }
        VAppManagerService.get().startup();
    }

    private void startup() {
        this.mSystemConfig.load();
        this.mUidSystem.initUidList();
        IntentFilter filter = new IntentFilter();
        filter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2QxNA5iDzgMLAUMVg==")));
        filter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2QxNEhiMiQKLBhSVg==")));
        filter.addDataScheme(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")));
        VirtualCore.get().getContext().registerReceiver(this.appEventReceiver, filter);
    }

    private void extractApacheFrameworksForPie() {
        String frameworkName = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0MPXojJAJ9Dig0KAMYMmUwBgJ1NwIgLj4+JWcORSVsJFk9"));
        File dex = VEnvironment.getOptimizedFrameworkFile(frameworkName);
        if (!dex.exists()) {
            try {
                FileUtils.copyFileFromAssets(VirtualCore.get().getContext(), frameworkName, dex);
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
    public void scanApps() {
        if (this.mScanning) {
            return;
        }
        VAppManagerService vAppManagerService = this;
        synchronized (vAppManagerService) {
            this.mScanning = true;
            this.mPersistenceLayer.read();
            if (this.mPersistenceLayer.changed) {
                this.mPersistenceLayer.changed = false;
                this.mPersistenceLayer.save();
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ihg+OWUzJC1iCiQCKAguL2wgAgZrARoqLhYEO2cKLDV5EQo5Lz5bCmsFMwQ=")), new Object[0]);
            }
            List<VUserInfo> userHandles = VUserManagerService.get().getUsers(true);
            for (String preInstallPkg : SpecialComponentList.getPreInstallPackages()) {
                try {
                    VirtualCore.get().getHostPackageManager().getApplicationInfo(preInstallPkg, 0L);
                }
                catch (PackageManager.NameNotFoundException e) {
                    continue;
                }
                for (VUserInfo userInfo : userHandles) {
                    if (!this.isAppInstalled(preInstallPkg) && userInfo.id == 0) {
                        VAppInstallerParams params = new VAppInstallerParams(10, 1);
                        this.installPackageInternal(Uri.parse((String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDQJF")) + preInstallPkg)), params);
                        continue;
                    }
                    if (this.isAppInstalledAsUser(userInfo.id, preInstallPkg)) continue;
                    this.installPackageAsUser(userInfo.id, preInstallPkg);
                }
            }
            VAccountManagerService.get().refreshAuthenticatorCache(null);
            this.mScanning = false;
        }
    }

    private void cleanUpResidualFiles(PackageSetting ps) {
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2sVBgVhVyQqKAgqMWkwGjdlVjwtIxgEJ2EkOCFsJyspPl9WJ2wjSFo=")), ps.packageName);
        this.uninstallPackageFully(ps, false);
    }

    public void onUserCreated(VUserInfo userInfo) {
        FileUtils.ensureDirCreate(VEnvironment.getDataUserDirectory(userInfo.id));
    }

    synchronized boolean loadPackage(PackageSetting setting) {
        if (!this.loadPackageInnerLocked(setting)) {
            this.cleanUpResidualFiles(setting);
            return false;
        }
        return true;
    }

    private boolean loadPackageInnerLocked(PackageSetting ps) {
        boolean dynamic = ps.dynamic;
        if (dynamic && !VirtualCore.get().isOutsideInstalled(ps.packageName)) {
            return false;
        }
        File cacheFile = VEnvironment.getPackageCacheFile(ps.packageName);
        VPackage pkg = null;
        try {
            pkg = PackageParserEx.readPackageCache(ps.packageName);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        if (pkg == null || pkg.packageName == null) {
            return false;
        }
        VEnvironment.chmodPackageDictionary(cacheFile);
        PackageCacheManager.put(pkg, ps);
        if (dynamic) {
            try {
                boolean isPathChange;
                PackageInfo outInfo = VirtualCore.get().getHostPackageManager().getPackageInfo(ps.packageName, 0L);
                boolean isVersionCodeChange = pkg.mVersionCode != outInfo.versionCode;
                boolean bl = isPathChange = !new File(pkg.applicationInfo.publicSourceDir).exists();
                if (isVersionCodeChange || isPathChange) {
                    VLog.d(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgc6KHsJRVo=")) + ps.packageName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAQ6CmsaLyh9JBo7Kj06PWk3TT5rDgo6IxgAKk5TODBlHjAqIz4fJGoKMwQ=")), new Object[0]);
                    VAppInstallerParams params = new VAppInstallerParams(10, 1);
                    this.installPackageInternal(Uri.parse((String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDQJF")) + ps.packageName)), params);
                }
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public int getUidForSharedUser(String sharedUserName) {
        if (sharedUserName == null) {
            return -1;
        }
        return this.mUidSystem.getUid(sharedUserName);
    }

    @Override
    public VAppInstallerResult installPackage(Uri uri, VAppInstallerParams params) {
        VAppManagerService vAppManagerService = this;
        synchronized (vAppManagerService) {
            try {
                return this.installPackageInternal(uri, params);
            }
            catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private VAppInstallerResult installPackageInternal(Uri uri, VAppInstallerParams params) {
        String instructionSet;
        File oatFile;
        File secondaryNativeLibraryDir;
        File nativeLibraryDir;
        File nativeLibraryRootDir;
        boolean nativeLibraryRootRequiresIsa;
        String secondaryCpuAbi;
        String primaryCpuAbi;
        PackageParser.ApkLite apkLite;
        Object packageFile;
        long installTime = System.currentTimeMillis();
        int installFlags = params.getInstallFlags();
        int resultFlags = 0;
        if (uri == null || uri.getScheme() == null) {
            return VAppInstallerResult.create(4);
        }
        String scheme = uri.getScheme();
        if (!scheme.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF"))) && !scheme.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YDmgVSFo=")))) {
            return VAppInstallerResult.create(4);
        }
        if (scheme.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF"))) && uri.getSchemeSpecificPart() == null) {
            return VAppInstallerResult.create(4);
        }
        if (scheme.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YDmgVSFo="))) && uri.getPath() == null) {
            return VAppInstallerResult.create(4);
        }
        ApplicationInfo outApplicationInfo = null;
        if (uri.getScheme().equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")))) {
            String packageName = uri.getSchemeSpecificPart();
            try {
                outApplicationInfo = HostPackageManager.get().getApplicationInfo(packageName, 1024L);
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (outApplicationInfo == null) {
                return VAppInstallerResult.create(packageName, 7);
            }
            packageFile = new File(outApplicationInfo.publicSourceDir);
        } else {
            packageFile = new File(uri.getPath());
        }
        VLog.d(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDDwzKhcLIA==")) + packageFile, new Object[0]);
        if (!((File)packageFile).exists() || !((File)packageFile).isFile()) {
            return VAppInstallerResult.create(4);
        }
        try {
            apkLite = PackageParser.parseApkLite((File)packageFile, 0);
        }
        catch (PackageParser.PackageParserException e) {
            return VAppInstallerResult.create(4);
        }
        if (apkLite.splitName != null) {
            return this.installSplitPackageInternal((File)packageFile, apkLite, params);
        }
        VPackage pkg = null;
        try {
            pkg = PackageParserEx.parsePackage((File)packageFile);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        if (pkg == null || pkg.packageName == null) {
            return VAppInstallerResult.create(7);
        }
        VPackage existOne = PackageCacheManager.get(pkg.packageName);
        if (existOne != null) {
            if ((installFlags & 4) != 0) {
                return VAppInstallerResult.create(pkg.packageName, 3);
            }
            if ((installFlags & 2) == 0 && existOne.mVersionCode >= pkg.mVersionCode) {
                return VAppInstallerResult.create(pkg.packageName, 5);
            }
            resultFlags |= 2;
            if ((installFlags & 8) == 0) {
                VActivityManagerService.get().killAppByPkg(pkg.packageName, -1);
            }
        }
        VAppInstallerResult res = new VAppInstallerResult();
        res.packageName = pkg.packageName;
        res.flags = resultFlags;
        File appDir = VEnvironment.getDataAppPackageDirectory(pkg.packageName);
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtLHiAsI14mPmwgRDJ4EVRF")) + appDir);
        if (!FileUtils.ensureDirCreate(appDir)) {
            VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqKGkjQQZrDTwsKQc5JGIaGjV7MCBF")) + appDir);
            res.flags = 6;
            return res;
        }
        String cpuAbiOverride = params.getCpuAbiOverride();
        ArrayList<SharedLibraryInfo> sharedLibraryInfoList = new ArrayList<SharedLibraryInfo>();
        if (cpuAbiOverride != null) {
            primaryCpuAbi = cpuAbiOverride;
            secondaryCpuAbi = null;
            nativeLibraryRootRequiresIsa = true;
            nativeLibraryRootDir = VEnvironment.getDataAppLibDirectory(pkg.packageName);
            nativeLibraryDir = new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(primaryCpuAbi));
            secondaryNativeLibraryDir = null;
        } else if (outApplicationInfo != null) {
            primaryCpuAbi = ApplicationInfoL.primaryCpuAbi.get(outApplicationInfo);
            secondaryCpuAbi = ApplicationInfoL.secondaryCpuAbi.get(outApplicationInfo);
            String nativeLibraryPath = outApplicationInfo.nativeLibraryDir;
            nativeLibraryDir = nativeLibraryPath != null ? new File(nativeLibraryPath) : null;
            String secondaryNativeLibraryPath = ApplicationInfoL.secondaryNativeLibraryDir.get(outApplicationInfo);
            secondaryNativeLibraryDir = secondaryNativeLibraryPath != null ? new File(secondaryNativeLibraryPath) : null;
            String nativeLibraryRootPath = ApplicationInfoL.nativeLibraryRootDir.get(outApplicationInfo);
            nativeLibraryRootDir = new File(nativeLibraryRootPath);
            nativeLibraryRootRequiresIsa = ApplicationInfoL.nativeLibraryRootRequiresIsa.get(outApplicationInfo);
        } else {
            SharedLibraryInfo sharedLibraryInfo;
            SystemConfig.SharedLibraryEntry entry;
            boolean needCheckTestBaseLib;
            boolean isUse32bitAbi = false;
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    isUse32bitAbi = apkLite.use32bitAbi;
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            Set<String> abis = NativeLibraryHelperCompat.getPackageAbiList(((File)packageFile).getPath());
            String supported64bitAbi = NativeLibraryHelperCompat.findSupportedAbi(Build.SUPPORTED_64_BIT_ABIS, abis);
            String supported32bitAbi = NativeLibraryHelperCompat.findSupportedAbi(Build.SUPPORTED_32_BIT_ABIS, abis);
            String defaultAbi = Build.SUPPORTED_ABIS[0];
            if (!VirtualCore.get().isExtPackageInstalled()) {
                isUse32bitAbi = false;
                String string2 = defaultAbi = isUse32bitAbi ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDWgVJCpjCl0uPC0iVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDXwkMyNmMxo7"));
            }
            if (supported32bitAbi != null && (isUse32bitAbi || supported64bitAbi == null)) {
                primaryCpuAbi = supported32bitAbi;
                secondaryCpuAbi = supported64bitAbi;
            } else if (!(supported64bitAbi == null || isUse32bitAbi && supported32bitAbi != null)) {
                primaryCpuAbi = supported64bitAbi;
                secondaryCpuAbi = supported32bitAbi;
            } else {
                primaryCpuAbi = defaultAbi;
                secondaryCpuAbi = null;
            }
            HashSet<String> sharedLibraryFiles = new HashSet<String>();
            if (pkg.usesLibraries == null) {
                pkg.usesLibraries = new ArrayList();
            }
            if (pkg.usesOptionalLibraries == null) {
                pkg.usesOptionalLibraries = new ArrayList();
            }
            if (pkg.applicationInfo.targetSdkVersion < 23 && !pkg.usesLibraries.contains(this.ORG_APACHE_HTTP_LEGACY) && !pkg.usesOptionalLibraries.contains(this.ORG_APACHE_HTTP_LEGACY)) {
                pkg.usesLibraries.add(this.ORG_APACHE_HTTP_LEGACY);
            }
            boolean bl = needCheckTestBaseLib = pkg.usesLibraries.contains(this.ANDROID_TEST_RUNNER) || pkg.usesOptionalLibraries.contains(this.ANDROID_TEST_RUNNER);
            if ((needCheckTestBaseLib || BuildCompat.isR() && pkg.applicationInfo.targetSdkVersion < 30) && !pkg.usesLibraries.contains(this.ANDROID_TEST_BASE) && !pkg.usesOptionalLibraries.contains(this.ANDROID_TEST_BASE)) {
                pkg.usesLibraries.add(this.ANDROID_TEST_BASE);
            }
            for (String name : pkg.usesOptionalLibraries) {
                entry = this.mSystemConfig.getSharedLibrary(name);
                if (entry == null) {
                    VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki5bCW8JICVhEQozKi0YOW83TQNqETg5LhgpJGAaGiVlNCQ7Lik5JA==")) + name);
                    continue;
                }
                sharedLibraryFiles.add(entry.path);
                if (!BuildCompat.isS()) continue;
                sharedLibraryInfo = new SharedLibraryInfo(entry.path, null, null, entry.name, -1L, 0, new VersionedPackage(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iEVRF")), 0L), null, null, false);
                sharedLibraryInfoList.add(sharedLibraryInfo);
            }
            for (String name : pkg.usesLibraries) {
                entry = this.mSystemConfig.getSharedLibrary(name);
                if (entry == null) {
                    VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki5bCW8JIARiASAvKQguPWk3TQNqETg5LhgpJGAaGiVlNCQ7Lik5JA==")) + name);
                    continue;
                }
                sharedLibraryFiles.add(entry.path);
                if (!BuildCompat.isS()) continue;
                sharedLibraryInfo = new SharedLibraryInfo(entry.path, null, null, entry.name, -1L, 0, new VersionedPackage(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iEVRF")), 0L), null, null, false);
                sharedLibraryInfoList.add(sharedLibraryInfo);
            }
            if (!sharedLibraryFiles.isEmpty()) {
                pkg.applicationInfo.sharedLibraryFiles = sharedLibraryFiles.toArray(new String[0]);
            }
            nativeLibraryRootRequiresIsa = true;
            nativeLibraryRootDir = VEnvironment.getDataAppLibDirectory(pkg.packageName);
            nativeLibraryDir = new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(primaryCpuAbi));
            secondaryNativeLibraryDir = secondaryCpuAbi != null ? new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(secondaryCpuAbi)) : null;
        }
        ApplicationInfoL.primaryCpuAbi.set(pkg.applicationInfo, primaryCpuAbi);
        ApplicationInfoL.secondaryCpuAbi.set(pkg.applicationInfo, secondaryCpuAbi);
        if (nativeLibraryRootDir != null) {
            ApplicationInfoL.nativeLibraryRootDir.set(pkg.applicationInfo, nativeLibraryRootDir.getAbsolutePath());
        }
        ApplicationInfoL.nativeLibraryRootRequiresIsa.set(pkg.applicationInfo, nativeLibraryRootRequiresIsa);
        if (nativeLibraryDir != null) {
            pkg.applicationInfo.nativeLibraryDir = nativeLibraryDir.getAbsolutePath();
        }
        if (secondaryNativeLibraryDir != null) {
            ApplicationInfoL.secondaryNativeLibraryDir.set(pkg.applicationInfo, secondaryNativeLibraryDir.getAbsolutePath());
        }
        if (outApplicationInfo != null) {
            pkg.applicationInfo.publicSourceDir = outApplicationInfo.publicSourceDir;
            pkg.applicationInfo.sourceDir = outApplicationInfo.sourceDir;
            pkg.applicationInfo.sharedLibraryFiles = outApplicationInfo.sharedLibraryFiles;
            if (Build.VERSION.SDK_INT >= 26) {
                pkg.applicationInfo.splitNames = outApplicationInfo.splitNames;
            }
            pkg.applicationInfo.splitSourceDirs = outApplicationInfo.splitSourceDirs;
            pkg.applicationInfo.splitPublicSourceDirs = outApplicationInfo.splitPublicSourceDirs;
            if (ApplicationInfoP.sharedLibraryInfos != null) {
                List sharedLibraryInfos = ApplicationInfoP.sharedLibraryInfos(outApplicationInfo);
                ApplicationInfoP.sharedLibraryInfos(pkg.applicationInfo, sharedLibraryInfos);
            }
        } else {
            SettingConfig config = VirtualCore.getConfig();
            String packageFileStub = config.isEnableIORedirect() && config.isUseRealApkPath(pkg.packageName) ? VEnvironment.getPackageFileStub(pkg.packageName) : VEnvironment.getPackageFile(pkg.packageName).getPath();
            pkg.applicationInfo.publicSourceDir = packageFileStub;
            pkg.applicationInfo.sourceDir = packageFileStub;
            if (ApplicationInfoP.sharedLibraryInfos != null) {
                ApplicationInfoP.sharedLibraryInfos(pkg.applicationInfo, sharedLibraryInfoList);
            }
        }
        String scanSourcePath = VEnvironment.getDataAppPackageDirectory(pkg.packageName).getAbsolutePath();
        ApplicationInfoL.scanSourceDir.set(pkg.applicationInfo, scanSourcePath);
        ApplicationInfoL.scanPublicSourceDir.set(pkg.applicationInfo, scanSourcePath);
        NativeLibraryHelperCompat nativeLibraryHelper = new NativeLibraryHelperCompat((File)packageFile);
        VLog.d(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+LGUaOCtoHgY6Iz0iKGgmRSVlJCxKIxcLPg==")) + nativeLibraryRootDir, new Object[0]);
        if (outApplicationInfo == null) {
            if (!FileUtils.ensureDirCreate(nativeLibraryRootDir)) {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqKGkjQQZrDTwbLRcqI2YwLyNsHhorOD1fKWUgMzRoAR4bMTkiVg==")) + nativeLibraryRootDir);
            }
            if (FileUtils.ensureDirCreate(nativeLibraryDir)) {
                nativeLibraryHelper.copyNativeBinaries(nativeLibraryDir, primaryCpuAbi);
            } else {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqKGkjQQZrDTwbLRcqI2YwLyNsHhorOD4cI2w0TDQ=")) + nativeLibraryDir);
            }
            if (secondaryCpuAbi != null) {
                if (FileUtils.ensureDirCreate(secondaryNativeLibraryDir)) {
                    nativeLibraryHelper.copyNativeBinaries(secondaryNativeLibraryDir, secondaryCpuAbi);
                } else {
                    VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqKGkjQQZrDTw6Lhg2KWAwFiRlNx0pKRhbCmoKOD97AQIwOzkiI28FNyx+N1RF")) + secondaryNativeLibraryDir);
                }
            }
        }
        PackageSetting ps = existOne != null ? (PackageSetting)existOne.mExtras : new PackageSetting();
        ps.primaryCpuAbi = primaryCpuAbi;
        ps.secondaryCpuAbi = secondaryCpuAbi;
        ps.is64bitPackage = NativeLibraryHelperCompat.is64bitAbi(ps.primaryCpuAbi);
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0uLGMaIAJgHgY5Lwg2MW8FMAllNyQcPQMhDk8kOz15EVRF")) + outApplicationInfo);
        if (outApplicationInfo == null) {
            File privatePackageFile = VEnvironment.getPackageFile(pkg.packageName);
            VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDDwzKhcLIH4zSFo=")) + packageFile + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OG8KFi9mNCAgKAYmOW4FJDdrJyhIIxgEJ3czSFo=")) + privatePackageFile);
            boolean copied = false;
            try {
                FileUtils.copyFile((File)packageFile, privatePackageFile);
                copied = true;
            }
            catch (IOException e) {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqDWowLyhrNx4dLl5WJA==")) + privatePackageFile);
                e.printStackTrace();
            }
            if (!copied) {
                FileUtils.deleteDir(VEnvironment.getDataAppPackageDirectory(pkg.packageName));
                res.status = 6;
                return res;
            }
            packageFile = privatePackageFile;
            VEnvironment.chmodPackageDictionary((File)packageFile);
        }
        ps.dynamic = outApplicationInfo != null;
        ps.packageName = pkg.packageName;
        ps.libPath = nativeLibraryDir.getPath();
        ps.appId = VUserHandle.getAppId(this.mUidSystem.getOrCreateUid(pkg));
        if ((resultFlags & 2) != 0) {
            ps.lastUpdateTime = installTime;
        } else {
            ps.firstInstallTime = installTime;
            ps.lastUpdateTime = installTime;
            for (Object userId : (File)VUserManagerService.get().getUserIds()) {
                boolean installed = userId == false;
                ps.setUserState((int)userId, false, false, installed);
            }
        }
        PackageParserEx.savePackageCache(pkg);
        PackageCacheManager.put(pkg, ps);
        if (!this.mScanning) {
            this.mPersistenceLayer.save();
        }
        if (!(outApplicationInfo != null || ps.isRunInExtProcess() || (oatFile = VEnvironment.getOatFile(ps.packageName, instructionSet = VirtualRuntime.getCurrentInstructionSet())).exists() && (params.getInstallFlags() & 0x10) != 0)) {
            try {
                DexOptimizer.dex2oat(((File)packageFile).getPath(), oatFile.getPath());
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        VAccountManagerService.get().refreshAuthenticatorCache(null);
        if (!this.mScanning) {
            VExtPackageAccessor.syncPackages();
        }
        if ((installFlags & 1) != 0) {
            this.notifyAppInstalled(ps, -1);
        }
        res.status = 0;
        return res;
    }

    private VAppInstallerResult installSplitPackageInternal(File splitPackageFile, PackageParser.ApkLite apkLite, VAppInstallerParams params) {
        int flags = 1;
        VPackage basePackage = PackageCacheManager.get(apkLite.packageName);
        if (basePackage == null) {
            return VAppInstallerResult.create(apkLite.packageName, 8);
        }
        PackageSetting ps = (PackageSetting)basePackage.mExtras;
        if (basePackage.splitNames == null) {
            basePackage.splitNames = new ArrayList();
        } else if (basePackage.splitNames.contains(apkLite.splitName)) {
            if ((params.getInstallFlags() & 2) == 0) {
                return VAppInstallerResult.create(apkLite.packageName, 5);
            }
            basePackage.splitNames.remove(apkLite.splitName);
            flags |= 2;
        }
        if ((params.getInstallFlags() & 8) == 0) {
            VActivityManagerService.get().killAppByPkg(apkLite.packageName, -1);
        }
        basePackage.splitNames.add(apkLite.splitName);
        File privateSplitPackage = VEnvironment.getSplitPackageFile(apkLite.packageName, apkLite.splitName);
        try {
            FileUtils.copyFile(splitPackageFile, privateSplitPackage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String[] abis = NativeLibraryHelperCompat.getPackageAbiList(splitPackageFile.getPath()).toArray(new String[0]);
        if (abis.length > 0) {
            String primaryCpuAbi = abis[0];
            File nativeLibraryRootDir = VEnvironment.getDataAppLibDirectory(basePackage.packageName);
            File nativeLibraryDir = new File(nativeLibraryRootDir, VirtualRuntime.getInstructionSet(primaryCpuAbi));
            ps.primaryCpuAbi = primaryCpuAbi;
            ps.is64bitPackage = NativeLibraryHelperCompat.is64bitAbi(primaryCpuAbi);
            NativeLibraryHelperCompat nativeLibraryHelper = new NativeLibraryHelperCompat(splitPackageFile);
            if (FileUtils.ensureDirCreate(nativeLibraryDir)) {
                nativeLibraryHelper.copyNativeBinaries(nativeLibraryDir, primaryCpuAbi);
            } else {
                VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLEQo1PxcqKGkjQQZrDTwbLRcqI2YwLyNsHhorOD4cI2w0TDQ=")) + nativeLibraryDir);
            }
        }
        PackageParserEx.savePackageCache(basePackage);
        if (!this.mScanning) {
            VExtPackageAccessor.syncPackages();
        }
        return new VAppInstallerResult(apkLite.packageName, 0, flags);
    }

    @Override
    public synchronized boolean installPackageAsUser(int userId, String packageName) {
        PackageSetting ps;
        if (VUserManagerService.get().exists(userId) && (ps = PackageCacheManager.getSetting(packageName)) != null) {
            if (!ps.isInstalled(userId)) {
                ps.setInstalled(userId, true);
                VExtPackageAccessor.syncPackages();
                this.notifyAppInstalled(ps, userId);
                this.mPersistenceLayer.save();
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean uninstallPackage(String packageName) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        if (ps != null) {
            this.uninstallPackageFully(ps, true);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean uninstallPackageAsUser(String packageName, int userId) {
        if (!VUserManagerService.get().exists(userId)) {
            return false;
        }
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        if (ps != null) {
            int[] userIds = this.getPackageInstalledUsers(packageName);
            if (!ArrayUtils.contains(userIds, userId)) {
                return false;
            }
            if (userIds.length <= 1) {
                this.uninstallPackageFully(ps, true);
            } else {
                this.cleanPackageData(packageName, userId);
                ps.setInstalled(userId, false);
                this.mPersistenceLayer.save();
                this.notifyAppUninstalled(ps, userId);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean cleanPackageData(String packageName, int userId) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        if (ps == null) {
            return false;
        }
        VActivityManagerService.get().killAppByPkg(packageName, userId);
        VNotificationManagerService.get().cancelAllNotification(ps.packageName, userId);
        FileUtils.deleteDir(VEnvironment.getDataUserPackageDirectory(userId, packageName));
        FileUtils.deleteDir(VEnvironment.getDeDataUserPackageDirectory(userId, packageName));
        VExtPackageAccessor.cleanPackageData(new int[]{userId}, ps.packageName);
        ComponentStateManager.user(userId).clear(packageName);
        return true;
    }

    private void uninstallPackageFully(PackageSetting ps, boolean notify) {
        String packageName = ps.packageName;
        VActivityManagerService.get().killAppByPkg(packageName, -1);
        PackageCacheManager.remove(packageName);
        FileUtils.deleteDir(VEnvironment.getDataAppPackageDirectory(packageName));
        FileUtils.deleteDir(VEnvironment.getOatDirectory(packageName));
        PackageCleaner.cleanAllUserPackage(VEnvironment.getDataUserDirectory(), packageName);
        for (VUserInfo info : VUserManager.get().getUsers()) {
            VNotificationManagerService.get().cancelAllNotification(ps.packageName, info.id);
            ComponentStateManager.user(info.id).clear(packageName);
        }
        if (notify) {
            this.notifyAppUninstalled(ps, -1);
        }
        if (!this.mScanning) {
            VExtPackageAccessor.syncPackages();
        }
        VAccountManagerService.get().refreshAuthenticatorCache(null);
    }

    @Override
    public int[] getPackageInstalledUsers(String packageName) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        if (ps != null) {
            int[] userIds;
            IntArray installedUsers = new IntArray(5);
            for (int userId : userIds = VUserManagerService.get().getUserIds()) {
                if (!ps.readUserState((int)userId).installed) continue;
                installedUsers.add(userId);
            }
            return installedUsers.getAll();
        }
        return new int[0];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InstalledAppInfo> getInstalledApps(int flags) {
        ArrayList<InstalledAppInfo> infoList = new ArrayList<InstalledAppInfo>(this.getInstalledAppCount());
        boolean filterXposedModules = (flags & 0x10000000) != 0;
        boolean excludeXposedModules = (flags & 0x20000000) != 0;
        boolean enabledXposedModules = (flags & 0x40000000) != 0;
        ArrayMap<String, VPackage> arrayMap = PackageCacheManager.PACKAGE_CACHE;
        synchronized (arrayMap) {
            for (VPackage p : PackageCacheManager.PACKAGE_CACHE.values()) {
                PackageSetting setting;
                if (excludeXposedModules) {
                    if (p.xposedModule != null) continue;
                    setting = (PackageSetting)p.mExtras;
                    infoList.add(setting.getAppInfo());
                    continue;
                }
                if (filterXposedModules && p.xposedModule == null || enabledXposedModules && (!XposedModuleProfile.isXposedEnable() || !XposedModuleProfile.isModuleEnable(p.packageName))) continue;
                setting = (PackageSetting)p.mExtras;
                infoList.add(setting.getAppInfo());
            }
        }
        VLog.d(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pls/Hx1WOR4cLAsdRCUJXhgrByYGCVoqU1sjWx9XLVUeUx9LXgREKVdNTVo=")) + infoList.size(), new Object[0]);
        return infoList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InstalledAppInfo> getInstalledAppsAsUser(int userId, int flags) {
        ArrayList<InstalledAppInfo> infoList = new ArrayList<InstalledAppInfo>(this.getInstalledAppCount());
        ArrayMap<String, VPackage> arrayMap = PackageCacheManager.PACKAGE_CACHE;
        synchronized (arrayMap) {
            for (VPackage p : PackageCacheManager.PACKAGE_CACHE.values()) {
                PackageSetting setting = (PackageSetting)p.mExtras;
                boolean visible = setting.isInstalled(userId);
                if ((flags & 1) == 0 && setting.isHidden(userId)) {
                    visible = false;
                }
                if (!visible) continue;
                infoList.add(setting.getAppInfo());
            }
        }
        return infoList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getInstalledSplitNames(String packageName) {
        Class<PackageCacheManager> clazz = PackageCacheManager.class;
        synchronized (PackageCacheManager.class) {
            VPackage pkg;
            if (packageName != null && (pkg = PackageCacheManager.get(packageName)) != null && pkg.splitNames != null) {
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return pkg.splitNames;
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return Collections.emptyList();
        }
    }

    @Override
    public int getInstalledAppCount() {
        return PackageCacheManager.size();
    }

    @Override
    public boolean isAppInstalled(String packageName) {
        return packageName != null && PackageCacheManager.contain(packageName);
    }

    @Override
    public boolean isAppInstalledAsUser(int userId, String packageName) {
        if (packageName == null || !VUserManagerService.get().exists(userId)) {
            return false;
        }
        PackageSetting setting = PackageCacheManager.getSetting(packageName);
        if (setting == null) {
            return false;
        }
        return setting.isInstalled(userId);
    }

    private void notifyAppInstalled(PackageSetting setting, int userId) {
        String pkg = setting.packageName;
        int N = this.mRemoteCallbackList.beginBroadcast();
        while (N-- > 0) {
            try {
                if (userId == -1) {
                    ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageInstalled(pkg);
                    ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageInstalledAsUser(0, pkg);
                    continue;
                }
                ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageInstalledAsUser(userId, pkg);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.sendInstalledBroadcast(pkg, new VUserHandle(userId));
        this.mRemoteCallbackList.finishBroadcast();
    }

    private void notifyAppUninstalled(PackageSetting setting, int userId) {
        String pkg = setting.packageName;
        int N = this.mRemoteCallbackList.beginBroadcast();
        while (N-- > 0) {
            try {
                if (userId == -1) {
                    ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageUninstalled(pkg);
                    ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageUninstalledAsUser(0, pkg);
                    continue;
                }
                ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(N)).onPackageUninstalledAsUser(userId, pkg);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.sendUninstalledBroadcast(pkg, new VUserHandle(userId));
        this.mRemoteCallbackList.finishBroadcast();
    }

    private void sendInstalledBroadcast(String packageName, VUserHandle user) {
        Intent intent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2ALMFVgHyxF")));
        intent.setData(Uri.parse((String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDQJF")) + packageName)));
        VActivityManagerService.get().sendBroadcastAsUser(intent, user);
    }

    private void sendUninstalledBroadcast(String packageName, VUserHandle user) {
        Intent intent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xOA5hIgIAJwYAE2QxNEhiMiQKLBhSVg==")));
        intent.setData(Uri.parse((String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iDQJF")) + packageName)));
        VActivityManagerService.get().sendBroadcastAsUser(intent, user);
    }

    @Override
    public void registerObserver(IPackageObserver observer) {
        try {
            this.mRemoteCallbackList.register((IInterface)observer);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterObserver(IPackageObserver observer) {
        try {
            this.mRemoteCallbackList.unregister((IInterface)observer);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public InstalledAppInfo getInstalledAppInfo(String packageName, int flags) {
        Class<PackageCacheManager> clazz = PackageCacheManager.class;
        synchronized (PackageCacheManager.class) {
            PackageSetting setting;
            if (packageName != null && (setting = PackageCacheManager.getSetting(packageName)) != null) {
                // ** MonitorExit[var3_3] (shouldn't be in output)
                return setting.getAppInfo();
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return null;
        }
    }

    @Override
    public boolean isRunInExtProcess(String packageName) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        return ps != null && ps.isRunInExtProcess();
    }

    @Override
    public boolean isPackageLaunched(int userId, String packageName) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        return ps != null && ps.isLaunched(userId);
    }

    @Override
    public void setPackageHidden(int userId, String packageName, boolean hidden) {
        PackageSetting ps = PackageCacheManager.getSetting(packageName);
        if (ps != null && VUserManagerService.get().exists(userId)) {
            ps.setHidden(userId, hidden);
            this.mPersistenceLayer.save();
        }
    }

    void restoreFactoryState() {
        VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS4+KmojAiZiIwU8Oz0ML2UzNARrDTw/IwgtJGIwPCZqHlk7LipXD28VJCBoVh05CD5SVg==")), new Object[0]);
        FileUtils.deleteDir(VEnvironment.getRoot());
        VEnvironment.systemReady();
    }

    public void savePersistenceData() {
        this.mPersistenceLayer.save();
    }
}

