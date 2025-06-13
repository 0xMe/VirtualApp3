/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.os.Binder
 *  android.util.SparseArray
 *  android.util.Xml
 *  com.kook.librelease.R$string
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.lody.virtual.server.pm;

import android.app.IStopUserCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.util.SparseArray;
import android.util.Xml;
import com.kook.librelease.R;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.AtomicFile;
import com.lody.virtual.helper.utils.FastXmlSerializer;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.interfaces.IUserManager;
import com.lody.virtual.server.pm.VAppManagerService;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VUserManagerService
extends IUserManager.Stub {
    private static final String LOG_TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITsuKWgaFg19Dlk7KC0MKGIFGgRvNx4qLhhSVg=="));
    private static final boolean DBG = false;
    private static final String TAG_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+DWgVSFo="));
    private static final String ATTR_FLAGS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4EP2gwLFo="));
    private static final String ATTR_ICON_PATH = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAg2D2ojSFo="));
    private static final String ATTR_ID = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgqVg=="));
    private static final String ATTR_CREATION_TIME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtiEVRF"));
    private static final String ATTR_LAST_LOGGED_IN_TIME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLHiViJDg/KBUcDg=="));
    private static final String ATTR_SERIAL_NO = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uKmUVJCRoNzA3Lz0MKA=="));
    private static final String ATTR_NEXT_SERIAL_NO = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4uIGwILCthNAY7KhUYLW8jRStsN1RF"));
    private static final String ATTR_PARTIAL = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+KmwFAjdgEVRF"));
    private static final String ATTR_USER_VERSION = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4uKm8zAiVgN1RF"));
    private static final String TAG_USERS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28gLFo="));
    private static final String TAG_USER = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo="));
    private static final String USER_INFO_DIR;
    private static final String USER_LIST_FILENAME;
    private static final String USER_PHOTO_FILENAME;
    private static final int MIN_USER_ID = 1;
    private static final int USER_VERSION = 1;
    private static final long EPOCH_PLUS_30_YEARS = 946080000000L;
    private static VUserManagerService sInstance;
    private final Context mContext;
    private final VPackageManagerService mPm;
    private final Object mInstallLock;
    private final Object mPackagesLock;
    private final File mUsersDir;
    private final File mUserListFile;
    private final File mBaseUserPath;
    private SparseArray<VUserInfo> mUsers = new SparseArray();
    private HashSet<Integer> mRemovingUserIds = new HashSet();
    private int[] mUserIds;
    private boolean mGuestEnabled;
    private int mNextSerialNumber;
    private int mNextUserId = 1;
    private int mUserVersion = 0;

    VUserManagerService(Context context, VPackageManagerService pm, Object installLock, Object packagesLock) {
        this(context, pm, installLock, packagesLock, VEnvironment.getDataDirectory(), new File(VEnvironment.getDataDirectory(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo="))));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private VUserManagerService(Context context, VPackageManagerService pm, Object installLock, Object packagesLock, File dataDir, File baseUserPath) {
        this.mContext = context;
        this.mPm = pm;
        this.mInstallLock = installLock;
        this.mPackagesLock = packagesLock;
        Object object = this.mInstallLock;
        synchronized (object) {
            Object object2 = this.mPackagesLock;
            synchronized (object2) {
                VUserInfo ui;
                int i;
                this.mUsersDir = new File(dataDir, USER_INFO_DIR);
                this.mUsersDir.mkdirs();
                File userZeroDir = new File(this.mUsersDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OhhSVg==")));
                userZeroDir.mkdirs();
                this.mBaseUserPath = baseUserPath;
                this.mUserListFile = new File(this.mUsersDir, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jHi9hJw02LRdXCA==")));
                this.readUserListLocked();
                ArrayList<VUserInfo> partials = new ArrayList<VUserInfo>();
                for (i = 0; i < this.mUsers.size(); ++i) {
                    ui = (VUserInfo)this.mUsers.valueAt(i);
                    if (!ui.partial || i == 0) continue;
                    partials.add(ui);
                }
                for (i = 0; i < partials.size(); ++i) {
                    ui = (VUserInfo)partials.get(i);
                    VLog.w(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITsuKWgaFg19Dlk7KC0MKGIFGgRvNx4qLhhSVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uDWowOC9gNDs8IxciKGUzLDdlEQI0PQg2CGIKPD9uDjMpIy0YJ2w3IzU=")) + i + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl9fCGsVEit0AVRF")) + ui.name + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAhSVg==")), new Object[0]);
                    this.removeUserStateLocked(ui.id);
                }
                sInstance = this;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static VUserManagerService get() {
        Class<VUserManagerService> clazz = VUserManagerService.class;
        synchronized (VUserManagerService.class) {
            // ** MonitorExit[var0] (shouldn't be in output)
            return sInstance;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<VUserInfo> getUsers(boolean excludeDying) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            ArrayList<VUserInfo> users = new ArrayList<VUserInfo>(this.mUsers.size());
            for (int i = 0; i < this.mUsers.size(); ++i) {
                VUserInfo ui = (VUserInfo)this.mUsers.valueAt(i);
                if (ui.partial || excludeDying && this.mRemovingUserIds.contains(ui.id)) continue;
                users.add(ui);
            }
            return users;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public VUserInfo getUserInfo(int userId) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            return this.getUserInfoLocked(userId);
        }
    }

    private VUserInfo getUserInfoLocked(int userId) {
        VUserInfo ui = (VUserInfo)this.mUsers.get(userId);
        if (ui != null && ui.partial && !this.mRemovingUserIds.contains(userId)) {
            VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthMgY2KD1eIH4wGiZqJxocKj4bJGYFNCBlMCMs")) + userId, new Object[0]);
            return null;
        }
        return ui;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean exists(int userId) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            return ArrayUtils.contains(this.mUserIds, userId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setUserName(int userId, String name) {
        boolean changed = false;
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo info = (VUserInfo)this.mUsers.get(userId);
            if (info == null || info.partial) {
                VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthMlk7KgcLIH4wGiZqJxocKj4bJGYFNCBlMCMs")) + userId, new Object[0]);
                return;
            }
            if (name != null && !name.equals(info.name)) {
                info.name = name;
                this.writeUserLocked(info);
                changed = true;
            }
        }
        if (changed) {
            this.sendUserInfoChangedBroadcast(userId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setUserIcon(int userId, Bitmap bitmap) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo info = (VUserInfo)this.mUsers.get(userId);
            if (info == null || info.partial) {
                VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGQaLCthMgY5Ki0XIH4wGiZqJxocKj4bJGYFNCBlMCMs")) + userId, new Object[0]);
                return;
            }
            this.writeBitmapLocked(info, bitmap);
            this.writeUserLocked(info);
        }
        this.sendUserInfoChangedBroadcast(userId);
    }

    private void sendUserInfoChangedBroadcast(int userId) {
        Intent changedIntent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgYMLRUmH2YmFlc=")));
        changedIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), userId);
        changedIntent.addFlags(0x40000000);
        VActivityManagerService.get().sendBroadcastAsUser(changedIntent, new VUserHandle(userId));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Bitmap getUserIcon(int userId) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo info = (VUserInfo)this.mUsers.get(userId);
            if (info == null || info.partial) {
                VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGQaLCthMgY5Ki0XIH4wGiZqJxocKj4bJGYFNCBlMCMs")) + userId, new Object[0]);
                return null;
            }
            if (info.iconPath == null) {
                return null;
            }
            return BitmapFactory.decodeFile((String)info.iconPath);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isGuestEnabled() {
        Object object = this.mPackagesLock;
        synchronized (object) {
            return this.mGuestEnabled;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setGuestEnabled(boolean enable) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            if (this.mGuestEnabled != enable) {
                this.mGuestEnabled = enable;
                for (int i = 0; i < this.mUsers.size(); ++i) {
                    VUserInfo user = (VUserInfo)this.mUsers.valueAt(i);
                    if (user.partial || !user.isGuest()) continue;
                    if (!enable) {
                        this.removeUser(user.id);
                    }
                    return;
                }
                if (enable) {
                    this.createUser(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JS0uM28wMFo=")), 4);
                }
            }
        }
    }

    @Override
    public void wipeUser(int userHandle) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void makeInitialized(int userId) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo info = (VUserInfo)this.mUsers.get(userId);
            if (info == null || info.partial) {
                VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwg+MWgbAiZjAQozLwdbMWgVGix+MzwwLC5bKmAjJCl5EQo8Ly1eJHgjSFo=")) + userId, new Object[0]);
            }
            if ((info.flags & 0x10) == 0) {
                info.flags |= 0x10;
                this.writeUserLocked(info);
            }
        }
    }

    private boolean isUserLimitReachedLocked() {
        int nUsers = this.mUsers.size();
        return nUsers >= VUserManager.getMaxSupportedUsers();
    }

    private void writeBitmapLocked(VUserInfo info, Bitmap bitmap) {
        try {
            FileOutputStream os;
            File dir = new File(this.mUsersDir, Integer.toString(info.id));
            File file = new File(dir, USER_PHOTO_FILENAME);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)(os = new FileOutputStream(file)))) {
                info.iconPath = file.getAbsolutePath();
            }
            try {
                os.close();
            }
            catch (IOException iOException) {}
        }
        catch (FileNotFoundException e) {
            VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShhJDAgLBccDmkJTQJqEQY/LDo6ImAjMyNqASwuLF9XVg==")), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int[] getUserIds() {
        Object object = this.mPackagesLock;
        synchronized (object) {
            return this.mUserIds;
        }
    }

    int[] getUserIdsLPr() {
        return this.mUserIds;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readUserList() {
        Object object = this.mPackagesLock;
        synchronized (object) {
            this.readUserListLocked();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readUserListLocked() {
        this.mGuestEnabled = false;
        if (!this.mUserListFile.exists()) {
            this.fallbackToSingleUserLocked();
            return;
        }
        FileInputStream fis = null;
        AtomicFile userListFile = new AtomicFile(this.mUserListFile);
        try {
            int type;
            fis = userListFile.openRead();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput((InputStream)fis, null);
            while ((type = parser.next()) != 2 && type != 1) {
            }
            if (type != 2) {
                VLog.e(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcP2sjHitLEQo1PxguPW4jAShvDjAgKSo6KGMFND8=")));
                this.fallbackToSingleUserLocked();
                return;
            }
            this.mNextSerialNumber = -1;
            if (parser.getName().equals(TAG_USERS)) {
                String versionNumber;
                String lastSerialNumber = parser.getAttributeValue(null, ATTR_NEXT_SERIAL_NO);
                if (lastSerialNumber != null) {
                    this.mNextSerialNumber = Integer.parseInt(lastSerialNumber);
                }
                if ((versionNumber = parser.getAttributeValue(null, ATTR_USER_VERSION)) != null) {
                    this.mUserVersion = Integer.parseInt(versionNumber);
                }
            }
            while ((type = parser.next()) != 1) {
                String id2;
                VUserInfo user;
                if (type != 2 || !parser.getName().equals(TAG_USER) || (user = this.readUser(Integer.parseInt(id2 = parser.getAttributeValue(null, ATTR_ID)))) == null) continue;
                this.mUsers.put(user.id, (Object)user);
                if (user.isGuest()) {
                    this.mGuestEnabled = true;
                }
                if (this.mNextSerialNumber >= 0 && this.mNextSerialNumber > user.id) continue;
                this.mNextSerialNumber = user.id + 1;
            }
            this.updateUserIdsLocked();
            this.upgradeIfNecessary();
        }
        catch (IOException ioe) {
            this.fallbackToSingleUserLocked();
        }
        catch (XmlPullParserException pe) {
            this.fallbackToSingleUserLocked();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void upgradeIfNecessary() {
        int userVersion = this.mUserVersion;
        if (userVersion < 1) {
            VUserInfo user = (VUserInfo)this.mUsers.get(0);
            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhcMCWoVJARnAVRF")).equals(user.name)) {
                user.name = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggqDWUVBlo="));
                this.writeUserLocked(user);
            }
            userVersion = 1;
        }
        if (userVersion < 1) {
            VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQc2M28nID5iASwpKQdfDn4zSFo=")) + this.mUserVersion + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PhgqCWgFAS1mVyQvIxc6KG4jBit4ETg6PQguPGEaLCZqHgotOD0cKXgVSFo=")) + 1, new Object[0]);
        } else {
            this.mUserVersion = userVersion;
            this.writeUserListLocked();
        }
    }

    private void fallbackToSingleUserLocked() {
        VUserInfo primary = new VUserInfo(0, this.mContext.getResources().getString(R.string.owner_name), null, 19);
        this.mUsers.put(0, (Object)primary);
        this.mNextSerialNumber = 1;
        this.updateUserIdsLocked();
        this.writeUserListLocked();
        this.writeUserLocked(primary);
    }

    private void writeUserLocked(VUserInfo userInfo) {
        FileOutputStream fos = null;
        AtomicFile userFile = new AtomicFile(new File(this.mUsersDir, userInfo.id + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz1fDWoFSFo="))));
        try {
            fos = userFile.startWrite();
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            FastXmlSerializer serializer = new FastXmlSerializer();
            serializer.setOutput(bos, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQcqPnpTRVo=")));
            serializer.startDocument(null, true);
            serializer.setFeature(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBcqLG8OTCVOJxo3KhgmLW8zOyZlJAouPD0hDU4gFippIFkvLy5bCm8KFj9vMxo/IBdbO3kgBgJpNwY5KV8ID2waMAJmAQpF")), true);
            serializer.startTag(null, TAG_USER);
            serializer.attribute(null, ATTR_ID, Integer.toString(userInfo.id));
            serializer.attribute(null, ATTR_SERIAL_NO, Integer.toString(userInfo.serialNumber));
            serializer.attribute(null, ATTR_FLAGS, Integer.toString(userInfo.flags));
            serializer.attribute(null, ATTR_CREATION_TIME, Long.toString(userInfo.creationTime));
            serializer.attribute(null, ATTR_LAST_LOGGED_IN_TIME, Long.toString(userInfo.lastLoggedInTime));
            if (userInfo.iconPath != null) {
                serializer.attribute(null, ATTR_ICON_PATH, userInfo.iconPath);
            }
            if (userInfo.partial) {
                serializer.attribute(null, ATTR_PARTIAL, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcMI2gVSFo=")));
            }
            serializer.startTag(null, TAG_NAME);
            serializer.text(userInfo.name);
            serializer.endTag(null, TAG_NAME);
            serializer.endTag(null, TAG_USER);
            serializer.endDocument();
            userFile.finishWrite(fos);
        }
        catch (Exception ioe) {
            VLog.e(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTQVsJyg5PQgYKmIwDSM=")) + userInfo.id + "\n" + ioe);
            userFile.failWrite(fos);
        }
    }

    private void writeUserListLocked() {
        FileOutputStream fos = null;
        AtomicFile userListFile = new AtomicFile(this.mUserListFile);
        try {
            fos = userListFile.startWrite();
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            FastXmlSerializer serializer = new FastXmlSerializer();
            serializer.setOutput(bos, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQcqPnpTRVo=")));
            serializer.startDocument(null, true);
            serializer.setFeature(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBcqLG8OTCVOJxo3KhgmLW8zOyZlJAouPD0hDU4gFippIFkvLy5bCm8KFj9vMxo/IBdbO3kgBgJpNwY5KV8ID2waMAJmAQpF")), true);
            serializer.startTag(null, TAG_USERS);
            serializer.attribute(null, ATTR_NEXT_SERIAL_NO, Integer.toString(this.mNextSerialNumber));
            serializer.attribute(null, ATTR_USER_VERSION, Integer.toString(this.mUserVersion));
            for (int i = 0; i < this.mUsers.size(); ++i) {
                VUserInfo user = (VUserInfo)this.mUsers.valueAt(i);
                serializer.startTag(null, TAG_USER);
                serializer.attribute(null, ATTR_ID, Integer.toString(user.id));
                serializer.endTag(null, TAG_USER);
            }
            serializer.endTag(null, TAG_USERS);
            serializer.endDocument();
            userListFile.finishWrite(fos);
        }
        catch (Exception e) {
            userListFile.failWrite(fos);
            VLog.e(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowEShmJywzLBccDmkJTQVsJyg5PQgEI2EjFlo=")));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private VUserInfo readUser(int id2) {
        int flags = 0;
        int serialNumber = id2;
        String name = null;
        String iconPath = null;
        long creationTime = 0L;
        long lastLoggedInTime = 0L;
        boolean partial = false;
        FileInputStream fis = null;
        try {
            int type;
            AtomicFile userFile = new AtomicFile(new File(this.mUsersDir, Integer.toString(id2) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz1fDWoFSFo="))));
            fis = userFile.openRead();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput((InputStream)fis, null);
            while ((type = parser.next()) != 2 && type != 1) {
            }
            if (type != 2) {
                VLog.e(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcP2sjHitLEQo1PxguPW4jAShvDjAgKSo6Vg==")) + id2);
                VUserInfo vUserInfo = null;
                return vUserInfo;
            }
            if (parser.getName().equals(TAG_USER)) {
                int storedId = this.readIntAttribute(parser, ATTR_ID, -1);
                if (storedId != id2) {
                    VLog.e(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQc2M28nIC9iVyQwKi0ML34zMCVvVjweLRcqJWNTOD9vHg0pLxg2KGsJIARrEQ40")));
                    VUserInfo vUserInfo = null;
                    return vUserInfo;
                }
                serialNumber = this.readIntAttribute(parser, ATTR_SERIAL_NO, id2);
                flags = this.readIntAttribute(parser, ATTR_FLAGS, 0);
                iconPath = parser.getAttributeValue(null, ATTR_ICON_PATH);
                creationTime = this.readLongAttribute(parser, ATTR_CREATION_TIME, 0L);
                lastLoggedInTime = this.readLongAttribute(parser, ATTR_LAST_LOGGED_IN_TIME, 0L);
                String valueString = parser.getAttributeValue(null, ATTR_PARTIAL);
                if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRcMI2gVSFo=")).equals(valueString)) {
                    partial = true;
                }
                while ((type = parser.next()) != 2 && type != 1) {
                }
                if (type == 2 && parser.getName().equals(TAG_NAME) && (type = parser.next()) == 4) {
                    name = parser.getText();
                }
            }
            VUserInfo userInfo = new VUserInfo(id2, name, iconPath, flags);
            userInfo.serialNumber = serialNumber;
            userInfo.creationTime = creationTime;
            userInfo.lastLoggedInTime = lastLoggedInTime;
            userInfo.partial = partial;
            VUserInfo vUserInfo = userInfo;
            return vUserInfo;
        }
        catch (IOException iOException) {
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            return null;
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    private int readIntAttribute(XmlPullParser parser, String attr2, int defaultValue) {
        String valueString = parser.getAttributeValue(null, attr2);
        if (valueString == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueString);
        }
        catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    private long readLongAttribute(XmlPullParser parser, String attr2, long defaultValue) {
        String valueString = parser.getAttributeValue(null, attr2);
        if (valueString == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(valueString);
        }
        catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public VUserInfo createUser(String name, int flags) {
        VUserInfo userInfo;
        long ident = Binder.clearCallingIdentity();
        try {
            Object object = this.mInstallLock;
            synchronized (object) {
                Object object2 = this.mPackagesLock;
                synchronized (object2) {
                    if (this.isUserLimitReachedLocked()) {
                        VUserInfo vUserInfo = null;
                        return vUserInfo;
                    }
                    int userId = this.getNextAvailableIdLocked();
                    userInfo = new VUserInfo(userId, name, null, flags);
                    File userPath = new File(this.mBaseUserPath, Integer.toString(userId));
                    userInfo.serialNumber = this.mNextSerialNumber++;
                    long now = System.currentTimeMillis();
                    userInfo.creationTime = now > 946080000000L ? now : 0L;
                    userInfo.partial = true;
                    VAppManagerService.get().onUserCreated(userInfo);
                    this.mUsers.put(userId, (Object)userInfo);
                    this.writeUserListLocked();
                    this.writeUserLocked(userInfo);
                    this.mPm.createNewUser(userId, userPath);
                    userInfo.partial = false;
                    this.writeUserLocked(userInfo);
                    this.updateUserIdsLocked();
                }
            }
            Intent addedIntent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgYOLBUMBmYVSFo=")));
            addedIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), userInfo.id);
            VActivityManagerService.get().sendBroadcastAsUser(addedIntent, VUserHandle.ALL, null);
        }
        finally {
            Binder.restoreCallingIdentity((long)ident);
        }
        new Thread(new Runnable(){

            @Override
            public void run() {
                for (String preInstallPkg : SpecialComponentList.getPreInstallPackages()) {
                    if (userInfo.id == 0 || VAppManagerService.get().isAppInstalledAsUser(userInfo.id, preInstallPkg)) continue;
                    VAppManagerService.get().installPackageAsUser(userInfo.id, preInstallPkg);
                }
            }
        }).start();
        return userInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeUser(int userHandle) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo user = (VUserInfo)this.mUsers.get(userHandle);
            if (userHandle == 0 || user == null) {
                return false;
            }
            this.mRemovingUserIds.add(userHandle);
            user.partial = true;
            this.writeUserLocked(user);
        }
        int res = VActivityManagerService.get().stopUser(userHandle, new IStopUserCallback.Stub(){

            @Override
            public void userStopped(int userId) {
                VUserManagerService.this.finishRemoveUser(userId);
            }

            @Override
            public void userStopAborted(int userId) {
            }
        });
        return res == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void finishRemoveUser(final int userHandle) {
        long identity = Binder.clearCallingIdentity();
        try {
            Intent addedIntent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKmwKNDdgV1k7Kj02KG8FLCx1Nx4bKgguKmZTRSRpJzAiKQgpKmcILFRnIgZALAVbGGI2Flc=")));
            addedIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), userHandle);
            VActivityManagerService.get().sendOrderedBroadcastAsUser(addedIntent, VUserHandle.ALL, null, new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    new Thread(){

                        /*
                         * WARNING - Removed try catching itself - possible behaviour change.
                         */
                        @Override
                        public void run() {
                            Object object = VUserManagerService.this.mInstallLock;
                            synchronized (object) {
                                Object object2 = VUserManagerService.this.mPackagesLock;
                                synchronized (object2) {
                                    VUserManagerService.this.removeUserStateLocked(userHandle);
                                }
                            }
                        }
                    }.start();
                }
            }, null, -1, null, null);
        }
        finally {
            Binder.restoreCallingIdentity((long)identity);
        }
    }

    private void removeUserStateLocked(int userHandle) {
        this.mPm.cleanUpUser(userHandle);
        this.mUsers.remove(userHandle);
        this.mRemovingUserIds.remove(userHandle);
        AtomicFile userFile = new AtomicFile(new File(this.mUsersDir, userHandle + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz1fDWoFSFo="))));
        userFile.delete();
        this.writeUserListLocked();
        this.updateUserIdsLocked();
        this.removeDirectoryRecursive(VEnvironment.getDataUserDirectory(userHandle));
    }

    private void removeDirectoryRecursive(File parent) {
        if (parent.isDirectory()) {
            String[] files;
            for (String filename : files = parent.list()) {
                File child = new File(parent, filename);
                this.removeDirectoryRecursive(child);
            }
        }
        parent.delete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getUserSerialNumber(int userHandle) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            if (!this.exists(userHandle)) {
                return -1;
            }
            return this.getUserInfoLocked((int)userHandle).serialNumber;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getUserHandle(int userSerialNumber) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            for (int userId : this.mUserIds) {
                if (this.getUserInfoLocked((int)userId).serialNumber != userSerialNumber) continue;
                return userId;
            }
            return -1;
        }
    }

    private void updateUserIdsLocked() {
        int num = 0;
        for (int i = 0; i < this.mUsers.size(); ++i) {
            if (((VUserInfo)this.mUsers.valueAt((int)i)).partial) continue;
            ++num;
        }
        int[] newUsers = new int[num];
        int n = 0;
        for (int i = 0; i < this.mUsers.size(); ++i) {
            if (((VUserInfo)this.mUsers.valueAt((int)i)).partial) continue;
            newUsers[n++] = this.mUsers.keyAt(i);
        }
        this.mUserIds = newUsers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void userForeground(int userId) {
        Object object = this.mPackagesLock;
        synchronized (object) {
            VUserInfo user = (VUserInfo)this.mUsers.get(userId);
            long now = System.currentTimeMillis();
            if (user == null || user.partial) {
                VLog.w(LOG_TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28hOCVhNDA9Iz1fLW8VATJ4HigbIz4cKWYgRCNqASwuLF9WJQ==")) + userId, new Object[0]);
                return;
            }
            if (now > 946080000000L) {
                user.lastLoggedInTime = now;
                this.writeUserLocked(user);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int getNextAvailableIdLocked() {
        Object object = this.mPackagesLock;
        synchronized (object) {
            int i;
            for (i = this.mNextUserId; i < Integer.MAX_VALUE && (this.mUsers.indexOfKey(i) >= 0 || this.mRemovingUserIds.contains(i)); ++i) {
            }
            this.mNextUserId = i + 1;
            return i;
        }
    }

    static {
        USER_LIST_FILENAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jHi9hJw02LRdXCA=="));
        USER_PHOTO_FILENAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhhfD2wFBSZhHlk9"));
        USER_INFO_DIR = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YKWwFNCM=")) + File.separator + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28gLFo="));
    }
}

