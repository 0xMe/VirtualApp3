/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.carlos.common.syncversion;

import android.content.Context;
import com.carlos.common.download.DownloadListner;
import com.carlos.common.download.DownloadManager;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;

public class AppUpgradeManager {
    public static AppUpgradeManager mAppUpgradeManager = new AppUpgradeManager();
    private boolean SYNC_STATUS = false;
    private String APPLICATION_SERVER_URL = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB49KQg2MmUjRCZoJwYePDs2J2EzICBsNSwsKQdfDmoFGgR6NCQwJz4MPGkKQTVqNFEAKT4uKm8zAiVgNR4qKAdbPW4gAithJzAcLC4iI2IkAiVsHlkrOQgmO2wgMD9vIwYhOwYYKmwgAjBsJwE5KBgIDg=="));

    public static AppUpgradeManager getInstance() {
        return mAppUpgradeManager;
    }

    public boolean syncVersion(Context context, final SyncCallback syncCallback) {
        this.SYNC_STATUS = false;
        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.add(context, this.APPLICATION_SERVER_URL, new DownloadListner(){

            @Override
            public void onFinished() {
                AppUpgradeManager.this.SYNC_STATUS = true;
                syncCallback.finishedListener();
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+H2szGiZiNAY9P1oNIhkNEzNXCS0bQAA/BlcVSFo=")));
            }

            @Override
            public void onProgress(float progress) {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+H2szGiZiNAY9P1oNIhkNEzNXDTw7KS4AIWEwLDZlICIeWgk6Vg==")) + progress);
            }

            @Override
            public void onPause() {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+H2szGiZiNAY9P1o7GhtWBxEaUiEzRDZcPxkFSFo=")));
            }

            @Override
            public void onCancel() {
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4+H2szGiZiNAY9P1oJRAJWGz4GUiEzRDZcPxkFSFo=")));
            }
        });
        downloadManager.downloadSingle(this.APPLICATION_SERVER_URL);
        return true;
    }

    public static interface SyncCallback {
        public void finishedListener();
    }
}

