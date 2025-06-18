/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.AlertDialog$Builder
 *  android.app.Application
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.provider.Settings
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AlertDialog$Builder
 *  com.google.android.material.bottomsheet.BottomSheetDialog
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$string
 *  com.kook.librelease.R$style
 */
package com.carlos.common.ui.activity.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.common.clouddisk.listview.FileItem;
import com.carlos.common.ui.activity.base.PermissionRequestActivity;
import com.carlos.common.ui.activity.base.VActivity;
import com.carlos.common.utils.InstallTools;
import com.carlos.common.utils.SPTools;
import com.carlos.common.widget.BottomSheetLayout;
import com.carlos.common.widget.toast.Toasty;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kook.common.utils.HVLog;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.core.AppCallback;
import com.lody.virtual.client.core.AppLauncherCallback;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.client.stub.RequestExternalStorageManagerActivity;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.PermissionCompat;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.oem.OemPermissionHelper;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VerifyActivity
extends VActivity
implements AppLauncherCallback {
    private static String META_DATA_KEY;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetLayout bottomSheetLayout;
    private static final String PKG_NAME_ARGUMENT;
    private static final String KEY_PKGNAME;
    private static final String APP_NAME;
    private static final String KEY_USER;
    protected final int ACTION_REQUEST_CODE_LAUNCH = 1;
    protected ViewOnclick mViewOnclick = new ViewOnclick();
    protected Dialog mDialog;
    AlertDialog.Builder mBuilder;
    String[] whitelist = new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qD2szSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qD2swRVo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1XCW8FSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0MP28jSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4YKGsVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0qIGwFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4uIGgVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MzkmIg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4uVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1XVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz42LA==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5bMw==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz42M2wKFjdjDlk/Iz5SVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qOg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0qP28jSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06PGgjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0lKWkFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4uKGwVFlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4ID2sjAlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+ImwzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+Imw0LFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4AKWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4AKWkjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1fKGsVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz42KGUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4EI2sVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5XP28jSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qDWgzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06KGwFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz06KGwKRVo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1fDm8zSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1fDm8wRVo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4IKH8zSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4YKGsVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4YKWozSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4YDWgzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4mCmozSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0qLGgjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0qLGszSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz0qIGgjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qLWgzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4MP2wFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4qDmoFSFo="))};
    private List<String> history = new ArrayList<String>();
    private List<FileItem> currentFile = new ArrayList<FileItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VirtualCore.get().setAppCallback(new AppCallback(){

            @Override
            public void beforeStartApplication(String packageName, String processName, Context context) {
            }

            @Override
            public void beforeApplicationCreate(String packageName, String processName, Application application) {
            }

            @Override
            public void afterApplicationCreate(String packageName, String processName, Application application) {
            }

            @Override
            public void beforeActivityOnCreate(Activity activity) {
            }

            @Override
            public void afterActivityOnCreate(Activity activity) {
            }

            @Override
            public void beforeActivityOnStart(Activity activity) {
            }

            @Override
            public void afterActivityOnStart(Activity activity) {
            }

            @Override
            public void beforeActivityOnResume(Activity activity) {
            }

            @Override
            public void afterActivityOnResume(Activity activity) {
            }

            @Override
            public void beforeActivityOnStop(Activity activity) {
            }

            @Override
            public void afterActivityOnStop(Activity activity) {
            }

            @Override
            public void beforeActivityOnDestroy(Activity activity) {
            }

            @Override
            public void afterActivityOnDestroy(Activity activity) {
            }
        });
    }

    public void tipsDialog(String content, View.OnClickListener ... onclick) {
        if (this.mDialog == null) {
            this.mBuilder = new AlertDialog.Builder((Context)this, R.style.VACustomTheme);
            View view1 = this.getLayoutInflater().inflate(R.layout.dialog_tips, null);
            this.mBuilder.setView(view1);
            if (!this.isFinishing()) {
                this.mDialog = this.mBuilder.show();
            }
            if (this.mDialog == null) {
                return;
            }
            this.mDialog.setCanceledOnTouchOutside(false);
            TextView textView = (TextView)view1.findViewById(R.id.tips_content);
            textView.setText((CharSequence)content);
            this.mDialog.setCancelable(false);
            if (onclick != null && onclick.length == 2) {
                view1.findViewById(R.id.double_btn_layout).setVisibility(View.VISIBLE);
                view1.findViewById(R.id.btn_cancel).setOnClickListener(onclick[0]);
                view1.findViewById(R.id.btn_ok).setOnClickListener(onclick[0]);
            } else if (onclick != null && onclick.length == 1) {
                view1.findViewById(R.id.single_btn_layout).setVisibility(View.VISIBLE);
                view1.findViewById(R.id.single_btn).setOnClickListener(onclick[0]);
            }
        } else {
            this.mDialog.show();
        }
    }

    private String getMetaDataFromApp(Context context, String meta) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            value = appInfo.metaData.getString(meta);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    protected void checkExtProcessAndlunch(int userId, String packageName, String appName) {
        if (this.bottomSheetLayout == null) {
            this.bottomSheetLayout = (BottomSheetLayout)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.layout_bottom_sheet, null);
        }
        if (this.bottomSheetDialog == null) {
            this.bottomSheetDialog = new BottomSheetDialog((Context)this, R.style.BottomSheetDialog);
            this.bottomSheetDialog.setContentView((View)this.bottomSheetLayout);
        }
        this.bottomSheetLayout.beginShow(packageName);
        try {
            this.bottomSheetDialog.show();
        } catch (Exception e) {

        }
        this.launchMirrorApp(userId, packageName, appName);
    }

    protected void launchMirrorApp(int userId, String packageName, String appName) {
        block18: {
            if (VirtualCore.get().isRunInExtProcess(packageName)) {
                if (!VirtualCore.get().isExtPackageInstalled()) {
                    Toast.makeText((Context)this, (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhgEM2saLCtLHgY2Iy42OW8zOyhjDlk/LhgcD2MKAil5HyAqKAg+O2sjNwQ=")), (int)0).show();
                    return;
                }
                if (!VExtPackageAccessor.hasExtPackageBootPermission()) {
                    Toast.makeText((Context)this, (int)R.string.permission_boot_content, (int)0).show();
                    return;
                }
            }
            try {
                if (userId == -1 || packageName == null) break block18;
                boolean runAppNow = true;
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] permissions;
                    InstalledAppInfo info = VirtualCore.get().getInstalledAppInfo(packageName, userId);
                    ApplicationInfo applicationInfo = info.getApplicationInfo(userId);
                    boolean isExt = VirtualCore.get().isRunInExtProcess(info.packageName);
                    int runHostTargetSdkVersion = VirtualCore.get().getHostApplicationInfo().targetSdkVersion;
                    if (isExt) {
                        try {
                            runHostTargetSdkVersion = this.getPackageManager().getApplicationInfo((String)VirtualCore.getConfig().getExtPackageName(), (int)0).targetSdkVersion;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (this.checkExtPackageBootPermission()) {
                            return;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (BuildCompat.isR() && runHostTargetSdkVersion >= 30 && info.getApplicationInfo((int)0).targetSdkVersion < 30 && (isExt && !VExtPackageAccessor.isExternalStorageManager() || !isExt && !Environment.isExternalStorageManager())) {
                            new AlertDialog.Builder(this.getContext()).setTitle(R.string.permission_boot_notice).setMessage(R.string.request_external_storage_manager_notice).setCancelable(false).setNegativeButton((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwAVg==")), (dialog, which) -> RequestExternalStorageManagerActivity.request(VirtualCore.get().getContext(), isExt)).show();
                            return;
                        }
                    }
                    if (PermissionCompat.isCheckPermissionRequired(applicationInfo) && !PermissionCompat.checkPermissions(permissions = VPackageManager.get().getDangerousPermissions(info.packageName), isExt)) {
                        runAppNow = false;
                        PermissionRequestActivity.requestPermission(this.getActivity(), permissions, appName, userId, packageName, 6);
                    }
                }
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhcMI2ohJAJhHFk1LCklIA==")) + runAppNow);
                if (runAppNow) {
                    this.channelLimit = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHFEzKgccLg==")));
                    this.channelStatus = this.getPersistentValueToInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHyggLwg2LWoFSFo=")));
                    int channelLimitLocal = SPTools.getInt((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHFEzKgccLg==")), 0);
                    long currentTimeMillisLimit = 0L;
                    if (channelLimitLocal == 0) {
                        SPTools.putLong((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li0uKm8jNCZmHwozKgcMUmwjOCRqDjBTIxgII2YVSFo=")), System.currentTimeMillis());
                    } else {
                        currentTimeMillisLimit = SPTools.getLong((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li0uKm8jNCZmHwozKgcMUmwjOCRqDjBTIxgII2YVSFo=")));
                    }
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHFEzKgccLmczNCloAQUx")) + channelLimitLocal + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFLCB9Dlk2KAdbU2wjPC9vV1FF")) + this.channelLimit);
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHyggLwg2LWoOIFo=")) + this.channelStatus + "    ");
                    if (this.channelLimit <= channelLimitLocal) {
                        Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                        this.finish();
                        return;
                    }
                    if (this.channelStatus == 0) {
                        Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                        this.finish();
                        return;
                    }
                    if (!this.checkUpgrade()) {
                        VActivityManager.get().launchApp(userId, packageName);
                    }
                    SPTools.putInt((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fP2ojBitgHFEzKgccLg==")), channelLimitLocal + 1);
                    this.finish();
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkExtPackageBootPermission() {
        if (VirtualCore.get().isExtPackageInstalled()) {
            if (!VExtPackageAccessor.hasExtPackageBootPermission()) {
                this.showPermissionDialog();
                return true;
            }
            if (BuildCompat.isQ() && !Settings.canDrawOverlays((Context)this.getActivity())) {
                this.showOverlayPermissionDialog();
                return true;
            }
        }
        return false;
    }

    private void showOverlayPermissionDialog() {
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwodAkYBPTI="))).setMessage((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwoJCkZbBxVZEiUhAhojA0ctBxFAXh8zAAlAGlgXJSpEEAMhAVYZPUEXWgZGAC0RHxpYKUQXOUxHKS0dBwoZXUYWWgpZXzEUAiABBkdNORNAEBMRPC5SVg=="))).setCancelable(false).setNegativeButton((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwAVg==")), (dialog, which) -> {
            Intent intent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kpKAg2LmwjMC1sIxosLT0qI2AgRClkDCRTICwIGWEhGgxgHAoRIwYAU30YFg5nJTBBIiwYUmIjSFo=")));
            intent.setData(Uri.parse((String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+OWUzJC1iDQJF")) + this.getPackageName())));
            this.startActivityForResult(intent, 0);
        }).show();
    }

    public void showPermissionDialog() {
        Intent intent = OemPermissionHelper.getPermissionActivityIntent((Context)this);
        new AlertDialog.Builder((Context)this).setTitle(R.string.permission_boot_notice).setMessage(R.string.permission_boot_content).setCancelable(false).setNegativeButton((CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwAVg==")), (dialog, which) -> {
            if (intent != null) {
                try {
                    this.startActivity(intent);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).show();
    }

    @Override
    public boolean checkVerify() {
        return true;
    }

    @Override
    public String currentActivity() {
        return this.getLocalClassName();
    }

    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IixfEX8VSFo=")));
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; ++i) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OhhSVg==")));
                }
                hexString.append(appendString);
                hexString.append(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OD5SVg==")));
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(value=23)
    private boolean checkAndRequestPermission() {
        ArrayList<String> lackedPermission = new ArrayList<String>();
        if (this.checkSelfPermission(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl99HAZXIRYAE2QmMB1kDyhF"))) != PackageManager.PERMISSION_GRANTED) {
            lackedPermission.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl99HAZXIRYAE2QmMB1kDyhF")));
        }
        if (this.checkSelfPermission(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIgoXOzwAU30xJExmMjBOLiwqAmYmFlo="))) != PackageManager.PERMISSION_GRANTED) {
            lackedPermission.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIgoXOzwAU30xJExmMjBOLiwqAmYmFlo=")));
        }
        if (this.checkSelfPermission(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw+H2UmLBB9JVlKIiwqGWEhHl5jNThOLQUYHw=="))) != PackageManager.PERMISSION_GRANTED) {
            lackedPermission.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw+H2UmLBB9JVlKIiwqGWEhHl5jNThOLQUYHw==")));
        }
        if (lackedPermission.size() == 0) {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwkFE0NND0xZEC0dAhw3KEcWIQ9BAAdPABtABF4vWhFFEC0sAQsNBkEyQiBGKR80H1c/DEQtFz5+NFk0KRcuKmojSFo=")));
            return true;
        }
        String[] requestPermissions = new String[lackedPermission.size()];
        lackedPermission.toArray(requestPermissions);
        this.requestPermissions(requestPermissions, 1024);
        return false;
    }

    public List<FileItem> getCurrentFile() {
        return this.currentFile;
    }

    public String getCloudDiskDirectory(String directoryName) {
        for (FileItem fileItem : this.currentFile) {
            if (!directoryName.equals(fileItem.getFilename())) continue;
            return fileItem.getId();
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
    }

    static {
        PKG_NAME_ARGUMENT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHh9lDywTJAVXWmcYBlo="));
        KEY_PKGNAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JCwuGWY2IAtqIlkRIgUMVg=="));
        APP_NAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JgU6AmYxBhFoDDBF"));
        KEY_USER = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JCwuGWY2NF5qDyxF"));
        META_DATA_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii42D28gIC9gJFkPIBUEVg=="));
    }

    class ViewOnclick
    implements View.OnClickListener {
        ViewOnclick() {
        }

        public void onClick(View v) {
            if (v.getId() == R.id.single_btn) {
                VerifyActivity.this.mDialog.dismiss();
                VerifyActivity.this.finish();
            } else if (v.getId() == R.id.btn_cancel) {
                VerifyActivity.this.mDialog.dismiss();
            } else if (v.getId() == R.id.btn_ok) {
                VerifyActivity.this.mDialog.dismiss();
                try {
                    String assetFileName = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgEI2gzAiZsJyw/KhcMOWoFBSZoDjwi"));
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwZXEMRFxVYABMAAlcBLA==")) + assetFileName);
                    InputStream inputStream = null;
                    File dir = VerifyActivity.this.getCacheDir();
                    try {
                        inputStream = VerifyActivity.this.getAssets().open(assetFileName);
                        File apkFile = new File(dir, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgEI2gzAiZsJyw/KhcMOWoFBSZoDjwi")));
                        FileUtils.writeToFile(inputStream, apkFile);
                        InstallTools.install((Context)VerifyActivity.this, apkFile);
                    }
                    catch (IOException e) {
                        HVLog.printException(e);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

