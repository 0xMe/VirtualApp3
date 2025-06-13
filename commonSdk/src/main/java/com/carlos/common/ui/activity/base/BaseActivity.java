/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.annotation.DrawableRes
 *  androidx.annotation.StringRes
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.core.content.FileProvider
 *  com.kook.librelease.R$color
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$style
 */
package com.carlos.common.ui.activity.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.carlos.common.device.DeviceInfo;
import com.carlos.common.download.DownloadListner;
import com.carlos.common.download.DownloadManager;
import com.carlos.common.network.VNetworkManagerService;
import com.carlos.common.persistent.StoragePersistenceServices;
import com.carlos.common.persistent.VPersistent;
import com.carlos.common.ui.adapter.bean.SoftVersions;
import com.carlos.common.ui.utils.StatusBarUtil;
import com.carlos.common.utils.MD5Utils;
import com.kook.common.utils.HVLog;
import com.kook.deviceinfo.DeviceSplash;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseActivity
extends AppCompatActivity
implements View.OnClickListener {
    String TAG = BaseActivity.class.getSimpleName();
    public static final int DOWNLOAD_FAIL = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_SUCCESS = 2;
    Handler mHandler = new Handler();
    protected SoftVersions mSoftVersions;
    DeviceSplash mDeviceSplash;
    VNetworkManagerService networkManagerService;
    protected int tsp_virtualbox = 0;
    protected int tsp_dingtalk = 0;
    protected int tsp_dingtalkPic = 0;
    protected int tsp_mockphone = 0;
    protected int tsp_mockwifi = 0;
    protected int tsp_virtuallocation = 0;
    protected int tsp_hookXposed = 0;
    protected int tsp_backupRecovery = 0;
    protected int channelLimit = 0;
    protected int channelStatus = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        this.setStatusBar();
        if (this.isCheckLog()) {
            this.networkManagerService = VNetworkManagerService.get();
            this.networkManagerService.systemReady((Context)this);
            this.networkManagerService.devicesLog();
            if (this.mDeviceSplash == null) {
                this.mDeviceSplash = new DeviceSplash();
            }
            this.mDeviceSplash.attachBaseApplication((Activity)this);
        }
        if (this.isCheckLog()) {
            this.tsp_virtualbox = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKmwKNDdgHiw1LRhSVg==")));
            this.tsp_dingtalk = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgYCGgwMDdgHg5F")));
            this.tsp_dingtalkPic = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgYCGgwMDdgHg4CKQcqVg==")));
            this.tsp_mockphone = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwgAOWUwICBgJFk/")));
            this.tsp_mockwifi = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwgAOWUwPC9iNAZF")));
            this.tsp_virtuallocation = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKmwKNDdgHlE1Ly0iLmwjNCY=")));
            this.tsp_hookXposed = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBgAD2U2RQJgJyg/KBhSVg==")));
            this.tsp_backupRecovery = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4+OWUwNAJpNDA5Ki4+PWoaLFo=")));
            this.channelLimit = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHFEzKgccLg==")));
            this.channelStatus = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHyggLwg2LWoFSFo=")));
        }
        this.checkUpgrade();
    }

    protected boolean checkUpgrade() {
        DeviceInfo deviceInfo = DeviceInfo.getInstance((Context)this);
        int versionCode = deviceInfo.getVersionCode();
        String versionName = deviceInfo.getVersionName((Context)this);
        int upgradeEnforce = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc6PW8jJCxiDDA2KD1fKG4FGlo=")));
        int upgradeVersion = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc6PW8jJCxiDzw/Iz4qMW8FMFo=")));
        String fileName = this.getVPersistent().getBuildConfig(VPersistent.fileName);
        VPersistent persistent = this.getVPersistent();
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKm8zAiVgMig1KBcLIA==")) + versionCode + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKNAJiJyw7KBcMBmkgRQNqAQYbPy5SVg==")) + upgradeVersion);
        if (versionCode < upgradeVersion) {
            String appConfigMd5 = persistent.getBuildConfig(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgbEixMAVRF")));
            String localApk = this.getFilesDir().getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MywqD2wzBiRgJCAwOi5SVg==")) + fileName;
            File apkFile = new File(localApk);
            String fileMD5Sync = MD5Utils.fileMD5Sync(apkFile);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgbEhZMDygZKj0pIA==")) + fileMD5Sync + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFJAJjIjwzKhcLIA==")) + apkFile.exists() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OGoFGil9DlERIxcDIA==")) + localApk + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFJAJhHCg1Kj0+MWkLPCx/ClFF")) + appConfigMd5);
            if (fileMD5Sync.equals(appConfigMd5)) {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Bwk/WkYWQj5YXh8XA1dAJUdJE0xBE0YM")));
                this.installApkWindow(localApk);
                return true;
            }
        }
        return false;
    }

    private void installApkWindow(String filePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this, R.style.VACustomTheme);
        View view1 = this.getLayoutInflater().inflate(R.layout.dialog_tips, null);
        builder.setView(view1);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        TextView textView = (TextView)view1.findViewById(R.id.tips_content);
        textView.setText((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwkBXEYyJQJYNhsKAgpYCEdNIQZBADk7BgsBAllbDzJEAwc9AUABDkERA1BDFQsK")));
        dialog.setCancelable(false);
        view1.findViewById(R.id.double_btn_layout).setVisibility(0);
        view1.findViewById(R.id.btn_cancel).setOnClickListener(arg_0 -> this.lambda$installApkWindow$0((Dialog)dialog, arg_0));
        view1.findViewById(R.id.btn_ok).setOnClickListener(arg_0 -> this.lambda$installApkWindow$1((Dialog)dialog, filePath, arg_0));
    }

    int getPersistentValueToInt(String key) {
        VPersistent persistent = this.getVPersistent();
        Map<String, String> buildAllConfig = persistent.buildAllConfig;
        String value = buildAllConfig.get(key);
        if (!TextUtils.isEmpty((CharSequence)value)) {
            try {
                return Integer.parseInt(value);
            }
            catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    VPersistent getVPersistent() {
        StoragePersistenceServices storagePersistenceServices = StoragePersistenceServices.get();
        VPersistent persistent = storagePersistenceServices.getVPersistent();
        return persistent;
    }

    protected boolean isCheckLog() {
        return false;
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor((Activity)this, this.getResources().getColor(R.color.color_6bc196), 1);
    }

    protected void setTitleName(@StringRes int res) {
        TextView title = (TextView)this.findViewById(R.id.toolbar_title);
        if (title != null) {
            title.setText(res);
        }
    }

    protected void setTitleName(String res) {
        TextView title = (TextView)this.findViewById(R.id.toolbar_title);
        if (title != null) {
            title.setText((CharSequence)res);
        }
    }

    protected ImageView getTitleLeftMenuIcon() {
        ImageView leftIv = (ImageView)this.findViewById(R.id.toolbar_left_menu);
        return leftIv;
    }

    protected void setTitleLeftMenuIcon(@DrawableRes int res) {
        ImageView leftIv = (ImageView)this.findViewById(R.id.toolbar_left_menu);
        leftIv.setImageResource(res);
    }

    public boolean isInstallAppByPackageName(Context context, String packageName) {
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

    protected void onDestroy() {
        super.onDestroy();
    }

    public void finish() {
        InputMethodManager manager;
        View view = this.getCurrentFocus();
        if (view != null && (manager = (InputMethodManager)this.getSystemService(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")))) != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }

    public void onClick(View v) {
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public static int getDeviceId(String pkg, int userId) {
        int hashCode = pkg.hashCode();
        return hashCode + userId;
    }

    public String getSavePath() {
        String path = Build.VERSION.SDK_INT > 29 ? this.getExternalFilesDir(null).getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) : Environment.getExternalStorageDirectory().getPath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg=="));
        return path;
    }

    private SoftVersions getSoftVersions() {
        return this.mSoftVersions;
    }

    public boolean isUpgrade() {
        String versionsNumber;
        SoftVersions softVersions = this.getSoftVersions();
        if (softVersions != null && !TextUtils.isEmpty((CharSequence)(versionsNumber = softVersions.getNumber()))) {
            int versionNumber = Integer.parseInt(versionsNumber);
            int versionCode = DeviceInfo.getInstance((Context)this).getVersionCode((Context)this);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2BW8FPAR9Dgo/Pxg+PWoaAi9lJxo6JC0uL30wLDV7N1RF")) + versionsNumber + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsNJR4VUwtXWFo3Xhk7Gx4eUi1BHDYdOFUzSFo=")) + versionCode);
            if (versionCode < versionNumber) {
                this.downloadVersion();
                return true;
            }
        }
        return false;
    }

    protected boolean isNovatioNecessaria() {
        SoftVersions softVersions = this.getSoftVersions();
        if (softVersions != null) {
            return softVersions.novatioNecessaria == 1;
        }
        return false;
    }

    private void downloadVersion() {
        SoftVersions softVersions = this.getSoftVersions();
        if (softVersions != null) {
            String updateUrl = softVersions.getUpdateUrl();
            DownloadManager downloadManager = DownloadManager.getInstance();
            downloadManager.add((Context)this, updateUrl, this.getDataDir().getAbsolutePath(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KA==")) + softVersions.getNumber() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")), new DownloadListner(){

                @Override
                public void onFinished() {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgACGAjAiZjASg0KAc1Og==")));
                }

                @Override
                public void onProgress(float progress) {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhcMD2gwFithJysi")) + progress);
                }

                @Override
                public void onPause() {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ByAVXUYyGyRLUwsNXSU3IFcNBxEaUjkeWio6KWAxOCRqASwuPhhSVg==")));
                }

                @Override
                public void onCancel() {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ByAVXUYyGyRLUwsNXSU3IFcNBxEaUjkeWio6KWA2NCRsNCwuKTk6Vg==")));
                }
            });
            downloadManager.downloadSingle(updateUrl);
        }
    }

    public boolean isNetwork() {
        try {
            URL url = new URL(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC45Dm4VQS9rHisbLT4ALw==")));
            InputStream stream = url.openStream();
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2U2gaMD1gJywx")));
            return true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void install(File file) {
        try {
            Intent intent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk4xIBZmDzxF")));
            intent.addFlags(0x10000000);
            intent.addFlags(1);
            if (Build.VERSION.SDK_INT >= 24) {
                String authority = this.getPackageName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06KmowOC9iHjAq"));
                Uri uri = FileProvider.getUriForFile((Context)this, (String)this.getPackageName().concat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06KmowOC9iHjAq"))), (File)file);
                intent.setDataAndType(uri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWUVMCx1NzgbLgcMKWMKESllHiQsKghbIWsJEjNvJzA/JQg6LA==")));
            } else {
                Uri uri = Uri.fromFile((File)file);
                intent.setDataAndType(uri, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWUVMCx1NzgbLgcMKWMKESllHiQsKghbIWsJEjNvJzA/JQg6LA==")));
            }
            this.startActivity(intent);
        }
        catch (Exception e) {
            HVLog.printException(e);
        }
    }

    private /* synthetic */ void lambda$installApkWindow$1(Dialog dialog, String filePath, View v2) {
        dialog.dismiss();
        File apkFile = new File(filePath);
        this.install(apkFile);
    }

    private /* synthetic */ void lambda$installApkWindow$0(Dialog dialog, View v2) {
        dialog.dismiss();
        this.finish();
    }
}

