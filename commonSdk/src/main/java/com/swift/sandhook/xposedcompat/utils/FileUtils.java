/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.swift.sandhook.xposedcompat.utils;

import android.os.Build;
import android.text.TextUtils;
import com.swift.sandhook.xposedcompat.utils.DexLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static final boolean IS_USING_PROTECTED_STORAGE = Build.VERSION.SDK_INT >= 24;

    public static void delete(File file) throws IOException {
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                FileUtils.delete(childFile);
                continue;
            }
            if (childFile.delete()) continue;
            throw new IOException();
        }
        if (!file.delete()) {
            throw new IOException();
        }
    }

    public static String readLine(File file) {
        String string2;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            string2 = reader.readLine();
        }
        catch (Throwable throwable) {
            try {
                try {
                    reader.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Throwable throwable3) {
                return "";
            }
        }
        reader.close();
        return string2;
    }

    public static void writeLine(File file, String line) {
        try {
            file.createNewFile();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));){
            writer.write(line);
            writer.flush();
        }
        catch (Throwable throwable) {
            DexLog.e("error writing line to file " + file + ": " + throwable.getMessage());
        }
    }

    public static String getPackageName(String dataDir) {
        if (TextUtils.isEmpty((CharSequence)dataDir)) {
            DexLog.e("getPackageName using empty dataDir");
            return "";
        }
        int lastIndex = dataDir.lastIndexOf("/");
        if (lastIndex < 0) {
            return dataDir;
        }
        return dataDir.substring(lastIndex + 1);
    }

    public static String getDataPathPrefix() {
        return IS_USING_PROTECTED_STORAGE ? "/data/user_de/0/" : "/data/data/";
    }
}

