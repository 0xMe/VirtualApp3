/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.IInterface
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.proxies.bluetooth;

import android.os.Build;
import android.os.IInterface;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.FixAttributionSourceMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultBinderMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import mirror.android.bluetooth.IBluetooth;

public class BluetoothStub
extends BinderInvocationProxy {
    private static final String SERVER_NAME = Build.VERSION.SDK_INT >= 17 ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4EI2gaMCVgJwo0Ji1XOW8VQS1rDgpF")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4EI2gaMCVgJwo0"));

    public BluetoothStub() {
        super(IBluetooth.Stub.asInterface, SERVER_NAME);
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new GetAddress());
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcwAgNmHjA3Jy1fDmkVLC1jARosLS4EJ2IbODVsJDgiKT4AD2MzGiZnATg2JS0mLm4FSFo="))));
        if (BuildCompat.isS()) {
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHis="))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitoNB4RLAg2DWYFNCZlNygqKghSVg=="))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiAVRF"))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVMCxhNDApIy5SVg=="))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIjJCNiAVRF"))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4cW2sVLAZgJywZOz0ML2kgBlo="))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitlNFE/"))));
            this.addMethodProxy(new FixAttributionSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiDCwoKAhSVg=="))));
        } else {
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHis="))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiAVRF"))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitoNB4RLAg2DWYFNCZlNygqKghSVg=="))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtlNFE/JwgmKmYFNAVlNCxF"))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitlNFE/"))));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiDCwoKAhSVg=="))));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.addMethodProxy(new ResultBinderMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASwRKBciKmUzGgQ="))){

                @Override
                public InvocationHandler createProxy(final IInterface base) {
                    return new InvocationHandler(){

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVMCxhNDApIy5SVg==")).equals(method.getName())) {
                                String mac = getDeviceConfig().bluetoothMac;
                                VDeviceConfig config = getDeviceConfig();
                                if (config.enable && !TextUtils.isEmpty((CharSequence)(getDeviceConfig().bluetoothMac))) {
                                    return mac;
                                }
                            }
                            return method.invoke(base, args);
                        }
                    };
                }
            });
        }
    }

    private static class GetAddress
    extends ReplaceLastPkgMethodProxy {
        public GetAddress() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVMCxhNDApIy5SVg==")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String mac;
            VDeviceConfig config = GetAddress.getDeviceConfig();
            if (config.enable && !TextUtils.isEmpty((CharSequence)(mac = GetAddress.getDeviceConfig().bluetoothMac))) {
                return mac;
            }
            return super.call(who, method, args);
        }
    }
}

