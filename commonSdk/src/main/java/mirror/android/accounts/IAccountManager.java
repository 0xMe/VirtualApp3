/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.accounts;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IAccountManager {
    public static Class<?> TYPE = RefClass.load(IAccountManager.class, StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGz8RBw4JFhc="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGz8RBw4JFhdWJREbPQ=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

