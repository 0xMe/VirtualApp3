/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.util.ArrayMap
 */
package com.lody.virtual.client.hook.proxies.location;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.util.ArrayMap;
import com.lody.virtual.client.hook.proxies.location.MockLocationHelper;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.vloc.VLocation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import mirror.android.location.LocationManager;

class GPSListenerThread
extends TimerTask {
    private static GPSListenerThread INSTANCE = new GPSListenerThread();
    private Handler handler = new Handler();
    private boolean isRunning = false;
    private HashMap<Object, Long> listeners = new HashMap();
    private Timer timer = new Timer();

    private void notifyGPSStatus(Map listeners) {
        if (listeners != null && !listeners.isEmpty()) {
            Set entries = listeners.entrySet();
            for (Map.Entry entry : entries) {
                try {
                    Object value = entry.getValue();
                    if (value == null) continue;
                    MockLocationHelper.invokeSvStatusChanged(value);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void notifyLocation(Map listeners) {
        if (listeners != null) {
            try {
                VLocation vLocation;
                if (!listeners.isEmpty() && (vLocation = VirtualLocationManager.get().getLocation()) != null) {
                    Location location = vLocation.toSysLocation();
                    Set entries = listeners.entrySet();
                    for (Map.Entry entry : entries) {
                        Object value = entry.getValue();
                        if (value == null) continue;
                        try {
                            LocationManager.ListenerTransport.onLocationChanged.call(value, location);
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyMNmeaListener(Map listeners) {
        if (listeners != null && !listeners.isEmpty()) {
            Set entries = listeners.entrySet();
            for (Map.Entry entry : entries) {
                try {
                    Object value = entry.getValue();
                    if (value == null) continue;
                    MockLocationHelper.invokeNmeaReceived(value);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addListenerTransport(Object transport) {
        this.listeners.put(transport, System.currentTimeMillis());
        if (!this.isRunning) {
            GPSListenerThread gPSListenerThread = this;
            synchronized (gPSListenerThread) {
                if (!this.isRunning) {
                    this.isRunning = true;
                    this.timer.schedule((TimerTask)this, 1000L, 1000L);
                }
            }
        }
    }

    public void removeListenerTransport(Object transport) {
        if (transport != null) {
            this.listeners.remove(transport);
        }
    }

    @Override
    public void run() {
        if (!this.listeners.isEmpty()) {
            if (VirtualLocationManager.get().getMode() == 0) {
                this.listeners.clear();
                return;
            }
            for (Map.Entry<Object, Long> entry : this.listeners.entrySet()) {
                try {
                    Map gpsStatusListeners;
                    Object transport = entry.getKey();
                    if (Build.VERSION.SDK_INT >= 24) {
                        Map nmeaListeners = LocationManager.mGnssNmeaListeners.get(transport);
                        this.notifyGPSStatus(LocationManager.mGnssStatusListeners.get(transport));
                        this.notifyMNmeaListener(nmeaListeners);
                        gpsStatusListeners = LocationManager.mGpsStatusListeners.get(transport);
                        if (gpsStatusListeners instanceof HashMap) {
                            this.notifyGPSStatus((HashMap)gpsStatusListeners);
                        } else if (gpsStatusListeners instanceof ArrayMap) {
                            this.notifyGPSStatus((Map)((ArrayMap)gpsStatusListeners));
                        }
                        this.notifyMNmeaListener(LocationManager.mGpsNmeaListeners.get(transport));
                    } else {
                        gpsStatusListeners = LocationManager.mGpsStatusListeners.get(transport);
                        if (gpsStatusListeners instanceof HashMap) {
                            this.notifyGPSStatus((HashMap)gpsStatusListeners);
                        } else if (gpsStatusListeners instanceof ArrayMap) {
                            this.notifyGPSStatus((Map)((ArrayMap)gpsStatusListeners));
                        }
                        this.notifyMNmeaListener(LocationManager.mNmeaListeners.get(transport));
                    }
                    final Map listeners = LocationManager.mListeners.get(transport);
                    if (gpsStatusListeners == null || gpsStatusListeners.isEmpty()) continue;
                    if (listeners == null || listeners.isEmpty()) {
                        this.handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                GPSListenerThread.this.notifyLocation(listeners);
                            }
                        }, 100L);
                        continue;
                    }
                    this.notifyLocation(listeners);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        this.timer.cancel();
    }

    public static GPSListenerThread get() {
        return INSTANCE;
    }

    private GPSListenerThread() {
    }
}

