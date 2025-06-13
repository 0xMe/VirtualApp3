/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package com.lody.virtual.client.hook.proxies.devicepolicy;

import android.content.ComponentName;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import java.lang.reflect.Method;
import mirror.android.app.admin.IDevicePolicyManager;

public class DevicePolicyManagerStub
extends BinderInvocationProxy {
    public DevicePolicyManagerStub() {
        super(IDevicePolicyManager.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguLmUVLCtsJyQ1KhccP2gjSFo=")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new GetStorageEncryptionStatus());
        this.addMethodProxy(new GetDeviceOwnerComponent());
        this.addMethodProxy(new NotifyPendingSystemUpdate());
        this.addMethodProxy(new GetDeviceOwnerName());
        this.addMethodProxy(new GetProfileOwnerName());
        this.addMethodProxy(new SetPasswordQuality());
        this.addMethodProxy(new GetFactoryResetProtectionPolicy());
        this.addMethodProxy(new IsDeviceProvisioned());
        this.addMethodProxy(new ReplaceFirstPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2BW8zFhZ9AQo7Oy0cM28VQSRqARouJhgcO30wTSBuEVRF"))));
    }

    private static class IsDeviceProvisioned
    extends MethodProxy {
        private IsDeviceProvisioned() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAc2WWgaOC99JDACIz1fLGwgAi9lJxogLghSVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return true;
        }
    }

    private static class GetFactoryResetProtectionPolicy
    extends MethodProxy {
        private GetFactoryResetProtectionPolicy() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAjJClmHh4qLQYuPWoFGgZkHgocKgguJWYaGipsNSAeKT42JW4FSFo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return null;
        }
    }

    private static class SetPasswordQuality
    extends MethodProxy {
        private SetPasswordQuality() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uLGcFJANhJzg1Iz02A2UjQSRqDiw0"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return null;
        }
    }

    private static class GetProfileOwnerName
    extends MethodProxy {
        private GetProfileOwnerName() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcKFiViNAYoKAVfI28VGgR9NzgeLhhSVg=="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return null;
        }
    }

    private static class GetDeviceOwnerName
    extends MethodProxy {
        private GetDeviceOwnerName() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFND5jDig/Ii46DmkgRQBoAQ4g"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return null;
        }
    }

    private static class NotifyPendingSystemUpdate
    extends MethodProxy {
        private NotifyPendingSystemUpdate() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOD9pHjA2KBccDmkIAj9sJCwgLBUuDmIaPD9uAVRF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return 0;
        }
    }

    private static class GetStorageEncryptionStatus
    extends MethodProxy {
        private GetStorageEncryptionStatus() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcwMCVhNCA9KAUMDm4KRT9sHiwaLD4cUmYaPD9qASxF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            args[0] = VirtualCore.get().getHostPkg();
            GetStorageEncryptionStatus.replaceLastUserId(args);
            return method.invoke(who, args);
        }
    }

    private static class GetDeviceOwnerComponent
    extends MethodProxy {
        private GetDeviceOwnerComponent() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFND5jDig/Ii46DmkgRRNlJw47LD4cJ2AzFlo="));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            return new ComponentName(GetDeviceOwnerComponent.getAppPkg(), "");
        }
    }
}

