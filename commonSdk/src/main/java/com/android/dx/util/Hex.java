/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.util;

public final class Hex {
    private Hex() {
    }

    public static String u8(long v) {
        char[] result = new char[16];
        for (int i = 0; i < 16; ++i) {
            result[15 - i] = Character.forDigit((int)v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String u4(int v) {
        char[] result = new char[8];
        for (int i = 0; i < 8; ++i) {
            result[7 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String u3(int v) {
        char[] result = new char[6];
        for (int i = 0; i < 6; ++i) {
            result[5 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String u2(int v) {
        char[] result = new char[4];
        for (int i = 0; i < 4; ++i) {
            result[3 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String u2or4(int v) {
        if (v == (char)v) {
            return Hex.u2(v);
        }
        return Hex.u4(v);
    }

    public static String u1(int v) {
        char[] result = new char[2];
        for (int i = 0; i < 2; ++i) {
            result[1 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String uNibble(int v) {
        char[] result = new char[]{Character.forDigit(v & 0xF, 16)};
        return new String(result);
    }

    public static String s8(long v) {
        char[] result = new char[17];
        if (v < 0L) {
            result[0] = 45;
            v = -v;
        } else {
            result[0] = 43;
        }
        for (int i = 0; i < 16; ++i) {
            result[16 - i] = Character.forDigit((int)v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String s4(int v) {
        char[] result = new char[9];
        if (v < 0) {
            result[0] = 45;
            v = -v;
        } else {
            result[0] = 43;
        }
        for (int i = 0; i < 8; ++i) {
            result[8 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String s2(int v) {
        char[] result = new char[5];
        if (v < 0) {
            result[0] = 45;
            v = -v;
        } else {
            result[0] = 43;
        }
        for (int i = 0; i < 4; ++i) {
            result[4 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String s1(int v) {
        char[] result = new char[3];
        if (v < 0) {
            result[0] = 45;
            v = -v;
        } else {
            result[0] = 43;
        }
        for (int i = 0; i < 2; ++i) {
            result[2 - i] = Character.forDigit(v & 0xF, 16);
            v >>= 4;
        }
        return new String(result);
    }

    public static String dump(byte[] arr, int offset, int length, int outOffset, int bpl, int addressLength) {
        int end = offset + length;
        if ((offset | length | end) < 0 || end > arr.length) {
            throw new IndexOutOfBoundsException("arr.length " + arr.length + "; " + offset + "..!" + end);
        }
        if (outOffset < 0) {
            throw new IllegalArgumentException("outOffset < 0");
        }
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(length * 4 + 6);
        boolean bol = true;
        int col = 0;
        while (length > 0) {
            if (col == 0) {
                String astr;
                switch (addressLength) {
                    case 2: {
                        astr = Hex.u1(outOffset);
                        break;
                    }
                    case 4: {
                        astr = Hex.u2(outOffset);
                        break;
                    }
                    case 6: {
                        astr = Hex.u3(outOffset);
                        break;
                    }
                    default: {
                        astr = Hex.u4(outOffset);
                    }
                }
                sb.append(astr);
                sb.append(": ");
            } else if (!(col & true)) {
                sb.append(' ');
            }
            sb.append(Hex.u1(arr[offset]));
            ++outOffset;
            ++offset;
            if (++col == bpl) {
                sb.append('\n');
                col = 0;
            }
            --length;
        }
        if (col != 0) {
            sb.append('\n');
        }
        return sb.toString();
    }
}

