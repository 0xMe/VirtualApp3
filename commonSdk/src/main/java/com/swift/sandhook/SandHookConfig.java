/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 */
package com.swift.sandhook;

import android.os.Build;

public class SandHookConfig {
    public static volatile int SDK_INT = SandHookConfig.getSdkInt();
    public static volatile boolean DEBUG = true;
    public static volatile boolean compiler = SDK_INT < 29;
    public static volatile ClassLoader initClassLoader;
    public static volatile int curUser;
    public static volatile boolean delayHook;

    public static int getSdkInt() {
        try {
            if (Build.VERSION.PREVIEW_SDK_INT > 0) {
                return Build.VERSION.SDK_INT + 1;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return Build.VERSION.SDK_INT;
    }

    static {
        curUser = 0;
        delayHook = true;
    }
}

