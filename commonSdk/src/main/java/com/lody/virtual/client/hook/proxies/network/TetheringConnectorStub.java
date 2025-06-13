/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.hook.proxies.network;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import mirror.android.net.ITetheringConnector;

public class TetheringConnectorStub
extends BinderInvocationProxy {
    private static final String SERVER_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRguLGUFNARjDlk9"));

    public TetheringConnectorStub() {
        super(ITetheringConnector.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRguLGUFNARjDlk9")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2BmgaMCBiASwzKj06AWUgTQJlJAo/LhgqVg=="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                try {
                    Object iIntResultListener = args[2];
                    Reflect.on(iIntResultListener).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4cDGgaLAVgEQpF")), 3);
                }
                catch (Exception e) {
                    return super.call(who, method, args);
                }
                return null;
            }
        });
    }
}

