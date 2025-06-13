/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.proxies.input;

import android.annotation.TargetApi;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.proxies.input.MethodProxies;
import mirror.com.android.internal.view.inputmethod.InputMethodManager;

@Inject(value=MethodProxies.class)
@TargetApi(value=16)
public class InputMethodManagerStub
extends BinderInvocationProxy {
    public InputMethodManagerStub() {
        super(InputMethodManager.mService.get(VirtualCore.get().getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")))), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")));
    }

    @Override
    public void inject() throws Throwable {
        Object inputMethodManager = this.getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")));
        InputMethodManager.mService.set(inputMethodManager, (IInterface)((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
        ((BinderInvocationStub)this.getInvocationStub()).replaceService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgJmAQoNKAg2Mm8FBg5qDjA/"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAVBjd9NFE/KBUcDmowGgZ9ASg/IwgAIGgaGjZqEVRF"))));
    }

    @Override
    public boolean isEnvBad() {
        Object inputMethodManager = this.getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKGwaMB9gDjAgKRdfPg==")));
        return InputMethodManager.mService.get(inputMethodManager) != ((BinderInvocationStub)this.getInvocationStub()).getBaseInterface();
    }
}

