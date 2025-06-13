/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.service.persistentdata;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IPersistentDataBlockService {
    public static Class<?> TYPE = RefClass.load(IPersistentDataBlockService.class, StringFog.decrypt("EgsWBAoHO10QCgAGAAwLXRUXBBYHLAcGAQYUCBsPXSwiExcdNgAXChwELQ4aEiceGQYFDBYRGRsTDA=="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10QCgAGAAwLXRUXBBYHLAcGAQYUCBsPXSwiExcdNgAXChwELQ4aEiceGQYFDBYRGRsTDEs9BxAQ"));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

