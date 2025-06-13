/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 */
package com.lody.virtual.client.hook.proxies.user;

import android.annotation.TargetApi;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import java.util.Collections;
import mirror.android.content.pm.UserInfo;
import mirror.android.os.IUserManager;

@TargetApi(value=17)
public class UserManagerStub
extends BinderInvocationProxy {
    public UserManagerStub() {
        super(IUserManager.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo=")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMaIAJgHgY5Lwg2MW8FMF9rDjA/KS4YJWYaGipsNyxF"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJgHgY5Lwg2MW8FMF9rDjA/KS4YJWYaGipsNyxF"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaIAJgHgY5Lwg2MW8FMF9rDjA/KS4YJWYaGipsNyxKKQdfXGwjNCY="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2BW8zNARuDlkoKi0qCWkjBlo="))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2Am8jGi5jDlE/"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2BW8zNARuDlkoKi0qCWwjMC19JAoWLC4EKX0gEiBuEVRF"))));
        this.addMethodProxy(new ReplaceLastUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2RGsVBjdiJDAwOxguDWkVLCRrAVRF"))));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFiViNAYoKAYmOWoVGiZvEVRF")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthMgY5Ki0YVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthMgY2KD1fVg==")), UserInfo.ctor.newInstance(0, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqDWUVBlo=")), UserInfo.FLAG_PRIMARY.get())));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFNC59ATAoLBU6LWkgAgZkNyg6KgcMI30jFixsJB48")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGAFNC59ATAoLBU6LWkgAgZkNyg6KgcMI30jFixsJB48")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtpNDApLBguMW4KBi9lJxo6")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthNyhF")), Collections.singletonList(UserInfo.ctor.newInstance(0, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqDWUVBlo=")), UserInfo.FLAG_PRIMARY.get()))));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtuASg/Iz5SVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtpESw1KD0cCGkhHiVsMig6LhcMVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFiViNAYoKAgqVg==")), Collections.EMPTY_LIST));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthMjA2LwcuCGkjBlo=")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtuASg/Iz5SVg==")), false));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthMlk7KgcMVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthMgY5Ki0YVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGMVMCxoDh4qKAVXOW8VQS1rASwRKS4AImMKTSBlJ1RF")), false));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthNSw/Iy42KGwjAgZqAQYbKT5SVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthNSw/Iy42KGwjAgZqAQYb")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+KmUxPAViASggID1fKGEzGiRrDiwaLD4cVg==")), true));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtpNDApLBguMW4KBitrHDw5LD4iI2AaLFo=")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFi9gDiAqLQYML2kgRVo=")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+KWMjJANiDzApKAguAmkgAgZsNx4qKggYKWAzSFo=")), false));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthMlk7KgcMVg==")), ""));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGczNCtiHCA5Ly1fLW8aBg9sHiwaLD4cDw==")), null));
    }
}

