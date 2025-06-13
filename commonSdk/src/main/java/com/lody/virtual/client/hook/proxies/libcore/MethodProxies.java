/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.hook.proxies.libcore;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import mirror.libcore.io.Os;

class MethodProxies {
    MethodProxies() {
    }

    static class Stat
    extends MethodProxy {
        private static Field st_uid;

        Stat() {
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            int uid = (Integer)st_uid.get(result);
            if (uid == VirtualCore.get().myUid()) {
                st_uid.set(result, Stat.getBaseVUid());
            }
            return result;
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wFSFo="));
        }

        static {
            try {
                Method stat = Os.TYPE.getMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wFSFo=")), String.class);
                Class<?> StructStat = stat.getReturnType();
                st_uid = StructStat.getDeclaredField(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qH2wVAiw=")));
                st_uid.setAccessible(true);
            }
            catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    static class GetsockoptUcred
    extends MethodProxy {
        GetsockoptUcred() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLG8zGiljJB4sLBYMP2oVGiw="));
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            Reflect ucred;
            int uid;
            if (result != null && (uid = ((Integer)(ucred = Reflect.on(result)).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgYPA==")))).intValue()) == VirtualCore.get().myUid()) {
                ucred.set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgYPA==")), GetsockoptUcred.getBaseVUid());
            }
            return result;
        }
    }

    static class GetUid
    extends MethodProxy {
        GetUid() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGwVAiw="));
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            int uid = (Integer)result;
            return NativeEngine.onGetUid(uid);
        }
    }

    static class Getpwnam
    extends MethodProxy {
        Getpwnam() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLG8KPCZ9Dl1F"));
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            Reflect pwd;
            int uid;
            if (result != null && (uid = ((Integer)(pwd = Reflect.on(result)).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhcmH2wVAiw=")))).intValue()) == VirtualCore.get().myUid()) {
                pwd.set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhcmH2wVAiw=")), VClient.get().getVUid());
            }
            return result;
        }
    }

    static class Fstat
    extends Stat {
        Fstat() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT02LGsaMFo="));
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            Reflect pwd;
            int uid;
            if (result != null && (uid = ((Integer)(pwd = Reflect.on(result)).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qH2wVAiw=")))).intValue()) == VirtualCore.get().myUid()) {
                pwd.set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qH2wVAiw=")), VClient.get().getVUid());
            }
            return result;
        }
    }

    static class Lstat
    extends Stat {
        Lstat() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixc2LGsaMFo="));
        }

        @Override
        public Object afterCall(Object who, Method method, Object[] args, Object result) throws Throwable {
            Reflect pwd;
            int uid;
            if (result != null && (uid = ((Integer)(pwd = Reflect.on(result)).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qH2wVAiw=")))).intValue()) == VirtualCore.get().myUid()) {
                pwd.set(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qH2wVAiw=")), VClient.get().getVUid());
            }
            return result;
        }
    }
}

