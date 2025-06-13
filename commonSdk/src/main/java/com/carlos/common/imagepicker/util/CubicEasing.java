/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.imagepicker.util;

public final class CubicEasing {
    public static float easeOut(float time, float start, float end, float duration) {
        time = time / duration - 1.0f;
        return end * (time * time * time + 1.0f) + start;
    }

    public static float easeIn(float time, float start, float end, float duration) {
        return end * (time /= duration) * time * time + start;
    }

    public static float easeInOut(float time, float start, float end, float duration) {
        float f;
        time /= duration / 2.0f;
        return f < 1.0f ? end / 2.0f * time * time * time + start : end / 2.0f * ((time -= 2.0f) * time * time + 2.0f) + start;
    }
}

