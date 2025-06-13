/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.com.android.internal.appwidget;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IAppWidgetService {
    public static Class<?> TYPE = RefClass.load(IAppWidgetService.class, StringFog.decrypt("EAofWAQAOwEMBhZeAAEaFhccFwlAPgMTGBsUDgoaXSwzBhU5NhcECgYjDB0YGgYX"));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EAofWAQAOwEMBhZeAAEaFhccFwlAPgMTGBsUDgoaXSwzBhU5NhcECgYjDB0YGgYXUjYaKhE="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

