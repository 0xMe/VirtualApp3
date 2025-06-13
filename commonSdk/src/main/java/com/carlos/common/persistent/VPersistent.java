/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.carlos.common.persistent;

import android.os.Parcel;
import android.os.Parcelable;
import com.carlos.common.network.StringFog;
import java.util.HashMap;
import java.util.Map;

public class VPersistent
implements Parcelable {
    public static final int VERSION = 3;
    public static final String PRODUCT_ENV_SIT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC42CQ=="));
    public static final String PRODUCT_ENV_PROD = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBdfKmkVSFo="));
    public static String PRODUCT_ENV_KEY;
    public int requestCount = 0;
    public long currentTimeMillis = 0L;
    public static final String upgradeEnforce;
    public static final String upgradeVersion;
    public static String fileName;
    public static final String fileMd5;
    public static final String urlHost;
    public static final String uploadAppUrl;
    public static final String downloadAppUrl;
    public static final String uploadDevicesUrl;
    public static final String downloadDevicesUrl;
    public static final String requestTime;
    public static final String heartbeatCount;
    public final Map<String, String> buildAllConfig = new HashMap<String, String>();
    public static final String adbHook;
    public static final String backupRecovery;
    public static final String dingtalk;
    public static final String dingtalkPic;
    public static final String hookXposed;
    public static final String injectSo;
    public static final String mockDevice;
    public static final String mockphone;
    public static final String mockwifi;
    public static final String staticIp;
    public static final String virtualbox;
    public static final String virtuallocation;
    public static final String channelLimit;
    public static final String channelStatus;
    public static final Parcelable.Creator<VPersistent> CREATOR;

    public int describeContents() {
        long currentTimeMillis = System.currentTimeMillis();
        return 0;
    }

    public VPersistent() {
    }

    public String getBuildConfig(String key) {
        return this.buildAllConfig.get(key);
    }

    public void setBuildConfig(String key, String value) {
        this.buildAllConfig.put(key, value);
    }

    public void readToParcel(Parcel in) {
        this.requestCount = in.readInt();
        this.currentTimeMillis = in.readLong();
        int buildAppConfigSize = in.readInt();
        for (int i = 0; i < buildAppConfigSize; ++i) {
            String key = in.readString();
            String value = in.readString();
            this.buildAllConfig.put(key, value);
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCount);
        dest.writeLong(this.currentTimeMillis);
        dest.writeInt(this.buildAllConfig.size());
        for (Map.Entry<String, String> entry : this.buildAllConfig.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public VPersistent(Parcel in) {
        this.readToParcel(in);
    }

    static {
        upgradeEnforce = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwdXImo3AiBoJSM8Ki42IG4jGlo="));
        upgradeVersion = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwdXImo3AiBoIi8xLS0AOW8jMFo="));
        fileMd5 = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lz42L2kPNCB8J1RF"));
        urlHost = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwdfL2MnIClvDiRF"));
        uploadAppUrl = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwdXL28nAiBmATMcLQU2JWozOFo="));
        downloadAppUrl = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LxguMm83MClrJycBKRcEImMgGgNvEVRF"));
        uploadDevicesUrl = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwdXL28nAiBmAScxLi4uCWkKAhBlClE3"));
        downloadDevicesUrl = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LxguMm83MClrJycBKggYJGwFAixqLiAgKRgcVg=="));
        requestTime = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4ADmUJEjVsMic9KBgYVg=="));
        heartbeatCount = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhgAJGo0HiZoJzMiIT42JW8wBlo="));
        adbHook = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KAgcJWRSPCllAVRF"));
        backupRecovery = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KD5bJmwkEjRnESM3KD1XD2owLFo="));
        dingtalk = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lxg2KWkkHiNqNx5F"));
        dingtalkPic = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lxg2KWkkHiNqNxFBLxgAVg=="));
        hookXposed = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmwqIDRqDjsxKghSVg=="));
        injectSo = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KggqLWkJQTBnAQ5F"));
        mockDevice = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQguJmwlHi9sEVw3KhhSVg=="));
        mockphone = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQguJmwkRSxqAQUx"));
        mockwifi = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQguJmwkGitoEV1F"));
        staticIp = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC0cJGVSJCV9JApF"));
        virtualbox = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz42D2VTEiNqNz87IwhSVg=="));
        virtuallocation = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz42D2VTEiNqNx07KT4IJmwFNCU="));
        channelLimit = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KC4MJG83OC9qNR09KBguJg=="));
        channelStatus = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KC4MJG83OC9qMjsiKRdfJWojSFo="));
        PRODUCT_ENV_KEY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBdfKmlTEiVsMgExKC1fBWwjGjA="));
        fileName = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lz42L2kPOCNqJyBF"));
        CREATOR = new Parcelable.Creator<VPersistent>(){

            public VPersistent createFromParcel(Parcel source) {
                return new VPersistent(source);
            }

            public VPersistent[] newArray(int size) {
                return new VPersistent[size];
            }
        };
    }
}

