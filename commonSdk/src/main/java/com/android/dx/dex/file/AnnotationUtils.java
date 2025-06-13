/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.CstAnnotation;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstKnownNull;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;

public final class AnnotationUtils {
    private static final CstType ANNOTATION_DEFAULT_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/AnnotationDefault;"));
    private static final CstType ENCLOSING_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingClass;"));
    private static final CstType ENCLOSING_METHOD_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingMethod;"));
    private static final CstType INNER_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/InnerClass;"));
    private static final CstType MEMBER_CLASSES_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/MemberClasses;"));
    private static final CstType SIGNATURE_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Signature;"));
    private static final CstType SOURCE_DEBUG_EXTENSION_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/SourceDebugExtension;"));
    private static final CstType THROWS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Throws;"));
    private static final CstString ACCESS_FLAGS_STRING = new CstString("accessFlags");
    private static final CstString NAME_STRING = new CstString("name");
    private static final CstString VALUE_STRING = new CstString("value");

    private AnnotationUtils() {
    }

    public static Annotation makeAnnotationDefault(Annotation defaults) {
        Annotation result = new Annotation(ANNOTATION_DEFAULT_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, new CstAnnotation(defaults)));
        result.setImmutable();
        return result;
    }

    public static Annotation makeEnclosingClass(CstType clazz) {
        Annotation result = new Annotation(ENCLOSING_CLASS_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, clazz));
        result.setImmutable();
        return result;
    }

    public static Annotation makeEnclosingMethod(CstMethodRef method) {
        Annotation result = new Annotation(ENCLOSING_METHOD_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, method));
        result.setImmutable();
        return result;
    }

    public static Annotation makeInnerClass(CstString name, int accessFlags) {
        Annotation result = new Annotation(INNER_CLASS_TYPE, AnnotationVisibility.SYSTEM);
        TypedConstant nameCst = name != null ? name : CstKnownNull.THE_ONE;
        result.put(new NameValuePair(NAME_STRING, nameCst));
        result.put(new NameValuePair(ACCESS_FLAGS_STRING, CstInteger.make(accessFlags)));
        result.setImmutable();
        return result;
    }

    public static Annotation makeMemberClasses(TypeList types) {
        CstArray array = AnnotationUtils.makeCstArray(types);
        Annotation result = new Annotation(MEMBER_CLASSES_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, array));
        result.setImmutable();
        return result;
    }

    public static Annotation makeSignature(CstString signature) {
        Annotation result = new Annotation(SIGNATURE_TYPE, AnnotationVisibility.SYSTEM);
        String raw = signature.getString();
        int rawLength = raw.length();
        ArrayList<String> pieces = new ArrayList<String>(20);
        int at = 0;
        while (at < rawLength) {
            int endAt;
            char c = raw.charAt(at);
            if (c == 'L') {
                for (endAt = at + 1; endAt < rawLength; ++endAt) {
                    c = raw.charAt(endAt);
                    if (c == ';') {
                        ++endAt;
                    } else if (c != '<') {
                        continue;
                    }
                    break;
                }
            } else {
                while (endAt < rawLength && (c = raw.charAt(endAt)) != 'L') {
                    ++endAt;
                }
            }
            pieces.add(raw.substring(at, endAt));
            at = endAt;
        }
        int size = pieces.size();
        CstArray.List list = new CstArray.List(size);
        for (int i = 0; i < size; ++i) {
            list.set(i, new CstString((String)pieces.get(i)));
        }
        list.setImmutable();
        result.put(new NameValuePair(VALUE_STRING, new CstArray(list)));
        result.setImmutable();
        return result;
    }

    public static Annotation makeSourceDebugExtension(CstString smapString) {
        Annotation result = new Annotation(SOURCE_DEBUG_EXTENSION_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, smapString));
        result.setImmutable();
        return result;
    }

    public static Annotation makeThrows(TypeList types) {
        CstArray array = AnnotationUtils.makeCstArray(types);
        Annotation result = new Annotation(THROWS_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, array));
        result.setImmutable();
        return result;
    }

    private static CstArray makeCstArray(TypeList types) {
        int size = types.size();
        CstArray.List list = new CstArray.List(size);
        for (int i = 0; i < size; ++i) {
            list.set(i, CstType.intern(types.getType(i)));
        }
        list.setImmutable();
        return new CstArray(list);
    }
}

