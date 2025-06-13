/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.lody.virtual.helper.compat;

import android.os.Build;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.compat.SystemPropertiesCompat;

public class BuildCompat {
    private static ROMType sRomType;

    public static int getPreviewSDKInt() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                return Build.VERSION.PREVIEW_SDK_INT;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return 0;
    }

    public static boolean isOreo() {
        return Build.VERSION.SDK_INT > 25 || Build.VERSION.SDK_INT == 25 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isPie() {
        return Build.VERSION.SDK_INT > 27 || Build.VERSION.SDK_INT == 27 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isQ() {
        return Build.VERSION.SDK_INT > 28 || Build.VERSION.SDK_INT == 28 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isR() {
        return Build.VERSION.SDK_INT > 29 || Build.VERSION.SDK_INT == 29 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isS() {
        return Build.VERSION.SDK_INT > 30 || Build.VERSION.SDK_INT == 30 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isT() {
        return Build.VERSION.SDK_INT > 31 || Build.VERSION.SDK_INT == 31 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isSL() {
        return Build.VERSION.SDK_INT > 31 || Build.VERSION.SDK_INT == 31 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isTiramisu() {
        return Build.VERSION.SDK_INT > 32 || Build.VERSION.SDK_INT == 32 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isUpsideDownCake() {
        return Build.VERSION.SDK_INT > 33 || Build.VERSION.SDK_INT == 33 && BuildCompat.getPreviewSDKInt() > 0;
    }

    public static boolean isSamsung() {
        return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4+DW8wNCZiJ1RF")).equalsIgnoreCase(Build.BRAND) || StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4+DW8wNCZiJ1RF")).equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isEMUI() {
        if (Build.DISPLAY.toUpperCase().startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQYIBX0VSFo=")))) {
            return true;
        }
        String property = SystemPropertiesCompat.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCGsgNC9gHg02LD0MKGoFLCVlMxogLBcuIw==")));
        return property != null && property.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQgID2wFAiVgNTAJ")));
    }

    public static boolean isMIUI() {
        return SystemPropertiesCompat.getInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCGoVAgVjClkvKQMYLGkgRQNqAQYbPC42KWIaLFo=")), 0) > 0;
    }

    public static boolean isFlyme() {
        return Build.DISPLAY.toLowerCase().contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4EJ2oVNFo=")));
    }

    public static boolean isColorOS() {
        return SystemPropertiesCompat.isExist(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCGsgNC9gHg02LD0MKGoFLCVlMxocKQc6KWEwAig="))) || SystemPropertiesCompat.isExist(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCG8jGiNONAozKD0+PWoVGiZvVho9LhcMD2MKAik=")));
    }

    public static boolean is360UI() {
        String property = SystemPropertiesCompat.get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCGsgNC9gHg02LAccLGkgRQNqAQYb")));
        return property != null && property.toUpperCase().contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OikhKGQbAlo=")));
    }

    public static boolean isLetv() {
        return Build.MANUFACTURER.equalsIgnoreCase(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxguLGwjSFo=")));
    }

    public static boolean isVivo() {
        return SystemPropertiesCompat.isExist(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4fCGwjAj5gIFk1IykYOGUjLCRrVhovIxc2DmAaPAZ8NBot")));
    }

    public static ROMType getROMType() {
        if (sRomType == null) {
            sRomType = BuildCompat.isEMUI() ? ROMType.EMUI : (BuildCompat.isMIUI() ? ROMType.MIUI : (BuildCompat.isFlyme() ? ROMType.FLYME : (BuildCompat.isColorOS() ? ROMType.COLOR_OS : (BuildCompat.is360UI() ? ROMType._360 : (BuildCompat.isLetv() ? ROMType.LETV : (BuildCompat.isVivo() ? ROMType.VIVO : (BuildCompat.isSamsung() ? ROMType.SAMSUNG : ROMType.OTHER)))))));
        }
        return sRomType;
    }

    public static enum ROMType {
        EMUI,
        MIUI,
        FLYME,
        COLOR_OS,
        LETV,
        VIVO,
        _360,
        SAMSUNG,
        OTHER;

    }
}

