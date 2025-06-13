/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AuthenticatorDescription
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 */
package com.lody.virtual.server.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.accounts.IAccountAuthenticator;
import android.accounts.IAccountAuthenticatorResponse;
import android.accounts.IAccountManagerResponse;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VBinder;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.accounts.AccountAndUser;
import com.lody.virtual.server.accounts.RegisteredServicesParser;
import com.lody.virtual.server.accounts.VAccount;
import com.lody.virtual.server.accounts.VAccountVisibility;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.interfaces.IAccountManager;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mirror.com.android.internal.R_Hide;
import org.xmlpull.v1.XmlPullParser;

public class VAccountManagerService
extends IAccountManager.Stub {
    private static final Singleton<VAccountManagerService> sInstance = new Singleton<VAccountManagerService>(){

        @Override
        protected VAccountManagerService create() {
            return new VAccountManagerService();
        }
    };
    private static final long CHECK_IN_TIME = 43200000L;
    private static final String TAG = VAccountManagerService.class.getSimpleName();
    private final SparseArray<List<VAccount>> accountsByUserId = new SparseArray();
    private final SparseArray<List<VAccountVisibility>> accountsVisibilitiesByUserId = new SparseArray();
    private final LinkedList<AuthTokenRecord> authTokenRecords = new LinkedList();
    private final LinkedHashMap<String, Session> mSessions = new LinkedHashMap();
    private final AuthenticatorCache cache = new AuthenticatorCache();
    private Context mContext = VirtualCore.get().getContext();
    private long lastAccountChangeTime = 0L;

    public static VAccountManagerService get() {
        return sInstance.get();
    }

    public static void systemReady() {
        VAccountManagerService.get().readAllAccounts();
        VAccountManagerService.get().readAllAccountVisibilities();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static AuthenticatorDescription parseAuthenticatorDescription(Resources resources, String packageName, AttributeSet attributeSet) {
        TypedArray array2 = resources.obtainAttributes(attributeSet, R_Hide.styleable.AccountAuthenticator.get());
        try {
            String accountType = array2.getString(R_Hide.styleable.AccountAuthenticator_accountType.get());
            int label = array2.getResourceId(R_Hide.styleable.AccountAuthenticator_label.get(), 0);
            int icon = array2.getResourceId(R_Hide.styleable.AccountAuthenticator_icon.get(), 0);
            int smallIcon = array2.getResourceId(R_Hide.styleable.AccountAuthenticator_smallIcon.get(), 0);
            int accountPreferences = array2.getResourceId(R_Hide.styleable.AccountAuthenticator_accountPreferences.get(), 0);
            boolean customTokens = array2.getBoolean(R_Hide.styleable.AccountAuthenticator_customTokens.get(), false);
            if (TextUtils.isEmpty((CharSequence)accountType)) {
                AuthenticatorDescription authenticatorDescription = null;
                return authenticatorDescription;
            }
            AuthenticatorDescription authenticatorDescription = new AuthenticatorDescription(accountType, packageName, label, icon, smallIcon, accountPreferences, customTokens);
            return authenticatorDescription;
        }
        finally {
            array2.recycle();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AuthenticatorDescription[] getAuthenticatorTypes(int userId) {
        AuthenticatorCache authenticatorCache = this.cache;
        synchronized (authenticatorCache) {
            AuthenticatorDescription[] descArray = new AuthenticatorDescription[this.cache.authenticators.size()];
            int i = 0;
            for (AuthenticatorInfo info : this.cache.authenticators.values()) {
                descArray[i] = info.desc;
                ++i;
            }
            return descArray;
        }
    }

    @Override
    public void getAccountsByFeatures(int userId, IAccountManagerResponse response, String type, String[] features) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (type == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(type);
        if (info == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")), (Parcelable[])new Account[0]);
            try {
                response.onResult(bundle);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        if (features == null || features.length == 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")), (Parcelable[])this.getAccounts(userId, type));
            try {
                response.onResult(bundle);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            new GetAccountsByTypeAndFeatureSession(response, userId, info, features).bind();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final String getPreviousName(int userId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            String previousName = null;
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                previousName = vAccount.previousName;
            }
            return previousName;
        }
    }

    @Override
    public Account[] getAccounts(int userId, String type) {
        List<Account> accountList = this.getAccountList(userId, type);
        return accountList.toArray(new Account[accountList.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Account> getAccountList(int userId, String type) {
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            ArrayList<Account> accounts = new ArrayList<Account>();
            List vAccounts = (List)this.accountsByUserId.get(userId);
            if (vAccounts != null) {
                for (VAccount vAccount : vAccounts) {
                    if (type != null && !vAccount.type.equals(type)) continue;
                    accounts.add(new Account(vAccount.name, vAccount.type));
                }
            }
            return accounts;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void getAuthToken(final int userId, IAccountManagerResponse response, final Account account, final String authTokenType, final boolean notifyOnAuthFailure, boolean expectActivityLaunch, final Bundle loginOptions) {
        String authToken;
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        try {
            if (account == null) {
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaNAZjHwo1KS0MDn4zAjdlEQIgLgQ6MWMFFit5Hh4+KT4hJGgFLDVqNCg5IBhSVg==")), new Object[0]);
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
                return;
            }
            if (authTokenType == null) {
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMaNAZjHwo1KS0MDn4zAjdlEQIgLgQ6MWMFFit5Hh4+KT4hJGgKNCBlDCw6JS02JWIVBiZpJ1RF")), new Object[0]);
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmknTS9sIzwbKhgEKA==")));
                return;
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
            return;
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(account.type);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        final String callerPkg = loginOptions.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iHyQ7Ly0EOWkFGgBoAQ4g")));
        final boolean customTokens = info.desc.customTokens;
        loginOptions.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+DmoFNARuDgYw")), VBinder.getCallingUid());
        loginOptions.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+DmoFNARpHgYw")), VBinder.getCallingPid());
        if (notifyOnAuthFailure) {
            loginOptions.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOD9oJFkRLAg2MmEVQS9lHig5LhhSVg==")), true);
        }
        if (!customTokens) {
            String authToken2;
            VAccount vAccount;
            SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
            synchronized (sparseArray) {
                vAccount = this.getAccount(userId, account);
            }
            String string2 = authToken2 = vAccount != null ? vAccount.authTokens.get(authTokenType) : null;
            if (authToken2 != null) {
                Bundle result = new Bundle();
                result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2")), authToken2);
                result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")), account.name);
                result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")), account.type);
                this.onResult(response, result);
                return;
            }
        }
        if (customTokens && (authToken = this.getCustomAuthToken(userId, account, authTokenType, callerPkg)) != null) {
            Bundle result = new Bundle();
            result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2")), authToken);
            result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")), account.name);
            result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")), account.type);
            this.onResult(response, result);
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, false, account.name){

            @Override
            protected String toDebugString(long now) {
                return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186PWgaMBFmAQo0JBdfCWkjMyR4EVRF")) + account + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2waMCBuHh4xKAcYAGggTSt4EVRF")) + authTokenType + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186DmozPC9gMh4sLBccDW8aDSg=")) + loginOptions + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186CGowMC9iNwYLKjsiLWUzFghoAR4dKhcMJ0sVSFo=")) + notifyOnAuthFailure;
            }

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.getAuthToken(this, account, authTokenType, loginOptions);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onResult(Bundle result) throws RemoteException {
                if (result != null) {
                    Intent intent;
                    String authToken = result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2")));
                    if (authToken != null) {
                        String name = result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")));
                        String type = result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")));
                        if (TextUtils.isEmpty((CharSequence)type) || TextUtils.isEmpty((CharSequence)name)) {
                            this.onError(5, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KRhfM3sKMD9hHjM8LwcYPn4zMDdlASsrKT5fKWYKTS95Hh4eIzpXJmsJID9qHjwZIQhSVg==")));
                            return;
                        }
                        if (!customTokens) {
                            SparseArray sparseArray = VAccountManagerService.this.accountsByUserId;
                            synchronized (sparseArray) {
                                VAccount account2 = VAccountManagerService.this.getAccount(userId, name, type);
                                if (account2 == null) {
                                    ArrayList<VAccount> accounts = (ArrayList<VAccount>)VAccountManagerService.this.accountsByUserId.get(userId);
                                    if (accounts == null) {
                                        accounts = new ArrayList<VAccount>();
                                        VAccountManagerService.this.accountsByUserId.put(userId, accounts);
                                    }
                                    account2 = new VAccount(userId, new Account(name, type));
                                    accounts.add(account2);
                                    VAccountManagerService.this.saveAllAccounts();
                                }
                            }
                        }
                        long expiryMillis = result.getLong(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxogLwc6I2EzGlo=")), 0L);
                        if (customTokens && expiryMillis > System.currentTimeMillis()) {
                            AuthTokenRecord record = new AuthTokenRecord(userId, account, authTokenType, callerPkg, authToken, expiryMillis);
                            LinkedList linkedList = VAccountManagerService.this.authTokenRecords;
                            synchronized (linkedList) {
                                VAccountManagerService.this.authTokenRecords.remove(record);
                                VAccountManagerService.this.authTokenRecords.add(record);
                            }
                        }
                    }
                    if ((intent = (Intent)result.getParcelable(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVBgY=")))) == null || !notifyOnAuthFailure || !customTokens) {
                        // empty if block
                    }
                }
                super.onResult(result);
            }
        }.bind();
    }

    @Override
    public void setPassword(int userId, Account account, String password) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        this.setPasswordInternal(userId, account, password);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setPasswordInternal(int userId, Account account, String password) {
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                vAccount.password = password;
                vAccount.authTokens.clear();
                this.saveAllAccounts();
                LinkedList<AuthTokenRecord> linkedList = this.authTokenRecords;
                synchronized (linkedList) {
                    Iterator iterator = this.authTokenRecords.iterator();
                    while (iterator.hasNext()) {
                        AuthTokenRecord record = (AuthTokenRecord)iterator.next();
                        if (record.userId != userId || !record.account.equals((Object)account)) continue;
                        iterator.remove();
                    }
                }
                this.sendAccountsChangedBroadcast(userId);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setAuthToken(int userId, Account account, String authTokenType, String authToken) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        if (authTokenType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmknTS9sIzwbKhgEKA==")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                vAccount.authTokens.put(authTokenType, authToken);
                this.saveAllAccounts();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setUserData(int userId, Account account, String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC4uJ3sFAgNLHlkvKhdbVg==")));
        }
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        VAccount vAccount = this.getAccount(userId, account);
        if (vAccount != null) {
            SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
            synchronized (sparseArray) {
                vAccount.userDatas.put(key, value);
                this.saveAllAccounts();
            }
        }
    }

    @Override
    public void hasFeatures(int userId, IAccountManagerResponse response, final Account account, final String[] features) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        if (features == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4uP2wKNARiASs8KQgpOm8aGiRlEVRF")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(account.type);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, false, true, account.name){

            @Override
            public void run() throws RemoteException {
                try {
                    this.mAuthenticator.hasFeatures(this, account, features);
                }
                catch (RemoteException e) {
                    this.onError(1, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowMCtLHjAaLy0MKmUzLCVlN1RF")));
                }
            }

            @Override
            public void onResult(Bundle result) throws RemoteException {
                IAccountManagerResponse response = this.getResponseAndClose();
                if (response != null) {
                    try {
                        if (result == null) {
                            response.onError(5, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoJICpmDlkwKhcMVg==")));
                            return;
                        }
                        Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YAmkgAgVlHi8ZM186KWA0ODVuASw5KQgqD2sJIFo=")) + response));
                        Bundle newResult = new Bundle();
                        newResult.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), result.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), false));
                        response.onResult(newResult);
                    }
                    catch (RemoteException e) {
                        Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoKNARiCiQtKRccCGknTSZlJCwaLi0YI2AwJyNlNAo8LD4uKmwjNFo=")), (Throwable)e);
                    }
                }
            }
        }.bind();
    }

    @Override
    public void updateCredentials(int userId, IAccountManagerResponse response, final Account account, final String authTokenType, boolean expectActivityLaunch, final Bundle loginOptions) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        if (authTokenType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmknTS9sIzwbKhgEKA==")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(account.type);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, false, account.name){

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.updateCredentials(this, account, authTokenType, loginOptions);
            }

            @Override
            protected String toDebugString(long now) {
                if (loginOptions != null) {
                    loginOptions.keySet();
                }
                return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186I28FMDdmHjAfIz0MPmkjMAZqATgdKToDJA==")) + account + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2waMCBuHh4xKAcYAGggTSt4EVRF")) + authTokenType + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186DmozPC9gMh4sLBccDW8aDSg=")) + loginOptions;
            }
        }.bind();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getPassword(int userId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                return vAccount.password;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getUserData(int userId, Account account, String key) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        if (key == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LC4uJ3sFAgNLHlkvKhdbVg==")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                return vAccount.userDatas.get(key);
            }
            return null;
        }
    }

    @Override
    public void editProperties(int userId, IAccountManagerResponse response, final String accountType, boolean expectActivityLaunch) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (accountType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(accountType);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, true, null){

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.editProperties(this, this.mAuthenticatorInfo.desc.type);
            }

            @Override
            protected String toDebugString(long now) {
                return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186M2gFAgZpESw1IxcMKGUzLCtsIwUrLRg2JWAjLClqHzAyLD4fJA==")) + accountType;
            }
        }.bind();
    }

    @Override
    public void getAuthTokenLabel(int userId, IAccountManagerResponse response, String accountType, final String authTokenType) {
        if (accountType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
        }
        if (authTokenType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmknTS9sIzwbKhgEKA==")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(accountType);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, false, false, null){

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.getAuthTokenLabel(this, authTokenType);
            }

            @Override
            public void onResult(Bundle result) throws RemoteException {
                if (result != null) {
                    String label = result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2IhciOGkjOAtrDh5F")));
                    Bundle bundle = new Bundle();
                    bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2IhciOGkjOAtrDh5F")), label);
                    super.onResult(bundle);
                } else {
                    super.onResult(null);
                }
            }
        }.bind();
    }

    @Override
    public void confirmCredentials(int userId, IAccountManagerResponse response, final Account account, final Bundle options, boolean expectActivityLaunch) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(account.type);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, true, account.name, true, true){

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.confirmCredentials(this, account, options);
            }
        }.bind();
    }

    @Override
    public void addAccount(int userId, IAccountManagerResponse response, final String accountType, final String authTokenType, final String[] requiredFeatures, boolean expectActivityLaunch, final Bundle optionsIn) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (accountType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(accountType);
        if (info == null) {
            try {
                Bundle result = new Bundle();
                result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2")), authTokenType);
                result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")), accountType);
                result.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), false);
                response.onResult(result);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, true, null, false, true){

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.addAccount(this, this.mAuthenticatorInfo.desc.type, authTokenType, requiredFeatures, optionsIn);
            }

            @Override
            protected String toDebugString(long now) {
                return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P2gFMBF9JCg1LAcYLn83TTdoJzAcKhgcCm4VGjNuCiBF")) + accountType + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmgaJAVjASw/KBU+PW4gBgVsNyg6PQhSVg==")) + (requiredFeatures != null ? TextUtils.join((CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MxhSVg==")), (Object[])requiredFeatures) : null);
            }
        }.bind();
    }

    @Override
    public boolean addAccountExplicitly(int userId, Account account, String password, Bundle extras) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        return this.insertAccountIntoDatabase(userId, account, password, extras);
    }

    @Override
    public boolean removeAccountExplicitly(int userId, Account account) {
        return account != null && this.removeAccountInternal(userId, account);
    }

    @Override
    public void renameAccount(int userId, IAccountManagerResponse response, Account accountToRename, String newName) {
        if (accountToRename == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        Account resultingAccount = this.renameAccountInternal(userId, accountToRename, newName);
        Bundle result = new Bundle();
        result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")), resultingAccount.name);
        result.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")), resultingAccount.type);
        try {
            response.onResult(result);
        }
        catch (RemoteException e) {
            Log.w((String)TAG, (String)e.getMessage());
        }
    }

    @Override
    public void removeAccount(final int userId, IAccountManagerResponse response, final Account account, boolean expectActivityLaunch) {
        if (response == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKW8FGiZhJDM8KQgpOm8aGiRlEVRF")));
        }
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        AuthenticatorInfo info = this.getAuthenticatorInfo(account.type);
        if (info == null) {
            try {
                response.onError(7, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV1kgLQgmPX4zBiVrDjMrLC4ACksaLAVvASw9")));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }
        new Session(response, userId, info, expectActivityLaunch, true, account.name){

            @Override
            protected String toDebugString(long now) {
                return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmgVEiVmNDARLy0qDWUjMAZ1VjwsLT42KWYKRT95EVRF")) + account;
            }

            @Override
            public void run() throws RemoteException {
                this.mAuthenticator.getAccountRemovalAllowed(this, account);
            }

            @Override
            public void onResult(Bundle result) throws RemoteException {
                if (result != null && result.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo="))) && !result.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVBgY=")))) {
                    IAccountManagerResponse response;
                    boolean removalAllowed = result.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")));
                    if (removalAllowed) {
                        VAccountManagerService.this.removeAccountInternal(userId, account);
                    }
                    if ((response = this.getResponseAndClose()) != null) {
                        Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YAmkgAgVlHi8ZM186KWA0ODVuASw5KQgqD2sJIFo=")) + response));
                        Bundle result2 = new Bundle();
                        result2.putBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), removalAllowed);
                        try {
                            response.onResult(result2);
                        }
                        catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                super.onResult(result);
            }
        }.bind();
    }

    @Override
    public void clearPassword(int userId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        this.setPasswordInternal(userId, account, null);
    }

    private boolean removeAccountInternal(int userId, Account account) {
        List accounts = (List)this.accountsByUserId.get(userId);
        if (accounts != null) {
            Iterator iterator = accounts.iterator();
            while (iterator.hasNext()) {
                VAccount vAccount = (VAccount)iterator.next();
                if (userId != vAccount.userId || !TextUtils.equals((CharSequence)vAccount.name, (CharSequence)account.name) || !TextUtils.equals((CharSequence)account.type, (CharSequence)vAccount.type)) continue;
                iterator.remove();
                this.saveAllAccounts();
                this.sendAccountsChangedBroadcast(userId);
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean accountAuthenticated(int userId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                vAccount.lastAuthenticatedTime = System.currentTimeMillis();
                this.saveAllAccounts();
                return true;
            }
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void invalidateAuthToken(int userId, String accountType, String authToken) {
        if (accountType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
        }
        if (authToken == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2PxccL34zMAVlEQJF")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            List accounts = (List)this.accountsByUserId.get(userId);
            if (accounts != null) {
                boolean changed = false;
                for (VAccount account : accounts) {
                    if (!account.type.equals(accountType)) continue;
                    account.authTokens.values().remove(authToken);
                    changed = true;
                }
                if (changed) {
                    this.saveAllAccounts();
                }
            }
            LinkedList<AuthTokenRecord> linkedList = this.authTokenRecords;
            synchronized (linkedList) {
                Iterator iterator = this.authTokenRecords.iterator();
                while (iterator.hasNext()) {
                    AuthTokenRecord record = (AuthTokenRecord)iterator.next();
                    if (record.userId != userId || !record.authTokenType.equals(accountType) || !record.authToken.equals(authToken)) continue;
                    iterator.remove();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Account renameAccountInternal(int userId, Account accountToRename, String newName) {
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, accountToRename);
            if (vAccount != null) {
                vAccount.previousName = vAccount.name;
                vAccount.name = newName;
                this.saveAllAccounts();
                Account newAccount = new Account(vAccount.name, vAccount.type);
                LinkedList<AuthTokenRecord> linkedList = this.authTokenRecords;
                synchronized (linkedList) {
                    for (AuthTokenRecord record : this.authTokenRecords) {
                        if (record.userId != userId || !record.account.equals((Object)accountToRename)) continue;
                        record.account = newAccount;
                    }
                }
                this.sendAccountsChangedBroadcast(userId);
                return newAccount;
            }
        }
        return accountToRename;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String peekAuthToken(int userId, Account account, String authTokenType) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        if (authTokenType == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmknTS9sIzwbKhgEKA==")));
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            VAccount vAccount = this.getAccount(userId, account);
            if (vAccount != null) {
                return vAccount.authTokens.get(authTokenType);
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getCustomAuthToken(int userId, Account account, String authTokenType, String packageName) {
        AuthTokenRecord record = new AuthTokenRecord(userId, account, authTokenType, packageName);
        String authToken = null;
        long now = System.currentTimeMillis();
        LinkedList<AuthTokenRecord> linkedList = this.authTokenRecords;
        synchronized (linkedList) {
            Iterator iterator = this.authTokenRecords.iterator();
            while (iterator.hasNext()) {
                AuthTokenRecord one = (AuthTokenRecord)iterator.next();
                if (one.expiryEpochMillis > 0L && one.expiryEpochMillis < now) {
                    iterator.remove();
                    continue;
                }
                if (!record.equals(one)) continue;
                authToken = record.authToken;
            }
        }
        return authToken;
    }

    private void onResult(IAccountManagerResponse response, Bundle result) {
        try {
            response.onResult(result);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private AuthenticatorInfo getAuthenticatorInfo(String type) {
        AuthenticatorCache authenticatorCache = this.cache;
        synchronized (authenticatorCache) {
            return type == null ? null : this.cache.authenticators.get(type);
        }
    }

    private VAccount getAccount(int userId, Account account) {
        return this.getAccount(userId, account.name, account.type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean insertAccountIntoDatabase(int userId, Account account, String password, Bundle extras) {
        if (account == null) {
            return false;
        }
        SparseArray<List<VAccount>> sparseArray = this.accountsByUserId;
        synchronized (sparseArray) {
            ArrayList<VAccount> accounts;
            if (this.getAccount(userId, account.name, account.type) != null) {
                return false;
            }
            VAccount vAccount = new VAccount(userId, account);
            vAccount.password = password;
            if (extras != null) {
                for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    if (!(value instanceof String)) continue;
                    vAccount.userDatas.put(key, (String)value);
                }
            }
            if ((accounts = (ArrayList<VAccount>)this.accountsByUserId.get(userId)) == null) {
                accounts = new ArrayList<VAccount>();
                this.accountsByUserId.put(userId, accounts);
            }
            accounts.add(vAccount);
            this.saveAllAccounts();
            this.sendAccountsChangedBroadcast(vAccount.userId);
            return true;
        }
    }

    private void sendAccountsChangedBroadcast(int userId) {
        Intent loginChangeIntent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxpTJDwmBWgxAg5hIixXOywqXWQmGh99DzgfLCs2BQ==")));
        VActivityManagerService.get().sendBroadcastAsUser(loginChangeIntent, new VUserHandle(userId));
        Intent accountsChangeIntent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxosLT0qI2AgRCliMhoSIixfAmMIGh1jNTAQKAVfWH0hRR1kNV0fJSwuWQ==")));
        VActivityManagerService.get().sendBroadcastAsUser(accountsChangeIntent, new VUserHandle(userId));
        this.broadcastCheckInNowIfNeed(userId);
    }

    private void broadcastCheckInNowIfNeed(int userId) {
        long time = System.currentTimeMillis();
        if (Math.abs(time - this.lastAccountChangeTime) > 43200000L) {
            this.lastAccountChangeTime = time;
            this.saveAllAccounts();
            Intent intent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kpKAguLGkgRCZoJ1kgLT5bI2A0RQBnHAoCIgY2DGEhBl5kN1RF")));
            VActivityManagerService.get().sendBroadcastAsUser(intent, new VUserHandle(userId));
        }
    }

    private void saveAllAccounts() {
        File accountFile = VEnvironment.getAccountConfigFile();
        Parcel dest = Parcel.obtain();
        try {
            dest.writeInt(1);
            ArrayList accounts = new ArrayList();
            for (int i = 0; i < this.accountsByUserId.size(); ++i) {
                List list = (List)this.accountsByUserId.valueAt(i);
                if (list == null) continue;
                accounts.addAll(list);
            }
            dest.writeInt(accounts.size());
            for (VAccount account : accounts) {
                account.writeToParcel(dest, 0);
            }
            dest.writeLong(this.lastAccountChangeTime);
            FileOutputStream fileOutputStream = new FileOutputStream(accountFile);
            fileOutputStream.write(dest.marshall());
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        dest.recycle();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readAllAccounts() {
        File accountFile = VEnvironment.getAccountConfigFile();
        this.refreshAuthenticatorCache(null);
        if (accountFile.exists()) {
            this.accountsByUserId.clear();
            Parcel dest = Parcel.obtain();
            try {
                FileInputStream is = new FileInputStream(accountFile);
                byte[] bytes = new byte[(int)accountFile.length()];
                int readLength = is.read(bytes);
                is.close();
                if (readLength != bytes.length) {
                    throw new IOException(String.format(Locale.ENGLISH, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQdfKGgVLAZLHlE/Kj06Lmw3TCtrVgUrLS0uCksaJCpqVyMuLzoqVg==")), bytes.length, readLength));
                }
                dest.unmarshall(bytes, 0, bytes.length);
                dest.setDataPosition(0);
                dest.readInt();
                int size = dest.readInt();
                while (size-- > 0) {
                    VAccount account = new VAccount(dest);
                    VLog.d(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uP2gFAiZiICQ7Ly0qDWUjMAZ4V1Ar")) + account.type, new Object[0]);
                    ArrayList<VAccount> accounts = (ArrayList<VAccount>)this.accountsByUserId.get(account.userId);
                    if (accounts == null) {
                        accounts = new ArrayList<VAccount>();
                        this.accountsByUserId.put(account.userId, accounts);
                    }
                    accounts.add(account);
                }
                this.lastAccountChangeTime = dest.readLong();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                dest.recycle();
            }
        }
    }

    private VAccount getAccount(int userId, String accountName, String accountType) {
        List accounts = (List)this.accountsByUserId.get(userId);
        if (accounts != null) {
            for (VAccount account : accounts) {
                if (!TextUtils.equals((CharSequence)account.name, (CharSequence)accountName) || !TextUtils.equals((CharSequence)account.type, (CharSequence)accountType)) continue;
                return account;
            }
        }
        return null;
    }

    public void refreshAuthenticatorCache(String packageName) {
        this.cache.authenticators.clear();
        Intent intent = new Intent(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxoCLT42KWYKRT9hAQo9Kj4AKm8VAjVrHiw6Jz5SVg==")));
        if (packageName != null) {
            intent.setPackage(packageName);
        }
        this.generateServicesMap(VPackageManagerService.get().queryIntentServices(intent, null, 128, 0), this.cache.authenticators, new RegisteredServicesParser());
    }

    private void generateServicesMap(List<ResolveInfo> services, Map<String, AuthenticatorInfo> map, RegisteredServicesParser accountParser) {
        for (ResolveInfo info : services) {
            XmlResourceParser parser = accountParser.getParser(this.mContext, info.serviceInfo, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxoCLT42KWYKRT9hAQo9Kj4AKm8VAjVrHiw6Jz5SVg==")));
            if (parser == null) continue;
            try {
                AuthenticatorDescription desc;
                int type;
                AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)parser);
                while ((type = parser.next()) != 1 && type != 2) {
                }
                if (!StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmV107LAg2MmkjMAZqATAsKggACA==")).equals(parser.getName()) || (desc = VAccountManagerService.parseAuthenticatorDescription(accountParser.getResources(this.mContext, info.serviceInfo.applicationInfo), info.serviceInfo.packageName, attributeSet)) == null) continue;
                map.put(desc.type, new AuthenticatorInfo(desc, info.serviceInfo));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(value=26)
    private boolean removeAccountVisibility(int userId, Account account) {
        List list = (List)this.accountsVisibilitiesByUserId.get(userId);
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                VAccountVisibility vAccountVisibility = (VAccountVisibility)it.next();
                if (userId != vAccountVisibility.userId || !TextUtils.equals((CharSequence)vAccountVisibility.name, (CharSequence)account.name) || !TextUtils.equals((CharSequence)account.type, (CharSequence)vAccountVisibility.type)) continue;
                it.remove();
                this.saveAllAccountVisibilities();
                this.sendAccountsChangedBroadcast(userId);
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @TargetApi(value=26)
    private boolean renameAccountVisibility(int userId, Account account, String name) {
        SparseArray<List<VAccountVisibility>> sparseArray = this.accountsVisibilitiesByUserId;
        synchronized (sparseArray) {
            VAccountVisibility accountVisibility = this.getAccountVisibility(userId, account);
            if (accountVisibility != null) {
                accountVisibility.name = name;
                this.saveAllAccountVisibilities();
                this.sendAccountsChangedBroadcast(userId);
                return true;
            }
            return false;
        }
    }

    @Override
    @TargetApi(value=26)
    public Map<String, Integer> getPackagesAndVisibilityForAccount(int userId, Account account) {
        VAccountVisibility accountVisibility = this.getAccountVisibility(userId, account);
        if (accountVisibility != null) {
            return accountVisibility.visibility;
        }
        return null;
    }

    @Override
    @TargetApi(value=26)
    public boolean addAccountExplicitlyWithVisibility(int userId, Account account, String password, Bundle extras, Map visibility) {
        if (account == null) {
            throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmVyQzIykmDmUjOCQ=")));
        }
        boolean insertAccountIntoDatabase = this.insertAccountIntoDatabase(userId, account, password, extras);
        this.insertAccountVisibilityIntoDatabase(userId, account, visibility);
        return insertAccountIntoDatabase;
    }

    @Override
    @TargetApi(value=26)
    public boolean setAccountVisibility(int userId, Account account, String packageName, int newVisibility) {
        VAccountVisibility accountVisibility = this.getAccountVisibility(userId, account);
        if (accountVisibility == null) {
            return false;
        }
        accountVisibility.visibility.put(packageName, newVisibility);
        this.saveAllAccountVisibilities();
        this.sendAccountsChangedBroadcast(userId);
        return true;
    }

    @Override
    @TargetApi(value=26)
    public int getAccountVisibility(int userId, Account account, String packageName) {
        VAccountVisibility accountVisibility = this.getAccountVisibility(userId, account);
        if (accountVisibility == null || !accountVisibility.visibility.containsKey(packageName)) {
            return 0;
        }
        return accountVisibility.visibility.get(packageName);
    }

    @Override
    @TargetApi(value=26)
    public Map<Account, Integer> getAccountsAndVisibilityForPackage(int userId, String packageName, String accountType) {
        HashMap<Account, Integer> hashMap = new HashMap<Account, Integer>();
        for (Account account : this.getAccountList(userId, accountType)) {
            VAccountVisibility accountVisibility = this.getAccountVisibility(userId, account);
            if (accountVisibility == null || !accountVisibility.visibility.containsKey(packageName)) continue;
            hashMap.put(account, accountVisibility.visibility.get(packageName));
        }
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @TargetApi(value=26)
    private boolean insertAccountVisibilityIntoDatabase(int userId, Account account, Map<String, Integer> map) {
        if (account == null) {
            return false;
        }
        SparseArray<List<VAccountVisibility>> sparseArray = this.accountsVisibilitiesByUserId;
        synchronized (sparseArray) {
            VAccountVisibility vAccountVisibility = new VAccountVisibility(userId, account, map);
            ArrayList<VAccountVisibility> list = (ArrayList<VAccountVisibility>)this.accountsVisibilitiesByUserId.get(userId);
            if (list == null) {
                list = new ArrayList<VAccountVisibility>();
                this.accountsVisibilitiesByUserId.put(userId, list);
            }
            list.add(vAccountVisibility);
            this.saveAllAccountVisibilities();
            this.sendAccountsChangedBroadcast(vAccountVisibility.userId);
        }
        return true;
    }

    @TargetApi(value=26)
    private void saveAllAccountVisibilities() {
        File accountVisibilityConfigFile = VEnvironment.getAccountVisibilityConfigFile();
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInt(1);
            obtain.writeInt(this.accountsVisibilitiesByUserId.size());
            for (int i = 0; i < this.accountsVisibilitiesByUserId.size(); ++i) {
                obtain.writeInt(i);
                List list = (List)this.accountsVisibilitiesByUserId.valueAt(i);
                if (list == null) {
                    obtain.writeInt(0);
                    continue;
                }
                obtain.writeInt(list.size());
                for (VAccountVisibility writeToParcel : list) {
                    writeToParcel.writeToParcel(obtain, 0);
                }
            }
            obtain.writeLong(this.lastAccountChangeTime);
            FileOutputStream fileOutputStream = new FileOutputStream(accountVisibilityConfigFile);
            fileOutputStream.write(obtain.marshall());
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        obtain.recycle();
    }

    @TargetApi(value=26)
    private void readAllAccountVisibilities() {
        File accountVisibilityConfigFile = VEnvironment.getAccountVisibilityConfigFile();
        Parcel dest = Parcel.obtain();
        if (accountVisibilityConfigFile.exists()) {
            try {
                FileInputStream is = new FileInputStream(accountVisibilityConfigFile);
                byte[] bytes = new byte[(int)accountVisibilityConfigFile.length()];
                int readLength = is.read(bytes);
                is.close();
                if (readLength != bytes.length) {
                    throw new IOException(String.format(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQdfKGgVLAZLHlE/Kj06Lmw3TCtrVgUrLS0uCksaJCpqVyMuLzoqVg==")), bytes.length, readLength));
                }
                dest.unmarshall(bytes, 0, bytes.length);
                dest.setDataPosition(0);
                int version = dest.readInt();
                int userCount = dest.readInt();
                for (int i = 0; i < userCount; ++i) {
                    int userId = dest.readInt();
                    int count = dest.readInt();
                    ArrayList<VAccountVisibility> list = new ArrayList<VAccountVisibility>();
                    this.accountsVisibilitiesByUserId.put(userId, list);
                    for (int j = 0; j < count; ++j) {
                        list.add(new VAccountVisibility(dest));
                    }
                }
                this.lastAccountChangeTime = dest.readLong();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        dest.recycle();
    }

    @TargetApi(value=26)
    private VAccountVisibility getAccountVisibility(int i, String name, String type) {
        List list = (List)this.accountsVisibilitiesByUserId.get(i);
        if (list != null) {
            for (VAccountVisibility vAccountVisibility : list) {
                if (!TextUtils.equals((CharSequence)vAccountVisibility.name, (CharSequence)name) || !TextUtils.equals((CharSequence)vAccountVisibility.type, (CharSequence)type)) continue;
                return vAccountVisibility;
            }
        }
        return null;
    }

    @TargetApi(value=26)
    private VAccountVisibility getAccountVisibility(int userId, Account account) {
        return this.getAccountVisibility(userId, account.name, account.type);
    }

    @Override
    public void startAddAccountSession(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        throw new RuntimeException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0qI2snJFo=")));
    }

    @Override
    public void startUpdateCredentialsSession(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        throw new RuntimeException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0qI2snJFo=")));
    }

    @Override
    public void registerAccountListener(String[] accountTypes) throws RemoteException {
    }

    @Override
    public void unregisterAccountListener(String[] accountTypes) throws RemoteException {
    }

    @Override
    public void finishSessionAsUser(IAccountManagerResponse response, Bundle sessionBundle, boolean expectActivityLaunch, Bundle appInfo, int userId) throws RemoteException {
        throw new RuntimeException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0qI2snJFo=")));
    }

    @Override
    public void isCredentialsUpdateSuggested(IAccountManagerResponse response, Account account, String statusToken) throws RemoteException {
        throw new RuntimeException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0qI2snJFo=")));
    }

    public AccountAndUser[] getAllAccounts() {
        ArrayList<AccountAndUser> list = new ArrayList<AccountAndUser>();
        for (int i = 0; i < this.accountsByUserId.size(); ++i) {
            List accounts = (List)this.accountsByUserId.valueAt(i);
            for (VAccount account : accounts) {
                list.add(new AccountAndUser(new Account(account.name, account.type), account.userId));
            }
        }
        return list.toArray(new AccountAndUser[0]);
    }

    private class GetAccountsByTypeAndFeatureSession
    extends Session {
        private final String[] mFeatures;
        private volatile Account[] mAccountsOfType;
        private volatile ArrayList<Account> mAccountsWithFeatures;
        private volatile int mCurrentAccount;

        public GetAccountsByTypeAndFeatureSession(IAccountManagerResponse response, int userId, AuthenticatorInfo info, String[] features) {
            super(response, userId, info, false, true, null);
            this.mAccountsOfType = null;
            this.mAccountsWithFeatures = null;
            this.mCurrentAccount = 0;
            this.mFeatures = features;
        }

        @Override
        public void run() throws RemoteException {
            this.mAccountsOfType = VAccountManagerService.this.getAccounts(this.mUserId, this.mAuthenticatorInfo.desc.type);
            this.mAccountsWithFeatures = new ArrayList(this.mAccountsOfType.length);
            this.mCurrentAccount = 0;
            this.checkAccount();
        }

        public void checkAccount() {
            if (this.mCurrentAccount >= this.mAccountsOfType.length) {
                this.sendResult();
                return;
            }
            IAccountAuthenticator accountAuthenticator = this.mAuthenticator;
            if (accountAuthenticator == null) {
                Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li5fM2szQRF9JCg1LAcYLnhSTTdoNwY5KggYKmIkODZuASw8Ki4uKngaLAVqJzA0DRg+LHkaOCRpI1E5Iyo6DmozBi1iAS88Ly1fDm8VGilvESgvPQcqKUsVFituCiAqIy0cLGsFBiBlETAoIBcYOXxTPFo=")) + this.toDebugString()));
                return;
            }
            try {
                accountAuthenticator.hasFeatures(this, this.mAccountsOfType[this.mCurrentAccount], this.mFeatures);
            }
            catch (RemoteException e) {
                this.onError(1, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowMCtLHjAaLy0MKmUzLCVlN1RF")));
            }
        }

        @Override
        public void onResult(Bundle result) {
            ++this.mNumResults;
            if (result == null) {
                this.onError(5, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoJICpmDlkwKhcMVg==")));
                return;
            }
            if (result.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), false)) {
                this.mAccountsWithFeatures.add(this.mAccountsOfType[this.mCurrentAccount]);
            }
            ++this.mCurrentAccount;
            this.checkAccount();
        }

        public void sendResult() {
            IAccountManagerResponse response = this.getResponseAndClose();
            if (response != null) {
                try {
                    Account[] accounts = new Account[this.mAccountsWithFeatures.size()];
                    for (int i = 0; i < accounts.length; ++i) {
                        accounts[i] = this.mAccountsWithFeatures.get(i);
                    }
                    if (Log.isLoggable((String)TAG, (int)2)) {
                        Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YAmkgAgVlHi8ZM186KWA0ODVuASw5KQgqD2sJIFo=")) + response));
                    }
                    Bundle result = new Bundle();
                    result.putParcelableArray(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmEShF")), (Parcelable[])accounts);
                    response.onResult(result);
                }
                catch (RemoteException e) {
                    Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoKNARiCiQtKRccCGknTSZlJCwaLi0YI2AwJyNlNAo8LD4uKmwjNFo=")), (Throwable)e);
                }
            }
        }

        @Override
        protected String toDebugString(long now) {
            return super.toDebugString(now) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186PWgaMBF9JCg1LAcYLmoLRT9nHh47LhY+KmIYICBpATA+LBgAD3VSIFo=")) + (this.mFeatures != null ? TextUtils.join((CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MxhSVg==")), (Object[])this.mFeatures) : null);
        }
    }

    private abstract class Session
    extends IAccountAuthenticatorResponse.Stub
    implements IBinder.DeathRecipient,
    ServiceConnection {
        final int mUserId;
        final AuthenticatorInfo mAuthenticatorInfo;
        private final boolean mStripAuthTokenFromResult;
        public int mNumResults;
        IAccountAuthenticator mAuthenticator;
        private IAccountManagerResponse mResponse;
        private boolean mExpectActivityLaunch;
        private long mCreationTime;
        private String mAccountName;
        private boolean mAuthDetailsRequired;
        private boolean mUpdateLastAuthenticatedTime;
        private int mNumRequestContinued;
        private int mNumErrors;

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        Session(IAccountManagerResponse response, int userId, AuthenticatorInfo info, boolean expectActivityLaunch, boolean stripAuthTokenFromResult, String accountName, boolean authDetailsRequired, boolean updateLastAuthenticatedTime) {
            if (info == null) {
                throw new IllegalArgumentException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcLOmwgDShlNCgdLAhSVg==")));
            }
            this.mStripAuthTokenFromResult = stripAuthTokenFromResult;
            this.mResponse = response;
            this.mUserId = userId;
            this.mAuthenticatorInfo = info;
            this.mExpectActivityLaunch = expectActivityLaunch;
            this.mCreationTime = SystemClock.elapsedRealtime();
            this.mAccountName = accountName;
            this.mAuthDetailsRequired = authDetailsRequired;
            this.mUpdateLastAuthenticatedTime = updateLastAuthenticatedTime;
            LinkedHashMap linkedHashMap = VAccountManagerService.this.mSessions;
            synchronized (linkedHashMap) {
                VAccountManagerService.this.mSessions.put(this.toString(), this);
            }
            if (response != null) {
                try {
                    response.asBinder().linkToDeath((IBinder.DeathRecipient)this, 0);
                }
                catch (RemoteException e) {
                    this.mResponse = null;
                    this.binderDied();
                }
            }
        }

        Session(IAccountManagerResponse response, int userId, AuthenticatorInfo info, boolean expectActivityLaunch, boolean stripAuthTokenFromResult, String accountName) {
            this(response, userId, info, expectActivityLaunch, stripAuthTokenFromResult, accountName, false, false);
        }

        IAccountManagerResponse getResponseAndClose() {
            if (this.mResponse == null) {
                return null;
            }
            IAccountManagerResponse response = this.mResponse;
            this.close();
            return response;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void close() {
            LinkedHashMap linkedHashMap = VAccountManagerService.this.mSessions;
            synchronized (linkedHashMap) {
                if (VAccountManagerService.this.mSessions.remove(this.toString()) == null) {
                    return;
                }
            }
            if (this.mResponse != null) {
                this.mResponse.asBinder().unlinkToDeath((IBinder.DeathRecipient)this, 0);
                this.mResponse = null;
            }
            this.unbind();
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            this.mAuthenticator = IAccountAuthenticator.Stub.asInterface(service);
            try {
                this.run();
            }
            catch (RemoteException e) {
                this.onError(1, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowMCtLHjAaLy0MKmUzLCVlN1RF")));
            }
        }

        @Override
        public void onRequestContinued() {
            ++this.mNumRequestContinued;
        }

        @Override
        public void onError(int errorCode, String errorMessage) {
            ++this.mNumErrors;
            IAccountManagerResponse response = this.getResponseAndClose();
            if (response != null) {
                Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YWmoaRSVsM1gaPQgAKksVMCBlJyAeKRcYJ3gVSFo=")) + response));
                try {
                    response.onError(errorCode, errorMessage);
                }
                catch (RemoteException e) {
                    Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uKW8zAiVgMFk1KjsMKGoVNAR+MzwqLRcuIWMVESN9NAocKQccJ2MKRTVoHjwZJQcYJXkVID5sJyQ0PhcMM28wICVgNAozKj06Vg==")), (Throwable)e);
                }
            } else {
                Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uKW8zAiVgMFk1KjsMKGoVNAR+MzwsLAcMJ30KFgZ5HiwbKQcYJ2sVSFo=")));
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            this.mAuthenticator = null;
            IAccountManagerResponse response = this.getResponseAndClose();
            if (response != null) {
                try {
                    response.onError(1, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWszGiZgNDA5LBcMPg==")));
                }
                catch (RemoteException e) {
                    Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uKW8zAiVgMFk1KjwqPWoaHi9oJyhKIxc2JWAgRSluDiw9Ly4bPngVLDNsESA/IF4iE24KTQFlNwYKKBg2M28KMC9gJFg8LC0AMW8zBShsNyg6KQgAKmIaGiluJ1RF")), (Throwable)e);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onResult(Bundle result) throws RemoteException {
            IAccountManagerResponse response;
            ++this.mNumResults;
            if (result != null) {
                boolean needUpdate;
                boolean isSuccessfulConfirmCreds = result.getBoolean(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4AD2oFNDdgNSw/Iy4MCGUzSFo=")), false);
                boolean isSuccessfulUpdateCredsOrAddAccount = result.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg=="))) && result.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")));
                boolean bl = needUpdate = this.mUpdateLastAuthenticatedTime && (isSuccessfulConfirmCreds || isSuccessfulUpdateCredsOrAddAccount);
                if (needUpdate || this.mAuthDetailsRequired) {
                    SparseArray sparseArray = VAccountManagerService.this.accountsByUserId;
                    synchronized (sparseArray) {
                        VAccount account = VAccountManagerService.this.getAccount(this.mUserId, this.mAccountName, this.mAuthenticatorInfo.desc.type);
                        if (needUpdate && account != null) {
                            account.lastAuthenticatedTime = System.currentTimeMillis();
                            VAccountManagerService.this.saveAllAccounts();
                        }
                        if (this.mAuthDetailsRequired) {
                            long lastAuthenticatedTime = -1L;
                            if (account != null) {
                                lastAuthenticatedTime = account.lastAuthenticatedTime;
                            }
                            result.putLong(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+KWwLJAVmHho/Kj42MW4FQQZrASwVIxgIJw==")), lastAuthenticatedTime);
                        }
                    }
                }
            }
            if (result == null || !TextUtils.isEmpty((CharSequence)result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2"))))) {
                // empty if block
            }
            Intent intent = null;
            if (result != null) {
                intent = (Intent)result.getParcelable(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVBgY=")));
            }
            if ((response = this.mExpectActivityLaunch && result != null && result.containsKey(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVBgY="))) ? this.mResponse : this.getResponseAndClose()) != null) {
                try {
                    if (result == null) {
                        Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YWmoaRSVsM1gaPQgAKksVMCBlJyAeKRcYJ3gVSFo=")) + response));
                        response.onError(5, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz0uDmoJICpmDlkwKhcLOmoVGgZvDgobLhgqVg==")));
                    } else {
                        if (this.mStripAuthTokenFromResult) {
                            result.remove(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUKMCVjJDA2")));
                        }
                        Log.v((String)TAG, (String)(this.getClass().getSimpleName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Phg2P2oFHi9gNDs8Ki0YAmkgAgVlHi8ZM186KWA0ODVuASw5KQgqD2sJIFo=")) + response));
                        if (result.getInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowFhNgJAo/")), -1) > 0 && intent == null) {
                            response.onError(result.getInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowFhNgJAo/"))), result.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowFg1iASgpLwc6PQ=="))));
                        } else {
                            response.onResult(result);
                        }
                    }
                }
                catch (RemoteException e) {
                    Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4+CWoKNARiCiQtKRccCGknTSZlJCwaLi0YI2AwJyNlNAo8LD4uKmwjNFo=")), (Throwable)e);
                }
            }
        }

        public abstract void run() throws RemoteException;

        void bind() {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcCWwFAjdmHgY2KCkmOGwjMCx4HiwcPQg+CWYaBiBsNzAiKAhbCmUgETRsDh4dJAMiVg==")) + this.mAuthenticatorInfo.desc.type));
            Intent intent = new Intent();
            intent.setAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1k7Ly0qDWUjMAZsIxoCLT42KWYKRT9hAQo9Kj4AKm8VAjVrHiw6Jz5SVg==")));
            intent.setClassName(this.mAuthenticatorInfo.serviceInfo.packageName, this.mAuthenticatorInfo.serviceInfo.name);
            if (!VActivityManager.get().bindService(VAccountManagerService.this.mContext, intent, this, 1, this.mUserId)) {
                Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4YCGgJIDdmEQo/KggmLn4zHjdqAQIgLgQ6ImAjMyM=")) + this.toDebugString()));
                this.onError(1, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4YCGgJIC59DgYoLAguPQ==")));
            }
        }

        protected String toDebugString() {
            return this.toDebugString(SystemClock.elapsedRealtime());
        }

        protected String toDebugString(long now) {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uKW8zAiVgMwU8KAgAKmkjAgZ9ETgwLC42LEsVSFo=")) + this.mExpectActivityLaunch + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186OWozBiZiDiggKAc1Og==")) + (this.mAuthenticator != null) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KWwFJAZhICc0")) + this.mNumResults + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")) + this.mNumRequestContinued + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")) + this.mNumErrors + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAQDOGoFAi5iAQozKgcLOg==")) + (double)(now - this.mCreationTime) / 1000.0;
        }

        private void unbind() {
            if (this.mAuthenticator != null) {
                this.mAuthenticator = null;
                VActivityManager.get().unbindService(VAccountManagerService.this.mContext, this);
            }
        }

        public void binderDied() {
            this.mResponse = null;
            this.close();
        }
    }

    private final class AuthenticatorCache {
        final Map<String, AuthenticatorInfo> authenticators = new HashMap<String, AuthenticatorInfo>();

        private AuthenticatorCache() {
        }
    }

    private final class AuthenticatorInfo {
        final AuthenticatorDescription desc;
        final ServiceInfo serviceInfo;

        AuthenticatorInfo(AuthenticatorDescription desc, ServiceInfo info) {
            this.desc = desc;
            this.serviceInfo = info;
        }
    }

    static final class AuthTokenRecord {
        public int userId;
        public Account account;
        public long expiryEpochMillis;
        public String authToken;
        private String authTokenType;
        private String packageName;

        AuthTokenRecord(int userId, Account account, String authTokenType, String packageName, String authToken, long expiryEpochMillis) {
            this.userId = userId;
            this.account = account;
            this.authTokenType = authTokenType;
            this.packageName = packageName;
            this.authToken = authToken;
            this.expiryEpochMillis = expiryEpochMillis;
        }

        AuthTokenRecord(int userId, Account account, String authTokenType, String packageName) {
            this.userId = userId;
            this.account = account;
            this.authTokenType = authTokenType;
            this.packageName = packageName;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            AuthTokenRecord that = (AuthTokenRecord)o;
            return this.userId == that.userId && this.account.equals((Object)that.account) && this.authTokenType.equals(that.authTokenType) && this.packageName.equals(that.packageName);
        }

        public int hashCode() {
            return ((this.userId * 31 + this.account.hashCode()) * 31 + this.authTokenType.hashCode()) * 31 + this.packageName.hashCode();
        }
    }
}

