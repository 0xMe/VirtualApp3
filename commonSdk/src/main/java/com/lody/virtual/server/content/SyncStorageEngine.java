/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.PeriodicSync
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteQueryBuilder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IInterface
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 *  android.util.SparseArray
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncStatusInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.Xml;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.AtomicFile;
import com.lody.virtual.helper.utils.FastXmlSerializer;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.server.accounts.AccountAndUser;
import com.lody.virtual.server.content.SyncManager;
import com.lody.virtual.server.content.SyncQueue;
import com.lody.virtual.server.content.VSyncInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SyncStorageEngine
extends Handler {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguVg=="));
    private static final boolean DEBUG = false;
    private static final String TAG_FILE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YCGsxEjdgNCA9KAguWWwjOCs="));
    private static final String XML_ATTR_NEXT_AUTHORITY_ID = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4uIGwLJAVmHho1Iz0cLmghLCw="));
    private static final String XML_ATTR_LISTEN_FOR_TICKLES = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCZODjw1IzlXLmwjAiFlESg6"));
    private static final String XML_ATTR_SYNC_RANDOM_OFFSET = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4iPm8zNAZrDlkPKAcqDW8VBgM="));
    private static final String XML_ATTR_ENABLED = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitiEVRF"));
    private static final String XML_ATTR_USER = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo="));
    private static final String XML_TAG_LISTEN_FOR_TICKLES = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCZqNB4qJBccP2wFOCtsJ1RF"));
    private static final long DEFAULT_POLL_FREQUENCY_SECONDS = 86400L;
    private static final double DEFAULT_FLEX_PERCENT_SYNC = 0.04;
    private static final long DEFAULT_MIN_FLEX_ALLOWED_SECS = 5L;
    public static final int EVENT_START = 0;
    public static final int EVENT_STOP = 1;
    public static final String[] EVENTS;
    public static final int SOURCE_SERVER = 0;
    public static final int SOURCE_LOCAL = 1;
    public static final int SOURCE_POLL = 2;
    public static final int SOURCE_USER = 3;
    public static final int SOURCE_PERIODIC = 4;
    public static final long NOT_IN_BACKOFF_MODE = -1L;
    public static final String[] SOURCES;
    public static final String MESG_SUCCESS;
    public static final String MESG_CANCELED;
    public static final int MAX_HISTORY = 100;
    private static final int MSG_WRITE_STATUS = 1;
    private static final long WRITE_STATUS_DELAY = 600000L;
    private static final int MSG_WRITE_STATISTICS = 2;
    private static final long WRITE_STATISTICS_DELAY = 1800000L;
    private static final boolean SYNC_ENABLED_DEFAULT = false;
    private static final int ACCOUNTS_VERSION = 2;
    private static HashMap<String, String> sAuthorityRenames;
    private final SparseArray<AuthorityInfo> mAuthorities = new SparseArray();
    private final HashMap<AccountAndUser, AccountInfo> mAccounts = new HashMap();
    private final ArrayList<PendingOperation> mPendingOperations = new ArrayList();
    private final SparseArray<ArrayList<VSyncInfo>> mCurrentSyncs = new SparseArray();
    private final SparseArray<SyncStatusInfo> mSyncStatus = new SparseArray();
    private final ArrayList<SyncHistoryItem> mSyncHistory = new ArrayList();
    private final RemoteCallbackList<ISyncStatusObserver> mChangeListeners = new RemoteCallbackList();
    private final HashMap<ComponentName, SparseArray<AuthorityInfo>> mServices = new HashMap();
    private int mNextAuthorityId = 0;
    private final DayStats[] mDayStats = new DayStats[28];
    private final Calendar mCal;
    private int mYear;
    private int mYearInDays;
    private final Context mContext;
    private static volatile SyncStorageEngine sSyncStorageEngine;
    private int mSyncRandomOffset;
    private final AtomicFile mAccountInfoFile;
    private final AtomicFile mStatusFile;
    private final AtomicFile mStatisticsFile;
    private final AtomicFile mPendingFile;
    private static final int PENDING_FINISH_TO_WRITE = 4;
    private int mNumPendingFinished = 0;
    private int mNextHistoryId = 0;
    private SparseArray<Boolean> mMasterSyncAutomatically = new SparseArray();
    private boolean mDefaultMasterSyncAutomatically;
    private OnSyncRequestListener mSyncRequestListener;
    public static final int STATUS_FILE_END = 0;
    public static final int STATUS_FILE_ITEM = 100;
    public static final int PENDING_OPERATION_VERSION = 3;
    private static final String XML_ATTR_AUTHORITYID;
    private static final String XML_ATTR_SOURCE;
    private static final String XML_ATTR_EXPEDITED;
    private static final String XML_ATTR_REASON;
    private static final String XML_ATTR_VERSION;
    public static final int STATISTICS_FILE_END = 0;
    public static final int STATISTICS_FILE_ITEM_OLD = 100;
    public static final int STATISTICS_FILE_ITEM = 101;

    private SyncStorageEngine(Context context, File syncDir) {
        this.mContext = context;
        sSyncStorageEngine = this;
        this.mCal = Calendar.getInstance(TimeZone.getTimeZone(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JSwIBnU0IFo="))));
        this.mDefaultMasterSyncAutomatically = false;
        this.maybeDeleteLegacyPendingInfoLocked(syncDir);
        this.mAccountInfoFile = new AtomicFile(new File(syncDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmESs2LRdXCA=="))));
        this.mStatusFile = new AtomicFile(new File(syncDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKNANONCwzKj5SVg=="))));
        this.mPendingFile = new AtomicFile(new File(syncDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiIFkaKgdbVg=="))));
        this.mStatisticsFile = new AtomicFile(new File(syncDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyZ9NAY2"))));
        this.readAccountInfoLocked();
        this.readStatusLocked();
        this.readPendingOperationsLocked();
        this.readStatisticsLocked();
        this.readAndDeleteLegacyAccountInfoLocked();
        this.writeAccountInfoLocked();
        this.writeStatusLocked();
        this.writePendingOperationsLocked();
        this.writeStatisticsLocked();
    }

    public static void init(Context context) {
        if (sSyncStorageEngine != null) {
            return;
        }
        File dataDir = VEnvironment.getSyncDirectory();
        FileUtils.ensureDirCreate(dataDir);
        sSyncStorageEngine = new SyncStorageEngine(context, dataDir);
    }

    public static SyncStorageEngine getSingleton() {
        if (sSyncStorageEngine == null) {
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALHsFAiZjAQozLwdbMWgVGiw=")));
        }
        return sSyncStorageEngine;
    }

    protected void setOnSyncRequestListener(OnSyncRequestListener listener) {
        if (this.mSyncRequestListener == null) {
            this.mSyncRequestListener = listener;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleMessage(Message msg) {
        if (msg.what == 1) {
            SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
            synchronized (sparseArray) {
                this.writeStatusLocked();
            }
        }
        if (msg.what == 2) {
            SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
            synchronized (sparseArray) {
                this.writeStatisticsLocked();
            }
        }
    }

    public int getSyncRandomOffset() {
        return this.mSyncRandomOffset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addStatusChangeListener(int mask, ISyncStatusObserver callback) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            this.mChangeListeners.register((IInterface)callback, (Object)mask);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeStatusChangeListener(ISyncStatusObserver callback) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            this.mChangeListeners.unregister((IInterface)callback);
        }
    }

    public static long calculateDefaultFlexTime(long syncTimeSeconds) {
        if (syncTimeSeconds < 5L) {
            return 0L;
        }
        if (syncTimeSeconds < 86400L) {
            return (long)((double)syncTimeSeconds * 0.04);
        }
        return 3456L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void reportChange(int which) {
        ArrayList<ISyncStatusObserver> reports = null;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            int i = this.mChangeListeners.beginBroadcast();
            while (i > 0) {
                Integer mask;
                if ((which & (mask = (Integer)this.mChangeListeners.getBroadcastCookie(--i))) == 0) continue;
                if (reports == null) {
                    reports = new ArrayList<ISyncStatusObserver>(i);
                }
                reports.add((ISyncStatusObserver)this.mChangeListeners.getBroadcastItem(i));
            }
            this.mChangeListeners.finishBroadcast();
        }
        if (reports != null) {
            int i = reports.size();
            while (i > 0) {
                --i;
                try {
                    ((ISyncStatusObserver)reports.get(i)).onStatusChanged(which);
                }
                catch (RemoteException remoteException) {}
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean getSyncAutomatically(Account account, int userId, String providerName) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            if (account != null) {
                // MONITOREXIT @DISABLED, blocks:[0, 1, 4] lbl5 : MonitorExitStatement: MONITOREXIT : var4_4
                AuthorityInfo authority = this.getAuthorityLocked(account, userId, providerName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcwAiZ9IiAvLBdfD24gBi9oJzgdLAcYVg==")));
                return authority != null && authority.enabled;
            }
            int i = this.mAuthorities.size();
            while (i > 0) {
                AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.valueAt(--i);
                if (!authority.authority.equals(providerName) || authority.userId != userId || !authority.enabled) continue;
                return true;
            }
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setSyncAutomatically(Account account, int userId, String providerName, boolean sync) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getOrCreateAuthorityLocked(account, userId, providerName, -1, false);
            if (authority.enabled == sync) {
                return;
            }
            authority.enabled = sync;
            this.writeAccountInfoLocked();
        }
        if (sync) {
            this.requestSync(account, userId, -6, providerName, new Bundle());
        }
        this.reportChange(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getIsSyncable(Account account, int userId, String providerName) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            if (account != null) {
                AuthorityInfo authority = this.getAuthorityLocked(account, userId, providerName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0aLF5nDlk5LwcuCGkjSFo=")));
                if (authority == null) {
                    return -1;
                }
                return authority.syncable;
            }
            int i = this.mAuthorities.size();
            while (i > 0) {
                AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.valueAt(--i);
                if (!authority.authority.equals(providerName)) continue;
                return authority.syncable;
            }
            return -1;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setIsSyncable(Account account, int userId, String providerName, int syncable) {
        if (syncable > 1) {
            syncable = 1;
        } else if (syncable < -1) {
            syncable = -1;
        }
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getOrCreateAuthorityLocked(account, userId, providerName, -1, false);
            if (authority.syncable == syncable) {
                return;
            }
            authority.syncable = syncable;
            this.writeAccountInfoLocked();
        }
        if (syncable > 0) {
            this.requestSync(account, userId, -5, providerName, new Bundle());
        }
        this.reportChange(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pair<Long, Long> getBackoff(Account account, int userId, String providerName) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getAuthorityLocked(account, userId, providerName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMjJCljJB4+KD5SVg==")));
            if (authority == null || authority.backoffTime < 0L) {
                return null;
            }
            return Pair.create((Object)authority.backoffTime, (Object)authority.backoffDelay);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setBackoff(Account account, int userId, String providerName, long nextSyncTime, long nextDelay) {
        boolean changed = false;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            if (account == null || providerName == null) {
                for (AccountInfo accountInfo : this.mAccounts.values()) {
                    if (account != null && !account.equals((Object)accountInfo.accountAndUser.account) && userId != accountInfo.accountAndUser.userId) continue;
                    for (AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                        if (providerName != null && !providerName.equals(authorityInfo.authority) || authorityInfo.backoffTime == nextSyncTime && authorityInfo.backoffDelay == nextDelay) continue;
                        authorityInfo.backoffTime = nextSyncTime;
                        authorityInfo.backoffDelay = nextDelay;
                        changed = true;
                    }
                }
            } else {
                AuthorityInfo authority = this.getOrCreateAuthorityLocked(account, userId, providerName, -1, true);
                if (authority.backoffTime == nextSyncTime && authority.backoffDelay == nextDelay) {
                    return;
                }
                authority.backoffTime = nextSyncTime;
                authority.backoffDelay = nextDelay;
                changed = true;
            }
        }
        if (changed) {
            this.reportChange(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearAllBackoffsLocked(SyncQueue syncQueue) {
        boolean changed = false;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            for (AccountInfo accountInfo : this.mAccounts.values()) {
                for (AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                    if (authorityInfo.backoffTime == -1L && authorityInfo.backoffDelay == -1L) continue;
                    authorityInfo.backoffTime = -1L;
                    authorityInfo.backoffDelay = -1L;
                    syncQueue.onBackoffChanged(accountInfo.accountAndUser.account, accountInfo.accountAndUser.userId, authorityInfo.authority, 0L);
                    changed = true;
                }
            }
        }
        if (changed) {
            this.reportChange(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setDelayUntilTime(Account account, int userId, String providerName, long delayUntil) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getOrCreateAuthorityLocked(account, userId, providerName, -1, true);
            if (authority.delayUntil == delayUntil) {
                return;
            }
            authority.delayUntil = delayUntil;
        }
        this.reportChange(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getDelayUntilTime(Account account, int userId, String providerName) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getAuthorityLocked(account, userId, providerName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFNCR9AQZKKj42MW8zSFo=")));
            if (authority == null) {
                return 0L;
            }
            return authority.delayUntil;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateOrRemovePeriodicSync(PeriodicSync toUpdate, int userId, boolean add) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            block16: {
                if (toUpdate.period <= 0L && add) {
                    Log.e((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGixLVlA8M15aOmoFFiVvAQIvPQgcJ2YwLDV5HgYqLD1XJ2U3IAVqIzwaJxcMKGoaFkhqHFk0IwgALmgYICthNAY1KBccP2IKLCZoIFArLRgqIE4FSFo=")) + add));
                }
                if (toUpdate.extras == null) {
                    Log.e((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoJICtnEQoqLwgpCH4wAiBlJCgdLgQ6KmIFICBlMCAhKC1XDmsFATRlER03IAgiI2kFLDFnDllALQgID2wjNExiASwzKi02MW4IAj9lNzMxPQg+IGJTQVo=")) + add));
                }
                AuthorityInfo authority = this.getOrCreateAuthorityLocked(toUpdate.account, userId, toUpdate.authority, -1, false);
                if (add) {
                    boolean alreadyPresent = false;
                    int N = authority.periodicSyncs.size();
                    for (int i = 0; i < N; ++i) {
                        PeriodicSync syncInfo = authority.periodicSyncs.get(i);
                        if (!mirror.android.content.PeriodicSync.syncExtrasEquals(toUpdate.extras, syncInfo.extras)) continue;
                        if (toUpdate.period == syncInfo.period && mirror.android.content.PeriodicSync.flexTime.get(toUpdate) == mirror.android.content.PeriodicSync.flexTime.get(syncInfo)) {
                            return;
                        }
                        authority.periodicSyncs.set(i, mirror.android.content.PeriodicSync.clone(toUpdate));
                        alreadyPresent = true;
                        break;
                    }
                    if (!alreadyPresent) {
                        authority.periodicSyncs.add(mirror.android.content.PeriodicSync.clone(toUpdate));
                        SyncStatusInfo status = this.getOrCreateSyncStatusLocked(authority.ident);
                        status.setPeriodicSyncTime(authority.periodicSyncs.size() - 1, 0L);
                    }
                    break block16;
                }
                SyncStatusInfo status = (SyncStatusInfo)this.mSyncStatus.get(authority.ident);
                boolean changed = false;
                Iterator<PeriodicSync> iterator = authority.periodicSyncs.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    PeriodicSync syncInfo = iterator.next();
                    if (mirror.android.content.PeriodicSync.syncExtrasEquals(syncInfo.extras, toUpdate.extras)) {
                        iterator.remove();
                        changed = true;
                        if (status != null) {
                            status.removePeriodicSyncTime(i);
                            continue;
                        }
                        Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IRcMCWgVMyhhNDA3Ki4+MW8VHShsJB4bLTo6D2YaPD9qAS8pKQgpJGwzNAFqNCQ0DRgiLGUwBgFpNzA2Phc2J2ojLyh9NzAgKBccPn4zMCVvVjwtIxgcIEsaGj98N1RF")));
                        continue;
                    }
                    ++i;
                }
                if (changed) break block16;
                return;
                finally {
                    this.writeAccountInfoLocked();
                    this.writeStatusLocked();
                }
            }
        }
        this.reportChange(1);
    }

    public void addPeriodicSync(PeriodicSync toAdd, int userId) {
        this.updateOrRemovePeriodicSync(toAdd, userId, true);
    }

    public void removePeriodicSync(PeriodicSync toRemove, int userId) {
        this.updateOrRemovePeriodicSync(toRemove, userId, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<PeriodicSync> getPeriodicSyncs(Account account, int userId, String providerName) {
        ArrayList<PeriodicSync> syncs = new ArrayList<PeriodicSync>();
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getAuthorityLocked(account, userId, providerName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFNARjDh4wKQcqAWgjMClsJ1RF")));
            if (authority != null) {
                for (PeriodicSync item : authority.periodicSyncs) {
                    syncs.add(mirror.android.content.PeriodicSync.clone(item));
                }
            }
        }
        return syncs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMasterSyncAutomatically(boolean flag, int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            Boolean auto = (Boolean)this.mMasterSyncAutomatically.get(userId);
            if (auto != null && auto == flag) {
                return;
            }
            this.mMasterSyncAutomatically.put(userId, (Object)flag);
            this.writeAccountInfoLocked();
        }
        if (flag) {
            this.requestSync(null, userId, -7, null, new Bundle());
        }
        this.reportChange(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean getMasterSyncAutomatically(int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            Boolean auto = (Boolean)this.mMasterSyncAutomatically.get(userId);
            return auto == null ? this.mDefaultMasterSyncAutomatically : auto;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeAuthority(Account account, int userId, String authority) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            this.removeAuthorityLocked(account, userId, authority, true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AuthorityInfo getAuthority(int authorityId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            return (AuthorityInfo)this.mAuthorities.get(authorityId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isSyncActive(Account account, int userId, String authority) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            for (VSyncInfo syncInfo : this.getCurrentSyncs(userId)) {
                AuthorityInfo ainfo = this.getAuthority(syncInfo.authorityId);
                if (ainfo == null || !ainfo.account.equals((Object)account) || !ainfo.authority.equals(authority) || ainfo.userId != userId) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PendingOperation insertIntoPending(PendingOperation op) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getOrCreateAuthorityLocked(op.account, op.userId, op.authority, -1, true);
            if (authority == null) {
                return null;
            }
            op = new PendingOperation(op);
            op.authorityId = authority.ident;
            this.mPendingOperations.add(op);
            this.appendPendingOperationLocked(op);
            SyncStatusInfo status = this.getOrCreateSyncStatusLocked(authority.ident);
            status.pending = true;
        }
        this.reportChange(2);
        return op;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean deleteFromPending(PendingOperation op) {
        boolean res = false;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            if (this.mPendingOperations.remove(op)) {
                if (this.mPendingOperations.size() == 0 || this.mNumPendingFinished >= 4) {
                    this.writePendingOperationsLocked();
                    this.mNumPendingFinished = 0;
                } else {
                    ++this.mNumPendingFinished;
                }
                AuthorityInfo authority = this.getAuthorityLocked(op.account, op.userId, op.authority, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguDmgaMCtqNyw1KgYmPW8VBi9lNyBF")));
                if (authority != null) {
                    int N = this.mPendingOperations.size();
                    boolean morePending = false;
                    for (int i = 0; i < N; ++i) {
                        PendingOperation cur = this.mPendingOperations.get(i);
                        if (!cur.account.equals((Object)op.account) || !cur.authority.equals(op.authority) || cur.userId != op.userId) continue;
                        morePending = true;
                        break;
                    }
                    if (!morePending) {
                        SyncStatusInfo status = this.getOrCreateSyncStatusLocked(authority.ident);
                        status.pending = false;
                    }
                }
                res = true;
            }
        }
        this.reportChange(2);
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ArrayList<PendingOperation> getPendingOperations() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            return new ArrayList<PendingOperation>(this.mPendingOperations);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getPendingOperationCount() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            return this.mPendingOperations.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doDatabaseCleanup(Account[] accounts, int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            SparseArray removing = new SparseArray();
            Iterator<AccountInfo> accIt = this.mAccounts.values().iterator();
            while (accIt.hasNext()) {
                AccountInfo acc = accIt.next();
                if (ArrayUtils.contains(accounts, acc.accountAndUser.account) || acc.accountAndUser.userId != userId) continue;
                for (AuthorityInfo auth : acc.authorities.values()) {
                    removing.put(auth.ident, (Object)auth);
                }
                accIt.remove();
            }
            int i = removing.size();
            if (i > 0) {
                while (i > 0) {
                    int ident = removing.keyAt(--i);
                    this.mAuthorities.remove(ident);
                    int j = this.mSyncStatus.size();
                    while (j > 0) {
                        if (this.mSyncStatus.keyAt(--j) != ident) continue;
                        this.mSyncStatus.remove(this.mSyncStatus.keyAt(j));
                    }
                    j = this.mSyncHistory.size();
                    while (j > 0) {
                        if (this.mSyncHistory.get((int)(--j)).authorityId != ident) continue;
                        this.mSyncHistory.remove(j);
                    }
                }
                this.writeAccountInfoLocked();
                this.writeStatusLocked();
                this.writePendingOperationsLocked();
                this.writeStatisticsLocked();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public VSyncInfo addActiveSync(SyncManager.ActiveSyncContext activeSyncContext) {
        VSyncInfo syncInfo;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getOrCreateAuthorityLocked(activeSyncContext.mSyncOperation.account, activeSyncContext.mSyncOperation.userId, activeSyncContext.mSyncOperation.authority, -1, true);
            syncInfo = new VSyncInfo(authority.ident, authority.account, authority.authority, activeSyncContext.mStartTime);
            this.getCurrentSyncs(authority.userId).add(syncInfo);
        }
        this.reportActiveChange();
        return syncInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeActiveSync(VSyncInfo syncInfo, int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            this.getCurrentSyncs(userId).remove(syncInfo);
        }
        this.reportActiveChange();
    }

    public void reportActiveChange() {
        this.reportChange(4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insertStartSyncEvent(Account accountName, int userId, int reason, String authorityName, long now, int source, boolean initialization, Bundle extras) {
        long id2;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authority = this.getAuthorityLocked(accountName, userId, authorityName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKWgaFgZpJwo7Iz42AWgjMCljDiQgLC0qVg==")));
            if (authority == null) {
                return -1L;
            }
            SyncHistoryItem item = new SyncHistoryItem();
            item.initialization = initialization;
            item.authorityId = authority.ident;
            item.historyId = this.mNextHistoryId++;
            if (this.mNextHistoryId < 0) {
                this.mNextHistoryId = 0;
            }
            item.eventTime = now;
            item.source = source;
            item.reason = reason;
            item.extras = extras;
            item.event = 0;
            this.mSyncHistory.add(0, item);
            while (this.mSyncHistory.size() > 100) {
                this.mSyncHistory.remove(this.mSyncHistory.size() - 1);
            }
            id2 = item.historyId;
        }
        this.reportChange(8);
        return id2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stopSyncEvent(long historyId, long elapsedTime, String resultMessage, long downstreamActivity, long upstreamActivity) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            SyncHistoryItem item = null;
            int i = this.mSyncHistory.size();
            while (i > 0) {
                item = this.mSyncHistory.get(--i);
                if ((long)item.historyId == historyId) break;
                item = null;
            }
            if (item == null) {
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qD28ILD9gNCgVLD0MDmU0IyhlNxkrIwgYD2YaAjVrCiAvKQdeJGoFMzQ=")) + historyId));
                return;
            }
            item.elapsedTime = elapsedTime;
            item.event = 1;
            item.mesg = resultMessage;
            item.downstreamActivity = downstreamActivity;
            item.upstreamActivity = upstreamActivity;
            SyncStatusInfo status = this.getOrCreateSyncStatusLocked(item.authorityId);
            ++status.numSyncs;
            status.totalElapsedTime += elapsedTime;
            switch (item.source) {
                case 1: {
                    ++status.numSourceLocal;
                    break;
                }
                case 2: {
                    ++status.numSourcePoll;
                    break;
                }
                case 3: {
                    ++status.numSourceUser;
                    break;
                }
                case 0: {
                    ++status.numSourceServer;
                    break;
                }
                case 4: {
                    ++status.numSourcePeriodic;
                }
            }
            boolean writeStatisticsNow = false;
            int day = this.getCurrentDayLocked();
            if (this.mDayStats[0] == null) {
                this.mDayStats[0] = new DayStats(day);
            } else if (day != this.mDayStats[0].day) {
                System.arraycopy(this.mDayStats, 0, this.mDayStats, 1, this.mDayStats.length - 1);
                this.mDayStats[0] = new DayStats(day);
                writeStatisticsNow = true;
            } else if (this.mDayStats[0] == null) {
                // empty if block
            }
            DayStats ds = this.mDayStats[0];
            long lastSyncTime = item.eventTime + elapsedTime;
            boolean writeStatusNow = false;
            if (MESG_SUCCESS.equals(resultMessage)) {
                if (status.lastSuccessTime == 0L || status.lastFailureTime != 0L) {
                    writeStatusNow = true;
                }
                status.lastSuccessTime = lastSyncTime;
                status.lastSuccessSource = item.source;
                status.lastFailureTime = 0L;
                status.lastFailureSource = -1;
                status.lastFailureMesg = null;
                status.initialFailureTime = 0L;
                ++ds.successCount;
                ds.successTime += elapsedTime;
            } else if (!MESG_CANCELED.equals(resultMessage)) {
                if (status.lastFailureTime == 0L) {
                    writeStatusNow = true;
                }
                status.lastFailureTime = lastSyncTime;
                status.lastFailureSource = item.source;
                status.lastFailureMesg = resultMessage;
                if (status.initialFailureTime == 0L) {
                    status.initialFailureTime = lastSyncTime;
                }
                ++ds.failureCount;
                ds.failureTime += elapsedTime;
            }
            if (writeStatusNow) {
                this.writeStatusLocked();
            } else if (!this.hasMessages(1)) {
                this.sendMessageDelayed(this.obtainMessage(1), 600000L);
            }
            if (writeStatisticsNow) {
                this.writeStatisticsLocked();
            } else if (!this.hasMessages(2)) {
                this.sendMessageDelayed(this.obtainMessage(2), 1800000L);
            }
        }
        this.reportChange(8);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<VSyncInfo> getCurrentSyncs(int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            return this.getCurrentSyncsLocked(userId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<VSyncInfo> getCurrentSyncsCopy(int userId) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            List<VSyncInfo> syncs = this.getCurrentSyncsLocked(userId);
            ArrayList<VSyncInfo> syncsCopy = new ArrayList<VSyncInfo>();
            for (VSyncInfo sync : syncs) {
                syncsCopy.add(new VSyncInfo(sync));
            }
            return syncsCopy;
        }
    }

    private List<VSyncInfo> getCurrentSyncsLocked(int userId) {
        ArrayList syncs = (ArrayList)this.mCurrentSyncs.get(userId);
        if (syncs == null) {
            syncs = new ArrayList();
            this.mCurrentSyncs.put(userId, syncs);
        }
        return syncs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ArrayList<SyncStatusInfo> getSyncStatus() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            int N = this.mSyncStatus.size();
            ArrayList<SyncStatusInfo> ops = new ArrayList<SyncStatusInfo>(N);
            for (int i = 0; i < N; ++i) {
                ops.add((SyncStatusInfo)this.mSyncStatus.valueAt(i));
            }
            return ops;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pair<AuthorityInfo, SyncStatusInfo> getCopyOfAuthorityWithSyncStatus(Account account, int userId, String authority) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            AuthorityInfo authorityInfo = this.getOrCreateAuthorityLocked(account, userId, authority, -1, true);
            return this.createCopyPairOfAuthorityWithSyncStatusLocked(authorityInfo);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ArrayList<Pair<AuthorityInfo, SyncStatusInfo>> getCopyOfAllAuthoritiesWithSyncStatus() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            ArrayList<Pair<AuthorityInfo, SyncStatusInfo>> infos = new ArrayList<Pair<AuthorityInfo, SyncStatusInfo>>(this.mAuthorities.size());
            for (int i = 0; i < this.mAuthorities.size(); ++i) {
                infos.add(this.createCopyPairOfAuthorityWithSyncStatusLocked((AuthorityInfo)this.mAuthorities.valueAt(i)));
            }
            return infos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SyncStatusInfo getStatusByAccountAndAuthority(Account account, int userId, String authority) {
        if (account == null || authority == null) {
            return null;
        }
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            int N = this.mSyncStatus.size();
            for (int i = 0; i < N; ++i) {
                SyncStatusInfo cur = (SyncStatusInfo)this.mSyncStatus.valueAt(i);
                AuthorityInfo ainfo = (AuthorityInfo)this.mAuthorities.get(cur.authorityId);
                if (ainfo == null || !ainfo.authority.equals(authority) || ainfo.userId != userId || !account.equals((Object)ainfo.account)) continue;
                return cur;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isSyncPending(Account account, int userId, String authority) {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            int N = this.mSyncStatus.size();
            for (int i = 0; i < N; ++i) {
                SyncStatusInfo cur = (SyncStatusInfo)this.mSyncStatus.valueAt(i);
                AuthorityInfo ainfo = (AuthorityInfo)this.mAuthorities.get(cur.authorityId);
                if (ainfo == null || userId != ainfo.userId || account != null && !ainfo.account.equals((Object)account) || !ainfo.authority.equals(authority) || !cur.pending) continue;
                return true;
            }
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ArrayList<SyncHistoryItem> getSyncHistory() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            int N = this.mSyncHistory.size();
            ArrayList<SyncHistoryItem> items = new ArrayList<SyncHistoryItem>(N);
            for (int i = 0; i < N; ++i) {
                items.add(this.mSyncHistory.get(i));
            }
            return items;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DayStats[] getDayStatistics() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            DayStats[] ds = new DayStats[this.mDayStats.length];
            System.arraycopy(this.mDayStats, 0, ds, 0, ds.length);
            return ds;
        }
    }

    private Pair<AuthorityInfo, SyncStatusInfo> createCopyPairOfAuthorityWithSyncStatusLocked(AuthorityInfo authorityInfo) {
        SyncStatusInfo syncStatusInfo = this.getOrCreateSyncStatusLocked(authorityInfo.ident);
        return Pair.create((Object)new AuthorityInfo(authorityInfo), (Object)new SyncStatusInfo(syncStatusInfo));
    }

    private int getCurrentDayLocked() {
        this.mCal.setTimeInMillis(System.currentTimeMillis());
        int dayOfYear = this.mCal.get(6);
        if (this.mYear != this.mCal.get(1)) {
            this.mYear = this.mCal.get(1);
            this.mCal.clear();
            this.mCal.set(1, this.mYear);
            this.mYearInDays = (int)(this.mCal.getTimeInMillis() / 86400000L);
        }
        return dayOfYear + this.mYearInDays;
    }

    private AuthorityInfo getAuthorityLocked(Account accountName, int userId, String authorityName, String tag) {
        AccountAndUser au = new AccountAndUser(accountName, userId);
        AccountInfo accountInfo = this.mAccounts.get(au);
        if (accountInfo == null) {
            if (tag != null) {
                // empty if block
            }
            return null;
        }
        AuthorityInfo authority = accountInfo.authorities.get(authorityName);
        if (authority == null) {
            if (tag != null) {
                // empty if block
            }
            return null;
        }
        return authority;
    }

    private AuthorityInfo getAuthorityLocked(ComponentName service, int userId, String tag) {
        AuthorityInfo authority = (AuthorityInfo)this.mServices.get(service).get(userId);
        if (authority == null) {
            if (tag != null) {
                // empty if block
            }
            return null;
        }
        return authority;
    }

    private AuthorityInfo getOrCreateAuthorityLocked(ComponentName cname, int userId, int ident, boolean doWrite) {
        AuthorityInfo authority;
        SparseArray aInfo = this.mServices.get(cname);
        if (aInfo == null) {
            aInfo = new SparseArray();
            this.mServices.put(cname, (SparseArray<AuthorityInfo>)aInfo);
        }
        if ((authority = (AuthorityInfo)aInfo.get(userId)) == null) {
            if (ident < 0) {
                ident = this.mNextAuthorityId++;
                doWrite = true;
            }
            authority = new AuthorityInfo(cname, userId, ident);
            aInfo.put(userId, (Object)authority);
            this.mAuthorities.put(ident, (Object)authority);
            if (doWrite) {
                this.writeAccountInfoLocked();
            }
        }
        return authority;
    }

    private AuthorityInfo getOrCreateAuthorityLocked(Account accountName, int userId, String authorityName, int ident, boolean doWrite) {
        AuthorityInfo authority;
        AccountAndUser au = new AccountAndUser(accountName, userId);
        AccountInfo account = this.mAccounts.get(au);
        if (account == null) {
            account = new AccountInfo(au);
            this.mAccounts.put(au, account);
        }
        if ((authority = account.authorities.get(authorityName)) == null) {
            if (ident < 0) {
                ident = this.mNextAuthorityId++;
                doWrite = true;
            }
            authority = new AuthorityInfo(accountName, userId, authorityName, ident);
            account.authorities.put(authorityName, authority);
            this.mAuthorities.put(ident, (Object)authority);
            if (doWrite) {
                this.writeAccountInfoLocked();
            }
        }
        return authority;
    }

    private void removeAuthorityLocked(Account account, int userId, String authorityName, boolean doWrite) {
        AuthorityInfo authorityInfo;
        AccountInfo accountInfo = this.mAccounts.get(new AccountAndUser(account, userId));
        if (accountInfo != null && (authorityInfo = accountInfo.authorities.remove(authorityName)) != null) {
            this.mAuthorities.remove(authorityInfo.ident);
            if (doWrite) {
                this.writeAccountInfoLocked();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setPeriodicSyncTime(int authorityId, PeriodicSync targetPeriodicSync, long when) {
        AuthorityInfo authorityInfo;
        boolean found = false;
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            authorityInfo = (AuthorityInfo)this.mAuthorities.get(authorityId);
            for (int i = 0; i < authorityInfo.periodicSyncs.size(); ++i) {
                PeriodicSync periodicSync = authorityInfo.periodicSyncs.get(i);
                if (!targetPeriodicSync.equals((Object)periodicSync)) continue;
                ((SyncStatusInfo)this.mSyncStatus.get(authorityId)).setPeriodicSyncTime(i, when);
                found = true;
                break;
            }
        }
        if (!found) {
            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgmCGowFi9gNDs8Iy0MLmIzGgRqAQYvIxg2UmcKRSZiHhocLypXCGsKJC9oHjAZDRc6JmU0PDV+NB4gIz41OGwFRTdmVyQwKi0ML34zMCVvVjwgLwgYD2ZTRCNhAQo9Kj4uCGoKMCt5IzxF")) + authorityInfo.authority));
        }
    }

    private SyncStatusInfo getOrCreateSyncStatusLocked(int authorityId) {
        SyncStatusInfo status = (SyncStatusInfo)this.mSyncStatus.get(authorityId);
        if (status == null) {
            status = new SyncStatusInfo(authorityId);
            this.mSyncStatus.put(authorityId, (Object)status);
        }
        return status;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void writeAllState() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            if (this.mNumPendingFinished > 0) {
                this.writePendingOperationsLocked();
            }
            this.writeStatusLocked();
            this.writeStatisticsLocked();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearAndReadState() {
        SparseArray<AuthorityInfo> sparseArray = this.mAuthorities;
        synchronized (sparseArray) {
            this.mAuthorities.clear();
            this.mAccounts.clear();
            this.mServices.clear();
            this.mPendingOperations.clear();
            this.mSyncStatus.clear();
            this.mSyncHistory.clear();
            this.readAccountInfoLocked();
            this.readStatusLocked();
            this.readPendingOperationsLocked();
            this.readStatisticsLocked();
            this.readAndDeleteLegacyAccountInfoLocked();
            this.writeAccountInfoLocked();
            this.writeStatusLocked();
            this.writePendingOperationsLocked();
            this.writeStatisticsLocked();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readAccountInfoLocked() {
        block30: {
            int highestAuthorityId = -1;
            FileInputStream fis = null;
            try {
                int version;
                fis = this.mAccountInfoFile.openRead();
                Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uP2gFAiZiICRF")) + this.mAccountInfoFile.getBaseFile()));
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput((InputStream)fis, null);
                int eventType = parser.getEventType();
                while (eventType != 2) {
                    eventType = parser.next();
                }
                String tagName = parser.getName();
                if (!StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")).equals(tagName)) break block30;
                String listen = parser.getAttributeValue(null, XML_ATTR_LISTEN_FOR_TICKLES);
                String versionString = parser.getAttributeValue(null, XML_ATTR_VERSION);
                try {
                    version = versionString == null ? 0 : Integer.parseInt(versionString);
                }
                catch (NumberFormatException e) {
                    version = 0;
                }
                String nextIdString = parser.getAttributeValue(null, XML_ATTR_NEXT_AUTHORITY_ID);
                try {
                    int id2 = nextIdString == null ? 0 : Integer.parseInt(nextIdString);
                    this.mNextAuthorityId = Math.max(this.mNextAuthorityId, id2);
                }
                catch (NumberFormatException id2) {
                    // empty catch block
                }
                String offsetString = parser.getAttributeValue(null, XML_ATTR_SYNC_RANDOM_OFFSET);
                try {
                    this.mSyncRandomOffset = offsetString == null ? 0 : Integer.parseInt(offsetString);
                }
                catch (NumberFormatException e) {
                    this.mSyncRandomOffset = 0;
                }
                if (this.mSyncRandomOffset == 0) {
                    Random random = new Random(System.currentTimeMillis());
                    this.mSyncRandomOffset = random.nextInt(86400);
                }
                this.mMasterSyncAutomatically.put(0, (Object)(listen == null || Boolean.parseBoolean(listen) ? 1 : 0));
                eventType = parser.next();
                AuthorityInfo authority = null;
                PeriodicSync periodicSync = null;
                do {
                    if (eventType != 2) continue;
                    tagName = parser.getName();
                    if (parser.getDepth() == 2) {
                        if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")).equals(tagName)) {
                            authority = this.parseAuthority(parser, version);
                            periodicSync = null;
                            if (authority.ident <= highestAuthorityId) continue;
                            highestAuthorityId = authority.ident;
                            continue;
                        }
                        if (!XML_TAG_LISTEN_FOR_TICKLES.equals(tagName)) continue;
                        this.parseListenForTickles(parser);
                        continue;
                    }
                    if (parser.getDepth() == 3) {
                        if (!StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGixjDigPLQcYPw==")).equals(tagName) || authority == null) continue;
                        periodicSync = this.parsePeriodicSync(parser, authority);
                        continue;
                    }
                    if (parser.getDepth() != 4 || periodicSync == null || !StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfLG8jJFo=")).equals(tagName)) continue;
                    this.parseExtra(parser, periodicSync.extras);
                } while ((eventType = parser.next()) != 1);
            }
            catch (XmlPullParserException e) {
                Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShhNDA7KBccDmkJTTdoJzAcKhgcCmEjSFo=")), (Throwable)e);
                return;
            }
            catch (IOException e) {
                if (fis == null) {
                    Log.i((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGUVBi9mHgY7Kl4mOW4FAiVvARo/KT5SVg==")));
                } else {
                    Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShhNDA7KBccDmkJTTdoJzAcKhgcCmEjSFo=")), (Throwable)e);
                }
                return;
            }
            finally {
                this.mNextAuthorityId = Math.max(highestAuthorityId + 1, this.mNextAuthorityId);
                if (fis != null) {
                    try {
                        fis.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        this.maybeMigrateSettingsForRenamedAuthorities();
    }

    private void maybeDeleteLegacyPendingInfoLocked(File syncDir) {
        File file = new File(syncDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiIFk6KQcYVg==")));
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    private boolean maybeMigrateSettingsForRenamedAuthorities() {
        boolean writeNeeded = false;
        ArrayList<AuthorityInfo> authoritiesToRemove = new ArrayList<AuthorityInfo>();
        int N = this.mAuthorities.size();
        for (int i = 0; i < N; ++i) {
            AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.valueAt(i);
            String newAuthorityName = sAuthorityRenames.get(authority.authority);
            if (newAuthorityName == null) continue;
            authoritiesToRemove.add(authority);
            if (!authority.enabled || this.getAuthorityLocked(authority.account, authority.userId, newAuthorityName, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EM2sVBgVhEVRF"))) != null) continue;
            AuthorityInfo newAuthority = this.getOrCreateAuthorityLocked(authority.account, authority.userId, newAuthorityName, -1, false);
            newAuthority.enabled = true;
            writeNeeded = true;
        }
        for (AuthorityInfo authorityInfo : authoritiesToRemove) {
            this.removeAuthorityLocked(authorityInfo.account, authorityInfo.userId, authorityInfo.authority, false);
            writeNeeded = true;
        }
        return writeNeeded;
    }

    private void parseListenForTickles(XmlPullParser parser) {
        String user = parser.getAttributeValue(null, XML_ATTR_USER);
        int userId = 0;
        try {
            userId = Integer.parseInt(user);
        }
        catch (NumberFormatException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowEShhHiAqIy0cDmkJTQZqESsrKhc2J2E0OCFsJyspKT42D28VNAR6ESQ6JzlbM28KMAVvNwYc")), (Throwable)e);
        }
        catch (NullPointerException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRhfM3sKNANiAS88KQcXOm8zLANvESgbPBgiKWE0QT9vDiwaKT4AD3gVAiV7ARoaJhdXVg==")), (Throwable)e);
        }
        String enabled = parser.getAttributeValue(null, XML_ATTR_ENABLED);
        boolean listen = enabled == null || Boolean.parseBoolean(enabled);
        this.mMasterSyncAutomatically.put(userId, (Object)listen);
    }

    private AuthorityInfo parseAuthority(XmlPullParser parser, int version) {
        AuthorityInfo authority = null;
        int id2 = -1;
        try {
            id2 = Integer.parseInt(parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgqVg=="))));
        }
        catch (NumberFormatException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowEShhHiAqIy0cDmkJTQZqESsrIxgpJGAgIyNqHgYuOD5bCW8VRQNvJx4ZIQhSVg==")), (Throwable)e);
        }
        catch (NullPointerException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRhfM3sFAixLHh4+Pxg2MmknTTdvDiwZLD0MI2YVBSNvAS8pKRcAKGUVSFo=")), (Throwable)e);
        }
        if (id2 >= 0) {
            int userId;
            String authorityName = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")));
            String enabled = parser.getAttributeValue(null, XML_ATTR_ENABLED);
            String syncable = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGszJCpgHjBF")));
            String accountName = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")));
            String accountType = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")));
            String user = parser.getAttributeValue(null, XML_ATTR_USER);
            String packageName = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")));
            String className = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EP28wLFo=")));
            int n = userId = user == null ? 0 : Integer.parseInt(user);
            if (accountType == null) {
                accountType = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojPCVgJDgoKAhSVg=="));
                syncable = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcMWojGj1gN1RF"));
            }
            authority = (AuthorityInfo)this.mAuthorities.get(id2);
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqPGUVBi1LHiAvLBcADWoVLAZuClArLRg2JWAjLClqVlFF")) + accountName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg+I2wFRDM=")) + authorityName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcuKWgaETM=")) + userId + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhguCGsVFiRiDg0d")) + enabled + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2J2ojLDd9NFE/PghSVg==")) + syncable));
            if (authority == null) {
                Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji0MM2saMC9gNDs8KAcYLmoaLFo=")));
                authority = accountName != null && accountType != null ? this.getOrCreateAuthorityLocked(new Account(accountName, accountType), userId, authorityName, id2, false) : this.getOrCreateAuthorityLocked(new ComponentName(packageName, className), userId, id2, false);
                if (version > 0) {
                    authority.periodicSyncs.clear();
                }
            }
            if (authority != null) {
                boolean bl = authority.enabled = enabled == null || Boolean.parseBoolean(enabled);
                authority.syncable = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcMWojGj1gN1RF")).equals(syncable) ? -1 : (syncable == null || Boolean.parseBoolean(syncable) ? 1 : 0);
            } else {
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JT4+CWoKNARiCiQ7KBc2MW8VHShoDig/IwgACGMFFgZ7MCAqKAgYKW8FBiB+EVRF")) + accountName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg+I2wFRDM=")) + authorityName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhguCGsVFiRiDg0d")) + enabled + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2J2ojLDd9NFE/PghSVg==")) + syncable));
            }
        }
        return authority;
    }

    private PeriodicSync parsePeriodicSync(XmlPullParser parser, AuthorityInfo authority) {
        long flextime;
        long period;
        Bundle extras = new Bundle();
        String periodValue = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGiw=")));
        String flexValue = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4EM2kFSFo=")));
        try {
            period = Long.parseLong(periodValue);
        }
        catch (NumberFormatException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowEShhHiAqIy0cDmkJTQZqESsrKQguCGMKAi95HlkvOD5aJGwVNCZlEQYzJQctL2UjBgJuAVRF")), (Throwable)e);
            return null;
        }
        catch (NullPointerException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRhfM3sKICthNAY1KF4mDWlSTTd4HjwgKS4YKWIaGiZ5ESwyKRgXJGoKLzRqJCg7JhhSVg==")), (Throwable)e);
            return null;
        }
        try {
            flextime = Long.parseLong(flexValue);
        }
        catch (NumberFormatException e) {
            Log.e((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShiNB4qKgciLmUzLCZrIzw9LRgECWIOODNpASg8Ly4bJGszGiZ7Djw0Jz0AJm4aBjN+NB4gIz41OGgjHitnVgU8")) + flexValue));
            flextime = SyncStorageEngine.calculateDefaultFlexTime(period);
        }
        catch (NullPointerException expected) {
            flextime = SyncStorageEngine.calculateDefaultFlexTime(period);
            Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGgjHitnVyQgKQdXPX4wAgJrATAaLi4YJ2JTOCFsJyspIz4MI2wnICVpERo2CF4iPGUgBgJpDVEoPhgqM2gjJAVgEQ02PxgmPWoVLCVrV1Ar")) + period + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgiDmgaRDJLEVRF")) + flextime));
        }
        PeriodicSync periodicSync = new PeriodicSync(authority.account, authority.authority, extras, period);
        mirror.android.content.PeriodicSync.flexTime.set(periodicSync, flextime);
        authority.periodicSyncs.add(periodicSync);
        return periodicSync;
    }

    private void parseExtra(XmlPullParser parser, Bundle extras) {
        String name = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+DWgVSFo=")));
        String type = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")));
        String value1 = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")));
        String value2 = parser.getAttributeValue(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwQ=")));
        try {
            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgACGgzSFo=")).equals(type)) {
                extras.putLong(name, Long.parseLong(value1));
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVPCthN1RF")).equals(type)) {
                extras.putInt(name, Integer.parseInt(value1));
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgAI2sjHis=")).equals(type)) {
                extras.putDouble(name, Double.parseDouble(value1));
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4ED2saMFo=")).equals(type)) {
                extras.putFloat(name, Float.parseFloat(value1));
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgN1RF")).equals(type)) {
                extras.putBoolean(name, Boolean.parseBoolean(value1));
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qKmUVBi0=")).equals(type)) {
                extras.putString(name, value1);
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")).equals(type)) {
                extras.putParcelable(name, (Parcelable)new Account(value1, value2));
            }
        }
        catch (NumberFormatException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowEShhHiAqIy0cDmkJTSpvARovLAgtJGYwPDdqDgpF")), (Throwable)e);
        }
        catch (NullPointerException e) {
            Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowEShhHiAqIy0cDmkJTSpvARovLAgtJGYwPDdqDgpF")), (Throwable)e);
        }
    }

    private void writeAccountInfoLocked() {
        block9: {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MCWwFAiZiICQ2KAg5Og==")) + this.mAccountInfoFile.getBaseFile()));
            FileOutputStream fos = null;
            try {
                fos = this.mAccountInfoFile.startWrite();
                FastXmlSerializer out = new FastXmlSerializer();
                out.setOutput(fos, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQcqPnpTRVo=")));
                out.startDocument(null, true);
                out.setFeature(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBcqLG8OTCVOJxo3KhgmLW8zOyZlJAouPD0hDU4gFippIFkvLy5bCm8KFj9vMxo/IBdbO3kgBgJpNwY5KV8ID2waMAJmAQpF")), true);
                out.startTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")));
                out.attribute(null, XML_ATTR_VERSION, Integer.toString(2));
                out.attribute(null, XML_ATTR_NEXT_AUTHORITY_ID, Integer.toString(this.mNextAuthorityId));
                out.attribute(null, XML_ATTR_SYNC_RANDOM_OFFSET, Integer.toString(this.mSyncRandomOffset));
                int M = this.mMasterSyncAutomatically.size();
                for (int m = 0; m < M; ++m) {
                    int userId = this.mMasterSyncAutomatically.keyAt(m);
                    Boolean listen = (Boolean)this.mMasterSyncAutomatically.valueAt(m);
                    out.startTag(null, XML_TAG_LISTEN_FOR_TICKLES);
                    out.attribute(null, XML_ATTR_USER, Integer.toString(userId));
                    out.attribute(null, XML_ATTR_ENABLED, Boolean.toString(listen));
                    out.endTag(null, XML_TAG_LISTEN_FOR_TICKLES);
                }
                int N = this.mAuthorities.size();
                for (int i = 0; i < N; ++i) {
                    AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.valueAt(i);
                    out.startTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")));
                    out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgqVg==")), Integer.toString(authority.ident));
                    out.attribute(null, XML_ATTR_USER, Integer.toString(authority.userId));
                    out.attribute(null, XML_ATTR_ENABLED, Boolean.toString(authority.enabled));
                    if (authority.service == null) {
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")), authority.account.name);
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), authority.account.type);
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")), authority.authority);
                    } else {
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")), authority.service.getPackageName());
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4EP28wLFo=")), authority.service.getClassName());
                    }
                    if (authority.syncable < 0) {
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGszJCpgHjBF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQgcMWojGj1gN1RF")));
                    } else {
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGszJCpgHjBF")), Boolean.toString(authority.syncable != 0));
                    }
                    for (PeriodicSync periodicSync : authority.periodicSyncs) {
                        out.startTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGixjDigPLQcYPw==")));
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGiw=")), Long.toString(periodicSync.period));
                        long flexTime = mirror.android.content.PeriodicSync.flexTime.get(periodicSync);
                        out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4EM2kFSFo=")), Long.toString(flexTime));
                        Bundle extras = periodicSync.extras;
                        this.extrasToXml(out, extras);
                        out.endTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguKmUVGixjDigPLQcYPw==")));
                    }
                    out.endTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")));
                }
                out.endTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")));
                out.endDocument();
                this.mAccountInfoFile.finishWrite(fos);
            }
            catch (IOException e1) {
                Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTTdoJzAcKhgcCmEjSFo=")), (Throwable)e1);
                if (fos == null) break block9;
                this.mAccountInfoFile.failWrite(fos);
            }
        }
    }

    static int getIntColumn(Cursor c, String name) {
        return c.getInt(c.getColumnIndex(name));
    }

    static long getLongColumn(Cursor c, String name) {
        return c.getLong(c.getColumnIndex(name));
    }

    private void readAndDeleteLegacyAccountInfoLocked() {
        File file = this.mContext.getDatabasePath(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGszEjdgNCA9KAgtDmkzRVo=")));
        if (!file.exists()) {
            return;
        }
        String path = file.getPath();
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase((String)path, null, (int)1);
        }
        catch (SQLiteException sQLiteException) {
            // empty catch block
        }
        if (db != null) {
            boolean hasType = db.getVersion() >= 11;
            Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uP2gFAiZiICQoKAc6OW4KLyhsJB4bLTo6O30gNCpqDh49LARXIGgzSFo=")));
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyRLESggLwg2LWoFSFo=")));
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy4YPA==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKNANONR4zKF4mOWoJTR9qASxF")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyZ9Dig5Ki4MDmU3TTdsIzwsLT42KWYKRT8=")));
            if (hasType) {
                map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHx4gLQgmPQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyZ9Dig5Ki4MDmU2NAZuDjwgPQg+D0saPCZpJFk+KRccE28aAiRoEVRF")));
            }
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyZ9ATAgKRdfKGwgBj94ETg6PQg+CWYaBiplNBo9Li5SVg==")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgALGsVHhVgHiAsIy0MPn0zLCNrAVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgALGsVHhVgHiAsIy0MPn0zLCNrAVRF")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWcwAiZ9JyhF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWcwAiZ9JyhF")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/IhdfP24jOFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/IhdfP24jOFo=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/OxdfCG8zSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/OxdfCG8zSFo=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/Oy0MKGUVGgQ=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/Oy0MKGUVGgQ=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/JAgqPWoVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/JAgqPWoVSFo=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAW8KGgRoJyhF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAW8KGgRoJyhF")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAGwjPCs=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAGwjPCs=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAW8KGgRoJyhF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAW8KGgRoJyhF")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAGwjPCs=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAGwjPCs=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MUmkgAi0=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MUmkgAi0=")));
            map.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiJ1RF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiJ1RF")));
            qb.setProjectionMap(map);
            qb.appendWhere((CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qP2wKLyZsJAYwP19WOmoKBjdvHig6PC02Cn0FFjZgJBot")));
            Cursor c = qb.query(db, null, null, null, null, null, null);
            while (c.moveToNext()) {
                String authorityName;
                AuthorityInfo authority;
                String accountType;
                String accountName = c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF"))));
                String string2 = accountType = hasType ? c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHx4gLQgmPQ==")))) : null;
                if (accountType == null) {
                    accountType = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojPCVgJDgoKAhSVg=="));
                }
                if ((authority = this.getOrCreateAuthorityLocked(new Account(accountName, accountType), 0, authorityName = c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZ")))), -1, false)) == null) continue;
                int i = this.mSyncStatus.size();
                boolean found = false;
                SyncStatusInfo st = null;
                while (i > 0) {
                    st = (SyncStatusInfo)this.mSyncStatus.valueAt(--i);
                    if (st.authorityId != authority.ident) continue;
                    found = true;
                    break;
                }
                if (!found) {
                    st = new SyncStatusInfo(authority.ident);
                    this.mSyncStatus.put(authority.ident, (Object)st);
                }
                st.totalElapsedTime = SyncStorageEngine.getLongColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRgALGsVHhVgHiAsIy0MPn0zLCNrAVRF")));
                st.numSyncs = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWcwAiZ9JyhF")));
                st.numSourceLocal = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/IhdfP24jOFo=")));
                st.numSourcePoll = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/OxdfCG8zSFo=")));
                st.numSourceServer = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/Oy0MKGUVGgQ=")));
                st.numSourceUser = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDWczGgVhNCg/JAgqPWoVSFo=")));
                st.numSourcePeriodic = 0;
                st.lastSuccessSource = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAW8KGgRoJyhF")));
                st.lastSuccessTime = SyncStorageEngine.getLongColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwILAV9JCg/Iy4qAGwjPCs=")));
                st.lastFailureSource = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAW8KGgRoJyhF")));
                st.lastFailureTime = SyncStorageEngine.getLongColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MAGwjPCs=")));
                st.lastFailureMesg = c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLODdjDlEvIz0MUmkgAi0="))));
                st.pending = SyncStorageEngine.getIntColumn(c, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiJ1RF"))) != 0;
            }
            c.close();
            qb = new SQLiteQueryBuilder();
            qb.setTables(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGwFAiZiJyhF")));
            c = qb.query(db, null, null, null, null, null, null);
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+DWgVSFo="))));
                String value = c.getString(c.getColumnIndex(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNFo="))));
                if (name == null) continue;
                if (name.equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgYKWwFNCZsJDw1IzxfLmwjAiFlESg6")))) {
                    this.setMasterSyncAutomatically(value == null || Boolean.parseBoolean(value), 0);
                    continue;
                }
                if (!name.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGs2GgJhNB4uKQc2PWoYNFo=")))) continue;
                String provider = name.substring(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YCGs2GgJhNB4uKQc2PWoYNFo=")).length(), name.length());
                int i = this.mAuthorities.size();
                while (i > 0) {
                    AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.valueAt(--i);
                    if (!authority.authority.equals(provider)) continue;
                    authority.enabled = value == null || Boolean.parseBoolean(value);
                    authority.syncable = 1;
                }
            }
            c.close();
            db.close();
            new File(path).delete();
        }
    }

    private void readStatusLocked() {
        Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uP2gFAiZiICRF")) + this.mStatusFile.getBaseFile()));
        try {
            int token;
            byte[] data = this.mStatusFile.readFully();
            Parcel in = Parcel.obtain();
            in.unmarshall(data, 0, data.length);
            in.setDataPosition(0);
            while ((token = in.readInt()) != 0) {
                if (token == 100) {
                    SyncStatusInfo status = new SyncStatusInfo(in);
                    if (this.mAuthorities.indexOfKey(status.authorityId) < 0) continue;
                    status.pending = false;
                    Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqPGUVBi1LESggLwg2LWoJTS5lJA0rIxgpJA==")) + status.authorityId));
                    this.mSyncStatus.put(status.authorityId, (Object)status);
                    continue;
                }
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQpLBciLmUgDShvEQYiLhgbPksVSFo=")) + token));
                break;
            }
        }
        catch (IOException e) {
            Log.i((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGUVBi9mHgY7Kl4mL2UzQQZvDjBF")));
        }
    }

    private void writeStatusLocked() {
        block3: {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MCWwFAiZiICQ2KAg5Og==")) + this.mStatusFile.getBaseFile()));
            this.removeMessages(1);
            FileOutputStream fos = null;
            try {
                fos = this.mStatusFile.startWrite();
                Parcel out = Parcel.obtain();
                int N = this.mSyncStatus.size();
                for (int i = 0; i < N; ++i) {
                    SyncStatusInfo status = (SyncStatusInfo)this.mSyncStatus.valueAt(i);
                    out.writeInt(100);
                    status.writeToParcel(out, 0);
                }
                out.writeInt(0);
                fos.write(out.marshall());
                out.recycle();
                this.mStatusFile.finishWrite(fos);
            }
            catch (IOException e1) {
                Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTQNvETg/Khc2Vg==")), (Throwable)e1);
                if (fos == null) break block3;
                this.mStatusFile.failWrite(fos);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readPendingOperationsLocked() {
        FileInputStream fis = null;
        if (!this.mPendingFile.getBaseFile().exists()) {
            Log.v((String)TAG_FILE, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOG8FNCZiHgY2KCkmDWozGgRoDiwaLD4bJGIwGjduCh5F")));
            return;
        }
        try {
            fis = this.mPendingFile.openRead();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput((InputStream)fis, null);
            int eventType = parser.getEventType();
            while (eventType != 2 && eventType != 1) {
                eventType = parser.next();
            }
            if (eventType == 1) {
                return;
            }
            String tagName = parser.getName();
            do {
                PendingOperation pop = null;
                if (eventType != 2) continue;
                try {
                    tagName = parser.getName();
                    if (parser.getDepth() == 1 && StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy06Vg==")).equals(tagName)) {
                        String versionString = parser.getAttributeValue(null, XML_ATTR_VERSION);
                        if (versionString == null || Integer.parseInt(versionString) != 3) {
                            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQsKAcYPmwjMC14EQY7LhcMO2YaGipsMCA/Ly1fD2oFGgR7AVRF")) + versionString));
                            throw new IOException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQuKAguL2wjNCZ1N1RF")));
                        }
                        int authorityId = Integer.valueOf(parser.getAttributeValue(null, XML_ATTR_AUTHORITYID));
                        boolean expedited = Boolean.valueOf(parser.getAttributeValue(null, XML_ATTR_EXPEDITED));
                        int syncSource = Integer.valueOf(parser.getAttributeValue(null, XML_ATTR_SOURCE));
                        int reason = Integer.valueOf(parser.getAttributeValue(null, XML_ATTR_REASON));
                        AuthorityInfo authority = (AuthorityInfo)this.mAuthorities.get(authorityId);
                        Log.v((String)TAG_FILE, (String)(authorityId + " " + expedited + " " + syncSource + " " + reason));
                        if (authority != null) {
                            pop = new PendingOperation(authority.account, authority.userId, reason, syncSource, authority.authority, new Bundle(), expedited);
                            pop.flatExtras = null;
                            this.mPendingOperations.add(pop);
                            Log.v((String)TAG_FILE, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqPGUVBi1LESQ/Kj02MW8VHShlJD8xPQhSVg==")) + pop.authority + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phc2Kms0Elo=")) + pop.syncSource + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhcMM2saLCVgM11F")) + pop.reason + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhguIG8FNCxjAQo/KF9XVg==")) + pop.expedited));
                            continue;
                        }
                        pop = null;
                        Log.v((String)TAG_FILE, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGsaNAZjHh4qKQg2IX4zHiVvARovPQgiKWE0OFo=")) + authorityId + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWUzAgJhHgY2KC5SVg=="))));
                        continue;
                    }
                    if (parser.getDepth() != 2 || pop == null || !StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfLG8jJFo=")).equals(tagName)) continue;
                    this.parseExtra(parser, pop.extras);
                }
                catch (NumberFormatException e) {
                    Log.d((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLmsVHi9iVyQwLwg2OX4zLCZ4HlkeLAQ6ImMKTSB8N1RF")), (Throwable)e);
                }
            } while ((eventType = parser.next()) != 1);
        }
        catch (IOException e) {
            Log.w((String)TAG_FILE, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShhNDA7KBccDmkJTQJrARovIxgcIUsaFiRqHicd")), (Throwable)e);
        }
        catch (XmlPullParserException e) {
            Log.w((String)TAG_FILE, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShhHiAqIy0cDmkJTQJrARovIxgcIUsaAjNlICAxKS4hKg==")), (Throwable)e);
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {}
            }
        }
    }

    private void writePendingOperationsLocked() {
        block4: {
            int N = this.mPendingOperations.size();
            FileOutputStream fos = null;
            try {
                if (N == 0) {
                    Log.v((String)TAG_FILE, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IRcMI2ojLDdmHgY2KCkmVg==")) + this.mPendingFile.getBaseFile()));
                    this.mPendingFile.truncate();
                    return;
                }
                Log.v((String)TAG_FILE, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MCWwFAiZiICQ2KAg5Og==")) + this.mPendingFile.getBaseFile()));
                fos = this.mPendingFile.startWrite();
                FastXmlSerializer out = new FastXmlSerializer();
                out.setOutput(fos, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQcqPnpTRVo=")));
                for (int i = 0; i < N; ++i) {
                    PendingOperation pop = this.mPendingOperations.get(i);
                    this.writePendingOperationLocked(pop, out);
                }
                out.endDocument();
                this.mPendingFile.finishWrite(fos);
            }
            catch (IOException e1) {
                Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTQJrARovIxgcIUsaAjNuASgqIz42KWUwLFo=")), (Throwable)e1);
                if (fos == null) break block4;
                this.mPendingFile.failWrite(fos);
            }
        }
    }

    private void writePendingOperationLocked(PendingOperation pop, XmlSerializer out) throws IOException {
        out.startTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy06Vg==")));
        out.attribute(null, XML_ATTR_VERSION, Integer.toString(3));
        out.attribute(null, XML_ATTR_AUTHORITYID, Integer.toString(pop.authorityId));
        out.attribute(null, XML_ATTR_SOURCE, Integer.toString(pop.syncSource));
        out.attribute(null, XML_ATTR_EXPEDITED, Boolean.toString(pop.expedited));
        out.attribute(null, XML_ATTR_REASON, Integer.toString(pop.reason));
        this.extrasToXml(out, pop.extras);
        out.endTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy06Vg==")));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void appendPendingOperationLocked(PendingOperation op) {
        Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jgc6KGgVBixjDlk9Pxg2DX4zSFo=")) + this.mPendingFile.getBaseFile()));
        FileOutputStream fos = null;
        try {
            fos = this.mPendingFile.openAppend();
        }
        catch (IOException e) {
            Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JT4+CWoFNCxLHiAsIxcMDmk0JyhvJAoaKggYKmIkOCFqDl0bOD4EI2UVNFo=")));
            this.writePendingOperationsLocked();
            return;
        }
        try {
            FastXmlSerializer out = new FastXmlSerializer();
            out.setOutput(fos, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQcqPnpTRVo=")));
            this.writePendingOperationLocked(op, out);
            out.endDocument();
            this.mPendingFile.finishWrite(fos);
        }
        catch (IOException e1) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTTdsHjwgLC4qI2AwJyNsJyAuLBhbCmoFGgQ=")), (Throwable)e1);
            this.mPendingFile.failWrite(fos);
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException iOException) {}
        }
    }

    private static byte[] flattenBundle(Bundle bundle) {
        byte[] flatData = null;
        Parcel parcel = Parcel.obtain();
        try {
            bundle.writeToParcel(parcel, 0);
            flatData = parcel.marshall();
        }
        finally {
            parcel.recycle();
        }
        return flatData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Bundle unflattenBundle(byte[] flatData) {
        Bundle bundle;
        Parcel parcel = Parcel.obtain();
        try {
            parcel.unmarshall(flatData, 0, flatData.length);
            parcel.setDataPosition(0);
            bundle = parcel.readBundle();
        }
        catch (RuntimeException e) {
            bundle = new Bundle();
        }
        finally {
            parcel.recycle();
        }
        return bundle;
    }

    private void extrasToXml(XmlSerializer out, Bundle extras) throws IOException {
        for (String key : extras.keySet()) {
            out.startTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfLG8jJFo=")));
            out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+DWgVSFo=")), key);
            Object value = extras.get(key);
            if (value instanceof Long) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgACGgzSFo=")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof Integer) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVPCthN1RF")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof Boolean) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgN1RF")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof Float) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4ED2saMFo=")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof Double) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgAI2sjHis=")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof String) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qKmUVBi0=")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), value.toString());
            } else if (value instanceof Account) {
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcYKGgVSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEVRF")));
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwE=")), ((Account)value).name);
                out.attribute(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNwQ=")), ((Account)value).type);
            }
            out.endTag(null, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfLG8jJFo=")));
        }
    }

    private void requestSync(Account account, int userId, int reason, String authority, Bundle extras) {
        if (Process.myUid() == 1000 && this.mSyncRequestListener != null) {
            this.mSyncRequestListener.onSyncRequest(account, userId, reason, authority, extras);
        } else {
            ContentResolver.requestSync((Account)account, (String)authority, (Bundle)extras);
        }
    }

    private void readStatisticsLocked() {
        try {
            int token;
            byte[] data = this.mStatisticsFile.readFully();
            Parcel in = Parcel.obtain();
            in.unmarshall(data, 0, data.length);
            in.setDataPosition(0);
            int index = 0;
            while ((token = in.readInt()) != 0) {
                if (token == 101 || token == 100) {
                    int day = in.readInt();
                    if (token == 100) {
                        day = day - 2009 + 14245;
                    }
                    DayStats ds = new DayStats(day);
                    ds.successCount = in.readInt();
                    ds.successTime = in.readLong();
                    ds.failureCount = in.readInt();
                    ds.failureTime = in.readLong();
                    if (index >= this.mDayStats.length) continue;
                    this.mDayStats[index] = ds;
                    ++index;
                    continue;
                }
                Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQpLBciLmoJTQZlJ10gLClWJA==")) + token));
                break;
            }
        }
        catch (IOException e) {
            Log.i((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGUVBi9mHgY7Kl4mL2UzQQZqDjA/Ixg2Dw==")));
        }
    }

    private void writeStatisticsLocked() {
        block3: {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MCWwFAiZiICQ2KAg5Og==")) + this.mStatisticsFile.getBaseFile()));
            this.removeMessages(2);
            FileOutputStream fos = null;
            try {
                fos = this.mStatisticsFile.startWrite();
                Parcel out = Parcel.obtain();
                for (DayStats ds : this.mDayStats) {
                    if (ds == null) break;
                    out.writeInt(101);
                    out.writeInt(ds.day);
                    out.writeInt(ds.successCount);
                    out.writeLong(ds.successTime);
                    out.writeInt(ds.failureCount);
                    out.writeLong(ds.failureTime);
                }
                out.writeInt(0);
                fos.write(out.marshall());
                out.recycle();
                this.mStatisticsFile.finishWrite(fos);
            }
            catch (IOException e1) {
                Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTQNvETg/KT5SVg==")), (Throwable)e1);
                if (fos == null) break block3;
                this.mStatisticsFile.failWrite(fos);
            }
        }
    }

    public void dumpPendingOperations(StringBuilder sb) {
        sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhguCGgFAiZiICQLIxgpIH4zSFo="))).append(this.mPendingOperations.size()).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgAKGgaFjdmHgY1KjkAL3wlIFo=")));
        for (PendingOperation pop : this.mPendingOperations) {
            sb.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PBhSVg==")) + pop.account).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Iw==")) + pop.userId).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + pop.authority).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + pop.extras).append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAJXVg==")));
        }
    }

    static {
        MESG_SUCCESS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0uOWszNANhJ1RF"));
        MESG_CANCELED = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+CGszNCRiDgpF"));
        XML_ATTR_AUTHORITYID = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUFGgRjAQoZJi0cPg=="));
        XML_ATTR_SOURCE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4AI28jLCs="));
        XML_ATTR_EXPEDITED = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfKGgVMC9mHjAw"));
        XML_ATTR_REASON = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uP28zGiY="));
        XML_ATTR_VERSION = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4uKm8zAiVgN1RF"));
        EVENTS = new String[]{StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IisqEWcmMFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IisqUmcFSFo="))};
        SOURCES = new String[]{StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IiwuDGQhNF8=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxYAE2MbHlo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhYAQGIFSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQU2WGcjSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhYuDH0bGhZrDChF"))};
        sAuthorityRenames = new HashMap();
        sAuthorityRenames.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGwFJClmEShF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1Dm4FNCZvETgqKgc2Vg==")));
        sAuthorityRenames.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+DmgVBix9ASxF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1Dm4FQSRrARovLRcMVg==")));
        sSyncStorageEngine = null;
    }

    public static interface OnSyncRequestListener {
        public void onSyncRequest(Account var1, int var2, int var3, String var4, Bundle var5);
    }

    public static class DayStats {
        public final int day;
        public int successCount;
        public long successTime;
        public int failureCount;
        public long failureTime;

        public DayStats(int day) {
            this.day = day;
        }
    }

    public static class SyncHistoryItem {
        int authorityId;
        int historyId;
        long eventTime;
        long elapsedTime;
        int source;
        int event;
        long upstreamActivity;
        long downstreamActivity;
        String mesg;
        boolean initialization;
        Bundle extras;
        int reason;
    }

    public static class AuthorityInfo {
        final ComponentName service;
        final Account account;
        final int userId;
        final String authority;
        final int ident;
        boolean enabled;
        int syncable;
        long backoffTime;
        long backoffDelay;
        long delayUntil;
        final ArrayList<PeriodicSync> periodicSyncs;

        AuthorityInfo(AuthorityInfo toCopy) {
            this.account = toCopy.account;
            this.userId = toCopy.userId;
            this.authority = toCopy.authority;
            this.service = toCopy.service;
            this.ident = toCopy.ident;
            this.enabled = toCopy.enabled;
            this.syncable = toCopy.syncable;
            this.backoffTime = toCopy.backoffTime;
            this.backoffDelay = toCopy.backoffDelay;
            this.delayUntil = toCopy.delayUntil;
            this.periodicSyncs = new ArrayList();
            for (PeriodicSync sync : toCopy.periodicSyncs) {
                this.periodicSyncs.add(mirror.android.content.PeriodicSync.clone(sync));
            }
        }

        AuthorityInfo(Account account, int userId, String authority, int ident) {
            this.account = account;
            this.userId = userId;
            this.authority = authority;
            this.service = null;
            this.ident = ident;
            this.enabled = false;
            this.syncable = -1;
            this.backoffTime = -1L;
            this.backoffDelay = -1L;
            this.periodicSyncs = new ArrayList();
            PeriodicSync sync = new PeriodicSync(account, authority, new Bundle(), 86400L);
            long flexTime = SyncStorageEngine.calculateDefaultFlexTime(86400L);
            mirror.android.content.PeriodicSync.flexTime.set(sync, flexTime);
            this.periodicSyncs.add(sync);
        }

        AuthorityInfo(ComponentName cname, int userId, int ident) {
            this.account = null;
            this.userId = userId;
            this.authority = null;
            this.service = cname;
            this.ident = ident;
            this.enabled = true;
            this.syncable = -1;
            this.backoffTime = -1L;
            this.backoffDelay = -1L;
            this.periodicSyncs = new ArrayList();
            PeriodicSync periodicSync = new PeriodicSync(this.account, this.authority, new Bundle(), 86400L);
            mirror.android.content.PeriodicSync.flexTime.set(periodicSync, SyncStorageEngine.calculateDefaultFlexTime(86400L));
            this.periodicSyncs.add(periodicSync);
        }
    }

    static class AccountInfo {
        final AccountAndUser accountAndUser;
        final HashMap<String, AuthorityInfo> authorities = new HashMap();

        AccountInfo(AccountAndUser accountAndUser) {
            this.accountAndUser = accountAndUser;
        }
    }

    public static class PendingOperation {
        final Account account;
        final int userId;
        final int reason;
        final int syncSource;
        final String authority;
        final Bundle extras;
        final ComponentName serviceName;
        final boolean expedited;
        int authorityId;
        byte[] flatExtras;

        PendingOperation(Account account, int userId, int reason, int source, String authority, Bundle extras, boolean expedited) {
            this.account = account;
            this.userId = userId;
            this.syncSource = source;
            this.reason = reason;
            this.authority = authority;
            this.extras = extras != null ? new Bundle(extras) : extras;
            this.expedited = expedited;
            this.authorityId = -1;
            this.serviceName = null;
        }

        PendingOperation(PendingOperation other) {
            this.account = other.account;
            this.userId = other.userId;
            this.reason = other.reason;
            this.syncSource = other.syncSource;
            this.authority = other.authority;
            this.extras = other.extras;
            this.authorityId = other.authorityId;
            this.expedited = other.expedited;
            this.serviceName = other.serviceName;
        }
    }
}

