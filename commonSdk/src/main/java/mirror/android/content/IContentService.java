/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.content;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IContentService {
    public static Class<?> TYPE = RefClass.load(IContentService.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXSwxGQsaOh0XPBcCHwYNFg=="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXSwxGQsaOh0XPBcCHwYNFkEhAhAM"));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

