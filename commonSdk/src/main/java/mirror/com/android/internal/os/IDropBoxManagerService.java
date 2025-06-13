/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IDropBoxManagerService {
    public static Class<?> TYPE = RefClass.load(IDropBoxManagerService.class, StringFog.decrypt("EAofWAQAOwEMBhZeAAEaFhccFwlAMABNJjYCBh8sHB0/FwsPOBYRPBcCHwYNFg=="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EAofWAQAOwEMBhZeAAEaFhccFwlAMABNJjYCBh8sHB0/FwsPOBYRPBcCHwYNFkEhAhAM"));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

