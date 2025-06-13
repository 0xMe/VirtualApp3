/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.os.Build$VERSION
 */
package com.lody.virtual.helper.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.RequestPermissionsActivity;
import com.lody.virtual.server.IRequestPermissionsResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermissionCompat {
    public static Set<String> DANGEROUS_PERMISSION = new HashSet<String>(){
        {
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl9hIiRAJywqGmAIFlo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIiwAITwADGMbJAA=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw2HWgILB9hAVRF")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl9hIllTOzxbH2cYLFo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIixXIRUcHWAmMA8=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCwmGW4bAg5hIixXOywqXWQjSFo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw+H2UmLBB9JVlKIiwqGWEhHl5jNThOLQUYHw==")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw+H2UmLBB9JVkCIQZbU2QhNBNiDwYMIwYMGmQmAlo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl99HAZXIRYAE2QmMB1kDyhF")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCw2HWgYTV99HAZXIRYAVg==")));
            if (Build.VERSION.SDK_INT >= 16) {
                this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl9hIiRAITsuAn0hPFo=")));
                this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIiwAITwiE30bGlY=")));
            }
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojJCZiESw1KQc1DmUVNC9oJygeLRgYKE4zOCBlNFEiLAcYI2UjAQRjHywJKiw6GGcIMFZnJV0WOxhSVg==")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsuUmoLAhBnDyBF")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCs6U2gmNAp9JSwOIQUAXWMhGlBiJSAAIysmHWQbMFo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCs2GWg2Fl99IlES")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUmLBZiMgoOJAYmUg==")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl99IlES")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUmLBZiMgoOOwZbQGEmIAlnNVlF")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUmLBZiMgoOISwmUg==")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUmAh9mH1kAOywcBX0jSFo=")));
            this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsmU2sLFgpgIgoXOzwAU30xJExmMjBOLiwqAmYmFlo=")));
            if (Build.VERSION.SDK_INT >= 16) {
                this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCsMGWUIFl9mDwYTJytfDGALHhNnMiwQLzsmAGYFSFo=")));
            }
            if (Build.VERSION.SDK_INT >= 20) {
                this.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksKAguD2wgAgNqAQYbPCwMA2obGl99IgpTJAYuU2QjSFo=")));
            }
        }
    };

    public static String[] findDangerousPermissions(List<String> permissions) {
        if (permissions == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String per : permissions) {
            if (!DANGEROUS_PERMISSION.contains(per)) continue;
            list.add(per);
        }
        return list.toArray(new String[0]);
    }

    public static String[] findDangrousPermissions(String[] permissions) {
        if (permissions == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String permission2 : permissions) {
            if (!DANGEROUS_PERMISSION.contains(permission2)) continue;
            list.add(permission2);
        }
        return list.toArray(new String[0]);
    }

    public static boolean isCheckPermissionRequired(ApplicationInfo info) {
        if (Build.VERSION.SDK_INT < 23 || VirtualCore.get().getTargetSdkVersion() < 23) {
            return false;
        }
        return info.targetSdkVersion < 23;
    }

    public static boolean checkPermissions(String[] permissions, boolean isExt) {
        if (permissions == null) {
            return true;
        }
        for (String per : permissions) {
            if (VirtualCore.get().checkSelfPermission(per, isExt)) continue;
            return false;
        }
        return true;
    }

    public static boolean isRequestGranted(int[] grantResults) {
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != -1) continue;
            allGranted = false;
            break;
        }
        return allGranted;
    }

    public static void startRequestPermissions(Context context, boolean isExt, String[] permissions, final CallBack callBack) {
        RequestPermissionsActivity.request(context, isExt, permissions, new IRequestPermissionsResult.Stub(){

            @Override
            public boolean onResult(int requestCode, String[] permissions, int[] grantResults) {
                if (callBack != null) {
                    return callBack.onResult(requestCode, permissions, grantResults);
                }
                return false;
            }
        });
    }

    public static interface CallBack {
        public boolean onResult(int var1, String[] var2, int[] var3);
    }
}

