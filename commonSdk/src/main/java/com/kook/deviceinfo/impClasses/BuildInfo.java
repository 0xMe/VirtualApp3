/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.database.Cursor
 *  android.graphics.Point
 *  android.hardware.SensorManager
 *  android.hardware.camera2.CameraCharacteristics
 *  android.hardware.camera2.CameraManager
 *  android.hardware.camera2.params.StreamConfigurationMap
 *  android.net.Uri
 *  android.os.BatteryManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.StatFs
 *  android.os.SystemClock
 *  android.provider.Settings$SettingNotFoundException
 *  android.provider.Settings$System
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.Size
 *  android.view.Display
 *  android.view.WindowManager
 *  androidx.core.app.ActivityCompat
 *  androidx.core.content.pm.PackageInfoCompat
 *  com.google.android.gms.ads.identifier.AdvertisingIdClient
 *  com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
 *  com.google.android.gms.common.GoogleApiAvailability
 *  com.google.android.gms.common.GooglePlayServicesNotAvailableException
 *  com.google.android.gms.common.GooglePlayServicesRepairableException
 *  com.kook.librelease.R$string
 */
package com.kook.deviceinfo.impClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.PackageInfoCompat;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.kook.common.utils.HVLog;
import com.kook.deviceinfo.constant.SystemFileConStant;
import com.kook.deviceinfo.models.ThermalModel;
import com.kook.librelease.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildInfo {
    private final Context context;
    CameraManager cameraManager;
    ArrayList<ThermalModel> thermalList = new ArrayList();

    public BuildInfo(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= 21) {
            this.cameraManager = (CameraManager)context.getSystemService("camera");
        }
    }

    public String getNetworkName() {
        StringBuilder name = new StringBuilder();
        TelephonyManager telephonyManager = (TelephonyManager)this.context.getSystemService("phone");
        if (Build.VERSION.SDK_INT > 22) {
            SubscriptionManager manager = (SubscriptionManager)this.context.getSystemService("telephony_subscription_service");
            if (ActivityCompat.checkSelfPermission((Context)this.context, (String)"android.permission.READ_PHONE_STATE") == 0) {
                List info = manager.getActiveSubscriptionInfoList();
                if (info != null) {
                    for (SubscriptionInfo subscriptionInfo : info) {
                        name.append(this.context.getResources().getString(R.string.netop)).append(" : ").append(subscriptionInfo.getCarrierName()).append("\n");
                    }
                } else {
                    name.append(this.context.getResources().getString(R.string.netop)).append(" : ").append(this.context.getResources().getString(R.string.nosim)).append("\n");
                }
            }
        } else if (telephonyManager.getNetworkOperatorName() != null) {
            name.append(this.context.getResources().getString(R.string.netop)).append(" : ").append(telephonyManager.getNetworkOperatorName()).append("\n");
        }
        return name.toString();
    }

    public String getPhoneType() {
        TelephonyManager manager = (TelephonyManager)this.context.getSystemService("phone");
        String devicetype = "";
        switch (manager.getPhoneType()) {
            case 0: {
                devicetype = "NONE";
                break;
            }
            case 2: {
                devicetype = "CDMA";
                break;
            }
            case 1: {
                devicetype = "GSM";
                break;
            }
            case 3: {
                devicetype = "SIP";
            }
        }
        return devicetype;
    }

    public String getCpuGovernor() {
        String line;
        try {
            File file = new File(SystemFileConStant.SOC_GOVERNOR);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            reader.close();
        }
        catch (IOException e) {
            line = this.context.getResources().getString(R.string.unknown);
            e.printStackTrace();
        }
        return line;
    }

    public String getCpuDriver() {
        String line;
        try {
            File file = new File(SystemFileConStant.SOC_DRIVER);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            reader.close();
        }
        catch (IOException e) {
            line = this.context.getResources().getString(R.string.unknown);
            e.printStackTrace();
        }
        return line;
    }

    public String getRunningCpuString() {
        StringBuilder cpuBuilder = new StringBuilder();
        for (int i = 0; i < this.getCoresCount(); ++i) {
            cpuBuilder.append("   Core ").append(i).append("       ").append(this.getRunningCpu(i, true)).append("\n\n");
        }
        String totalRunning = cpuBuilder.toString();
        return totalRunning.substring(0, totalRunning.length() - 2);
    }

    public String getUsage() {
        int c = 0;
        for (int i = 0; i < this.getCoresCount(); ++i) {
            c += (int)(this.getCpuUsage(i) * 100.0f);
        }
        return c / this.getCoresCount() + "%";
    }

    public float getCpuUsage(int i) {
        int freq = 0;
        int maxFreq = 0;
        try {
            String line2;
            Process process1 = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/scaling_cur_freq");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()));
            Process process2 = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line1 = reader1.readLine();
            if (line1 != null) {
                freq = Integer.parseInt(line1) / 1000;
            }
            if ((line2 = reader2.readLine()) != null) {
                maxFreq = Integer.parseInt(line2) / 1000;
            }
            process1.destroy();
            reader1.close();
            process2.destroy();
            reader2.close();
        }
        catch (IOException e) {
            e.getMessage();
        }
        if (maxFreq == 0) {
            return 0.5f;
        }
        return (float)freq / (float)maxFreq;
    }

    public String getCpuFrequency() {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < this.getCoresCount(); ++i) {
            try {
                int maxFreq;
                String line2;
                int minFreq;
                Process process1 = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_min_freq");
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()));
                Process process2 = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
                String line1 = reader1.readLine();
                if (line1 != null && min >= (minFreq = Integer.parseInt(line1) / 1000)) {
                    min = minFreq;
                }
                if ((line2 = reader2.readLine()) != null && max <= (maxFreq = Integer.parseInt(line2) / 1000)) {
                    max = maxFreq;
                }
                process1.destroy();
                reader1.close();
                process2.destroy();
                reader2.close();
                continue;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (min != Integer.MAX_VALUE && max != Integer.MIN_VALUE) {
            return min + " Mhz - " + max + " Mhz";
        }
        return this.context.getResources().getString(R.string.unknown);
    }

    @SuppressLint(value={"DefaultLocale"})
    public String getCpuArchitecture() {
        StringBuilder builder = new StringBuilder();
        int cores = this.getCoresCount();
        if (cores <= 4) {
            try {
                Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                float f = 0.0f;
                String line = reader.readLine();
                if (line != null) {
                    f = Float.parseFloat(line) / 1000000.0f;
                }
                builder.append(cores).append(" x ").append(String.format("%.2f", Float.valueOf(f))).append(" GHz\n");
            }
            catch (IOException e) {
                e.getMessage();
            }
        } else {
            float[] arr = new float[cores];
            for (int i = 0; i < cores; ++i) {
                try {
                    float f;
                    Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = reader.readLine();
                    if (line == null) continue;
                    arr[i] = f = Float.parseFloat(line) / 1000000.0f;
                    continue;
                }
                catch (IOException e) {
                    e.getMessage();
                }
            }
            int count = 0;
            float freq = 0.0f;
            for (int j = 0; j < arr.length; ++j) {
                if (j == 0) {
                    freq = arr[0];
                    ++count;
                } else if (j >= 1) {
                    if (freq == arr[j]) {
                        ++count;
                    } else {
                        builder.append(count).append(" x ").append(String.format("%.2f", Float.valueOf(freq))).append(" GHz\n");
                        freq = arr[j];
                        count = 1;
                    }
                }
                if (j != arr.length - 1) continue;
                builder.append(count).append(" x ").append(String.format("%.2f", Float.valueOf(freq))).append(" GHz\n");
            }
        }
        if (builder.toString().length() > 0) {
            return builder.toString().substring(0, builder.toString().length() - 1);
        }
        return this.context.getResources().getString(R.string.unknown);
    }

    @SuppressLint(value={"DefaultLocale"})
    public String getRunningCpu(int i, boolean bool) {
        String frequency = this.context.getResources().getString(R.string.unknown);
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/scaling_cur_freq");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                if (bool) {
                    float freq = Float.parseFloat(line) / 1000.0f;
                    frequency = String.format("%.2f", Float.valueOf(freq)) + " MHz";
                } else {
                    int f = Integer.parseInt(line) / 1000;
                    frequency = String.format("%d", f) + " MHz";
                }
            }
            process.destroy();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return frequency;
    }

    public int getCoresCount() {
        int processorCount = 0;
        try {
            Process process = Runtime.getRuntime().exec("cat " + SystemFileConStant.SOC_PRESENT);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                processorCount = Integer.parseInt(line.substring(line.indexOf("-") + 1)) + 1;
            }
            process.destroy();
            reader.close();
        }
        catch (IOException | InterruptedException | NumberFormatException e) {
            processorCount = Runtime.getRuntime().availableProcessors();
            e.printStackTrace();
        }
        return processorCount;
    }

    public long getTotalStorageInfo(String path) {
        long t = 10L;
        try {
            StatFs statFs = new StatFs(path);
            t = statFs.getTotalBytes();
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
        return t;
    }

    public long getUsedStorageInfo(String path) {
        long u = 10L;
        try {
            StatFs statFs = new StatFs(path);
            u = statFs.getTotalBytes() - statFs.getAvailableBytes();
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
        return u;
    }

    public Map<String, String> getZRamInfo() {
        HashMap<String, String> map = new HashMap<String, String>();
        File file = new File("proc/meminfo");
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] str = line.split(":");
                map.put(str[0].trim().toLowerCase(), str[1].trim().replace("kB", "").toLowerCase());
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getBatteryLevel() {
        int pct = 0;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        if (batterystat != null) {
            int level = batterystat.getIntExtra("level", -1);
            int scale = batterystat.getIntExtra("scale", -1);
            pct = level * 100 / scale;
        }
        return pct + "%";
    }

    public String getCurrentLevel() {
        int current = 0;
        if (Build.VERSION.SDK_INT >= 21) {
            BatteryManager batteryManager = (BatteryManager)this.context.getSystemService("batterymanager");
            current = batteryManager.getIntProperty(2);
        }
        return -(current / 1000) + " mA";
    }

    public String getBatteryStatus() {
        String batterystatus = null;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        int status = 0;
        if (batterystat != null) {
            status = batterystat.getIntExtra("status", -1);
        }
        switch (status) {
            case 2: {
                batterystatus = this.context.getResources().getString(R.string.charging);
                break;
            }
            case 3: {
                batterystatus = this.context.getResources().getString(R.string.discharge);
                break;
            }
            case 4: {
                batterystatus = this.context.getResources().getString(R.string.notcharge);
                break;
            }
            case 5: {
                batterystatus = this.context.getResources().getString(R.string.full);
                break;
            }
            case 1: {
                batterystatus = this.context.getResources().getString(R.string.unknown);
            }
        }
        return batterystatus;
    }

    public String getBatteryHealth() {
        String health = null;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        int status = 0;
        if (batterystat != null) {
            status = batterystat.getIntExtra("health", -1);
        }
        switch (status) {
            case 7: {
                health = this.context.getResources().getString(R.string.cold);
                break;
            }
            case 4: {
                health = this.context.getResources().getString(R.string.dead);
                break;
            }
            case 2: {
                health = this.context.getResources().getString(R.string.good);
                break;
            }
            case 5: {
                health = this.context.getResources().getString(R.string.overvoltage);
                break;
            }
            case 3: {
                health = this.context.getResources().getString(R.string.overheat);
                break;
            }
            case 1: {
                health = this.context.getResources().getString(R.string.unknown);
                break;
            }
            case 6: {
                health = this.context.getResources().getString(R.string.unspec_fail);
            }
        }
        return health;
    }

    public String getPowerSource() {
        String source;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        int status = 0;
        if (batterystat != null) {
            status = batterystat.getIntExtra("plugged", -1);
        }
        switch (status) {
            case 1: {
                source = this.context.getResources().getString(R.string.ac);
                break;
            }
            case 2: {
                source = this.context.getResources().getString(R.string.usbport);
                break;
            }
            case 4: {
                source = this.context.getResources().getString(R.string.wireless);
                break;
            }
            default: {
                source = this.context.getResources().getString(R.string.battery);
            }
        }
        return source;
    }

    public String getBatteryTechnology() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        String tech = null;
        if (batterystat != null) {
            tech = batterystat.getStringExtra("technology");
        }
        return tech;
    }

    @SuppressLint(value={"PrivateApi"})
    public String getBatteryCapacity() {
        double capacity = 0.0;
        try {
            Object power = Class.forName("com.android.internal.os.PowerProfile").getConstructor(Context.class).newInstance(this.context);
            capacity = (Double)Class.forName("com.android.internal.os.PowerProfile").getMethod("getBatteryCapacity", new Class[0]).invoke(power, new Object[0]);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return (int)capacity + " mAh";
    }

    public String getVoltage() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent batterystat = this.context.registerReceiver(null, intentFilter);
        int volt = 0;
        if (batterystat != null) {
            volt = batterystat.getIntExtra("voltage", 0);
        }
        return volt + " mV";
    }

    @SuppressLint(value={"DefaultLocale"})
    public String getScreenSize() {
        Point point = new Point();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Display.class.getMethod("getRealSize", Point.class).invoke(((Activity)this.context).getWindowManager().getDefaultDisplay(), point);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ((Activity)this.context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        double x = Math.pow((float)point.x / displayMetrics.xdpi, 2.0);
        double y = Math.pow((float)point.y / displayMetrics.ydpi, 2.0);
        double inches = (float)Math.round(Math.sqrt(x + y) * 10.0) / 10.0f;
        return String.format("%.2f", inches);
    }

    public String getBrightnessMode() {
        String mode = null;
        try {
            switch (Settings.System.getInt((ContentResolver)this.context.getContentResolver(), (String)"screen_brightness_mode")) {
                case 0: {
                    mode = "Manual";
                    break;
                }
                case 1: {
                    mode = "Automatic";
                }
            }
        }
        catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mode;
    }

    public String getOrientation() {
        String orien = "";
        switch (this.context.getResources().getConfiguration().orientation) {
            case 0: {
                orien = this.context.getResources().getString(R.string.undefined);
                break;
            }
            case 1: {
                orien = this.context.getResources().getString(R.string.portrait);
                break;
            }
            case 2: {
                orien = this.context.getResources().getString(R.string.landscape);
                break;
            }
            case 3: {
                orien = this.context.getResources().getString(R.string.square);
            }
        }
        return orien;
    }

    public String getDensityDpi() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)this.context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density = metrics.density;
        String dpi = density >= 0.75f && density < 1.0f ? " (LDPI)" : (density >= 1.0f && density < 1.5f ? " (MDPI)" : (density >= 1.5f && density <= 2.0f ? " (HDPI)" : (density > 2.0f && density <= 3.0f ? " (XHDPI)" : (density >= 3.0f && density < 4.0f ? " (XXHDPI)" : " (XXXHDPI)"))));
        return dpi;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getSensorsCount() {
        SensorManager sm = (SensorManager)this.context.getSystemService("sensor");
        List sensors = sm.getSensorList(-1);
        return sensors.size();
    }

    public String getRootInfo() {
        if (this.checkRootFiles() || this.checkTags()) {
            return "Yes";
        }
        return "No";
    }

    public boolean checkRootFiles() {
        String path;
        String[] paths;
        boolean root = false;
        String[] stringArray = paths = SystemFileConStant.ROOT_FILE;
        int n = stringArray.length;
        for (int i = 0; i < n && !(root = new File(path = stringArray[i]).exists()); ++i) {
        }
        return root;
    }

    public boolean checkTags() {
        String tag = Build.TAGS;
        return tag != null && tag.trim().contains("test-keys");
    }

    @SuppressLint(value={"PrivateApi"})
    public String getTrebleInfo() {
        String treble = null;
        try {
            Class<?> s = Class.forName("android.os.SystemProperties");
            Method m = s.getMethod("get", String.class);
            treble = (String)m.invoke(null, "ro.treble.enabled");
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (treble != null) {
                if (treble.equalsIgnoreCase("true")) {
                    return this.context.getResources().getString(R.string.supported);
                }
                return this.context.getResources().getString(R.string.not_supported);
            }
            return this.context.getResources().getString(R.string.not_supported);
        }
        return this.context.getResources().getString(R.string.not_supported);
    }

    @SuppressLint(value={"PrivateApi"})
    public String getSeamlessUpdatesInfo() {
        String updates = null;
        try {
            Class<?> s = Class.forName("android.os.SystemProperties");
            Method m = s.getMethod("get", String.class);
            if (m.invoke(null, "ro.virtual_ab.enabled") == "true" && m.invoke(null, "ro.virtual_ab.retrofit") == "false") {
                updates = "true";
            }
            updates = m.invoke(null, "ro.build.ab_update") == "true" ? "true" : "false";
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            if (updates != null) {
                if (updates.equalsIgnoreCase("true")) {
                    return this.context.getResources().getString(R.string.supported);
                }
                return this.context.getResources().getString(R.string.not_supported);
            }
            return this.context.getResources().getString(R.string.not_supported);
        }
        return this.context.getResources().getString(R.string.not_supported);
    }

    @SuppressLint(value={"PrivateApi"})
    public String getSeLinuxInfo() {
        String seLinux = "";
        try {
            Class<?> s = Class.forName("android.os.SystemProperties");
            Method m = s.getMethod("get", String.class);
            seLinux = (String)m.invoke(null, "ro.build.selinux");
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (seLinux != null && seLinux.isEmpty()) {
            seLinux = this.context.getResources().getString(R.string.unable);
        }
        return seLinux;
    }

    @SuppressLint(value={"DefaultLocale"})
    public String getUpTime() {
        long time = SystemClock.elapsedRealtime();
        long sec = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
        long min = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long hour = TimeUnit.MILLISECONDS.toHours(time);
        String uptime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
        return uptime;
    }

    public String getCodeName(int level) {
        String[] api_level = new String[]{"Android 4.1 Jelly Bean", "Android 4.2 Jelly Bean", "Android 4.3 Jelly Bean", "Android 4.4 KitKat", "Android 4.4W KitKat", "Android 5.0 Lollipop", "Android 5.1 Lollipop", "Android 6.0 Marshmallow", "Android 7.0 Nougat", "Android 7.1 Nougat", "Android 8.0 Oreo", "Android 8.1.0 Oreo", "Android 9 Pie", "Android 10", "Android 11", "Android 12", "Android 12L Sv2", " Android 13 Tiramisu"};
        return (String)Array.get(api_level, level - 16);
    }

    public String getReleaseDate(int level) {
        Configuration configuration = this.context.getResources().getConfiguration();
        Locale locale = configuration.locale;
        String[] date = locale != null && locale.getLanguage().equals("zh") ? new String[]{"2012\u5e747\u67089\u65e5", "2012\u5e7411\u670813\u65e5", "2013\u5e747\u670824\u65e5", "2013\u5e7410\u670831\u65e5", "2014\u5e746\u670825\u65e5", "2014\u5e7411\u670812\u65e5", "2015\u5e743\u67089\u65e5", "2015\u5e7410\u67085\u65e5", "2016\u5e748\u670822\u65e5", "2016\u5e7410\u67084\u65e5", "2017\u5e748\u670821\u65e5", "2017\u5e7412\u67085\u65e5", "2018\u5e748\u67086\u65e5", "2019\u5e749\u67083\u65e5", "2020\u5e749\u67088\u65e5", "2021\u5e7410\u67084\u65e5", "2022\u5e743\u67087\u65e5", "2022\u5e7410\u67082\u65e5", "2023\u5e744\u670824\u65e5"} : new String[]{"July 9, 2012", "November 13, 2012", "July 24, 2013", "October 31, 2013", "June 25, 2014", "November 12, 2014", "March 9, 2015", "October 5, 2015", "August 22, 2016", "October 4, 2016", "August 21, 2017", "December 5, 2017", "August 6, 2018", "September 3, 2019", "September 8, 2020", "October 4, 2021", "March 7, 2022", "October 2, 2022", "April 24, 2023"};
        return (String)Array.get(date, level - 16);
    }

    public String advertId() {
        String adId = this.context.getResources().getString(R.string.unable);
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo((Context)this.context);
            adId = info.getId();
        }
        catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException e) {
            e.printStackTrace();
        }
        return adId;
    }

    public String getMacAddress() {
        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : networkInterfaces) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] b = ni.getHardwareAddress();
                StringBuilder builder = new StringBuilder();
                Log.e((String)"kookmac", (String)("  getMacAddress b " + b));
                if (b == null) continue;
                for (byte add : b) {
                    builder.append(String.format("%02X:", add));
                }
                return builder.deleteCharAt(builder.length() - 1).toString();
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00";
    }

    public String getPlayServices() {
        String playServices = "";
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context) == 0) {
            try {
                String version = String.valueOf(PackageInfoCompat.getLongVersionCode((PackageInfo)this.context.getPackageManager().getPackageInfo("com.google.android.gms", 0)));
                playServices = version.substring(0, 2).concat(".").concat(version.substring(2, 4)).concat(".").concat(version.substring(4, 6));
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            playServices = "Play Services Not Available";
        }
        return playServices;
    }

    public String afCamera(int facing) {
        StringBuilder afm = new StringBuilder();
        String afModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] af;
                for (String mode : af = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        afm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        afm.append("Auto, ");
                    }
                    if (mode.trim().contains("2")) {
                        afm.append("Macro, ");
                    }
                    if (mode.trim().contains("3")) {
                        afm.append("Continuous Video, ");
                    }
                    if (mode.trim().contains("4")) {
                        afm.append("Continuous Picture, ");
                    }
                    if (!mode.trim().contains("5")) continue;
                    afm.append("EDof, ");
                }
            }
            afModes = afm.toString().length() > 0 ? afm.toString().substring(0, afm.toString().length() - 2) : "Not Available";
        }
        return afModes;
    }

    public String abCamera(int facing) {
        StringBuilder abm = new StringBuilder();
        String abModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] ab;
                for (String mode : ab = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        abm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        abm.append("50Hz, ");
                    }
                    if (mode.trim().contains("2")) {
                        abm.append("60Hz, ");
                    }
                    if (!mode.trim().contains("3")) continue;
                    abm.append("Auto, ");
                }
            }
            abModes = abm.toString().length() > 0 ? abm.toString().substring(0, abm.toString().length() - 2) : "Not Available";
        }
        return abModes;
    }

    public String effCamera(int facing) {
        StringBuilder effm = new StringBuilder();
        String effects = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] eff;
                for (String mode : eff = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        effm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        effm.append("Mono, ");
                    }
                    if (mode.trim().contains("2")) {
                        effm.append("Negative, ");
                    }
                    if (mode.trim().contains("3")) {
                        effm.append("Solarize, ");
                    }
                    if (mode.trim().contains("4")) {
                        effm.append("Sepia, ");
                    }
                    if (mode.trim().contains("5")) {
                        effm.append("Posterize, ");
                    }
                    if (mode.trim().contains("6")) {
                        effm.append("Whiteboard, ");
                    }
                    if (mode.trim().contains("7")) {
                        effm.append("Blackboard, ");
                    }
                    if (!mode.trim().contains("8")) continue;
                    effm.append("Aqua, ");
                }
            }
            effects = effm.toString().length() > 0 ? effm.toString().substring(0, effm.toString().length() - 2) : "Not Available";
        }
        return effects;
    }

    public String sceneCamera(int facing) {
        String sceneModes = null;
        StringBuilder scenem = new StringBuilder();
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] scenes;
                for (String mode : scenes = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        scenem.append("Disabled, ");
                    }
                    if (mode.trim().contains("1")) {
                        scenem.append("Face Priority, ");
                    }
                    if (mode.trim().contains("2")) {
                        scenem.append("Action, ");
                    }
                    if (mode.trim().contains("3")) {
                        scenem.append("Portrait, ");
                    }
                    if (mode.trim().contains("4")) {
                        scenem.append("Landscape, ");
                    }
                    if (mode.trim().contains("5")) {
                        scenem.append("Night, ");
                    }
                    if (mode.trim().contains("6")) {
                        scenem.append("Night Portrait, ");
                    }
                    if (mode.trim().contains("7")) {
                        scenem.append("Theatre, ");
                    }
                    if (mode.trim().contains("8")) {
                        scenem.append("Beach, ");
                    }
                    if (mode.trim().contains("9")) {
                        scenem.append("Snow, ");
                    }
                    if (mode.trim().contains("10")) {
                        scenem.append("Sunset, ");
                    }
                    if (mode.trim().contains("11")) {
                        scenem.append("Steady Photo, ");
                    }
                    if (mode.trim().contains("12")) {
                        scenem.append("FireWorks, ");
                    }
                    if (mode.trim().contains("13")) {
                        scenem.append("Sports, ");
                    }
                    if (mode.trim().contains("14")) {
                        scenem.append("Party, ");
                    }
                    if (mode.trim().contains("15")) {
                        scenem.append("CandleLight, ");
                    }
                    if (!mode.trim().contains("16")) continue;
                    scenem.append("Barcode, ");
                }
            }
            sceneModes = scenem.toString().length() > 0 ? scenem.toString().substring(0, scenem.toString().length() - 2) : "Not Available";
        }
        return sceneModes;
    }

    public String awbCamera(int facing) {
        StringBuilder awbm = new StringBuilder();
        String awbModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] awb;
                for (String mode : awb = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        awbm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        awbm.append("Auto, ");
                    }
                    if (mode.trim().contains("2")) {
                        awbm.append("Incandescent, ");
                    }
                    if (mode.trim().contains("3")) {
                        awbm.append("Fluorescent, ");
                    }
                    if (mode.trim().contains("4")) {
                        awbm.append("Warm Fluorescent, ");
                    }
                    if (mode.trim().contains("5")) {
                        awbm.append("Daylight, ");
                    }
                    if (mode.trim().contains("6")) {
                        awbm.append("Cloudy Daylight, ");
                    }
                    if (mode.trim().contains("7")) {
                        awbm.append("Twilight, ");
                    }
                    if (!mode.trim().contains("8")) continue;
                    awbm.append("Shade, ");
                }
            }
            awbModes = awbm.toString().length() > 0 ? awbm.toString().substring(0, awbm.toString().length() - 2) : "Not Available";
        }
        return awbModes;
    }

    public String hotPixelCamera(int facing) {
        StringBuilder hpm = new StringBuilder();
        String hotPixelModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] hp;
                for (String mode : hp = Arrays.toString((int[])characteristics.get(CameraCharacteristics.HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        hpm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        hpm.append("Fast, ");
                    }
                    if (!mode.trim().contains("2")) continue;
                    hpm.append("High Quality, ");
                }
            }
            hotPixelModes = hpm.toString().length() > 0 ? hpm.toString().substring(0, hpm.toString().length() - 2) : "Not Available";
        }
        return hotPixelModes;
    }

    public String edgeModesCamera(int facing) {
        StringBuilder edgem = new StringBuilder();
        String edgeModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] ed;
                for (String mode : ed = Arrays.toString((int[])characteristics.get(CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        edgem.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        edgem.append("Fast, ");
                    }
                    if (mode.trim().contains("2")) {
                        edgem.append("High Quality, ");
                    }
                    if (!mode.trim().contains("3")) continue;
                    edgem.append("Zero Shutter Lag, ");
                }
            }
            edgeModes = edgem.toString().length() > 0 ? edgem.toString().substring(0, edgem.toString().length() - 2) : "Not Available";
        }
        return edgeModes;
    }

    public String videoModesCamera(int facing) {
        StringBuilder vsm = new StringBuilder();
        String videoModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] vs;
                for (String mode : vs = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        vsm.append("Off, ");
                    }
                    if (!mode.trim().contains("1")) continue;
                    vsm.append("On, ");
                }
            }
            videoModes = vsm.toString().length() > 0 ? vsm.toString().substring(0, vsm.toString().length() - 2) : "Not Available";
        }
        return videoModes;
    }

    public String camCapCamera(int facing) {
        StringBuilder camcapm = new StringBuilder();
        String capabilities = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] camcap;
                for (String mode : camcap = Arrays.toString((int[])characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        camcapm.append("Backward Compatible, ");
                    }
                    if (mode.trim().contains("1")) {
                        camcapm.append("Manual Sensor, ");
                    }
                    if (mode.trim().contains("2")) {
                        camcapm.append("Manual Post Processing, ");
                    }
                    if (mode.trim().contains("3")) {
                        camcapm.append("RAW, ");
                    }
                    if (mode.trim().contains("4")) {
                        camcapm.append("Private Reprocessing, ");
                    }
                    if (mode.trim().contains("5")) {
                        camcapm.append("Read Sensor Settings, ");
                    }
                    if (mode.trim().contains("6")) {
                        camcapm.append("Burst Capture, ");
                    }
                    if (mode.trim().contains("7")) {
                        camcapm.append("YUV Reprocessing, ");
                    }
                    if (mode.trim().contains("8")) {
                        camcapm.append("Depth Output, ");
                    }
                    if (mode.trim().contains("9")) {
                        camcapm.append("High Speed Video, ");
                    }
                    if (mode.trim().contains("10")) {
                        camcapm.append("Motion Tracking, ");
                    }
                    if (mode.trim().contains("11")) {
                        camcapm.append("Logical Multi Camera, ");
                    }
                    if (mode.trim().contains("12")) {
                        camcapm.append("Monochrome, ");
                    }
                    if (!mode.trim().contains("13")) continue;
                    camcapm.append("Secure Image Data, ");
                }
            }
            capabilities = camcapm.toString().length() > 0 ? camcapm.toString().substring(0, camcapm.toString().length() - 2) : "Not Available";
        }
        return capabilities;
    }

    public String testModesCamera(int facing) {
        StringBuilder tpm = new StringBuilder();
        String testPattern = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] tp;
                for (String mode : tp = Arrays.toString((int[])characteristics.get(CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        tpm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        tpm.append("Solid Color, ");
                    }
                    if (mode.trim().contains("2")) {
                        tpm.append("Color Bars, ");
                    }
                    if (mode.trim().contains("3")) {
                        tpm.append("Color Bars Fade to Gray, ");
                    }
                    if (mode.trim().contains("4")) {
                        tpm.append("PN9, ");
                    }
                    if (!mode.trim().contains("256")) continue;
                    tpm.append("Custom1, ");
                }
            }
            testPattern = tpm.toString().length() > 0 ? tpm.toString().substring(0, tpm.toString().length() - 2) : "Not Available";
        }
        return testPattern;
    }

    public String aeCamera(int facing) {
        StringBuilder aem = new StringBuilder();
        String aeModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] ae;
                for (String mode : ae = Arrays.toString((int[])characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        aem.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        aem.append("On, ");
                    }
                    if (mode.trim().contains("2")) {
                        aem.append("Auto Flash, ");
                    }
                    if (mode.trim().contains("3")) {
                        aem.append("Always Flash, ");
                    }
                    if (mode.trim().contains("4")) {
                        aem.append("Auto Flash Red-Eye, ");
                    }
                    if (!mode.trim().contains("5")) continue;
                    aem.append("External Flash, ");
                }
            }
            aeModes = aem.toString().length() > 0 ? aem.toString().substring(0, aem.toString().length() - 2) : "Not Available";
        }
        return aeModes;
    }

    public String fdCamera(int facing) {
        StringBuilder fdm = new StringBuilder();
        String faceDetectModes = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] fd;
                for (String mode : fd = Arrays.toString((int[])characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        fdm.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        fdm.append("Simple, ");
                    }
                    if (!mode.trim().contains("2")) continue;
                    fdm.append("Full, ");
                }
            }
            faceDetectModes = fdm.toString().length() > 0 ? fdm.toString().substring(0, fdm.toString().length() - 2) : "Not Available";
        }
        return faceDetectModes;
    }

    public String amCamera(int facing) {
        StringBuilder am = new StringBuilder();
        String aberrationMode = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] aberrationModes;
                for (String mode : aberrationModes = Arrays.toString((int[])characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        am.append("Off, ");
                    }
                    if (mode.trim().contains("1")) {
                        am.append("Fast, ");
                    }
                    if (!mode.trim().contains("2")) continue;
                    am.append("High Quality, ");
                }
            }
            aberrationMode = am.toString().length() > 0 ? am.toString().substring(0, am.toString().length() - 2) : "Not Available";
        }
        return aberrationMode;
    }

    public String osCamera(int facing) {
        StringBuilder osm = new StringBuilder();
        String opticalStable = null;
        if (Build.VERSION.SDK_INT >= 21) {
            CameraCharacteristics characteristics = null;
            try {
                characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (characteristics != null) {
                String[] os;
                for (String mode : os = Arrays.toString((int[])characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION)).replace("[", "").replace("]", "").split(",")) {
                    if (mode.trim().contains("0")) {
                        osm.append("Off, ");
                    }
                    if (!mode.trim().contains("1")) continue;
                    osm.append("On, ");
                }
            }
            opticalStable = osm.toString().length() > 0 ? osm.toString().substring(0, osm.toString().length() - 2) : "Not Available";
        }
        return opticalStable;
    }

    @SuppressLint(value={"DefaultLocale"})
    public String resolutionsCamera(int facing) {
        StringBuilder srm = new StringBuilder();
        String resolutions = null;
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                CameraCharacteristics characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
                StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap)characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size[] sizes = streamConfigurationMap.getOutputSizes(32);
                if (sizes != null) {
                    for (Size size : sizes) {
                        srm.append(String.format("%.2f", (double)(size.getWidth() * size.getHeight()) / 1000000.0)).append(" MP - ").append(size.getWidth()).append(" x ").append(size.getHeight()).append("\n");
                    }
                }
                if ((sizes = streamConfigurationMap.getOutputSizes(256)) != null) {
                    for (Size size : sizes) {
                        srm.append(String.format("%.2f", (double)(size.getWidth() * size.getHeight()) / 1000000.0)).append(" MP - ").append(size.getWidth()).append(" x ").append(size.getHeight()).append("\n");
                    }
                }
                if (srm.length() > 0) {
                    resolutions = srm.toString().substring(0, srm.toString().length() - 1);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resolutions;
    }

    public String getIp4Address() {
        String a = this.context.getResources().getString(R.string.connect_to_net);
        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intF : networkInterfaces) {
                List<InterfaceAddress> interfaceAddresses = intF.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    if (interfaceAddress.getAddress().isLoopbackAddress() || !(interfaceAddress.getAddress() instanceof Inet4Address)) continue;
                    if (interfaceAddress.getNetworkPrefixLength() % 8 == 0) {
                        a = interfaceAddress.getAddress().getHostAddress();
                        continue;
                    }
                    if (!a.equalsIgnoreCase(this.context.getResources().getString(R.string.connect_to_net))) continue;
                    a = interfaceAddress.getAddress().getHostAddress();
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return a;
    }

    public String getIp6Address() {
        String a = this.context.getResources().getString(R.string.connect_to_net);
        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intF : networkInterfaces) {
                List<InterfaceAddress> interfaceAddresses = intF.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    if (interfaceAddress.getAddress().isLoopbackAddress() || !(interfaceAddress.getAddress() instanceof Inet6Address)) continue;
                    a = interfaceAddress.getAddress().getHostAddress();
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        if (a.contains("%")) {
            int index = a.indexOf("%");
            a = a.substring(0, index);
        }
        return a;
    }

    public String subnet() {
        int x = 0;
        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            block2: for (NetworkInterface intF : networkInterfaces) {
                List<InterfaceAddress> interfaceAddresses = intF.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    if (interfaceAddress.getAddress().isLoopbackAddress() || !(interfaceAddress.getAddress() instanceof Inet4Address)) continue;
                    if (interfaceAddress.getNetworkPrefixLength() % 8 == 0) {
                        x = interfaceAddress.getNetworkPrefixLength();
                        continue block2;
                    }
                    x = interfaceAddress.getNetworkPrefixLength();
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            int bitValue = 0;
            if (x > 0) {
                int v = x >= 8 ? 0 : x % 8;
                if (v == 0) {
                    stringBuilder.append("255.");
                } else {
                    int j = 7;
                    while (v > 0) {
                        bitValue = (int)((double)bitValue + Math.pow(2.0, j));
                        --j;
                        --v;
                    }
                    stringBuilder.append(bitValue).append(".");
                }
                x -= 8;
                continue;
            }
            stringBuilder.append("0.");
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    public String getNetworkG(int type) {
        switch (type) {
            case 1: 
            case 2: 
            case 4: 
            case 7: 
            case 11: {
                return "2G";
            }
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 12: 
            case 14: 
            case 15: {
                return "3G";
            }
            case 13: {
                return "4G LTE";
            }
            case 20: {
                return "5G";
            }
        }
        return "Unknown";
    }

    public String getAxis(int num) {
        String axis = null;
        switch (num) {
            case 0: {
                axis = "AXIS_X";
                break;
            }
            case 1: {
                axis = "AXIS_Y";
                break;
            }
            case 2: {
                axis = "AXIS_PRESSURE";
                break;
            }
            case 3: {
                axis = "AXIS_SIZE";
                break;
            }
            case 4: {
                axis = "AXIS_TOUCH_MAJOR";
                break;
            }
            case 5: {
                axis = "AXIS_TOUCH_MINOR";
                break;
            }
            case 6: {
                axis = "AXIS_TOOL_MAJOR";
                break;
            }
            case 7: {
                axis = "AXIS_TOOL_MINOR";
                break;
            }
            case 8: {
                axis = "AXIS_ORIENTATION";
                break;
            }
            case 9: {
                axis = "AXIS_VSCROLL";
                break;
            }
            case 10: {
                axis = "AXIS_HSCROLL";
                break;
            }
            case 11: {
                axis = "AXIS_Z";
                break;
            }
            case 12: {
                axis = "AXIS_RX";
                break;
            }
            case 13: {
                axis = "AXIS_RY";
                break;
            }
            case 14: {
                axis = "AXIS_RZ";
                break;
            }
            case 15: {
                axis = "AXIS_HAT_X";
                break;
            }
            case 16: {
                axis = "AXIS_HAT_Y";
                break;
            }
            case 17: {
                axis = "AXIS_LTRIGGER";
                break;
            }
            case 18: {
                axis = "AXIS_RTRIGGER";
                break;
            }
            case 19: {
                axis = "AXIS_THROTTLE";
                break;
            }
            case 20: {
                axis = "AXIS_RUDDER";
                break;
            }
            case 21: {
                axis = "AXIS_WHEEL";
                break;
            }
            case 22: {
                axis = "AXIS_GAS";
                break;
            }
            case 23: {
                axis = "AXIS_BRAKE";
                break;
            }
            case 24: {
                axis = "AXIS_DISTANCE";
                break;
            }
            case 25: {
                axis = "AXIS_TILT";
                break;
            }
            case 26: {
                axis = "AXIS_SCROLL";
                break;
            }
            case 27: {
                axis = "AXIS_RELATIVE_X";
                break;
            }
            case 28: {
                axis = "AXIS_RELATIVE_Y";
                break;
            }
            case 32: {
                axis = "AXIS_GENERIC_1";
                break;
            }
            case 33: {
                axis = "AXIS_GENERIC_2";
                break;
            }
            case 34: {
                axis = "AXIS_GENERIC_3";
                break;
            }
            case 35: {
                axis = "AXIS_GENERIC_4";
                break;
            }
            case 36: {
                axis = "AXIS_GENERIC_5";
                break;
            }
            case 37: {
                axis = "AXIS_GENERIC_6";
                break;
            }
            case 38: {
                axis = "AXIS_GENERIC_7";
                break;
            }
            case 39: {
                axis = "AXIS_GENERIC_8";
                break;
            }
            case 40: {
                axis = "AXIS_GENERIC_9";
                break;
            }
            case 41: {
                axis = "AXIS_GENERIC_10";
                break;
            }
            case 42: {
                axis = "AXIS_GENERIC_11";
                break;
            }
            case 43: {
                axis = "AXIS_GENERIC_12";
                break;
            }
            case 44: {
                axis = "AXIS_GENERIC_13";
                break;
            }
            case 45: {
                axis = "AXIS_GENERIC_14";
                break;
            }
            case 46: {
                axis = "AXIS_GENERIC_15";
                break;
            }
            case 47: {
                axis = "AXIS_GENERIC_16";
            }
        }
        return axis;
    }

    public String getWifiChannel(int frequency) {
        int i;
        switch (frequency) {
            case 2412: {
                i = 1;
                break;
            }
            case 2417: {
                i = 2;
                break;
            }
            case 2422: {
                i = 3;
                break;
            }
            case 2427: {
                i = 4;
                break;
            }
            case 2432: {
                i = 5;
                break;
            }
            case 2437: {
                i = 6;
                break;
            }
            case 2442: {
                i = 7;
                break;
            }
            case 2447: {
                i = 8;
                break;
            }
            case 2452: {
                i = 9;
                break;
            }
            case 2457: {
                i = 10;
                break;
            }
            case 2462: {
                i = 11;
                break;
            }
            case 2467: {
                i = 12;
                break;
            }
            case 2472: {
                i = 13;
                break;
            }
            case 2484: {
                i = 14;
                break;
            }
            default: {
                return this.context.getResources().getString(R.string.unable);
            }
        }
        return String.valueOf(i);
    }

    public ArrayList<ThermalModel> getThermalList() {
        return this.thermalList;
    }

    @SuppressLint(value={"PrivateApi"})
    public String getSimState() {
        TelephonyManager telephonyManager = (TelephonyManager)this.context.getSystemService("phone");
        String state = "Unknown";
        switch (telephonyManager.getSimState()) {
            case 0: {
                state = "Unknown";
                break;
            }
            case 1: {
                state = "Absent";
                break;
            }
            case 2: {
                state = "PIN Required";
                break;
            }
            case 3: {
                state = "PUK Required";
                break;
            }
            case 4: {
                state = "Locked";
                break;
            }
            case 5: {
                state = "Ready";
                break;
            }
            case 6: {
                state = "Not Ready";
                break;
            }
            case 7: {
                state = "Permanently Disabled";
                break;
            }
            case 8: {
                state = "Card IO Error";
                break;
            }
            case 9: {
                state = "Card Restricted";
            }
        }
        return state;
    }

    public Map<String, String> getAndroidProperty() {
        BufferedReader bufferedReader = null;
        String pattern = "\\[(.*?)\\]: \\[(.*?)\\]";
        Pattern compiledPattern = Pattern.compile(pattern);
        HashMap<String, String> property = new HashMap<String, String>();
        try {
            String line;
            Process process = Runtime.getRuntime().exec("getprop");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int index = 1;
            while ((line = bufferedReader.readLine()) != null) {
                ++index;
                System.out.println(line);
                Matcher matcher = compiledPattern.matcher(line);
                if (matcher.find()) {
                    String key = matcher.group(1);
                    String value = matcher.group(2);
                    property.put(key, value);
                    continue;
                }
                HVLog.d("ro\u5c5e\u6027 \u5728" + line + "\u6ca1\u6709\u53d1\u73b0\u5339\u914d");
            }
            HVLog.d("\u4e00\u5171\u6709" + index + "\u4e2aRO\u5c5e\u6027");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return property;
    }

    public Map<String, String> getJavaProperties() {
        Properties properties = System.getProperties();
        Set<String> propertyKeys = properties.stringPropertyNames();
        HashMap<String, String> property = new HashMap<String, String>();
        for (String key : propertyKeys) {
            String value = properties.getProperty(key);
            property.put(key, value);
        }
        return property;
    }

    public Map<String, String> settingsProperty(Uri uri) {
        Uri globalSettingsUri;
        HashMap<String, String> property = new HashMap<String, String>();
        ContentResolver resolver = this.context.getContentResolver();
        Cursor cursor = resolver.query(globalSettingsUri = uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex("name"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                property.put(key, value);
            }
            cursor.close();
        }
        return property;
    }
}

