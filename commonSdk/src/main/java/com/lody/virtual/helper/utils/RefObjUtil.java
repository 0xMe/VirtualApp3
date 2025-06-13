/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.helper.utils;

import mirror.RefObject;

public class RefObjUtil {
    public static <T> T getRefObjectValue(RefObject ref, Object obj) {
        if (ref == null) {
            return null;
        }
        return ref.get(obj);
    }

    public static <T> void setRefObjectValue(RefObject ref, Object obj, T value) {
        if (ref == null) {
            return;
        }
        ref.set(obj, value);
    }
}

