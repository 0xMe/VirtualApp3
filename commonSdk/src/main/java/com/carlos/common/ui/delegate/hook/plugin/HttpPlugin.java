/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.view.View
 */
package com.carlos.common.ui.delegate.hook.plugin;

import android.app.Application;
import android.view.View;
import com.carlos.common.ui.delegate.hook.utils.ClassUtil;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.net.URL;

public class HttpPlugin {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqIRdfDWwLFgZvHjxF"));
    ClassLoader mClassLoader;
    String mVersionName;
    boolean isHooking = false;

    public void hook(String packageName, String processName, Application application) {
        this.mClassLoader = application.getClassLoader();
        if (this.isHooking) {
            return;
        }
        this.isHooking = true;
        this.hookHttp();
    }

    private void hookHttp() {
        Class<?> MttRequestBaseClass;
        HVLog.d(TAG, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqAhxYHEcvHwtiEQYcIzxfCmYVOFo=")));
        try {
            Class<?> UrlParamsClass = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8gBgZ1Nwo5LD0mD2IFMylqJBodLz4uMXU2NCZqDDwoJz0mJGUjSFo=")), this.mClassLoader);
            XposedBridge.hookAllConstructors(UrlParamsClass, new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object object = param.thisObject;
                    ClassUtil.printFieldsInClassAndObject(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQcMDmcFJAR9Dl0p")), object.getClass(), object);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class<?> httpUrlConnection = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LD4+LmtSBiZiAQ02IRg2Lmo2Gl99HzAcLC4cJ30jFixsJB5F")), this.mClassLoader);
            XposedBridge.hookAllConstructors(httpUrlConnection, new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) {
                    if (param.args.length != 1 || param.args[0].getClass() != URL.class) {
                        return;
                    }
                    URL url = (URL)param.args[0];
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqIRdfDWwLFgZvHjxF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBcqLG8INF9oHCg1Kj0YPW4KBi9lJx0xPQhSVg==")) + param.args[0] + "");
                    if (url.toString().contains(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OgM9KXokDT4=")))) {
                        StringBuilder TraceString = new StringBuilder("");
                        TraceString.append(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("P14DJH4JHSNOClw3OgNWD38nPyN1DQEePF8HL04OQCh8ClAcOSolL3UJHQF6Vx0pMjpeD3lTP1E="))).append(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Djo5JH4OGTROClw3OgNWD38nPyN1DQEePF8HL04OQCh8ClAcOSolL3UJHQF6VgE8MjpeD3g3Alo="))).append("\n");
                        HVLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqIRdfDWwLFgZvHjxF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Bxw3W0YvLQpYXhM7Agk/DUUWJVc=")) + TraceString.toString());
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MttRequestBaseClass = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm4FNCNlAQYbPC5fCmYVOylkATA9JBgADW8FNCVsDwooJy02Vg==")), this.mClassLoader);
            XposedBridge.hookAllMethods(MttRequestBaseClass, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggqPH0FNDdiHjAqIy5SVg==")), new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object thisObject = param.thisObject;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MttRequestBaseClass = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm4FNCNlAQYbPC5fCmYVOylkATA9JBgADW8FNCVsDwooJy02Vg==")), this.mClassLoader);
            XposedBridge.hookAllMethods(MttRequestBaseClass, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggqPH0FNDdiHjAq")), new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object thisObject = param.thisObject;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class<?> RequestClass = XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogLAFmDiAqKAgMKn8VNCFqHiw/KQQcU2IFPDBuASw9")), this.mClassLoader);
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogLAFmDiAqKAgMKn8VNCFqHiw/KQQcA2MmBj9qESACKT42J2UwMFo=")), this.mClassLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4uLWMzJCRgEVRF")), RequestClass, new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object param0 = param.args[0];
                    ClassUtil.printFieldsInClassAndObject(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oy5bXWwKMAJlJFEzKAcYLn8VMCtvJTAsLAgDL2EaPDVpDlA5")), param0.getClass(), param0);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8gBgZ1MiAaLC4qKWYmNCpsASAeKRgAKm8bNCxsASg5Jy0AJmw2BgNqN1RF")), this.mClassLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LD5SVg==")), new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqIRdfDWwLFgZvHjxF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IS4YCGgFGj1lJB43IxdfDmkjMAZjDlk/LhgcD2MKAilnDlE5ORg5LHoJIjICXiI/RBlEIxwEJFo=")));
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8gBgZ1Nwo5LD0mD2IFMylpNygqORccKWUjHjZrHg05JRhSVg==")), this.mClassLoader, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4cE2oFAiljJ1RF")), View.class, new XC_MethodHook(){

                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IgU+HG8jGj1hJDAqIRdfDWwLFgZvHjxF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRgAD2oFFjdhMFk0Oj1fDmYFOC9oJ1wZM184IhwWJitXEF8tW0QEVg==")));
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

