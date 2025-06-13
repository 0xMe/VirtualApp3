/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 */
package mirror.android.content;

import android.content.Intent;
import android.os.Bundle;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IIntentReceiverJB {
    public static Class<?> TYPE = RefClass.load(IIntentReceiverJB.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXSw7GBELMQcxChEVABkLAQ=="));
    @MethodParams(value={Intent.class, int.class, String.class, Bundle.class, boolean.class, boolean.class, int.class})
    public static RefMethod<Void> performReceive;
}

