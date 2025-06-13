/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.permissionmgr;

import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import mirror.android.app.ActivityThread;

public class PermissionManagerStub
extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
    public PermissionManagerStub() {
        super(new MethodInvocationStub<IInterface>(ActivityThread.sPermissionManager.get()));
    }

    @Override
    public void inject() throws Throwable {
        try {
            VirtualCore.get().getPackageManager().getAllPermissionGroups(0);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        IInterface hooked = (IInterface)((MethodInvocationStub)this.getInvocationStub()).getProxyInterface();
        ActivityThread.sPermissionManager.set(hooked);
        try {
            Object mPermissionManager = Reflect.on(VirtualCore.getPM()).field(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU6M28jEi9hJygzKi0YUm4jMDdrJyg5"))).get();
            Object packagemanager = VirtualCore.getPM();
            if (BuildCompat.isS()) {
                packagemanager = mPermissionManager;
                mPermissionManager = Reflect.on(mPermissionManager).field(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU6M28jEi9hJygzKi0YUm4jMDdrJyg5"))).get();
            }
            if (mPermissionManager != hooked) {
                Reflect.on(packagemanager).set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwU6M28jEi9hJygzKi0YUm4jMDdrJyg5")), hooked);
            }
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
        BinderInvocationStub hookBinder = new BinderInvocationStub((IInterface)((MethodInvocationStub)this.getInvocationStub()).getBaseInterface());
        hookBinder.copyMethodProxies((MethodInvocationStub)this.getInvocationStub());
        hookBinder.replaceService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmoVAgNhJAY1Kj1XM2oVSFo=")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGIzBkxiASw3KQgqL2wjNCZsJTAZLRgcIWIITSxlJzAuKRgACA=="))){

            @Override
            public Object call(Object who, Method method, Object ... args) {
                return 0;
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtoJFkCKAguD2wgAgNqAQYbKTw2LH0KRSJuDF0iLAccJ2UzNCY="))){

            @Override
            public Object call(Object who, Method method, Object ... args) {
                return 0;
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGcFNARgDgYpIy0cDW8VSFo="))){

            @Override
            public Object call(Object who, Method method, Object ... args) {
                return true;
            }
        });
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQUxiASw3KQgqL2wjNCY="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                return VPackageManager.get().checkPermission((String)args[0], (String)args[1], (Integer)args[2]);
            }
        });
        this.addMethodProxy(new ReplaceFirstPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki5fD2wVHixpJBo1LCwuPWogGitsJCwRLhcML2MFNDZvDlkdJBhbCmoFGgRrEQI0"))));
    }

    @Override
    public boolean isEnvBad() {
        return false;
    }
}

