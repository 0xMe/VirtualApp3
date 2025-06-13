/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx;

import com.android.dx.FieldId;
import com.android.dx.MethodId;
import com.android.dx.TypeList;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import java.util.HashMap;
import java.util.Map;

public final class TypeId<T> {
    public static final TypeId<Boolean> BOOLEAN = new TypeId(Type.BOOLEAN);
    public static final TypeId<Byte> BYTE = new TypeId(Type.BYTE);
    public static final TypeId<Character> CHAR = new TypeId(Type.CHAR);
    public static final TypeId<Double> DOUBLE = new TypeId(Type.DOUBLE);
    public static final TypeId<Float> FLOAT = new TypeId(Type.FLOAT);
    public static final TypeId<Integer> INT = new TypeId(Type.INT);
    public static final TypeId<Long> LONG = new TypeId(Type.LONG);
    public static final TypeId<Short> SHORT = new TypeId(Type.SHORT);
    public static final TypeId<Void> VOID = new TypeId(Type.VOID);
    public static final TypeId<Object> OBJECT = new TypeId(Type.OBJECT);
    public static final TypeId<String> STRING = new TypeId(Type.STRING);
    private static final Map<Class<?>, TypeId<?>> PRIMITIVE_TO_TYPE = new HashMap();
    final String name;
    final Type ropType;
    final CstType constant;

    TypeId(Type ropType) {
        this(ropType.getDescriptor(), ropType);
    }

    TypeId(String name, Type ropType) {
        if (name == null || ropType == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.ropType = ropType;
        this.constant = CstType.intern(ropType);
    }

    public static <T> TypeId<T> get(String name) {
        return new TypeId<T>(name, Type.internReturnType(name));
    }

    public static <T> TypeId<T> get(Class<T> type) {
        if (type.isPrimitive()) {
            TypeId<?> result = PRIMITIVE_TO_TYPE.get(type);
            return result;
        }
        String name = type.getName().replace('.', '/');
        return TypeId.get(type.isArray() ? name : 'L' + name + ';');
    }

    public <V> FieldId<T, V> getField(TypeId<V> type, String name) {
        return new FieldId(this, type, name);
    }

    public MethodId<T, Void> getConstructor(TypeId<?> ... parameters) {
        return new MethodId(this, VOID, "<init>", new TypeList(parameters));
    }

    public MethodId<T, Void> getStaticInitializer() {
        return new MethodId(this, VOID, "<clinit>", new TypeList(new TypeId[0]));
    }

    public <R> MethodId<T, R> getMethod(TypeId<R> returnType, String name, TypeId<?> ... parameters) {
        return new MethodId(this, returnType, name, new TypeList(parameters));
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        return o instanceof TypeId && ((TypeId)o).name.equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }

    static {
        PRIMITIVE_TO_TYPE.put(Boolean.TYPE, BOOLEAN);
        PRIMITIVE_TO_TYPE.put(Byte.TYPE, BYTE);
        PRIMITIVE_TO_TYPE.put(Character.TYPE, CHAR);
        PRIMITIVE_TO_TYPE.put(Double.TYPE, DOUBLE);
        PRIMITIVE_TO_TYPE.put(Float.TYPE, FLOAT);
        PRIMITIVE_TO_TYPE.put(Integer.TYPE, INT);
        PRIMITIVE_TO_TYPE.put(Long.TYPE, LONG);
        PRIMITIVE_TO_TYPE.put(Short.TYPE, SHORT);
        PRIMITIVE_TO_TYPE.put(Void.TYPE, VOID);
    }
}

