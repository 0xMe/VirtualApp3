/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.os.Build$VERSION
 *  android.view.Window
 *  android.view.WindowManager$LayoutParams
 */
package com.carlos.common.ui.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import com.carlos.common.ui.utils.SystemBarTintManager;
import com.kook.librelease.StringFog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NewStatusBarUtil {
    @TargetApi(value=19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.clearFlags(0xC000000);
            window.getDecorView().setSystemUiVisibility(1792);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
            window.setNavigationBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window window = activity.getWindow();
            window.setFlags(0x4000000, 0x4000000);
        }
    }

    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= 19) {
            NewStatusBarUtil.transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            if (NewStatusBarUtil.MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (NewStatusBarUtil.FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            }
        }
        return result;
    }

    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            NewStatusBarUtil.MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            NewStatusBarUtil.FlymeSetStatusBarLightMode(activity.getWindow(), true);
        }
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class<?> clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kuKQcMI38bPC9vAR4UIxgcIGAjJBJpDh4qLwgACHsbHjNpEQYaIBYiKGUwOANqAVRF")));
                Field field = layoutParams.getField(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JQVfBmchJB9qMlERICxfAX0xQVFnDDAMJSw+U2wmFg59MgIOISwuGmMFSFo=")));
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uLGAaRQZhNCAUKhciM2oFSFo=")), Integer.TYPE, Integer.TYPE);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return result;
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYuXGEmNB9qMlERICxfW2YmRQthIjAVJRUqXGkhAg9hDygOIiwYA30zSFo=")));
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwguCWkgNAhgHiA9Iy5SVg==")));
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value = dark ? (value |= bit) : (value &= ~bit);
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return result;
    }
}

