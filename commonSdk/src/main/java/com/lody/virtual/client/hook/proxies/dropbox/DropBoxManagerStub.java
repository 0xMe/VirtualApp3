/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.DropBoxManager
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.dropbox;

import android.os.DropBoxManager;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.com.android.internal.os.IDropBoxManagerService;

public class DropBoxManagerStub
extends BinderInvocationProxy {
    public DropBoxManagerStub() {
        super(IDropBoxManagerService.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRcMD28FFiVnEVRF")));
    }

    @Override
    public void inject() throws Throwable {
        super.inject();
        DropBoxManager dm = (DropBoxManager)VirtualCore.get().getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRcMD28FFiVnEVRF")));
        try {
            mirror.android.os.DropBoxManager.mService.set(dm, (IInterface)((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIjNDBmHDA2LBguIQ==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIjNDBmHDA2LBguIX0FLAZqHzg/KgcMI30zLD9vDlkd")), null));
    }
}

