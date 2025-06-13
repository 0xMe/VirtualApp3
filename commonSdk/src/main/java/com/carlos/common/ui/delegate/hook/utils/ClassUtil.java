/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.ui.delegate.hook.utils;

import com.carlos.common.ui.delegate.hook.utils.LogUtil;
import com.kook.librelease.StringFog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtil {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4EP28wLFBmHgYo"));

    public static void printMethodsInClass(String printTag, Class mClazz) {
        for (Method method : mClazz.getDeclaredMethods()) {
            String typeName = method.getReturnType().getSimpleName();
            String canonicalName = method.getReturnType().getCanonicalName();
            String methodName = method.getName();
            Class<?>[] methodParameterTypes = method.getParameterTypes();
            String types = "";
            for (Class<?> clazz : methodParameterTypes) {
                types = types + clazz.getName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MxhSVg=="));
            }
            LogUtil.d(TAG, printTag + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgIM2wFRSViHFk7KgcLJQ==")) + methodName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BSEBQGwKAgJiDFk7KgcLJQ==")) + typeName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mxg2P2ojGiZjDig7KhUYOW8jBTM=")) + canonicalName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BSEBQEMWB1FYEwNBLBgcKmkkPyA=")) + types + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PAhSVg==")));
            method.setAccessible(true);
        }
    }

    private static void printFieldsInClass(String printTag, Class mClazz) {
        for (Field field : mClazz.getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                LogUtil.d(TAG, printTag + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgiCWgVHixoNCA3KARXVg==")) + fieldName);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void printFieldsInClassAndObject(String printTag, Class mClazz, Object object) {
        for (Field field : mClazz.getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                LogUtil.d(TAG, printTag + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgiCWgVHixoNCA3KARXVg==")) + fieldName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M1srElQNJVUVJ1RF")) + field.get(object));
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

