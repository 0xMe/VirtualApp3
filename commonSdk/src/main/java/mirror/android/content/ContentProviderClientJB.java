/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProviderClient
 */
package mirror.android.content;

import android.content.ContentProviderClient;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefConstructor;

public class ContentProviderClientJB {
    public static Class TYPE = RefClass.load(ContentProviderClientJB.class, ContentProviderClient.class);
    @MethodReflectParams(value={"android.content.ContentResolver", "android.content.IContentProvider", "boolean"})
    public static RefConstructor<ContentProviderClient> ctor;
}

