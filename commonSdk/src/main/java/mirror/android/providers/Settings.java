/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ContentResolver
 *  android.os.IInterface
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$Secure
 *  android.provider.Settings$System
 */
package mirror.android.providers;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;
import mirror.RefStaticObject;

public class Settings {
    public static Class<?> TYPE = RefClass.load(Settings.class, android.provider.Settings.class);

    public static class Config {
        public static Class<?> TYPE = RefClass.load(Config.class, StringFog.decrypt("EgsWBAoHO10THR0GAAsLAUshExEaNh0EHFYzBgEIGgI="));
        private static RefMethod<Object> getString;

        public static Object getString(ContentResolver contentResolver, String str) {
            RefMethod<Object> refMethod = getString;
            if (refMethod == null) {
                return null;
            }
            return refMethod.call(new Object[]{contentResolver, str}, new Object[0]);
        }
    }

    public static class System {
        public static Class<?> TYPE = RefClass.load(System.class, Settings.System.class);
        public static RefStaticObject<Object> sNameValueCache;
    }

    public static class Secure {
        public static Class<?> TYPE = RefClass.load(Secure.class, Settings.Secure.class);
        public static RefStaticObject<Object> sNameValueCache;
    }

    public static class ContentProviderHolder {
        public static Class<?> TYPE = RefClass.load(ContentProviderHolder.class, StringFog.decrypt("EgsWBAoHO10THR0GAAsLAUshExEaNh0EHFYzBgEaFgsGJhcBKRoHCgA4BgMKFhc="));
        public static RefObject<IInterface> mContentProvider;
    }

    public static class NameValueCacheOreo {
        public static Class<?> TYPE = RefClass.load(NameValueCacheOreo.class, StringFog.decrypt("EgsWBAoHO10THR0GAAsLAUshExEaNh0EHFY+CAILJQQeAwAtPhALCg=="));
        public static RefObject<Object> mProviderHolder;
    }

    public static class NameValueCache {
        public static Class<?> TYPE = RefClass.load(NameValueCache.class, StringFog.decrypt("EgsWBAoHO10THR0GAAsLAUshExEaNh0EHFY+CAILJQQeAwAtPhALCg=="));
        public static RefObject<Object> mContentProvider;
    }

    @TargetApi(value=17)
    public static class Global {
        public static Class<?> TYPE = RefClass.load(Global.class, Settings.Global.class);
        public static RefStaticObject<Object> sNameValueCache;
    }
}

