/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.window.session;

import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.proxies.window.session.BaseMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.android.view.WindowManagerGlobal;

public class WindowSessionPatch
extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
    private static final int ADD_PERMISSION_DENIED = WindowManagerGlobal.ADD_PERMISSION_DENIED != null ? WindowManagerGlobal.ADD_PERMISSION_DENIED.get() : -8;

    public WindowSessionPatch(IInterface session) {
        super(new MethodInvocationStub<IInterface>(session));
    }

    @Override
    public void onBindMethods() {
        this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPA=="))));
        this.addMethodProxy(new AddToDisplay(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQFGhZjASgsKhciIWYgAlBsJyg5"))));
        this.addMethodProxy(new AddToDisplay(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQFGhZjASgsKhciIQ=="))));
        this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQFGhZjASgsKhciIX0FLAZqEQYwKgYYKmEVLD9hJAYqKRgqJ2UVSFo="))));
        this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQzAgZjHh4vLBUcDmowGgZgJ1ksLC4cJ2AVSFo="))));
        this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDmsaAiVmAQpF"))));
        if (BuildCompat.isQ()) {
            this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQFGhZjASgsKhciIWYgAlBsJyg5"))));
            this.addMethodProxy(new BaseMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS0MP2ogMAlgNyQvLBUqMm4jMCZrAQJF"))));
        }
    }

    @Override
    public void inject() throws Throwable {
    }

    @Override
    public boolean isEnvBad() {
        return ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface() != null;
    }

    class AddToDisplay
    extends BaseMethodProxy {
        public AddToDisplay(String name) {
            super(name);
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (this.isDrawOverlays() && VirtualCore.getConfig().isDisableDrawOverlays(AddToDisplay.getAppPkg())) {
                return ADD_PERMISSION_DENIED;
            }
            return super.call(who, method, args);
        }
    }
}

