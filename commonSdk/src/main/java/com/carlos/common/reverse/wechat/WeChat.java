/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package com.carlos.common.reverse.wechat;

import android.app.Application;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class WeChat {
    public static void hook(ClassLoader classLoader, Application application) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsrJBQNMS0cICQ0Ki1fCX4zAiVlDRo/LhgcJWIKRT98NFEcODpXVg==")));
        Class<?> logClz = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZsJywiPC06KH0FFiFsJygcIz4uKWUaLwRiAQYy")), classLoader);
        Class<?> logImpClz = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZsJywiPC06KH0FFiFsJygcIz4uKWUaLwRiAQYyDhVXJm4mBgNqN1RF")), classLoader);
        XposedHelpers.setStaticIntField(logClz, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IxguLmgVHlo=")), 0);
        Object getLogLevel = XposedHelpers.callStaticMethod(logClz, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGIFGi1oHjAuKAdbVg==")), new Object[0]);
        XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZsJywiPC06KH0FFiFsJygcIz4uKWUaLwRiAQYy")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGIFGi1oHjAuKAdbVg==")), new XC_MethodHook(){

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(0);
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86CmozGiFLHig1KgMYLmkjMClrARo/PC4IL04zNC9vIB45KT5bCmszGiZqHiw6Ji1XOnw2QQFpDSwyLQcqQGozPA5iATw/Kl4lOmkFGgZkNyg6KhgECnczSFo=")) + param.getResult());
            }

            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
            }
        });
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl86CmozGiFLHig1KgMYLmkjMClrARo/PC4IL0tTOCJuATBAKQgIAmsKOD9qDTxF")) + getLogLevel);
    }
}

