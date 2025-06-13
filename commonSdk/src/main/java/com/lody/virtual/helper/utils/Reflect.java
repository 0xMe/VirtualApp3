/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.helper.utils;

import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.ReflectException;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reflect {
    private final Object object;
    private final boolean isClass;

    private Reflect(Class<?> type) {
        this.object = type;
        this.isClass = true;
    }

    private Reflect(Object object) {
        this.object = object;
        this.isClass = false;
    }

    public static Reflect on(String name) throws ReflectException {
        return Reflect.on(Reflect.forName(name));
    }

    public static Reflect on(String name, ClassLoader classLoader) throws ReflectException {
        return Reflect.on(Reflect.forName(name, classLoader));
    }

    public static Reflect on(Class<?> clazz) {
        return new Reflect(clazz);
    }

    public static Reflect on(Object object) {
        return new Reflect(object);
    }

    public static <T extends AccessibleObject> T accessible(T accessible) {
        Member member;
        if (accessible == null) {
            return null;
        }
        if (accessible instanceof Member && Modifier.isPublic((member = (Member)((Object)accessible)).getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
            return accessible;
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    private static String property(String string2) {
        int length = string2.length();
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            return string2.toLowerCase();
        }
        return string2.substring(0, 1).toLowerCase() + string2.substring(1);
    }

    private static Reflect on(Constructor<?> constructor, Object ... args) throws ReflectException {
        try {
            return Reflect.on(Reflect.accessible(constructor).newInstance(args));
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Reflect on(Method method, Object object, Object ... args) throws ReflectException {
        try {
            Reflect.accessible(method);
            if (method.getReturnType() == Void.TYPE) {
                method.invoke(object, args);
                return Reflect.on(object);
            }
            return Reflect.on(method.invoke(object, args));
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Object unwrap(Object object) {
        if (object instanceof Reflect) {
            return ((Reflect)object).get();
        }
        return object;
    }

    private static Class<?>[] types(Object ... values) {
        if (values == null) {
            return new Class[0];
        }
        Class[] result = new Class[values.length];
        for (int i = 0; i < values.length; ++i) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }
        return result;
    }

    private static Class<?> forName(String name) throws ReflectException {
        try {
            return Class.forName(name);
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) throws ReflectException {
        try {
            return Class.forName(name, true, classLoader);
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        }
        if (type.isPrimitive()) {
            if (Boolean.TYPE == type) {
                return Boolean.class;
            }
            if (Integer.TYPE == type) {
                return Integer.class;
            }
            if (Long.TYPE == type) {
                return Long.class;
            }
            if (Short.TYPE == type) {
                return Short.class;
            }
            if (Byte.TYPE == type) {
                return Byte.class;
            }
            if (Double.TYPE == type) {
                return Double.class;
            }
            if (Float.TYPE == type) {
                return Float.class;
            }
            if (Character.TYPE == type) {
                return Character.class;
            }
            if (Void.TYPE == type) {
                return Void.class;
            }
        }
        return type;
    }

    public static Object defaultValue(Class<?> _type) {
        Class<?> type = Reflect.wrapper(_type);
        if (type == null) {
            return null;
        }
        if (type.isPrimitive()) {
            if (Boolean.class == type) {
                return false;
            }
            if (Number.class.isAssignableFrom(type)) {
                return 0;
            }
            if (Character.class == type) {
                return Character.valueOf('\u0000');
            }
            if (Void.class == type) {
                return null;
            }
        }
        return null;
    }

    public <T> T get() {
        return (T)this.object;
    }

    public Reflect set(String name, Object value) throws ReflectException {
        try {
            Field field = this.field0(name);
            field.setAccessible(true);
            field.set(this.object, Reflect.unwrap(value));
            return this;
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public <T> T opt(String name) {
        try {
            return this.field(name).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T get(String name) throws ReflectException {
        return this.field(name).get();
    }

    public Reflect field(String name) throws ReflectException {
        try {
            Field field = this.field0(name);
            return Reflect.on(field.get(this.object));
        }
        catch (Exception e) {
            throw new ReflectException(this.object.getClass().getName(), e);
        }
    }

    private Field field0(String name) throws ReflectException {
        Class<?> type = this.type();
        try {
            return type.getField(name);
        }
        catch (NoSuchFieldException e) {
            while (true) {
                try {
                    return Reflect.accessible(type.getDeclaredField(name));
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    if ((type = type.getSuperclass()) != null) continue;
                    throw new ReflectException(e);
                }
                break;
            }
        }
    }

    public Map<String, Reflect> fields() {
        LinkedHashMap<String, Reflect> result = new LinkedHashMap<String, Reflect>();
        Class<?> type = this.type();
        do {
            for (Field field : type.getDeclaredFields()) {
                String name;
                if (!(!this.isClass ^ Modifier.isStatic(field.getModifiers())) || result.containsKey(name = field.getName())) continue;
                result.put(name, this.field(name));
            }
        } while ((type = type.getSuperclass()) != null);
        return result;
    }

    public Reflect call(String name) throws ReflectException {
        return this.call(name, new Object[0]);
    }

    public Reflect call(String name, Object ... args) throws ReflectException {
        Class<?>[] types = Reflect.types(args);
        try {
            Method method = this.exactMethod(name, types);
            return Reflect.on(method, this.object, args);
        }
        catch (NoSuchMethodException e) {
            try {
                Method method = this.similarMethod(name, types);
                return Reflect.on(method, this.object, args);
            }
            catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    public Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> type = this.type();
        try {
            return type.getMethod(name, types);
        }
        catch (NoSuchMethodException e) {
            while (true) {
                try {
                    return type.getDeclaredMethod(name, types);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    if ((type = type.getSuperclass()) != null) continue;
                    throw new NoSuchMethodException();
                }
                break;
            }
        }
    }

    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> type = this.type();
        for (Method method : type.getMethods()) {
            if (!this.isSimilarSignature(method, name, types)) continue;
            return method;
        }
        do {
            for (Method method : type.getDeclaredMethods()) {
                if (!this.isSimilarSignature(method, name, types)) continue;
                return method;
            }
        } while ((type = type.getSuperclass()) != null);
        throw new NoSuchMethodException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOG8zAiNjDlE7IzkmD2kgBiBlJy8r")) + name + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcmCWwFRChhHiAqLwdXL34zSFo=")) + Arrays.toString(types) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2D2wVHixLHiw/Pxc+DWUjMCx4EQYbPQcqM2EaLyM=")) + this.type() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz5SVg==")));
    }

    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && this.match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    public Reflect create() throws ReflectException {
        return this.create(new Object[0]);
    }

    public Reflect create(Object ... args) throws ReflectException {
        Class<?>[] types = Reflect.types(args);
        try {
            Constructor<?> constructor = this.type().getDeclaredConstructor(types);
            return Reflect.on(constructor, args);
        }
        catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : this.type().getDeclaredConstructors()) {
                if (!this.match(constructor.getParameterTypes(), types)) continue;
                return Reflect.on(constructor, args);
            }
            throw new ReflectException(e);
        }
    }

    public <P> P as(Class<P> proxyType) {
        final boolean isMap = this.object instanceof Map;
        InvocationHandler handler = new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                try {
                    return Reflect.on(Reflect.this.object).call(name, args).get();
                }
                catch (ReflectException e) {
                    if (isMap) {
                        int length;
                        Map map = (Map)Reflect.this.object;
                        int n = length = args == null ? 0 : args.length;
                        if (length == 0 && name.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLA==")))) {
                            return map.get(Reflect.property(name.substring(3)));
                        }
                        if (length == 0 && name.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2Vg==")))) {
                            return map.get(Reflect.property(name.substring(2)));
                        }
                        if (length == 1 && name.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLA==")))) {
                            map.put(Reflect.property(name.substring(3)), args[0]);
                            return null;
                        }
                    }
                    throw e;
                }
            }
        };
        return (P)Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; ++i) {
                if (actualTypes[i] == NULL.class || Reflect.wrapper(declaredTypes[i]).isAssignableFrom(Reflect.wrapper(actualTypes[i]))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.object.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof Reflect && this.object.equals(((Reflect)obj).get());
    }

    public String toString() {
        return this.object.toString();
    }

    public Class<?> type() {
        if (this.isClass) {
            return (Class)this.object;
        }
        return this.object.getClass();
    }

    public static String getMethodDetails(Method method) {
        Class<?>[] parameters;
        StringBuilder sb = new StringBuilder(40);
        sb.append(Modifier.toString(method.getModifiers())).append(" ").append(method.getReturnType().getName()).append(" ").append(method.getName()).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PBhSVg==")));
        for (Class<?> parameter : parameters = method.getParameterTypes()) {
            sb.append(parameter.getName()).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")));
        }
        if (parameters.length > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAhSVg==")));
        return sb.toString();
    }

    public Reflect callBest(String name, Object ... args) throws ReflectException {
        Object[] types = Reflect.types(args);
        Class<?> type = this.type();
        Method bestMethod = null;
        int level = 0;
        for (Method method : type.getDeclaredMethods()) {
            if (this.isSimilarSignature(method, name, (Class<?>[])types)) {
                bestMethod = method;
                level = 2;
                break;
            }
            if (this.matchObjectMethod(method, name, (Class<?>[])types)) {
                bestMethod = method;
                level = 1;
                continue;
            }
            if (!method.getName().equals(name) || method.getParameterTypes().length != 0 || level != 0) continue;
            bestMethod = method;
        }
        if (bestMethod != null) {
            if (level == 0) {
                args = new Object[]{};
            }
            if (level == 1) {
                Object[] args2 = new Object[]{args};
                args = args2;
            }
            return Reflect.on(bestMethod, this.object, args);
        }
        throw new ReflectException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4fOGoVNAZjHh4wPxc+DWUjMCx4ESQcKSo6Vg==")) + name, new NoSuchMethodException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGsjNANmVyQ3KAg2Mm8FASg=")) + name + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcmCWwFRChhHiAqLwdXL34zSFo=")) + Arrays.toString(types) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2D2wVHixLHiw/Pxc+DWUjMCx4EQYbPQcqM2EaLyM=")) + this.type() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz5SVg=="))));
    }

    public void printFields() {
        if (this.object == null) {
            return;
        }
        Map<String, Reflect> fields = this.fields();
        if (fields == null) {
            return;
        }
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Reflect> entry : fields.entrySet()) {
            String name = entry.getKey();
            Reflect reflect = entry.getValue();
            String value = reflect.object == null ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoFSFo=")) : reflect.object.toString();
            out.append(name + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl4HOA==")) + value);
            out.append('\n');
        }
        VLog.e(this.isClass ? ((Class)this.object).getSimpleName() : this.object.getClass().getSimpleName(), out.toString());
    }

    private boolean matchObjectMethod(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && this.matchObject(possiblyMatchingMethod.getParameterTypes());
    }

    private boolean matchObject(Class<?>[] parameterTypes) {
        Class<Object[]> c = Object[].class;
        return parameterTypes.length > 0 && parameterTypes[0].isAssignableFrom(c);
    }

    private static class NULL {
        private NULL() {
        }
    }
}

