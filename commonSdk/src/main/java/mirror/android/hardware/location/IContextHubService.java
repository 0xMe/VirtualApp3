/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.hardware.location;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IContextHubService {
    public static Class<?> TYPE = RefClass.load(IContextHubService.class, StringFog.decrypt("EgsWBAoHO10LDgAUHg4cFkseGQYPKxoMAVw5KgAABwAKAi0bPSAGHQQZCgo="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10LDgAUHg4cFkseGQYPKxoMAVw5KgAABwAKAi0bPSAGHQQZCgpKIBEHFA=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

