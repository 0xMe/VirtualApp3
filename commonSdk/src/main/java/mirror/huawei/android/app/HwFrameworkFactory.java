/*
 * Decompiled with CFR 0.152.
 */
package mirror.huawei.android.app;

import com.lody.virtual.StringFog;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class HwFrameworkFactory {
    public static Class<?> TYPE = RefClass.load(HwFrameworkFactory.class, StringFog.decrypt("EgsWBAoHO10AAB8dBgFAOxI0BAQDOgQMHRk2CAwaHBcL"));
    public static RefStaticMethod<Object> getHwApiCacheManagerEx;

    public static Object getHwApiCacheManagerEx() {
        RefStaticMethod<Object> obj = getHwApiCacheManagerEx;
        if (obj != null) {
            return obj.call(new Object[0]);
        }
        return null;
    }
}

