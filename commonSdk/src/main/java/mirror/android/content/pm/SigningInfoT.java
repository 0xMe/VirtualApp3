/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.pm.Signature
 *  android.util.ArraySet
 */
package mirror.android.content.pm;

import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.util.ArraySet;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Constructor;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.android.content.pm.SigningInfo;

public class SigningInfoT {
    public static Class<?> TYPE = RefClass.load(SigningInfo.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDYHOB0KARU5BwkB"));
    @MethodReflectParams(value={"android.content.pm.SigningDetails"})
    public static RefConstructor<Object> ctor;

    public static Object createSigningInfo(PackageParser.SigningDetails signingDetails) {
        try {
            Object detail = Class.forName(StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDYHOB0KARU0DBsPGgkB")).getDeclaredConstructor(new Signature[0].getClass(), Integer.TYPE, new ArraySet().getClass(), new Signature[0].getClass()).newInstance(Reflect.on(signingDetails).field(StringFog.decrypt("AAwVGAQaKgEGHA==")).get(), Reflect.on(signingDetails).field(StringFog.decrypt("AAwVGAQaKgEGPBEYDAILJQAABQwBMQ==")).get(), Reflect.on(signingDetails).field(StringFog.decrypt("AxAQGgwNFBYaHA==")).get(), Reflect.on(signingDetails).field(StringFog.decrypt("AwQBAjYHOB0KARUzDB0aGgMbFQQaOgA=")).get());
            for (Constructor<?> constructorSigInfo : Class.forName(StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDYHOB0KARU5BwkB")).getDeclaredConstructors()) {
                if (!constructorSigInfo.toString().contains(StringFog.decrypt("IAwVGAwAODcGGxMZBRw="))) continue;
                return constructorSigInfo.newInstance(detail);
            }
            return null;
        }
        catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}

