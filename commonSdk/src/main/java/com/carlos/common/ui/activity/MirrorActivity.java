/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Dialog
 *  android.content.ClipboardManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.EditText
 *  android.widget.Toast
 *  androidx.annotation.Nullable
 *  androidx.appcompat.app.AlertDialog
 *  androidx.appcompat.app.AlertDialog$Builder
 *  androidx.appcompat.widget.AppCompatImageView
 *  androidx.appcompat.widget.AppCompatTextView
 *  androidx.core.app.ActivityCompat
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ItemDecoration
 *  androidx.recyclerview.widget.RecyclerView$LayoutManager
 *  androidx.recyclerview.widget.StaggeredGridLayoutManager
 *  androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams
 *  com.kook.librelease.R$dimen
 *  com.kook.librelease.R$drawable
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$string
 *  com.kook.librelease.R$style
 */
package com.carlos.common.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.carlos.common.App;
import com.carlos.common.clouddisk.ClouddiskLauncher;
import com.carlos.common.clouddisk.http.HttpWorker;
import com.carlos.common.clouddisk.listview.FileItem;
import com.carlos.common.device.DeviceInfo;
import com.carlos.common.download.DownloadListner;
import com.carlos.common.download.DownloadManager;
import com.carlos.common.network.VNetworkManagerService;
import com.carlos.common.ui.activity.DeviceDetailActiivty;
import com.carlos.common.ui.activity.GDChooseLocationActivity;
import com.carlos.common.ui.activity.abs.nestedadapter.SmartRecyclerAdapter;
import com.carlos.common.ui.activity.base.VerifyActivity;
import com.carlos.common.ui.adapter.MirrorAdapter;
import com.carlos.common.ui.adapter.bean.DeviceData;
import com.carlos.common.ui.adapter.bean.MirrorData;
import com.carlos.common.ui.adapter.decorations.ItemOffsetDecoration;
import com.carlos.common.ui.parse.MirrorDataParse;
import com.carlos.common.utils.AESUtil;
import com.carlos.common.utils.FileTools;
import com.carlos.common.utils.MD5Utils;
import com.carlos.common.utils.ResponseProgram;
import com.carlos.common.utils.SPTools;
import com.carlos.common.utils.ZipTools;
import com.carlos.common.widget.LabelView;
import com.carlos.common.widget.MirrorDialog;
import com.carlos.common.widget.TextProgressBar;
import com.carlos.common.widget.toast.Toasty;
import com.kook.common.utils.HVLog;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.vloc.VLocation;
import java.io.File;
import java.util.List;

public class MirrorActivity
extends VerifyActivity
implements MirrorAdapter.OnAppClickListener {
    private static final String PKG_NAME_ARGUMENT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHh9lDywTJAVXWmcYBlo="));
    private static final String KEY_PKGNAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JCwuGWY2IAtqIlkRIgUMVg=="));
    private static final String APP_NAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JgU6AmYxBhFoDDBF"));
    private static final String KEY_USER = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JCwuGWY2NF5qDyxF"));
    RecyclerView mMirrorMenuPanel;
    MirrorAdapter mMirrorAdapter;
    AppCompatTextView startApplication;
    AppCompatImageView appIcon;
    AppCompatTextView appNameView;
    AppCompatTextView appVersion;
    LabelView appUserId;
    LabelView appIsExt;
    List<MirrorData> mirrorDataList;
    String packageName;
    String appName;
    int userId;
    public String mMirrorParseFileName = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwgYKm8jGgRpHiAqIy0LDmUzGjBvEVRF"));
    public static final int MENU_TYPE_WIFI = 0;
    public static final int MENU_TYPE_LOC = 1;
    public static final int MENU_TYPE_PHONE = 2;
    public static final int MENU_TYPE_SHORTCUT = 3;
    public static final int MENU_TYPE_DELETE_CLEAR = 4;
    public static final int MENU_TYPE_BACKUP_RECOVERY = 5;
    public static final int LOCATION_CODE = 12;
    public static final int MOCK_PHONE = 1001;
    public static int MENU_TYPE_BACKUP = 7;
    public static int MENU_TYPE_RECOVERY = 8;
    boolean isNetWork = true;

    public static void launch(Context context, String packageName, int userId) {
        Intent intent = VirtualCore.get().getLaunchIntent(packageName, userId);
        if (intent != null) {
            Intent loadingPageIntent = new Intent(context, MirrorActivity.class);
            loadingPageIntent.putExtra(PKG_NAME_ARGUMENT, packageName);
            loadingPageIntent.addFlags(0x10000000);
            loadingPageIntent.putExtra(KEY_PKGNAME, (Parcelable)intent);
            loadingPageIntent.putExtra(KEY_USER, userId);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+OWUzJC1iDFk7KgcLIA==")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OGwaLCthMgYwPT5SVg==")) + userId);
            context.startActivity(loadingPageIntent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mirror);
        this.userId = this.getIntent().getIntExtra(KEY_USER, -1);
        this.packageName = this.getIntent().getStringExtra(PKG_NAME_ARGUMENT);
        this.initView();
        this.initData(this.packageName, this.userId);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cE28jNDdmHjBF")));
        Application application = this.getApplication();
    }

    @Override
    protected boolean isCheckLog() {
        return true;
    }

    private void initData(String pkg, int userId) {
        this.mirrorDataList = MirrorData.mirrorDataList;
        this.mMirrorAdapter.setList(this.mirrorDataList);
        boolean runInExtProcess = VirtualCore.get().isRunInExtProcess(pkg);
        InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(pkg, 0);
        ApplicationInfo applicationInfo = installedAppInfo.getApplicationInfo(installedAppInfo.getInstalledUsers()[0]);
        PackageManager pm = this.getPackageManager();
        CharSequence sequence = applicationInfo.loadLabel(pm);
        this.appName = sequence.toString();
        Drawable icon = applicationInfo.loadIcon(pm);
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(pkg, 0);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.appIcon.setImageDrawable(icon);
        this.appIcon.setOnClickListener(view -> {
            String apkPath = installedAppInfo.getApkPath();
            File externalFilesDir = this.getExternalFilesDir(this.getPackageName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
            HVLog.d(externalFilesDir + applicationInfo.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")));
            FileTools.copyFile(apkPath, externalFilesDir + applicationInfo.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")));
        });
        this.appIcon.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                VNetworkManagerService networkManagerService = VNetworkManagerService.get();
                networkManagerService.systemReady(MirrorActivity.this.getContext());
                networkManagerService.randomDevices();
                return true;
            }
        });
        this.appNameView.setText((CharSequence)this.appName);
        if (packageInfo != null) {
            this.appVersion.setText((CharSequence)packageInfo.versionName);
        }
        this.getTitleLeftMenuIcon().setOnClickListener(view -> this.finish());
        if (userId > 0) {
            this.appUserId.setVisibility(0);
            this.appUserId.setText(userId + 1 + "");
        } else {
            this.appUserId.setVisibility(4);
        }
        if (runInExtProcess) {
            this.appIsExt.setVisibility(0);
            this.appIsExt.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JQdfLA==")));
        } else {
            this.appIsExt.setVisibility(4);
        }
    }

    private void initView() {
        this.mMirrorMenuPanel = (RecyclerView)this.findViewById(R.id.menu_panel);
        this.setTitleLeftMenuIcon(R.drawable.icon_back);
        this.setTitleName(R.string.title_mirror_manager);
        this.appIcon = (AppCompatImageView)this.findViewById(R.id.app_icon);
        this.appNameView = (AppCompatTextView)this.findViewById(R.id.app_name);
        this.appVersion = (AppCompatTextView)this.findViewById(R.id.app_version);
        this.appUserId = (LabelView)this.findViewById(R.id.app_user_id);
        this.appIsExt = (LabelView)this.findViewById(R.id.app_is_ext);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        this.mMirrorMenuPanel.setLayoutManager((RecyclerView.LayoutManager)layoutManager);
        this.mMirrorAdapter = new MirrorAdapter(this, this.packageName, this.userId);
        SmartRecyclerAdapter wrap = new SmartRecyclerAdapter(this.mMirrorAdapter);
        View footer = new View((Context)this);
        footer.setLayoutParams((ViewGroup.LayoutParams)new StaggeredGridLayoutManager.LayoutParams(-1, ResponseProgram.dpToPx((Context)this, 60)));
        wrap.setFooterView(footer);
        this.mMirrorMenuPanel.setAdapter((RecyclerView.Adapter)wrap);
        this.mMirrorMenuPanel.addItemDecoration((RecyclerView.ItemDecoration)new ItemOffsetDecoration((Context)this, R.dimen.desktop_divider));
        this.mMirrorAdapter.setAppClickListener(this);
        this.startApplication = (AppCompatTextView)this.findViewById(R.id.start_application);
        this.startApplication.setOnClickListener(view -> {
            if (this.isNovatioNecessaria()) {
                Toasty.warning(this.getContext(), this.getString(R.string.update_latest_version)).show();
                return;
            }
            this.checkExtProcessAndlunch(this.userId, this.packageName, this.appName);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cEWswMC9mNAYgLQYuPWoKGiRvVj8rKS4uDWYKLDZqHCweLz4eKVdNTVo=")) + requestCode + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86KmgaLAVgEQofKi02PXgVSFo=")) + resultCode + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFMDdmHiMi")) + data);
        if (requestCode == 12 && resultCode == -1) {
            int position = data.getIntExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgAKWUaMC9gJFlF")), -1);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgAKWUaMC9gJFs1W0QIVg==")) + position);
            this.mMirrorAdapter.notifyItemChanged(position);
        }
        if (requestCode == 1001 && resultCode == -1) {
            this.mMirrorAdapter.notifyDataSetChanged();
        }
        if (resultCode == 1107 && requestCode == 1) {
            String pkg = data.getStringExtra(KEY_PKGNAME);
            String appName = data.getStringExtra(APP_NAME);
            int userId = data.getIntExtra(KEY_USER, -1);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0qP28gMBF9JwozLD0cLmgnTChDKAcXPQhSVg==")) + pkg + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OGwaLCthMgYwP18HOg==")) + userId);
            this.launchMirrorApp(userId, this.packageName, appName);
            return;
        }
    }

    @Override
    public void onAppClick(int position, MirrorData model, String tag) {
        if (!this.isNetWork) {
            Toasty.warning((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsFA0ZJDw1YK14BAiIBGEcGEw1BExscAAknLE5XAyofLBsoUlsBDBwNOTcYUiUuHFodARpXE1QdUhsXRABcJRozSFo="))).show();
            return;
        }
        switch (model.getMenuType()) {
            case 0: {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgID2gFNCRONDg/LBVXPW8aGlFuDjwgMwQXPg==")) + model.getMenuType() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKICVhJAYgKQdfDngVSFo=")) + position + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKMANhHx43Ki0qCWUFLC5qClFF")) + this.tsp_mockwifi);
                if (this.tsp_mockwifi != 2) {
                    Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                    return;
                }
                this.modifyWifiAddr(position, model);
                break;
            }
            case 1: {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgID2gFNCRONDg/LBVXPW8aGlFuDjwgMwQXPg==")) + model.getMenuType() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKICVhJAYgKQdfDngVSFo=")) + position + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKMANhHx4uKQguLmUjQSRlEQYqLRcqI2AgRD0=")) + this.tsp_virtuallocation);
                if (this.tsp_virtuallocation != 2) {
                    Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                    return;
                }
                this.startMockLocation(position, model);
                break;
            }
            case 2: {
                if (Build.VERSION.SDK_INT >= 23 && VirtualCore.get().getTargetSdkVersion() >= 23) {
                    ActivityCompat.requestPermissions((Activity)this, (String[])new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl99HAZXIRYAE2QmMB1kDyhF"))}, (int)0);
                }
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgID2gFNCRONDg/LBVXPW8aGlFuDjwgMwQXPg==")) + model.getMenuType() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKICVhJAYgKQdfDngVSFo=")) + position + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKMANhHx43Ki0qCWozFiVlNysx")) + this.tsp_mockphone);
                if (this.tsp_mockphone != 2) {
                    Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                    return;
                }
                this.startMockDevice(position, model);
                break;
            }
            case 3: {
                this.createShortcut();
                break;
            }
            case 4: {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+PXkjSFo=")) + tag);
                if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsqU38VSFo=")).equals(tag)) {
                    boolean res = VirtualCore.get().cleanPackageData(this.packageName, this.userId);
                    Toast.makeText((Context)this, (CharSequence)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4EM2sVASh9ASQsPxc2OWUzQCg=")) + (res ? com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0uOWszNANhIFlF")) : com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4+CWoFNCxON1RF")))), (int)0).show();
                    break;
                }
                if (!com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsqU38jSFo=")).equals(tag)) break;
                new AlertDialog.Builder(this.getContext()).setTitle(R.string.tip_delete).setMessage((CharSequence)this.getContext().getString(R.string.text_delete_app, new Object[]{this.appName})).setPositiveButton(17039379, (dialog, which) -> {
                    VirtualCore.get().uninstallPackageAsUser(this.packageName, this.userId);
                    this.finish();
                }).setNegativeButton(17039369, null).show();
                break;
            }
            case 5: {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgID2gFNCRONDg/LBVXPW8aGlFuDjwgMwQXPg==")) + model.getMenuType() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKICVhJAYgKQdfDngVSFo=")) + position + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsLEhVoNTBAJBYcDGEmNBRgDzBNIhU6E2k2LABkJThJJBU1Pg==")) + 5);
                if (this.tsp_backupRecovery != 2) {
                    Toasty.warning(this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAH0MXFzNYEBNIAxoZDEQXWhFAXhs+AEABLFlbRgNEK0YdAQsJL0BJIQpGAgsuH1dYJkRaGyBHAiUBBkQ3WG8VAjBjDjMsMyolLGYzEiNoAR4dPC42KWAFSFo="))).show();
                    return;
                }
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+PXkjSFo=")) + tag);
                ClouddiskLauncher.getInstance().launcherCloud((Context)this);
                String content = "";
                if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsqU38VSFo=")).equals(tag)) {
                    content = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwnWkYWQjNYXh8NA1c7WkoGMRd8Cj87JFsrPlRXIVRVUz0UWTYBAR47MVFVCSEtER87IlooIRQYLxsTWwArGR4JGC5UFSY+XxsWPhwRQy4VFRggQApEJxgHECIGXxgtWhxZIhURBAUUAgAxE1YWLBgGPjFVEwQyXQopDkZaJRRYA0YJAiA7A0dJD0hAXhs+AEQ7ElgGQiVEXkYoARkZLEBaBz9GTUYAH0ArDnxXEzIaCR8uEQA/BVUNJQ0VPDFXXgQHXXokIiAbEEItRAkeJxUHGC8dEFBOPFsrOBs2PAVoJxAzXyEkLRoWRFF6CS0rXTY/HlcrMRAcLDE0ER87XBQPIwZ+Py0xXwAjXRRXIQECLA8cUzYBXFpWJUgfJ1RF"));
                    MirrorDialog.getInstance().showBakupAndRecovery((Activity)this, content, (view, dialog, textProgressBar) -> {
                        textProgressBar.setVisibility(0);
                        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwnWkYWQjNYEgsdOloJDhVWAzMCDQI+IxgiI05XLUgcPA8NXwABXFpWJUgfJ1RF")));
                        this.dataBakupAndRecovery(MENU_TYPE_BACKUP, dialog, textProgressBar);
                    });
                    break;
                }
                if (!com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsqU38jSFo=")).equals(tag)) break;
                content = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YAwssAglADkcGJVdBEjFIAFYFUlgUBxJEEBtKDAs3LUEyWlVGNi0dHglYJUQBB1ZKNgszM1tcD1o7RjZXLAsVEh8NIhs7AzAfPy0vUzYnPRkSLVUeUz1BE1sFJBUFSFo="));
                MirrorDialog.getInstance().singleRecoveryInputDialog((Activity)this, content, (view, editText, dialog, textProgressBar) -> {
                    String code = editText.getText().toString();
                    if (TextUtils.isEmpty((CharSequence)code)) {
                        this.getHandler().post(() -> Toasty.error((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YAwssAglADkcULRZBKzECABtcAVlNMQJEXhszARwNPg=="))).show());
                        return;
                    }
                    try {
                        String desDecrypt = AESUtil.desDecrypt(code);
                        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguKWAFNClhNwYsLF8IVg==")) + desDecrypt + " ");
                        if (!TextUtils.isEmpty((CharSequence)desDecrypt)) {
                            String[] strings;
                            textProgressBar.setVisibility(0);
                            textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtZXz05AgoVDEoGMVVBEx8MAAw3HQ==")));
                            String fileNameByPath = FileTools.getFileNameByPath(desDecrypt, false);
                            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguKWAFNClhNwYsLF8IVg==")) + desDecrypt + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsJIC5jDlE/Ij0iD2khRT9kETg/IwNXVg==")) + fileNameByPath);
                            if (!TextUtils.isEmpty((CharSequence)fileNameByPath) && (strings = fileNameByPath.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")))).length == 4) {
                                String deviceNo = strings[0];
                                String pkgName = strings[1];
                                String userId = strings[2];
                                String time = strings[3];
                                String currentDate = ClouddiskLauncher.getCurrentDate(time);
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguLmUVLCtoNBEi")) + deviceNo + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86KGUzPAB9Dl0/PT5SVg==")) + pkgName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKNANiASwJKF8IVg==")) + userId + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKMC9gDjMi")) + time + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OGswNARhNDA2LBU2OWUzBTI=")) + currentDate);
                                if (this.packageName.equals(pkgName)) {
                                    textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYAwssAglADkctJRU=")));
                                    boolean res = VirtualCore.get().cleanPackageData(this.packageName, this.userId);
                                    Toast.makeText((Context)this, (CharSequence)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4EM2sVASh9ASQsPxc2OWUzQCg=")) + (res ? com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0uOWszNANhIFlF")) : com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4+CWoFNCxON1RF")))), (int)0).show();
                                    ClouddiskLauncher.getInstance().launcherCloud((Context)this, currentDate, fileItemList -> {
                                        final String fileName = FileTools.getFileNameByPath(desDecrypt, true);
                                        for (FileItem fileItem : fileItemList) {
                                            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgbBjdgDjMiPxhSVg==")) + fileName + "  " + fileItem.toString());
                                            if (!fileName.equals(fileItem.getFilename())) continue;
                                            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwoVJkZJRgJYXgcUA1Y7E0cGEwtAXwMoAAlAGlgHLQxEXgc/ASAJXkBaIQZ7DFFF")));
                                            ClouddiskLauncher.getInstance().downFileByCloud(fileItem.getId()).done(downloadLink -> {
                                                if (downloadLink == null || TextUtils.isEmpty((CharSequence)downloadLink.getDlLink())) {
                                                    this.getHandler().post(() -> {
                                                        dialog.dismiss();
                                                        Toasty.warning((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YEAdAA0AnJUcsJRRBEloz"))).show();
                                                    });
                                                } else {
                                                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl84M1QXIisGNgAwWkACMhwsPyg=")));
                                                    DownloadManager mDownloadManager = DownloadManager.getInstance();
                                                    final File dataUserPackageDirectory = VEnvironment.getDataUserPackageDirectory(this.userId, this.packageName);
                                                    if (!dataUserPackageDirectory.exists()) {
                                                        dataUserPackageDirectory.mkdirs();
                                                    }
                                                    mDownloadManager.add(this.getContext(), downloadLink.getDlLink(), dataUserPackageDirectory.getPath(), fileName, new DownloadListner(){

                                                        @Override
                                                        public void onFinished() {
                                                            MirrorActivity.this.getHandler().post(() -> {
                                                                Toasty.success(MirrorActivity.this.getContext(), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYEloOAgkdDH4jSFo=")), 0).show();
                                                                File file = new File(dataUserPackageDirectory.getPath(), fileName);
                                                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmhSIiwYSQA0WxxEPVVJOi4GST8r")) + file.getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86PmUVHitLHgYpPxcMImwgAgZsIz8x")) + file.exists());
                                                                String fileMD5Sync = MD5Utils.fileMD5Sync(file);
                                                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgbEhZMDygZKj0pIA==")) + fileMD5Sync);
                                                                int i = ZipTools.uncompressZip(file.getAbsolutePath(), dataUserPackageDirectory.getPath(), zipName -> MirrorActivity.this.getHandler().post(() -> textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcjOUZJAws=")) + zipName)));
                                                                if (i == 0) {
                                                                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcjOUZJAwtYEloOAgkdDA==")));
                                                                    MirrorDataParse mirrorDataParse = new MirrorDataParse();
                                                                    mirrorDataParse.parseBackupData(dataUserPackageDirectory.getPath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + MirrorActivity.this.mMirrorParseFileName);
                                                                }
                                                                dialog.dismiss();
                                                                MirrorActivity.this.mMirrorAdapter.notifyDataSetChanged();
                                                            });
                                                        }

                                                        @Override
                                                        public void onProgress(float progress) {
                                                            MirrorActivity.this.getHandler().post(() -> textProgressBar.setProgress((int)progress));
                                                        }

                                                        @Override
                                                        public void onPause() {
                                                            MirrorActivity.this.getHandler().post(() -> Toast.makeText((Context)MirrorActivity.this.getContext(), (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwlAHEZJIR5YXgcUPwhSVg==")), (int)0).show());
                                                        }

                                                        @Override
                                                        public void onCancel() {
                                                            MirrorActivity.this.getHandler().post(() -> {
                                                                dialog.dismiss();
                                                                textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ol8uVg==")));
                                                                textProgressBar.setProgress(0);
                                                                textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYEg8rA1cNPQ==")));
                                                                Toast.makeText((Context)MirrorActivity.this.getContext(), (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYFUYQAhwdIkoBRiFBExsTACE/BksFSFo=")), (int)0).show();
                                                            });
                                                        }
                                                    });
                                                    mDownloadManager.downloadSingle(downloadLink.getDlLink());
                                                }
                                            });
                                            break;
                                        }
                                    });
                                } else {
                                    this.getHandler().post(() -> Toasty.warning((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwnWkYWQjNYAwssAglADkcGEw1ATQsoBUQ3GFgtEytEFUYSASANAXkVSFo=")) + this.appName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PzYrIhkNOVEUEVRF"))).show());
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        this.getHandler().post(() -> Toasty.error((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcdLUMWA15YEAs/AgtAP0cRQiZBNkZKAFcnG1haHwJEAw85AUQjKkEvLR0="))).show());
                    }
                });
            }
        }
    }

    public void dataBakupAndRecovery(int ITEM_TYPE, final Dialog dialog, final TextProgressBar textProgressBar) {
        DeviceInfo instance = DeviceInfo.getInstance((Context)this);
        String devicesNo = instance.getDevicesNo();
        textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtYEg8WAiAjWUcsLVFBNiEZAD8rDlgEQik=")));
        File dataUserPackageDirectory = VEnvironment.getDataUserPackageDirectory(this.userId, this.packageName);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrIhkNOVEUUzFKWFo7UlVWH1YePy0oX184KRlJHlo=")) + dataUserPackageDirectory.getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OEYyRiVYEzk+AhtABEcyJSBDKAcX")) + dataUserPackageDirectory.exists());
        if (!dataUserPackageDirectory.exists()) {
            this.getHandler().post(() -> {
                Toasty.error((Context)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsFA0ZJDw1YFQdLAiANMkdNBwJBAwMbABtcPlgsBz0="))).show();
                dialog.dismiss();
            });
            return;
        }
        String currentDateFolderId = ClouddiskLauncher.getInstance().getCurrentDateFolderId();
        MirrorDataParse mirrorDataParse = new MirrorDataParse();
        String backupData = mirrorDataParse.getBackupData(this.packageName, this.userId);
        FileTools.saveAsFileWriter(dataUserPackageDirectory.getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + this.mMirrorParseFileName, backupData);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRg+LGsYNANiASwCLwcqCW4jEitjER45Lhg2CmAjMAZ7N1RF")) + dataUserPackageDirectory.getAbsolutePath());
        long currentTimeMillis = System.currentTimeMillis();
        final String zipFile = this.getSavePath() + devicesNo + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + this.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + this.userId + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + currentTimeMillis + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz1XCW8FSFo="));
        final String desEncrypt = AESUtil.desEncrypt(zipFile);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD4YKGAjAiRiDQJF")) + zipFile + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsNMQwCUws1XToIVg==")) + desEncrypt);
        String desDecrypt = AESUtil.desDecrypt(desEncrypt);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcjOUZaBwhLHgo/Iys2PW4KRT9sHi4cEhlXVg==")) + desDecrypt);
        ResponseProgram.defer().when(() -> {
            try {
                if (ITEM_TYPE == MENU_TYPE_BACKUP) {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtYEFoXAiJYMXgVSFo=")));
                    textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtYEFoXAiJYMQ==")));
                    ZipTools.compressZip(dataUserPackageDirectory.getAbsolutePath(), zipFile, name -> this.getHandler().post(() -> textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtYEFoXAiJYMQ==")) + name)));
                    textProgressBar.setProgress(10);
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxoZXkYGGy9YEloOAgkdDHgVSFo=")) + zipFile);
                    textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxoZXkYGGy9YEloOAgkdDA==")));
                    int maxlength = 0x6300000;
                    File file = new File(zipFile);
                    long sizeKb = file.length() / 1024L;
                    long sizeMb = sizeKb / 1024L;
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Bwk/WkYWQj5YEg89AhwjREUWJVc=")) + sizeKb + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC4LOHsJIANjAQI/IgctIA==")) + sizeMb + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhYIHA==")));
                    if (file.length() >= (long)maxlength) {
                        this.getHandler().post(() -> MirrorDialog.getInstance().tipsSingleDialog((Activity)this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxtABkYEPSBYAwssAglADkoGGxVAXxtJOV45DmgIMzdEFUYSASANAQ==")) + sizeMb + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYLDkYWRg1ZSS0dAlcdXUcGJSg=")), new MirrorDialog.SingleDialogClickListener(){

                            @Override
                            public void onClick(View view, Dialog exitDialog) {
                                exitDialog.dismiss();
                            }
                        }));
                        dialog.dismiss();
                        return;
                    }
                    ClouddiskLauncher.getInstance().updaterCloud(zipFile, currentDateFolderId, new HttpWorker.UpLoadCallbackListener(){
                        long time = 0L;

                        @Override
                        public void onError(int count) {
                            MirrorActivity.this.getHandler().post(() -> {
                                textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcX0YWGyhZST0p")) + count + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhknGUMRByU=")));
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cWG8gFiVhMCY/XlYkMRUvACscTSIvEQsGIVUEECAZAAQgWgwOIhgHPjBXAict")) + zipFile);
                                FileTools.delete(zipFile);
                                dialog.dismiss();
                                MirrorDialog.getInstance().tipsSingleDialog((Activity)MirrorActivity.this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcX0YWGyhZST0p")) + count + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhknGUMRByVOUx81WSUZHBs7BxdUN1RF")), new MirrorDialog.SingleDialogClickListener(){

                                    @Override
                                    public void onClick(View view, Dialog exitDialog) {
                                        exitDialog.dismiss();
                                    }
                                });
                            });
                        }

                        @Override
                        public void Progress(double progress) {
                            long timeMillis = System.currentTimeMillis();
                            if (timeMillis - this.time > 2000L) {
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhcMD2gwFithJys8AlcdXUcGJShBX0ZIPy5SVg==")) + progress);
                                this.time = timeMillis;
                            }
                            if (textProgressBar != null) {
                                MirrorActivity.this.getHandler().post(() -> textProgressBar.setProgress((int)progress));
                            }
                            if (progress == 100.0) {
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cW2UVBi9hJB08AhkdOkoyFyxBFSFJABtYP1gtGwlEEBNPARs/I0EEJVZGEl4hMT5SVg==")) + zipFile);
                                FileTools.delete(zipFile);
                                MirrorActivity.this.getHandler().post(() -> {
                                    dialog.dismiss();
                                    MirrorDialog.getInstance().tipsSingleDialog((Activity)MirrorActivity.this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcdLUMRAwJYXl4LOC5SVg==")) + desEncrypt + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PSYjOBRSGCAVKAg9EUAgMwIBECxUAxwZEgldJx0HDCEGFSIvXiIoIFcWBDEZFVg7HiEjJUVbDzJHKx8SBxpcLg==")), new MirrorDialog.SingleDialogClickListener(){

                                        @Override
                                        public void onClick(View view, Dialog exitDialog) {
                                            exitDialog.dismiss();
                                            ClipboardManager cmb = (ClipboardManager)MirrorActivity.this.getSystemService(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ECW8FFiV9ASww")));
                                            cmb.setText((CharSequence)desEncrypt);
                                            Toasty.success((Context)MirrorActivity.this, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ByEBAEYBLRFYFT0qAhsNUkctEz4=")), 1).show();
                                        }
                                    });
                                });
                            }
                        }

                        @Override
                        public void onFinish(int count) {
                            MirrorActivity.this.getHandler().post(() -> {
                                if (textProgressBar != null) {
                                    textProgressBar.setText(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcX0YWGyg=")) + count + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwZQEYtRkw=")));
                                }
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cW2UVBi9hJB08AhkdOkoyFyxBFSFJABtYP1gtGwlEEBNPARs/I0EEJVZGEl4hMT5SVg==")) + zipFile);
                                FileTools.delete(zipFile);
                                dialog.dismiss();
                            });
                        }
                    });
                }
                if (ITEM_TYPE == MENU_TYPE_RECOVERY) {
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsBEkZaOQtZXhNPAhkVHQ==")));
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YEloOAgkdDHgVSFo=")) + zipFile);
                }
            }
            catch (Exception e) {
                HVLog.printException(e);
            }
        });
    }

    public void startMockDevice(int position, MirrorData model) {
        DeviceData deviceData = new DeviceData(this.getContext(), null, this.userId);
        int count = this.userId + 1;
        deviceData.name = this.getString(R.string.menu_mock_phone) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PBhSVg==")) + this.appName + (this.userId == 0 ? "" : com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MwhSVg==")) + count) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PAhSVg=="));
        deviceData.packageName = this.packageName;
        DeviceDetailActiivty.open((Activity)this, deviceData, position);
    }

    public void createShortcut() {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpcG0ZbQjJYFRMxAglAI0dNGz9BEgdB")));
        VirtualCore.OnEmitShortcutListener listener = new VirtualCore.OnEmitShortcutListener(){

            @Override
            public Bitmap getIcon(Bitmap originIcon) {
                return originIcon;
            }

            @Override
            public String getName(String originName) {
                return originName;
            }
        };
        boolean shortcut = VirtualCore.get().createShortcut(this.userId, this.packageName, listener);
        Toast.makeText((Context)this.getContext(), (CharSequence)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpcG0ZbQjI=")) + (shortcut ? com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwpcAkZJWh8=")) : com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwnL0MWPSs=")))), (int)0).show();
    }

    public void startMockLocation(int position, MirrorData model) {
        VLocation location = VLocationManager.get().getLocation(this.packageName, this.userId);
        Intent intent = new Intent(this.getContext(), GDChooseLocationActivity.class);
        if (location != null) {
            intent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKmwKNDdgHx4oKi0qOWUzLCVlN1RF")), (Parcelable)location);
        }
        intent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKmwKNDdgV1k/LRg2KG4gDSZsETgqIz4+IWIFSFo=")), this.packageName);
        intent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKmwKNDdgV1k/LRg2KG4gDSZvDjAgKS4YIA==")), this.userId);
        intent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgAKWUaMC9gJFlF")), position);
        this.startActivityForResult(intent, 12);
    }

    public void modifyWifiAddr(int position, MirrorData model) {
        String SSID_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki02CWgIGiFiAQZF")) + this.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + this.userId;
        String MAC_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+OWYzQStnAVRF")) + this.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + this.userId;
        SettingConfig.FakeWifiStatus fakeWifiStatus = App.getApp().mConfig.getFakeWifiStatus(this.packageName, this.userId);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this, R.style.VACustomTheme);
        View view1 = this.getLayoutInflater().inflate(R.layout.dialog_change_wifi, null);
        builder.setView(view1);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        EditText editText1 = (EditText)view1.findViewById(R.id.edt_ssid);
        if (fakeWifiStatus != null) {
            editText1.setText((CharSequence)fakeWifiStatus.getSSID());
        } else {
            editText1.setText((CharSequence)SettingConfig.FakeWifiStatus.DEFAULT_SSID);
        }
        EditText editText2 = (EditText)view1.findViewById(R.id.edt_mac);
        if (fakeWifiStatus != null) {
            editText2.setText((CharSequence)fakeWifiStatus.getMAC());
        } else {
            editText2.setText((CharSequence)SettingConfig.FakeWifiStatus.DEFAULT_MAC);
        }
        dialog.setCancelable(false);
        view1.findViewById(R.id.btn_cancel).setOnClickListener(arg_0 -> MirrorActivity.lambda$modifyWifiAddr$17((Dialog)dialog, arg_0));
        view1.findViewById(R.id.btn_ok).setOnClickListener(arg_0 -> this.lambda$modifyWifiAddr$18((Dialog)dialog, editText1, editText2, SSID_KEY, MAC_KEY, position, arg_0));
    }

    private /* synthetic */ void lambda$modifyWifiAddr$18(Dialog dialog, EditText editText1, EditText editText2, String SSID_KEY, String MAC_KEY, int position, View v2) {
        dialog.dismiss();
        try {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwxcP0YtQh9mJAY+KQMmDWwFSFo=")));
            String ssid = editText1.getText().toString();
            String mac = editText2.getText().toString();
            SPTools.putString(this.getContext(), SSID_KEY, ssid);
            SPTools.putString(this.getContext(), MAC_KEY, mac);
            this.mMirrorAdapter.notifyItemChanged(position);
        }
        catch (Exception e) {
            Toast.makeText((Context)this.getContext(), (int)R.string.input_loc_error, (int)0).show();
        }
    }

    private static /* synthetic */ void lambda$modifyWifiAddr$17(Dialog dialog, View v2) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwxcP0YtQh9mJAY+KQMmP24jMClrAQJF")));
        dialog.dismiss();
    }
}

