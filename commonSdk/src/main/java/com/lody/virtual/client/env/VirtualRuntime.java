/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.pm.ApplicationInfo
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Process
 */
package com.lody.virtual.client.env;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;
import java.util.HashMap;
import java.util.Map;
import mirror.android.ddm.DdmHandleAppNameJBMR1;
import mirror.dalvik.system.VMRuntime;

public class VirtualRuntime {
    private static final Handler sUIHandler = new Handler(Looper.getMainLooper());
    private static String sInitialPackageName;
    private static String sProcessName;
    private static final Map<String, String> ABI_TO_INSTRUCTION_SET_MAP;

    public static Handler getUIHandler() {
        return sUIHandler;
    }

    public static String getProcessName() {
        return sProcessName;
    }

    public static String getInitialPackageName() {
        return sInitialPackageName;
    }

    public static String getInstructionSet(String abi) {
        String instructionSet = ABI_TO_INSTRUCTION_SET_MAP.get(abi);
        if (instructionSet == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcKWwaIAJgJywgKAc1OmYhRQl+MzxF")) + abi);
        }
        return instructionSet;
    }

    public static String getCurrentInstructionSet() {
        return VMRuntime.getCurrentInstructionSet.call(new Object[0]);
    }

    public static void setupRuntime(String processName, ApplicationInfo appInfo) {
        if (sProcessName != null) {
            return;
        }
        sInitialPackageName = appInfo.packageName;
        sProcessName = processName;
        mirror.android.os.Process.setArgV0.call(processName);
        DdmHandleAppNameJBMR1.setAppName.call(processName, 0);
    }

    public static <T> T crash(Throwable e) throws RuntimeException {
        e.printStackTrace();
        throw new RuntimeException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcMP2ogLDd9Jw08Iz0MD28KBit4HjAgKS0iJ2E0OCFpDhobLy4cVg==")), e);
    }

    public static boolean is64bit() {
        if (Build.VERSION.SDK_INT >= 23) {
            return Process.is64Bit();
        }
        return VMRuntime.is64Bit.call(VMRuntime.getRuntime.call(new Object[0]), new Object[0]);
    }

    public static String adjustLibName(String libName) {
        if (VirtualCore.get().isMainPackage()) {
            return libName;
        }
        return libName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy4uIGwFSFo="));
    }

    public static void exit() {
        VLog.d(VirtualRuntime.class.getSimpleName(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQdfCWwJIAJhNB45KAgqL340Iyh7DjMrMwQuD0kORVo=")), VirtualRuntime.getProcessName(), VirtualCore.get().getProcessName());
        Process.killProcess((int)Process.myPid());
    }

    public static boolean isArt() {
        return System.getProperty(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4+LmtSBj5gClkuKAguL2wjNCY="))).startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oj5SVg==")));
    }

    static {
        ABI_TO_INSTRUCTION_SET_MAP = new HashMap<String, String>(16);
        ABI_TO_INSTRUCTION_SET_MAP.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDWgVJCpjAVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDQ==")));
        ABI_TO_INSTRUCTION_SET_MAP.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDWgVJCpjCl0uPC0iVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDQ==")));
        ABI_TO_INSTRUCTION_SET_MAP.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgYKG8zSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgYKG8zSFo=")));
        ABI_TO_INSTRUCTION_SET_MAP.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgYKG80OwY=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgYKG80OwY=")));
        ABI_TO_INSTRUCTION_SET_MAP.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDXwkMyNmMxo7")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcMDXwkMFo=")));
    }
}

