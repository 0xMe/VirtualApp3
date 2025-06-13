/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.carlos.common.imagepicker.photoview;

import android.widget.ImageView;
import com.kook.librelease.StringFog;

class Util {
    Util() {
    }

    static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgYCGUVEgVgCiQiKi1fD34zFjdsIzw/LDo6JmIOODduASw8OD0cLGgFATRiESgzJQg2JHkVEgFvASM5PhY2P2oFGShhJDAgIgccDmwjPAVlDFEcLD4HLEkOODJvATAhOD5aJGUFGiZoVjwoJxgiOWwjPCRsJ10ZLQQ6LmsVHgViAVRF")));
        }
        if (midZoom >= maxZoom) {
            throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwguPGUaNCNLEQI1Ki1WOmwzQQN4HiwcPQgMJ0saTSBlJy8pIz4MO2U3IEhrHlkwJgg2JHkVEgFvASM5PhY2P2oFGShhJDAgIgciImwjPAVlDFEcLD4HLEkOODJvATAhOD5aJGUFGiZoVjwoJxgiOWwjPCRsJ10ZLQQ6LmsVHgViAVRF")));
        }
    }

    static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    static boolean isSupportedScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        switch (scaleType) {
            case MATRIX: {
                throw new IllegalStateException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Owg+LG8jAjBLESg5LwdbPX4wBj9sESsrIxc1JGAwAj95ESw+LD1XKWwwMD9oAVRF")));
            }
        }
        return true;
    }

    static int getPointerIndex(int action) {
        return (action & 0xFF00) >> 8;
    }
}

