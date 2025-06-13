/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.open;

import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstalledAppInfo;

public class MultiAppHelper {
    public static int installExistedPackage(String pkg) throws IllegalStateException {
        return MultiAppHelper.installExistedPackage(VirtualCore.get().getInstalledAppInfo(pkg, 0));
    }

    public static int installExistedPackage(InstalledAppInfo info) throws IllegalStateException {
        boolean success;
        if (info == null) {
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhhbPXsFEgVhJw08Lz0LOmwjMANvETgdLAguIE4zSFo=")));
        }
        int[] userIds = info.getInstalledUsers();
        int nextUserId = userIds.length;
        for (int i = 0; i < userIds.length; ++i) {
            if (userIds[i] == i) continue;
            nextUserId = i;
            break;
        }
        if (VUserManager.get().getUserInfo(nextUserId) == null) {
            String nextUserName = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii06P2szNyg=")) + (nextUserId + 1);
            VUserInfo newUserInfo = VUserManager.get().createUser(nextUserName, 2);
            if (newUserInfo == null) {
                throw new IllegalStateException();
            }
        }
        if (!(success = VirtualCore.get().installPackageAsUser(nextUserId, info.packageName))) {
            throw new IllegalStateException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKWwFJCRgVyQ+LwccCA==")));
        }
        return nextUserId;
    }
}

