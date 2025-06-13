/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package com.carlos.science.client;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.carlos.libcommon.StringFog;
import com.carlos.science.IVirtualController;
import com.carlos.science.client.ClientActivityLifecycle;
import com.carlos.science.client.ClientControlerServiceCache;
import com.carlos.science.client.ForegroundCallbacks;
import com.kook.common.utils.HVLog;
import com.kook.controller.server.IServerController;

public class ClientServiceConnection
extends ForegroundCallbacks
implements ServiceConnection {
    String TAG = StringFog.decrypt("MAkbEwsaDBYRGRsTDCwBHQsXFREHMB0=");
    Application mApplication;
    IServerController mIServerController;
    IVirtualController mVirtualControllerImpl;
    IBinder iBinder = null;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        public void binderDied() {
            HVLog.e(ClientServiceConnection.this.TAG, StringFog.decrypt("ldbak+Piu8vji8raj8LVl9/Tkt7NuOPliu7YgdD3muL+"));
        }
    };
    ForegroundCallbacks.Listener mListener = new ForegroundCallbacks.Listener(){

        @Override
        public void onBecameForeground(Activity activity) {
            if (ClientServiceConnection.this.mVirtualControllerImpl.getController() == null) {
                return;
            }
            if (ClientServiceConnection.this.mVirtualControllerImpl.getController() != null && !ClientServiceConnection.this.mVirtualControllerImpl.getController().needShow()) {
                return;
            }
            String packageName = activity.getPackageName();
            HVLog.d(ClientServiceConnection.this.TAG, StringFog.decrypt("l978k/XguvzTiunujOfeluz/k+retu/jh9Txj+bJm8T+kf/qtvPYh8zhSQ==") + ClientServiceConnection.this.getClientIBinder(packageName));
            try {
                IBinder service = ClientServiceConnection.this.getService(packageName);
                if (ClientServiceConnection.this.mIServerController != null && service != null) {
                    ClientServiceConnection.this.mIServerController.show(activity.getPackageName(), service);
                } else {
                    HVLog.e(StringFog.decrypt("HAswEwYPMhYlAAAVDh0BBgsWVggnDBYRGRcCKgAABxcdGgAcfxoQTxwfHU8HHQwG"));
                }
            }
            catch (RemoteException e) {
                HVLog.printThrowable(e);
            }
        }

        @Override
        public void onBecameBackground() {
            try {
                HVLog.d(ClientServiceConnection.this.TAG, StringFog.decrypt("ltjhk+zjuNvoisj/jOfplejQk+3euuPtiv3A"));
                if (ClientServiceConnection.this.mIServerController != null) {
                    ClientServiceConnection.this.mIServerController.hide();
                } else {
                    HVLog.d(ClientServiceConnection.this.TAG, StringFog.decrypt("HAswEwYPMhYhDhEbDh0BBgsWVggnDBYRGRcCKgAABxcdGgAcfxoQTxwfHU8HHQwG"));
                }
            }
            catch (RemoteException e) {
                HVLog.printThrowable(e);
            }
        }
    };

    public ClientServiceConnection(Application application, IVirtualController virtualController) {
        this.mApplication = application;
        this.mVirtualControllerImpl = virtualController;
        this.mApplication.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)this);
        this.addListener(this.mListener);
    }

    public IBinder getServerIBinder() {
        return this.iBinder;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.mIServerController = IServerController.Stub.asInterface(iBinder);
            if (this.mApplication != null) {
                String packageName = this.mApplication.getPackageName();
                IBinder service = this.getService(packageName);
                this.iBinder = iBinder;
                HVLog.i(this.TAG, StringFog.decrypt("m9rrn+Lif5vc8ZT+zIvW+YHI8EVONjEKARYVG1U=") + iBinder);
                if (service instanceof ClientActivityLifecycle) {
                    ClientActivityLifecycle clientActivityLifecycle = (ClientActivityLifecycle)service;
                    if (this.mIServerController != null) {
                        clientActivityLifecycle.setIServerController(this.mApplication, this.mIServerController, this.mVirtualControllerImpl);
                    }
                }
                if (service != null) {
                    service.linkToDeath(this.mDeathRecipient, 0);
                    if (this.mVirtualControllerImpl.getController() == null) {
                        return;
                    }
                    if (this.mVirtualControllerImpl.getController() != null && !this.mVirtualControllerImpl.getController().needShow()) {
                        return;
                    }
                    this.mIServerController.setClientApplication(packageName, this.getClientIBinder(packageName));
                }
            } else {
                HVLog.d(StringFog.decrypt("HiQCBgkHPBIXBh0eSQYdUwsHGgk="));
            }
        }
        catch (RemoteException e) {
            HVLog.printException((Exception)((Object)e));
        }
    }

    public IBinder getService(String packageName) {
        IBinder service = ClientControlerServiceCache.getService(packageName);
        return service;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        HVLog.i(this.TAG, StringFog.decrypt("m9rrn+Lif5vc8ZT+zIvW3oPk24HU2Q=="));
    }

    @Override
    public void onActivityResumed(Activity activity) {
        String packageName;
        IBinder service;
        super.onActivityResumed(activity);
        if (this.mVirtualControllerImpl != null) {
            this.mVirtualControllerImpl.controllerActivityResume(activity);
        }
        if ((service = this.getClientIBinder(packageName = activity.getPackageName())) instanceof ClientActivityLifecycle) {
            ClientActivityLifecycle clientActivityLifecycle = (ClientActivityLifecycle)service;
            clientActivityLifecycle.onActivityResumed(activity);
            if (this.mIServerController != null) {
                clientActivityLifecycle.setIServerController(this.mApplication, this.mIServerController, this.mVirtualControllerImpl);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        super.onActivityPaused(activity);
        String packageName = activity.getPackageName();
        IBinder service = this.getService(packageName);
        if (service instanceof ClientActivityLifecycle) {
            ClientActivityLifecycle clientActivityLifecycle = (ClientActivityLifecycle)service;
            clientActivityLifecycle.onActivityPaused(activity);
        }
    }

    public IBinder getClientIBinder(String packageName) {
        IBinder service = this.getService(packageName);
        return service;
    }
}

