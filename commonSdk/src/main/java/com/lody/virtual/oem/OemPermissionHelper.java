/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ResolveInfo
 */
package com.lody.virtual.oem;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.compat.BuildCompat;
import java.util.Arrays;
import java.util.List;

public class OemPermissionHelper {
    private static List<ComponentName> EMUI_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDU=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDV8Nyw9KC1fCm8KIAFoNA05IAcfJX0jLDVqHhoaKhYcD28jEjdgHCAsIxVbMWoKBhFoJCwaKi4YCmcFSFo="))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDU=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDV8NFk5Iz42L2oKTT96Jwo6Ji4MOmoaOCRlMywLIy4ALGcwMDdhNwoRLy42MWUVLAZuAVRF"))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDU=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDV8NCQ5LD4YKWUwMCZqNwU5OwcuM28FJD1lNDM5Ii0qP28gMAVhHCAsIxUqDW8aBgRlJwICLT0qI2YwGj9rAVRF"))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDU=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojRQV9ATg/KQMYL2ggAgZrAQ4eLRgcO2IgLDV8Nyw9KC1fCm8KIAFoNA05IAcfJX0jLDVqHhoaKhY+LWsVQStiHCAsIxVbMWoKBhFoJCwaKi4YCmcFSFo="))));
    private static List<ComponentName> FLYME_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEitjAQIvOj4qOWkVGlo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEitjAQIvOj4qOWkVBSZsESg5LBgYD2EgGipsMB4SKS5bCG8bFlZjETAZJQg6IGoVBlo="))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEitjAQIvOj4qOWkVGlo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEitjAQIvOj4qOWkVBSZsJygqKhcMI2YVBSlnHlkcLyxbJW8VAiJlHiwg"))));
    private static List<ComponentName> VIVO_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojAgFgJBE2Iy0MP2UgRSs=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojAgFgJBE2Iy0MP2UgRSt1NCgaPC06LGAgRSBsJyA9Ki4mI24zNwRjJyBBIBcmOWobFiZnJ105LggmM28jSFo="))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuAShF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuASsdKC4YCmoKOAVsDhE5Lxg2OWowBjFlDBooLjw+OWwFAj5jAQoZ"))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuAShF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuASsdKC4YCmoKOAVsDhE5Ly0YLWobPDFqESAwKi02CWozBhZiAQo7KQdbE24KBi9vNx4/LxhSVg=="))), new ComponentName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuAShF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogOC9mNBE2IxcMKG8jLANsJx4cLC4IO2AwPCJuASsdKC4YCmoKOAVsDhE5Lxg2OWowBjFlD102KRgYLmUaMD8="))));

    public static Intent getPermissionActivityIntent(Context context) {
        BuildCompat.ROMType romType = BuildCompat.getROMType();
        switch (romType) {
            case EMUI: {
                for (ComponentName component : EMUI_AUTO_START_COMPONENTS) {
                    Intent intent = new Intent();
                    intent.addFlags(0x10000000);
                    intent.setComponent(component);
                    if (!OemPermissionHelper.verifyIntent(context, intent)) continue;
                    return intent;
                }
                break;
            }
            case MIUI: {
                Intent intent = new Intent();
                intent.addFlags(0x10000000);
                intent.setClassName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEi9mDhk2Iy0MP2UgRS9vHh4qLhgcCmIFMFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojEi9mDhk2IxcMKG8jAitlNCwgKSocO2YFFiplJzAqLBcbKmAKNCBqMjAZOwgqM2QKOAJuJw40IwguCGwLJClmHgYuKQg2IQ==")));
                if (!OemPermissionHelper.verifyIntent(context, intent)) break;
                return intent;
            }
            case FLYME: {
                for (ComponentName component : FLYME_AUTO_START_COMPONENTS) {
                    Intent intent = new Intent();
                    intent.addFlags(0x10000000);
                    intent.setComponent(component);
                    if (!OemPermissionHelper.verifyIntent(context, intent)) continue;
                    return intent;
                }
                break;
            }
            case COLOR_OS: {
                Intent intent = new Intent();
                intent.addFlags(0x10000000);
                intent.setClassName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojLCVgHh4qKi4pDmoFQS5rATAgLC0qJ2EzSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojLCVgHh4qKi4pDmoFQS5rATAgLC0qJ2E0RTZqHiQ7Iz0ADmgKICR6IjAZOwgqM2oFPB9qNFERLAc2LGMVLAZjATwzLBgcVg==")));
                if (!OemPermissionHelper.verifyIntent(context, intent)) break;
                return intent;
            }
            case LETV: {
                Intent intent = new Intent();
                intent.addFlags(0x10000000);
                intent.setClassName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojHitmET82LwcYPmoVNC9rVhodLhcqMmEgPCFuAVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXojHitmET82LwcYPmoVNC9rVhodLhcqMmEgPCFuCh4AIy0cKWgzGgNsDw4oJj0mLm4IODNlNzAhLAcqJw==")));
                if (!OemPermissionHelper.verifyIntent(context, intent)) break;
                return intent;
            }
            case VIVO: {
                for (ComponentName component : VIVO_AUTO_START_COMPONENTS) {
                    Intent intent = new Intent();
                    intent.addFlags(0x10000000);
                    intent.setComponent(component);
                    if (!OemPermissionHelper.verifyIntent(context, intent)) continue;
                    return intent;
                }
                break;
            }
            case _360: {
                Intent intent = new Intent();
                intent.addFlags(0x10000000);
                intent.setClassName(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogJC9jHh41Myo9Kn8VPCVoNx4dLhc2O2IwLFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ADXogJC9jHh41Myo9Kn8VPCVoNx4dLhc2O2IwLylqDh0dKi4qIGsKRARjHjwdLAdfM24FNB9uDhowKT4YLGkVSFo=")));
                if (!OemPermissionHelper.verifyIntent(context, intent)) break;
                return intent;
            }
        }
        return null;
    }

    private static boolean verifyIntent(Context context, Intent intent) {
        ResolveInfo info = context.getPackageManager().resolveActivity(intent, 0);
        return info != null && info.activityInfo != null && info.activityInfo.exported;
    }
}

