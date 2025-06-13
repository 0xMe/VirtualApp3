/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.util.Log
 */
package com.carlos.common.reverse.dingding;

import android.content.Intent;
import android.util.Log;
import com.carlos.common.reverse.ReflectionApplication;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import com.lody.virtual.helper.utils.VLog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DingTalk
extends ReflectionApplication {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBUhDWAFAiZiJQo7KhcEVg=="));

    public static void hook(ClassLoader classLoader) {
        if (!REFLECTION_DTALK) {
            return;
        }
        try {
            Class<?> ActionRequest = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm8zLC1qHiwsKQc5KmEzLClqHhocLyoqHWggMAVqNxpAJAgmPG4FMCI=")), classLoader);
            Class<?> Plugin = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm8zLC1qHiwsKQc5KmEzLClqHhocLyoqQGUaNDFlERpF")), classLoader);
            Class<?> Method2 = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LD4+LmtSBiR9Dlk9Oj4uPWkVOCtoJC8bJBguCmMaAi8=")), classLoader);
            Class<?> TheOneActivityBase = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm8zLC1qHiwsKQc5KmEzLClqHhocLyoqO2wzAiJoHg05KBccLGQgAjFmJx4ZLAciCWwKAhR9ASg/")), classLoader);
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm4jMCxsNwYaLgQcIGMKRSJqHiQbKghfO2wjNwRgAR45JC4MKGwaHh5uJB40Jgg2LGUaOC9mEQZF")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cU2gaPAlgNwo/Kj42Vg==")), Intent.class, new XC_MethodHook(){

                @Override
                public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.afterHookedMethod(methodHookParam);
                    VLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBUhDWAFAiZiJQo7KhcEVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRgYCGgwMDdgHg4QLwgqPWYjAgZqDiQaKgcXJEsaJCBqHBodIz4AKm9TTVo=")) + methodHookParam.getResult(), new Object[0]);
                }

                @Override
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.beforeHookedMethod(methodHookParam);
                }
            });
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm8zLC1qHiwsKQc5KmEzLClqHhocLyoqDmUaNDFlER05JQdfM24FNAJuJyc5IQcqCWoFSFo=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGQwNDc=")), ActionRequest, new XC_MethodHook(){

                @Override
                public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.afterHookedMethod(methodHookParam);
                    VLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBUhDWAFAiZiJQo7KhcEVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwkdM0YEGwtZXhNLAhoBHngVSFo=")) + methodHookParam.getResult(), new Object[0]);
                    VLog.printStackTrace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGQwNDc=")));
                    methodHookParam.setResult(null);
                }

                @Override
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.beforeHookedMethod(methodHookParam);
                }
            });
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm8zLC1qHiwsKQc5KmEzLClqHhocLyoqDmUaNDFlER05JQdfM24FNAJuJyc5IQcqCWoFSFo=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGILFl5uJzA7")), ActionRequest, new XC_MethodHook(){

                @Override
                public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.afterHookedMethod(methodHookParam);
                    VLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBUhDWAFAiZiJQo7KhcEVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwkdM0YEGwtZXhNLAhoBHngVSFo=")) + methodHookParam.getResult(), new Object[0]);
                    VLog.printStackTrace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGILFl5uJzA7")));
                    methodHookParam.setResult(null);
                }

                @Override
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    methodHookParam.setResult(null);
                    super.beforeHookedMethod(methodHookParam);
                }
            });
            Class<?> apiPermissionInfo = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm4gRS9vNyg5PC06J2EwQSxlJywiKQgpKmUFGjBoEQU5IwgiIH0aFiRvJzAcKi4YD2ohAiZiNB5F")), classLoader);
            Class<?> apiPermissionCheckResult = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm4gRS9vNyg5PC5bJ2EwRSBsVx4qLD41KmwjNDVsHgowIBgfJWEFPD1iNwYbIwgYKW8zAiVgMig0KAcqCWIVGgNvAQI/")), classLoader);
            final Object[] enumConstants = apiPermissionCheckResult.getEnumConstants();
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm4gRS9vNyg5PC06J2EwQSxlJywiKQgpKmwjNCZsJx42JANfBW4KJDVlJyQZJgcuLGUFNCZmHgY5Lwg2MW8FMExsNwYzLxYYL2EaTVo=")), classLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBg+KWcFNARgDgYpIy0cDW8VSFo=")), apiPermissionInfo, String.class, String.class, new XC_MethodHook(){

                @Override
                public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    super.afterHookedMethod(methodHookParam);
                }

                @Override
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    methodHookParam.setResult(enumConstants[0]);
                }
            });
        }
        catch (Throwable throwable) {
            Log.e((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRgYCGg2MDdgHgE8KRdfDWwJTStuETAgKQcqI2AgRD15EVRF")) + throwable.toString()));
            HVLog.printThrowable(throwable);
        }
    }

    private static void hookInterface(ClassLoader classLoader, Object pthis) {
        try {
            Class<?> previewCallback = classLoader.loadClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1k0LwguPmUFQQRrDRoALRgIJ2EwPy99ESguIxg2J28hLDNqAQI1OwcuIg==")));
            Object obj_proxy = Proxy.newProxyInstance(classLoader, new Class[]{previewCallback}, new InvocationHandler(){

                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MwQHDXpSHSNOClw3OgNWD38nTSNrDiwZLD4pJA==")) + method.getName());
                    return null;
                }
            });
            HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwguLGUFGixLV1w3OgNWD38nPyN1DQEePF8HL04OQCh8ClAcOSolL3UJHQF6VgE8CANaJHwOTAN/IyM8MwQHDXsFSFo=")));
            XposedHelpers.callMethod(pthis, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LghSVg==")), obj_proxy);
        }
        catch (NoClassDefFoundError fe) {
            HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4tOA==")) + fe.getMessage());
        }
        catch (Exception e) {
            HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQQ6Vg==")) + e.getMessage());
        }
    }
}

