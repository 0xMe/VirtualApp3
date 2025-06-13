/*
 * Decompiled with CFR 0.152.
 */
package mirror;

import com.lody.virtual.StringFog;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mirror.MethodParams;
import mirror.MethodReflectParams;

public class RefStaticMethod<T> {
    private Method method;
    private String parent;
    private String name;

    public RefStaticMethod(Class<?> cls, Field field) throws NoSuchMethodException {
        this.name = field.getName();
        this.parent = cls.getName();
        if (field.isAnnotationPresent(MethodParams.class)) {
            Class<?>[] types = field.getAnnotation(MethodParams.class).value();
            for (int i = 0; i < types.length; ++i) {
                Class<?> clazz = types[i];
                if (clazz.getClassLoader() != this.getClass().getClassLoader()) continue;
                try {
                    Class realClass;
                    Class.forName(clazz.getName());
                    types[i] = realClass = (Class)clazz.getField(StringFog.decrypt("JzwiMw==")).get(null);
                    continue;
                }
                catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            this.method = cls.getDeclaredMethod(field.getName(), types);
            this.method.setAccessible(true);
        } else if (field.isAnnotationPresent(MethodReflectParams.class)) {
            block19: {
                boolean arrayset = false;
                String[] typeNames = field.getAnnotation(MethodReflectParams.class).value();
                Class[] types = new Class[typeNames.length];
                Class[] types2 = new Class[typeNames.length];
                for (int i = 0; i < typeNames.length; ++i) {
                    Class<?> type = RefStaticMethod.getProtoType(typeNames[i]);
                    if (type == null) {
                        try {
                            type = Class.forName(typeNames[i]);
                        }
                        catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    types[i] = type;
                    if (StringFog.decrypt("GQQEF0sbKxoPQToRGgc9FhE=").equals(typeNames[i])) {
                        arrayset = true;
                        Class<?> type2 = type;
                        try {
                            type2 = Class.forName(StringFog.decrypt("EgsWBAoHO10WGxscRy4cAQQLJQAa"));
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            // empty catch block
                        }
                        if (type2 != null) {
                            types2[i] = type2;
                            continue;
                        }
                        types2[i] = type;
                        continue;
                    }
                    types2[i] = type;
                }
                try {
                    this.method = cls.getDeclaredMethod(field.getName(), types);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    if (!arrayset) break block19;
                    this.method = cls.getDeclaredMethod(field.getName(), types2);
                }
            }
            this.method.setAccessible(true);
        } else {
            for (Method method : cls.getDeclaredMethods()) {
                if (!method.getName().equals(field.getName())) continue;
                this.method = method;
                this.method.setAccessible(true);
                break;
            }
        }
        if (this.method == null) {
            throw new NoSuchMethodException(field.getName());
        }
    }

    static Class<?> getProtoType(String typeName) {
        if (typeName.equals(StringFog.decrypt("GgsG"))) {
            return Integer.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("HwocEQ=="))) {
            return Long.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("EQodGgAPMQ=="))) {
            return Boolean.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("ERwGEw=="))) {
            return Byte.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("AA0dBBE="))) {
            return Short.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("EA0TBA=="))) {
            return Character.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("FQkdFxE="))) {
            return Float.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("FwoHFAkL"))) {
            return Double.TYPE;
        }
        if (typeName.equals(StringFog.decrypt("BQobEg=="))) {
            return Void.TYPE;
        }
        return null;
    }

    public T call(Object ... params) {
        Object obj = null;
        try {
            obj = this.method.invoke(null, params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return (T)obj;
    }

    public T callWithException(Object ... params) throws Throwable {
        try {
            return (T)this.method.invoke(null, params);
        }
        catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }
    }

    public String toString() {
        return StringFog.decrypt("IQAUJREPKxoAIhcEAQAKCA==") + this.parent + StringFog.decrypt("Mw==") + this.name + StringFog.decrypt("UwMbGAFT") + (this.method != null) + '}';
    }
}

