/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.location.LocationManager
 *  android.os.Binder
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.proxies.location;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.proxies.location.MethodProxies;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.ReflectException;
import java.lang.reflect.Method;
import mirror.android.location.GeocoderParams;
import mirror.android.location.GpsStatus;
import mirror.android.location.ILocationManager;
import mirror.android.location.LocationManager;
import mirror.android.os.ServiceManager;

@Inject(value=MethodProxies.class)
public class LocationManagerStub
extends MethodInvocationProxy<BinderInvocationStub> {
    public LocationManagerStub() {
        super(new BinderInvocationStub(LocationManagerStub.getInterface()));
    }

    private static IInterface getInterface() {
        IBinder base = ServiceManager.getService.call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAOWsaMC9gJFlF")));
        if (base instanceof Binder) {
            try {
                return (IInterface)Reflect.on(base).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwYYQGozLDdmHgY1KjtXOW8VQS1rDgpF")));
            }
            catch (ReflectException e) {
                e.printStackTrace();
            }
        }
        return ILocationManager.Stub.asInterface.call(base);
    }

    @Override
    public void inject() {
        android.location.LocationManager locationManager = (android.location.LocationManager)this.getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAOWsaMC9gJFlF")));
        IInterface base = LocationManager.mService.get(locationManager);
        if (base instanceof Binder) {
            Reflect.on(base).set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwYYQGozLDdmHgY1KjtXOW8VQS1rDgpF")), ((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
        }
        LocationManager.mService.set(locationManager, (IInterface)((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
        ((BinderInvocationStub)this.getInvocationStub()).replaceService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAOWsaMC9gJFlF")));
    }

    @Override
    public boolean isEnvBad() {
        return false;
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        if (Build.VERSION.SDK_INT >= 23) {
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGQFNANmHyQqKi4+MWkzGgQ="))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtuHjApLBYmKG8KHi9rESg5"))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQFNANmHyQqKi4+MWkzGgR9EQYqLRcqI2AgRVo="))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFlFiASggOxguDWUVLCxrDgpTLD42O2YaGipsN1RF"))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQFNANmHyQqKi4+MWkzGgRjARosLS4EJ2IVSFo="))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFlFiASggOxguDWUVLCxrDgpLLC4+JmAaLC8="))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQFNANmHyQqKi4+MWkzGgRkJCwsKgcuDw=="))));
            this.addMethodProxy(new ReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFlFiASggOxguDWUVLCxrDgoQKgg+CmYFNFo="))));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGAwIANoDjA7Iy4MKGkjPCtlNCxTIxc2CmIKRSBlN1RF")), true));
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGAwIANoNCAuKQc6OWUzLCVlNQ4gKT02O2IgLBFvASw9Ly4qJ2wzSFo=")), true));
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtqJyQpIgcMOWoKGgRrAQ4gLC0qAmMFND9uDh4uLBhSVg==")), 0));
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtqJyQpIj0iLGwjEjdvER4cLCwIJ2EjNCRuJApAKi0YCmsFBj9vJ1RF")), 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uL2wVNANmHDg/Ki0+PW8VAis=")), 0));
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtqJDA1KD0MDm4FGlo=")), 0));
        }
        if (Build.VERSION.SDK_INT <= 16) {
            this.addMethodProxy(new MethodProxies.GetLastKnownLocation());
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGcKFiVnHgY3KQg2IWYjOCtsNCxF")), 0));
        }
        if (Build.VERSION.SDK_INT <= 16) {
            this.addMethodProxy(new MethodProxies.RequestLocationUpdatesPI());
            this.addMethodProxy(new MethodProxies.RemoveUpdatesPI());
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.addMethodProxy(new MethodProxies.RequestLocationUpdates());
            this.addMethodProxy(new MethodProxies.RemoveUpdates());
        }
        this.addMethodProxy(new MethodProxies.IsProviderEnabled());
        this.addMethodProxy(new MethodProxies.GetBestProvider());
        if (Build.VERSION.SDK_INT >= 17) {
            this.addMethodProxy(new MethodProxies.GetLastLocation());
            this.addMethodProxy(new MethodProxies.AddGpsStatusListener());
            this.addMethodProxy(new MethodProxies.RemoveGpsStatusListener());
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGIjEit9DFEzIy42PW8VGgQ=")), 0));
            this.addMethodProxy(new FakeReplaceLastPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtoNF0/LwVbMWoKBitlNyg5")), 0));
        }
        if (Build.VERSION.SDK_INT >= 24) {
            if (BuildCompat.isS()) {
                this.addMethodProxy(new ReplaceFirstPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASwTKj4qL2IKBjdvHig6JT4+KGAaMCRpJAJF"))));
            } else {
                this.addMethodProxy(new MethodProxies.RegisterGnssStatusCallback());
            }
            this.addMethodProxy(new MethodProxies.UnregisterGnssStatusCallback());
        }
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2Am8jGj5jDgo/IzsMDm4jRSRrASxILD0MXGEgLDU="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2QGozLDdmHgY1KjsMDm4jRSRrASxILD0MXGEgLDU="))));
        if (BuildCompat.isQ()) {
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGIFGil9AQozKi0YEW8FMAZsNwYdLAguCGoFBj9lNCQfKC4YLWgFPD9gERooOz1XLG4VSFo="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    return null;
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGAaRQZhNCAOKi0qOWUzLCVlNTAcLC0qCGAgTTduASgfKC4YLWgFPD9gERooOz1XLG4VSFo="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    return null;
                }
            });
            this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGAaRQZhNCAOKi0qOWUzLCVlNTAcLC0qCGAgTTduASgfKC4YLWgFPD8="))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    return null;
                }
            });
        }
        if (BuildCompat.isR()) {
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGIFGil9AQozKi0YWm8VQSplESgvJi4ACG4FNCBlN1RF")), null));
        }
        if (BuildCompat.isS()) {
            this.addMethodProxy(new ReplaceSequencePkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASwOKi0qOWUzLCVlMjwgLC4qI2AwJBZsNzAuKRccVg==")), 2));
            this.addMethodProxy(new ReplaceSequencePkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASwOKi0qOWUzLCVlMjwgLC4qI2AwJBZsNzAuKRccVg==")), 2));
            this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uPWUaLAZiASwTKj4qL2cVPCtoDzAsLAgEJn0KNC4="))));
            this.addMethodProxy(new GetFromLocation(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAgFiVgDFE1Ly0iLmwjNCZ9NzgeLhhSVg=="))));
            this.addMethodProxy(new GetFromLocation(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAgFiVgDFE1Ly0iLmwjNCY="))));
        }
    }

    private static class GetFromLocation
    extends StaticMethodProxy {
        public GetFromLocation(String str) {
            super(str);
        }

        @Override
        public Object call(Object obj, Method method, Object ... args) throws Throwable {
            int index = ArrayUtils.indexOf(args, GpsStatus.TYPE, 0);
            if (index >= 0) {
                int mUid;
                String hostPkg = VirtualCore.get().getHostPkg();
                String mPackageName = GeocoderParams.mPackageName(args[index]);
                if (mPackageName != null && !TextUtils.equals((CharSequence)hostPkg, (CharSequence)mPackageName)) {
                    GeocoderParams.mPackageName(args[index], hostPkg);
                }
                if ((mUid = GeocoderParams.mUid(args[index])) > 0 && mUid != VirtualCore.get().myUid()) {
                    GeocoderParams.mUid(args[index], VirtualCore.get().myUid());
                }
            }
            return super.call(obj, method, args);
        }
    }

    private static class FakeReplaceLastPkgMethodProxy
    extends ReplaceLastPkgMethodProxy {
        private Object mDefValue;

        private FakeReplaceLastPkgMethodProxy(String name, Object def) {
            super(name);
            this.mDefValue = def;
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (FakeReplaceLastPkgMethodProxy.isFakeLocationEnable()) {
                return this.mDefValue;
            }
            return super.call(who, method, args);
        }
    }
}

