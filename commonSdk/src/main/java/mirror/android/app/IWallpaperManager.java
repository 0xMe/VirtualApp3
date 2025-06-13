/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.app;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.android.app.IUsageStatsManager;

public class IWallpaperManager {
    public static Class<?> TYPE = RefClass.load(IWallpaperManager.class, StringFog.decrypt("EgsWBAoHO10CHwJeIDgPHwkCFxULLT4CARMXDB0="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(IUsageStatsManager.Stub.class, StringFog.decrypt("EgsWBAoHO10CHwJeIDgPHwkCFxULLT4CARMXDB1KIBEHFA=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

