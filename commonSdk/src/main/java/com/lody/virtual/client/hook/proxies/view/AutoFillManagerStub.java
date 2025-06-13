/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Application
 *  android.content.ComponentName
 *  android.util.Log
 */
package com.lody.virtual.client.hook.proxies.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import mirror.android.app.ActivityThread;
import mirror.android.view.IAutoFillManager;

public class AutoFillManagerStub
extends BinderInvocationProxy {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRkJCwwLS5SVg=="));
    private static final String AUTO_FILL_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGozOC9gHlFF"));

    public AutoFillManagerStub() {
        super(IAutoFillManager.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGozOC9gHlFF")));
    }

    @Override
    @SuppressLint(value={"WrongConstant"})
    public void inject() throws Throwable {
        super.inject();
        try {
            Object AutoFillManagerInstance;
            Object mainThread = ActivityThread.currentActivityThread.call(new Object[0]);
            VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhY+I2wFGghjDlEoIgciDm4jEitsMjA/KhgLJGMKRS1uDiw9OD4mO2oFBgplDgo0OwcLMQ==")) + mainThread);
            if (mainThread != null) {
                Application application = ActivityThread.mInitialApplication.get(mainThread);
                VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBUhDQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhY+I2wFGghjDlEoIgciDm4jEitsMjA/KhgLJGMKRS1uDiw9OD5bDmwVHgVrNzgZJQcYJXszSFo=")) + application);
            }
            if ((AutoFillManagerInstance = this.getContext().getSystemService(AUTO_FILL_NAME)) == null) {
                throw new NullPointerException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRiARo6Kgg+Kn0gLyNvAS8pKRcAKGVSBlo=")));
            }
            Object AutoFillManagerProxy = ((BinderInvocationStub)this.getInvocationStub()).getProxyInterface();
            if (AutoFillManagerProxy == null) {
                throw new NullPointerException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRkHgocLwcXJGMFNyNsNwobKToqVg==")));
            }
            Field AutoFillManagerServiceField = AutoFillManagerInstance.getClass().getDeclaredField(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU2M28gOC99JDBF")));
            AutoFillManagerServiceField.setAccessible(true);
            AutoFillManagerServiceField.set(AutoFillManagerInstance, AutoFillManagerProxy);
        }
        catch (Throwable tr) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRkJCwwLSo6I2AwHiBpJzMpLy1fCGUgEQQ=")), (Throwable)tr);
            return;
        }
        this.addMethodProxy(new ReplacePkgAndComponentProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMF5iASgpKQdfDg=="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                1.replaceFirstUserId(args);
                return super.call(who, method, args);
            }
        });
        this.addMethodProxy(new ReplacePkgAndComponentProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtoJywAKAgqLm4gRQZkJyg6KT4YKWAzSFo="))));
        this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2A2gaFj5jDig/IAcYOW4VOCtrEVRF"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGMzHi9iDlkg"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtlJFEzKAcYLg=="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtpJDApIy0cDW8VSFo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGUaLCBpJDApIy0cDW8VSFo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRpJDApIy0cDW8VSFo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaNAZjHjA2LBccP24gBi9lJxofLhc2CWAVFlo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLH0FJANlJCAoKhcuOW4FJFo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiDB4tKj0MPmYgGgZlJyQaLAgEUmIFMDFvDiwuLAhSVg=="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2A2gaFj5jDig/Oy4MKmozNARvESgv"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2A2gaFj5jDig/IAcYOW4VOCtrEVRF"))){

            @Override
            public boolean beforeCall(Object who, Method method, Object ... args) {
                MethodParameterUtils.replaceLastAppPkg(args);
                return super.beforeCall(who, method, args);
            }
        });
    }

    public static void disableAutoFill(Object object) {
        try {
            if (object == null) {
                throw new NullPointerException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRiARo6Kgg+Kn0gLyNvAS8pKRcAKGVSBlo=")));
            }
            Field AutoFillManagerServiceField = object.getClass().getDeclaredField(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU2M28gOC99JDBF")));
            AutoFillManagerServiceField.setAccessible(true);
            AutoFillManagerServiceField.set(object, null);
        }
        catch (Throwable tr) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JgcuLGoxOC9gHlENLwcYOWkFGgRkJCwwLSo6I2AwHiBpJzMpLy1fCGUgEQQ=")), (Throwable)tr);
            return;
        }
    }

    static class ReplacePkgAndComponentProxy
    extends ReplaceLastPkgMethodProxy {
        ReplacePkgAndComponentProxy(String name) {
            super(name);
        }

        @Override
        public boolean beforeCall(Object who, Method method, Object ... args) {
            this.replaceLastAppComponent(args, ReplacePkgAndComponentProxy.getHostPkg());
            return super.beforeCall(who, method, args);
        }

        private void replaceLastAppComponent(Object[] args, String hostPkg) {
            int index = ArrayUtils.indexOfLast(args, ComponentName.class);
            if (index != -1) {
                ComponentName orig = (ComponentName)args[index];
                ComponentName newComponent = new ComponentName(hostPkg, orig.getClassName());
                args[index] = newComponent;
            }
        }
    }
}

