/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 */
package com.lody.virtual.client.hook.proxies.telephony;

import android.os.Build;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import java.lang.reflect.Method;
import mirror.com.android.internal.telephony.ITelephonyRegistry;

public class TelephonyRegistryStub
extends BinderInvocationProxy {
    public TelephonyRegistryStub() {
        super(ITelephonyRegistry.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRguDmgaICBgJFkZOj4uPWkFLANvHgo0")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGIzBl5mDiwpLy4uMWowBi9lJxo6JT5fO2AwJCBuHF0iLAccJ2UzNCY="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGIzBg9hESQ1Iz42LW8VLANvER4qIT0uJmEgNDVvASA9Ki4uKmwhLAZrERoyJAcMHW8FMCJpJyw0Kj5SVg=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtoJFkPLAcuL24KRS9sHiwaLD4cD2UgBiRsNDwuLzwiI2wgMD9qJygb"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCY="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCZuJAYgKRUMLGkjMAZ9ER46KghSVg=="))));
        this.addMethodProxy(new ReplaceSequencePkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCZqNB4qOy4MOGoFAgRqAQogKS5SVg==")), 1){

            @Override
            public boolean beforeCall(Object who, Method method, Object ... args) {
                if (Build.VERSION.SDK_INT >= 17 && 1.isFakeLocationEnable()) {
                    for (int i = args.length - 1; i > 0; --i) {
                        if (!(args[i] instanceof Integer)) continue;
                        int events = (Integer)args[i];
                        events ^= 0x400;
                        args[i] = events ^= 0x10;
                        break;
                    }
                }
                return super.beforeCall(who, method, args);
            }
        });
    }
}

