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

public class BallPool {
    public static void hook(ClassLoader classLoader, Application application) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrJBQNMS0cICQ0Ki1fCX4zAiVlDRoeIxgcI30gTSxlVx4uKi4ILG8VFjNqAQIdJi0YO3lTPFo=")));
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojPCVgJDgoKAMYPGwgRStoNzg6Ll8cJWEwPDZvHl0yIz42JWwnBgVqJCw0Jz1fKGxTAl9vAQ4yLQcMVg==")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4+CGIFGi0=")), Integer.TYPE, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojEi9gNAY5KhccKn8VGi9rJ1k/LS4+KGAVOCpsJFwdJy42IWoaMBBrEQI7LxcYJmwYODNlNzAhLAcqJw==")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4+CGIFGi0=")), Integer.TYPE, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }
        });
    }
}

