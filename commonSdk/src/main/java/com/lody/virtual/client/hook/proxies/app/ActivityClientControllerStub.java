/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.app;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.proxies.am.MethodProxies;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import mirror.android.app.ActivityClient;
import mirror.android.util.Singleton;

@Inject(value=MethodProxies.class)
public class ActivityClientControllerStub
extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
    private static IInterface sActivityClientControllerProxy = null;

    public static IInterface getProxyInterface() {
        return sActivityClientControllerProxy;
    }

    public ActivityClientControllerStub() {
        super(new MethodInvocationStub<IInterface>(ActivityClient.getActivityClientController.call(new Object[0])));
    }

    @Override
    public void inject() {
        if (ActivityClient.INTERFACE_SINGLETON != null) {
            if (ActivityClient.ActivityClientControllerSingleton.mKnownInstance != null) {
                ActivityClient.ActivityClientControllerSingleton.mKnownInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), (IInterface)((MethodInvocationStub)this.getInvocationStub()).getProxyInterface());
            }
            Singleton.mInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface());
            sActivityClientControllerProxy = (IInterface)((MethodInvocationStub)this.getInvocationStub()).getProxyInterface();
        }
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQYWKAgqLmoVND9rASxF"))){

            @Override
            public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
                IBinder token = (IBinder)args[0];
                VActivityManager.get().onActivityDestroy(token);
                return super.afterCall(who, method, args, result);
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQYAKAgqLW8jGiw="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                IBinder token = (IBinder)args[0];
                VActivityManager.get().onActivityResumed(token);
                return super.call(who, method, args);
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGUaLCBlDiggKQg+MWUwLFo="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                IBinder token = (IBinder)args[0];
                Intent intent = (Intent)args[2];
                if (intent != null) {
                    args[2] = ComponentUtils.processOutsideIntent(VUserHandle.myUserId(), VirtualCore.get().isExtPackage(), intent);
                }
                VActivityManager.get().onFinishActivity(token);
                return super.call(who, method, args);
            }

            @Override
            public boolean isEnable() {
                return 3.isAppProcess();
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGUaLCBlDiggKQg+MWUwLBFrNyQaLC4YCmcFSFo="))){

            @Override
            public Object call(Object who, Method method, Object ... args) {
                IBinder token = (IBinder)args[0];
                return VActivityManager.get().finishActivityAffinity(4.getAppUserId(), token);
            }

            @Override
            public boolean isEnable() {
                return 4.isAppProcess();
            }
        });
        if (BuildCompat.isSamsung()) {
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMBFhESQOKi0qCWIFGgRvNx4qLhhSVg=="))){

                @Override
                public Object call(Object who, Method method, Object ... args) {
                    return 0;
                }
            });
        }
    }

    @Override
    public boolean isEnvBad() {
        return ActivityClient.getActivityClientController.call(new Object[0]) != ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface();
    }
}

