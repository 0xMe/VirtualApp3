/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.utils;

import com.kook.librelease.StringFog;
import java.io.FileInputStream;

public final class SysUtils {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0YKWQaMC9gEShF"));

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            int b;
            String fn = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My06KmozLyVhJDAoKDlfP28jBiRqARog"));
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte)b;
            }
            if (len > 0) {
                String s;
                String string2 = s = new String(buffer, 0, len, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQUqW3pTRVo=")));
                return string2;
            }
        }
        catch (Throwable throwable) {
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        return null;
    }
}

