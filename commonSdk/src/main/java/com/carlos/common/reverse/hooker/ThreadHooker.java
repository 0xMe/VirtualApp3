/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.carlos.common.reverse.hooker;

import android.util.Log;
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

@HookClass(value=Thread.class)
public class ThreadHooker {
    static List<String> bypassList = new ArrayList<String>();
    @HookMethodBackup(value="start")
    static Method methodStart;

    @HookMethod(value="start")
    public static void start(Thread thiz) throws Throwable {
        if (VirtualCore.get().isMainProcess()) {
            SandHook.callOriginByBackup(methodStart, thiz, new Object[0]);
            return;
        }
        String packageName = VClient.get().getCurrentPackage();
        String clzName = thiz.getClass().getName();
        Log.e((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRhfKmgVJCxrHh41KS0MKA==")), (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBgAD2UzNCxLHwo0Iz0MOWk3TQNvETg5KgQ6Dn0KNC5pDjwuIRhbL2sOTVo=")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86LGUFAjJ3N1RF")) + thiz + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86OWoKTQB9Dl0/PT5SVg==")) + clzName));
        thiz.dumpStack();
        if (bypassList.contains(clzName)) {
            thiz.interrupt();
            Log.e((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRhfKmgVJCxrHh41KS0MKA==")), (String)(clzName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhhfD2ozQStiVyQsLwgqLw=="))));
            return;
        }
        SandHook.callOriginByBackup(methodStart, thiz, new Object[0]);
    }

    static {
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYI2sKGixvDBoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxYYI2sKGixvDBoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxYYBWMKGixvDBoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYI2sKGixvDBoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYI2MKGixvDBpNKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxYYI2sKGixvDhoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxgYBWMKGixnDhoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYI2sKGixvDhpNIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYI2sKGixvDBoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxYYBWsKGixvDhoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYBWsKGixvDhoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYI2MIGixnDhoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYI2MKGixvDBoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxgYBWsKGixvDhpNKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxgYBWsKGixvDhoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxYYBWMKGixvDhpNIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxgYI2MIGixvDBoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYBWsIGixvDBoiIi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYI2sKGixvDBoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbOxYYI2sKGixvDBoiKi5SVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbKj4+Vg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbKj5bVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbKS4MVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbLi4MVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbLAgEVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgYVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxgMVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIy4MVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbKj4MVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIxhSVg==")));
        bypassList.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojAiZ9JCM2Iy0MP2UgRS9vHhEbIQcMKWcVBSlhASA5JwcAO2wzMA5vJwYvIQVbKGwwOD9pJFgzOghSVg==")));
    }
}

