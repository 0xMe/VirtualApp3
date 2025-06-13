/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.oem.vivo;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class ISystemDefenceManager {
    public static Class<?> TYPE = RefClass.load(ISystemDefenceManager.class, StringFog.decrypt("BQwEGUsPLwNNHAsDHQoDFwAUEwsNOl0qPAsDHQoDNwAUEwsNOj4CARMXDB0="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("BQwEGUsPLwNNHAsDHQoDFwAUEwsNOl0qPAsDHQoDNwAUEwsNOj4CARMXDB1KIBEHFA=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

