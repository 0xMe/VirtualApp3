/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package com.carlos.common.reverse.xhs;

import android.app.Application;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import java.io.File;

public class XHSHook {
    public static String XPOSED_MAIN = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgtCG8jGipmMFk7Kj02KG8FLCx1NFk7LD02J2JTRVBlHlk8Ly4cBmsFHiRoHgoc"));

    public static void hook(ClassLoader classLoader, Application application) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrJBQNMS0cICQ0Ki1fCX4zAiVlDRozIxgcIWMKRClrHgY8ODpXVg==")));
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LD4+LmtSBiR9Dlk9OjwqLmoVLCZrJ1RF")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4AKmoVJAY=")), String.class, Object[].class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                String args0 = (String)param.args[0];
                Object[] args1 = (Object[])param.args[1];
                Object paramResult = param.getResult();
                if (com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4qP2wFJyViHiAgLwNePWoFSFo=")).equals(args0)) {
                    HVLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBgAD2UzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86A2wKFi9gNDs2KD1fKG8jQQZ4EVRF")) + args0 + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OG8FJAR9Dl0AKAgqLW8wATI=")) + paramResult);
                    if (!com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4qP2wFJyViHiAgLwNfP28FPyZuER4bLj4YKk4zBitlJ1RF")).equals(paramResult)) {
                        param.setResult(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4qP2wFJyViHiAgLwNfP28FPyZuER4bLj4YKk4zBitlIB4hKQguLQ==")));
                        boolean exists = new File(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4qP2wFJyViHiAgLwNfP28FPyZuER4bLj4YKk4zBitlIB4hKQguLQ=="))).exists();
                        HVLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBgAD2UzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86A2wKFi9gNDs2KD1fKG8jQQZ4Vj8cLgg+Cn0OAi9pATAqOQgYKWUJBixlERoyJQdeJWsaGiN/EQo6Iy5aOHsFNDBjASggIyoIVg==")) + exists);
                    }
                }
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }
        });
        XposedHelpers.findAndHookConstructor(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LD4+LmtSBiR9Dlk9OjwqLmoVLCZrJ1RF")), classLoader, byte[].class, new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                byte[] args = (byte[])param.args[0];
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwMHO35THTN0DVwdPgRWJXskPzN5CgEoOF4HP3QJQAJ4DVA2PSklP3kOEixvATA7ITkiJ2wgRQV4EVRF")));
                if (args != null) {
                    String xpclz = new String(args);
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwMHO34aRQJ9JFEiPT5SVg==")) + xpclz);
                }
                super.afterHookedMethod(param);
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
        });
    }
}

