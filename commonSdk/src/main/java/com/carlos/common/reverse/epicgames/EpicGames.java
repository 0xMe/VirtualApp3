/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package com.carlos.common.reverse.epicgames;

import android.app.Application;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class EpicGames {
    public static void hook(ClassLoader classLoader, Application application) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrJBQNMS0cICQOKi06M2kgRCh4VwEoOF4HP3QJQAJ4DVA2PSklP3kOHTd+VwEsMgRaDngJTCl7ICMsPwMHO35THTN0DVwdPgRWJXskPzN5CgEoOF4HP3QJQAJ4CiMpODpWJHhTETR7AVRF")));
        final Class<?> Logger2 = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJAgAIWIgLDU=")), classLoader);
        XposedHelpers.callStaticMethod(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0uKG8KFithJygOKi06Lw==")), new Object[0]);
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJAgAIWIgLDU=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguOmwVPFo=")), String.class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwvLhgMCWIkODdsJD8z")) + param.args[0]);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                boolean bAllowLogging = XposedHelpers.getStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1oHh49KC0cDmkFSFo=")));
                boolean bAllowExceptionLogging = XposedHelpers.getStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1qARo5KAgmLmwjNCZ9EQYuLj4YKmIjSFo=")));
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwvLhgMCWIkODdsJD8z")) + param.args[0] + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFFhFgHlE1LCtbDWkFEi9lNyMx")) + bAllowLogging + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFFhFgHlE1LCsMIm4FGgJvER4cLCwEKWIgJCxsND8z")) + bAllowExceptionLogging);
                XposedHelpers.setStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1oHh49KC0cDmkFSFo=")), true);
                XposedHelpers.setStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1qARo5KAgmLmwjNCZ9EQYuLj4YKmIjSFo=")), true);
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJAgAIWIgLDU=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQcMKmowFlo=")), String.class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwgKS0MKWE0ODdsJD8z")) + param.args[0] + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKFithJzAoLF8IVg==")) + param.getResult());
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCJyg5KS4ACEsaTSpuIw5F")) + param.args[0]);
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJAgAIWIgLDU=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKmsjGgNiAVRF")), String.class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzw9LhcMJmAjNCB5Hl0eLwM6Vg==")) + param.args[0]);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                boolean bAllowLogging = XposedHelpers.getStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1oHh49KC0cDmkFSFo=")));
                boolean bAllowExceptionLogging = XposedHelpers.getStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1qARo5KAgmLmwjNCZ9EQYuLj4YKmIjSFo=")));
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzw9LhcMJmAjNCB5Hl0eLwM6Vg==")) + param.args[0] + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFFhFgHlE1LCtbDWkFEi9lNyMx")) + bAllowLogging + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFFhFgHlE1LCsMIm4FGgJvER4cLCwEKWIgJCxsND8z")) + bAllowExceptionLogging);
                XposedHelpers.setStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1oHh49KC0cDmkFSFo=")), true);
                XposedHelpers.setStaticBooleanField(Logger2, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ljw+DmoFGj1qARo5KAgmLmwjNCZ9EQYuLj4YKmIjSFo=")), true);
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJAgAIWIgLDU=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4+KmojSFo=")), String.class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzw+LRcMKksaTSpuIw5F")) + param.args[0]);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzw+LRcMKksaTSpuIw5F")) + param.args[0]);
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJj4+L2IIPCZqHho/Ki0cMw==")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JggcPG8jGi9iHwo0LAcYCWQVQT5oDAZJLhcqAWIFFiRmHiQ9KCw2Km8VSFo=")), String.class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwCLC4qCGAgGi9iHgY+KRg+AGgKODNmNSA0IBVbLGoaOFduJBooJAgcLHsFHiViIwJF")) + param.args[0] + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKFithJzAoLF8IVg==")) + param.getResult());
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwCLC4qCGAgGi9iHgY+KRg+AGgKODNmNSA0IBVbLGoaOFduJBooJAgcLHsFHiViIwJF")) + param.args[0] + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKFithJzAoLF8IVg==")) + param.getResult());
            }
        });
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojNAJjDig9LwdXPWoJMAVrCi8bJj4+L2IIPCZqHho/Ki0cMw==")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JggcPG8jGi9iHwo0LAcYCWQVQT5oDAZILD0MJWILPDBvATBF")), new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwCLC4qCGAgGi9iHgY+KRg+AGgKODNmNSQ6Jz0uLH0FFj1lM1FF")));
                HVLog.printInfo();
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQc6CWszPDdgDjApP1o7Ih07GwoCIzwCLC4qCGAgGi9iHgY+KRg+AGgKODNmNSQ6Jz0uLH0FFj1lM1E7Iy4lIg==")));
            }
        });
    }
}

