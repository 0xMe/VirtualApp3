/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.renderscript.RenderScript
 */
package mirror.android.view;

import java.io.File;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class RenderScript {
    public static Class<?> TYPE = RefClass.load(RenderScript.class, android.renderscript.RenderScript.class);
    @MethodParams(value={File.class})
    public static RefStaticMethod<Void> setupDiskCache;
}

