/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Handler
 *  android.os.IBinder
 */
package com.carlos.science.client;

import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import com.carlos.libcommon.StringFog;
import com.carlos.science.IVirtualController;
import com.carlos.science.client.ClientActivityLifecycle;
import com.carlos.science.stebcore.IStep;
import com.carlos.science.stebcore.IStepInfo;
import com.carlos.science.stebcore.StepControllerImpl;
import com.carlos.science.stebcore.StepImpl;
import com.kook.common.utils.HVLog;
import com.kook.controller.server.IServerController;

public class FeaturesStepController
extends StepControllerImpl<StepImpl> {
    private int mContainerId = 0;

    public FeaturesStepController(ClientActivityLifecycle clientActivityLifecycle, IBinder iBinder) {
        super(clientActivityLifecycle, iBinder);
    }

    public void setContainerId(int containerId) {
        this.mContainerId = containerId;
    }

    @Override
    public void initSteps() {
    }

    @Override
    public Activity getCurrentActivity() {
        if (this.clientActivityLifecycle == null) {
            HVLog.d(StringFog.decrypt("UyMXFxEbLRYQPAYVGSwBHREAGQkCOgFDCBcEKhocAQAcAiQNKxoVBgYJSQwCGgAcAiQNKxoVBgYJJQYIFgYLFQkLfxoQTxwFBQNO"));
        }
        return this.clientActivityLifecycle.getCurrentActivity();
    }

    @Override
    public Handler getHandler() {
        return this.clientActivityLifecycle.getHandler();
    }

    @Override
    public IBinder getCallBackIBinder() {
        HVLog.d(StringFog.decrypt("UyMXFxEbLRYQPAYVGSwBHREAGQkCOgFDCBcEKg4CHycTFQ4nHRoNCxcCSQwPHwkwFwYFFjEKARYVG1U=") + this.callBackIBinder);
        return this.callBackIBinder;
    }

    @Override
    public IServerController getIServerController() {
        return this.clientActivityLifecycle.getIServerController();
    }

    @Override
    public IVirtualController getVirtualControllerImpl() {
        return this.clientActivityLifecycle.getVirtualControllerImpl();
    }

    public void doTask(IStepInfo iStepInfo) {
        IStepInfo nextStepInfo = iStepInfo;
        if (iStepInfo != null) {
            StepImpl currentStep;
            this.mCurrentStep = currentStep = (StepImpl)nextStepInfo.getStepImpl();
            currentStep.doTask();
            if (!currentStep.finishStep) {
                HVLog.d(StringFog.decrypt("IBEXBiYBMQcRAB4cDB0nHhUe"), StringFog.decrypt("lerikcHUsM/5hu7wgcnvlvnakMjLttnHT1U=") + currentStep.getTitle() + StringFog.decrypt("VEWU/8KG/v+Gwf6W4f+L4+uaxuaJy9sFBhwZGgdGWoPv04LVzJX+8JTdzIbE1w=="));
            }
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            HVLog.i(StringFog.decrypt("IBEXBiYBMQcRAB4cDB0nHhUe"), StringFog.decrypt("Fh0RExUaNhwNTxdK") + e.toString());
        }
    }

    @Override
    public IStep getCurrentStep() {
        return this.mCurrentStep;
    }

    @Override
    public void fastForward() {
        HVLog.i(StringFog.decrypt("IBEXBiYBMQcRAB4cDB0nHhUe"), StringFog.decrypt("lcjXn8/Ku8jth83pgOjiltnyk8LlufrEh9P8"));
        IStepInfo topStep = this.getTopStepInfo();
        this.doTask(topStep);
    }
}

