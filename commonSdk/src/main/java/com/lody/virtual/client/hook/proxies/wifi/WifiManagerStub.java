/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.net.DhcpInfo
 *  android.net.wifi.ScanResult
 *  android.net.wifi.SupplicantState
 *  android.net.wifi.WifiConfiguration
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Build$VERSION
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.WorkSource
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.proxies.wifi;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import mirror.android.net.wifi.IWifiManager;
import mirror.android.net.wifi.WifiInfo;
import mirror.android.net.wifi.WifiSsid;

public class WifiManagerStub
extends BinderInvocationProxy {
    @Override
    public void inject() throws Throwable {
        super.inject();
        WifiManager wifiManager = (WifiManager)VirtualCore.get().getContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KS4YPmUVSFo=")));
        if (mirror.android.net.wifi.WifiManager.mService != null) {
            try {
                mirror.android.net.wifi.WifiManager.mService.set(wifiManager, (IInterface)((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (mirror.android.net.wifi.WifiManager.sService != null) {
            try {
                mirror.android.net.wifi.WifiManager.sService.set((IInterface)((BinderInvocationStub)this.getInvocationStub()).getProxyInterface());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public WifiManagerStub() {
        super(IWifiManager.Stub.asInterface, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KS4YPmUVSFo=")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new MethodProxy(){

            @Override
            public String getMethodName() {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2B2UVOC9qDlk7Lz1bPWkzSFo="));
            }

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                SettingConfig.FakeWifiStatus fakeWifiStatus = 1.getConfig().getFakeWifiStatus(1.getAppPkg(), 1.getAppUserId());
                if (fakeWifiStatus != null) {
                    return true;
                }
                return super.call(who, method, args);
            }
        });
        this.addMethodProxy(new MethodProxy(){

            @Override
            public String getMethodName() {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQzAi5jDDA2LwcuCGkjBl5vETg/LhhSVg=="));
            }

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                SettingConfig.FakeWifiStatus fakeWifiStatus = 2.getConfig().getFakeWifiStatus(2.getAppPkg(), 2.getAppUserId());
                if (fakeWifiStatus != null) {
                    return 3;
                }
                return super.call(who, method, args);
            }
        });
        this.addMethodProxy(new MethodProxy(){

            @Override
            public String getMethodName() {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtqHho5IxUcDmkVNFo="));
            }

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                IPInfo ipInfo;
                SettingConfig.FakeWifiStatus fakeWifiStatus = 3.getConfig().getFakeWifiStatus(3.getAppPkg(), 3.getAppUserId());
                if (fakeWifiStatus != null && (ipInfo = WifiManagerStub.getIPInfo()) != null) {
                    return WifiManagerStub.this.createDhcpInfo(ipInfo);
                }
                return super.call(who, method, args);
            }
        });
        this.addMethodProxy(new GetConnectionInfo());
        this.addMethodProxy(new GetScanResults());
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMjJAZ9JBo/KBYqP24jMF9rDjAwLAcqDw=="))));
        this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2L2wVAgRiDzgzKD0cU28FAiE="))));
        this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtuJAY+KQVbDW4FJFJlJAoiIT4ACWEwNCA="))));
        if (Build.VERSION.SDK_INT > 21) {
            this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMA5gJCg7LBccDW8YRStsJCw5Ixg2CmIKFhBpJCQd"))));
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uL2wVNANmHCw7LBcqMmkjBl5oJzgb"))));
        }
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQzAi5jDDA2LwcuCGkjBlo="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMzGiZiNAY9LAguPWkxMCtvHiAcKS5bDw=="))));
        this.addMethodProxy(new StaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQzAi5jDCAsJy1fDmkVLC1vDgosKggYKWAzSFo="))){

            @Override
            public Object call(Object who, Method method, Object ... args) throws Throwable {
                List configurations = ((WifiManager)WifiManagerStub.this.getContext().getApplicationContext().getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KS4YPmUVSFo=")))).getConfiguredNetworks();
                if (!configurations.isEmpty()) {
                    return configurations.get(0);
                }
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.SSID = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggcPG8jGi9iHCACJi5SVg==")) + new Random().nextInt(9000) + 1000;
                wifiConfiguration.allowedKeyManagement.set(4);
                String uuid = UUID.randomUUID().toString();
                wifiConfiguration.preSharedKey = uuid.substring(0, 8) + uuid.substring(9, 13);
                return wifiConfiguration;
            }
        });
        this.addMethodProxy(new ResultStaticMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQzAi5jDCAsJy1fDmkVLC1vDgosKggYKWAzSFo=")), 0));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMA5gJCg7KhVfDm8wLAplJCw6KQgACg=="))));
        if (BuildCompat.isOreo()) {
            this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMF59JCA2"))){

                @Override
                public Object call(Object who, Method method, Object ... args) throws Throwable {
                    MethodParameterUtils.replaceFirstAppPkg(args);
                    return super.call(who, method, args);
                }
            });
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.addMethodProxy(new RemoveWorkSourceMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMF59JCA2"))));
        }
    }

    private static ScanResult cloneScanResult(Parcelable scanResult) {
        Parcel p = Parcel.obtain();
        scanResult.writeToParcel(p, 0);
        p.setDataPosition(0);
        ScanResult newScanResult = (ScanResult)Reflect.on(scanResult).field(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JisMWGMYMA9pN1RF"))).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtqNyw1KgYmOWoVAitlEVRF")), p).get();
        p.recycle();
        return newScanResult;
    }

    private static IPInfo getIPInfo() {
        try {
            ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                ArrayList<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    String sAddr;
                    boolean isIPv4;
                    if (addr.isLoopbackAddress() || !(isIPv4 = WifiManagerStub.isIPv4Address(sAddr = addr.getHostAddress().toUpperCase()))) continue;
                    IPInfo info = new IPInfo();
                    info.addr = addr;
                    info.intf = intf;
                    info.ip = sAddr;
                    info.ip_hex = WifiManagerStub.InetAddress_to_hex(addr);
                    info.netmask_hex = WifiManagerStub.netmask_to_hex(intf.getInterfaceAddresses().get(0).getNetworkPrefixLength());
                    return info;
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isIPv4Address(String input) {
        Pattern IPV4_PATTERN = Pattern.compile(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JzpeKnwYQAJODTBTLl8uGXo3PwZhDAIvKAVaDk4JPEx4JV0tPQUiIHoJRRJ6I1gbCgYHP3wJFhNrMFlJOl8HLGYYHixkHwEsOgQiH3sIOCx5IgIvM18YPU8jQC8=")));
        return IPV4_PATTERN.matcher(input).matches();
    }

    private static int netmask_to_hex(int netmask_slash) {
        int r = 0;
        int b = 1;
        int i = 0;
        while (i < netmask_slash) {
            r |= b;
            ++i;
            b <<= 1;
        }
        return r;
    }

    private static int InetAddress_to_hex(InetAddress a) {
        int result = 0;
        byte[] b = a.getAddress();
        for (int i = 0; i < 4; ++i) {
            result |= (b[i] & 0xFF) << 8 * i;
        }
        return result;
    }

    private DhcpInfo createDhcpInfo(IPInfo ip) {
        DhcpInfo i = new DhcpInfo();
        i.ipAddress = ip.ip_hex;
        i.netmask = ip.netmask_hex;
        i.dns1 = 0x4040404;
        i.dns2 = 0x8080808;
        return i;
    }

    private static android.net.wifi.WifiInfo createWifiInfo(SettingConfig.FakeWifiStatus status) {
        android.net.wifi.WifiInfo info = WifiInfo.ctor.newInstance();
        IPInfo ip = WifiManagerStub.getIPInfo();
        InetAddress address = ip != null ? ip.addr : null;
        WifiInfo.mNetworkId.set(info, 1);
        WifiInfo.mSupplicantState.set(info, SupplicantState.COMPLETED);
        WifiInfo.mBSSID.set(info, status.getBSSID());
        WifiInfo.mMacAddress.set(info, status.getMAC());
        WifiInfo.mIpAddress.set(info, address);
        WifiInfo.mLinkSpeed.set(info, 65);
        if (Build.VERSION.SDK_INT >= 21) {
            WifiInfo.mFrequency.set(info, 5000);
        }
        WifiInfo.mRssi.set(info, 200);
        if (WifiInfo.mWifiSsid != null) {
            WifiInfo.mWifiSsid.set(info, WifiSsid.createFromAsciiEncoded.call(status.getSSID()));
        } else {
            WifiInfo.mSSID.set(info, status.getSSID());
        }
        return info;
    }

    public static class IPInfo {
        NetworkInterface intf;
        InetAddress addr;
        String ip;
        int ip_hex;
        int netmask_hex;
    }

    private final class GetScanResults
    extends ReplaceCallingPkgMethodProxy {
        public GetScanResults() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGczLDdgNSw/Iy4MCGUwAlo=")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (GetScanResults.isFakeLocationEnable()) {
                return new ArrayList();
            }
            return super.call(who, method, args);
        }
    }

    private final class GetConnectionInfo
    extends MethodProxy {
        private GetConnectionInfo() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMzGiZgNDA5LBccDW8bLCZrNwZF"));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            SettingConfig.FakeWifiStatus status = GetConnectionInfo.getConfig().getFakeWifiStatus(GetConnectionInfo.getAppPkg(), GetConnectionInfo.getAppUserId());
            if (status != null) {
                return WifiManagerStub.createWifiInfo(status);
            }
            android.net.wifi.WifiInfo wifiInfo = (android.net.wifi.WifiInfo)method.invoke(who, args);
            if (wifiInfo != null) {
                if (GetConnectionInfo.isFakeLocationEnable()) {
                    WifiInfo.mBSSID.set(wifiInfo, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ol45In8OIzJPViciM18lIHo0TDJ8VzxF")));
                    WifiInfo.mMacAddress.set(wifiInfo, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ol45In8OIzJPViciM18lIHo0TDJ8VzxF")));
                } else {
                    String mac;
                    VDeviceConfig config = GetConnectionInfo.getDeviceConfig();
                    if (config.enable && !TextUtils.isEmpty((CharSequence)(mac = GetConnectionInfo.getDeviceConfig().wifiMac))) {
                        WifiInfo.mMacAddress.set(wifiInfo, mac);
                    }
                }
            }
            return wifiInfo;
        }
    }

    private class RemoveWorkSourceMethodProxy
    extends StaticMethodProxy {
        RemoveWorkSourceMethodProxy(String name) {
            super(name);
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            int index = ArrayUtils.indexOfFirst(args, WorkSource.class);
            if (index >= 0) {
                args[index] = null;
            }
            return super.call(who, method, args);
        }
    }
}

