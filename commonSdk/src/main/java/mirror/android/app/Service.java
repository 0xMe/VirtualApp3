/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 */
package mirror.android.app;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;

public class Service {
    public static Class<?> TYPE = RefClass.load(Service.class, android.app.Service.class);
    @MethodReflectParams(value={"android.content.Context", "android.app.ActivityThread", "java.lang.String", "android.os.IBinder", "android.app.Application", "java.lang.Object"})
    public static RefMethod<Void> attach;
}

