/*
 * Decompiled with CFR 0.152.
 */
package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import external.org.apache.commons.lang3.builder.ToStringBuilder;
import external.org.apache.commons.lang3.builder.ToStringStyle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ReflectionToStringBuilder
extends ToStringBuilder {
    private boolean appendStatics = false;
    private boolean appendTransients = false;
    protected String[] excludeFieldNames;
    private Class<?> upToClass = null;

    public static String toString(Object object) {
        return ReflectionToStringBuilder.toString(object, null, false, false, null);
    }

    public static String toString(Object object, ToStringStyle style2) {
        return ReflectionToStringBuilder.toString(object, style2, false, false, null);
    }

    public static String toString(Object object, ToStringStyle style2, boolean outputTransients) {
        return ReflectionToStringBuilder.toString(object, style2, outputTransients, false, null);
    }

    public static String toString(Object object, ToStringStyle style2, boolean outputTransients, boolean outputStatics) {
        return ReflectionToStringBuilder.toString(object, style2, outputTransients, outputStatics, null);
    }

    public static <T> String toString(T object, ToStringStyle style2, boolean outputTransients, boolean outputStatics, Class<? super T> reflectUpToClass) {
        return new ReflectionToStringBuilder(object, style2, null, reflectUpToClass, outputTransients, outputStatics).toString();
    }

    public static String toStringExclude(Object object, Collection<String> excludeFieldNames) {
        return ReflectionToStringBuilder.toStringExclude(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFieldNames));
    }

    static String[] toNoNullStringArray(Collection<String> collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return ReflectionToStringBuilder.toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object[] array2) {
        ArrayList<String> list = new ArrayList<String>(array2.length);
        for (Object e : array2) {
            if (e == null) continue;
            list.add(e.toString());
        }
        return list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static String toStringExclude(Object object, String ... excludeFieldNames) {
        return new ReflectionToStringBuilder(object).setExcludeFieldNames(excludeFieldNames).toString();
    }

    public ReflectionToStringBuilder(Object object) {
        super(object);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style2) {
        super(object, style2);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style2, StringBuffer buffer) {
        super(object, style2, buffer);
    }

    public <T> ReflectionToStringBuilder(T object, ToStringStyle style2, StringBuffer buffer, Class<? super T> reflectUpToClass, boolean outputTransients, boolean outputStatics) {
        super(object, style2, buffer);
        this.setUpToClass(reflectUpToClass);
        this.setAppendTransients(outputTransients);
        this.setAppendStatics(outputStatics);
    }

    protected boolean accept(Field field) {
        if (field.getName().indexOf(36) != -1) {
            return false;
        }
        if (Modifier.isTransient(field.getModifiers()) && !this.isAppendTransients()) {
            return false;
        }
        if (Modifier.isStatic(field.getModifiers()) && !this.isAppendStatics()) {
            return false;
        }
        return this.excludeFieldNames == null || Arrays.binarySearch(this.excludeFieldNames, field.getName()) < 0;
    }

    protected void appendFieldsIn(Class<?> clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }
        AccessibleObject[] fields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (AccessibleObject field : fields) {
            String fieldName = ((Field)field).getName();
            if (!this.accept((Field)field)) continue;
            try {
                Object fieldValue = this.getValue((Field)field);
                this.append(fieldName, fieldValue);
            }
            catch (IllegalAccessException ex) {
                throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
            }
        }
    }

    public String[] getExcludeFieldNames() {
        return (String[])this.excludeFieldNames.clone();
    }

    public Class<?> getUpToClass() {
        return this.upToClass;
    }

    protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
        return field.get(this.getObject());
    }

    public boolean isAppendStatics() {
        return this.appendStatics;
    }

    public boolean isAppendTransients() {
        return this.appendTransients;
    }

    public ReflectionToStringBuilder reflectionAppendArray(Object array2) {
        this.getStyle().reflectionAppendArrayDetail(this.getStringBuffer(), null, array2);
        return this;
    }

    public void setAppendStatics(boolean appendStatics) {
        this.appendStatics = appendStatics;
    }

    public void setAppendTransients(boolean appendTransients) {
        this.appendTransients = appendTransients;
    }

    public ReflectionToStringBuilder setExcludeFieldNames(String ... excludeFieldNamesParam) {
        if (excludeFieldNamesParam == null) {
            this.excludeFieldNames = null;
        } else {
            this.excludeFieldNames = ReflectionToStringBuilder.toNoNullStringArray(excludeFieldNamesParam);
            Arrays.sort(this.excludeFieldNames);
        }
        return this;
    }

    public void setUpToClass(Class<?> clazz) {
        Object object;
        if (clazz != null && (object = this.getObject()) != null && !clazz.isInstance(object)) {
            throw new IllegalArgumentException("Specified class is not a superclass of the object");
        }
        this.upToClass = clazz;
    }

    @Override
    public String toString() {
        Class<?> clazz;
        if (this.getObject() == null) {
            return this.getStyle().getNullText();
        }
        this.appendFieldsIn(clazz);
        for (clazz = this.getObject().getClass(); clazz.getSuperclass() != null && clazz != this.getUpToClass(); clazz = clazz.getSuperclass()) {
            this.appendFieldsIn(clazz);
        }
        return super.toString();
    }
}

