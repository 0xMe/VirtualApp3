/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 */
package com.lody.virtual.helper.utils;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.Reflect;
import java.util.Set;

public class VLog {
    public static boolean OPEN_LOG;
    public static final String TAG_DEFAULT;

    public static void i(String tag, String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.i((String)tag, (String)String.format(msg, format));
        }
    }

    public static void d(String tag, String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.d((String)tag, (String)String.format(msg, format));
        }
    }

    public static void d(String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.d((String)TAG_DEFAULT, (String)String.format(msg, format));
        }
    }

    public static void logbug(String tag, String msg) {
        VLog.d(tag, msg, new Object[0]);
    }

    public static void w(String tag, String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.w((String)tag, (String)String.format(msg, format));
        }
    }

    public static void e(String tag, String msg) {
        if (OPEN_LOG) {
            Log.e((String)tag, (String)msg);
        }
    }

    public static void e(String tag, String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.e((String)tag, (String)String.format(msg, format));
        }
    }

    public static void v(String tag, String msg) {
        if (OPEN_LOG) {
            Log.v((String)tag, (String)msg);
        }
    }

    public static void v(String tag, String msg, Object ... format) {
        if (OPEN_LOG) {
            Log.v((String)tag, (String)String.format(msg, format));
        }
    }

    public static String toString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (Reflect.on(bundle).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU6P28jLCtgHlE/KBU2OWUzQVo="))) != null) {
            Set keys = bundle.keySet();
            StringBuilder stringBuilder = new StringBuilder(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jj0uCGgFHitvJ1RF")));
            if (keys != null) {
                for (String key : keys) {
                    stringBuilder.append(key);
                    stringBuilder.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PwhSVg==")));
                    stringBuilder.append(bundle.get(key));
                    stringBuilder.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MxhSVg==")));
                }
            }
            stringBuilder.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JwhSVg==")));
            return stringBuilder.toString();
        }
        return bundle.toString();
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString((Throwable)tr);
    }

    public static void printStackTrace(String tag) {
        Log.e((String)tag, (String)VLog.getStackTraceString(new Exception()));
    }

    public static void e(String tag, Throwable e) {
        Log.e((String)tag, (String)VLog.getStackTraceString(e));
    }

    public static void printException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        Throwable cause = e.getCause();
        VLog.d(TAG_DEFAULT, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jj0uCWoFMyZuMjAAOyscRGdSMF5jH10MOxYcXUtSQCM=")) + Build.VERSION.SDK_INT, new Object[0]);
        if (cause != null) {
            String stackTraceString = Log.getStackTraceString((Throwable)cause);
            VLog.e(TAG_DEFAULT, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("BxsBHEZbRjBLHig7LAgqPXwwTQRqARo/JhdfJWIFOD9vDlkdOik6Vg==")) + stackTraceString);
        } else {
            VLog.e(TAG_DEFAULT, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("BxsBHEZbRjB3N1RF")) + e.toString() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OHsJICl9ATApKAMmMWoJTSZvAQIdPQMAVg==")) + (cause == null));
            for (int i = 0; i < stackTrace.length; ++i) {
                VLog.e(TAG_DEFAULT, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQdfOWgaIAZjDh42PxcLIA==")) + stackTrace[i].toString());
            }
        }
    }

    static {
        TAG_DEFAULT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITw9DQ=="));
        OPEN_LOG = true;
    }
}

