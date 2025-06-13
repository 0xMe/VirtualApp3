/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.view.LayoutInflater
 *  androidx.annotation.RequiresApi
 *  org.jdeferred.Promise
 */
package com.carlos.science.server;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import androidx.annotation.RequiresApi;
import com.carlos.common.utils.ResponseProgram;
import com.carlos.libcommon.StringFog;
import com.carlos.science.FloatBallManager;
import com.carlos.science.floatball.FloatBallCfg;
import com.carlos.science.menu.FloatMenuCfg;
import com.carlos.science.server.EventService;
import com.carlos.science.server.ServerController;
import com.carlos.science.tab.FloatTab;
import com.carlos.science.tab.TabChild;
import com.carlos.science.tab.TabContainerFactory;
import com.carlos.science.utils.BackGroudSeletor;
import com.carlos.science.utils.DensityUtil;
import com.kook.common.utils.HVLog;
import java.util.List;
import org.jdeferred.Promise;

public class FloatWindowServices
extends EventService {
    public static final String TAG = StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBQ==");
    ServerController serverController;
    private FloatBallManager mFloatballManager;
    LayoutInflater layoutInflater;
    private final BroadcastReceiver homeListenerReceiver = new BroadcastReceiver(){
        final String SYSTEM_DIALOG_REASON_KEY = StringFog.decrypt("AQATBQoA");
        final String SYSTEM_DIALOG_REASON_HOME_KEY = StringFog.decrypt("GwofEw4LJg==");

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            HVLog.e(StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBQ=="), StringFog.decrypt("EgYGHwoAZQ==") + action);
            FloatWindowServices.this.hide();
        }
    };

    @Override
    @RequiresApi(api=16)
    public void onCreate() {
        super.onCreate();
        HVLog.i(TAG, StringFog.decrypt("lefekNDAuNn0T5f49IrJ+ID+4A=="));
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager)this.getSystemService(StringFog.decrypt("HQoGHwMHPBIXBh0e"));
            NotificationChannel channel = new NotificationChannel(TAG, (CharSequence)TAG, 3);
            notificationManager.createNotificationChannel(channel);
        }
        this.layoutInflater = LayoutInflater.from((Context)this);
        this.serverController = new ServerController(this);
        this.init();
        TabContainerFactory.getInstance().initTag(this.layoutInflater, this.mFloatballManager);
        IntentFilter homeFilter = new IntentFilter(StringFog.decrypt("EgsWBAoHO10KAQYVBxtAEgYGHwoAcTAvICE1Njw3IDE3OzoqFjIvIDUj"));
        homeFilter.addAction(StringFog.decrypt("EAofWA4BMBhNDhEEAAAA"));
        this.registerReceiver(this.homeListenerReceiver, homeFilter);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean closeFloat = false;
        boolean showFloat = false;
        if (intent != null) {
            closeFloat = intent.getBooleanExtra(StringFog.decrypt("EAkdBQAoMxwCGw=="), false);
            showFloat = intent.getBooleanExtra(StringFog.decrypt("AA0dASMCMBIX"), false);
        }
        HVLog.d(TAG, StringFog.decrypt("HAshAgQcKzAMAh8RBwtO") + this.getPackageName() + StringFog.decrypt("U0URGgodOjUPABMESVVO") + closeFloat + StringFog.decrypt("U0VSVhYGMAQlAx0RHVU=") + showFloat);
        if (closeFloat) {
            this.hide();
        }
        if (showFloat) {
            // empty if block
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        int ballSize = DensityUtil.dip2px((Context)this, 45.0f);
        Drawable ballIcon = BackGroudSeletor.getdrawble(StringFog.decrypt("GgYtEAkBPgcBDh4c"), (Context)this);
        HVLog.d(TAG, StringFog.decrypt("ltjhk+zjuu/lis/SgeDylujnk8HJusPsTxARBQM9Gh8Xmdn0") + ballSize + "    " + ballIcon);
        FloatBallCfg ballCfg = new FloatBallCfg(ballSize, ballIcon, FloatBallCfg.Gravity.RIGHT_CENTER);
        ballCfg.setHideHalfLater(false);
        int menuSize = DensityUtil.dip2px((Context)this, 180.0f);
        int menuItemSize = DensityUtil.dip2px((Context)this, 40.0f);
        FloatMenuCfg menuCfg = new FloatMenuCfg(menuSize, menuItemSize);
        this.mFloatballManager = new FloatBallManager(this.getApplicationContext(), this.serverController, ballCfg, menuCfg);
    }

    public void show(String packageName, IBinder binder) {
        try {
            List<TabChild> tabChildren = TabContainerFactory.getInstance().getTabChildListByPackageName(packageName);
            HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOLBsMGElQCwYAFwAATA==") + binder + StringFog.decrypt("U0VSVhUPPBgCCBc+CAILSQ==") + packageName);
            if (tabChildren == null) {
                HVLog.i(TAG, StringFog.decrypt("lt/mkfHGZQ==") + packageName + StringFog.decrypt("lfnYkOzQuvvThu7wgcnvlvXdk+/GuOnnh/3sjOL7"));
                return;
            }
            this.mFloatballManager.addTabChilds(tabChildren).buildTabChild();
            if (!this.mFloatballManager.isShowing()) {
                FloatTab floatTab = this.mFloatballManager.getFloatTab();
                floatTab.setClientBinder(binder);
                floatTab.setPackageName(packageName);
                floatTab.setFocusableInTouchMode(true);
                this.mFloatballManager.show();
            }
        }
        catch (Exception e) {
            HVLog.printException(TAG, e);
        }
    }

    public void hide() {
        try {
            HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOfxsKCxc="));
            if (this.mFloatballManager.isShowing()) {
                this.mFloatballManager.hide();
            }
        }
        catch (Exception e) {
            HVLog.e(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUUdNxwUTxcICgoeBwwdGF8=") + e.toString());
        }
    }

    public Promise<Void, Throwable, Void> hideControllerContainer() {
        HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOfxsKCxczBgEaAQoeGgAcHBwNGxMZBwoc"));
        this.mFloatballManager.reset();
        return ResponseProgram.defer().when(() -> {
            while (this.mFloatballManager.getFloatTab().isAdded()) {
                this.sleep(100L);
            }
            HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOfxsKCxczBgEaAQoeGgAcHBwNGxMZBwocUwAcEg=="));
        });
    }

    public Promise<Void, Throwable, Void> showControllerContainer() {
        HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOfxsKCxczBgEaAQoeGgAcHBwNGxMZBwoc"));
        return ResponseProgram.defer().when(() -> {
            while (!this.mFloatballManager.getFloatTab().isAdded()) {
                this.sleep(100L);
            }
            HVLog.i(TAG, StringFog.decrypt("NQkdFxE5Nh0HAAUjDB0YGgYXBUVOfxsKCxczBgEaAQoeGgAcHBwNGxMZBwocUwAcEg=="));
        });
    }

    public void setPosition(int position) {
        this.getFloatTab().setPosition(position);
    }

    public FloatTab getFloatTab() {
        return null;
    }

    public IBinder onBind(Intent intent) {
        HVLog.e(TAG, StringFog.decrypt("HAswHwsKfxoNGxceHVU=") + intent.toString());
        return this.serverController.asBinder();
    }

    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        this.mFloatballManager.hide();
        HVLog.e(TAG, StringFog.decrypt("TlhPS1hTYk5eUk9NVFJTTlhPSxAAPRoNCyEVGxkHEABSVgYBMR1Z") + conn);
        this.unregisterReceiver(this.homeListenerReceiver);
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.homeListenerReceiver);
    }
}

