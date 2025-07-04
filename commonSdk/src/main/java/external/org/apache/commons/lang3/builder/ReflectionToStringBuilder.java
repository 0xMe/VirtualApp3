//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReflectionToStringBuilder extends ToStringBuilder {
    private boolean appendStatics = false;
    private boolean appendTransients = false;
    protected String[] excludeFieldNames;
    private Class<?> upToClass = null;

    public static String toString(Object object) {
        return toString(object, (ToStringStyle)null, false, false, (Class)null);
    }

    public static String toString(Object object, ToStringStyle style) {
        return toString(object, style, false, false, (Class)null);
    }

    public static String toString(Object object, ToStringStyle style, boolean outputTransients) {
        return toString(object, style, outputTransients, false, (Class)null);
    }

    public static String toString(Object object, ToStringStyle style, boolean outputTransients, boolean outputStatics) {
        return toString(object, style, outputTransients, outputStatics, (Class)null);
    }

    public static <T> String toString(T object, ToStringStyle style, boolean outputTransients, boolean outputStatics, Class<? super T> reflectUpToClass) {
        return (new ReflectionToStringBuilder(object, style, (StringBuffer)null, reflectUpToClass, outputTransients, outputStatics)).toString();
    }

    public static String toStringExclude(Object object, Collection<String> excludeFieldNames) {
        return toStringExclude(object, toNoNullStringArray(excludeFieldNames));
    }

    static String[] toNoNullStringArray(Collection<String> collection) {
        return collection == null ? ArrayUtils.EMPTY_STRING_ARRAY : toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object[] array) {
        List<String> list = new ArrayList(array.length);
        Object[] var2 = array;
        int var3 = array.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object e = var2[var4];
            if (e != null) {
                list.add(e.toString());
            }
        }

        return (String[])list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static String toStringExclude(Object object, String... excludeFieldNames) {
        return (new ReflectionToStringBuilder(object)).setExcludeFieldNames(excludeFieldNames).toString();
    }

    public ReflectionToStringBuilder(Object object) {
        super(object);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style) {
        super(object, style);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer) {
        super(object, style, buffer);
    }

    public <T> ReflectionToStringBuilder(T object, ToStringStyle style, StringBuffer buffer, Class<? super T> reflectUpToClass, boolean outputTransients, boolean outputStatics) {
        super(object, style, buffer);
        this.setUpToClass(reflectUpToClass);
        this.setAppendTransients(outputTransients);
        this.setAppendStatics(outputStatics);
    }

    protected boolean accept(Field field) {
        if (field.getName().indexOf(36) != -1) {
            return false;
        } else if (Modifier.isTransient(field.getModifiers()) && !this.isAppendTransients()) {
            return false;
        } else if (Modifier.isStatic(field.getModifiers()) && !this.isAppendStatics()) {
            return false;
        } else {
            return this.excludeFieldNames == null || Arrays.binarySearch(this.excludeFieldNames, field.getName()) < 0;
        }
    }

    protected void appendFieldsIn(Class<?> clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
        } else {
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            Field[] var3 = fields;
            int var4 = fields.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                String fieldName = field.getName();
                if (this.accept(field)) {
                    try {
                        Object fieldValue = this.getValue(field);
                        this.append(fieldName, fieldValue);
                    } catch (IllegalAccessException var9) {
                        throw new InternalError("Unexpected IllegalAccessException: " + var9.getMessage());
                    }
                }
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

    public ReflectionToStringBuilder reflectionAppendArray(Object array) {
        this.getStyle().reflectionAppendArrayDetail(this.getStringBuffer(), (String)null, array);
        return this;
    }

    public void setAppendStatics(boolean appendStatics) {
        this.appendStatics = appendStatics;
    }

    public void setAppendTransients(boolean appendTransients) {
        this.appendTransients = appendTransients;
    }

    public ReflectionToStringBuilder setExcludeFieldNames(String... excludeFieldNamesParam) {
        if (excludeFieldNamesParam == null) {
            this.excludeFieldNames = null;
        } else {
            this.excludeFieldNames = toNoNullStringArray((Object[])excludeFieldNamesParam);
            Arrays.sort(this.excludeFieldNames);
        }

        return this;
    }

    public void setUpToClass(Class<?> clazz) {
        if (clazz != null) {
            Object object = this.getObject();
            if (object != null && !clazz.isInstance(object)) {
                throw new IllegalArgumentException("Specified class is not a superclass of the object");
            }
        }

        this.upToClass = clazz;
    }

    public String toString() {
        if (this.getObject() == null) {
            return this.getStyle().getNullText();
        } else {
            Class<?> clazz = this.getObject().getClass();
            this.appendFieldsIn(clazz);

            while(clazz.getSuperclass() != null && clazz != this.getUpToClass()) {
                clazz = clazz.getSuperclass();
                this.appendFieldsIn(clazz);
            }

            return super.toString();
        }
    }
}
