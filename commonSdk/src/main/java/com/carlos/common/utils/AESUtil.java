/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.utils;

import com.kook.librelease.StringFog;
import com.lody.virtual.helper.utils.VLog;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class AESUtil {
    private static final String SECRET_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKW8kJwFPVjhF"));

    public static void main(String[] args) {
        String content = "";
        String key = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ojk5L3w0JwFPDThF"));
        System.currentTimeMillis();
        System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAOEZaBwhYEBsNDCJYGg==")) + content);
        byte[] encrypted = AESUtil.desEncrypt(content.getBytes(), key.getBytes());
        System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpAOEZaBwhYEzkMDCJYGg==")) + AESUtil.byteToHexString(encrypted));
        byte[] decrypted = AESUtil.desDecrypt(encrypted, key.getBytes());
        System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcjOUZaBwhYEzkMDCJYGg==")) + new String(decrypted));
    }

    public static String desEncrypt(String content) {
        byte[] desEncrypt = AESUtil.desEncrypt(content.getBytes(), SECRET_KEY.getBytes());
        return AESUtil.byteToHexString(desEncrypt);
    }

    private static byte[] desEncrypt(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAw==")));
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuA3oxLBRlIB4CISsqAXUmTTdrESwaLC4mVg==")));
            cipher.init(1, (Key)key, new IvParameterSpec(keySpec.getKey()));
            byte[] result = cipher.doFinal(content);
            return result;
        }
        catch (Exception e) {
            System.out.println(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LQdfOWgaIAZjDh42PT5SVg==")) + e.toString());
            return null;
        }
    }

    public static String desDecrypt(String content) {
        VLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ITw9DWAILBY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phg2D2ogMCtgNw08P18hKXonTCh+N1RF")) + content);
        byte[] bytes = AESUtil.hexStringToByteArray(content);
        byte[] desDecrypt = AESUtil.desDecrypt(bytes, SECRET_KEY.getBytes());
        if (desDecrypt == null) {
            return null;
        }
        String time = new String(desDecrypt);
        return time;
    }

    public static String desDecrypt(byte[] content) {
        byte[] desDecrypt = AESUtil.desDecrypt(content, SECRET_KEY.getBytes());
        return new String(desDecrypt);
    }

    private static byte[] desDecrypt(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuAw==")));
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRYuA3oxLBRlIB4CISsqAXUmTTdrESwaLC4mVg==")));
            cipher.init(2, (Key)key, new IvParameterSpec(keyBytes));
            byte[] result = cipher.doFinal(content);
            return result;
        }
        catch (Exception e) {
            VLog.e(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ITw9DWAILBY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgqM28xMCt9JywZIxg1OmkgFilrDjw/IxgAKktTOz0=")) + e.toString());
            return null;
        }
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        for (int i = 0; i < bytes.length; ++i) {
            String sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp);
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            b[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }
}

