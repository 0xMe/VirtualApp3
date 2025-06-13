/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.am;

import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgAndLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.proxies.am.MethodProxies;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.ActivityManagerOreo;
import mirror.android.app.IActivityManager;
import mirror.android.os.ServiceManager;
import mirror.android.util.Singleton;

@Inject(value=MethodProxies.class)
public class ActivityManagerStub
extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
    public ActivityManagerStub() {
        super(new MethodInvocationStub<IInterface>(ActivityManagerNative.getDefault.call(new Object[0])));
    }

    @Override
    public void inject() {
        if (BuildCompat.isOreo()) {
            Object singleton = ActivityManagerOreo.IActivityManagerSingleton.get();
            Singleton.mInstance.set(singleton, ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface());
        } else if (ActivityManagerNative.gDefault.type() == IActivityManager.TYPE) {
            ActivityManagerNative.gDefault.set(((MethodInvocationStub)this.getInvocationStub()).getProxyInterface());
        } else if (ActivityManagerNative.gDefault.type() == Singleton.TYPE) {
            Object gDefault = ActivityManagerNative.gDefault.get();
            Singleton.mInstance.set(gDefault, ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface());
        }
        BinderInvocationStub hookAMBinder = new BinderInvocationStub((IInterface)((MethodInvocationStub)this.getInvocationStub()).getBaseInterface());
        hookAMBinder.copyMethodProxies((MethodInvocationStub)this.getInvocationStub());
        ServiceManager.sCache.get().put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")), hookAMBinder);
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        if (VirtualCore.get().isVAppProcess()) {
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGcjNAFmDjApLBcMPmcKRS9rARo/LRcqI2AgRVo="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    try {
                        return super.call(who, method, args);
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
            this.addMethodProxy(new ReplaceCallingPkgAndLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0FAgNmHh4qKQcqOW82TQRlJzAgKT02GWcaGj99NAoqLAguKmwjSFo="))));
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASxKKQc2RG4aAitsNCQgKS5SVg==")), 0));
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcKmgVPC9hJwo/IzwMMWkxNCpsJyg5Ki4uCA==")), 0));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJpJwo7Iz42Um8FBis="))));
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4YCGgKNAJiHiAgKAUqDW8VHi9rJCg5LRcqI2AgRVo=")), 0));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaIAJoHh45KS0MPn0VGgRqASQ0IxgcIQ=="))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKGowFgZrNzA2KSs+KG8FPBFsHjxF"))));
            this.addMethodProxy(new ReplaceCallingPkgAndLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2W2owFiliDgoJKgdXPWoaAi9vNyhIKhgEKGkgNDVuDgod"))));
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQYAKAgqLW8jGiw="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    IBinder token = (IBinder)args[0];
                    VActivityManager.get().onActivityResumed(token);
                    return super.call(who, method, args);
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQYWKAgqLmoVND9rASxF"))){

                @Override
                public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
                    IBinder token = (IBinder)args[0];
                    VActivityManager.get().onActivityDestroy(token);
                    return super.afterCall(who, method, args, result);
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQVBhNAYCKAguD2wgAgNqAQYb"))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    if (args[0] instanceof Uri && args[0].toString().equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGwFNCZmVgU1Oi42PW8zGgJqEQYbL18AJX0FMDVvDgo7LAQuDmwzNDJoHgooJxdfVg==")))) {
                        return VirtualCore.get().checkSelfPermission(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Owg+CGUVOCthJw02IxcMKG8jLANsJx4cLCocXmk2GlRmD1kAJDwqE2QhNApkDx4fLCwuVg==")), VirtualCore.get().isExtPackage()) ? 0 : -1;
                    }
                    return 0;
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGUaLCBlDiggKQg+MWUwLFo="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    IBinder token = (IBinder)args[0];
                    VActivityManager.get().onFinishActivity(token);
                    return super.call(who, method, args);
                }

                @Override
                public boolean isEnable() {
                    return 5.isAppProcess();
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGUaLCBlDiggKQg+MWUwLBFrNyQaLC4YCmcFSFo="))){

                @Override
                public Object call(Object who, Method method, Object ... args) {
                    IBinder token = (IBinder)args[0];
                    return VActivityManager.get().finishActivityAffinity(6.getAppUserId(), token);
                }

                @Override
                public boolean isEnable() {
                    return 6.isAppProcess();
                }
            });
        }
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgZiDlkgOy0MDmkzGgRnJx4/IwYiJ30FFjBlNApF"))));
    }

    @Override
    public boolean isEnvBad() {
        return ActivityManagerNative.getDefault.call(new Object[0]) != ((MethodInvocationStub)this.getInvocationStub()).getProxyInterface();
    }

    static final class BroadcastIntentWithFeature
    extends MethodProxies.BroadcastIntent {
        BroadcastIntentWithFeature() {
        }

        @Override
        public final String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj0MD2sVMCl9ASggIQcYLmkjMAZnJx4/IwYiJ30FFjBlNApF"));
        }

        @Override
        public final Object call(Object who, Method method, Object[] args) throws Throwable {
            Intent v1 = (Intent)args[2];
            v1.setDataAndType(v1.getData(), (String)args[3]);
            Intent v1_1 = this.handleIntent(v1);
            if (v1_1 != null) {
                args[2] = v1_1;
                if (args[8] instanceof String || args[8] instanceof String[]) {
                    args[8] = null;
                }
                return method.invoke(who, args);
            }
            return 0;
        }

        @Override
        public final boolean isEnable() {
            return BroadcastIntentWithFeature.isAppProcess();
        }
    }
}

