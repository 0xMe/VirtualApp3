/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.RemoteException
 *  androidx.annotation.RequiresApi
 *  org.jdeferred.Promise
 */
package com.carlos.science.server;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import androidx.annotation.RequiresApi;
import com.carlos.libcommon.StringFog;
import com.carlos.science.server.FloatWindowServices;
import com.carlos.science.tab.TabChild;
import com.carlos.science.tab.TabContainerFactory;
import com.kook.common.utils.HVLog;
import com.kook.controller.server.IServerController;
import java.util.List;
import org.jdeferred.Promise;

public class ServerController
extends IServerController.Stub {
    String TAG = StringFog.decrypt("IAAAAAAcHBwNGwAfBQoc");
    public static FloatWindowServices floatWindowServices;
    public static int cpid;
    private boolean isOpened = false;
    public final int SERVER_CONTROLLER_METHOD_SHOW = 3;
    public final int SERVER_CONTROLLER_METHOD_HIDE = 4;
    public final int SERVER_CONTROLLER_METHOD_EMULATOR_CLICK = 5;
    public final int SERVER_CONTROLLER_METHOD_EMULATOR_TOUCH = 6;
    public final int SERVER_CONTROLLER_METHOD_KEYEVENT = 7;
    private Handler handler = new Handler(){

        public void dispatchMessage(Message msg) {
            HVLog.i(ServerController.this.TAG, StringFog.decrypt("IAAAAAAcHBwNGwAfBQMLAUUaFwsKMxYRT1I=") + msg.what);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 3: {
                    String pkgName = bundle.getString(StringFog.decrypt("Aw4VOAQDOg=="));
                    IBinder iBinder = bundle.getBinder(StringFog.decrypt("GicbGAELLQ=="));
                    ServerController.getServices().show(pkgName, iBinder);
                    break;
                }
                case 4: {
                    ServerController.getServices().hide();
                    break;
                }
                case 5: {
                    int centerX = bundle.getInt(StringFog.decrypt("EAAcAgAcBw=="));
                    int centerY = bundle.getInt(StringFog.decrypt("EAAcAgAcBg=="));
                    ServerController.getServices().emulatorClick(centerX, centerY);
                    break;
                }
                case 6: {
                    int fromX = bundle.getInt(StringFog.decrypt("FRcdGz0="));
                    int fromY = bundle.getInt(StringFog.decrypt("FRcdGzw="));
                    int toX = bundle.getInt(StringFog.decrypt("Bwoq"));
                    int toY = bundle.getInt(StringFog.decrypt("Bwor"));
                    boolean direction = bundle.getBoolean(StringFog.decrypt("FwwAEwYaNhwN"));
                    ServerController.getServices().emulatorTouch(fromX, fromY, toX, toY, direction);
                    break;
                }
                case 7: {
                    int keycode = bundle.getInt(StringFog.decrypt("GAALFQoKOg=="));
                    ServerController.getServices().emulatorKey(keycode);
                }
            }
        }
    };

    public ServerController(FloatWindowServices floatWindowServices) {
        ServerController.floatWindowServices = floatWindowServices;
    }

    public static FloatWindowServices getServices() {
        if (floatWindowServices == null) {
            throw new NullPointerException(StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUUHLFMNGh4c"));
        }
        return floatWindowServices;
    }

    @Override
    @RequiresApi(api=18)
    public void setClientApplication(String packageName, IBinder iBinder) throws RemoteException {
        List<TabChild> tabChildList = TabContainerFactory.getInstance().getTabChildListByPackageName(packageName);
        HVLog.i(this.TAG, StringFog.decrypt("MAkbEwsaHBwNGwAfBQocUxYXAiYCNhYNGzMAGQMHEAQGHwoAf1MTDhEbCAgLPQQfE18=") + packageName + StringFog.decrypt("U0VSVgwsNh0HCgBK") + iBinder);
        if (tabChildList != null && tabChildList.size() > 0) {
            Message message = this.handler.obtainMessage(3);
            Bundle bundle = new Bundle();
            bundle.putString(StringFog.decrypt("Aw4VOAQDOg=="), packageName);
            bundle.putBinder(StringFog.decrypt("GicbGAELLQ=="), iBinder);
            message.setData(bundle);
            this.handler.sendMessage(message);
        }
    }

    @Override
    public void setWidth(int width) throws RemoteException {
    }

    @Override
    public void setHeight(int height) throws RemoteException {
    }

    @Override
    public void hideFloatWindow(boolean hide) throws RemoteException {
    }

    @Override
    public void setNeedAttach(boolean needAttach) throws RemoteException {
    }

    @Override
    @RequiresApi(api=19)
    public void show(String pkgName, IBinder ibinder) throws RemoteException {
        List<String> controlerApplication = TabContainerFactory.getInstance().getControlerApplication();
        boolean isShow = controlerApplication != null && controlerApplication.contains(pkgName);
        List<TabChild> children = TabContainerFactory.getInstance().getTabChildListByPackageName(pkgName);
        HVLog.d(this.TAG, StringFog.decrypt("IAAAAAAcHBwNGwAfBQMLAUUBHgoZf0gTBBU+CAILSQ==") + pkgName + StringFog.decrypt("U0VSVgwMNh0HCgBK") + ibinder + "    " + ibinder.getInterfaceDescriptor());
        if (isShow || children.size() > 0) {
            Message message = this.handler.obtainMessage(3);
            Bundle bundle = new Bundle();
            bundle.putString(StringFog.decrypt("Aw4VOAQDOg=="), pkgName);
            bundle.putBinder(StringFog.decrypt("GicbGAELLQ=="), ibinder);
            message.setData(bundle);
            this.handler.sendMessage(message);
        } else {
            Message message = this.handler.obtainMessage(4);
            this.handler.sendMessage(message);
        }
    }

    @Override
    public void hide() throws RemoteException {
        HVLog.d(this.TAG, StringFog.decrypt("IAAAAAAcHBwNGwAfBQMLAUUaHwELf5bZ+5rfzIn23IDI4oL695br6Jf42Yr+/YD9xoDg5JfZ6Q=="));
        Message message = this.handler.obtainMessage(4);
        this.handler.sendMessage(message);
    }

    public Promise<Void, Throwable, Void> hideControllerContainer() {
        return ServerController.getServices().hideControllerContainer();
    }

    public Promise<Void, Throwable, Void> showControllerContainer() {
        return ServerController.getServices().showControllerContainer();
    }

    @Override
    public boolean virtualClick(int centerX, int centerY) throws RemoteException {
        Message message = this.handler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putInt(StringFog.decrypt("EAAcAgAcBw=="), centerX);
        bundle.putInt(StringFog.decrypt("EAAcAgAcBg=="), centerY);
        message.setData(bundle);
        this.handler.sendMessage(message);
        return true;
    }

    @Override
    public boolean virtualTouch(int fromX, int fromY, int toX, int toY, boolean direction) throws RemoteException {
        HVLog.i(this.TAG, StringFog.decrypt("BQwAAhAPMycMGhEYSQsHAQARAgwBMUk=") + direction);
        Message message = this.handler.obtainMessage(6);
        Bundle bundle = new Bundle();
        bundle.putInt(StringFog.decrypt("FRcdGz0="), fromX);
        bundle.putInt(StringFog.decrypt("FRcdGzw="), fromY);
        bundle.putInt(StringFog.decrypt("Bwoq"), toX);
        bundle.putInt(StringFog.decrypt("Bwor"), toY);
        bundle.putBoolean(StringFog.decrypt("FwwAEwYaNhwN"), direction);
        message.setData(bundle);
        this.handler.sendMessage(message);
        return true;
    }

    @Override
    public boolean sendKeyEvent(int keycode) throws RemoteException {
        HVLog.i(this.TAG, StringFog.decrypt("AAAcEi4LJjYVChwESQQLCgYdEgBU") + keycode);
        Message message = this.handler.obtainMessage(7);
        Bundle bundle = new Bundle();
        bundle.putInt(StringFog.decrypt("GAALFQoKOg=="), keycode);
        message.setData(bundle);
        this.handler.sendMessage(message);
        return true;
    }
}

