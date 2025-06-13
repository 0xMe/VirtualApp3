/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.lody.virtual.helper;

import android.os.Bundle;
import android.os.Parcel;
import java.util.HashMap;
import java.util.Map;

public class ParcelHelper {
    public static void writeMeta(Parcel p, Bundle meta) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (meta != null) {
            for (String key : meta.keySet()) {
                map.put(key, meta.getString(key));
            }
        }
        p.writeMap(map);
    }

    public static Bundle readMeta(Parcel p) {
        Bundle meta = new Bundle();
        HashMap map = p.readHashMap(String.class.getClassLoader());
        for (Map.Entry entry : map.entrySet()) {
            meta.putString((String)entry.getKey(), (String)entry.getValue());
        }
        return meta;
    }
}

