/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.DialogFragment
 *  android.util.Log
 */
package com.carlos.common.reverse.hooker;

import android.app.DialogFragment;
import android.util.Log;
import com.carlos.common.reverse.hooker.HookDialogUtils;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@HookClass(value=DialogFragment.class)
public class DialogFragmentHooker {
    static List<String> hiddenDialog = new ArrayList<String>();
    @HookMethodBackup(value="onStart")
    static Method onStartBackup;
    @HookMethodBackup(value="dismiss")
    static Method dismiss;

    @HookMethod(value="onStart")
    public static void onStart(DialogFragment thiz) throws Throwable {
        if (VirtualCore.get().isMainProcess()) {
            SandHook.callOriginByBackup(onStartBackup, thiz, new Object[0]);
            return;
        }
        String packageName = VClient.get().getCurrentPackage();
        String clzName = thiz.getClass().getName();
        Log.e((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBgAD2UzNAQ=")), (String)(packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhYqCWsVHiViIjwqLwc6D2kjMAZ4EQYbIT0qO2EzESM=")) + clzName + "  " + thiz.getDialog().hashCode()));
        if (HookDialogUtils.isHiddDialog(clzName, packageName)) {
            HookDialogUtils.addHiddenDialogCode(thiz.getDialog().hashCode());
        }
        SandHook.callOriginByBackup(onStartBackup, thiz, new Object[0]);
    }

    static {
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC0LCGszBSZgNDAaKi0XDmUzND91NzgbLgcMKWMKESlqDh0dKC0ACmpSBjNrNzA6IAdfM2wKFgJlIywfIBU6EWszLCVmDlkgIgcMDmUhBi9oAQIcLj5SVg==")));
    }
}

