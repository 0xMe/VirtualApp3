/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.carlos.common.reverse.hooker;

import android.app.Dialog;
import com.kook.librelease.StringFog;
import java.util.ArrayList;
import java.util.List;

public class HookDialogUtils {
    static List<Integer> hiddenDialogCode = new ArrayList<Integer>(5);
    static List<String> hiddenDialog = new ArrayList<String>();

    public static void addHiddenDialogCode(int code) {
        if (hiddenDialogCode.size() >= 5) {
            hiddenDialogCode.clear();
        }
        hiddenDialogCode.add(code);
    }

    public static boolean isHiddDialog(Dialog dialog) {
        return dialog != null && hiddenDialogCode.contains(dialog.hashCode());
    }

    public static boolean isHiddDialog(String clzName, String packageName) {
        for (String str : hiddenDialog) {
            String[] arr;
            if (!(str.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OD5SVg=="))) > 0 ? (arr = str.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OD5SVg==")))) != null && arr.length == 2 && arr[0].equals(packageName) && arr[1].equals(clzName) : str.equals(clzName))) continue;
            return true;
        }
        return false;
    }

    static {
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC0LCGszBSZgNDAaKi0XDmUzND91NzgbLgcMKWMKESlqDh0dKBguO2wzMwRiIjxBJRcYPmIaRTJuJDAJLAg+DmozPFo=")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogMDdhHgI1LQMYMW8aBitsNxosLAQcJQ==")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojBjBmHh02LC1fPm9TIDdlNyw5LD4YIE4wPDNlVx4AKT4ACG8bMAVrEQI6JC5SVg==")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojFiRmDjAsKi42MW8FMC1oAQ4gKTocJ2AjNCh7NCQdLz1fKWoFMwRrHjwdCDsmO24FNCJhNzAoIxgAPQ==")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojFiRmDjAsKi42MW8FMC1oAQ4gKTocJ2AjNCh7NCQdLz1fKWoFMwRrHjwdCDwiOWwgICRpJB4cJRgYP2oFGi0=")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojBilhJB4+LF4YOW8VBgRlJx4vPC4IKWFTRRNpIiwqKS1XO2oFPARkNyg1KD0ALGomLD1uJyQ6LS5SVg==")));
        hiddenDialog.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojQTdjJCA1KC0iD2knMAJsNwYeLD0qI2AgRClqNBouIwQqUm8VJCZsAR45JCwiOWwgTQFlNzA6IzwiKmsVPCNiDlkg")));
    }
}

