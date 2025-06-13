/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  dalvik.system.DexFile
 */
package de.robv.android.xposed;

import android.content.res.Resources;
import dalvik.system.DexFile;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import external.org.apache.commons.lang3.ClassUtils;
import external.org.apache.commons.lang3.reflect.MemberUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipFile;

public final class XposedHelpers {
    private static final HashMap<String, Field> fieldCache = new HashMap();
    private static final HashMap<String, Method> methodCache = new HashMap();
    private static final HashMap<String, Constructor<?>> constructorCache = new HashMap();
    private static final WeakHashMap<Object, HashMap<String, Object>> additionalFields = new WeakHashMap();
    private static final HashMap<String, ThreadLocal<AtomicInteger>> sMethodDepth = new HashMap();

    private XposedHelpers() {
    }

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = XposedBridge.BOOTCLASSLOADER;
        }
        try {
            return ClassUtils.getClass(classLoader, className, false);
        }
        catch (ClassNotFoundException e) {
            throw new ClassNotFoundError(e);
        }
    }

    public static Class<?> findClassIfExists(String className, ClassLoader classLoader) {
        try {
            return XposedHelpers.findClass(className, classLoader);
        }
        catch (ClassNotFoundError e) {
            return null;
        }
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        String fullFieldName = clazz.getName() + '#' + fieldName;
        if (fieldCache.containsKey(fullFieldName)) {
            Field field = fieldCache.get(fullFieldName);
            if (field == null) {
                throw new NoSuchFieldError(fullFieldName);
            }
            return field;
        }
        try {
            Field field = XposedHelpers.findFieldRecursiveImpl(clazz, fieldName);
            field.setAccessible(true);
            fieldCache.put(fullFieldName, field);
            return field;
        }
        catch (NoSuchFieldException e) {
            fieldCache.put(fullFieldName, null);
            throw new NoSuchFieldError(fullFieldName);
        }
    }

    public static Field findFieldIfExists(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName);
        }
        catch (NoSuchFieldError e) {
            return null;
        }
    }

    private static Field findFieldRecursiveImpl(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            while ((clazz = clazz.getSuperclass()) != null && !clazz.equals(Object.class)) {
                try {
                    return clazz.getDeclaredField(fieldName);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                }
            }
            throw e;
        }
    }

    public static Field findFirstFieldByExactType(Class<?> clazz, Class<?> type) {
        Class<?> clz = clazz;
        do {
            for (Field field : clz.getDeclaredFields()) {
                if (field.getType() != type) continue;
                field.setAccessible(true);
                return field;
            }
        } while ((clz = clz.getSuperclass()) != null);
        throw new NoSuchFieldError("Field of type " + type.getName() + " in class " + clazz.getName());
    }

    public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object ... parameterTypesAndCallback) {
        if (parameterTypesAndCallback.length == 0 || !(parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof XC_MethodHook)) {
            throw new IllegalArgumentException("no callback defined");
        }
        XC_MethodHook callback = (XC_MethodHook)parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Method m = XposedHelpers.findMethodExact(clazz, methodName, XposedHelpers.getParameterClasses(clazz.getClassLoader(), parameterTypesAndCallback));
        return XposedBridge.hookMethod(m, callback);
    }

    public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object ... parameterTypesAndCallback) {
        return XposedHelpers.findAndHookMethod(XposedHelpers.findClass(className, classLoader), methodName, parameterTypesAndCallback);
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Object ... parameterTypes) {
        return XposedHelpers.findMethodExact(clazz, methodName, XposedHelpers.getParameterClasses(clazz.getClassLoader(), parameterTypes));
    }

    public static Method findMethodExactIfExists(Class<?> clazz, String methodName, Object ... parameterTypes) {
        try {
            return XposedHelpers.findMethodExact(clazz, methodName, parameterTypes);
        }
        catch (ClassNotFoundError | NoSuchMethodError e) {
            return null;
        }
    }

    public static Method findMethodExact(String className, ClassLoader classLoader, String methodName, Object ... parameterTypes) {
        return XposedHelpers.findMethodExact(XposedHelpers.findClass(className, classLoader), methodName, XposedHelpers.getParameterClasses(classLoader, parameterTypes));
    }

    public static Method findMethodExactIfExists(String className, ClassLoader classLoader, String methodName, Object ... parameterTypes) {
        try {
            return XposedHelpers.findMethodExact(className, classLoader, methodName, parameterTypes);
        }
        catch (ClassNotFoundError | NoSuchMethodError e) {
            return null;
        }
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Class<?> ... parameterTypes) {
        String fullMethodName = clazz.getName() + '#' + methodName + XposedHelpers.getParametersString(parameterTypes) + "#exact";
        if (methodCache.containsKey(fullMethodName)) {
            Method method = methodCache.get(fullMethodName);
            if (method == null) {
                throw new NoSuchMethodError(fullMethodName);
            }
            return method;
        }
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            methodCache.put(fullMethodName, method);
            return method;
        }
        catch (NoSuchMethodException e) {
            methodCache.put(fullMethodName, null);
            throw new NoSuchMethodError(fullMethodName);
        }
    }

    public static Method[] findMethodsByExactParameters(Class<?> clazz, Class<?> returnType, Class<?> ... parameterTypes) {
        LinkedList<Method> result = new LinkedList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            Class<?>[] methodParameterTypes;
            if (returnType != null && returnType != method.getReturnType() || parameterTypes.length != (methodParameterTypes = method.getParameterTypes()).length) continue;
            boolean match = true;
            for (int i = 0; i < parameterTypes.length; ++i) {
                if (parameterTypes[i] == methodParameterTypes[i]) continue;
                match = false;
                break;
            }
            if (!match) continue;
            method.setAccessible(true);
            result.add(method);
        }
        return result.toArray(new Method[result.size()]);
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Class<?> ... parameterTypes) {
        String fullMethodName = clazz.getName() + '#' + methodName + XposedHelpers.getParametersString(parameterTypes) + "#bestmatch";
        if (methodCache.containsKey(fullMethodName)) {
            Method method = methodCache.get(fullMethodName);
            if (method == null) {
                throw new NoSuchMethodError(fullMethodName);
            }
            return method;
        }
        try {
            Method method = XposedHelpers.findMethodExact(clazz, methodName, parameterTypes);
            methodCache.put(fullMethodName, method);
            return method;
        }
        catch (NoSuchMethodError method) {
            AccessibleObject bestMatch = null;
            Class<?> clz = clazz;
            boolean considerPrivateMethods = true;
            do {
                for (Method method2 : clz.getDeclaredMethods()) {
                    if (!considerPrivateMethods && Modifier.isPrivate(method2.getModifiers()) || !method2.getName().equals(methodName) || !ClassUtils.isAssignable(parameterTypes, method2.getParameterTypes(), true) || bestMatch != null && MemberUtils.compareParameterTypes(method2.getParameterTypes(), ((Method)bestMatch).getParameterTypes(), parameterTypes) >= 0) continue;
                    bestMatch = method2;
                }
                considerPrivateMethods = false;
            } while ((clz = clz.getSuperclass()) != null);
            if (bestMatch != null) {
                bestMatch.setAccessible(true);
                methodCache.put(fullMethodName, (Method)bestMatch);
                return bestMatch;
            }
            NoSuchMethodError e = new NoSuchMethodError(fullMethodName);
            methodCache.put(fullMethodName, null);
            throw e;
        }
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Object ... args) {
        return XposedHelpers.findMethodBestMatch(clazz, methodName, XposedHelpers.getParameterTypes(args));
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Class<?>[] argsClasses = null;
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (parameterTypes[i] != null) continue;
            if (argsClasses == null) {
                argsClasses = XposedHelpers.getParameterTypes(args);
            }
            parameterTypes[i] = argsClasses[i];
        }
        return XposedHelpers.findMethodBestMatch(clazz, methodName, parameterTypes);
    }

    public static Class<?>[] getParameterTypes(Object ... args) {
        Class[] clazzes = new Class[args.length];
        for (int i = 0; i < args.length; ++i) {
            clazzes[i] = args[i] != null ? args[i].getClass() : null;
        }
        return clazzes;
    }

    private static Class<?>[] getParameterClasses(ClassLoader classLoader, Object[] parameterTypesAndCallback) {
        Class[] parameterClasses = null;
        for (int i = parameterTypesAndCallback.length - 1; i >= 0; --i) {
            Object type = parameterTypesAndCallback[i];
            if (type == null) {
                throw new ClassNotFoundError("parameter type must not be null", null);
            }
            if (type instanceof XC_MethodHook) continue;
            if (parameterClasses == null) {
                parameterClasses = new Class[i + 1];
            }
            if (type instanceof Class) {
                parameterClasses[i] = (Class)type;
                continue;
            }
            if (type instanceof String) {
                parameterClasses[i] = XposedHelpers.findClass((String)type, classLoader);
                continue;
            }
            throw new ClassNotFoundError("parameter type must either be specified as Class or String", null);
        }
        if (parameterClasses == null) {
            parameterClasses = new Class[]{};
        }
        return parameterClasses;
    }

    public static Class<?>[] getClassesAsArray(Class<?> ... clazzes) {
        return clazzes;
    }

    private static String getParametersString(Class<?> ... clazzes) {
        StringBuilder sb = new StringBuilder("(");
        boolean first = true;
        for (Class<?> clazz : clazzes) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            if (clazz != null) {
                sb.append(clazz.getCanonicalName());
                continue;
            }
            sb.append("null");
        }
        sb.append(")");
        return sb.toString();
    }

    public static Constructor<?> findConstructorExact(Class<?> clazz, Object ... parameterTypes) {
        return XposedHelpers.findConstructorExact(clazz, XposedHelpers.getParameterClasses(clazz.getClassLoader(), parameterTypes));
    }

    public static Constructor<?> findConstructorExactIfExists(Class<?> clazz, Object ... parameterTypes) {
        try {
            return XposedHelpers.findConstructorExact(clazz, parameterTypes);
        }
        catch (ClassNotFoundError | NoSuchMethodError e) {
            return null;
        }
    }

    public static Constructor<?> findConstructorExact(String className, ClassLoader classLoader, Object ... parameterTypes) {
        return XposedHelpers.findConstructorExact(XposedHelpers.findClass(className, classLoader), XposedHelpers.getParameterClasses(classLoader, parameterTypes));
    }

    public static Constructor<?> findConstructorExactIfExists(String className, ClassLoader classLoader, Object ... parameterTypes) {
        try {
            return XposedHelpers.findConstructorExact(className, classLoader, parameterTypes);
        }
        catch (ClassNotFoundError | NoSuchMethodError e) {
            return null;
        }
    }

    public static Constructor<?> findConstructorExact(Class<?> clazz, Class<?> ... parameterTypes) {
        String fullConstructorName = clazz.getName() + XposedHelpers.getParametersString(parameterTypes) + "#exact";
        if (constructorCache.containsKey(fullConstructorName)) {
            Constructor<?> constructor = constructorCache.get(fullConstructorName);
            if (constructor == null) {
                throw new NoSuchMethodError(fullConstructorName);
            }
            return constructor;
        }
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            constructorCache.put(fullConstructorName, constructor);
            return constructor;
        }
        catch (NoSuchMethodException e) {
            constructorCache.put(fullConstructorName, null);
            throw new NoSuchMethodError(fullConstructorName);
        }
    }

    public static XC_MethodHook.Unhook findAndHookConstructor(Class<?> clazz, Object ... parameterTypesAndCallback) {
        if (parameterTypesAndCallback.length == 0 || !(parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof XC_MethodHook)) {
            throw new IllegalArgumentException("no callback defined");
        }
        XC_MethodHook callback = (XC_MethodHook)parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Constructor<?> m = XposedHelpers.findConstructorExact(clazz, XposedHelpers.getParameterClasses(clazz.getClassLoader(), parameterTypesAndCallback));
        return XposedBridge.hookMethod(m, callback);
    }

    public static XC_MethodHook.Unhook findAndHookConstructor(String className, ClassLoader classLoader, Object ... parameterTypesAndCallback) {
        return XposedHelpers.findAndHookConstructor(XposedHelpers.findClass(className, classLoader), parameterTypesAndCallback);
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Class<?> ... parameterTypes) {
        String fullConstructorName = clazz.getName() + XposedHelpers.getParametersString(parameterTypes) + "#bestmatch";
        if (constructorCache.containsKey(fullConstructorName)) {
            Constructor<?> constructor = constructorCache.get(fullConstructorName);
            if (constructor == null) {
                throw new NoSuchMethodError(fullConstructorName);
            }
            return constructor;
        }
        try {
            Constructor<?> constructor = XposedHelpers.findConstructorExact(clazz, parameterTypes);
            constructorCache.put(fullConstructorName, constructor);
            return constructor;
        }
        catch (NoSuchMethodError constructor) {
            Constructor<?>[] constructors;
            Constructor<?> bestMatch = null;
            for (Constructor<?> constructor2 : constructors = clazz.getDeclaredConstructors()) {
                if (!ClassUtils.isAssignable(parameterTypes, constructor2.getParameterTypes(), true) || bestMatch != null && MemberUtils.compareParameterTypes(constructor2.getParameterTypes(), bestMatch.getParameterTypes(), parameterTypes) >= 0) continue;
                bestMatch = constructor2;
            }
            if (bestMatch != null) {
                bestMatch.setAccessible(true);
                constructorCache.put(fullConstructorName, bestMatch);
                return bestMatch;
            }
            NoSuchMethodError e = new NoSuchMethodError(fullConstructorName);
            constructorCache.put(fullConstructorName, null);
            throw e;
        }
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Object ... args) {
        return XposedHelpers.findConstructorBestMatch(clazz, XposedHelpers.getParameterTypes(args));
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Class<?>[] parameterTypes, Object[] args) {
        Class<?>[] argsClasses = null;
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (parameterTypes[i] != null) continue;
            if (argsClasses == null) {
                argsClasses = XposedHelpers.getParameterTypes(args);
            }
            parameterTypes[i] = argsClasses[i];
        }
        return XposedHelpers.findConstructorBestMatch(clazz, parameterTypes);
    }

    public static int getFirstParameterIndexByType(Member method, Class<?> type) {
        Class<?>[] classes = method instanceof Method ? ((Method)method).getParameterTypes() : ((Constructor)method).getParameterTypes();
        for (int i = 0; i < classes.length; ++i) {
            if (classes[i] != type) continue;
            return i;
        }
        throw new NoSuchFieldError("No parameter of type " + type + " found in " + method);
    }

    public static int getParameterIndexByType(Member method, Class<?> type) {
        Class<?>[] classes = method instanceof Method ? ((Method)method).getParameterTypes() : ((Constructor)method).getParameterTypes();
        int idx = -1;
        for (int i = 0; i < classes.length; ++i) {
            if (classes[i] != type) continue;
            if (idx == -1) {
                idx = i;
                continue;
            }
            throw new NoSuchFieldError("More than one parameter of type " + type + " found in " + method);
        }
        if (idx != -1) {
            return idx;
        }
        throw new NoSuchFieldError("No parameter of type " + type + " found in " + method);
    }

    public static void setObjectField(Object obj, String fieldName, Object value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).set(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setBooleanField(Object obj, String fieldName, boolean value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setBoolean(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setByteField(Object obj, String fieldName, byte value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setByte(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setCharField(Object obj, String fieldName, char value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setChar(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setDoubleField(Object obj, String fieldName, double value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setDouble(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setFloatField(Object obj, String fieldName, float value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setFloat(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setIntField(Object obj, String fieldName, int value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setInt(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setLongField(Object obj, String fieldName, long value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setLong(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setShortField(Object obj, String fieldName, short value) {
        try {
            XposedHelpers.findField(obj.getClass(), fieldName).setShort(obj, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static Object getObjectField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).get(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static Object getSurroundingThis(Object obj) {
        return XposedHelpers.getObjectField(obj, "this$0");
    }

    public static boolean getBooleanField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getBoolean(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static byte getByteField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getByte(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static char getCharField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getChar(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static double getDoubleField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getDouble(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static float getFloatField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getFloat(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static int getIntField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getInt(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static long getLongField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getLong(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static short getShortField(Object obj, String fieldName) {
        try {
            return XposedHelpers.findField(obj.getClass(), fieldName).getShort(obj);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticObjectField(Class<?> clazz, String fieldName, Object value) {
        try {
            XposedHelpers.findField(clazz, fieldName).set(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticBooleanField(Class<?> clazz, String fieldName, boolean value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setBoolean(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticByteField(Class<?> clazz, String fieldName, byte value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setByte(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticCharField(Class<?> clazz, String fieldName, char value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setChar(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticDoubleField(Class<?> clazz, String fieldName, double value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setDouble(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticFloatField(Class<?> clazz, String fieldName, float value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setFloat(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticIntField(Class<?> clazz, String fieldName, int value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setInt(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticLongField(Class<?> clazz, String fieldName, long value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setLong(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static void setStaticShortField(Class<?> clazz, String fieldName, short value) {
        try {
            XposedHelpers.findField(clazz, fieldName).setShort(null, value);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static Object getStaticObjectField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).get(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static boolean getStaticBooleanField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getBoolean(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static byte getStaticByteField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getByte(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static char getStaticCharField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getChar(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static double getStaticDoubleField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getDouble(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static float getStaticFloatField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getFloat(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static int getStaticIntField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getInt(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static long getStaticLongField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getLong(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static short getStaticShortField(Class<?> clazz, String fieldName) {
        try {
            return XposedHelpers.findField(clazz, fieldName).getShort(null);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static Object callMethod(Object obj, String methodName, Object ... args) {
        try {
            return XposedHelpers.findMethodBestMatch(obj.getClass(), methodName, args).invoke(obj, args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
    }

    public static Object callMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object ... args) {
        try {
            return XposedHelpers.findMethodBestMatch(obj.getClass(), methodName, parameterTypes, args).invoke(obj, args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
    }

    public static Object callStaticMethod(Class<?> clazz, String methodName, Object ... args) {
        try {
            return XposedHelpers.findMethodBestMatch(clazz, methodName, args).invoke(null, args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
    }

    public static Object callStaticMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object ... args) {
        try {
            return XposedHelpers.findMethodBestMatch(clazz, methodName, parameterTypes, args).invoke(null, args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
    }

    public static Object newInstance(Class<?> clazz, Object ... args) {
        try {
            return XposedHelpers.findConstructorBestMatch(clazz, args).newInstance(args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
        catch (InstantiationException e) {
            throw new InstantiationError(e.getMessage());
        }
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object ... args) {
        try {
            return XposedHelpers.findConstructorBestMatch(clazz, parameterTypes, args).newInstance(args);
        }
        catch (IllegalAccessException e) {
            XposedBridge.log(e);
            throw new IllegalAccessError(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new InvocationTargetError(e.getCause());
        }
        catch (InstantiationException e) {
            throw new InstantiationError(e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object setAdditionalInstanceField(Object obj, String key, Object value) {
        HashMap<String, Object> objectFields;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        }
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        AbstractMap abstractMap = additionalFields;
        synchronized (abstractMap) {
            objectFields = additionalFields.get(obj);
            if (objectFields == null) {
                objectFields = new HashMap();
                additionalFields.put(obj, objectFields);
            }
        }
        abstractMap = objectFields;
        synchronized (abstractMap) {
            return objectFields.put(key, value);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object getAdditionalInstanceField(Object obj, String key) {
        HashMap<String, Object> objectFields;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        }
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        AbstractMap abstractMap = additionalFields;
        synchronized (abstractMap) {
            objectFields = additionalFields.get(obj);
            if (objectFields == null) {
                return null;
            }
        }
        abstractMap = objectFields;
        synchronized (abstractMap) {
            return objectFields.get(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object removeAdditionalInstanceField(Object obj, String key) {
        HashMap<String, Object> objectFields;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        }
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        AbstractMap abstractMap = additionalFields;
        synchronized (abstractMap) {
            objectFields = additionalFields.get(obj);
            if (objectFields == null) {
                return null;
            }
        }
        abstractMap = objectFields;
        synchronized (abstractMap) {
            return objectFields.remove(key);
        }
    }

    public static Object setAdditionalStaticField(Object obj, String key, Object value) {
        return XposedHelpers.setAdditionalInstanceField(obj.getClass(), key, value);
    }

    public static Object getAdditionalStaticField(Object obj, String key) {
        return XposedHelpers.getAdditionalInstanceField(obj.getClass(), key);
    }

    public static Object removeAdditionalStaticField(Object obj, String key) {
        return XposedHelpers.removeAdditionalInstanceField(obj.getClass(), key);
    }

    public static Object setAdditionalStaticField(Class<?> clazz, String key, Object value) {
        return XposedHelpers.setAdditionalInstanceField(clazz, key, value);
    }

    public static Object getAdditionalStaticField(Class<?> clazz, String key) {
        return XposedHelpers.getAdditionalInstanceField(clazz, key);
    }

    public static Object removeAdditionalStaticField(Class<?> clazz, String key) {
        return XposedHelpers.removeAdditionalInstanceField(clazz, key);
    }

    public static byte[] assetAsByteArray(Resources res, String path) throws IOException {
        return XposedHelpers.inputStreamToByteArray(res.getAssets().open(path));
    }

    static byte[] inputStreamToByteArray(InputStream is) throws IOException {
        int read;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        while ((read = is.read(temp)) > 0) {
            buf.write(temp, 0, read);
        }
        is.close();
        return buf.toByteArray();
    }

    static void closeSilently(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    static void closeSilently(DexFile dexFile) {
        if (dexFile != null) {
            try {
                dexFile.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    static void closeSilently(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static String getMD5Sum(String file) throws IOException {
        try {
            int read;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream is = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            while ((read = ((InputStream)is).read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            ((InputStream)is).close();
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            return bigInt.toString(16);
        }
        catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static int incrementMethodDepth(String method) {
        return XposedHelpers.getMethodDepthCounter(method).get().incrementAndGet();
    }

    public static int decrementMethodDepth(String method) {
        return XposedHelpers.getMethodDepthCounter(method).get().decrementAndGet();
    }

    public static int getMethodDepth(String method) {
        return XposedHelpers.getMethodDepthCounter(method).get().get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ThreadLocal<AtomicInteger> getMethodDepthCounter(String method) {
        HashMap<String, ThreadLocal<AtomicInteger>> hashMap = sMethodDepth;
        synchronized (hashMap) {
            ThreadLocal<AtomicInteger> counter = sMethodDepth.get(method);
            if (counter == null) {
                counter = new ThreadLocal<AtomicInteger>(){

                    @Override
                    protected AtomicInteger initialValue() {
                        return new AtomicInteger();
                    }
                };
                sMethodDepth.put(method, counter);
            }
            return counter;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static boolean fileContains(File file, String str) throws IOException {
        boolean bl;
        BufferedReader in = null;
        try {
            String line;
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                if (!line.contains(str)) continue;
                bl = true;
            }
        }
        catch (Throwable throwable) {
            XposedHelpers.closeSilently(in);
            throw throwable;
        }
        {
            XposedHelpers.closeSilently(in);
            return bl;
        }
        boolean bl2 = false;
        XposedHelpers.closeSilently(in);
        return bl2;
    }

    static Method getOverriddenMethod(Method method) {
        int modifiers = method.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isPrivate(modifiers)) {
            return null;
        }
        String name = method.getName();
        Class<?>[] parameters = method.getParameterTypes();
        for (Class<?> clazz = method.getDeclaringClass().getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method superMethod = clazz.getDeclaredMethod(name, parameters);
                modifiers = superMethod.getModifiers();
                if (!Modifier.isPrivate(modifiers) && !Modifier.isAbstract(modifiers)) {
                    return superMethod;
                }
                return null;
            }
            catch (NoSuchMethodException ignored) {
                continue;
            }
        }
        return null;
    }

    static Set<Method> getOverriddenMethods(Class<?> clazz) {
        HashSet<Method> methods = new HashSet<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            Method overridden = XposedHelpers.getOverriddenMethod(method);
            if (overridden == null) continue;
            methods.add(overridden);
        }
        return methods;
    }

    public static final class InvocationTargetError
    extends Error {
        private static final long serialVersionUID = -1070936889459514628L;

        public InvocationTargetError(Throwable cause) {
            super(cause);
        }
    }

    public static final class ClassNotFoundError
    extends Error {
        private static final long serialVersionUID = -1070936889459514628L;

        public ClassNotFoundError(Throwable cause) {
            super(cause);
        }

        public ClassNotFoundError(String detailMessage, Throwable cause) {
            super(detailMessage, cause);
        }
    }
}

