/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.kook.librelease.R$array
 */
package com.carlos.common.utils;

import android.content.Context;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class StringUtils {
    private static boolean isTencentMap = true;

    public static String replaceChar2JsonString(String baseString) {
        return baseString.replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxUEHnsjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pj5SVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxUEHnsjSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pj5SVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("J18MVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pj5SVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pj1bVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KC5SVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PjtbVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxUECA==")), "\n").replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxgcVg==")), "").replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxUELA==")), "").replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwQMVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LwQMVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LwhSVg=="))).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxUELA==")), "").replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxcqVg==")), "").replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PT4+DW8OQVo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PT5SVg==")));
    }

    public static boolean isString(String tag) {
        return tag != null && !"".equals(tag);
    }

    public static double doubleFor6(double num) {
        if (isTencentMap) {
            return num;
        }
        return Double.parseDouble(StringUtils.doubleFor6String(num));
    }

    public static double doubleFor8(double num) {
        if (isTencentMap) {
            return num;
        }
        return Double.parseDouble(StringUtils.doubleFor8String(num));
    }

    public static String doubleFor8String(double num) {
        if (isTencentMap) {
            return String.valueOf(num);
        }
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(8);
        return formater.format(num);
    }

    public static String doubleFor6String(double num) {
        if (isTencentMap) {
            return String.valueOf(num);
        }
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(6);
        return formater.format(num);
    }

    public static String getTimeNoHour(long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwIBnU0IFo="))));
        calendar.setTimeInMillis(time);
        int h = calendar.get(11);
        int m = calendar.get(12);
        int s = calendar.get(13);
        return String.format(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQM5KmgOTCtPViww")), h * 60 + m, s);
    }

    public static String getTime(long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwIBnU0IFo="))));
        calendar.setTimeInMillis(time);
        int h = calendar.get(11);
        int m = calendar.get(12);
        int s = calendar.get(13);
        return String.format(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQM5KmgOTCtPViwwPTkLKnoVBlo=")), h, m, s);
    }

    public static String timeForString(Context context, long tag) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(tag);
        int week = calendar.get(7);
        String[] strs = context.getResources().getStringArray(R.array.weeks);
        String str = strs[week - 1];
        SimpleDateFormat dateFm = new SimpleDateFormat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KAcYJ2lSEg1oCl0wKF4mGQ==")) + str + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwQ6CmUOTSNgAVRF")));
        return dateFm.format(tag);
    }
}

