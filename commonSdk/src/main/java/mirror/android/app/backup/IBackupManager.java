/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.app.backup;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IBackupManager {
    public static Class<?> TYPE = RefClass.load(IBackupManager.class, StringFog.decrypt("EgsWBAoHO10CHwJeCw4NGBACWCwsPhAIGgI9CAEPFAAA"));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10CHwJeCw4NGBACWCwsPhAIGgI9CAEPFAAAUjYaKhE="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

