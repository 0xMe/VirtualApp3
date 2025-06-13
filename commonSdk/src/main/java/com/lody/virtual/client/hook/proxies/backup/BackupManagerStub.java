/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.hook.proxies.backup;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import mirror.android.app.backup.IBackupManager;

public class BackupManagerStub
extends BinderInvocationProxy {
    public BackupManagerStub() {
        super(IBackupManager.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4+OWUwNAI=")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRg+LGsbLCB9Dlk9KAc2Vg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2saFhR9DigxLAgmW24gBjc=")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggmM2ogMBNgJFk2KAcqLmkjBlo=")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggmM2ogMBZjASg5Ki0YDmkjAgZrASxF")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKWwFGgRiDCAgIQcYL2UzQSRlEVRF")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMjJCljJzAsIAcYOW4VOCtrEVRF")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMjJCljJzAsOxguDWUVLANqAQYbLhgqVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4+OWUwNAJoNB4t")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT0uDmoLFjd9JA4vIxhSVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT0uDmoIMAR9DlkpIxdfKGUxRTdoJ10wKQhSVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT0uDmoIFithJwo1Iz0MVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2MWojGj1gHjAwKC0MWWUjOCRgNzgqIz0uDmgjMB9uASw9KQdfJw==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMwNARhNDA2LBY2KG4jMANsEQY5KghSVg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwLJCRgHwoqLwcYL2ozNARvHjBF")), new String[0]));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZlNCA5KS4MKn0wRTdlNDA7LD0MCg==")), null));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2HGsVLCFmASQVKj0iOG8zGiw=")), false));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGMjJCljJzAsOxciL2oKEiVsNyxF")), true));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+KWMjJCljJzAsOxciL2oKEiVsNyxF")), false));
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4uPWUVBl9iASggKi4uPWIFGgNsJx4cLC5SVg==")), null));
        if (BuildCompat.isOreo()) {
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZlNCA5KS4MKn0wRTdlNDA7LD0MCmUFNAZsNCxF")), null));
        }
        if (BuildCompat.isPie()) {
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtuESw7Kj4qKm8KRQZgDiw/KS4YJmYFFiBlJ1RF")), null));
            this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2HGsVLCFmASQPKAguLGwjAitgATA/IxciJw==")), false));
        }
    }
}

