/*
 * Decompiled with CFR 0.152.
 */
package android.os;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.os.IStatsManagerService;

public class StatsManagerServiceStub
extends BinderInvocationProxy {
    private static final String SERVER_NAME = StringFog.decrypt("ABETAhYDPh0CCBcC");

    public StatsManagerServiceStub() {
        super(IStatsManagerService.Stub.asInterface, StringFog.decrypt("ABETAhYDPh0CCBcC"));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AAAGMgQaPjUGGxEYJh8LAQQGHwoA"), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AQAfGRMLGxIXDjQVHQwGPBUXBAQaNhwN"), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AAAGNwYaNgUGLB0eDwYJACYaFwsJOhcsHxcCCBsHHAs="), new long[0]));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AQAfGRMLHhAXBgQVKgAAFQwVBSYGPh0EChY/GQocEhEbGQs="), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AAAGNBcBPhcADgEEOhoMAAYAHwcLLQ=="), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("BgsBExEsLRwCCxERGhs9BgcBFRcHPRYR"), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("FAAGJAAJNgAXCgAVDSoWAwAAHwgLMQcqCwE="), new long[0]));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("FAAGOwAaPhcCGxM="), new byte[0]));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("FAAGMgQaPg=="), new byte[0]));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("EgEWNQoAORoEGgARHQYBHQ=="), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("AQAVHxYaOgEzGh4cKBsBHiYTGgkMPhAI"), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt("BgsAEwIHLAcGHSIFBQMvBwofNQQCMxECDBk="), null));
    }
}

