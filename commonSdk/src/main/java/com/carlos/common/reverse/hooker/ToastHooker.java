/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.widget.Toast
 */
package com.carlos.common.reverse.hooker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.kook.librelease.StringFog;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import com.swift.sandhook.annotation.MethodParams;
import java.lang.reflect.Method;
import java.util.Map;

@HookClass(value=Toast.class)
public class ToastHooker {
    @HookMethodBackup(value="show")
    static Method method_m1;
    @HookMethodBackup(value="makeText")
    @MethodParams(value={Context.class, CharSequence.class, int.class})
    static Method method_m2;

    public static void e() {
        Thread.currentThread();
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        if (map != null && map.size() != 0) {
            for (Thread eachThread : map.keySet()) {
                StackTraceElement[] array = map.get(eachThread);
                System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MwQHDXpSHSNYAhMLAlcdCkcWLTVBK1pNAAlAGlgXDz1EA14bAQsrO0EHIQM=")));
                System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsjIlQ7OSAcLAsCXx83Mx87MTQbN1RF")) + eachThread.getName());
                System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhU2LGsVLCFuESw7Ly0MWm8zGiNrARo/JzsHKmAaLCluJzAhPS5SVg==")) + array.length);
                System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsjIlQ7OSAcLD1NXVo3XR5WGxIVCRs3XC5SVg==")) + (Object)((Object)eachThread.getState()));
                if (array.length != 0) {
                    System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PltcJhg7MRcYNSggLwcqCX0wRTdoJyhLLAguL2IKRT9jJUIvRCFEIVQtMj8VXyIzXxosIx0sOjAbKyo6EUBXVg==")));
                    for (int i = 0; i < array.length; ++i) {
                        StackTraceElement eachElement = array[i];
                        System.out.println(" " + eachElement.getClassName() + " " + eachElement.getMethodName() + " " + eachElement.getFileName() + " " + eachElement.getLineNumber());
                    }
                    continue;
                }
                System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pls/KhxWJR4cDyggLwcqCX0wRTdoJyhLLAguL2IKRT9jJUItWhxZIhURBANUEwQ0EyEgIxxaHD8dXyoyWURbVg==")) + eachThread.getName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcDUYEWhZhJwo7Ly0EAGoVQSlrDygdLhgIJ2AzFlNgCh4bLy4qIW8VRDd+EVRF")) + array.length);
            }
        }
    }

    @HookMethod(value="makeText")
    @MethodParams(value={Context.class, CharSequence.class, int.class})
    public static Toast makeText(Context context, CharSequence cs, int d) throws Throwable {
        Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRgAP28wMApgJB4xKAguVg==")), (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+MWgYMCtnEQ0i")) + cs.toString() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phc6P2szQTdiJDAMLwdXPXgVSFo=")) + context.getPackageName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRhfKmgVJCx3N1RF")) + Thread.currentThread().getClass().getName()));
        ToastHooker.e();
        return (Toast)SandHook.callOriginByBackup(method_m2, null, context, cs, d);
    }

    @HookMethod(value="show")
    public static void show(Toast thiz) throws Throwable {
        try {
            Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRgAP28wMApgJB4xKAguVg==")), (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phc2CmowPFo=")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        SandHook.callOriginByBackup(method_m1, thiz, new Object[0]);
    }
}

