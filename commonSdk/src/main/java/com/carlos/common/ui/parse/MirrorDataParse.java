/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson.JSON
 *  com.alibaba.fastjson.JSONObject
 */
package com.carlos.common.ui.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carlos.common.App;
import com.carlos.common.ui.activity.base.BaseActivity;
import com.carlos.common.utils.FileTools;
import com.carlos.common.utils.SPTools;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VDeviceManager;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.remote.vloc.VLocation;

public class MirrorDataParse {
    JSONObject mElement = new JSONObject();

    public String getBackupData(String packageName, int userId) {
        this.mElement.clear();
        this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+OWUzJC1iDFk7KgcMVg==")), packageName);
        this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc2M28hAiw=")), userId);
        SettingConfig.FakeWifiStatus fakeWifiStatus = App.getApp().mConfig.getFakeWifiStatus(packageName, userId);
        this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki02CWgFSFo=")), fakeWifiStatus == null ? "" : fakeWifiStatus.getSSID());
        this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+OQ==")), fakeWifiStatus == null ? "" : fakeWifiStatus.getMAC());
        this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj02KWUVMFo=")), fakeWifiStatus == null ? "" : fakeWifiStatus.getBSSID());
        VLocation location = VLocationManager.get().getLocation(packageName, userId);
        if (location != null) {
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+LGUaMAViHjBF")), location.latitude);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IxgACGgzAgZmDgo/")), location.longitude);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggELGUaMAViHjBF")), location.altitude);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWwaFjd9JwZF")), Float.valueOf(location.accuracy));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki06M2gVMFo=")), Float.valueOf(location.speed));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4uP28jAiZiJ1RF")), Float.valueOf(location.bearing));
        }
        int deviceId = BaseActivity.getDeviceId(packageName, userId);
        VDeviceConfig deviceConfig = VDeviceManager.get().getDeviceConfig(deviceId);
        boolean enable = VDeviceManager.get().isEnable(deviceId);
        if (enable) {
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsMEWIhMFo=")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsMEWIhMFo="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHlo=")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHlo="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhUMUmAINBNuEVRF")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhUMUmAINBNuEVRF"))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAH0bLBU=")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAH0bLBU="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjwAEWchMFo=")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjwAEWchMFo="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYYA2cLHhFvAVRF")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYYA2cLHhFvAVRF"))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAYqVg==")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAYqVg=="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwY+U2QbOBFlJQpKOzsMAg==")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwY+U2QbOBFlJQpKOzsMAg=="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JTwYU2AxNF9pHywJIjw2Vg==")), deviceConfig.getProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JTwYU2AxNF9pHywJIjw2Vg=="))));
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmUVJCQ=")), deviceConfig.serial);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguLmUVLCtrDgpF")), deviceConfig.deviceId);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAg2OX0VMFo=")), deviceConfig.iccId);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4YPmUbEjd9J1RF")), deviceConfig.wifiMac);
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iHAYw")), deviceConfig.androidId);
        } else {
            this.elementAddProperty(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguLmUVLCtrDgoVKj0iOG8zGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4+Dm8zNFo=")));
        }
        return this.mElement.toString();
    }

    public void parseBackupData(String filePath) {
        String readFile = FileTools.readFile(filePath);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uP2gLOC9gHjMi")) + readFile + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFOC9gHjACLwg2MngVSFo=")) + filePath);
        JSONObject jsonObject = JSON.parseObject((String)readFile);
        if (jsonObject == null) {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YAwssAglADkcsJRRBEloz")));
            return;
        }
        String packageName = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+OWUzJC1iDFk7KgcMVg==")));
        int userId = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc2M28hAiw=")));
        String ssid = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki02CWgFSFo=")));
        String mac = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+OQ==")));
        String bssid = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj02KWUVMFo=")));
        String SSID_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki02CWgIGiFiAQZF")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + userId;
        String MAC_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+OWYzQStnAVRF")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy5SVg==")) + userId;
        SPTools.putString(VirtualCore.get().getContext(), SSID_KEY, ssid);
        SPTools.putString(VirtualCore.get().getContext(), MAC_KEY, mac);
        if (jsonObject.containsKey((Object)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+LGUaMAViHjBF"))) || jsonObject.containsKey((Object)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IxgACGgzAgZmDgo/")))) {
            VLocation mLatLng = new VLocation();
            mLatLng.latitude = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+LGUaMAViHjBF")));
            mLatLng.longitude = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IxgACGgzAgZmDgo/")));
            mLatLng.altitude = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggELGUaMAViHjBF")));
            mLatLng.accuracy = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWwaFjd9JwZF")));
            mLatLng.speed = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki06M2gVMFo=")));
            mLatLng.bearing = this.getPropertyInt(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4uP28jAiZiJ1RF")));
            VirtualLocationManager.get().setMode(userId, packageName, 2);
            VirtualLocationManager.get().setLocation(userId, packageName, mLatLng);
        }
        int deviceId = BaseActivity.getDeviceId(packageName, userId);
        VDeviceConfig deviceConfig = VDeviceManager.get().getDeviceConfig(deviceId);
        if (!jsonObject.containsKey((Object)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguLmUVLCtrDgoVKj0iOG8zGlo=")))) {
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsMEWIhMFo=")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjsMEWIhMFo="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHlo=")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwYAWWAbHlo="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhUMUmAINBNuEVRF")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IhUMUmAINBNuEVRF"))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAH0bLBU=")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAH0bLBU="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjwAEWchMFo=")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JjwAEWchMFo="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYYA2cLHhFvAVRF")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYYA2cLHhFvAVRF"))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAYqVg==")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAYqVg=="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwY+U2QbOBFlJQpKOzsMAg==")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwY+U2QbOBFlJQpKOzsMAg=="))));
            deviceConfig.setProp(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JTwYU2AxNF9pHywJIjw2Vg==")), this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JTwYU2AxNF9pHywJIjw2Vg=="))));
            deviceConfig.serial = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmUVJCQ=")));
            deviceConfig.deviceId = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRguLmUVLCtrDgpF")));
            deviceConfig.iccId = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAg2OX0VMFo=")));
            deviceConfig.wifiMac = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4YPmUbEjd9J1RF")));
            deviceConfig.androidId = this.getPropertyString(jsonObject, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iHAYw")));
            VDeviceManager.get().updateDeviceConfig(deviceId, deviceConfig);
        }
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlYdGkZJAx9YAwssAglADkcvPQ5BA1oR")));
    }

    private void elementAddProperty(String property, Object object) {
        if (this.mElement.containsKey((Object)property)) {
            this.mElement.remove((Object)property);
        }
        if (object instanceof String) {
            this.mElement.put(property, (Object)((String)object));
        } else if (object instanceof Integer) {
            this.mElement.put(property, (Object)((Integer)object));
        } else if (object instanceof Boolean) {
            this.mElement.put(property, (Object)((Boolean)object));
        } else if (object instanceof Long) {
            this.mElement.put(property, (Object)((Long)object));
        } else if (object instanceof Double) {
            this.mElement.put(property, (Object)((Double)object));
        } else if (object instanceof Float) {
            this.mElement.put(property, (Object)((Float)object));
        } else {
            throw new NullPointerException(property + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl5WOHsJIFo=")) + object + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcREMXFzNZXl4oAgkdDGwaAiVlMz4tWhsCJxlNDCEGFSIvXiIqVg==")));
        }
    }

    private String getPropertyString(JSONObject jsonObject, String key) {
        if (jsonObject.containsKey((Object)key)) {
            return jsonObject.getString(key);
        }
        return "";
    }

    private int getPropertyInt(JSONObject jsonObject, String key) {
        if (jsonObject.containsKey((Object)key)) {
            return jsonObject.getIntValue(key);
        }
        return -1;
    }

    private long getPropertyLong(JSONObject jsonObject, String key) {
        if (jsonObject.containsKey((Object)key)) {
            return jsonObject.getLongValue(key);
        }
        return -1L;
    }

    private boolean getPropertyBoolean(JSONObject jsonObject, String key) {
        if (jsonObject.containsKey((Object)key)) {
            return jsonObject.getBooleanValue(key);
        }
        return false;
    }
}

