/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.hook.base;

import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import java.lang.reflect.Method;

public class ReplaceCallingPkgMethodProxy
extends StaticMethodProxy {
    public ReplaceCallingPkgMethodProxy(String name) {
        super(name);
    }

    @Override
    public boolean beforeCall(Object who, Method method, Object ... args) {
        MethodParameterUtils.replaceFirstAppPkg(args);
        return super.beforeCall(who, method, args);
    }
}

