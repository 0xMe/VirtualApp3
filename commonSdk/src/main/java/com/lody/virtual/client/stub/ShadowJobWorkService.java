/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Service
 *  android.app.job.JobParameters
 *  android.app.job.JobScheduler
 *  android.app.job.JobWorkItem
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.lody.virtual.client.stub;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.IJobCallback;
import android.app.job.IJobService;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.InvocationStubManager;
import com.lody.virtual.client.hook.proxies.am.ActivityManagerStub;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.helper.compat.JobWorkItemCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.job.VJobSchedulerService;
import java.util.Map;

@TargetApi(value=21)
public class ShadowJobWorkService
extends Service {
    private static final boolean debug = true;
    private static final String TAG = ShadowJobWorkService.class.getSimpleName();
    private final SparseArray<JobSession> mJobSessions = new SparseArray();
    private JobScheduler mScheduler;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUVGiZONyggLwguLmQVNCo=")).equals(action)) {
                JobParameters jobParams = (JobParameters)intent.getParcelableExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOmcFJAR9Dl0p")));
                this.startJob(jobParams);
            } else if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUVGiZONyggKi4mXW8FRVo=")).equals(action)) {
                JobParameters jobParams = (JobParameters)intent.getParcelableExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOmcFJAR9Dl0p")));
                this.stopJob(jobParams);
            }
        }
        return 2;
    }

    public static void startJob(Context context, JobParameters jobParams) {
        Intent intent = new Intent(context, ShadowJobWorkService.class);
        intent.setAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUVGiZONyggLwguLmQVNCo=")));
        intent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOmcFJAR9Dl0p")), (Parcelable)jobParams);
        context.startService(intent);
    }

    public static void stopJob(Context context, JobParameters jobParams) {
        Intent intent = new Intent(context, ShadowJobWorkService.class);
        intent.setAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUVGiZONyggKi4mXW8FRVo=")));
        intent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOmcFJAR9Dl0p")), (Parcelable)jobParams);
        context.startService(intent);
    }

    private void emptyCallback(IJobCallback callback, int jobId) {
        try {
            callback.acknowledgeStartMessage(jobId, false);
            callback.jobFinished(jobId, false);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onCreate() {
        super.onCreate();
        InvocationStubManager.getInstance().checkEnv(ActivityManagerStub.class);
        this.mScheduler = (JobScheduler)this.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LD4AOm8zLCBiDgovKhcMKA==")));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onDestroy() {
        VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEcLCwqJ2EjFjVsJxpF")), new Object[0]);
        SparseArray<JobSession> sparseArray = this.mJobSessions;
        synchronized (sparseArray) {
            for (int i = this.mJobSessions.size() - 1; i >= 0; --i) {
                JobSession session = this.mJobSessions.valueAt(i);
                session.stopSessionLocked();
            }
            this.mJobSessions.clear();
        }
        super.onDestroy();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startJob(JobParameters jobParams) {
        int jobId = jobParams.getJobId();
        IBinder binder = mirror.android.app.job.JobParameters.callback.get(jobParams);
        IJobCallback callback = IJobCallback.Stub.asInterface(binder);
        Map.Entry<VJobSchedulerService.JobId, VJobSchedulerService.JobConfig> entry = VJobSchedulerService.get().findJobByVirtualJobId(jobId);
        if (entry == null) {
            this.emptyCallback(callback, jobId);
            this.mScheduler.cancel(jobId);
        } else {
            JobSession session;
            VJobSchedulerService.JobId key = entry.getKey();
            VJobSchedulerService.JobConfig config = entry.getValue();
            SparseArray<JobSession> sparseArray = this.mJobSessions;
            synchronized (sparseArray) {
                session = this.mJobSessions.get(jobId);
            }
            if (session != null) {
                session.startJob(true);
            } else {
                boolean bound = false;
                SparseArray<JobSession> sparseArray2 = this.mJobSessions;
                synchronized (sparseArray2) {
                    mirror.android.app.job.JobParameters.jobId.set(jobParams, key.clientJobId);
                    session = new JobSession(jobId, callback, jobParams, key.packageName);
                    mirror.android.app.job.JobParameters.callback.set(jobParams, session.asBinder());
                    this.mJobSessions.put(jobId, session);
                    Intent service = new Intent();
                    service.setComponent(new ComponentName(key.packageName, config.serviceName));
                    try {
                        VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEpIxgcUmIFMDFvDiwuPl8AD3VSID5qNwoWJF9aLGUjSFo=")), service.getComponent(), jobId);
                        bound = VActivityManager.get().bindService((Context)this, service, session, 5, VUserHandle.getUserId(key.vuid));
                    }
                    catch (Throwable e) {
                        VLog.e(TAG, e);
                    }
                }
                if (!bound) {
                    sparseArray2 = this.mJobSessions;
                    synchronized (sparseArray2) {
                        this.mJobSessions.remove(jobId);
                    }
                    this.emptyCallback(callback, jobId);
                    this.mScheduler.cancel(jobId);
                    VJobSchedulerService.get().cancel(-1, jobId);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stopJob(JobParameters jobParams) {
        int jobId = jobParams.getJobId();
        SparseArray<JobSession> sparseArray = this.mJobSessions;
        synchronized (sparseArray) {
            JobSession session = this.mJobSessions.get(jobId);
            if (session != null) {
                VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0qD28LTSV9MwU/KBhSVg==")), jobId);
                session.stopSessionLocked();
            }
        }
    }

    private final class JobSession
    extends IJobCallback.Stub
    implements ServiceConnection {
        private int jobId;
        private IJobCallback clientCallback;
        private JobParameters jobParams;
        private IJobService clientJobService;
        private boolean isWorking;
        private String packageName;
        private JobWorkItem lastWorkItem;

        JobSession(int jobId, IJobCallback clientCallback, JobParameters jobParams, String packageName) {
            this.jobId = jobId;
            this.clientCallback = clientCallback;
            this.jobParams = jobParams;
            this.packageName = packageName;
        }

        @Override
        public void acknowledgeStartMessage(int jobId, boolean ongoing) throws RemoteException {
            this.isWorking = true;
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEsLT5bKmAjJDduDjAgLysYCmgKFiBiESgcJy0mLm4JHTFpN1RF")), this.jobId);
            this.clientCallback.acknowledgeStartMessage(this.jobId, ongoing);
        }

        @Override
        public void acknowledgeStopMessage(int jobId, boolean reschedule) throws RemoteException {
            this.isWorking = false;
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEsLT5bKmAjJDduDjAgLysYCmUgIEhoHjAcOwc+LHs0FjI=")), this.jobId);
            this.clientCallback.acknowledgeStopMessage(this.jobId, reschedule);
        }

        @Override
        public void jobFinished(int jobId, boolean reschedule) throws RemoteException {
            this.isWorking = false;
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEhLD4MBGMKRSxlJAYuLzk5J2sVSFo=")), this.jobId);
            this.clientCallback.jobFinished(this.jobId, reschedule);
        }

        @Override
        public boolean completeWork(int jobId, int workId) throws RemoteException {
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEqLD4IDmAaLD9uDzweLBg9PnsFMFo=")), this.jobId);
            return this.clientCallback.completeWork(this.jobId, workId);
        }

        @Override
        public JobWorkItem dequeueWork(int jobId) throws RemoteException {
            try {
                this.lastWorkItem = null;
                VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEvLhc+CWIFLCBiJFk7KgM5J2sVSFo=")), this.jobId);
                JobWorkItem workItem = this.clientCallback.dequeueWork(this.jobId);
                if (workItem != null) {
                    this.lastWorkItem = JobWorkItemCompat.parse(workItem);
                    return this.lastWorkItem;
                }
                this.isWorking = false;
                this.stopSessionLocked();
            }
            catch (Exception e) {
                e.printStackTrace();
                VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEvLhc+CWIFLCBiJFk7KgM6Vg==")) + e, new Object[0]);
            }
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void startJob(boolean wait) {
            if (this.isWorking) {
                VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClE6Kgg+CGYYHippMwEuLzoiJm8KMzRlHjM3IC0YOW8gBgJpAVRF")), this.jobId);
                return;
            }
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClE6Kgg+CGYYHippMwEuLz5SVg==")), this.jobId);
            if (this.clientJobService == null) {
                if (!wait) {
                    ShadowJobWorkService.this.emptyCallback(this.clientCallback, this.jobId);
                    SparseArray sparseArray = ShadowJobWorkService.this.mJobSessions;
                    synchronized (sparseArray) {
                        this.stopSessionLocked();
                    }
                }
                return;
            }
            try {
                this.clientJobService.startJob(this.jobParams);
            }
            catch (RemoteException e) {
                this.forceFinishJob();
                Log.e((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClE6Kgg+CGYYHippN1RF")), (Throwable)e);
            }
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClEcLCs2J2EzICxpJAoCKQgqKmsFLCBoES8tDgguVg==")), name);
            this.clientJobService = IJobService.Stub.asInterface(service);
            this.startJob(false);
        }

        public void onServiceDisconnected(ComponentName name) {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void forceFinishJob() {
            try {
                this.clientCallback.jobFinished(this.jobId, false);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
            finally {
                SparseArray sparseArray = ShadowJobWorkService.this.mJobSessions;
                synchronized (sparseArray) {
                    this.stopSessionLocked();
                }
            }
        }

        void stopSessionLocked() {
            VLog.i(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fP2gFGj1rNB46Oy0MKGUVLClrClE6KggADmkgLDZlJBoeKV45J2sVSFo=")), this.jobId);
            if (this.clientJobService != null) {
                try {
                    this.clientJobService.stopJob(this.jobParams);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            ShadowJobWorkService.this.mJobSessions.remove(this.jobId);
            VActivityManager.get().unbindService((Context)ShadowJobWorkService.this, this);
        }
    }
}

