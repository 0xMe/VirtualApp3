//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lody.virtual.client.hook.proxies.location;

import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Build.VERSION;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.env.VirtualGPSSatalines;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.remote.vloc.VLocation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import mirror.android.location.GpsStatusL;
import mirror.android.location.LocationManager.GnssStatusListenerTransport;
import mirror.android.location.LocationManager.GnssStatusListenerTransportO;
import mirror.android.location.LocationManager.GpsStatusListenerTransport;
import mirror.android.location.LocationManager.GpsStatusListenerTransportOPPO_R815T;
import mirror.android.location.LocationManager.GpsStatusListenerTransportSumsungS5;
import mirror.android.location.LocationManager.GpsStatusListenerTransportVIVO;

public class MockLocationHelper {
    public MockLocationHelper() {
    }

    public static void invokeNmeaReceived(Object listener) {
        if (listener != null) {
            VirtualGPSSatalines satalines = VirtualGPSSatalines.get();

            try {
                VLocation location = VLocationManager.get().getCurAppLocation();
                if (location != null) {
                    String date = (new SimpleDateFormat(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JBZfDWoaLAN3NSgP")), Locale.US)).format(new Date());
                    String lat = getGPSLat(location.getLatitude());
                    String lon = getGPSLat(location.getLongitude());
                    String latNW = getNorthWest(location);
                    String lonSE = getSouthEast(location);
                    String $GPGGA = checksum(String.format(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmAxPBFOVzApOl4ML383GgN1Vig6PAQuD05SPzd+AS8bM141CHVSASR/DQISCF5eP3VTQV5/Myc7")), date, lat, latNW, lon, lonSE, satalines.getSvCount()));
                    String $GPRMC = checksum(String.format(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmchEhNOVzApOhUhCHkgDSR7DjMdPhc1KEgFNzd1V1w5OTleMnxTPyN8MwU7CBUlOw==")), date, lat, latNW, lon, lonSE));
                    if (GnssStatusListenerTransport.onNmeaReceived != null) {
                        GnssStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmA2LFNOViMoMwNaKnU3OwF8MwU7Ol8DDUwJBTd1IzsbPCkfKH9TJwJ/ClgiCF8lPHxSOy1/MBEvM14LLn8nGQNPV1ApMwNaLXVSOwF/VycdOV4XKEk3JzY="))});
                        GnssStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), $GPGGA});
                        GnssStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmQmMBdOVicoJF5aKn8xPyR8VgJAPAM5KGskTQ58VwE7My5SVg=="))});
                        GnssStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), $GPRMC});
                        GnssStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmA2LBFOHCMoMzlaKXpSOwF/DQU8P18DD08OTDd8V1wbOTohKHVTOyR8DQUiCQQpO3sJBSB/Mz8bOS5SVg=="))});
                    } else if (GpsStatusListenerTransport.onNmeaReceived != null) {
                        GpsStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmA2LFNOViMoMwNaKnU3OwF8MwU7Ol8DDUwJBTd1IzsbPCkfKH9TJwJ/ClgiCF8lPHxSOy1/MBEvM14LLn8nGQNPV1ApMwNaLXVSOwF/VycdOV4XKEk3JzY="))});
                        GpsStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), $GPGGA});
                        GpsStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmQmMBdOVicoJF5aKn8xPyR8VgJAPAM5KGskTQ58VwE7My5SVg=="))});
                        GpsStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), $GPRMC});
                        GpsStatusListenerTransport.onNmeaReceived.call(listener, new Object[]{System.currentTimeMillis(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRYmAmA2LBFOHCMoMzlaKXpSOwF/DQU8P18DD08OTDd8V1wbOTohKHVTOyR8DQUiCQQpO3sJBSB/Mz8bOS5SVg=="))});
                    }
                }
            } catch (Throwable var10) {
                var10.printStackTrace();
            }
        }

    }

    public static void fakeGpsStatusN(LocationManager locationManager) {
        if (mirror.android.location.LocationManager.mGpsStatusListeners != null) {
            Map mGpsStatusListeners = (Map)mirror.android.location.LocationManager.mGpsStatusListeners.get(locationManager);
            Iterator var2 = mGpsStatusListeners.values().iterator();
            if (var2.hasNext()) {
                Object listenerTransport = var2.next();
                invokeSvStatusChanged(listenerTransport);
            }

        }
    }

    public static void fakeGpsStatus(LocationManager locationManager) {
        if (VERSION.SDK_INT >= 24) {
            fakeGpsStatusN(locationManager);
        } else {
            GpsStatus mGpsStatus = null;

            try {
                mGpsStatus = (GpsStatus)Reflect.on(locationManager).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwYmKG82LAZ9AQovIy5SVg==")));
            } catch (Throwable var13) {
            }

            if (mGpsStatus != null) {
                VirtualGPSSatalines satalines = VirtualGPSSatalines.get();
                int svCount = satalines.getSvCount();
                float[] snrs = satalines.getSnrs();
                int[] prns = satalines.getPrns();
                float[] elevations = satalines.getElevations();
                float[] azimuths = satalines.getAzimuths();

                try {
                    int length;
                    int i;
                    if (GpsStatusL.setStatus != null) {
                        svCount = satalines.getSvCount();
                        length = satalines.getPrns().length;
                        elevations = satalines.getElevations();
                        azimuths = satalines.getAzimuths();
                        int[] ephemerisMask = new int[length];

                        for(i = 0; i < length; ++i) {
                            ephemerisMask[i] = satalines.getEphemerisMask();
                        }

                        int[] almanacMask = new int[length];

                        for(int i2 = 0; i2 < length; ++i2) {
                            almanacMask[i] = satalines.getAlmanacMask();
                        }

                        int[] usedInFixMask = new int[length];

                        for(int i3 = 0; i3 < length; ++i3) {
                            usedInFixMask[i3] = satalines.getUsedInFixMask();
                        }

                        GpsStatusL.setStatus.call(mGpsStatus, new Object[]{svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask});
                    } else if (mirror.android.location.GpsStatus.setStatus != null) {
                        length = satalines.getEphemerisMask();
                        int almanacMask = satalines.getAlmanacMask();
                        i = satalines.getUsedInFixMask();
                        mirror.android.location.GpsStatus.setStatus.call(mGpsStatus, new Object[]{svCount, prns, snrs, elevations, azimuths, length, almanacMask, i});
                    }
                } catch (Exception var14) {
                }

            }
        }
    }

    public static void invokeSvStatusChanged(Object transport) {
        if (transport != null) {
            VirtualGPSSatalines satalines = VirtualGPSSatalines.get();

            try {
                Class<?> aClass = transport.getClass();
                int svCount;
                float[] snrs;
                float[] elevations;
                float[] azimuths;
                int[] prns;
                if (aClass == GnssStatusListenerTransport.TYPE) {
                    svCount = satalines.getSvCount();
                    prns = satalines.getPrnWithFlags();
                    snrs = satalines.getSnrs();
                    elevations = satalines.getElevations();
                    azimuths = satalines.getAzimuths();
                    if (BuildCompat.isOreo()) {
                        float[] carrierFreqs = satalines.getCarrierFreqs();

                        try {
                            GnssStatusListenerTransportO.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths, carrierFreqs});
                        } catch (NullPointerException var16) {
                        }
                    } else {
                        GnssStatusListenerTransport.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths});
                    }
                } else if (aClass == GpsStatusListenerTransport.TYPE) {
                    svCount = satalines.getSvCount();
                    prns = satalines.getPrns();
                    snrs = satalines.getSnrs();
                    elevations = satalines.getElevations();
                    azimuths = satalines.getAzimuths();
                    int ephemerisMask = satalines.getEphemerisMask();
                    int almanacMask = satalines.getAlmanacMask();
                    int usedInFixMask = satalines.getUsedInFixMask();
                    if (GpsStatusListenerTransport.onSvStatusChanged != null) {
                        GpsStatusListenerTransport.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask});
                    } else if (GpsStatusListenerTransportVIVO.onSvStatusChanged != null) {
                        GpsStatusListenerTransportVIVO.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask, new long[svCount]});
                    } else if (GpsStatusListenerTransportSumsungS5.onSvStatusChanged != null) {
                        GpsStatusListenerTransportSumsungS5.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask, new int[svCount]});
                    } else if (GpsStatusListenerTransportOPPO_R815T.onSvStatusChanged != null) {
                        int len = prns.length;
                        int[] ephemerisMasks = new int[len];

                        for(int i = 0; i < len; ++i) {
                            ephemerisMasks[i] = satalines.getEphemerisMask();
                        }

                        int[] almanacMasks = new int[len];

                        for(int i = 0; i < len; ++i) {
                            almanacMasks[i] = satalines.getAlmanacMask();
                        }

                        int[] usedInFixMasks = new int[len];

                        for(int i = 0; i < len; ++i) {
                            usedInFixMasks[i] = satalines.getUsedInFixMask();
                        }

                        GpsStatusListenerTransportOPPO_R815T.onSvStatusChanged.call(transport, new Object[]{svCount, prns, snrs, elevations, azimuths, ephemerisMasks, almanacMasks, usedInFixMasks, svCount});
                    }
                }
            } catch (Throwable var17) {
                var17.printStackTrace();
            }
        }

    }

    private static String getSouthEast(VLocation location) {
        return location.getLongitude() > 0.0 ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQhSVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS5SVg=="));
    }

    private static String getNorthWest(VLocation location) {
        return location.getLatitude() > 0.0 ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz5SVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5SVg=="));
    }

    public static String getGPSLat(double v) {
        int du = (int)v;
        double fen = (v - (double)du) * 60.0;
        return du + leftZeroPad((int)fen, 2) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OD5SVg==")) + String.valueOf(fen).substring(2);
    }

    private static String leftZeroPad(int num, int size) {
        return leftZeroPad(String.valueOf(num), size);
    }

    private static String leftZeroPad(String num, int size) {
        StringBuilder sb = new StringBuilder(size);
        int i;
        if (num == null) {
            for(i = 0; i < size; ++i) {
                sb.append('0');
            }
        } else {
            for(i = 0; i < size - num.length(); ++i) {
                sb.append('0');
            }

            sb.append(num);
        }

        return sb.toString();
    }

    public static String checksum(String nema) {
        String checkStr = nema;
        if (nema.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRhSVg==")))) {
            checkStr = nema.substring(1);
        }

        int sum = 0;

        for(int i = 0; i < checkStr.length(); ++i) {
            sum ^= (byte)checkStr.charAt(i);
        }

        return nema + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PD5SVg==")) + String.format(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PQM5KmEFSFo=")), sum).toLowerCase();
    }
}
