/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.widget.Toast
 */
package com.carlos.common.ui.delegate;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import java.io.File;

public class MyAppRequestListener
implements VirtualCore.AppRequestListener {
    private final Context mContext;

    public MyAppRequestListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onRequestInstall(String path) {
        MyAppRequestListener.info(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0qP28gMyhjDlkpLBciCG8zLCZrIFAr")) + path);
        VAppInstallerParams params = new VAppInstallerParams();
        VAppInstallerResult res = VirtualCore.get().installPackage(Uri.fromFile((File)new File(path)), params);
        if (res.status == 0) {
            MyAppRequestListener.info(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcKWwFJCRgVyRF")) + res.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phc2I2szLCthJys2")));
            boolean success = VActivityManager.get().launchApp(0, res.packageName);
            MyAppRequestListener.info(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+I2ojLCBLHiAsI14mVg==")) + (success ? com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0uOWszNANhIFlF")) : com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4+CWoJBlo="))));
        } else {
            MyAppRequestListener.info(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcKWwFJCRgVyRF")) + res.packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgiP2UVGSRLHjAqIz1fKH4zAiVrESsxPQhSVg==")) + res.status);
        }
    }

    private static void info(String msg) {
        VLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgc6KH0VBgNmHiAoKhcMKA==")), msg);
    }

    @Override
    public void onRequestUninstall(String pkg) {
        Toast.makeText((Context)this.mContext, (CharSequence)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcLGgaFiliASQgPxgMDmwjMANvETgdLAQ6CGIFPDBuASw9Pl9XVg==")) + pkg), (int)0).show();
    }
}

