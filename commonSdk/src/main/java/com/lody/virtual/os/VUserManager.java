/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.lody.virtual.os;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.LocalProxyUtils;
import com.lody.virtual.client.ipc.ServiceManagerNative;
import com.lody.virtual.helper.utils.IInterfaceUtils;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.server.interfaces.IUserManager;
import java.util.List;

public class VUserManager {
    private static String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITsuKWgaFg19Dlk7KC0MKA=="));
    private IUserManager mService;
    private static final VUserManager sInstance = new VUserManager();

    private Object getRemoteInterface() {
        return IUserManager.Stub.asInterface(ServiceManagerNative.getService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28jSFo="))));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public IUserManager getService() {
        if (IInterfaceUtils.isAlive(this.mService) && !VirtualCore.get().isExtHelperProcess()) return this.mService;
        Class<VUserManager> clazz = VUserManager.class;
        synchronized (VUserManager.class) {
            Object remote = this.getRemoteInterface();
            this.mService = LocalProxyUtils.genProxy(IUserManager.class, remote);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return this.mService;
        }
    }

    public static synchronized VUserManager get() {
        return sInstance;
    }

    public static boolean supportsMultipleUsers() {
        return VUserManager.getMaxSupportedUsers() > 1;
    }

    public int getUserHandle() {
        return VUserHandle.myUserId();
    }

    public String getUserName() {
        try {
            return this.getService().getUserInfo((int)this.getUserHandle()).name;
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQVsJyg5PQgcO2AKLFo=")), (Throwable)re);
            return "";
        }
    }

    public boolean isUserAGoat() {
        return false;
    }

    public VUserInfo getUserInfo(int handle) {
        try {
            return this.getService().getUserInfo(handle);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQVsJyg5PQgYKmIwAlo=")), (Throwable)re);
            return null;
        }
    }

    public long getSerialNumberForUser(VUserHandle user) {
        return this.getUserSerialNumber(user.getIdentifier());
    }

    public VUserHandle getUserForSerialNumber(long serialNumber) {
        int ident = this.getUserHandle((int)serialNumber);
        return ident >= 0 ? new VUserHandle(ident) : null;
    }

    public VUserInfo createUser(String name, int flags) {
        try {
            return this.getService().createUser(name, flags);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxcqKGkjQQZrDTwsPQcuD2IFMFo=")), (Throwable)re);
            return null;
        }
    }

    public int getUserCount() {
        List<VUserInfo> users = this.getUsers();
        return users != null ? users.size() : 1;
    }

    public List<VUserInfo> getUsers() {
        try {
            return this.getService().getUsers(false);
        }
        catch (RemoteException re) {
            re.printStackTrace();
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQVsJyg5PQgEI2EjFlo=")), (Throwable)re);
            return null;
        }
    }

    public List<VUserInfo> getUsers(boolean excludeDying) {
        try {
            return this.getService().getUsers(excludeDying);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQVsJyg5PQgEI2EjFlo=")), (Throwable)re);
            return null;
        }
    }

    public boolean removeUser(int handle) {
        try {
            return this.getService().removeUser(handle);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxguPW8jND5rDTwwKT4uCEsVSFo=")), (Throwable)re);
            return false;
        }
    }

    public void setUserName(int handle, String name) {
        try {
            this.getService().setUserName(handle, name);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxgqPWU3TQZqESsrKhc2J2E0OClpDlEuOD5SVg==")), (Throwable)re);
        }
    }

    public void setUserIcon(int handle, Bitmap icon) {
        try {
            this.getService().setUserIcon(handle, icon);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxgqPWU3TQZqESsrKhc2J2E0OCxpJFkdOD5SVg==")), (Throwable)re);
        }
    }

    public Bitmap getUserIcon(int handle) {
        try {
            return this.getService().getUserIcon(handle);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQZqESsrKhc2J2E0OCxpJFkdOD5SVg==")), (Throwable)re);
            return null;
        }
    }

    public void setGuestEnabled(boolean enable) {
        try {
            this.getService().setGuestEnabled(enable);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxcqMm4jMC1rDTwuKhguD2ZTOCRpJCweIy4qCngVJCJrER47OwcqIGwaBiJoI1EZIyo6Vg==")) + enable));
        }
    }

    public boolean isGuestEnabled() {
        try {
            return this.getService().isGuestEnabled();
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxguPWUwRS9rDiQgPQgmCWIFND95HgodKC5fKGsFMzRvNCwoIBc2Vg==")));
            return false;
        }
    }

    public void wipeUser(int handle) {
        try {
            this.getService().wipeUser(handle);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxg6MWozBShvDjAgKSo6Vg==")) + handle));
        }
    }

    public static int getMaxSupportedUsers() {
        return Integer.MAX_VALUE;
    }

    public int getUserSerialNumber(int handle) {
        try {
            return this.getService().getUserSerialNumber(handle);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TQNrDgoaLRgDJGAzLChpNAo7OD4EKWw3IC9vNygbDRhSVg==")) + handle));
            return -1;
        }
    }

    public int getUserHandle(int userSerialNumber) {
        try {
            return this.getService().getUserHandle(userSerialNumber);
        }
        catch (RemoteException re) {
            Log.w((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMyhgNB4gPxc6PWU3TVNnDjAgKSxfO2AwFjduCiAvKQdeJG8KLD9vIzxF")) + userSerialNumber));
            return -1;
        }
    }
}

