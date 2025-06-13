/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.lody.virtual.client.hook.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.ServiceLocalManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.helper.utils.Reflect;
import java.io.FileDescriptor;
import java.lang.reflect.Method;
import mirror.RefStaticMethod;
import mirror.android.os.ServiceManager;

public class BinderInvocationStub
extends MethodInvocationStub<IInterface>
implements IBinder {
    private static final String TAG = BinderInvocationStub.class.getSimpleName();
    private IBinder mBaseBinder = this.getBaseInterface() != null ? ((IInterface)this.getBaseInterface()).asBinder() : null;

    public BinderInvocationStub(RefStaticMethod<IInterface> asInterfaceMethod, IBinder binder) {
        this(BinderInvocationStub.asInterface(asInterfaceMethod, binder));
    }

    public BinderInvocationStub(Class<?> stubClass, IBinder binder) {
        this(BinderInvocationStub.asInterface(stubClass, binder));
    }

    public BinderInvocationStub(IInterface mBaseInterface) {
        super(mBaseInterface);
        this.addMethodProxy(new AsBinder());
    }

    private static IInterface asInterface(RefStaticMethod<IInterface> asInterfaceMethod, IBinder binder) {
        if (asInterfaceMethod == null || binder == null) {
            return null;
        }
        return asInterfaceMethod.call(binder);
    }

    private static IInterface asInterface(Class<?> stubClass, IBinder binder) {
        try {
            if (stubClass == null) {
                return null;
            }
            if (binder == null) {
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxcqKGkjQQZrDTw6KgcuJksaMCBpJCQ+LAgfJGgzAgRoASgbDV9aL2wzFgRvMyc3Ki0qI2shLCR9ASgpPghSVg==")) + stubClass));
                return null;
            }
            Method asInterface = stubClass.getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgc2XGogMCthNDw7Ly0MVg==")), IBinder.class);
            return (IInterface)asInterface.invoke(null, binder);
        }
        catch (Exception e) {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxcqKGkjQQZrDTw6KgcuJksVSFo=")) + stubClass.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mzo6E2saNANiDQU8")) + e));
            return null;
        }
    }

    public void replaceService(String name) {
        if (this.mBaseBinder != null) {
            ServiceManager.sCache.get().put(name, this);
            ServiceLocalManager.addService(name, this);
        }
    }

    public String getInterfaceDescriptor() throws RemoteException {
        return this.mBaseBinder.getInterfaceDescriptor();
    }

    public Context getContext() {
        return VirtualCore.get().getContext();
    }

    public boolean pingBinder() {
        return this.mBaseBinder.pingBinder();
    }

    public boolean isBinderAlive() {
        return this.mBaseBinder.isBinderAlive();
    }

    public IInterface queryLocalInterface(String descriptor) {
        return (IInterface)this.getProxyInterface();
    }

    public void dump(FileDescriptor fd, String[] args) throws RemoteException {
        this.mBaseBinder.dump(fd, args);
    }

    @TargetApi(value=13)
    public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {
        this.mBaseBinder.dumpAsync(fd, args);
    }

    public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return this.mBaseBinder.transact(code, data, reply, flags);
    }

    public void linkToDeath(IBinder.DeathRecipient recipient, int flags) throws RemoteException {
        this.mBaseBinder.linkToDeath(recipient, flags);
    }

    public boolean unlinkToDeath(IBinder.DeathRecipient recipient, int flags) {
        return this.mBaseBinder.unlinkToDeath(recipient, flags);
    }

    public IBinder getBaseBinder() {
        return this.mBaseBinder;
    }

    public IBinder getExtension() throws RemoteException {
        try {
            Object result = Reflect.on(this.mBaseBinder).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAaRQZiDlkpKQdfDg=="))).get();
            return (IBinder)result;
        }
        catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof RemoteException) {
                throw (RemoteException)cause;
            }
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQ/LRcqPWowBi9lJxpF")), cause);
        }
    }

    private final class AsBinder
    extends MethodProxy {
        private AsBinder() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgc2HGUVBixiASxF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return BinderInvocationStub.this;
        }
    }
}

