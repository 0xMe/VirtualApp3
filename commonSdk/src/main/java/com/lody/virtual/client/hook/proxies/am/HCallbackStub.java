/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.lody.virtual.client.hook.proxies.am;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.proxies.app.ActivityClientControllerStub;
import com.lody.virtual.client.interfaces.IInjector;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.AvoidRecursive;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.ShadowActivityInfo;
import java.util.List;
import mirror.android.app.ActivityClient;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.ActivityThread;
import mirror.android.app.ClientTransactionHandler;
import mirror.android.app.IActivityManager;
import mirror.android.app.servertransaction.ClientTransaction;
import mirror.android.app.servertransaction.ClientTransactionItem;
import mirror.android.app.servertransaction.LaunchActivityItem;
import mirror.android.app.servertransaction.TopResumedActivityChangeItem;
import mirror.android.os.Handler;

public class HCallbackStub
implements Handler.Callback,
IInjector {
    private static final int LAUNCH_ACTIVITY;
    private static final int EXECUTE_TRANSACTION;
    private static final int SCHEDULE_CRASH;
    private static final String TAG;
    private static final HCallbackStub sCallback;
    private final AvoidRecursive mAvoidRecurisve = new AvoidRecursive();
    private Handler.Callback otherCallback;

    private HCallbackStub() {
    }

    public static HCallbackStub getDefault() {
        return sCallback;
    }

    private static android.os.Handler getH() {
        return ActivityThread.mH.get(VirtualCore.mainThread());
    }

    private static Handler.Callback getHCallback() {
        try {
            android.os.Handler handler = HCallbackStub.getH();
            return Handler.mCallback.get(handler);
        }
        catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean handleMessage(Message msg) {
        if (this.mAvoidRecurisve.beginCall()) {
            try {
                if (LAUNCH_ACTIVITY == msg.what) {
                    if (!this.handleLaunchActivity(msg, msg.obj)) {
                        boolean bl = true;
                        return bl;
                    }
                } else if (BuildCompat.isPie() && EXECUTE_TRANSACTION == msg.what) {
                    if (!this.handleExecuteTransaction(msg)) {
                        boolean bl = true;
                        return bl;
                    }
                } else if (SCHEDULE_CRASH == msg.what) {
                    String crashReason = (String)msg.obj;
                    new RemoteException(crashReason).printStackTrace();
                    boolean bl = false;
                    return bl;
                }
                if (this.otherCallback != null) {
                    boolean bl = this.otherCallback.handleMessage(msg);
                    return bl;
                }
            }
            finally {
                this.mAvoidRecurisve.finishCall();
            }
        }
        return false;
    }

    private boolean handleExecuteTransaction(Message msg) {
        Object transaction = msg.obj;
        IBinder token = this.getTokenByClientTransaction(transaction);
        Object r = ClientTransactionHandler.getActivityClient.call(VirtualCore.mainThread(), token);
        List<Object> activityCallbacks = ClientTransaction.mActivityCallbacks.get(transaction);
        if (activityCallbacks == null || activityCallbacks.isEmpty()) {
            return true;
        }
        Object item = activityCallbacks.get(0);
        if (item == null) {
            return true;
        }
        if (r == null) {
            if (item.getClass() != LaunchActivityItem.TYPE) {
                return true;
            }
            return this.handleLaunchActivity(msg, item);
        }
        if (BuildCompat.isQ() && TopResumedActivityChangeItem.TYPE != null && item.getClass() == TopResumedActivityChangeItem.TYPE) {
            try {
                if (TopResumedActivityChangeItem.mOnTop.get(item) == ActivityThread.ActivityClientRecord.isTopResumedActivity.get(r)) {
                    Log.e((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jgg2LGUaOC9mERk8LBdfKn4wTSVsJx4/IxgAKksaPDdlNAoqLz01JGwjNCB7Diw6DRcYJWIaRSZ7J1RF")) + TopResumedActivityChangeItem.mOnTop.get(item)));
                    return false;
                }
            }
            catch (Throwable e) {
                return false;
            }
        }
        return true;
    }

    private IBinder getTokenByClientTransaction(Object transaction) {
        IBinder token = null;
        if (ClientTransaction.mActivityToken == null) {
            List<Object> items;
            if (ClientTransaction.getTransactionItems != null && (items = ClientTransaction.getTransactionItems.call(transaction, new Object[0])) != null && !items.isEmpty()) {
                Object item = items.get(0);
                token = ClientTransactionItem.getActivityToken.call(item, new Object[0]);
            }
            if (token == null) {
                Object mLifecycleStateRequest = ClientTransaction.mLifecycleStateRequest.get(transaction);
                if (mLifecycleStateRequest == null) {
                    List<Object> activityCallbacks = ClientTransaction.mActivityCallbacks.get(transaction);
                    if (activityCallbacks != null && !activityCallbacks.isEmpty()) {
                        return ClientTransactionItem.getActivityToken.call(activityCallbacks.get(0), new Object[0]);
                    }
                    return token;
                }
                return ClientTransactionItem.getActivityToken.call(mLifecycleStateRequest, new Object[0]);
            }
            return token;
        }
        return ClientTransaction.mActivityToken.get(transaction);
    }

    private boolean handleLaunchActivity(Message msg, Object r) {
        Intent stubIntent = BuildCompat.isPie() ? LaunchActivityItem.mIntent.get(r) : ActivityThread.ActivityClientRecord.intent.get(r);
        ShadowActivityInfo saveInstance = new ShadowActivityInfo(stubIntent);
        if (saveInstance.intent == null) {
            return true;
        }
        Intent intent = saveInstance.intent;
        IBinder token = BuildCompat.isPie() ? ClientTransaction.mActivityToken.get(msg.obj) : this.getTokenByClientTransaction(msg.obj);
        ActivityInfo info = saveInstance.info;
        if (info == null) {
            return true;
        }
        if (VClient.get().getClientConfig() == null) {
            InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(info.packageName, 0);
            if (installedAppInfo == null) {
                return true;
            }
            VActivityManager.get().processRestarted(info.packageName, info.processName, saveInstance.userId);
            HCallbackStub.getH().sendMessageAtFrontOfQueue(Message.obtain((Message)msg));
            return false;
        }
        VClient.get().bindApplication(info.packageName, info.processName);
        int taskId = IActivityManager.getTaskForActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), token, false);
        VActivityManager.get().onActivityCreate(saveInstance.virtualToken, token, taskId);
        ClassLoader appClassLoader = VClient.get().getClassLoader(info.applicationInfo);
        ComponentUtils.unpackFillIn(intent, appClassLoader);
        if (BuildCompat.isPie()) {
            IInterface activityClientController;
            Object activityClientRecord;
            if (BuildCompat.isS() && ActivityThread.getLaunchingActivity != null && (activityClientRecord = ActivityThread.getLaunchingActivity.call(VirtualCore.mainThread(), token)) != null) {
                Object compatInfo = ActivityThread.ActivityClientRecord.compatInfo.get(activityClientRecord);
                Object loadedApk = ActivityThread.getPackageInfoNoCheck.call(VirtualCore.mainThread(), info.applicationInfo, compatInfo);
                ActivityThread.ActivityClientRecord.intent.set(activityClientRecord, intent);
                ActivityThread.ActivityClientRecord.activityInfo.set(activityClientRecord, info);
                ActivityThread.ActivityClientRecord.packageInfo.set(activityClientRecord, loadedApk);
            }
            if (BuildCompat.isS() && LaunchActivityItem.mActivityClientController != null && (activityClientController = LaunchActivityItem.mActivityClientController.get(r)) != null) {
                ActivityClient.ActivityClientControllerSingleton.mKnownInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), ActivityClientControllerStub.getProxyInterface());
            }
            LaunchActivityItem.mIntent.set(r, intent);
            LaunchActivityItem.mInfo.set(r, info);
        } else {
            ActivityThread.ActivityClientRecord.intent.set(r, intent);
            ActivityThread.ActivityClientRecord.activityInfo.set(r, info);
        }
        return true;
    }

    @Override
    public void inject() {
        this.otherCallback = HCallbackStub.getHCallback();
        Handler.mCallback.set(HCallbackStub.getH(), this);
    }

    @Override
    public boolean isEnvBad() {
        boolean envBad;
        Handler.Callback callback = HCallbackStub.getHCallback();
        boolean bl = envBad = callback != this;
        if (callback != null && envBad) {
            VLog.d(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBY2P2oFHip9DigxPxcAOWoJTSpoAS8dPQgACmMaLDV5HiwqKT4iJmgFLD17CgE3")) + callback, new Object[0]);
        }
        return envBad;
    }

    static {
        SCHEDULE_CRASH = ActivityThread.H.SCHEDULE_CRASH.get();
        LAUNCH_ACTIVITY = BuildCompat.isPie() ? -1 : ActivityThread.H.LAUNCH_ACTIVITY.get();
        EXECUTE_TRANSACTION = BuildCompat.isPie() ? ActivityThread.H.EXECUTE_TRANSACTION.get() : -1;
        TAG = HCallbackStub.class.getSimpleName();
        sCallback = new HCallbackStub();
    }
}

