/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Instrumentation
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ProviderInfo
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.app;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import mirror.MethodParams;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefMethod;
import mirror.RefObject;
import mirror.RefStaticInt;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class ActivityThread {
    public static Class<?> TYPE = RefClass.load(ActivityThread.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhY="));
    public static RefStaticObject<Boolean> USE_CACHE;
    public static RefStaticMethod<Object> currentActivityThread;
    public static RefMethod<String> getProcessName;
    public static RefStaticMethod<String> currentPackageName;
    public static RefStaticMethod<Application> currentApplication;
    public static RefMethod<Handler> getHandler;
    public static RefMethod<Object> installProvider;
    public static RefObject<Object> mBoundApplication;
    public static RefObject<Handler> mH;
    public static RefObject<Application> mInitialApplication;
    public static RefObject<Instrumentation> mInstrumentation;
    public static RefObject<Map<String, WeakReference<?>>> mPackages;
    public static RefObject<Map<IBinder, Object>> mActivities;
    public static RefObject<Map> mProviderMap;
    @MethodParams(value={IBinder.class, List.class})
    public static RefMethod<Void> performNewIntents;
    public static RefStaticObject<IInterface> sPackageManager;
    public static RefStaticObject<IInterface> sPermissionManager;
    @MethodParams(value={IBinder.class, String.class, int.class, int.class, Intent.class})
    public static RefMethod<Void> sendActivityResult;
    public static RefMethod<IBinder> getApplicationThread;
    public static RefStaticMethod<IPackageManager> getPackageManager;
    public static RefMethod<Object> getLaunchingActivity;
    public static RefMethod<Object> getPackageInfoNoCheck;
    public static RefStaticMethod<IInterface> getPermissionManager;

    public static Object installProvider(Object mainThread, Context context, ProviderInfo providerInfo, Object holder) throws Throwable {
        if (Build.VERSION.SDK_INT <= 15) {
            return installProvider.callWithException(mainThread, context, holder, providerInfo, false, true);
        }
        return installProvider.callWithException(mainThread, context, holder, providerInfo, false, true, true);
    }

    public static void handleNewIntent(Object obj, List list) {
        try {
            Method declaredMethod;
            Object currentActivityThread = ActivityThread.currentActivityThread.call(new Object[0]);
            if (currentActivityThread != null && (declaredMethod = currentActivityThread.getClass().getDeclaredMethod(StringFog.decrypt("GwQcEgkLERYUJhwEDAEa"), obj.getClass(), List.class)) != null) {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(currentActivityThread, obj, list);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void USE_CACHE(boolean useCache) {
        RefStaticObject<Boolean> obj = USE_CACHE;
        if (obj != null) {
            obj.set(useCache);
        }
    }

    public static class H {
        public static Class<?> TYPE = RefClass.load(H.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUIQ=="));
        public static RefStaticInt LAUNCH_ACTIVITY;
        public static RefStaticInt EXECUTE_TRANSACTION;
        public static RefStaticInt SCHEDULE_CRASH;
    }

    public static class AppBindData {
        public static Class<?> TYPE = RefClass.load(AppBindData.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUKB8eMQwcEiEPKxI="));
        public static RefObject<ApplicationInfo> appInfo;
        public static RefObject<Object> info;
        public static RefObject<String> processName;
        public static RefObject<ComponentName> instrumentationName;
        public static RefObject<List<ProviderInfo>> providers;
    }

    public static class ProviderKeyJBMR1 {
        public static Class<?> TYPE = RefClass.load(ProviderKeyJBMR1.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUOR0BBQwWExclOgo="));
        @MethodParams(value={String.class, int.class})
        public static RefConstructor<?> ctor;
    }

    public static class ProviderClientRecordJB {
        public static Class<?> TYPE = RefClass.load(ProviderClientRecordJB.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUOR0BBQwWExctMxoGAQYiDAwBAQE="));
        public static RefObject<Object> mHolder;
        public static RefObject<IInterface> mProvider;
    }

    public static class ProviderClientRecord {
        public static Class<?> TYPE = RefClass.load(ProviderClientRecord.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUOR0BBQwWExctMxoGAQYiDAwBAQE="));
        @MethodReflectParams(value={"android.app.ActivityThread", "java.lang.String", "android.content.IContentProvider", "android.content.ContentProvider"})
        public static RefConstructor<?> ctor;
        public static RefObject<String> mName;
        public static RefObject<IInterface> mProvider;
    }

    public static class ActivityClientRecord {
        public static Class<?> TYPE = RefClass.load(ActivityClientRecord.class, StringFog.decrypt("EgsWBAoHO10CHwJeKAwaGhMbAhw6NwEGDhZUKAwaGhMbAhwtMxoGAQYiDAwBAQE="));
        public static RefObject<Activity> activity;
        public static RefObject<ActivityInfo> activityInfo;
        public static RefObject<Intent> intent;
        public static RefObject<IBinder> token;
        public static RefObject<Boolean> isTopResumedActivity;
        public static RefObject<Object> packageInfo;
        public static RefObject<Object> compatInfo;
    }
}

