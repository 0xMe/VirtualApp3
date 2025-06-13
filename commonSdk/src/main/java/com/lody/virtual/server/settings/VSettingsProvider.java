/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcel
 *  android.provider.Settings$Global
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 *  android.util.SparseArray
 */
package com.lody.virtual.server.settings;

import android.content.ContentResolver;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mirror.android.providers.Settings;

public class VSettingsProvider {
    private static final int MSG_QUITE = 2;
    private static final int MSG_SAVE_SETTINGS_TO_FILE = 1;
    private static final int SETTINGS_FILE_VERIFY_FLAG = 1;
    public static final int TABLE_INDEX_CONFIG = 3;
    public static final int TABLE_INDEX_GLOBAL = 2;
    public static final int TABLE_INDEX_SECURE = 1;
    public static final int TABLE_INDEX_SYSTEM = 0;
    private static final long TIME_TO_QUITE = 60000L;
    public static final List<String> sConfigTableCanLookup;
    private static VSettingsProvider sVSettingsProvider;
    private final ContentResolver mContentResolver = VirtualCore.get().getContext().getContentResolver();
    private HandlerThread mHandleThread;
    private Handler mHandlerSettingsSync;
    private final SparseArray<HashMap<String, String>> mSystem = new SparseArray();
    private final SparseArray<HashMap<String, String>> sSecure = new SparseArray();
    private final SparseArray<HashMap<String, String>> mGlobal = new SparseArray();
    private final SparseArray<HashMap<String, String>> mConfig = new SparseArray();
    private final SparseArray[] mTables = new SparseArray[]{this.mSystem, this.sSecure, this.mGlobal, this.mConfig};

    private VSettingsProvider() {
    }

    public void saveSettingsToFile(int userId) {
        Parcel target = Parcel.obtain();
        try {
            target.writeInt(1);
            VSettingsProvider.writeMapToParcel((HashMap)this.mSystem.get(userId), target);
            VSettingsProvider.writeMapToParcel((HashMap)this.sSecure.get(userId), target);
            VSettingsProvider.writeMapToParcel((HashMap)this.mGlobal.get(userId), target);
            VSettingsProvider.writeMapToParcel((HashMap)this.mConfig.get(userId), target);
            File settingsFile = this.getSystemSettingsFile(userId);
            if (!settingsFile.exists()) {
                settingsFile.createNewFile();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        target.recycle();
    }

    private void loadSettingsFromFile(int userId) {
        block18: {
            HashMap<String, String> system = new HashMap<String, String>();
            HashMap<String, String> secure = new HashMap<String, String>();
            HashMap<String, String> global = new HashMap<String, String>();
            HashMap<String, String> config = new HashMap<String, String>();
            this.mSystem.put(userId, system);
            this.sSecure.put(userId, secure);
            this.mGlobal.put(userId, global);
            this.mConfig.put(userId, config);
            File settingsFile = this.getSystemSettingsFile(userId);
            if (settingsFile.exists()) {
                int fileLen = (int)settingsFile.length();
                byte[] settingsContent = new byte[fileLen];
                FileInputStream fis = null;
                try {
                    try {
                        fis = new FileInputStream(settingsFile);
                        int readCount = fis.read(settingsContent);
                        try {
                            fis.close();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (fileLen != readCount) {
                            settingsFile.delete();
                            return;
                        }
                        Parcel target = Parcel.obtain();
                        target.unmarshall(settingsContent, 0, fileLen);
                        target.setDataPosition(0);
                        int flag = target.readInt();
                        if (flag != 1) {
                            target.recycle();
                            return;
                        }
                        int size = target.readInt();
                        for (int i = 0; i < size; ++i) {
                            system.put(target.readString(), target.readString());
                        }
                        int size2 = target.readInt();
                        for (int i2 = 0; i2 < size2; ++i2) {
                            secure.put(target.readString(), target.readString());
                        }
                        int size3 = target.readInt();
                        for (int i3 = 0; i3 < size3; ++i3) {
                            global.put(target.readString(), target.readString());
                        }
                        int size4 = target.readInt();
                        for (int i4 = 0; i4 < size4; ++i4) {
                            config.put(target.readString(), target.readString());
                        }
                        target.recycle();
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                        settingsFile.delete();
                        if (fis == null) break block18;
                        try {
                            fis.close();
                        }
                        catch (Exception exception) {}
                    }
                }
                catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public static VSettingsProvider getInstance() {
        return sVSettingsProvider;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final String getSettingsProvider(int userId, int tableIndex, String arg) {
        try {
            String string2;
            String value;
            if (tableIndex >= this.mTables.length) return null;
            if (userId < 0) return null;
            SparseArray sparseArray = this.mTables[tableIndex];
            HashMap hashMap = (HashMap)sparseArray.get(userId);
            if (hashMap == null) {
                Class<VSettingsProvider> clazz = VSettingsProvider.class;
                // MONITORENTER : com.lody.virtual.server.settings.VSettingsProvider.class
                this.loadSettingsFromFile(userId);
                // MONITOREXIT : clazz
                hashMap = (HashMap)sparseArray.get(userId);
            }
            if ((value = (String)hashMap.get(arg)) != null) {
                return value;
            }
            if (tableIndex == 1) {
                return Settings.Secure.getString((ContentResolver)this.mContentResolver, (String)arg);
            }
            if (tableIndex == 2) {
                String string3;
                if (Build.VERSION.SDK_INT >= 17) {
                    string3 = Settings.Global.getString((ContentResolver)this.mContentResolver, (String)arg);
                    return string3;
                }
                string3 = value;
                return string3;
            }
            if (tableIndex != 3) return value;
            if (Build.VERSION.SDK_INT < 29) return value;
            if (sConfigTableCanLookup.contains(arg.split(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")))[0])) {
                string2 = (String)Settings.Config.getString(this.mContentResolver, arg);
                return string2;
            }
            string2 = value;
            return string2;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void setSettingsProvider(int userId, int tableIndex, String arg, String value) {
        try {
            if (tableIndex >= this.mTables.length) return;
            if (userId < 0) return;
            SparseArray table = this.mTables[tableIndex];
            Class<VSettingsProvider> clazz = VSettingsProvider.class;
            synchronized (VSettingsProvider.class) {
                HashMap hashMap;
                if (table.get(userId) == null) {
                    this.loadSettingsFromFile(userId);
                }
                if (TextUtils.equals((CharSequence)((CharSequence)(hashMap = (HashMap)table.get(userId)).get(arg)), (CharSequence)value)) return;
                hashMap.put(arg, value);
                boolean hasUpdate = true;
                if (!hasUpdate) return;
                if (this.mHandleThread == null) {
                    this.mHandleThread = new HandlerThread(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IiwuBmQLAgBqJShAOysiBmEmNFF9IgZIOxYEGQ==")));
                    this.mHandleThread.start();
                    this.mHandlerSettingsSync = new HandlerSM(this.mHandleThread.getLooper());
                }
                this.mHandlerSettingsSync.removeMessages(1);
                this.mHandlerSettingsSync.sendMessageDelayed(this.mHandlerSettingsSync.obtainMessage(1, userId, 0), 5000L);
                // ** MonitorExit[var7_7] (shouldn't be in output)
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getSystemSettingsFile(int userId) {
        return VEnvironment.getSystemSettingsFile(userId);
    }

    private static void writeMapToParcel(HashMap<String, String> hashMap, Parcel parcel) {
        if (hashMap == null || hashMap.size() <= 0) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(hashMap.size());
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    static {
        sVSettingsProvider = new VSettingsProvider();
        sConfigTableCanLookup = new ArrayList<String>();
        sConfigTableCanLookup.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRguIGwFLCR9ASgpKQc+MWkgRVo=")));
        sConfigTableCanLookup.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj0uCGwFAiNiAVRF")));
    }

    class HandlerSM
    extends Handler {
        HandlerSM(Looper looper) {
            super(looper);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void handleMessage(Message message) {
            if (message.what == 1) {
                Class<VSettingsProvider> clazz = VSettingsProvider.class;
                synchronized (VSettingsProvider.class) {
                    VSettingsProvider.this.saveSettingsToFile(message.arg1);
                    if (VSettingsProvider.this.mHandlerSettingsSync.hasMessages(1)) return;
                    VSettingsProvider.this.mHandlerSettingsSync.sendEmptyMessageDelayed(2, 60000L);
                    // ** MonitorExit[var2_2] (shouldn't be in output)
                    return;
                }
            }
            if (message.what != 2) return;
            Class<VSettingsProvider> clazz = VSettingsProvider.class;
            synchronized (VSettingsProvider.class) {
                if (this.hasMessages(1)) return;
                this.removeMessages(2);
                if (VSettingsProvider.this.mHandleThread != null) {
                    VSettingsProvider.this.mHandleThread.quit();
                }
                VSettingsProvider.this.mHandleThread = null;
                VSettingsProvider.this.mHandlerSettingsSync = null;
                // ** MonitorExit[var2_3] (shouldn't be in output)
                return;
            }
        }
    }
}

