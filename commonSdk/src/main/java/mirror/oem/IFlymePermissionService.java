/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.oem;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IFlymePermissionService {
    public static Class<?> TYPE = RefClass.load(IFlymePermissionService.class, StringFog.decrypt("HgAbDBBALBYAGgAZHRZAOiMeDwgLDxYRAhsDGgYBHTYXBBMHPBY="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("HgAbDBBALBYAGgAZHRZAOiMeDwgLDxYRAhsDGgYBHTYXBBMHPBZHPAYFCw=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

