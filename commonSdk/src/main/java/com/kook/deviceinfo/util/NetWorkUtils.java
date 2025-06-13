/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.bluetooth.BluetoothAdapter
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.wifi.ScanResult
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Build$VERSION
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 *  androidx.core.app.ActivityCompat
 */
package com.kook.deviceinfo.util;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import com.kook.common.utils.HVLog;
import com.kook.deviceinfo.DeviceApplication;
import com.kook.deviceinfo.data.NetWorkData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class NetWorkUtils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager;
        NetworkInfo mNetworkInfo;
        if (context != null && (mNetworkInfo = (mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null) {
            return mNetworkInfo.isAvailable();
        }
        HVLog.d("\u7f51\u7edc\u4e0d\u901a");
        return false;
    }

    public static NetWorkData getNetWorkInfo(NetWorkData wifiInfos) {
        String ssid;
        WifiManager mWifiManager = (WifiManager)DeviceApplication.getApp().getApplicationContext().getSystemService("wifi");
        WifiInfo info = mWifiManager.getConnectionInfo();
        wifiInfos.current_wifi.bssid = info.getBSSID();
        wifiInfos.current_wifi.name = ssid = info.getSSID().replace("\"", "");
        wifiInfos.current_wifi.ssid = ssid;
        wifiInfos.current_wifi.mac = info.getMacAddress();
        wifiInfos.ip = NetWorkUtils.int2ip(info.getIpAddress());
        wifiInfos.configured_wifi.addAll(NetWorkUtils.getAroundWifiDeciceInfo());
        return wifiInfos;
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append(ipInt >> 8 & 0xFF).append(".");
        sb.append(ipInt >> 16 & 0xFF).append(".");
        sb.append(ipInt >> 24 & 0xFF);
        return sb.toString();
    }

    public static List<NetWorkData.NetWorkInfo> getAroundWifiDeciceInfo() {
        StringBuffer sInfo = new StringBuffer();
        WifiManager mWifiManager = (WifiManager)DeviceApplication.getApp().getApplicationContext().getSystemService("wifi");
        List scanResults = mWifiManager.getScanResults();
        ArrayList<NetWorkData.NetWorkInfo> wifiLists = new ArrayList<NetWorkData.NetWorkInfo>();
        for (ScanResult scanResult : scanResults) {
            wifiLists.add(new NetWorkData.NetWorkInfo(scanResult.BSSID, scanResult.SSID, scanResult.SSID));
        }
        return wifiLists;
    }

    public static String getMacAddress() {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < 23) {
            mac = NetWorkUtils.getMacDefault();
        } else if (Build.VERSION.SDK_INT < 24) {
            mac = NetWorkUtils.getMacAddresss();
        } else if (Build.VERSION.SDK_INT >= 24) {
            mac = NetWorkUtils.getMacFromHardware();
        }
        return mac;
    }

    private static String getMacDefault() {
        String mac = "02:00:00:00:00:00";
        WifiManager wifi = (WifiManager)DeviceApplication.getApp().getApplicationContext().getSystemService("wifi");
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty((CharSequence)mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    private static String getMacAddresss() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }

    private static String getMacFromHardware() {
        try {
            ArrayList<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    public static String getBluetoothMac() {
        String bluetoothAddress = "";
        bluetoothAddress = Build.VERSION.SDK_INT < 23 ? Settings.Secure.getString((ContentResolver)DeviceApplication.getApp().getContentResolver(), (String)"bluetooth_address") : NetWorkUtils.getBluetoothAddressSdk23(BluetoothAdapter.getDefaultAdapter());
        return bluetoothAddress;
    }

    @TargetApi(value=23)
    public static String getBluetoothAddressSdk23(BluetoothAdapter adapter) {
        if (adapter == null) {
            return null;
        }
        Class<?> btAdapterClass = adapter.getClass();
        try {
            Class<?> btClass = Class.forName("android.bluetooth.IBluetooth");
            Field bluetooth = btAdapterClass.getDeclaredField("mService");
            bluetooth.setAccessible(true);
            Method btAddress = btClass.getMethod("getAddress", new Class[0]);
            btAddress.setAccessible(true);
            return (String)btAddress.invoke(bluetooth.get(adapter), new Object[0]);
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String getBluetoothName(Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= 31 && ActivityCompat.checkSelfPermission((Context)context, (String)"android.permission.BLUETOOTH_CONNECT") != 0) {
            return "Permission not granted";
        }
        return bluetoothAdapter.getName();
    }
}

