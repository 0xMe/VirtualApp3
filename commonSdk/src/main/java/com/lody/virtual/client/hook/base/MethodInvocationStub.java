/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.lody.virtual.client.hook.base;

import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.LogInvocation;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MethodInvocationStub<T> {
    private static final String TAG = MethodInvocationStub.class.getSimpleName();
    private Map<String, MethodProxy> mInternalMethodProxies = new HashMap<String, MethodProxy>();
    private T mBaseInterface;
    private T mProxyInterface;
    private MethodProxy mDefaultProxy;
    private LogInvocation.Condition mInvocationLoggingCondition = LogInvocation.Condition.NEVER;

    public Map<String, MethodProxy> getAllHooks() {
        return this.mInternalMethodProxies;
    }

    public MethodInvocationStub(T baseInterface, Class<?> ... proxyInterfaces) {
        this.mBaseInterface = baseInterface;
        if (baseInterface != null) {
            if (proxyInterfaces == null) {
                proxyInterfaces = MethodParameterUtils.getAllInterface(baseInterface.getClass());
            }
            this.mProxyInterface = Proxy.newProxyInstance(baseInterface.getClass().getClassLoader(), proxyInterfaces, new HookInvocationHandler());
        }
    }

    public LogInvocation.Condition getInvocationLoggingCondition() {
        return this.mInvocationLoggingCondition;
    }

    public void setInvocationLoggingCondition(LogInvocation.Condition invocationLoggingCondition) {
        this.mInvocationLoggingCondition = invocationLoggingCondition;
    }

    public MethodInvocationStub(T baseInterface) {
        this(baseInterface, null);
    }

    public void copyMethodProxies(MethodInvocationStub from) {
        this.mInternalMethodProxies.putAll(from.getAllHooks());
    }

    public MethodProxy addMethodProxy(MethodProxy methodProxy) {
        if (methodProxy != null && !TextUtils.isEmpty((CharSequence)methodProxy.getMethodName())) {
            if (this.mInternalMethodProxies.containsKey(methodProxy.getMethodName())) {
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IRhfM3sLRSVgJAE0OAgpCH43GgN6DTw0LD0tJH0KFi9uDjMpKj5bD3gVFj9oER03JQdeL24FGj1qDho0Iz42M3ojSFo=")), methodProxy.getMethodName(), methodProxy.getClass().getName());
                return methodProxy;
            }
            this.mInternalMethodProxies.put(methodProxy.getMethodName(), methodProxy);
        }
        return methodProxy;
    }

    public MethodProxy removeMethodProxy(String hookName) {
        return this.mInternalMethodProxies.remove(hookName);
    }

    public void removeMethodProxy(MethodProxy methodProxy) {
        if (methodProxy != null) {
            this.removeMethodProxy(methodProxy.getMethodName());
        }
    }

    public void removeAllMethodProxies() {
        this.mInternalMethodProxies.clear();
    }

    public <H extends MethodProxy> H getMethodProxy(String name) {
        MethodProxy proxy = this.mInternalMethodProxies.get(name);
        if (proxy == null) {
            return (H)this.mDefaultProxy;
        }
        return (H)proxy;
    }

    public void setDefaultMethodProxy(MethodProxy proxy) {
        this.mDefaultProxy = proxy;
    }

    public T getProxyInterface() {
        return this.mProxyInterface;
    }

    public T getBaseInterface() {
        return this.mBaseInterface;
    }

    public int getMethodProxiesCount() {
        return this.mInternalMethodProxies.size();
    }

    public static String argToString(Object obj) {
        if (obj != null && obj.getClass().isArray()) {
            StringBuilder b = new StringBuilder();
            Object[] array2 = (Object[])obj;
            for (int j = 0; j < array2.length; ++j) {
                Object e = array2[j];
                b.append(e);
                if (j == array2.length - 1) continue;
                b.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")));
            }
            return b.toString();
        }
        return String.valueOf(obj);
    }

    public static String argsToString(Object[] a) {
        if (a == null) {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoFSFo="));
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("P14cVg=="));
        }
        StringBuilder b = new StringBuilder();
        b.append('<');
        int i = 0;
        while (true) {
            Object obj = a[i];
            b.append(MethodInvocationStub.argToString(obj));
            if (i == iMax) {
                return b.append('>').toString();
            }
            b.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")));
            ++i;
        }
    }

    private void dumpMethodProxies() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PDpWMnUnTCJJMAUyOTkHCnxSIyJ6M1AhMypWLg==")));
        for (MethodProxy proxy : this.mInternalMethodProxies.values()) {
            sb.append(proxy.getMethodName()).append("\n");
        }
        sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PDpWMnUnTCJJMAUyOTkHCnxSIyJ6M1AhMypWLg==")));
        VLog.e(TAG, sb.toString());
    }

    private class HookInvocationHandler
    implements InvocationHandler {
        private HookInvocationHandler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object e2;
            block16: {
                boolean mightLog;
                Object methodProxy = MethodInvocationStub.this.getMethodProxy(method.getName());
                boolean useProxy = VirtualCore.get().isStartup() && methodProxy != null && ((MethodProxy)methodProxy).isEnable();
                boolean bl = mightLog = MethodInvocationStub.this.mInvocationLoggingCondition != LogInvocation.Condition.NEVER || methodProxy != null && ((MethodProxy)methodProxy).getInvocationLoggingCondition() != LogInvocation.Condition.NEVER;
                if (!VirtualCore.get().isVAppProcess()) {
                    mightLog = false;
                }
                String argStr = null;
                Object res = null;
                Throwable exception = null;
                if (mightLog) {
                    try {
                        argStr = MethodInvocationStub.argsToString(args);
                        argStr = argStr.substring(1, argStr.length() - 1);
                    }
                    catch (Throwable e2) {
                        argStr = "" + e2.getMessage();
                    }
                }
                try {
                    if (useProxy && ((MethodProxy)methodProxy).beforeCall(MethodInvocationStub.this.mBaseInterface, method, args)) {
                        res = ((MethodProxy)methodProxy).call(MethodInvocationStub.this.mBaseInterface, method, args);
                        res = ((MethodProxy)methodProxy).afterCall(MethodInvocationStub.this.mBaseInterface, method, args, res);
                    } else {
                        res = method.invoke(MethodInvocationStub.this.mBaseInterface, args);
                    }
                    e2 = res;
                    if (!mightLog) break block16;
                }
                catch (Throwable t) {
                    try {
                        exception = t;
                        if (exception instanceof InvocationTargetException && ((InvocationTargetException)exception).getTargetException() != null) {
                            exception = ((InvocationTargetException)exception).getTargetException();
                        }
                        throw exception;
                    }
                    catch (Throwable throwable) {
                        if (mightLog) {
                            int logPriority = MethodInvocationStub.this.mInvocationLoggingCondition.getLogLevel(useProxy, exception != null);
                            if (methodProxy != null) {
                                logPriority = Math.max(logPriority, ((MethodProxy)methodProxy).getInvocationLoggingCondition().getLogLevel(useProxy, exception != null));
                            }
                            if (logPriority >= 0) {
                                String retString = exception != null ? exception.toString() : (method.getReturnType().equals(Void.TYPE) ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4ACWgFSFo=")) : MethodInvocationStub.argToString(res));
                                Log.println((int)logPriority, (String)TAG, (String)(method.getDeclaringClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz5SVg==")) + method.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PBhSVg==")) + argStr + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAQ5O34nIFo=")) + retString));
                            }
                        }
                        throw throwable;
                    }
                }
                int logPriority = MethodInvocationStub.this.mInvocationLoggingCondition.getLogLevel(useProxy, exception != null);
                if (methodProxy != null) {
                    logPriority = Math.max(logPriority, ((MethodProxy)methodProxy).getInvocationLoggingCondition().getLogLevel(useProxy, exception != null));
                }
                if (logPriority >= 0) {
                    String retString = exception != null ? exception.toString() : (method.getReturnType().equals(Void.TYPE) ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4ACWgFSFo=")) : MethodInvocationStub.argToString(res));
                    Log.println((int)logPriority, (String)TAG, (String)(method.getDeclaringClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz5SVg==")) + method.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PBhSVg==")) + argStr + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAQ5O34nIFo=")) + retString));
                }
            }
            return e2;
        }
    }
}

