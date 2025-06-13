/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.hook.proxies.vibrator;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.com.android.internal.os.IVibratorManagerService;
import mirror.com.android.internal.os.IVibratorService;

public class VibratorStub
extends BinderInvocationProxy {
    public VibratorStub() {
        super(BuildCompat.isS() ? IVibratorManagerService.Stub.asInterface : IVibratorService.Stub.asInterface, BuildCompat.isS() ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZgJyxAKgciDm4jEitsN1RF")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZgJyxF")));
    }

    @Override
    protected void onBindMethods() {
        this.addMethodProxy(new VibrateMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZiDF07KC0YMWUwGixrAVRF"))));
        this.addMethodProxy(new VibrateMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZiDyQ7LBg2PWoVMA1oASAbIxcqCWIaLFo="))));
        this.addMethodProxy(new VibrateMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZiAVRF"))));
        this.addMethodProxy(new VibrateMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YOm8jJAZiDyQ7LBg2PWoVMFo="))));
    }

    private static final class VibrateMethodProxy
    extends ReplaceCallingPkgMethodProxy {
        private VibrateMethodProxy(String name) {
            super(name);
        }

        @Override
        public boolean beforeCall(Object who, Method method, Object ... args) {
            if (args[0] instanceof Integer) {
                args[0] = VibrateMethodProxy.getRealUid();
            }
            return super.beforeCall(who, method, args);
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return super.call(who, method, args);
        }
    }
}

