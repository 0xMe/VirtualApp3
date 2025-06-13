/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.stock;

import com.android.dx.Code;
import com.android.dx.Comparison;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Label;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ProxyBuilder<T> {
    public static final int VERSION = 1;
    private static final String FIELD_NAME_HANDLER = "$__handler";
    private static final String FIELD_NAME_METHODS = "$__methodArray";
    private static final Map<ProxiedClass<?>, Class<?>> generatedProxyClasses = Collections.synchronizedMap(new HashMap());
    private final Class<T> baseClass;
    private ClassLoader parentClassLoader = ProxyBuilder.class.getClassLoader();
    private InvocationHandler handler;
    private File dexCache;
    private Class<?>[] constructorArgTypes = new Class[0];
    private Object[] constructorArgValues = new Object[0];
    private List<Class<?>> interfaces = new ArrayList();
    private Method[] methods;
    private boolean sharedClassLoader;
    private boolean markTrusted;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_BOXED = new HashMap();
    private static final Map<TypeId<?>, MethodId<?, ?>> PRIMITIVE_TYPE_TO_UNBOX_METHOD;
    private static final Map<Class<?>, MethodId<?, ?>> PRIMITIVE_TO_UNBOX_METHOD;

    private ProxyBuilder(Class<T> clazz) {
        this.baseClass = clazz;
    }

    public static <T> ProxyBuilder<T> forClass(Class<T> clazz) {
        return new ProxyBuilder<T>(clazz);
    }

    public ProxyBuilder<T> parentClassLoader(ClassLoader parent) {
        this.parentClassLoader = parent;
        return this;
    }

    public ProxyBuilder<T> handler(InvocationHandler handler) {
        this.handler = handler;
        return this;
    }

    public ProxyBuilder<T> dexCache(File dexCacheParent) {
        this.dexCache = new File(dexCacheParent, "v" + Integer.toString(1));
        this.dexCache.mkdir();
        return this;
    }

    public ProxyBuilder<T> implementing(Class<?> ... interfaces) {
        List<Class<?>> list = this.interfaces;
        for (Class<?> i : interfaces) {
            if (!i.isInterface()) {
                throw new IllegalArgumentException("Not an interface: " + i.getName());
            }
            if (list.contains(i)) continue;
            list.add(i);
        }
        return this;
    }

    public ProxyBuilder<T> constructorArgValues(Object ... constructorArgValues) {
        this.constructorArgValues = constructorArgValues;
        return this;
    }

    public ProxyBuilder<T> constructorArgTypes(Class<?> ... constructorArgTypes) {
        this.constructorArgTypes = constructorArgTypes;
        return this;
    }

    public ProxyBuilder<T> onlyMethods(Method[] methods) {
        this.methods = methods;
        return this;
    }

    public ProxyBuilder<T> withSharedClassLoader() {
        this.sharedClassLoader = true;
        return this;
    }

    public ProxyBuilder<T> markTrusted() {
        this.markTrusted = true;
        return this;
    }

    public T build() throws IOException {
        T result;
        Constructor<T> constructor;
        ProxyBuilder.check(this.handler != null, "handler == null");
        ProxyBuilder.check(this.constructorArgTypes.length == this.constructorArgValues.length, "constructorArgValues.length != constructorArgTypes.length");
        Class<T> proxyClass = this.buildProxyClass();
        try {
            constructor = proxyClass.getConstructor(this.constructorArgTypes);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No constructor for " + this.baseClass.getName() + " with parameter types " + Arrays.toString(this.constructorArgTypes));
        }
        try {
            result = constructor.newInstance(this.constructorArgValues);
        }
        catch (InstantiationException e) {
            throw new AssertionError((Object)e);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
        catch (InvocationTargetException e) {
            throw ProxyBuilder.launderCause(e);
        }
        ProxyBuilder.setInvocationHandler(result, this.handler);
        return result;
    }

    public Class<? extends T> buildProxyClass() throws IOException {
        ClassLoader requestedClassloader = this.sharedClassLoader ? this.baseClass.getClassLoader() : this.parentClassLoader;
        ProxiedClass cacheKey = new ProxiedClass(this.baseClass, this.interfaces, requestedClassloader, this.sharedClassLoader);
        Class<Object> proxyClass = generatedProxyClasses.get(cacheKey);
        if (proxyClass != null) {
            return proxyClass;
        }
        DexMaker dexMaker = new DexMaker();
        String generatedName = ProxyBuilder.getMethodNameForProxyOf(this.baseClass, this.interfaces);
        TypeId generatedType = TypeId.get("L" + generatedName + ";");
        TypeId<T> superType = TypeId.get(this.baseClass);
        ProxyBuilder.generateConstructorsAndFields(dexMaker, generatedType, superType, this.baseClass);
        Method[] methodsToProxy = this.methods == null ? this.getMethodsToProxyRecursive() : this.methods;
        Arrays.sort(methodsToProxy, new Comparator<Method>(){

            @Override
            public int compare(Method method1, Method method2) {
                String m1Signature = method1.getDeclaringClass() + method1.getName() + Arrays.toString(method1.getParameterTypes()) + method1.getReturnType();
                String m2Signature = method2.getDeclaringClass() + method2.getName() + Arrays.toString(method2.getParameterTypes()) + method2.getReturnType();
                return m1Signature.compareTo(m2Signature);
            }
        });
        ProxyBuilder.generateCodeForAllMethods(dexMaker, generatedType, methodsToProxy, superType);
        dexMaker.declare(generatedType, generatedName + ".generated", 1, superType, this.getInterfacesAsTypeIds());
        if (this.sharedClassLoader) {
            dexMaker.setSharedClassLoader(requestedClassloader);
        }
        if (this.markTrusted) {
            dexMaker.markAsTrusted();
        }
        ClassLoader classLoader = this.sharedClassLoader ? dexMaker.generateAndLoad(null, this.dexCache) : dexMaker.generateAndLoad(this.parentClassLoader, this.dexCache);
        try {
            proxyClass = this.loadClass(classLoader, generatedName);
        }
        catch (IllegalAccessError e) {
            throw new UnsupportedOperationException("cannot proxy inaccessible class " + this.baseClass, e);
        }
        catch (ClassNotFoundException e) {
            throw new AssertionError((Object)e);
        }
        ProxyBuilder.setMethodsStaticField(proxyClass, methodsToProxy);
        generatedProxyClasses.put(cacheKey, proxyClass);
        return proxyClass;
    }

    private Class<? extends T> loadClass(ClassLoader classLoader, String generatedName) throws ClassNotFoundException {
        return classLoader.loadClass(generatedName);
    }

    private static RuntimeException launderCause(InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof Error) {
            throw (Error)cause;
        }
        if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
        }
        throw new UndeclaredThrowableException(cause);
    }

    private static void setMethodsStaticField(Class<?> proxyClass, Method[] methodsToProxy) {
        try {
            Field methodArrayField = proxyClass.getDeclaredField(FIELD_NAME_METHODS);
            methodArrayField.setAccessible(true);
            methodArrayField.set(null, methodsToProxy);
        }
        catch (NoSuchFieldException e) {
            throw new AssertionError((Object)e);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static InvocationHandler getInvocationHandler(Object instance) {
        try {
            Field field = instance.getClass().getDeclaredField(FIELD_NAME_HANDLER);
            field.setAccessible(true);
            return (InvocationHandler)field.get(instance);
        }
        catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Not a valid proxy instance", e);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static void setInvocationHandler(Object instance, InvocationHandler handler) {
        try {
            Field handlerField = instance.getClass().getDeclaredField(FIELD_NAME_HANDLER);
            handlerField.setAccessible(true);
            handlerField.set(instance, handler);
        }
        catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Not a valid proxy instance", e);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static boolean isProxyClass(Class<?> c) {
        try {
            c.getDeclaredField(FIELD_NAME_HANDLER);
            return true;
        }
        catch (NoSuchFieldException e) {
            return false;
        }
    }

    private static void throwAbstractMethodError(Code code, Method method, Local<String> abstractMethodErrorMessage, Local<AbstractMethodError> abstractMethodError) {
        TypeId<AbstractMethodError> abstractMethodErrorClass = TypeId.get(AbstractMethodError.class);
        MethodId<AbstractMethodError, Void> abstractMethodErrorConstructor = abstractMethodErrorClass.getConstructor(TypeId.STRING);
        code.loadConstant(abstractMethodErrorMessage, "'" + method + "' cannot be called");
        code.newInstance(abstractMethodError, abstractMethodErrorConstructor, abstractMethodErrorMessage);
        code.throwValue(abstractMethodError);
    }

    private static <T, G extends T> void generateCodeForAllMethods(DexMaker dexMaker, TypeId<G> generatedType, Method[] methodsToProxy, TypeId<T> superclassType) {
        TypeId<InvocationHandler> handlerType = TypeId.get(InvocationHandler.class);
        TypeId<Method[]> methodArrayType = TypeId.get(Method[].class);
        FieldId<G, InvocationHandler> handlerField = generatedType.getField(handlerType, FIELD_NAME_HANDLER);
        FieldId<G, Method[]> allMethods = generatedType.getField(methodArrayType, FIELD_NAME_METHODS);
        TypeId<Method> methodType = TypeId.get(Method.class);
        TypeId<Object[]> objectArrayType = TypeId.get(Object[].class);
        MethodId<InvocationHandler, Object> methodInvoke = handlerType.getMethod(TypeId.OBJECT, "invoke", TypeId.OBJECT, methodType, objectArrayType);
        for (int m = 0; m < methodsToProxy.length; ++m) {
            Method method = methodsToProxy[m];
            String name = method.getName();
            Class<?>[] argClasses = method.getParameterTypes();
            TypeId[] argTypes = new TypeId[argClasses.length];
            for (int i = 0; i < argTypes.length; ++i) {
                argTypes[i] = TypeId.get(argClasses[i]);
            }
            Class<?> returnType = method.getReturnType();
            TypeId<?> resultType = TypeId.get(returnType);
            MethodId<G, ?> methodId = generatedType.getMethod(resultType, name, argTypes);
            TypeId<AbstractMethodError> abstractMethodErrorClass = TypeId.get(AbstractMethodError.class);
            Code code = dexMaker.declare(methodId, 1);
            Local<G> localThis = code.getThis(generatedType);
            Local<InvocationHandler> localHandler = code.newLocal(handlerType);
            Local<Object> invokeResult = code.newLocal(TypeId.OBJECT);
            Local<Integer> intValue = code.newLocal(TypeId.INT);
            Local<Object[]> args = code.newLocal(objectArrayType);
            Local<Integer> argsLength = code.newLocal(TypeId.INT);
            Local<Object> temp = code.newLocal(TypeId.OBJECT);
            Local<?> resultHolder = code.newLocal(resultType);
            Local<Method[]> methodArray = code.newLocal(methodArrayType);
            Local<Method> thisMethod = code.newLocal(methodType);
            Local<Integer> methodIndex = code.newLocal(TypeId.INT);
            Class<?> aBoxedClass = PRIMITIVE_TO_BOXED.get(returnType);
            Local<?> aBoxedResult = null;
            if (aBoxedClass != null) {
                aBoxedResult = code.newLocal(TypeId.get(aBoxedClass));
            }
            Local<InvocationHandler> nullHandler = code.newLocal(handlerType);
            Local[] superArgs2 = null;
            Local<?> superResult2 = null;
            MethodId<T, ?> superMethod = null;
            Local<String> abstractMethodErrorMessage = null;
            Local<AbstractMethodError> abstractMethodError = null;
            if ((method.getModifiers() & 0x400) == 0) {
                superArgs2 = new Local[argClasses.length];
                superResult2 = code.newLocal(resultType);
                superMethod = superclassType.getMethod(resultType, name, argTypes);
            } else {
                abstractMethodErrorMessage = code.newLocal(TypeId.STRING);
                abstractMethodError = code.newLocal(abstractMethodErrorClass);
            }
            code.loadConstant(methodIndex, m);
            code.sget(allMethods, methodArray);
            code.aget(thisMethod, methodArray, methodIndex);
            code.loadConstant(argsLength, argTypes.length);
            code.newArray(args, argsLength);
            code.iget(handlerField, localHandler, localThis);
            code.loadConstant(nullHandler, null);
            Label handlerNullCase = new Label();
            code.compare(Comparison.EQ, handlerNullCase, nullHandler, localHandler);
            for (int p = 0; p < argTypes.length; ++p) {
                code.loadConstant(intValue, p);
                Local parameter = code.getParameter(p, argTypes[p]);
                Local unboxedIfNecessary = ProxyBuilder.boxIfRequired(code, parameter, temp);
                code.aput(args, intValue, unboxedIfNecessary);
            }
            code.invokeInterface(methodInvoke, invokeResult, localHandler, localThis, thisMethod, args);
            ProxyBuilder.generateCodeForReturnStatement(code, returnType, invokeResult, resultHolder, aBoxedResult);
            code.mark(handlerNullCase);
            if ((method.getModifiers() & 0x400) == 0) {
                for (int i = 0; i < superArgs2.length; ++i) {
                    superArgs2[i] = code.getParameter(i, argTypes[i]);
                }
                if (Void.TYPE.equals(returnType)) {
                    code.invokeSuper(superMethod, null, localThis, superArgs2);
                    code.returnVoid();
                } else {
                    ProxyBuilder.invokeSuper(superMethod, code, localThis, superArgs2, superResult2);
                    code.returnValue(superResult2);
                }
            } else {
                ProxyBuilder.throwAbstractMethodError(code, method, abstractMethodErrorMessage, abstractMethodError);
            }
            MethodId<G, ?> callsSuperMethod = generatedType.getMethod(resultType, ProxyBuilder.superMethodName(method), argTypes);
            Code superCode = dexMaker.declare(callsSuperMethod, 1);
            if ((method.getModifiers() & 0x400) == 0) {
                Local<G> superThis = superCode.getThis(generatedType);
                Local[] superArgs = new Local[argClasses.length];
                for (int i = 0; i < superArgs.length; ++i) {
                    superArgs[i] = superCode.getParameter(i, argTypes[i]);
                }
                if (Void.TYPE.equals(returnType)) {
                    superCode.invokeSuper(superMethod, null, superThis, superArgs);
                    superCode.returnVoid();
                    continue;
                }
                Local<?> superResult = superCode.newLocal(resultType);
                ProxyBuilder.invokeSuper(superMethod, superCode, superThis, superArgs, superResult);
                superCode.returnValue(superResult);
                continue;
            }
            Local<String> superAbstractMethodErrorMessage = superCode.newLocal(TypeId.STRING);
            Local<AbstractMethodError> superAbstractMethodError = superCode.newLocal(abstractMethodErrorClass);
            ProxyBuilder.throwAbstractMethodError(superCode, method, superAbstractMethodErrorMessage, superAbstractMethodError);
        }
    }

    private static void invokeSuper(MethodId superMethod, Code superCode, Local superThis, Local[] superArgs, Local superResult) {
        superCode.invokeSuper(superMethod, superResult, superThis, superArgs);
    }

    private static Local<?> boxIfRequired(Code code, Local<?> parameter, Local<Object> temp) {
        MethodId<?, ?> unboxMethod = PRIMITIVE_TYPE_TO_UNBOX_METHOD.get(parameter.getType());
        if (unboxMethod == null) {
            return parameter;
        }
        code.invokeStatic(unboxMethod, temp, parameter);
        return temp;
    }

    public static Object callSuper(Object proxy, Method method, Object ... args) throws Throwable {
        try {
            return proxy.getClass().getMethod(ProxyBuilder.superMethodName(method), method.getParameterTypes()).invoke(proxy, args);
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static String superMethodName(Method method) {
        String returnType = method.getReturnType().getName();
        return "super$" + method.getName() + "$" + returnType.replace('.', '_').replace('[', '_').replace(';', '_');
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    private static <T, G extends T> void generateConstructorsAndFields(DexMaker dexMaker, TypeId<G> generatedType, TypeId<T> superType, Class<T> superClass) {
        TypeId<InvocationHandler> handlerType = TypeId.get(InvocationHandler.class);
        TypeId<Method[]> methodArrayType = TypeId.get(Method[].class);
        FieldId<G, InvocationHandler> handlerField = generatedType.getField(handlerType, FIELD_NAME_HANDLER);
        dexMaker.declare(handlerField, 2, null);
        FieldId<G, Method[]> allMethods = generatedType.getField(methodArrayType, FIELD_NAME_METHODS);
        dexMaker.declare(allMethods, 10, null);
        for (Constructor<T> constructor : ProxyBuilder.getConstructorsToOverwrite(superClass)) {
            if (constructor.getModifiers() == 16) continue;
            TypeId<?>[] types = ProxyBuilder.classArrayToTypeArray(constructor.getParameterTypes());
            MethodId<G, Void> method = generatedType.getConstructor(types);
            Code constructorCode = dexMaker.declare(method, 1);
            Local<G> thisRef = constructorCode.getThis(generatedType);
            Local[] params = new Local[types.length];
            for (int i = 0; i < params.length; ++i) {
                params[i] = constructorCode.getParameter(i, types[i]);
            }
            MethodId<T, Void> superConstructor = superType.getConstructor(types);
            constructorCode.invokeDirect(superConstructor, null, thisRef, params);
            constructorCode.returnVoid();
        }
    }

    private static <T> Constructor<T>[] getConstructorsToOverwrite(Class<T> clazz) {
        return clazz.getDeclaredConstructors();
    }

    private TypeId<?>[] getInterfacesAsTypeIds() {
        TypeId[] result = new TypeId[this.interfaces.size()];
        int i = 0;
        for (Class<?> implemented : this.interfaces) {
            result[i++] = TypeId.get(implemented);
        }
        return result;
    }

    private Method[] getMethodsToProxyRecursive() {
        Class c;
        HashSet<MethodSetEntry> methodsToProxy = new HashSet<MethodSetEntry>();
        HashSet<MethodSetEntry> seenFinalMethods = new HashSet<MethodSetEntry>();
        for (c = this.baseClass; c != null; c = c.getSuperclass()) {
            this.getMethodsToProxy(methodsToProxy, seenFinalMethods, c);
        }
        for (c = this.baseClass; c != null; c = c.getSuperclass()) {
            for (Class<?> i : c.getInterfaces()) {
                this.getMethodsToProxy(methodsToProxy, seenFinalMethods, i);
            }
        }
        for (Class clazz : this.interfaces) {
            this.getMethodsToProxy(methodsToProxy, seenFinalMethods, clazz);
        }
        Method[] results = new Method[methodsToProxy.size()];
        boolean bl = false;
        for (MethodSetEntry entry : methodsToProxy) {
            results[++var4_8] = entry.originalMethod;
        }
        return results;
    }

    private void getMethodsToProxy(Set<MethodSetEntry> sink, Set<MethodSetEntry> seenFinalMethods, Class<?> c) {
        for (Method method : c.getDeclaredMethods()) {
            MethodSetEntry entry;
            if ((method.getModifiers() & 0x10) != 0) {
                entry = new MethodSetEntry(method);
                seenFinalMethods.add(entry);
                sink.remove(entry);
                continue;
            }
            if ((method.getModifiers() & 8) != 0 || !Modifier.isPublic(method.getModifiers()) && !Modifier.isProtected(method.getModifiers()) && (!this.sharedClassLoader || Modifier.isPrivate(method.getModifiers())) || method.getName().equals("finalize") && method.getParameterTypes().length == 0 || seenFinalMethods.contains(entry = new MethodSetEntry(method))) continue;
            sink.add(entry);
        }
        if (c.isInterface()) {
            for (GenericDeclaration genericDeclaration : c.getInterfaces()) {
                this.getMethodsToProxy(sink, seenFinalMethods, (Class<?>)genericDeclaration);
            }
        }
    }

    private static <T> String getMethodNameForProxyOf(Class<T> clazz, List<Class<?>> interfaces) {
        String interfacesHash = Integer.toHexString(interfaces.hashCode());
        return clazz.getName().replace(".", "/") + "_" + interfacesHash + "_Proxy";
    }

    private static TypeId<?>[] classArrayToTypeArray(Class<?>[] input) {
        TypeId[] result = new TypeId[input.length];
        for (int i = 0; i < input.length; ++i) {
            result[i] = TypeId.get(input[i]);
        }
        return result;
    }

    private static void generateCodeForReturnStatement(Code code, Class methodReturnType, Local localForResultOfInvoke, Local localOfMethodReturnType, Local aBoxedResult) {
        if (PRIMITIVE_TO_UNBOX_METHOD.containsKey(methodReturnType)) {
            code.cast(aBoxedResult, localForResultOfInvoke);
            MethodId<?, ?> unboxingMethodFor = ProxyBuilder.getUnboxMethodForPrimitive(methodReturnType);
            code.invokeVirtual(unboxingMethodFor, localOfMethodReturnType, aBoxedResult, new Local[0]);
            code.returnValue(localOfMethodReturnType);
        } else if (Void.TYPE.equals(methodReturnType)) {
            code.returnVoid();
        } else {
            code.cast(localOfMethodReturnType, localForResultOfInvoke);
            code.returnValue(localOfMethodReturnType);
        }
    }

    private static MethodId<?, ?> getUnboxMethodForPrimitive(Class<?> methodReturnType) {
        return PRIMITIVE_TO_UNBOX_METHOD.get(methodReturnType);
    }

    static {
        PRIMITIVE_TO_BOXED.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_TO_BOXED.put(Integer.TYPE, Integer.class);
        PRIMITIVE_TO_BOXED.put(Byte.TYPE, Byte.class);
        PRIMITIVE_TO_BOXED.put(Long.TYPE, Long.class);
        PRIMITIVE_TO_BOXED.put(Short.TYPE, Short.class);
        PRIMITIVE_TO_BOXED.put(Float.TYPE, Float.class);
        PRIMITIVE_TO_BOXED.put(Double.TYPE, Double.class);
        PRIMITIVE_TO_BOXED.put(Character.TYPE, Character.class);
        PRIMITIVE_TYPE_TO_UNBOX_METHOD = new HashMap();
        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_BOXED.entrySet()) {
            TypeId<?> primitiveType = TypeId.get(entry.getKey());
            TypeId<?> boxedType = TypeId.get(entry.getValue());
            MethodId<?, ?> valueOfMethod = boxedType.getMethod(boxedType, "valueOf", primitiveType);
            PRIMITIVE_TYPE_TO_UNBOX_METHOD.put(primitiveType, valueOfMethod);
        }
        HashMap map = new HashMap();
        map.put(Boolean.TYPE, TypeId.get(Boolean.class).getMethod(TypeId.BOOLEAN, "booleanValue", new TypeId[0]));
        map.put(Integer.TYPE, TypeId.get(Integer.class).getMethod(TypeId.INT, "intValue", new TypeId[0]));
        map.put(Byte.TYPE, TypeId.get(Byte.class).getMethod(TypeId.BYTE, "byteValue", new TypeId[0]));
        map.put(Long.TYPE, TypeId.get(Long.class).getMethod(TypeId.LONG, "longValue", new TypeId[0]));
        map.put(Short.TYPE, TypeId.get(Short.class).getMethod(TypeId.SHORT, "shortValue", new TypeId[0]));
        map.put(Float.TYPE, TypeId.get(Float.class).getMethod(TypeId.FLOAT, "floatValue", new TypeId[0]));
        map.put(Double.TYPE, TypeId.get(Double.class).getMethod(TypeId.DOUBLE, "doubleValue", new TypeId[0]));
        map.put(Character.TYPE, TypeId.get(Character.class).getMethod(TypeId.CHAR, "charValue", new TypeId[0]));
        PRIMITIVE_TO_UNBOX_METHOD = map;
    }

    private static class ProxiedClass<U> {
        final Class<U> clazz;
        final List<Class<?>> interfaces;
        final ClassLoader requestedClassloader;
        final boolean sharedClassLoader;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || this.getClass() != other.getClass()) {
                return false;
            }
            ProxiedClass that = (ProxiedClass)other;
            return this.clazz == that.clazz && this.interfaces.equals(that.interfaces) && this.requestedClassloader == that.requestedClassloader && this.sharedClassLoader == that.sharedClassLoader;
        }

        public int hashCode() {
            return this.clazz.hashCode() + this.interfaces.hashCode() + this.requestedClassloader.hashCode() + (this.sharedClassLoader ? 1 : 0);
        }

        private ProxiedClass(Class<U> clazz, List<Class<?>> interfaces, ClassLoader requestedClassloader, boolean sharedClassLoader) {
            this.clazz = clazz;
            this.interfaces = new ArrayList(interfaces);
            this.requestedClassloader = requestedClassloader;
            this.sharedClassLoader = sharedClassLoader;
        }
    }

    public static class MethodSetEntry {
        public final String name;
        public final Class<?>[] paramTypes;
        public final Class<?> returnType;
        public final Method originalMethod;

        public MethodSetEntry(Method method) {
            this.originalMethod = method;
            this.name = method.getName();
            this.paramTypes = method.getParameterTypes();
            this.returnType = method.getReturnType();
        }

        public boolean equals(Object o) {
            if (o instanceof MethodSetEntry) {
                MethodSetEntry other = (MethodSetEntry)o;
                return this.name.equals(other.name) && this.returnType.equals(other.returnType) && Arrays.equals(this.paramTypes, other.paramTypes);
            }
            return false;
        }

        public int hashCode() {
            int result = 17;
            result += 31 * result + this.name.hashCode();
            result += 31 * result + this.returnType.hashCode();
            result += 31 * result + Arrays.hashCode(this.paramTypes);
            return result;
        }
    }
}

