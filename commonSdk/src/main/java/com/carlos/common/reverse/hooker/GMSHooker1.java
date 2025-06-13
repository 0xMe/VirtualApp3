/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.carlos.common.reverse.hooker;

import android.content.Context;
import android.util.Log;
import com.kook.librelease.StringFog;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookReflectClass;
import com.swift.sandhook.annotation.MethodParams;

@HookReflectClass(value="com.google.android.gms.common.GooglePlayServicesUtilLight")
public class GMSHooker1 {
    @HookMethod(value="isGooglePlayServicesAvailable")
    @MethodParams(value={Context.class})
    public static boolean isGooglePlayServicesAvailable(Context v1) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2WmozGi1gHjACKhciIWIFGgRvNx4qLhc2HWYwPCxsHiQrKT4fDQ==")));
        return true;
    }

    @HookMethod(value="isGooglePlayServicesAvailable")
    @MethodParams(value={Context.class, int.class})
    public static boolean isGooglePlayServicesAvailable2(Context v1, int v2) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2WmozGi1gHjACKhciIWIFGgRvNx4qLhc2HWYwPCxsHiQrKT4fCA==")));
        return true;
    }

    @HookMethod(value="isPlayServicesPossiblyUpdating")
    @MethodParams(value={Context.class, int.class})
    public static boolean isGooglePlayServicesUid(Context v1, int v2) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2AmoFJD9pJDAqLD0cP2kgAkxlJDA6IxgMKGcLLDNuHiQ9Ki4qIQ==")));
        return true;
    }

    @HookMethod(value="isPlayStorePossiblyUpdating")
    @MethodParams(value={Context.class, int.class})
    public static boolean isPlayStorePossiblyUpdating(Context v1, int v2) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+LGgaLAY=")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2AmoFJD9pJwo1Iz0MDG8KAgNqAQodLxUuDmIaPD9vDh4g")));
        return true;
    }
}

