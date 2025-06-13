/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.lody.virtual.helper.compat;

import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.Reflect;

public class SystemPropertiesCompat {
    public static String get(String key, String def) {
        try {
            return (String)Reflect.on(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k1IykYAWggAgZrAQ4RKS4ADmIFMD9vDgo8"))).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLA==")), key, def).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }

    public static String get(String key) {
        try {
            return (String)Reflect.on(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k1IykYAWggAgZrAQ4RKS4ADmIFMD9vDgo8"))).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLA==")), key).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isExist(String key) {
        return !TextUtils.isEmpty((CharSequence)SystemPropertiesCompat.get(key));
    }

    public static int getInt(String key, int def) {
        try {
            return (Integer)Reflect.on(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k1IykYAWggAgZrAQ4RKS4ADmIFMD9vDgo8"))).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgY=")), key, def).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }
}

