/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.telephony.CellIdentityCdma
 *  android.telephony.CellIdentityGsm
 *  android.telephony.CellInfo
 *  android.telephony.CellInfoCdma
 *  android.telephony.CellInfoGsm
 *  android.telephony.CellSignalStrengthCdma
 *  android.telephony.CellSignalStrengthGsm
 *  android.telephony.NeighboringCellInfo
 *  android.telephony.cdma.CdmaCellLocation
 *  android.telephony.gsm.GsmCellLocation
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.proxies.telephony;

import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.NeighboringCellInfo;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.hook.annotations.SkipInject;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.remote.vloc.VCell;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import mirror.android.telephony.CellIdentityGsm;

class MethodProxies {
    MethodProxies() {
    }

    private static Bundle getCellLocationInternal(VCell cell) {
        if (cell != null) {
            Bundle cellData = new Bundle();
            if (cell.type == 2) {
                try {
                    CdmaCellLocation cellLoc = new CdmaCellLocation();
                    cellLoc.setCellLocationData(cell.baseStationId, Integer.MAX_VALUE, Integer.MAX_VALUE, cell.systemId, cell.networkId);
                    cellLoc.fillInNotifierBundle(cellData);
                }
                catch (Throwable e) {
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4+KWgYLAZ9AQozKi0YXmkzSFo=")), cell.baseStationId);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4+KWgYLAZ9AQozKi0YU24gBi9vHigvLhhSVg==")), Integer.MAX_VALUE);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4+KWgYLAZ9AQozKi0YU28FMC1qDiwwLgguVg==")), Integer.MAX_VALUE);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YKWwFNCNrDgpF")), cell.systemId);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4uLGwzGgRjIgYw")), cell.networkId);
                }
            } else {
                try {
                    GsmCellLocation cellLoc = new GsmCellLocation();
                    cellLoc.setLacAndCid(cell.lac, cell.cid);
                    cellLoc.fillInNotifierBundle(cellData);
                }
                catch (Throwable e) {
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+OQ==")), cell.lac);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4YPA==")), cell.cid);
                    cellData.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khc2OQ==")), cell.psc);
                }
            }
            return cellData;
        }
        return null;
    }

    private static CellInfo createCellInfo(VCell cell) {
        if (cell.type == 2) {
            CellInfoCdma cdma = mirror.android.telephony.CellInfoCdma.ctor.newInstance();
            CellIdentityCdma identityCdma = mirror.android.telephony.CellInfoCdma.mCellIdentityCdma.get(cdma);
            CellSignalStrengthCdma strengthCdma = mirror.android.telephony.CellInfoCdma.mCellSignalStrengthCdma.get(cdma);
            mirror.android.telephony.CellIdentityCdma.mNetworkId.set(identityCdma, cell.networkId);
            mirror.android.telephony.CellIdentityCdma.mSystemId.set(identityCdma, cell.systemId);
            mirror.android.telephony.CellIdentityCdma.mBasestationId.set(identityCdma, cell.baseStationId);
            mirror.android.telephony.CellSignalStrengthCdma.mCdmaDbm.set(strengthCdma, -74);
            mirror.android.telephony.CellSignalStrengthCdma.mCdmaEcio.set(strengthCdma, -91);
            mirror.android.telephony.CellSignalStrengthCdma.mEvdoDbm.set(strengthCdma, -64);
            mirror.android.telephony.CellSignalStrengthCdma.mEvdoSnr.set(strengthCdma, 7);
            return cdma;
        }
        CellInfoGsm gsm = mirror.android.telephony.CellInfoGsm.ctor.newInstance();
        android.telephony.CellIdentityGsm identityGsm = mirror.android.telephony.CellInfoGsm.mCellIdentityGsm.get(gsm);
        CellSignalStrengthGsm strengthGsm = mirror.android.telephony.CellInfoGsm.mCellSignalStrengthGsm.get(gsm);
        CellIdentityGsm.mMcc.set(identityGsm, cell.mcc);
        CellIdentityGsm.mMnc.set(identityGsm, cell.mnc);
        CellIdentityGsm.mLac.set(identityGsm, cell.lac);
        CellIdentityGsm.mCid.set(identityGsm, cell.cid);
        mirror.android.telephony.CellSignalStrengthGsm.mSignalStrength.set(strengthGsm, 20);
        mirror.android.telephony.CellSignalStrengthGsm.mBitErrorRate.set(strengthGsm, 0);
        return gsm;
    }

    @SkipInject
    static class GetNeighboringCellInfo
    extends ReplaceCallingPkgMethodProxy {
        public GetNeighboringCellInfo() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIjNC9iJBo6Ki4uMW8VEhNrAQIdOxgcImAjSFo=")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (GetNeighboringCellInfo.isFakeLocationEnable()) {
                List<VCell> cells = VirtualLocationManager.get().getNeighboringCell(GetNeighboringCellInfo.getAppUserId(), GetNeighboringCellInfo.getAppPkg());
                if (cells != null) {
                    ArrayList<NeighboringCellInfo> infos = new ArrayList<NeighboringCellInfo>();
                    for (VCell cell : cells) {
                        NeighboringCellInfo info = new NeighboringCellInfo();
                        mirror.android.telephony.NeighboringCellInfo.mLac.set(info, cell.lac);
                        mirror.android.telephony.NeighboringCellInfo.mCid.set(info, cell.cid);
                        mirror.android.telephony.NeighboringCellInfo.mRssi.set(info, 6);
                        infos.add(info);
                    }
                    return infos;
                }
                return null;
            }
            return super.call(who, method, args);
        }
    }

    @SkipInject
    static class GetAllCellInfo
    extends ReplaceCallingPkgMethodProxy {
        public GetAllCellInfo() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVHiRlJDAoKhUcDmkVNFo=")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (GetAllCellInfo.isFakeLocationEnable() && !GetAllCellInfo.getAppPkg().equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCRjDiw7Lz0hDm4jMCxsNwYaLgQcCGMKQSBqEVRF")))) {
                List<VCell> cells = VirtualLocationManager.get().getAllCell(GetAllCellInfo.getAppUserId(), GetAllCellInfo.getAppPkg());
                if (cells != null) {
                    ArrayList<CellInfo> result = new ArrayList<CellInfo>();
                    for (VCell cell : cells) {
                        result.add(MethodProxies.createCellInfo(cell));
                    }
                    return result;
                }
                return null;
            }
            return super.call(who, method, args);
        }
    }

    static class GetAllCellInfoUsingSubId
    extends ReplaceCallingPkgMethodProxy {
        public GetAllCellInfoUsingSubId() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVHiRlJDAoKhUcDmkVNFBsJx4bLjs2CX02Gi8=")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (GetAllCellInfoUsingSubId.isFakeLocationEnable()) {
                return null;
            }
            return super.call(who, method, args);
        }
    }

    @SkipInject
    static class GetCellLocation
    extends ReplaceCallingPkgMethodProxy {
        public GetCellLocation() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMzNCRgHFE1Ly0iLmwjNCY=")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (GetCellLocation.isFakeLocationEnable()) {
                VCell cell = VirtualLocationManager.get().getCell(GetCellLocation.getAppUserId(), GetCellLocation.getAppPkg());
                if (cell != null) {
                    return MethodProxies.getCellLocationInternal(cell);
                }
                return null;
            }
            return super.call(who, method, args);
        }
    }

    static class GetMeidForSlot
    extends GetDeviceId {
        GetMeidForSlot() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIVNC9iHDw1IzwqCG8KBlo="));
        }
    }

    static class GetImeiForSlot
    extends GetDeviceId {
        GetImeiForSlot() {
        }

        @Override
        public String getMethodName() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VEitjDDw1IzwqCG8KBlo="));
        }
    }

    static class GetDeviceId
    extends ReplaceLastPkgMethodProxy {
        public GetDeviceId() {
            super(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAFND5jDig/IQc2Vg==")));
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            String imei;
            VDeviceConfig config = GetDeviceId.getDeviceConfig();
            if (config.enable && !TextUtils.isEmpty((CharSequence)(imei = config.deviceId))) {
                return imei;
            }
            if (config != null && !TextUtils.isEmpty((CharSequence)(imei = config.deviceId))) {
                return imei;
            }
            return super.call(who, method, args);
        }
    }
}

