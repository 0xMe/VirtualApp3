/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build$VERSION
 *  android.widget.RemoteViews
 */
package com.lody.virtual.server.notification;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.RemoteViews;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.server.notification.NotificationCompatCompatV14;
import com.lody.virtual.server.notification.NotificationCompatCompatV21;
import com.lody.virtual.server.notification.NotificationFixer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import mirror.com.android.internal.R_Hide;

public abstract class NotificationCompat {
    public static final String EXTRA_TITLE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKQg2CGkjSFo="));
    public static final String EXTRA_TITLE_BIG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKQg2CGknMCpqASBF"));
    public static final String EXTRA_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kgKAgALg=="));
    public static final String EXTRA_SUB_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kpLAcuAGkgFgY="));
    public static final String EXTRA_INFO_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj0+DX0zGjBvEVRF"));
    public static final String EXTRA_SUMMARY_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kpLAdXD24gRT9nESgzKghSVg=="));
    public static final String EXTRA_BIG_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k6KQc6AGkgFgY="));
    public static final String EXTRA_PROGRESS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fM2oVGgNsJ1RF"));
    public static final String EXTRA_PROGRESS_MAX = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fM2oVGgNsJQ4sLwhSVg=="));
    public static final String EXTRA_BUILDER_APPLICATION_INFO = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7IxgmXm8VHiU="));
    static final String TAG;
    static final String SYSTEM_UI_PKG;
    private final List<Integer> sSystemLayoutResIds = new ArrayList<Integer>(10);
    private NotificationFixer mNotificationFixer;

    NotificationCompat() {
        this.loadSystemLayoutRes();
        this.mNotificationFixer = new NotificationFixer(this);
    }

    public static NotificationCompat create() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new NotificationCompatCompatV21();
        }
        return new NotificationCompatCompatV14();
    }

    private void loadSystemLayoutRes() {
        Field[] fields;
        for (Field field : fields = R_Hide.layout.TYPE.getFields()) {
            if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) continue;
            try {
                int id2 = field.getInt(null);
                this.sSystemLayoutResIds.add(id2);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    NotificationFixer getNotificationFixer() {
        return this.mNotificationFixer;
    }

    boolean isSystemLayout(RemoteViews remoteViews) {
        return remoteViews != null && this.sSystemLayoutResIds.contains(remoteViews.getLayoutId());
    }

    public Context getHostContext() {
        return VirtualCore.get().getContext();
    }

    PackageInfo getPackageInfo(String packageName) {
        try {
            return VirtualCore.get().getHostPackageManager().getPackageInfo(packageName, 0L);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    public abstract boolean dealNotification(int var1, Notification var2, String var3);

    static {
        SYSTEM_UI_PKG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmoKLANvESgeKhgYVg=="));
        TAG = NotificationCompat.class.getSimpleName();
    }
}

