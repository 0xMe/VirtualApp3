/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.content.Context
 */
package mirror.android.app;

import android.app.Notification;
import android.content.Context;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class NotificationL {
    public static Class<?> TYPE = RefClass.load(NotificationL.class, Notification.class);

    public static class Builder {
        public static Class<?> TYPE = RefClass.load(Builder.class, Notification.Builder.class);
        @MethodParams(value={Context.class, Notification.class})
        public static RefStaticMethod<Notification> rebuild;
    }
}

