/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Environment
 *  org.zeroturnaround.zip.NameMapper
 *  org.zeroturnaround.zip.ZipException
 *  org.zeroturnaround.zip.ZipUtil
 */
package com.carlos.common.utils.xapk;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.carlos.common.utils.xapk.XAPKUtils;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.io.File;
import java.util.ArrayList;
import org.zeroturnaround.zip.NameMapper;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;

public class XAPKInstaller {
    public static void doInstallApk(Context context, String xapkFilePath) {
        if (xapkFilePath.isEmpty()) {
            return;
        }
        File xapkFile = new File(xapkFilePath);
        String unzipOutputDirPath = XAPKInstaller.getUnzipOutputDirPath(xapkFile);
        if (unzipOutputDirPath.isEmpty()) {
            return;
        }
        File unzipOutputDir = new File(unzipOutputDirPath);
        ZipUtil.unpack((File)xapkFile, (File)unzipOutputDir, (NameMapper)new NameMapper(){

            public String map(String name) {
                if (name.endsWith(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")))) {
                    return name;
                }
                return null;
            }
        });
        File[] files = unzipOutputDir.listFiles();
        int apkSize = 0;
        for (File file : files) {
            if (!file.isFile() || !file.getName().endsWith(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")))) continue;
            ++apkSize;
        }
        XAPKInstaller.unzipObbToAndroidObbDir(xapkFile, new File(XAPKInstaller.getMobileAndroidObbDir()));
        HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KAdXCg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6MWczAjJiDQU8PxhSVg==")) + apkSize);
        if (apkSize > 0) {
            XAPKInstaller.doInstallApk(context, xapkFilePath, unzipOutputDir);
        }
    }

    private static String getUnzipOutputDirPath(File file) {
        String filePathPex = file.getParent() + File.separator;
        String unzipOutputDir = filePathPex + XAPKUtils.getFileNameNoExtension(file);
        HVLog.d("");
        boolean result = XAPKUtils.createOrExistsDir(unzipOutputDir);
        if (result) {
            return unzipOutputDir;
        }
        return null;
    }

    private static boolean unzipObbToAndroidObbDir(File xapkFile, File unzipOutputDir) {
        final String prefix = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JggcPG8jGi9iVx41Lz0uVg=="));
        ZipUtil.unpack((File)xapkFile, (File)unzipOutputDir, (NameMapper)new NameMapper(){

            public String map(String name) {
                if (name.startsWith(prefix)) {
                    return name.substring(prefix.length());
                }
                return null;
            }
        });
        return true;
    }

    public static String getMobileAndroidObbDir() {
        String path = XAPKInstaller.isSDCardEnableByEnvironment() ? Environment.getExternalStorageDirectory().getPath() + File.separator + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JggcPG8jGi9iEVRF")) + File.separator + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4MOg==")) : Environment.getDataDirectory().getParent().toString() + File.separator + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JggcPG8jGi9iEVRF")) + File.separator + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iy4MOg=="));
        XAPKUtils.createOrExistsDir(path);
        return path;
    }

    private static boolean isSDCardEnableByEnvironment() {
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwgAI2ogMCtiEVRF")) == Environment.getExternalStorageState();
    }

    private static void doInstallApk(Context context, String xapkPath, File xapkUnzipOutputDir) {
        try {
            File[] files = xapkUnzipOutputDir.listFiles();
            if (files == null || files.length < 1) {
                return;
            }
            ArrayList<String> apkFilePaths = new ArrayList<String>();
            for (File file : files) {
                if (file == null || !file.isFile() || !file.getName().endsWith(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz4+KGUzSFo=")))) continue;
                apkFilePaths.add(file.getAbsolutePath());
            }
            Intent intent = new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42GillJzAqKT4iHWggMAVsJx4ZIQhSVg==")));
            intent.putExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KBg+KGU2GgJ9AQo0")), xapkPath);
            intent.putStringArrayListExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6MWYwIDdmHhpF")), apkFilePaths);
            intent.addFlags(0x10000000);
            context.startActivity(intent);
        }
        catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

