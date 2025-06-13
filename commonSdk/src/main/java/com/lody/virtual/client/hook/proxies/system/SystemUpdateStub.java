/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.PersistableBundle
 */
package com.lody.virtual.client.hook.proxies.system;

import android.os.Bundle;
import android.os.ISystemUpdateManager;
import android.os.PersistableBundle;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import mirror.android.os.ServiceManager;

public class SystemUpdateStub
extends BinderInvocationProxy {
    private static final String SERVICE_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YKWwFNCNsJzAsKBciLmkjSFo="));

    public SystemUpdateStub() {
        super(new EmptySystemUpdateManagerImpl(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YKWwFNCNsJzAsKBciLmkjSFo=")));
    }

    @Override
    public void inject() throws Throwable {
        if (ServiceManager.checkService.call(SERVICE_NAME) == null) {
            super.inject();
        }
    }

    static class EmptySystemUpdateManagerImpl
    extends ISystemUpdateManager.Stub {
        EmptySystemUpdateManagerImpl() {
        }

        @Override
        public Bundle retrieveSystemUpdateInfo() {
            Bundle info = new Bundle();
            info.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKNAM=")), 0);
            return info;
        }

        @Override
        public void updateSystemUpdateInfo(PersistableBundle data) {
        }
    }
}

