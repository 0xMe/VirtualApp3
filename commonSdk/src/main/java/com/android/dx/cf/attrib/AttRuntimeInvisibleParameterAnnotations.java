/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.cf.attrib;

import com.android.dx.cf.attrib.BaseParameterAnnotations;
import com.android.dx.rop.annotation.AnnotationsList;

public final class AttRuntimeInvisibleParameterAnnotations
extends BaseParameterAnnotations {
    public static final String ATTRIBUTE_NAME = "RuntimeInvisibleParameterAnnotations";

    public AttRuntimeInvisibleParameterAnnotations(AnnotationsList parameterAnnotations, int byteLength) {
        super(ATTRIBUTE_NAME, parameterAnnotations, byteLength);
    }
}

