/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.window;

import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.proxies.window.session.WindowSessionPatch;
import java.lang.reflect.Method;

class MethodProxies {
    MethodProxies() {
    }

    static abstract class BasePatchSession
    extends MethodProxy {
        BasePatchSession() {
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            Object session = method.invoke(who, args);
            if (session instanceof IInterface) {
                return this.proxySession((IInterface)session);
            }
            return session;
        }

        private Object proxySession(IInterface session) {
            WindowSessionPatch windowSessionPatch = new WindowSessionPatch(session);
            return ((MethodInvocationStub)windowSessionPatch.getInvocationStub()).getProxyInterface();
        }
    }

    static class SetAppStartingWindow
    extends BasePatchSession {
        SetAppStartingWindow() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaIAJpJwo7Iz42MW8VElJqARovLD0mVg=="));
        }
    }

    static class OverridePendingAppTransitionInPlace
    extends MethodProxy {
        OverridePendingAppTransitionInPlace() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0iM28gFi9iHjACKAcYPmwjMC1gDjw7IgcMO2AzNCxqHhoeKRY2KmQVHjNrNyhF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (args[0] instanceof String) {
                args[0] = OverridePendingAppTransitionInPlace.getHostPkg();
            }
            return method.invoke(who, args);
        }
    }

    static class OverridePendingAppTransition
    extends BasePatchSession {
        OverridePendingAppTransition() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy0iM28gFi9iHjACKAcYPmwjMC1gDjw7IgcMO2AzNCxqHhoeKRhSVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (args[0] instanceof String) {
                args[0] = OverridePendingAppTransition.getHostPkg();
            }
            return super.call(who, method, args);
        }
    }

    static class OpenSession
    extends BasePatchSession {
        OpenSession() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy06M2omLCthJygzKi0YVg=="));
        }
    }
}

