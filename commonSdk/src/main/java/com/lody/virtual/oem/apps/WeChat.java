/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package com.lody.virtual.oem.apps;

import android.app.Application;
import com.lody.virtual.StringFog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class WeChat {
    private static final String PKG_MM = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPFo="));
    private static HashSet<String> sBinderMapClsNameSet;

    private static void cleanMap(String clsName, Application application) {
        try {
            for (Field field : Class.forName(clsName, true, application.getClassLoader()).getDeclaredFields()) {
                HashMap map;
                field.setAccessible(true);
                if (!HashMap.class.isAssignableFrom(field.getType()) || (map = (HashMap)field.get(null)).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF"))) == null || map.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguLmUVLCtsJAYwKAcYLmwjHi9rDgo6"))) == null || map.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc6CmozBithJzA6KQcYPG8FSFo="))) == null) continue;
                map.clear();
                return;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void disableBinderHook(String pkg, Application application) {
        if (PKG_MM.equals(pkg)) {
            Iterator<String> it = sBinderMapClsNameSet.iterator();
            while (it.hasNext()) {
                WeChat.cleanMap(it.next(), application);
            }
        }
    }

    static {
        HashSet<String> hashSet = new HashSet<String>();
        sBinderMapClsNameSet = hashSet;
        hashSet.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZsJygbKT4YCmMFICB8NF1F")));
        sBinderMapClsNameSet.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogMCtgNCg/Kj41Dm8jPyZsJygbKT4YCmMFICB8NFFF")));
    }
}

