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
import mirror.RefStaticMethod;

public class RefMethod<T> {
    private Method method;

    public RefMethod(Class<?> cls, Field field) throws NoSuchMethodException {
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
            String[] typeNames = field.getAnnotation(MethodReflectParams.class).value();
            Class[] types = new Class[typeNames.length];
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
            }
            this.method = cls.getDeclaredMethod(field.getName(), types);
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

    public T call(Object receiver, Object ... args) {
        try {
            return (T)this.method.invoke(receiver, args);
        }
        catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public T callWithException(Object receiver, Object ... args) throws Throwable {
        try {
            return (T)this.method.invoke(receiver, args);
        }
        catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }
    }

    public Class<?>[] paramList() {
        return this.method.getParameterTypes();
    }
}

