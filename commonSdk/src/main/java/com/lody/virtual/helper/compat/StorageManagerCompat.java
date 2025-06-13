/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.storage.StorageManager
 */
package com.lody.virtual.helper.compat;

import android.content.Context;
import android.os.storage.StorageManager;
import com.lody.virtual.StringFog;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StorageManagerCompat {
    private StorageManagerCompat() {
    }

    public static String[] getAllPoints(Context context) {
        StorageManager manager = (StorageManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qD28jJC1iAVRF")));
        String[] points = null;
        try {
            Method method = manager.getClass().getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQjGiRmDl0/OxciLmwwAlo=")), new Class[0]);
            points = (String[])method.invoke(manager, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }

    public static boolean isMounted(Context context, String point) {
        if (point == null) {
            return false;
        }
        StorageManager manager = (StorageManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qD28jJC1iAVRF")));
        try {
            Method method = manager.getClass().getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQjGiRmDl0/Oy42OWUzGlo=")), String.class);
            String state = (String)method.invoke(manager, point);
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgAI2ogMCtiEVRF")).equals(state);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getMountedPoints(Context context) {
        StorageManager manager = (StorageManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qD28jJC1iAVRF")));
        ArrayList<String> mountedPoints = new ArrayList<String>();
        try {
            Method getVolumePaths = manager.getClass().getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQjGiRmDl0/OxciLmwwAlo=")), new Class[0]);
            String[] points = (String[])getVolumePaths.invoke(manager, new Object[0]);
            if (points != null && points.length > 0) {
                Method getVolumeState = manager.getClass().getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQjGiRmDl0/Oy42OWUzGlo=")), String.class);
                for (String point : points) {
                    String state = (String)getVolumeState.invoke(manager, point);
                    if (!StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgAI2ogMCtiEVRF")).equals(state)) continue;
                    mountedPoints.add(point);
                }
                return mountedPoints;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

