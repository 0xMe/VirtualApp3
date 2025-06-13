/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package com.carlos.common.reverse.hooker;

import android.os.Bundle;
import android.util.Log;
import com.kook.librelease.StringFog;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import com.swift.sandhook.annotation.HookReflectClass;
import com.swift.sandhook.annotation.MethodParams;
import java.lang.reflect.Method;
import java.util.Set;

@HookReflectClass(value="com.google.android.gms.games.ui.signin.SignInActivity")
public class PlayGamesHooker {
    @HookMethodBackup(value="onCreate")
    @MethodParams(value={Bundle.class})
    static Method method_m1;

    @HookMethod(value="onCreate")
    @MethodParams(value={Bundle.class})
    public static void m1(Object thiz, Bundle v1) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4YPWohAiZlDiggKQg+MWUwLFo=")));
        Set keys = v1.keySet();
        for (String key : keys) {
            Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC4uJ3kjSFo=")) + key));
            Object object = v1.get(key);
        }
        SandHook.callOriginByBackup(method_m1, thiz, v1);
    }
}

