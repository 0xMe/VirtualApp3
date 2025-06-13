/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package com.carlos.common.reverse.grame;

import android.app.Application;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import java.io.File;

public class Nexon {
    public static File file = new File(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My02PGszJARiVx4RKj02KG8FLCx1JywsKgg9KX0gAih8NCwqLBgiKWwnBgFsEQIZJQcmP2VTRTBsJyQ0KioAEWojMARgJAYwJi4+OX8OTCVgARovKS4AI2JTAi9pATAqOQgYKWUJBgRoHlk6JjlfJ28FLyR/AVRF")));

    public static void hook(ClassLoader classLoader, Application application) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrJBQNMS0cICQ0Ki1fCX4zAiVlDRobLhdfKWA0RStvATM7ODpXVg==")));
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojBitnHh42Oj4mLW5SMCpoDg0bJCtfQH0FFiZvHgo7PzwYKWUzOAVoN1RF")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2WWgVFgViIl01KBcMVg==")), new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }
        });
    }
}

