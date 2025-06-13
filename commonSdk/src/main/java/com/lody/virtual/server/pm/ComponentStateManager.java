/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.SharedPreferences
 *  android.util.SparseArray
 */
package com.lody.virtual.server.pm;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.util.SparseArray;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import java.util.Map;

public class ComponentStateManager {
    private static SparseArray<UserComponentState> helpers = new SparseArray();

    public static synchronized UserComponentState user(int userId) {
        UserComponentState state = (UserComponentState)helpers.get(userId);
        if (state == null) {
            state = new UserComponentState(userId);
            helpers.put(userId, (Object)state);
        }
        return state;
    }

    public static class UserComponentState {
        private SharedPreferences sharedPreferences;

        private UserComponentState(int userId) {
            this.sharedPreferences = VirtualCore.get().getContext().getSharedPreferences(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+H2szGiNhHh42KAcYLmoINANvETg/LhUACQ==")) + userId, 0);
        }

        public int get(ComponentName componentName) {
            return this.sharedPreferences.getInt(this.componentKey(componentName), 0);
        }

        public void set(ComponentName componentName, int state) {
            this.sharedPreferences.edit().putInt(this.componentKey(componentName), state).apply();
        }

        public void clear(String packageName) {
            Map all = this.sharedPreferences.getAll();
            if (all == null) {
                return;
            }
            for (String component : all.keySet()) {
                if (!component.startsWith(packageName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JhhSVg==")))) continue;
                this.sharedPreferences.edit().remove(component).apply();
            }
        }

        public void clearAll() {
            this.sharedPreferences.edit().clear().apply();
        }

        private String componentKey(ComponentName componentName) {
            return componentName.getPackageName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JhhSVg==")) + componentName.getClassName();
        }
    }
}

