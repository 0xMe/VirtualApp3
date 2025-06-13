/*
 * Decompiled with CFR 0.152.
 */
package external.org.apache.commons.lang3;

import external.org.apache.commons.lang3.builder.EqualsBuilder;
import external.org.apache.commons.lang3.builder.HashCodeBuilder;
import external.org.apache.commons.lang3.builder.ToStringBuilder;
import external.org.apache.commons.lang3.builder.ToStringStyle;
import external.org.apache.commons.lang3.mutable.MutableInt;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArrayUtils {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
    public static final int INDEX_NOT_FOUND = -1;

    public static String toString(Object array2) {
        return ArrayUtils.toString(array2, "{}");
    }

    public static String toString(Object array2, String stringIfNull) {
        if (array2 == null) {
            return stringIfNull;
        }
        return new ToStringBuilder(array2, ToStringStyle.SIMPLE_STYLE).append(array2).toString();
    }

    public static int hashCode(Object array2) {
        return new HashCodeBuilder().append(array2).toHashCode();
    }

    public static boolean isEquals(Object array1, Object array2) {
        return new EqualsBuilder().append(array1, array2).isEquals();
    }

    public static Map<Object, Object> toMap(Object[] array2) {
        if (array2 == null) {
            return null;
        }
        HashMap<Object, Object> map = new HashMap<Object, Object>((int)((double)array2.length * 1.5));
        for (int i = 0; i < array2.length; ++i) {
            Object[] entry;
            Object object = array2[i];
            if (object instanceof Map.Entry) {
                entry = (Object[])object;
                map.put(entry.getKey(), entry.getValue());
                continue;
            }
            if (object instanceof Object[]) {
                entry = (Object[])object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
                continue;
            }
            throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
        }
        return map;
    }

    public static <T> T[] toArray(T ... items) {
        return items;
    }

    public static <T> T[] clone(T[] array2) {
        if (array2 == null) {
            return null;
        }
        return (Object[])array2.clone();
    }

    public static long[] clone(long[] array2) {
        if (array2 == null) {
            return null;
        }
        return (long[])array2.clone();
    }

    public static int[] clone(int[] array2) {
        if (array2 == null) {
            return null;
        }
        return (int[])array2.clone();
    }

    public static short[] clone(short[] array2) {
        if (array2 == null) {
            return null;
        }
        return (short[])array2.clone();
    }

    public static char[] clone(char[] array2) {
        if (array2 == null) {
            return null;
        }
        return (char[])array2.clone();
    }

    public static byte[] clone(byte[] array2) {
        if (array2 == null) {
            return null;
        }
        return (byte[])array2.clone();
    }

    public static double[] clone(double[] array2) {
        if (array2 == null) {
            return null;
        }
        return (double[])array2.clone();
    }

    public static float[] clone(float[] array2) {
        if (array2 == null) {
            return null;
        }
        return (float[])array2.clone();
    }

    public static boolean[] clone(boolean[] array2) {
        if (array2 == null) {
            return null;
        }
        return (boolean[])array2.clone();
    }

    public static Object[] nullToEmpty(Object[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array2;
    }

    public static String[] nullToEmpty(String[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return array2;
    }

    public static long[] nullToEmpty(long[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        return array2;
    }

    public static int[] nullToEmpty(int[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        return array2;
    }

    public static short[] nullToEmpty(short[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        return array2;
    }

    public static char[] nullToEmpty(char[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        return array2;
    }

    public static byte[] nullToEmpty(byte[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        return array2;
    }

    public static double[] nullToEmpty(double[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array2;
    }

    public static float[] nullToEmpty(float[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array2;
    }

    public static boolean[] nullToEmpty(boolean[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array2;
    }

    public static Long[] nullToEmpty(Long[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Integer[] nullToEmpty(Integer[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Short[] nullToEmpty(Short[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Character[] nullToEmpty(Character[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Byte[] nullToEmpty(Byte[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Double[] nullToEmpty(Double[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Float[] nullToEmpty(Float[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array2;
    }

    public static Boolean[] nullToEmpty(Boolean[] array2) {
        if (array2 == null || array2.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array2;
    }

    public static <T> T[] subarray(T[] array2, int startIndexInclusive, int endIndexExclusive) {
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class<?> type = array2.getClass().getComponentType();
        if (newSize <= 0) {
            Object[] emptyArray = (Object[])Array.newInstance(type, 0);
            return emptyArray;
        }
        Object[] subarray = (Object[])Array.newInstance(type, newSize);
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static long[] subarray(long[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] subarray = new long[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static int[] subarray(int[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] subarray = new int[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static short[] subarray(short[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] subarray = new short[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static char[] subarray(char[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] subarray = new char[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static byte[] subarray(byte[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] subarray = new byte[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static double[] subarray(double[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] subarray = new double[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static float[] subarray(float[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] subarray = new float[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static boolean[] subarray(boolean[] array2, int startIndexInclusive, int endIndexExclusive) {
        int newSize;
        if (array2 == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array2.length) {
            endIndexExclusive = array2.length;
        }
        if ((newSize = endIndexExclusive - startIndexInclusive) <= 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] subarray = new boolean[newSize];
        System.arraycopy(array2, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static boolean isSameLength(Object[] array1, Object[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(long[] array1, long[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(int[] array1, int[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(short[] array1, short[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(char[] array1, char[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(byte[] array1, byte[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(double[] array1, double[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(float[] array1, float[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        return !(array1 == null && array2 != null && array2.length > 0 || array2 == null && array1 != null && array1.length > 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static int getLength(Object array2) {
        if (array2 == null) {
            return 0;
        }
        return Array.getLength(array2);
    }

    public static boolean isSameType(Object array1, Object array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    public static void reverse(Object[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            Object tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(long[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            long tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(int[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            int tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(short[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            short tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(char[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            char tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(byte[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            byte tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(double[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            double tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(float[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            float tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static void reverse(boolean[] array2) {
        if (array2 == null) {
            return;
        }
        int i = 0;
        for (int j = array2.length - 1; j > i; --j, ++i) {
            boolean tmp = array2[j];
            array2[j] = array2[i];
            array2[i] = tmp;
        }
    }

    public static int indexOf(Object[] array2, Object objectToFind) {
        return ArrayUtils.indexOf(array2, objectToFind, 0);
    }

    public static int indexOf(Object[] array2, Object objectToFind, int startIndex) {
        block5: {
            block4: {
                if (array2 == null) {
                    return -1;
                }
                if (startIndex < 0) {
                    startIndex = 0;
                }
                if (objectToFind != null) break block4;
                for (int i = startIndex; i < array2.length; ++i) {
                    if (array2[i] != null) continue;
                    return i;
                }
                break block5;
            }
            if (!array2.getClass().getComponentType().isInstance(objectToFind)) break block5;
            for (int i = startIndex; i < array2.length; ++i) {
                if (!objectToFind.equals(array2[i])) continue;
                return i;
            }
        }
        return -1;
    }

    public static int lastIndexOf(Object[] array2, Object objectToFind) {
        return ArrayUtils.lastIndexOf(array2, objectToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(Object[] array2, Object objectToFind, int startIndex) {
        block6: {
            block5: {
                if (array2 == null) {
                    return -1;
                }
                if (startIndex < 0) {
                    return -1;
                }
                if (startIndex >= array2.length) {
                    startIndex = array2.length - 1;
                }
                if (objectToFind != null) break block5;
                for (int i = startIndex; i >= 0; --i) {
                    if (array2[i] != null) continue;
                    return i;
                }
                break block6;
            }
            if (!array2.getClass().getComponentType().isInstance(objectToFind)) break block6;
            for (int i = startIndex; i >= 0; --i) {
                if (!objectToFind.equals(array2[i])) continue;
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(Object[] array2, Object objectToFind) {
        return ArrayUtils.indexOf(array2, objectToFind) != -1;
    }

    public static int indexOf(long[] array2, long valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(long[] array2, long valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(long[] array2, long valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(long[] array2, long valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(long[] array2, long valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(int[] array2, int valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(int[] array2, int valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(int[] array2, int valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(int[] array2, int valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(int[] array2, int valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(short[] array2, short valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(short[] array2, short valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(short[] array2, short valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(short[] array2, short valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(short[] array2, short valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(char[] array2, char valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(char[] array2, char valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(char[] array2, char valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(char[] array2, char valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(char[] array2, char valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(byte[] array2, byte valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(byte[] array2, byte valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(byte[] array2, byte valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(byte[] array2, byte valueToFind, int startIndex) {
        if (array2 == null) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(byte[] array2, byte valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(double[] array2, double valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(double[] array2, double valueToFind, double tolerance) {
        return ArrayUtils.indexOf(array2, valueToFind, 0, tolerance);
    }

    public static int indexOf(double[] array2, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int indexOf(double[] array2, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i < array2.length; ++i) {
            if (!(array2[i] >= min) || !(array2[i] <= max)) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(double[] array2, double valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(double[] array2, double valueToFind, double tolerance) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE, tolerance);
    }

    public static int lastIndexOf(double[] array2, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(double[] array2, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i >= 0; --i) {
            if (!(array2[i] >= min) || !(array2[i] <= max)) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(double[] array2, double valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static boolean contains(double[] array2, double valueToFind, double tolerance) {
        return ArrayUtils.indexOf(array2, valueToFind, 0, tolerance) != -1;
    }

    public static int indexOf(float[] array2, float valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(float[] array2, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(float[] array2, float valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(float[] array2, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(float[] array2, float valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static int indexOf(boolean[] array2, boolean valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind, 0);
    }

    public static int indexOf(boolean[] array2, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array2.length; ++i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(boolean[] array2, boolean valueToFind) {
        return ArrayUtils.lastIndexOf(array2, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(boolean[] array2, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array2)) {
            return -1;
        }
        if (startIndex < 0) {
            return -1;
        }
        if (startIndex >= array2.length) {
            startIndex = array2.length - 1;
        }
        for (int i = startIndex; i >= 0; --i) {
            if (valueToFind != array2[i]) continue;
            return i;
        }
        return -1;
    }

    public static boolean contains(boolean[] array2, boolean valueToFind) {
        return ArrayUtils.indexOf(array2, valueToFind) != -1;
    }

    public static char[] toPrimitive(Character[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i].charValue();
        }
        return result;
    }

    public static char[] toPrimitive(Character[] array2, char valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Character b = array2[i];
            result[i] = b == null ? valueForNull : b.charValue();
        }
        return result;
    }

    public static Character[] toObject(char[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        Character[] result = new Character[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = Character.valueOf(array2[i]);
        }
        return result;
    }

    public static long[] toPrimitive(Long[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static long[] toPrimitive(Long[] array2, long valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Long b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Long[] toObject(long[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        Long[] result = new Long[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static int[] toPrimitive(Integer[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static int[] toPrimitive(Integer[] array2, int valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Integer b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Integer[] toObject(int[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        Integer[] result = new Integer[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static short[] toPrimitive(Short[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static short[] toPrimitive(Short[] array2, short valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Short b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Short[] toObject(short[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        Short[] result = new Short[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static byte[] toPrimitive(Byte[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static byte[] toPrimitive(Byte[] array2, byte valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Byte b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Byte[] toObject(byte[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        Byte[] result = new Byte[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static double[] toPrimitive(Double[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static double[] toPrimitive(Double[] array2, double valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Double b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Double[] toObject(double[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        Double[] result = new Double[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static float[] toPrimitive(Float[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i].floatValue();
        }
        return result;
    }

    public static float[] toPrimitive(Float[] array2, float valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Float b = array2[i];
            result[i] = b == null ? valueForNull : b.floatValue();
        }
        return result;
    }

    public static Float[] toObject(float[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        Float[] result = new Float[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = Float.valueOf(array2[i]);
        }
        return result;
    }

    public static boolean[] toPrimitive(Boolean[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i];
        }
        return result;
    }

    public static boolean[] toPrimitive(Boolean[] array2, boolean valueForNull) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            Boolean b = array2[i];
            result[i] = b == null ? valueForNull : b;
        }
        return result;
    }

    public static Boolean[] toObject(boolean[] array2) {
        if (array2 == null) {
            return null;
        }
        if (array2.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        Boolean[] result = new Boolean[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            result[i] = array2[i] ? Boolean.TRUE : Boolean.FALSE;
        }
        return result;
    }

    public static boolean isEmpty(Object[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(long[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(int[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(short[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(char[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(byte[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(double[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(float[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static boolean isEmpty(boolean[] array2) {
        return array2 == null || array2.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(long[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(int[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(short[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(char[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(byte[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(double[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(float[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static boolean isNotEmpty(boolean[] array2) {
        return array2 != null && array2.length != 0;
    }

    public static <T> T[] addAll(T[] array1, T ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        Class<?> type1 = array1.getClass().getComponentType();
        Object[] joinedArray = (Object[])Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        }
        catch (ArrayStoreException ase) {
            Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
            }
            throw ase;
        }
        return joinedArray;
    }

    public static boolean[] addAll(boolean[] array1, boolean ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static char[] addAll(char[] array1, char ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] addAll(byte[] array1, byte ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static short[] addAll(short[] array1, short ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static int[] addAll(int[] array1, int ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static long[] addAll(long[] array1, long ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static float[] addAll(float[] array1, float ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static double[] addAll(double[] array1, double ... array2) {
        if (array1 == null) {
            return ArrayUtils.clone(array2);
        }
        if (array2 == null) {
            return ArrayUtils.clone(array1);
        }
        double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static <T> T[] add(T[] array2, T element) {
        Class<?> type;
        if (array2 != null) {
            type = array2.getClass();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        Object[] newArray = (Object[])ArrayUtils.copyArrayGrow1(array2, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static boolean[] add(boolean[] array2, boolean element) {
        boolean[] newArray = (boolean[])ArrayUtils.copyArrayGrow1(array2, Boolean.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static byte[] add(byte[] array2, byte element) {
        byte[] newArray = (byte[])ArrayUtils.copyArrayGrow1(array2, Byte.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static char[] add(char[] array2, char element) {
        char[] newArray = (char[])ArrayUtils.copyArrayGrow1(array2, Character.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static double[] add(double[] array2, double element) {
        double[] newArray = (double[])ArrayUtils.copyArrayGrow1(array2, Double.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static float[] add(float[] array2, float element) {
        float[] newArray = (float[])ArrayUtils.copyArrayGrow1(array2, Float.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static int[] add(int[] array2, int element) {
        int[] newArray = (int[])ArrayUtils.copyArrayGrow1(array2, Integer.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static long[] add(long[] array2, long element) {
        long[] newArray = (long[])ArrayUtils.copyArrayGrow1(array2, Long.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static short[] add(short[] array2, short element) {
        short[] newArray = (short[])ArrayUtils.copyArrayGrow1(array2, Short.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    private static Object copyArrayGrow1(Object array2, Class<?> newArrayComponentType) {
        if (array2 != null) {
            int arrayLength = Array.getLength(array2);
            Object newArray = Array.newInstance(array2.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array2, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

    public static <T> T[] add(T[] array2, int index, T element) {
        Class<?> clss = null;
        if (array2 != null) {
            clss = array2.getClass().getComponentType();
        } else if (element != null) {
            clss = element.getClass();
        } else {
            throw new IllegalArgumentException("Array and element cannot both be null");
        }
        Object[] newArray = (Object[])ArrayUtils.add(array2, index, element, clss);
        return newArray;
    }

    public static boolean[] add(boolean[] array2, int index, boolean element) {
        return (boolean[])ArrayUtils.add(array2, index, element, Boolean.TYPE);
    }

    public static char[] add(char[] array2, int index, char element) {
        return (char[])ArrayUtils.add(array2, index, Character.valueOf(element), Character.TYPE);
    }

    public static byte[] add(byte[] array2, int index, byte element) {
        return (byte[])ArrayUtils.add(array2, index, element, Byte.TYPE);
    }

    public static short[] add(short[] array2, int index, short element) {
        return (short[])ArrayUtils.add(array2, index, element, Short.TYPE);
    }

    public static int[] add(int[] array2, int index, int element) {
        return (int[])ArrayUtils.add(array2, index, element, Integer.TYPE);
    }

    public static long[] add(long[] array2, int index, long element) {
        return (long[])ArrayUtils.add(array2, index, element, Long.TYPE);
    }

    public static float[] add(float[] array2, int index, float element) {
        return (float[])ArrayUtils.add(array2, index, Float.valueOf(element), Float.TYPE);
    }

    public static double[] add(double[] array2, int index, double element) {
        return (double[])ArrayUtils.add(array2, index, element, Double.TYPE);
    }

    private static Object add(Object array2, int index, Object element, Class<?> clss) {
        if (array2 == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        int length = Array.getLength(array2);
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(clss, length + 1);
        System.arraycopy(array2, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array2, index, result, index + 1, length - index);
        }
        return result;
    }

    public static <T> T[] remove(T[] array2, int index) {
        return (Object[])ArrayUtils.remove(array2, index);
    }

    public static <T> T[] removeElement(T[] array2, Object element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static boolean[] remove(boolean[] array2, int index) {
        return (boolean[])ArrayUtils.remove((Object)array2, index);
    }

    public static boolean[] removeElement(boolean[] array2, boolean element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static byte[] remove(byte[] array2, int index) {
        return (byte[])ArrayUtils.remove((Object)array2, index);
    }

    public static byte[] removeElement(byte[] array2, byte element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static char[] remove(char[] array2, int index) {
        return (char[])ArrayUtils.remove((Object)array2, index);
    }

    public static char[] removeElement(char[] array2, char element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static double[] remove(double[] array2, int index) {
        return (double[])ArrayUtils.remove((Object)array2, index);
    }

    public static double[] removeElement(double[] array2, double element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static float[] remove(float[] array2, int index) {
        return (float[])ArrayUtils.remove((Object)array2, index);
    }

    public static float[] removeElement(float[] array2, float element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static int[] remove(int[] array2, int index) {
        return (int[])ArrayUtils.remove((Object)array2, index);
    }

    public static int[] removeElement(int[] array2, int element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static long[] remove(long[] array2, int index) {
        return (long[])ArrayUtils.remove((Object)array2, index);
    }

    public static long[] removeElement(long[] array2, long element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    public static short[] remove(short[] array2, int index) {
        return (short[])ArrayUtils.remove((Object)array2, index);
    }

    public static short[] removeElement(short[] array2, short element) {
        int index = ArrayUtils.indexOf(array2, element);
        if (index == -1) {
            return ArrayUtils.clone(array2);
        }
        return ArrayUtils.remove(array2, index);
    }

    private static Object remove(Object array2, int index) {
        int length = ArrayUtils.getLength(array2);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(array2.getClass().getComponentType(), length - 1);
        System.arraycopy(array2, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array2, index + 1, result, index, length - index - 1);
        }
        return result;
    }

    public static <T> T[] removeAll(T[] array2, int ... indices) {
        return (Object[])ArrayUtils.removeAll(array2, ArrayUtils.clone(indices));
    }

    public static <T> T[] removeElements(T[] array2, T ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<T, MutableInt> occurrences = new HashMap<T, MutableInt>(values.length);
        for (Object v : values) {
            MutableInt count = (MutableInt)occurrences.get(v);
            if (count == null) {
                occurrences.put(v, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Object v;
            v = e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static byte[] removeAll(byte[] array2, int ... indices) {
        return (byte[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static byte[] removeElements(byte[] array2, byte ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Byte, MutableInt> occurrences = new HashMap<Byte, MutableInt>(values.length);
        for (byte v : values) {
            Byte boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Byte v = (Byte)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static short[] removeAll(short[] array2, int ... indices) {
        return (short[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static short[] removeElements(short[] array2, short ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Short, MutableInt> occurrences = new HashMap<Short, MutableInt>(values.length);
        for (short v : values) {
            Short boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Short v = (Short)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static int[] removeAll(int[] array2, int ... indices) {
        return (int[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static int[] removeElements(int[] array2, int ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Integer, MutableInt> occurrences = new HashMap<Integer, MutableInt>(values.length);
        for (int v : values) {
            Integer boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Integer v = (Integer)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static char[] removeAll(char[] array2, int ... indices) {
        return (char[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static char[] removeElements(char[] array2, char ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Character, MutableInt> occurrences = new HashMap<Character, MutableInt>(values.length);
        for (char v : values) {
            Character boxed = Character.valueOf(v);
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Character v = (Character)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v.charValue(), found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static long[] removeAll(long[] array2, int ... indices) {
        return (long[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static long[] removeElements(long[] array2, long ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Long, MutableInt> occurrences = new HashMap<Long, MutableInt>(values.length);
        for (long v : values) {
            Long boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Long v = (Long)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static float[] removeAll(float[] array2, int ... indices) {
        return (float[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static float[] removeElements(float[] array2, float ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Float, MutableInt> occurrences = new HashMap<Float, MutableInt>(values.length);
        for (float v : values) {
            Float boxed = Float.valueOf(v);
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Float v = (Float)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v.floatValue(), found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static double[] removeAll(double[] array2, int ... indices) {
        return (double[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static double[] removeElements(double[] array2, double ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Double, MutableInt> occurrences = new HashMap<Double, MutableInt>(values.length);
        for (double v : values) {
            Double boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Double v = (Double)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, (double)v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    public static boolean[] removeAll(boolean[] array2, int ... indices) {
        return (boolean[])ArrayUtils.removeAll((Object)array2, ArrayUtils.clone(indices));
    }

    public static boolean[] removeElements(boolean[] array2, boolean ... values) {
        if (ArrayUtils.isEmpty(array2) || ArrayUtils.isEmpty(values)) {
            return ArrayUtils.clone(array2);
        }
        HashMap<Boolean, MutableInt> occurrences = new HashMap<Boolean, MutableInt>(values.length);
        for (boolean v : values) {
            Boolean boxed = v;
            MutableInt count = (MutableInt)occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
                continue;
            }
            count.increment();
        }
        HashSet<Integer> toRemove = new HashSet<Integer>();
        for (Map.Entry e : occurrences.entrySet()) {
            Boolean v = (Boolean)e.getKey();
            int found = 0;
            int ct = ((MutableInt)e.getValue()).intValue();
            for (int i = 0; i < ct && (found = ArrayUtils.indexOf(array2, v, found)) >= 0; ++i) {
                toRemove.add(found++);
            }
        }
        return ArrayUtils.removeAll(array2, ArrayUtils.extractIndices(toRemove));
    }

    private static Object removeAll(Object array2, int ... indices) {
        int length = ArrayUtils.getLength(array2);
        int diff = 0;
        if (ArrayUtils.isNotEmpty(indices)) {
            Arrays.sort(indices);
            int i = indices.length;
            int prevIndex = length;
            while (--i >= 0) {
                int index = indices[i];
                if (index < 0 || index >= length) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index >= prevIndex) continue;
                ++diff;
                prevIndex = index;
            }
        }
        Object result = Array.newInstance(array2.getClass().getComponentType(), length - diff);
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; --i) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    System.arraycopy(array2, index + 1, result, dest -= cp, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array2, 0, result, 0, end);
            }
        }
        return result;
    }

    private static int[] extractIndices(HashSet<Integer> coll) {
        int[] result = new int[coll.size()];
        int i = 0;
        for (Integer index : coll) {
            result[i++] = index;
        }
        return result;
    }
}

