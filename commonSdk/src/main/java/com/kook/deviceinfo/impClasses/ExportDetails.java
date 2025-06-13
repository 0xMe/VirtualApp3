/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.bluetooth.BluetoothAdapter
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ConfigurationInfo
 *  android.content.pm.FeatureInfo
 *  android.content.pm.PackageManager
 *  android.hardware.camera2.CameraCharacteristics
 *  android.hardware.camera2.CameraCharacteristics$Key
 *  android.hardware.camera2.CameraManager
 *  android.media.MediaCodecInfo
 *  android.media.MediaCodecList
 *  android.media.MediaDrm
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.provider.Settings$Global
 *  android.provider.Settings$Secure
 *  android.provider.Settings$SettingNotFoundException
 *  android.provider.Settings$System
 *  android.util.DisplayMetrics
 *  android.view.Display$HdrCapabilities
 *  android.view.InputDevice
 *  android.webkit.WebSettings
 *  androidx.annotation.RequiresApi
 *  androidx.core.content.ContextCompat
 *  com.alibaba.fastjson.JSON
 *  com.alibaba.fastjson.JSONObject
 *  com.kook.librelease.R$string
 */
package com.kook.deviceinfo.impClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.InputDevice;
import android.webkit.WebSettings;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kook.common.jni.FileInfoNative;
import com.kook.common.systemutil.SystemManager;
import com.kook.common.util.FileTools;
import com.kook.common.utils.HVLog;
import com.kook.deviceinfo.constant.BuildInfoConstant;
import com.kook.deviceinfo.constant.SystemFileConStant;
import com.kook.deviceinfo.data.GeneralData;
import com.kook.deviceinfo.impClasses.BuildInfo;
import com.kook.deviceinfo.impClasses.JsonData;
import com.kook.deviceinfo.models.InputModel;
import com.kook.deviceinfo.models.SensorListModel;
import com.kook.deviceinfo.persistence.IniFile;
import com.kook.deviceinfo.util.AppInfoHelper;
import com.kook.librelease.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import mirror.RefClassAttr;

public class ExportDetails {
    private final Context context;
    BuildInfo buildInfo;
    CameraManager cameraManager;
    String[] cameraIds;
    JsonData jsonData;
    ArrayList<SensorListModel> sensorListModels;
    ArrayList<InputModel> inputListModels;
    private final IniFile mIniFile = IniFile.getInstance(IniFile.SYSTEM_EXPORT_CONFIG);
    private boolean isSystemSign = false;

    public ExportDetails(Context context) {
        this.context = context;
        this.buildInfo = new BuildInfo(context);
        this.jsonData = new JsonData(context);
        this.isSystemSign = SystemManager.isSystemSign(context);
    }

    public IniFile getIniFile() {
        return this.mIniFile;
    }

    public void setSensorList(ArrayList<SensorListModel> sensorList) {
        this.sensorListModels = sensorList;
    }

    public void setInputList(ArrayList<InputModel> inputList) {
        this.inputListModels = inputList;
    }

    @SuppressLint(value={"HardwareIds"})
    public void device() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dateFormat.format(date);
        String model = this.context.getResources().getString(R.string.model) + " : ";
        String manufacturer = this.context.getResources().getString(R.string.manu) + " : ";
        String device = this.context.getResources().getString(R.string.device_lav) + " : ";
        String board = this.context.getResources().getString(R.string.board) + " : ";
        String hardware = this.context.getResources().getString(R.string.hardware) + " : ";
        String device_id = this.context.getResources().getString(R.string.device_id) + " : ";
        String deviceType = this.context.getResources().getString(R.string.devicetype) + " : ";
        String buildprint = this.context.getResources().getString(R.string.build_print) + " : ";
        String usbhost = this.context.getResources().getString(R.string.usb_host) + " : ";
        String macadd = this.context.getResources().getString(R.string.mac_address) + " : ";
        String usbSupport = this.context.getResources().getString(R.string.supported);
        if (!this.context.getPackageManager().hasSystemFeature("android.hardware.usb.host")) {
            usbSupport = this.context.getResources().getString(R.string.not_supported);
        }
        String advertId = this.context.getResources().getString(R.string.advertid) + " : ";
        String timezone = this.context.getResources().getString(R.string.timezone) + " : ";
        SimpleDateFormat d = new SimpleDateFormat("z", Locale.getDefault());
        String time = d.format(System.currentTimeMillis());
        String t = TimeZone.getDefault().getDisplayName() + " (" + time + ")";
        this.mIniFile.set(BuildInfoConstant.Telephony.TYPE, BuildInfoConstant.Telephony.TELEPHONY_TYPE, this.buildInfo.getPhoneType());
    }

    public void deviceFeatures() {
        FeatureInfo[] featureInfo;
        for (FeatureInfo feature : featureInfo = this.context.getPackageManager().getSystemAvailableFeatures()) {
            this.mIniFile.set(BuildInfoConstant.SystemAvailableFeatures.TYPE, feature.name, JSON.toJSON((Object)feature).toString());
        }
    }

    public void drmDetails() {
        MediaDrm mediaDrm;
        String[] wide = new String[9];
        String[] clearkey = new String[2];
        try {
            mediaDrm = new MediaDrm(UUID.fromString("edef8ba9-79d6-4ace-a3c8-27dcd51d21ed"));
            wide[0] = mediaDrm.getPropertyString("vendor");
            wide[1] = mediaDrm.getPropertyString("version");
            wide[2] = mediaDrm.getPropertyString("algorithms");
            wide[3] = mediaDrm.getPropertyString("systemId");
            wide[4] = mediaDrm.getPropertyString("securityLevel");
            wide[5] = mediaDrm.getPropertyString("maxHdcpLevel");
            wide[6] = mediaDrm.getPropertyString("maxNumberOfSessions");
            wide[7] = mediaDrm.getPropertyString("usageReportingSupport");
            wide[8] = mediaDrm.getPropertyString("hdcpLevel");
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "vendor", wide[0]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "version", wide[1]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "algorithms", wide[2]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "systemId", wide[3]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "securityLevel", wide[4]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "maxHdcpLevel", wide[5]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "maxNumberOfSessions", wide[6]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "usageReportingSupport", wide[7]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "hdcpLevel", wide[8]);
            mediaDrm.release();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mediaDrm = new MediaDrm(UUID.fromString("e2719d58-a985-b3c9-781a-b030af78d30e"));
            clearkey[0] = mediaDrm.getPropertyString("vendor");
            clearkey[1] = mediaDrm.getPropertyString("version");
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "vendor", clearkey[0]);
            this.mIniFile.set(BuildInfoConstant.SystemMediaDrm.TYPE, "version", clearkey[1]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void system() {
        ActivityManager activityManager = (ActivityManager)this.context.getSystemService("activity");
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();
        this.mIniFile.set(BuildInfoConstant.Graphics.TYPE, BuildInfoConstant.Graphics.GL_ES_VERSION, info.getGlEsVersion());
        this.mIniFile.set(BuildInfoConstant.GeneralDataInfo.TYPE, BuildInfoConstant.GeneralDataInfo.LANGUAGE, Locale.getDefault().getDisplayLanguage());
    }

    public void cpu() {
        for (String fingerFile : SystemFileConStant.SOC_FILE_LIST) {
            this.mIniFile.set(BuildInfoConstant.FingerprintInfo.TYPE, fingerFile, FileTools.readFile(fingerFile));
        }
        int coresCount = this.buildInfo.getCoresCount();
        for (int i = 0; i < coresCount; ++i) {
            String minfreq = "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_min_freq";
            String maxfreq = "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq";
            this.mIniFile.set(BuildInfoConstant.FingerprintInfo.TYPE, minfreq, FileTools.readFile(minfreq));
            this.mIniFile.set(BuildInfoConstant.FingerprintInfo.TYPE, maxfreq, FileTools.readFile(maxfreq));
        }
        String osArch = System.getProperty("os.arch");
        this.mIniFile.set(BuildInfoConstant.GeneralDataInfo.TYPE, BuildInfoConstant.GeneralDataInfo.OS_ARCH, osArch);
        this.mIniFile.set(BuildInfoConstant.GeneralDataInfo.TYPE, BuildInfoConstant.GeneralDataInfo.SUPPORTED_ABIS, Arrays.toString(Build.SUPPORTED_ABIS));
    }

    public void battery() {
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.HEALTH, this.buildInfo.getBatteryHealth());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.STATUS, this.buildInfo.getBatteryStatus());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.LEVEL, this.buildInfo.getBatteryLevel());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.VOLTAGE, this.buildInfo.getVoltage());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.POWER_SOURCE, this.buildInfo.getPowerSource());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.TECHNOLOGY, this.buildInfo.getBatteryTechnology());
        this.mIniFile.set(BuildInfoConstant.Battery.TYPE, BuildInfoConstant.Battery.CAPACITY, this.buildInfo.getBatteryCapacity());
    }

    @SuppressLint(value={"DefaultLocale"})
    public void display() {
        Display.HdrCapabilities hdrCapabilities;
        String fontscale = "";
        String size = "";
        String refreshrate = "";
        String hdr = "";
        String hdr_cap = "";
        String bright_level = "";
        String bright_mode = "";
        String screen_timeout = "";
        String orientation = "";
        String pixel = "";
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)this.context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int h = metrics.heightPixels;
        int w = metrics.widthPixels;
        int dpi = metrics.densityDpi;
        String refreshRate = String.format("%.1f", Float.valueOf(((Activity)this.context).getWindowManager().getDefaultDisplay().getRefreshRate())) + " Hz";
        int brightness = 0;
        int time = 0;
        float font = 0.0f;
        try {
            brightness = Settings.System.getInt((ContentResolver)this.context.getContentResolver(), (String)"screen_brightness");
            time = Settings.System.getInt((ContentResolver)this.context.getContentResolver(), (String)"screen_off_timeout");
            font = this.context.getResources().getConfiguration().fontScale;
        }
        catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        String bright = brightness * 100 / 255 + " %";
        String timeout = time / 1000 + " " + this.context.getResources().getString(R.string.seconds);
        String fontstr = String.valueOf(font);
        String hdr_support = this.context.getResources().getString(R.string.supported);
        String hdr_capable = this.context.getResources().getString(R.string.none);
        StringBuilder str = new StringBuilder();
        if (Build.VERSION.SDK_INT >= 24 && (hdrCapabilities = ((Activity)this.context).getWindowManager().getDefaultDisplay().getHdrCapabilities()).getSupportedHdrTypes().length != 0) {
            int[] hdrtypes;
            block8: for (int hdrtype : hdrtypes = hdrCapabilities.getSupportedHdrTypes()) {
                switch (hdrtype) {
                    case 1: {
                        str.append("Dolby Vision HDR\n");
                        continue block8;
                    }
                    case 2: {
                        str.append("HDR10\n");
                        continue block8;
                    }
                    case 3: {
                        str.append("Hybrid Log-Gamma HDR\n");
                        continue block8;
                    }
                    case 4: {
                        str.append("HDR10+\n");
                    }
                }
            }
        }
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.RESOLUTION, w + "x" + h);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.DENSITY, dpi + "dpi");
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.FONTSCALE, fontstr);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.SIZE, this.buildInfo.getScreenSize());
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.REFRESHRATE, refreshRate);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.HDR, hdr_support);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.HDR_CAP, hdr_cap);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.BRIGHT_LEVEL, bright);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.BRIGHT_MODE, this.buildInfo.getBrightnessMode());
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.SCREEN_TIMEOUT, timeout);
        this.mIniFile.set(BuildInfoConstant.Display.TYPE, BuildInfoConstant.Display.ORIENTATION, this.buildInfo.getOrientation());
    }

    public void memory() {
        String ram = this.context.getResources().getString(R.string.ram);
        String sys = this.context.getResources().getString(R.string.sys_store);
        String internal = this.context.getResources().getString(R.string.in_store);
        String external = this.context.getResources().getString(R.string.ext_store);
        String totalMem = this.context.getResources().getString(R.string.total_mem) + " : ";
        String usedMem = this.context.getResources().getString(R.string.used_mem) + " : ";
        String availMem = this.context.getResources().getString(R.string.avail_mem) + " : ";
        String system = Environment.getRootDirectory().getPath();
        String data = Environment.getDataDirectory().getPath();
        ActivityManager manager = (ActivityManager)this.context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);
        File[] files = ContextCompat.getExternalFilesDirs((Context)this.context, null);
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.MEMORY_INFO_TOTALMEM, memoryInfo.totalMem);
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.MEMORY_INFO_USEMEM, memoryInfo.totalMem - memoryInfo.availMem);
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.MEMORY_INFO_AVAILMEM, memoryInfo.availMem);
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.SYSTEM_INFO_TOTALMEM, this.buildInfo.getTotalStorageInfo(system));
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.SYSTEM_INFO_USEMEM, this.buildInfo.getUsedStorageInfo(system));
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.SYSTEM_INFO_AVAILMEM, this.buildInfo.getTotalStorageInfo(system) - this.buildInfo.getUsedStorageInfo(system));
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.INTERNAL_INFO_TOTALMEM, this.buildInfo.getTotalStorageInfo(data));
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.INTERNAL_INFO_USEMEM, this.buildInfo.getUsedStorageInfo(data));
        this.mIniFile.set(BuildInfoConstant.StorageSpace.TYPE, BuildInfoConstant.StorageSpace.INTERNAL_INFO_AVAILMEM, this.buildInfo.getTotalStorageInfo(data) - this.buildInfo.getUsedStorageInfo(data));
    }

    @RequiresApi(api=29)
    public void codecs() {
        MediaCodecInfo[] mediaCodecInfo;
        MediaCodecList codecList = new MediaCodecList(1);
        for (MediaCodecInfo codecInfo : mediaCodecInfo = codecList.getCodecInfos()) {
            JSONObject mElement = new JSONObject();
            Object[] supportedTypes = codecInfo.getSupportedTypes();
            mElement.put("supportedTypes", (Object)Arrays.toString(supportedTypes));
            String canonicalName = codecInfo.getCanonicalName();
            mElement.put("canonicalName", (Object)canonicalName);
            String name = codecInfo.getName();
            mElement.put("name", (Object)name);
            boolean alias = codecInfo.isAlias();
            mElement.put("alias", (Object)alias);
            boolean encoder = codecInfo.isEncoder();
            mElement.put("encoder", (Object)encoder);
            boolean softwareOnly = codecInfo.isSoftwareOnly();
            mElement.put("softwareOnly", (Object)softwareOnly);
            boolean hardwareAccelerated = codecInfo.isHardwareAccelerated();
            mElement.put("hardwareAccelerated", (Object)hardwareAccelerated);
            boolean vendor = codecInfo.isVendor();
            mElement.put("vendor", (Object)vendor);
            String toJson = mElement.toString();
            this.mIniFile.set(BuildInfoConstant.SystemMediaCodecList.TYPE, name, toJson);
        }
    }

    public void glExtensions() {
    }

    public void inputDevices() {
        int[] id2;
        for (int facing : id2 = InputDevice.getDeviceIds()) {
            InputDevice inputDevice = InputDevice.getDevice((int)facing);
            String jsonString = JSON.toJSONString((Object)inputDevice);
            this.mIniFile.set(BuildInfoConstant.SystemInputDevice.TYPE, String.valueOf(facing), jsonString);
        }
    }

    public void cameraApi21() {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                this.cameraManager = (CameraManager)this.context.getSystemService("camera");
                this.cameraIds = this.cameraManager.getCameraIdList();
                this.mIniFile.setList(BuildInfoConstant.Camera.TYPE, BuildInfoConstant.Camera.CAMERA_IDS, Arrays.asList(this.cameraIds));
                for (String cameraId : this.cameraIds) {
                    this.camera21Facing(Integer.parseInt(cameraId));
                }
            }
            catch (Exception e) {
                HVLog.printException(e);
            }
        }
    }

    private void camera21Facing(int facing) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                CameraCharacteristics cameraCharacteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
                cameraCharacteristics.getKeys();
                CameraCharacteristics characteristics = this.cameraManager.getCameraCharacteristics(String.valueOf(facing));
                JSONObject mElement = new JSONObject();
                for (CameraCharacteristics.Key key : characteristics.getKeys()) {
                    Object value = characteristics.get(key);
                    mElement.put(key.getName(), (Object)this.valueToString(value));
                }
                this.mIniFile.set(BuildInfoConstant.Camera.TYPE, "CameraCharacteristics-" + String.valueOf(facing), mElement.toJSONString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String valueToString(Object value) {
        if (value == null) {
            return "";
        }
        if (value.getClass().isArray()) {
            if (value instanceof int[]) {
                return Arrays.toString((int[])value);
            }
            if (value instanceof float[]) {
                return Arrays.toString((float[])value);
            }
            if (value instanceof String[]) {
                return Arrays.toString((String[])value);
            }
            if (value instanceof boolean[]) {
                return Arrays.toString((boolean[])value);
            }
            return Arrays.toString((Object[])value);
        }
        return value.toString();
    }

    public void systemProperty() {
        Map<String, String> property = this.buildInfo.getAndroidProperty();
        for (Map.Entry<String, String> entry : property.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.mIniFile.set(BuildInfoConstant.SystemProperty.TYPE, key, value);
        }
    }

    public void javaProperty() {
        Map<String, String> property = this.buildInfo.getJavaProperties();
        HVLog.d(" ========== javaProperty \u6709" + property.size());
        for (Map.Entry<String, String> entry : property.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.mIniFile.set(BuildInfoConstant.JavaProperty.TYPE, key, value);
        }
    }

    public void settingsProperty() {
        Map<String, String> propertyGlobal = this.buildInfo.settingsProperty(Settings.Global.CONTENT_URI);
        HVLog.d(" ========== settingsProperty \u6709" + propertyGlobal.size());
        for (Map.Entry<String, String> entry : propertyGlobal.entrySet()) {
            String string2 = entry.getKey();
            String value = entry.getValue();
            this.mIniFile.set(BuildInfoConstant.SettingsGlobalProperty.TYPE, string2, value);
        }
        Map<String, String> propertySystem = this.buildInfo.settingsProperty(Settings.System.CONTENT_URI);
        HVLog.d(" ========== propertySystem \u6709" + propertySystem.size());
        for (Map.Entry<String, String> entry : propertySystem.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.mIniFile.set(BuildInfoConstant.SettingsSystemProperty.TYPE, key, value);
        }
        Map<String, String> map = this.buildInfo.settingsProperty(Settings.Secure.CONTENT_URI);
        HVLog.d(" ========== propertySecure \u6709" + map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.mIniFile.set(BuildInfoConstant.SettingsSecureProperty.TYPE, key, value);
        }
    }

    @SuppressLint(value={"DefaultLocale"})
    public String formattedValue(long l) {
        String s;
        if ((double)l > 1.073741824E10) {
            float f = (float)l / 1.07374182E9f;
            s = String.format("%.1f", Float.valueOf(f)) + " GB";
        } else if ((double)l > 1.048576E7 && (double)l <= 1.073741824E10) {
            float f = (float)l / 1048576.0f;
            s = String.format("%.1f", Float.valueOf(f)) + " MB";
        } else if (l > 1024L && (double)l <= 1.048576E7) {
            float f = (float)l / 1024.0f;
            s = String.format("%.1f", Float.valueOf(f)) + " KB";
        } else {
            float f = l;
            s = String.format("%.2f", Float.valueOf(f)) + " Bytes";
        }
        return s;
    }

    public void netlink() {
        String address;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ContextCompat.checkSelfPermission((Context)this.context, (String)"android.permission.BLUETOOTH_CONNECT") != 0) {
            // empty if block
        }
        if (bluetoothAdapter == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 31) {
            if (ContextCompat.checkSelfPermission((Context)this.context, (String)"android.permission.BLUETOOTH_CONNECT") != 0) {
                return;
            }
            address = bluetoothAdapter.getAddress();
        } else {
            address = bluetoothAdapter.getAddress();
        }
        this.mIniFile.set(BuildInfoConstant.GeneralDataInfo.BT_INFO, "bt_address", address);
    }

    public void fingerprintFile() {
        for (String fingerFile : SystemFileConStant.SOC_FILE_LIST) {
            this.mIniFile.set(BuildInfoConstant.FingerprintInfo.TYPE, fingerFile, FileTools.readFile(fingerFile));
        }
    }

    public void systemFileInfo() {
        String fileInfoToJson;
        long timeMillis = System.currentTimeMillis();
        List<String> fileList = FileInfoNative.getFileList("/system");
        for (String file : fileList) {
            String fileInfoToJson2 = FileInfoNative.fileInfoToJson(file);
            this.mIniFile.set(BuildInfoConstant.SystemFileInfo.TYPE, file, fileInfoToJson2);
        }
        int size = this.mIniFile.get(BuildInfoConstant.SystemFileInfo.TYPE).getValues().size();
        HVLog.d("\u67e5\u770b\u5c06 system \u4e0b\u6240\u6709\u6587\u4ef6\u4fe1\u606f\u4fdd\u5b58\u8017\u65f6" + (System.currentTimeMillis() - timeMillis) + "  \u6587\u4ef6\u6709" + fileList.size() + "\u4e2a    \u67e5\u770b\u6570\u636e\u6709" + size + " \u6761");
        timeMillis = System.currentTimeMillis();
        fileList = FileInfoNative.getFileList("/vendor");
        for (String file : fileList) {
            fileInfoToJson = FileInfoNative.fileInfoToJson(file);
            this.mIniFile.set(BuildInfoConstant.SystemFileInfo.TYPE, file, fileInfoToJson);
        }
        size = this.mIniFile.get(BuildInfoConstant.SystemFileInfo.TYPE).getValues().size();
        HVLog.d("\u67e5\u770b\u5c06 vendor \u4e0b\u6240\u6709\u6587\u4ef6\u4fe1\u606f\u4fdd\u5b58\u8017\u65f6" + (System.currentTimeMillis() - timeMillis) + "  \u6587\u4ef6\u6709" + fileList.size() + "\u4e2a    \u67e5\u770b\u6570\u636e\u6709" + size + " \u6761");
        timeMillis = System.currentTimeMillis();
        fileList = FileInfoNative.getFileList("/data/system");
        for (String file : fileList) {
            fileInfoToJson = FileInfoNative.fileInfoToJson(file);
            this.mIniFile.set(BuildInfoConstant.SystemFileInfo.TYPE, file, fileInfoToJson);
        }
        size = this.mIniFile.get(BuildInfoConstant.SystemFileInfo.TYPE).getValues().size();
        HVLog.d("\u67e5\u770b\u5c06 /data/system \u4e0b\u6240\u6709\u6587\u4ef6\u4fe1\u606f\u4fdd\u5b58\u8017\u65f6" + (System.currentTimeMillis() - timeMillis) + "  \u6587\u4ef6\u6709" + fileList.size() + "\u4e2a    \u67e5\u770b\u6570\u636e\u6709" + size + " \u6761");
    }

    public void systemAppInfo() {
        long timeMillis = System.currentTimeMillis();
        PackageManager packageManager = this.context.getPackageManager();
        List packagelist = packageManager.getInstalledApplications(128);
        for (ApplicationInfo applicationInfo : packagelist) {
            String jsonToAppInfo = AppInfoHelper.getJsonToAppInfo(this.context, applicationInfo.packageName);
            this.mIniFile.set(BuildInfoConstant.SystemAppInfo.TYPE, applicationInfo.packageName, jsonToAppInfo);
        }
        int count = this.mIniFile.get(BuildInfoConstant.SystemAppInfo.TYPE).getValues().size();
        HVLog.d("\u67e5\u770b\u7cfb\u7edf\u6709  " + count + "  \u4e2aAPP,\u8017\u65f6" + (System.currentTimeMillis() - timeMillis));
    }

    public void userAgent() {
        String systemUserAgent = System.getProperty("http.agent");
        String webviewUserAgent = WebSettings.getDefaultUserAgent((Context)this.context);
        this.mIniFile.set(BuildInfoConstant.UserAgent.TYPE, "systemUserAgent", systemUserAgent);
        this.mIniFile.set(BuildInfoConstant.UserAgent.TYPE, "webviewUserAgent", webviewUserAgent);
    }

    public void generalDataInfo() {
        long timeMillis = System.currentTimeMillis();
        GeneralData generalData = new GeneralData();
        List<String> classField = RefClassAttr.getClassField(GeneralData.class);
        for (String fieldName : classField) {
            String valueByObject = RefClassAttr.getFieldStringValueByObject(fieldName, generalData);
            this.mIniFile.set(BuildInfoConstant.GeneralDataInfo.TYPE, fieldName, valueByObject);
        }
    }

    public void systemSensorInfo() {
        for (SensorListModel sensor : this.sensorListModels) {
            this.mIniFile.set(BuildInfoConstant.SensorInfo.TYPE, String.valueOf(sensor.getName()), sensor.toJSON());
        }
    }

    public void systemInputInfo() {
        for (InputModel input : this.inputListModels) {
            this.mIniFile.set(BuildInfoConstant.InputInfo.TYPE, input.getName(), input.toJSON());
        }
    }
}

