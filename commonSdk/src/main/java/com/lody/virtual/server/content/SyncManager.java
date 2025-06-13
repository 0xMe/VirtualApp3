/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.PeriodicSync
 *  android.content.ServiceConnection
 *  android.content.SyncAdapterType
 *  android.content.SyncResult
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.text.format.Time
 *  android.util.Log
 *  android.util.Pair
 */
package com.lody.virtual.server.content;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ISyncAdapter;
import android.content.ISyncContext;
import android.content.ISyncStatusObserver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PeriodicSync;
import android.content.ServiceConnection;
import android.content.SyncAdapterType;
import android.content.SyncResult;
import android.content.SyncStatusInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.ContentResolverCompat;
import com.lody.virtual.os.BackgroundThread;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.server.accounts.AccountAndUser;
import com.lody.virtual.server.accounts.VAccountManagerService;
import com.lody.virtual.server.content.SyncAdaptersCache;
import com.lody.virtual.server.content.SyncOperation;
import com.lody.virtual.server.content.SyncQueue;
import com.lody.virtual.server.content.SyncStorageEngine;
import com.lody.virtual.server.content.VSyncInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SyncManager {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg=="));
    private static final long LOCAL_SYNC_DELAY;
    private static final long MAX_TIME_PER_SYNC;
    private static final long SYNC_NOTIFICATION_DELAY;
    private static final long INITIAL_SYNC_RETRY_TIME_IN_MS = 30000L;
    private static final long DEFAULT_MAX_SYNC_RETRY_TIME_IN_SECONDS = 3600L;
    private static final int DELAY_RETRY_SYNC_IN_PROGRESS_IN_SECONDS = 10;
    private static final int INITIALIZATION_UNBIND_DELAY_MS = 5000;
    private static final String SYNC_WAKE_LOCK_PREFIX;
    private static final String HANDLE_SYNC_ALARM_WAKE_LOCK;
    private static final String SYNC_LOOP_WAKE_LOCK;
    private static final int MAX_SIMULTANEOUS_REGULAR_SYNCS;
    private static final int MAX_SIMULTANEOUS_INITIALIZATION_SYNCS;
    private Context mContext;
    private static final AccountAndUser[] INITIAL_ACCOUNTS_ARRAY;
    private volatile AccountAndUser[] mRunningAccounts = INITIAL_ACCOUNTS_ARRAY;
    private volatile boolean mDataConnectionIsConnected = false;
    private volatile boolean mStorageIsLow = false;
    private AlarmManager mAlarmService = null;
    private SyncStorageEngine mSyncStorageEngine;
    private final SyncQueue mSyncQueue;
    protected final ArrayList<ActiveSyncContext> mActiveSyncContexts = new ArrayList();
    private final PendingIntent mSyncAlarmIntent;
    private ConnectivityManager mConnManagerDoNotUseDirectly;
    protected SyncAdaptersCache mSyncAdapters;
    private BroadcastReceiver mStorageIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42FgpiMhoCJysuUmcbGgBjHyAKKitXGGIjSFo=")).equals(action)) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLGgaFiZ9DlA8Iy42DWoVQS1rDTwaKTo6KGAjJyk=")));
                SyncManager.this.mStorageIsLow = true;
                SyncManager.this.cancelActiveSync(null, -1, null);
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42FgpiMhoCJysuUmcbGgBjHyAKKisYBA==")).equals(action)) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLGgaFiZ9DlA8Iy42DWoVQS1rDTwaKTo6KWMkRVo=")));
                SyncManager.this.mStorageIsLow = false;
                SyncManager.this.sendCheckAlarmsMessage();
            }
        }
    };
    private BroadcastReceiver mBootCompletedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            SyncManager.this.mSyncHandler.onBootCompleted();
        }
    };
    private BroadcastReceiver mBackgroundDataSettingChanged = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (SyncManager.this.getConnectivityManager().getBackgroundDataSetting()) {
                SyncManager.this.scheduleSync(null, -1, -1, null, new Bundle(), 0L, 0L, false);
            }
        }
    };
    private BroadcastReceiver mAccountsUpdatedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            SyncManager.this.updateRunningAccounts();
            SyncManager.this.scheduleSync(null, -1, -2, null, null, 0L, 0L, false);
        }
    };
    private final PowerManager mPowerManager;
    private int mSyncRandomOffsetMillis;
    private final VUserManager mUserManager;
    private static final long SYNC_ALARM_TIMEOUT_MIN = 30000L;
    private static final long SYNC_ALARM_TIMEOUT_MAX = 0x6DDD00L;
    private BroadcastReceiver mConnectivityIntentReceiver = new BroadcastReceiver(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onReceive(Context context, Intent intent) {
            boolean wasConnected = SyncManager.this.mDataConnectionIsConnected;
            SyncManager.this.mDataConnectionIsConnected = SyncManager.this.readDataConnectionState();
            if (SyncManager.this.mDataConnectionIsConnected) {
                if (!wasConnected) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uOWozBiZiDiggKQdfDn4zBitvESgqKgguIHc0OCZsHgoqLBg2KmsnIDNqAQU3Oz0mKm8gRTBpHh5F")));
                    SyncQueue syncQueue = SyncManager.this.mSyncQueue;
                    synchronized (syncQueue) {
                        SyncManager.this.mSyncStorageEngine.clearAllBackoffsLocked(SyncManager.this.mSyncQueue);
                    }
                }
                SyncManager.this.sendCheckAlarmsMessage();
            }
        }
    };
    private BroadcastReceiver mShutdownIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            Log.w((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MCWwFAiZiICQpLQcYP34wAgZoDiwgPQgMJ2IwAjVuCiA8Kj0ACmsVGiFqIx05CD5SVg==")));
            SyncManager.this.getSyncStorageEngine().writeAllState();
        }
    };
    private BroadcastReceiver mUserIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int userId = intent.getIntExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), -10000);
            if (userId == -10000) {
                return;
            }
            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgZALAVbGGI2Flc=")).equals(action)) {
                SyncManager.this.onUserRemoved(userId);
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgYOLBUMBmYVSFo=")).equals(action)) {
                SyncManager.this.onUserStarting(userId);
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgZALAVbGGI2Flc=")).equals(action)) {
                SyncManager.this.onUserStopping(userId);
            }
        }
    };
    private static final String ACTION_SYNC_ALARM;
    private final SyncHandler mSyncHandler;
    private volatile boolean mBootCompleted = false;

    private List<VUserInfo> getAllUsers() {
        return this.mUserManager.getUsers();
    }

    private boolean containsAccountAndUser(AccountAndUser[] accounts, Account account, int userId) {
        boolean found = false;
        for (int i = 0; i < accounts.length; ++i) {
            if (accounts[i].userId != userId || !accounts[i].account.equals((Object)account)) continue;
            found = true;
            break;
        }
        return found;
    }

    public void updateRunningAccounts() {
        this.mRunningAccounts = VAccountManagerService.get().getAllAccounts();
        if (this.mBootCompleted) {
            this.doDatabaseCleanup();
        }
        for (ActiveSyncContext currentSyncContext : this.mActiveSyncContexts) {
            if (this.containsAccountAndUser(this.mRunningAccounts, currentSyncContext.mSyncOperation.account, currentSyncContext.mSyncOperation.userId)) continue;
            Log.d((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRjDlk9PxgqIW8VDShsJx4bLT4tJGYaBiB5HiQsKAguCWUwMzRlHjM3Jj0XL2waRQJpAQYbPhcMI2ojBi9gNDhF")));
            this.sendSyncFinishedOrCanceledMessage(currentSyncContext, null);
        }
        this.sendCheckAlarmsMessage();
    }

    private void doDatabaseCleanup() {
        for (VUserInfo user : this.mUserManager.getUsers(true)) {
            if (user.partial) continue;
            Account[] accountsForUser = VAccountManagerService.get().getAccounts(user.id, null);
            this.mSyncStorageEngine.doDatabaseCleanup(accountsForUser, user.id);
        }
    }

    private boolean readDataConnectionState() {
        NetworkInfo networkInfo = this.getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConnectivityManager getConnectivityManager() {
        SyncManager syncManager = this;
        synchronized (syncManager) {
            if (this.mConnManagerDoNotUseDirectly == null) {
                this.mConnManagerDoNotUseDirectly = (ConnectivityManager)this.mContext.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGojNClmHgYuKQg2IQ==")));
            }
            return this.mConnManagerDoNotUseDirectly;
        }
    }

    public SyncManager(Context context) {
        this.mContext = context;
        SyncStorageEngine.init(context);
        this.mSyncStorageEngine = SyncStorageEngine.getSingleton();
        this.mSyncStorageEngine.setOnSyncRequestListener(new SyncStorageEngine.OnSyncRequestListener(){

            @Override
            public void onSyncRequest(Account account, int userId, int reason, String authority, Bundle extras) {
                SyncManager.this.scheduleSync(account, userId, reason, authority, extras, 0L, 0L, false);
            }
        });
        this.mSyncAdapters = new SyncAdaptersCache(this.mContext);
        this.mSyncAdapters.refreshServiceCache(null);
        this.mSyncQueue = new SyncQueue(this.mSyncStorageEngine, this.mSyncAdapters);
        this.mSyncHandler = new SyncHandler(BackgroundThread.get().getLooper());
        this.mSyncAlarmIntent = Build.VERSION.SDK_INT >= 31 ? PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDA0LC42L30KRSRuJAo7ORUYWH0xLBNjHwIOLztbVg=="))), (int)0x4000000) : PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDA0LC42L30KRSRuJAo7ORUYWH0xLBNjHwIOLztbVg=="))), (int)0);
        IntentFilter intentFilter = new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k2KAg1Dm4FNCZlMxoAJDwcDGoINFRnDzhNOzs2E2AhRR1iJSAK")));
        context.registerReceiver(this.mConnectivityIntentReceiver, intentFilter);
        intentFilter = new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42MBRkJTAOIAYuAWQbHlRkDygJ")));
        context.registerReceiver(this.mBootCompletedReceiver, intentFilter);
        intentFilter = new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k2KAg1Dm4FNCZlMxoPJRY2B2ohMBRiDB5IJQYcHWcbJBNnNShOKBUAH2YhRR1kNV0fJSwuWQ==")));
        context.registerReceiver(this.mBackgroundDataSettingChanged, intentFilter);
        intentFilter = new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42FgpiMhoCJysuUmcbGgBjHyAKKitXGGIjSFo=")));
        intentFilter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42FgpiMhoCJysuUmcbGgBjHyAKKisYBA==")));
        context.registerReceiver(this.mStorageIntentReceiver, intentFilter);
        intentFilter = new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42PABiHBpXIRUuUmIYNApgDwZNLj5SVg==")));
        intentFilter.setPriority(100);
        context.registerReceiver(this.mShutdownIntentReceiver, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgZALAVbGGI2Flc=")));
        intentFilter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgYOLBUMBmYVSFo=")));
        intentFilter.addAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgZALAVbGGI2Flc=")));
        this.mContext.registerReceiver(this.mUserIntentReceiver, intentFilter);
        context.registerReceiver((BroadcastReceiver)new SyncAlarmIntentReceiver(), new IntentFilter(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDA0LC42L30KRSRuJAo7ORUYWH0xLBNjHwIOLztbVg=="))));
        this.mPowerManager = (PowerManager)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhgALWgaFlo=")));
        this.mUserManager = VUserManager.get();
        this.mSyncStorageEngine.addStatusChangeListener(1, new ISyncStatusObserver.Stub(){

            @Override
            public void onStatusChanged(int which) {
                SyncManager.this.sendCheckAlarmsMessage();
            }
        });
        this.mSyncRandomOffsetMillis = this.mSyncStorageEngine.getSyncRandomOffset() * 1000;
    }

    private long jitterize(long minValue, long maxValue) {
        Random random = new Random(SystemClock.elapsedRealtime());
        long spread = maxValue - minValue;
        if (spread > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRhfM3sFMC9iNDw/Iz0MDm4FBShoNyg/Kj4uJ2A0OD9vHg0pKS5bPGczJAJsESs3OwdfI3kVLD5pI1E8LAgcAGsVHgViCiQ3LAgqLn4zRSt4EQIgKT01JGYaBiRsMCM7PCkbMX9TRCV8IC8i")));
        }
        return minValue + (long)random.nextInt((int)spread);
    }

    public SyncStorageEngine getSyncStorageEngine() {
        return this.mSyncStorageEngine;
    }

    public int getIsSyncable(Account account, int userId, String providerName) {
        int isSyncable = this.mSyncStorageEngine.getIsSyncable(account, userId, providerName);
        VUserInfo userInfo = VUserManager.get().getUserInfo(userId);
        if (userInfo == null || !userInfo.isRestricted()) {
            return isSyncable;
        }
        SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = this.mSyncAdapters.getServiceInfo(account, providerName);
        if (syncAdapterInfo == null) {
            return isSyncable;
        }
        return 0;
    }

    private void ensureAlarmService() {
        if (this.mAlarmService == null) {
            this.mAlarmService = (AlarmManager)this.mContext.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggEP28jElo=")));
        }
    }

    public void scheduleSync(Account requestedAccount, int userId, int reason, String requestedAuthority, Bundle extras, long beforeRuntimeMillis, long runtimeMillis, boolean onlyThoseWithUnkownSyncableState) {
        AccountAndUser[] accounts;
        boolean backgroundDataUsageAllowed;
        boolean bl = backgroundDataUsageAllowed = !this.mBootCompleted || this.getConnectivityManager().getBackgroundDataSetting();
        if (extras == null) {
            extras = new Bundle();
        }
        Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4cM3oaMC9gDjM8Iy4cDm4JTS5lJA0xPQhSVg==")) + requestedAccount + " " + extras.toString() + " " + requestedAuthority));
        Boolean expedited = extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfKGgVMC9mHjAw")), false);
        if (expedited.booleanValue()) {
            runtimeMillis = -1L;
        }
        if (requestedAccount != null && userId != -1) {
            accounts = new AccountAndUser[]{new AccountAndUser(requestedAccount, userId)};
        } else {
            accounts = this.mRunningAccounts;
            if (accounts.length == 0) {
                Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAPLQcYP3hSTSZlIzwsLT42KWYKRT9lICAsKQgqImoFPC9vJygzCF4iI2UwRSZqNzA5LS5SVg==")));
                return;
            }
        }
        boolean uploadOnly = extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6DmozJCw=")), false);
        boolean manualSync = extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4AKmszNFo=")), false);
        if (manualSync) {
            extras.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgmCGowFitsJCw7Ly0EDWkVHlo=")), true);
            extras.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgmCGowFitsJyg/LBg2MW8VEgM=")), true);
        }
        boolean ignoreSettings = extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgmCGowFitsJyg/LBg2MW8VEgM=")), false);
        int source = uploadOnly ? 1 : (manualSync ? 3 : (requestedAuthority == null ? 2 : 0));
        for (AccountAndUser account : accounts) {
            HashSet<String> syncableAuthorities = new HashSet<String>();
            for (SyncAdaptersCache.SyncAdapterInfo syncAdapter : this.mSyncAdapters.getAllServices()) {
                syncableAuthorities.add(syncAdapter.type.authority);
            }
            if (requestedAuthority != null) {
                boolean hasSyncAdapter = syncableAuthorities.contains(requestedAuthority);
                syncableAuthorities.clear();
                if (hasSyncAdapter) {
                    syncableAuthorities.add(requestedAuthority);
                }
            }
            for (String authority : syncableAuthorities) {
                long backoffTime;
                boolean syncAllowed;
                SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo;
                int isSyncable = this.getIsSyncable(account.account, account.userId, authority);
                if (isSyncable == 0 || (syncAdapterInfo = this.mSyncAdapters.getServiceInfo(account.account, authority)) == null) continue;
                boolean allowParallelSyncs = syncAdapterInfo.type.allowParallelSyncs();
                boolean isAlwaysSyncable = syncAdapterInfo.type.isAlwaysSyncable();
                if (isSyncable < 0 && isAlwaysSyncable) {
                    this.mSyncStorageEngine.setIsSyncable(account.account, account.userId, authority, 1);
                    isSyncable = 1;
                }
                if (onlyThoseWithUnkownSyncableState && isSyncable >= 0 || !syncAdapterInfo.type.supportsUploading() && uploadOnly) continue;
                boolean bl2 = syncAllowed = isSyncable < 0 || ignoreSettings || backgroundDataUsageAllowed && this.mSyncStorageEngine.getMasterSyncAutomatically(account.userId) && this.mSyncStorageEngine.getSyncAutomatically(account.account, account.userId, authority);
                if (!syncAllowed) {
                    Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAPLQcYP3hSTQNuARoqPQgAIksVSFo=")) + account + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgYKXsFBiVmVyQ7KhdbDWUFGix1VjwvKS4ADmEaGiluICA7Ly1bCWsKLCA="))));
                    continue;
                }
                Pair<Long, Long> backoff = this.mSyncStorageEngine.getBackoff(account.account, account.userId, authority);
                long delayUntil = this.mSyncStorageEngine.getDelayUntilTime(account.account, account.userId, authority);
                long l = backoffTime = backoff != null ? (Long)backoff.first : 0L;
                if (isSyncable < 0) {
                    Bundle newExtras = new Bundle();
                    newExtras.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcCWwFAjdgHgYiKAhSVg==")), true);
                    Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjM8KQcYMWUzLDdlER46LRcqI2AgRCN9JxodKAM5KHgVMD9qATggDRg2JWoaBgR+N1RF")) + delayUntil + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmwVASh9Nxk8")) + 0 + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWowNAR9JDM8")) + source + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2szLCVmDlkgPxhSVg==")) + account + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2waMCBgJywzLBgbOg==")) + authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186M2kKMAR9ASs8")) + newExtras));
                    this.scheduleSyncOperation(new SyncOperation(account.account, account.userId, reason, source, authority, newExtras, 0L, 0L, backoffTime, delayUntil, allowParallelSyncs));
                }
                if (onlyThoseWithUnkownSyncableState) continue;
                Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAPLQcYP3hSTSxrAQIsL186CWAzFixsVyBF")) + delayUntil + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcMI2onICpnCiRF")) + runtimeMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgiDmgaRCg=")) + beforeRuntimeMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWowNAR9JDM8")) + source + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2szLCVmDlkgPxhSVg==")) + account + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2waMCBgJywzLBgbOg==")) + authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186M2kKMAR9ASs8")) + extras));
                this.scheduleSyncOperation(new SyncOperation(account.account, account.userId, reason, source, authority, extras, runtimeMillis, beforeRuntimeMillis, backoffTime, delayUntil, allowParallelSyncs));
            }
        }
    }

    public void scheduleLocalSync(Account account, int userId, int reason, String authority) {
        Bundle extras = new Bundle();
        extras.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6DmozJCw=")), true);
        this.scheduleSync(account, userId, reason, authority, extras, LOCAL_SYNC_DELAY, 2L * LOCAL_SYNC_DELAY, false);
    }

    public SyncAdapterType[] getSyncAdapterTypes() {
        Collection<SyncAdaptersCache.SyncAdapterInfo> serviceInfos = this.mSyncAdapters.getAllServices();
        SyncAdapterType[] types = new SyncAdapterType[serviceInfos.size()];
        int i = 0;
        for (SyncAdaptersCache.SyncAdapterInfo serviceInfo : serviceInfos) {
            types[i] = serviceInfo.type;
            ++i;
        }
        return types;
    }

    private void sendSyncAlarmMessage() {
        Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uCGgFAiZiICQNIAYqAWYhEhVhIjAKJCw2E2UITQ59MlFF")));
        this.mSyncHandler.sendEmptyMessage(2);
    }

    private void sendCheckAlarmsMessage() {
        Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uCGgFAiZiICQNIAYqAWYhEhVhJTBOJhY2B2wmPBFhDyhBJAhSVg==")));
        this.mSyncHandler.removeMessages(3);
        this.mSyncHandler.sendEmptyMessage(3);
    }

    private void sendSyncFinishedOrCanceledMessage(ActiveSyncContext syncContext, SyncResult syncResult) {
        Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uCGgFAiZiICQNIAYqAWYhEhVhIjAKJCw2E2o2GhNnDyxMJywcVg==")));
        Message msg = this.mSyncHandler.obtainMessage();
        msg.what = 1;
        msg.obj = new SyncHandlerMessagePayload(syncContext, syncResult);
        this.mSyncHandler.sendMessage(msg);
    }

    private void sendCancelSyncsMessage(Account account, int userId, String authority) {
        Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uCGgFAiZiICQNIAYqAWYhEhVhJTACJCw2GWgVSFo=")));
        Message msg = this.mSyncHandler.obtainMessage();
        msg.what = 6;
        msg.obj = Pair.create((Object)account, (Object)authority);
        msg.arg1 = userId;
        this.mSyncHandler.sendMessage(msg);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void clearBackoffSetting(SyncOperation op) {
        this.mSyncStorageEngine.setBackoff(op.account, op.userId, op.authority, -1L, -1L);
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.onBackoffChanged(op.account, op.userId, op.authority, 0L);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void increaseBackoffSetting(SyncOperation op) {
        long maxSyncRetryTimeInSeconds;
        long now = SystemClock.elapsedRealtime();
        Pair<Long, Long> previousSettings = this.mSyncStorageEngine.getBackoff(op.account, op.userId, op.authority);
        long newDelayInMs = -1L;
        if (previousSettings != null) {
            if (now < (Long)previousSettings.first) {
                Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0qCWoFGShjDlg8Lz0iP2wFNC5rMwUrLggfJGAwAj95HhodKAdfJ2gKLD97AR4ZCDkiE24KTTVsJywwIz4lInsFSFo=")) + ((Long)previousSettings.first - now) / 1000L + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2M2szGiZiESs2"))));
                return;
            }
            newDelayInMs = (Long)previousSettings.second * 2L;
        }
        if (newDelayInMs <= 0L) {
            newDelayInMs = this.jitterize(30000L, 33000L);
        }
        if (newDelayInMs > (maxSyncRetryTimeInSeconds = 3600L) * 1000L) {
            newDelayInMs = maxSyncRetryTimeInSeconds * 1000L;
        }
        long backoff = now + newDelayInMs;
        this.mSyncStorageEngine.setBackoff(op.account, op.userId, op.authority, backoff, newDelayInMs);
        op.backoff = backoff;
        op.updateEffectiveRunTime();
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.onBackoffChanged(op.account, op.userId, op.authority, backoff);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setDelayUntilTime(SyncOperation op, long delayUntilSeconds) {
        long delayUntil = delayUntilSeconds * 1000L;
        long absoluteNow = System.currentTimeMillis();
        long newDelayUntilTime = delayUntil > absoluteNow ? SystemClock.elapsedRealtime() + (delayUntil - absoluteNow) : 0L;
        this.mSyncStorageEngine.setDelayUntilTime(op.account, op.userId, op.authority, newDelayUntilTime);
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.onDelayUntilTimeChanged(op.account, op.authority, newDelayUntilTime);
        }
    }

    public void cancelActiveSync(Account account, int userId, String authority) {
        this.sendCancelSyncsMessage(account, userId, authority);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void scheduleSyncOperation(SyncOperation syncOperation) {
        boolean queueChanged;
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            queueChanged = this.mSyncQueue.add(syncOperation);
        }
        if (queueChanged) {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAPLQcYP2cKTStsNzg/IxgAKnc0OCBsNyQ+Ly0AJ2tSIFo=")) + syncOperation));
            this.sendCheckAlarmsMessage();
        } else {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAPLQcYP2cKTStsNzg/IxgAKnc0OC9lNFk5LD42KmsnIDBsHjw7JQcuKGoaETZqDjA5Lio6D28FNAR9AQozKi0XOg==")) + syncOperation));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearScheduledSyncOperations(Account account, int userId, String authority) {
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.remove(account, userId, authority);
        }
        this.mSyncStorageEngine.setBackoff(account, userId, authority, -1L, -1L);
    }

    void maybeRescheduleSync(SyncResult syncResult, SyncOperation operation) {
        Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcOWowNCZmHjAqKAc1OmkgRQRlJA0ZKToXJGIVLDVvDh4gOD0cLGsJICVpERo2MTkiVg==")) + syncResult + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + operation));
        operation = new SyncOperation(operation);
        if (operation.extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgmCGowFitsJCw7Ly0EDWkVHlo=")), false)) {
            operation.extras.remove(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgmCGowFitsJCw7Ly0EDWkVHlo=")));
        }
        if (operation.extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgAH2ojGgZsJyw/LBguIQ==")), false)) {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALHsKFitmESwZKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdOD5fJ2gjJC9vNys3LywAH2EhRVZgMhpAJgU2H2ALGh9oMh5LJiwuWn02RVR4HiAsKTo6D2EaLCZvDjgiLy4bJA==")) + operation));
        } else if (operation.extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6DmozJCw=")), false) && !syncResult.syncAlreadyInProgress) {
            operation.extras.remove(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6DmozJCw=")));
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uLG8gAi9gNDs8Iy4cDm4JTSVsESg5LRcqI2AgRCNpAS8pKCpXCm8jBQFsNzggDRguCmwwMzZuEQY2LgcuKWhSIDdgMCQvIxdbDW4jASNlJxodL186D2cKRSZ5HgodKAguCWUwMD9vJygzDRcmJXkaFiRqESgbODo6Vg==")) + operation));
            this.scheduleSyncOperation(operation);
        } else if (syncResult.tooManyRetries) {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALHsKFitmESwZKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdOD5fJ2gjJC9vNys3JQgLL2UwFiJqETA0LV86LGozBShgDiA2LQMmLmwjPCtsIFAr")) + operation));
        } else if (syncResult.madeSomeProgress()) {
            Log.d((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uLG8gAi9gNDs8Iy4cDm4JTSVsESg5LRcqI2AgRCNpNAosKC0AD2sJID9sJyg5DRgMJ2wjFj9sM1EwKV86CmsVMyh9Dlg8KAguKG8KRChqDi8rLRg2LGMKLDFuDjMpLAguL2sJICVsETA2JAguOg==")));
            this.scheduleSyncOperation(operation);
        } else if (syncResult.syncAlreadyInProgress) {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uLG8gAi9gNDs8Iy4cDm4JTSVsESg5LRcqI2AgRCNqHgYqIzpXImgFAgJoES83Oz02KmkFFiNpI1EZLBguKmhSID19ASs8LwdbKGkjQSxuDTwsPQc2M2AwNyNvDhEpLD1fKWsgFj9vNDMtDRhSVg==")) + operation));
            this.scheduleSyncOperation(new SyncOperation(operation.account, operation.userId, operation.reason, operation.syncSource, operation.authority, operation.extras, 10000L, operation.flexTime, operation.backoff, operation.delayUntil, operation.allowParallelSyncs));
        } else if (syncResult.hasSoftError()) {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uLG8gAi9gNDs8Iy4cDm4JTSVsESg5LRcqI2AgRCNpNAosKC0AD2sJIAVsDTw0Jj0uJmoKAiJpJFk0LV86P3sKLCViNw08KAguKG8KRDJ4EVRF")) + operation));
            this.scheduleSyncOperation(operation);
        } else {
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALHsKFitmESwZKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdOD5fJ2gjJC9vNys3IBccLHkaFiRqESgbPhgYKXsFJyhjHiAqKF4mPWoaRSVsMFAr")) + operation));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onUserStarting(int userId) {
        Account[] accounts;
        this.mSyncAdapters.refreshServiceCache(null);
        this.updateRunningAccounts();
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.addPendingOperations(userId);
        }
        for (Account account : accounts = VAccountManagerService.get().getAccounts(userId, null)) {
            this.scheduleSync(account, userId, -8, null, null, 0L, 0L, true);
        }
        this.sendCheckAlarmsMessage();
    }

    private void onUserStopping(int userId) {
        this.updateRunningAccounts();
        this.cancelActiveSync(null, userId, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onUserRemoved(int userId) {
        this.updateRunningAccounts();
        this.mSyncStorageEngine.doDatabaseCleanup(new Account[0], userId);
        SyncQueue syncQueue = this.mSyncQueue;
        synchronized (syncQueue) {
            this.mSyncQueue.removeUser(userId);
        }
    }

    static String formatTime(long time) {
        Time tobj = new Time();
        tobj.set(time);
        return tobj.format(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PQUXDXgVHSNIDg08OAUfIHkhPzJ7DDBF")));
    }

    private String getLastFailureMessage(int code) {
        switch (code) {
            case 1: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGs3IDdgESw/Lwc2IX4zLCZ4Hjw5LD4mCGIFNDY="));
            }
            case 2: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFNCZmHgY5Lwg2MW8FMyhrDgo5LD0MVg=="));
            }
            case 3: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAQAUnsFNARhNB4q"));
            }
            case 4: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+Km8zNyhiASwqKi4uVg=="));
            }
            case 5: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGgjHi99Jw08KAguKG8KRVo="));
            }
            case 6: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgAD3sFEjdgNxk8KBcMCGkgBi9lJxo6PQguCGEwAjU="));
            }
            case 7: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgAD3sFEjdgNxk8Iz0MLmoVLCtsIzwgKS0MKWEzSFo="));
            }
            case 8: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgaFiZ9DlA8KAguKG8KRVo="));
            }
        }
        return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcMWojGj1gN1RF"));
    }

    private boolean isSyncStillActive(ActiveSyncContext activeSyncContext) {
        for (ActiveSyncContext sync : this.mActiveSyncContexts) {
            if (sync != activeSyncContext) continue;
            return true;
        }
        return false;
    }

    static {
        SYNC_WAKE_LOCK_PREFIX = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PD02J2ojLyI="));
        HANDLE_SYNC_ALARM_WAKE_LOCK = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguX24jMCxlESgQLxgcJWUKTSRlNFFF"));
        SYNC_LOOP_WAKE_LOCK = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxHiVgJyRILwcEPWczNClqJ1RF"));
        ACTION_SYNC_ALARM = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1NDA0LC42L30KRSRuJAo7ORUYWH0xLBNjHwIOLztbVg=="));
        boolean isLargeRAM = true;
        int defaultMaxInitSyncs = 5;
        int defaultMaxRegularSyncs = 2;
        MAX_SIMULTANEOUS_INITIALIZATION_SYNCS = defaultMaxInitSyncs;
        MAX_SIMULTANEOUS_REGULAR_SYNCS = defaultMaxRegularSyncs;
        LOCAL_SYNC_DELAY = 30000L;
        MAX_TIME_PER_SYNC = 300000L;
        SYNC_NOTIFICATION_DELAY = 30000L;
        INITIAL_ACCOUNTS_ARRAY = new AccountAndUser[0];
    }

    class SyncHandler
    extends Handler {
        private static final int MESSAGE_SYNC_FINISHED = 1;
        private static final int MESSAGE_SYNC_ALARM = 2;
        private static final int MESSAGE_CHECK_ALARMS = 3;
        private static final int MESSAGE_SERVICE_CONNECTED = 4;
        private static final int MESSAGE_SERVICE_DISCONNECTED = 5;
        private static final int MESSAGE_CANCEL = 6;
        public final SyncNotificationInfo mSyncNotificationInfo;
        private Long mAlarmScheduleTime;
        public final SyncTimeTracker mSyncTimeTracker;
        private List<Message> mBootQueue;

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onBootCompleted() {
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jj4AD2wJIClgJF0sKhcMLmkjASR4ETAdLhg+CGMKRSJ5HigeKQcbJGwKND9sESs5")));
            SyncManager.this.doDatabaseCleanup();
            SyncHandler syncHandler = this;
            synchronized (syncHandler) {
                for (Message message : this.mBootQueue) {
                    this.sendMessage(message);
                }
                this.mBootQueue = null;
                SyncManager.this.mBootCompleted = true;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean tryEnqueueMessageUntilReadyToRun(Message msg) {
            SyncHandler syncHandler = this;
            synchronized (syncHandler) {
                if (!SyncManager.this.mBootCompleted) {
                    this.mBootQueue.add(Message.obtain((Message)msg));
                    return true;
                }
                return false;
            }
        }

        public SyncHandler(Looper looper) {
            super(looper);
            this.mSyncNotificationInfo = new SyncNotificationInfo();
            this.mAlarmScheduleTime = null;
            this.mSyncTimeTracker = new SyncTimeTracker();
            this.mBootQueue = new ArrayList<Message>();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message msg) {
            if (this.tryEnqueueMessageUntilReadyToRun(msg)) {
                return;
            }
            long earliestFuturePollTime = Long.MAX_VALUE;
            long nextPendingSyncTime = Long.MAX_VALUE;
            try {
                SyncManager.this.mDataConnectionIsConnected = SyncManager.this.readDataConnectionState();
                earliestFuturePollTime = this.scheduleReadyPeriodicSyncs();
                switch (msg.what) {
                    case 6: {
                        Pair payload = (Pair)msg.obj;
                        Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmMjAKLzw6GmEmFhFmD10fJiwuQHknIFo=")) + payload.first + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + (String)payload.second));
                        this.cancelActiveSyncLocked((Account)payload.first, msg.arg1, (String)payload.second);
                        nextPendingSyncTime = this.maybeStartNextSyncLocked();
                        return;
                    }
                    case 1: {
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmMjBLLjsuU2Y2BkxkIh4VJQYqVg==")));
                        SyncHandlerMessagePayload payload = (SyncHandlerMessagePayload)msg.obj;
                        if (!SyncManager.this.isSyncStillActive(payload.activeSyncContext)) {
                            Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCAtLBguDmwVAgRoMzwcJQdfKm4OPCJsNxk3Ki0YCGs3IC9hICQ2KikmCG8FMC1rDg0rLRg2CmMFICB7MCBF")) + payload.activeSyncContext));
                            return;
                        }
                        this.runSyncFinishedOrCanceledLocked(payload.syncResult, payload.activeSyncContext);
                        nextPendingSyncTime = this.maybeStartNextSyncLocked();
                        return;
                    }
                    case 4: {
                        ServiceConnectionData msgData = (ServiceConnectionData)msg.obj;
                        Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmMjAKLzw6GmEmFhFmDygfOzwuE2QLNBZ3MCRF")) + msgData.activeSyncContext));
                        if (!SyncManager.this.isSyncStillActive(msgData.activeSyncContext)) return;
                        this.runBoundToSyncAdapter(msgData.activeSyncContext, msgData.syncAdapter);
                        return;
                    }
                    case 5: {
                        ActiveSyncContext currentSyncContext = ((ServiceConnectionData)msg.obj).activeSyncContext;
                        Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmMjAKLzw6GmEmFhFhNTBBJiwAU2IhNBNuHDAWPTkmVg==")) + currentSyncContext));
                        if (!SyncManager.this.isSyncStillActive(currentSyncContext)) return;
                        if (currentSyncContext.mSyncAdapter != null) {
                            try {
                                currentSyncContext.mSyncAdapter.cancelSync(currentSyncContext);
                            }
                            catch (RemoteException remoteException) {
                                // empty catch block
                            }
                        }
                        SyncResult syncResult = new SyncResult();
                        ++syncResult.stats.numIoExceptions;
                        this.runSyncFinishedOrCanceledLocked(syncResult, currentSyncContext);
                        nextPendingSyncTime = this.maybeStartNextSyncLocked();
                        return;
                    }
                    case 2: {
                        boolean isLoggable = true;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmMjBLLjsuU2EIQR9iHyBF")));
                        this.mAlarmScheduleTime = null;
                        nextPendingSyncTime = this.maybeStartNextSyncLocked();
                        return;
                    }
                    case 3: {
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+CGgFHitpJwY2LysAOW8VBiRrDgpXLhc2D30KJCB7MCBBJysYUmALPFRmNTAVLAUuBGAmOF9mIlkSIi5SVg==")));
                        nextPendingSyncTime = this.maybeStartNextSyncLocked();
                        return;
                    }
                }
                return;
            }
            finally {
                this.manageSyncNotificationLocked();
                this.manageSyncAlarmLocked(earliestFuturePollTime, nextPendingSyncTime);
                this.mSyncTimeTracker.update();
            }
        }

        private long scheduleReadyPeriodicSyncs() {
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki42CmgVMAVgHjAAKAciPmgmTStsNx4cLggYJWkjGilpJyxF")));
            boolean backgroundDataUsageAllowed = SyncManager.this.getConnectivityManager().getBackgroundDataSetting();
            long earliestFuturePollTime = Long.MAX_VALUE;
            if (!backgroundDataUsageAllowed) {
                return earliestFuturePollTime;
            }
            AccountAndUser[] accounts = SyncManager.this.mRunningAccounts;
            long nowAbsolute = System.currentTimeMillis();
            long shiftedNowAbsolute = 0L < nowAbsolute - (long)SyncManager.this.mSyncRandomOffsetMillis ? nowAbsolute - (long)SyncManager.this.mSyncRandomOffsetMillis : 0L;
            ArrayList<Pair<SyncStorageEngine.AuthorityInfo, SyncStatusInfo>> infos = SyncManager.this.mSyncStorageEngine.getCopyOfAllAuthoritiesWithSyncStatus();
            for (Pair<SyncStorageEngine.AuthorityInfo, SyncStatusInfo> info : infos) {
                SyncStorageEngine.AuthorityInfo authorityInfo = (SyncStorageEngine.AuthorityInfo)info.first;
                SyncStatusInfo status = (SyncStatusInfo)info.second;
                if (TextUtils.isEmpty((CharSequence)authorityInfo.authority)) {
                    Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JS4ALHsFJCZLHjA3Ixg2IX4wTQRlJCQaLgguCEsVND9lNBodLwQpJGQjQQVvDjwwJj09MXkVSFo=")) + authorityInfo));
                    continue;
                }
                if (!SyncManager.this.containsAccountAndUser(accounts, authorityInfo.account, authorityInfo.userId) || !SyncManager.this.mSyncStorageEngine.getMasterSyncAutomatically(authorityInfo.userId) || !SyncManager.this.mSyncStorageEngine.getSyncAutomatically(authorityInfo.account, authorityInfo.userId, authorityInfo.authority) || SyncManager.this.getIsSyncable(authorityInfo.account, authorityInfo.userId, authorityInfo.authority) == 0) continue;
                int N = authorityInfo.periodicSyncs.size();
                for (int i = 0; i < N; ++i) {
                    long nextPollTimeAbsolute;
                    PeriodicSync sync = authorityInfo.periodicSyncs.get(i);
                    Bundle extras = sync.extras;
                    long periodInMillis = sync.period * 1000L;
                    long flexInMillis = mirror.android.content.PeriodicSync.flexTime.get(sync) * 1000L;
                    if (periodInMillis <= 0L) continue;
                    long lastPollTimeAbsolute = status.getPeriodicSyncTime(i);
                    long remainingMillis = periodInMillis - shiftedNowAbsolute % periodInMillis;
                    long timeSinceLastRunMillis = nowAbsolute - lastPollTimeAbsolute;
                    boolean runEarly = remainingMillis <= flexInMillis && timeSinceLastRunMillis > periodInMillis - flexInMillis;
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGs0TCg=")) + i + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgiD28nIFo=")) + authorityInfo.authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mzo6KGgaFi9gJA0iPxhSVg==")) + periodInMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgiDmgaRDJLEVRF")) + flexInMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcMM2oVJC9gNAY2KCoHOg==")) + remainingMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcqCWoVNB9hJAY2Ly0MHW8zQQNvV1Ar")) + timeSinceLastRunMillis + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgEP28wMyhhHh4oKl4mOW4aAiVlV1Ar")) + lastPollTimeAbsolute + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2CmUVOAZiDg08Kj1fI3hSTVo=")) + shiftedNowAbsolute + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcMI2omGit9ASwoLQQHOg==")) + runEarly));
                    if (runEarly || remainingMillis == periodInMillis || lastPollTimeAbsolute > nowAbsolute || timeSinceLastRunMillis >= periodInMillis) {
                        Pair<Long, Long> backoff = SyncManager.this.mSyncStorageEngine.getBackoff(authorityInfo.account, authorityInfo.userId, authorityInfo.authority);
                        SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = SyncManager.this.mSyncAdapters.getServiceInfo(authorityInfo.account, authorityInfo.authority);
                        if (syncAdapterInfo == null) continue;
                        SyncManager.this.mSyncStorageEngine.setPeriodicSyncTime(authorityInfo.ident, authorityInfo.periodicSyncs.get(i), nowAbsolute);
                        SyncManager.this.scheduleSyncOperation(new SyncOperation(authorityInfo.account, authorityInfo.userId, -4, 4, authorityInfo.authority, extras, 0L, 0L, backoff != null ? (Long)backoff.first : 0L, SyncManager.this.mSyncStorageEngine.getDelayUntilTime(authorityInfo.account, authorityInfo.userId, authorityInfo.authority), syncAdapterInfo.type.allowParallelSyncs()));
                    }
                    if ((nextPollTimeAbsolute = runEarly ? nowAbsolute + periodInMillis + remainingMillis : nowAbsolute + remainingMillis) >= earliestFuturePollTime) continue;
                    earliestFuturePollTime = nextPollTimeAbsolute;
                }
            }
            if (earliestFuturePollTime == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            return SystemClock.elapsedRealtime() + (earliestFuturePollTime < nowAbsolute ? 0L : earliestFuturePollTime - nowAbsolute);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private long maybeStartNextSyncLocked() {
            boolean isLoggable = true;
            if (isLoggable) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+J2sjNF5mHiAqLBUYPWgwBl5uARoq")));
            }
            if (!SyncManager.this.mDataConnectionIsConnected) {
                if (isLoggable) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+J2sjNF5mHiAqLBUYPWgwBl5uARoqPyo6KmAkOC9pATAqOD4YKWUzBj9rNCwwJi1eO3kVMAVsJFEdLAgcPQ==")));
                }
                return Long.MAX_VALUE;
            }
            if (SyncManager.this.mStorageIsLow) {
                if (isLoggable) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+J2sjNF5mHiAqLBUYPWgwBl5uARoqPyo6L2IKQSplNx0pKT4uMXVSICVlNx4dJxcAJW4jSFo=")));
                }
                return Long.MAX_VALUE;
            }
            AccountAndUser[] accounts = SyncManager.this.mRunningAccounts;
            if (accounts == INITIAL_ACCOUNTS_ARRAY) {
                if (isLoggable) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+J2sjNF5mHiAqLBUYPWgwBl5uARoqPyo6O30gNCpqDh49LARXKmUgMzRlNxo6IC1eO3kVMAVsJFEdLAgcPQ==")));
                }
                return Long.MAX_VALUE;
            }
            long now = SystemClock.elapsedRealtime();
            long nextReadyToRunTime = Long.MAX_VALUE;
            ArrayList operations = new ArrayList();
            SyncQueue syncQueue = SyncManager.this.mSyncQueue;
            synchronized (syncQueue) {
                if (isLoggable) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj0uCWoFMyhmHho/PxdfKmkgRTdvER4cLCo6O2EzMCRrClwpLAc2KmgmJC9oHig0DRguIGswETZsJBE3")) + SyncManager.this.mSyncQueue.getOperations().size()));
                }
                Iterator<SyncOperation> operationIterator = SyncManager.this.mSyncQueue.getOperations().iterator();
                HashSet<Integer> removedUsers = new HashSet<Integer>();
                while (operationIterator.hasNext()) {
                    SyncOperation op = operationIterator.next();
                    if (!SyncManager.this.containsAccountAndUser(accounts, op.account, op.userId)) {
                        operationIterator.remove();
                        SyncManager.this.mSyncStorageEngine.deleteFromPending(op.pendingOperation);
                        if (!isLoggable) continue;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OHsLMARgJyQsKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdPl9XO2gjLANsERoZDRcMJm4FMAJ5Dh03LQdfCW8wMyY=")));
                        continue;
                    }
                    int syncableState = SyncManager.this.getIsSyncable(op.account, op.userId, op.authority);
                    if (syncableState == 0) {
                        operationIterator.remove();
                        SyncManager.this.mSyncStorageEngine.deleteFromPending(op.pendingOperation);
                        if (!isLoggable) continue;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OHsLMARgJyQsKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdPl9XI2wmLCtqJzAoOz1XLHlSTCl+MFA5")));
                        continue;
                    }
                    VUserInfo userInfo = SyncManager.this.mUserManager.getUserInfo(op.userId);
                    if (userInfo == null) {
                        removedUsers.add(op.userId);
                    }
                    if (!isLoggable) continue;
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OHsLMARgJyQsKQcYM34wAj9lNzMrLD06J2EwPD9vDlkdPl9XCWwjNCZ7ARo6IF4iOWoKAgJsJywyMz5SVg==")));
                }
                for (Integer user : removedUsers) {
                    if (SyncManager.this.mUserManager.getUserInfo(user) != null) continue;
                    SyncManager.this.onUserRemoved(user);
                }
            }
            if (isLoggable) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4AKmwJIAZjHjM8Ly0iDmkzLCxoDiwgPQgADmIFMCRqHhoeKRcXKHgaLAVpJys3")) + operations.size()));
            }
            Collections.sort(operations);
            if (isLoggable) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKW8FJAZ9JB08LwdbCH4wRStoASw0PQc2M2AwNyNsJyAuLBhbCmoFGgRvN1RF")));
            }
            int N = operations.size();
            for (int i = 0; i < N; ++i) {
                boolean roomAvailable;
                SyncOperation candidate = (SyncOperation)operations.get(i);
                boolean candidateIsInitialization = candidate.isInitialization();
                int numInit = 0;
                int numRegular = 0;
                ActiveSyncContext conflict = null;
                ActiveSyncContext longRunning = null;
                ActiveSyncContext toReschedule = null;
                ActiveSyncContext oldestNonExpeditedRegular = null;
                for (ActiveSyncContext activeSyncContext : SyncManager.this.mActiveSyncContexts) {
                    SyncOperation activeOp = activeSyncContext.mSyncOperation;
                    if (activeOp.isInitialization()) {
                        ++numInit;
                    } else {
                        ++numRegular;
                        if (!(activeOp.isExpedited() || oldestNonExpeditedRegular != null && oldestNonExpeditedRegular.mStartTime <= activeSyncContext.mStartTime)) {
                            oldestNonExpeditedRegular = activeSyncContext;
                        }
                    }
                    if (activeOp.account.type.equals(candidate.account.type) && activeOp.authority.equals(candidate.authority) && activeOp.userId == candidate.userId && (!activeOp.allowParallelSyncs || activeOp.account.name.equals(candidate.account.name))) {
                        conflict = activeSyncContext;
                        continue;
                    }
                    if (candidateIsInitialization != activeOp.isInitialization() || activeSyncContext.mStartTime + MAX_TIME_PER_SYNC >= now) continue;
                    longRunning = activeSyncContext;
                }
                if (isLoggable) {
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGgFAix9AQo/PxhSVg==")) + (i + 1) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgAPnsFSFo=")) + N + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ODo6Vg==")) + candidate));
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl86CGwVEhF9JwozLD0MXm8VLAZ5AVRF")) + numInit + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186CGwVEhF9JwozLD0MAmkjEgVlETg5OBhSVg==")) + numRegular));
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl86DmozBi1pNzA2Kj0cDmkOIyg=")) + longRunning));
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl86OWozBi5gHgY5LF8HOg==")) + conflict));
                    Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl86D2oFMCthJwoMKi0YWmgwTStrER4/LhgqU2IKJDBsHiQ7Pl9XVg==")) + oldestNonExpeditedRegular));
                }
                boolean bl = candidateIsInitialization ? numInit < MAX_SIMULTANEOUS_INITIALIZATION_SYNCS : (roomAvailable = numRegular < MAX_SIMULTANEOUS_REGULAR_SYNCS);
                if (conflict != null) {
                    if (candidateIsInitialization && !conflict.mSyncOperation.isInitialization() && numInit < MAX_SIMULTANEOUS_INITIALIZATION_SYNCS) {
                        toReschedule = conflict;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRjDlk9PxciDmk3TQRrDjAqIwguIGYKTSxsND8pLAc2KmgnICVlERo2JAMiKGw0PD1vETAZLAg+DmUaTTdmHgY1KjkmLm4jJCtsIzwZIxgmLGIFMyNlESgiKQdfI28aDQJ7AVRF")) + conflict));
                    } else {
                        if (!candidate.expedited || conflict.mSyncOperation.expedited || candidateIsInitialization != conflict.mSyncOperation.isInitialization()) continue;
                        toReschedule = conflict;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRjDlk9PxciDmk3TQRrDjAqIwguIGYKTSxsND8pLAc2KmgnICVlERo2JAMiKGw0PDFoNFE0LRgYLGgVMyhmHiAxKAgpOmwzLC1qESg5PQc6CGMKAjVvATAyOTpXVg==")) + conflict));
                    }
                } else if (!roomAvailable) {
                    if (candidate.isExpedited() && oldestNonExpeditedRegular != null && !candidateIsInitialization) {
                        toReschedule = oldestNonExpeditedRegular;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRjDlk9PxciDmk3TQRrDjAqIwguIGYKTSxsND8pLAc2KmgnICVlERo2JAMiKGw0PDFoNFE0LRgYLGgVMyhjASs8Iz0MOWkwLyhvERkrKS0uKk5TOFo=")) + oldestNonExpeditedRegular));
                    } else {
                        if (longRunning == null || candidateIsInitialization != longRunning.mSyncOperation.isInitialization()) continue;
                        toReschedule = longRunning;
                        Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRjDlk9PxciDmk3TQRrDjAqIwguIGYKTSxsND8pLAc2KmgnICVlERo2JAMiIGpTPCRuJy83Kj4AD3sFHiVgNDsoPxhSVg==")) + longRunning));
                    }
                }
                if (toReschedule != null) {
                    this.runSyncFinishedOrCanceledLocked(null, toReschedule);
                    SyncManager.this.scheduleSyncOperation(toReschedule.mSyncOperation);
                }
                SyncQueue syncQueue2 = SyncManager.this.mSyncQueue;
                synchronized (syncQueue2) {
                    SyncManager.this.mSyncQueue.remove(candidate);
                }
                this.dispatchSyncOperation(candidate);
            }
            return nextReadyToRunTime;
        }

        private boolean dispatchSyncOperation(SyncOperation op) {
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKW8FJAZ9JBoPLQcYP2cKTStsNzg/IxgAKnc0ODJuCiAqLBgfJGsjGgVqJyM3IBcXL2UjBgJuDVFF")) + op));
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDXsFJClmHgYuKAMmL2gjMClsIFAr")) + SyncManager.this.mActiveSyncContexts.size()));
            for (ActiveSyncContext syncContext : SyncManager.this.mActiveSyncContexts) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)syncContext.toString());
            }
            SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = SyncManager.this.mSyncAdapters.getServiceInfo(op.account, op.authority);
            if (syncAdapterInfo == null) {
                Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CHgwMyhiNAY2KF4mOX4wAj9lNzMrLRgqO2EVFiBlMCAvKQdeJA==")) + op.authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmgVEiVmNAY2KCkmL2kgBgZqARouKTo6ImAjMyNvATBF"))));
                SyncManager.this.mSyncStorageEngine.removeAuthority(op.account, op.userId, op.authority);
                return false;
            }
            ActiveSyncContext activeSyncContext = new ActiveSyncContext(op, this.insertStartSyncEvent(op));
            activeSyncContext.mSyncInfo = SyncManager.this.mSyncStorageEngine.addActiveSync(activeSyncContext);
            SyncManager.this.mActiveSyncContexts.add(activeSyncContext);
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKW8FJAZ9JBoPLQcYP2cKTStsNzg/IxgAKnc0ODZqHiQ7Iz42KmsnIFo=")) + activeSyncContext));
            if (!activeSyncContext.bindToSyncAdapter(syncAdapterInfo, op.userId)) {
                Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jj4YCGgJIDdmEQo/KggmLn4zHjdqAQIgLgQ6CmAkOFo=")) + syncAdapterInfo));
                this.closeActiveSyncContext(activeSyncContext);
                return false;
            }
            return true;
        }

        private void runBoundToSyncAdapter(ActiveSyncContext activeSyncContext, ISyncAdapter syncAdapter) {
            activeSyncContext.mSyncAdapter = syncAdapter;
            SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            try {
                activeSyncContext.mIsLinkedToDeath = true;
                syncAdapter.asBinder().linkToDeath((IBinder.DeathRecipient)activeSyncContext, 0);
                syncAdapter.startSync(activeSyncContext, syncOperation.authority, syncOperation.account, syncOperation.extras);
            }
            catch (RemoteException remoteExc) {
                Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+J2sjNF5mHiAqLBUYPWgwBl5uARoqPyo6JX0FLCJvETMpKCpXU2sFEgNsASgKIRcuLGUVLD1vAS87PhcMM28zLCBiDgovKhccDmkFSFo=")), (Throwable)remoteExc);
                this.closeActiveSyncContext(activeSyncContext);
                SyncManager.this.increaseBackoffSetting(syncOperation);
                SyncManager.this.scheduleSyncOperation(new SyncOperation(syncOperation));
            }
            catch (RuntimeException exc) {
                this.closeActiveSyncContext(activeSyncContext);
                Log.e((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4+I2gzRQZLHywvKj42MW8jGhVuETAgKQcqI2AgRCNqJAYiKT4fJGwgMDNvJCwwJj09L2oaGjF+NB4gIz41OA==")) + syncOperation), (Throwable)exc);
            }
        }

        private void cancelActiveSyncLocked(Account account, int userId, String authority) {
            ArrayList<ActiveSyncContext> activeSyncs = new ArrayList<ActiveSyncContext>(SyncManager.this.mActiveSyncContexts);
            for (ActiveSyncContext activeSyncContext : activeSyncs) {
                if (activeSyncContext == null || account != null && !account.equals((Object)activeSyncContext.mSyncOperation.account) || authority != null && !authority.equals(activeSyncContext.mSyncOperation.authority) || userId != -1 && userId != activeSyncContext.mSyncOperation.userId) continue;
                this.runSyncFinishedOrCanceledLocked(null, activeSyncContext);
            }
        }

        private void runSyncFinishedOrCanceledLocked(SyncResult syncResult, ActiveSyncContext activeSyncContext) {
            int upstreamActivity;
            int downstreamActivity;
            String historyMessage;
            if (activeSyncContext.mIsLinkedToDeath) {
                activeSyncContext.mSyncAdapter.asBinder().unlinkToDeath((IBinder.DeathRecipient)activeSyncContext, 0);
                activeSyncContext.mIsLinkedToDeath = false;
            }
            this.closeActiveSyncContext(activeSyncContext);
            SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            long elapsedTime = SystemClock.elapsedRealtime() - activeSyncContext.mStartTime;
            if (syncResult != null) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj0uCGcwAiZ9IjwzKj0cL2wzGix9JAoALRgcJWIKTSBuVyAKLxg2KmoKLAZoESwCMTkiVg==")) + syncOperation + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmgaLAVgEQ08")) + syncResult));
                if (!syncResult.hasError()) {
                    historyMessage = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0uOWszNANhJ1RF"));
                    downstreamActivity = 0;
                    upstreamActivity = 0;
                    SyncManager.this.clearBackoffSetting(syncOperation);
                } else {
                    Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoFNCxLESgZKj0pOm8KTStsNzg/IxgAKksVSFo=")) + syncOperation + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + syncResult));
                    if (!syncResult.syncAlreadyInProgress) {
                        SyncManager.this.increaseBackoffSetting(syncOperation);
                    }
                    SyncManager.this.maybeRescheduleSync(syncResult, syncOperation);
                    historyMessage = ContentResolverCompat.syncErrorToString(this.syncResultToErrorNumber(syncResult));
                    downstreamActivity = 0;
                    upstreamActivity = 0;
                }
                SyncManager.this.setDelayUntilTime(syncOperation, syncResult.delayUntil);
            } else {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj0uCGcwAiZ9IjwzKj0cL2wzGix9JAoALRgcJWIKTSBuVyAKKAhbKmgjNAJoESwCMTkiVg==")) + syncOperation));
                if (activeSyncContext.mSyncAdapter != null) {
                    try {
                        activeSyncContext.mSyncAdapter.cancelSync(activeSyncContext);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                historyMessage = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRiDgpF"));
                downstreamActivity = 0;
                upstreamActivity = 0;
            }
            this.stopSyncEvent(activeSyncContext.mHistoryRowId, syncOperation, historyMessage, upstreamActivity, downstreamActivity, elapsedTime);
            if (syncResult != null && syncResult.fullSyncRequested) {
                SyncManager.this.scheduleSyncOperation(new SyncOperation(syncOperation.account, syncOperation.userId, syncOperation.reason, syncOperation.syncSource, syncOperation.authority, new Bundle(), 0L, 0L, syncOperation.backoff, syncOperation.delayUntil, syncOperation.allowParallelSyncs));
            }
        }

        private void closeActiveSyncContext(ActiveSyncContext activeSyncContext) {
            activeSyncContext.close();
            SyncManager.this.mActiveSyncContexts.remove(activeSyncContext);
            SyncManager.this.mSyncStorageEngine.removeActiveSync(activeSyncContext.mSyncInfo, activeSyncContext.mSyncOperation.userId);
        }

        private int syncResultToErrorNumber(SyncResult syncResult) {
            if (syncResult.syncAlreadyInProgress) {
                return 1;
            }
            if (syncResult.stats.numAuthExceptions > 0L) {
                return 2;
            }
            if (syncResult.stats.numIoExceptions > 0L) {
                return 3;
            }
            if (syncResult.stats.numParseExceptions > 0L) {
                return 4;
            }
            if (syncResult.stats.numConflictDetectedExceptions > 0L) {
                return 5;
            }
            if (syncResult.tooManyDeletions) {
                return 6;
            }
            if (syncResult.tooManyRetries) {
                return 7;
            }
            if (syncResult.databaseError) {
                return 8;
            }
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KS4tOGsaFitLHlk1LF4mMW9STTdlMzwgKS0MKWE0ODZqHiQ9LyohJA==")) + syncResult);
        }

        private void manageSyncNotificationLocked() {
            boolean shouldInstall;
            boolean shouldCancel;
            if (SyncManager.this.mActiveSyncContexts.isEmpty()) {
                this.mSyncNotificationInfo.startTime = null;
                shouldCancel = this.mSyncNotificationInfo.isActive;
                shouldInstall = false;
            } else {
                long now = SystemClock.elapsedRealtime();
                if (this.mSyncNotificationInfo.startTime == null) {
                    this.mSyncNotificationInfo.startTime = now;
                }
                if (this.mSyncNotificationInfo.isActive) {
                    shouldCancel = false;
                    shouldInstall = false;
                } else {
                    boolean timeToShowNotification;
                    shouldCancel = false;
                    boolean bl = timeToShowNotification = now > this.mSyncNotificationInfo.startTime + SYNC_NOTIFICATION_DELAY;
                    if (timeToShowNotification) {
                        shouldInstall = true;
                    } else {
                        shouldInstall = false;
                        for (ActiveSyncContext activeSyncContext : SyncManager.this.mActiveSyncContexts) {
                            boolean manualSync = activeSyncContext.mSyncOperation.extras.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4AKmszNFo=")), false);
                            if (!manualSync) continue;
                            shouldInstall = true;
                            break;
                        }
                    }
                }
            }
            if (shouldCancel && !shouldInstall) {
                this.sendSyncStateIntent();
                this.mSyncNotificationInfo.isActive = false;
            }
            if (shouldInstall) {
                this.sendSyncStateIntent();
                this.mSyncNotificationInfo.isActive = true;
            }
        }

        private void manageSyncAlarmLocked(long nextPeriodicEventElapsedTime, long nextPendingEventElapsedTime) {
            boolean needAlarm;
            if (!SyncManager.this.mDataConnectionIsConnected) {
                return;
            }
            if (SyncManager.this.mStorageIsLow) {
                return;
            }
            long notificationTime = !((SyncManager)SyncManager.this).mSyncHandler.mSyncNotificationInfo.isActive && ((SyncManager)SyncManager.this).mSyncHandler.mSyncNotificationInfo.startTime != null ? ((SyncManager)SyncManager.this).mSyncHandler.mSyncNotificationInfo.startTime + SYNC_NOTIFICATION_DELAY : Long.MAX_VALUE;
            long earliestTimeoutTime = Long.MAX_VALUE;
            for (ActiveSyncContext currentSyncContext : SyncManager.this.mActiveSyncContexts) {
                long currentSyncTimeoutTime = currentSyncContext.mTimeoutStartTime + MAX_TIME_PER_SYNC;
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+MzwsLT0qI2YwLyNlJxodKAQhJGUIMAVqESg6IAgMHGoaOCRlMhowIwgtOHU3IA1lDx08KQgpOg==")) + currentSyncTimeoutTime));
                if (earliestTimeoutTime <= currentSyncTimeoutTime) continue;
                earliestTimeoutTime = currentSyncTimeoutTime;
            }
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+MzwbLD0qI2IwGiZpATAiKQgqXWoFEj97AR4cDRhSVg==")) + notificationTime));
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+MzwgLRcMKGMKLDZqHzAiKS4AKW8KMAplEQ40DRcAOnkVSFo=")) + earliestTimeoutTime));
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+MzwbLhdfCmkaLDVvDlktKi4YGW8zNARsDyg7OwgiOm4KLAxsJyA0PhgYKXsFSFo=")) + nextPeriodicEventElapsedTime));
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+MzwbLhdfCmkaLCluHhodLwYAMmsFBiBgEQIoJxguLG4bLD1vJxk3LAc1OA==")) + nextPendingEventElapsedTime));
            long alarmTime = Math.min(notificationTime, earliestTimeoutTime);
            alarmTime = Math.min(alarmTime, nextPeriodicEventElapsedTime);
            alarmTime = Math.min(alarmTime, nextPendingEventElapsedTime);
            long now = SystemClock.elapsedRealtime();
            if (alarmTime < now + 30000L) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+Mzw/IwgtJH0KTSRlNFETKi4mJ3gVAiV7Diw6JikiOmwKOARvMyc3")) + alarmTime + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWgaMAZjDlk9Pxg2DX4zSFo=")) + (now + 30000L)));
                alarmTime = now + 30000L;
            } else if (alarmTime > now + 0x6DDD00L) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+CGsVPCtpJwY2LysiCG4gRSN+Mzw/IwgtJH0KTSRlNFETKi4mJ3gVAiV7Diw6JikiO2kFND9pIyc3")) + alarmTime + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWgaMAZjDlk9Pxg2DX4zSFo=")) + (now + 30000L)));
                alarmTime = now + 0x6DDD00L;
            }
            boolean shouldSet = false;
            boolean shouldCancel = false;
            boolean alarmIsActive = this.mAlarmScheduleTime != null && now < this.mAlarmScheduleTime;
            boolean bl = needAlarm = alarmTime != Long.MAX_VALUE;
            if (needAlarm) {
                if (!alarmIsActive || alarmTime < this.mAlarmScheduleTime) {
                    shouldSet = true;
                }
            } else {
                shouldCancel = alarmIsActive;
            }
            SyncManager.this.ensureAlarmService();
            if (shouldSet) {
                Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uL2wVNANmHgY2KCkmLmwzQQZ4HiwZLl86O2AaPDVsCiAcKC4qO2sjNCZ7DiAoJS01L2oFMzZlJFA3LgcpOGgVHjdhESg/KF4mLmwjPCt4EVRF")) + alarmTime + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186CGowPyhjASs8")) + now + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + (alarmTime - now) / 1000L + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2M2swLyhiNyw1KgMmDm8KElo="))));
                this.mAlarmScheduleTime = alarmTime;
                SyncManager.this.mAlarmService.setExact(2, alarmTime, SyncManager.this.mSyncAlarmIntent);
            } else if (shouldCancel) {
                this.mAlarmScheduleTime = null;
                SyncManager.this.mAlarmService.cancel(SyncManager.this.mSyncAlarmIntent);
            }
        }

        private void sendSyncStateIntent() {
        }

        public long insertStartSyncEvent(SyncOperation syncOperation) {
            int source = syncOperation.syncSource;
            long now = System.currentTimeMillis();
            return SyncManager.this.mSyncStorageEngine.insertStartSyncEvent(syncOperation.account, syncOperation.userId, syncOperation.reason, syncOperation.authority, now, source, syncOperation.isInitialization(), syncOperation.extras);
        }

        public void stopSyncEvent(long rowId, SyncOperation syncOperation, String resultMessage, int upstreamActivity, int downstreamActivity, long elapsedTime) {
            SyncManager.this.mSyncStorageEngine.stopSyncEvent(rowId, elapsedTime, resultMessage, downstreamActivity, upstreamActivity);
        }

        class SyncNotificationInfo {
            public boolean isActive = false;
            public Long startTime = null;

            SyncNotificationInfo() {
            }

            public void toString(StringBuilder sb) {
                sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2EWswMC9mNDM8"))).append(this.isActive).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWwFJARmHwozKgcLOg=="))).append(this.startTime);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                this.toString(sb);
                return sb.toString();
            }
        }
    }

    class ServiceConnectionData {
        public final ActiveSyncContext activeSyncContext;
        public final ISyncAdapter syncAdapter;

        ServiceConnectionData(ActiveSyncContext activeSyncContext, ISyncAdapter syncAdapter) {
            this.activeSyncContext = activeSyncContext;
            this.syncAdapter = syncAdapter;
        }
    }

    private class SyncTimeTracker {
        boolean mLastWasSyncing = false;
        long mWhenSyncStarted = 0L;
        private long mTimeSpentSyncing;

        private SyncTimeTracker() {
        }

        public synchronized void update() {
            boolean isSyncInProgress;
            boolean bl = isSyncInProgress = !SyncManager.this.mActiveSyncContexts.isEmpty();
            if (isSyncInProgress == this.mLastWasSyncing) {
                return;
            }
            long now = SystemClock.elapsedRealtime();
            if (isSyncInProgress) {
                this.mWhenSyncStarted = now;
            } else {
                this.mTimeSpentSyncing += now - this.mWhenSyncStarted;
            }
            this.mLastWasSyncing = isSyncInProgress;
        }

        public synchronized long timeSpentSyncing() {
            if (!this.mLastWasSyncing) {
                return this.mTimeSpentSyncing;
            }
            long now = SystemClock.elapsedRealtime();
            return this.mTimeSpentSyncing + (now - this.mWhenSyncStarted);
        }
    }

    class ActiveSyncContext
    extends ISyncContext.Stub
    implements ServiceConnection,
    IBinder.DeathRecipient {
        final SyncOperation mSyncOperation;
        final long mHistoryRowId;
        ISyncAdapter mSyncAdapter;
        final long mStartTime;
        long mTimeoutStartTime;
        boolean mBound;
        VSyncInfo mSyncInfo;
        boolean mIsLinkedToDeath = false;

        public ActiveSyncContext(SyncOperation syncOperation, long historyRowId) {
            this.mSyncOperation = syncOperation;
            this.mHistoryRowId = historyRowId;
            this.mSyncAdapter = null;
            this.mTimeoutStartTime = this.mStartTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void sendHeartbeat() {
        }

        @Override
        public void onFinished(SyncResult result) {
            Log.v((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4cW2UVBi9hJBo/KF8HOg==")) + this));
            SyncManager.this.sendSyncFinishedOrCanceledMessage(this, result);
        }

        public void toString(StringBuilder sb) {
            sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP28gMFFjDl0/PxhSVg=="))).append(this.mStartTime).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186DWQFAiNiDh4vLBYqLm4gRQZnER4eLl86Vg=="))).append(this.mTimeoutStartTime).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186DX0FAgNmHh4qLQYuDWULLCx4EVRF"))).append(this.mHistoryRowId).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWkVBiloJyQ/Iz0iLmwjNCZ4EVRF"))).append(this.mSyncOperation);
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Message msg = SyncManager.this.mSyncHandler.obtainMessage();
            msg.what = 4;
            msg.obj = new ServiceConnectionData(this, ISyncAdapter.Stub.asInterface(service));
            SyncManager.this.mSyncHandler.sendMessage(msg);
        }

        public void onServiceDisconnected(ComponentName name) {
            Message msg = SyncManager.this.mSyncHandler.obtainMessage();
            msg.what = 5;
            msg.obj = new ServiceConnectionData(this, null);
            SyncManager.this.mSyncHandler.sendMessage(msg);
        }

        boolean bindToSyncAdapter(SyncAdaptersCache.SyncAdapterInfo info, int userId) {
            Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4YCGgIMCVpJwY2LysiPm4gTQZrDg0xPQhSVg==")) + info.serviceInfo + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186OWozBiZiDiggKQdfDn4zSFo=")) + this));
            Intent intent = new Intent();
            intent.setAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k5Ki0YLmkjMAZ1MjA0LC42HWIaPDNqHgo7")));
            intent.setComponent(info.componentName);
            this.mBound = true;
            boolean bindResult = VActivityManager.get().bindService(SyncManager.this.mContext, intent, this, 21, this.mSyncOperation.userId);
            if (!bindResult) {
                this.mBound = false;
            }
            return bindResult;
        }

        protected void close() {
            Log.d((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcHGUVBixqNyw1KgYqIW8VAhFrETg7KgguCHc0OCZsJB4dLy4YCmoFGgR7AVRF")) + this));
            if (this.mBound) {
                this.mBound = false;
                VActivityManager.get().unbindService(SyncManager.this.mContext, this);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            this.toString(sb);
            return sb.toString();
        }

        public void binderDied() {
            SyncManager.this.sendSyncFinishedOrCanceledMessage(this, null);
        }
    }

    class SyncAlarmIntentReceiver
    extends BroadcastReceiver {
        SyncAlarmIntentReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            SyncManager.this.sendSyncAlarmMessage();
        }
    }

    class SyncHandlerMessagePayload {
        public final ActiveSyncContext activeSyncContext;
        public final SyncResult syncResult;

        SyncHandlerMessagePayload(ActiveSyncContext syncContext, SyncResult syncResult) {
            this.activeSyncContext = syncContext;
            this.syncResult = syncResult;
        }
    }
}

