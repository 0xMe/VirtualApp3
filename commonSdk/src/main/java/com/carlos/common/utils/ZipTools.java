/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.utils;

import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipTools {
    private static int result;
    private static final String SECRET_KEY;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int compressZip(String src, String dest, ZipCallback zipCallback) {
        ZipOutputStream out = null;
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcJMUZJAwtYK0YzIy4uP3gVSFo=")) + src + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKTS9hUx8cRCUJWB1WEz0eLy01UwNXVg==")) + dest);
        try {
            File outFile = new File(dest);
            File fileOrDirectory = new File(src);
            out = new ZipOutputStream(new FileOutputStream(outFile));
            if (fileOrDirectory.isFile()) {
                ZipTools.zipFileOrDirectory(out, fileOrDirectory, "", zipCallback);
            } else {
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; ++i) {
                    ZipTools.zipFileOrDirectory(out, entries[i], "", zipCallback);
                }
            }
        }
        catch (IOException ex) {
            HVLog.printException(ex);
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException ex) {
                    HVLog.printException(ex);
                }
            }
        }
        return result;
    }

    private static String checkString(String sourceFileName) {
        if (sourceFileName.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5SVg=="))) > 0) {
            sourceFileName = sourceFileName.substring(0, sourceFileName.length() - 4);
            HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li5fM2szQV5mESwzKj05IH47GygdCRMhQABcOR8oIVIbESweIy1fJWsLOAVqASgfOwdbLEQHBwFFEjlI")) + sourceFileName);
        }
        return sourceFileName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int uncompressZip(String zipFileName, String outputDirectory, UnZipCallback unZipCallback) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFileName);
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            ZipEntry zipEntry = null;
            File dest = new File(outputDirectory);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgqM28wMyh3N1RF")) + dest.exists());
            if (!dest.exists()) {
                dest.mkdirs();
            }
            while (e.hasMoreElements()) {
                zipEntry = e.nextElement();
                String entryName = zipEntry.getName();
                InputStream in = null;
                FileOutputStream out = null;
                unZipCallback.callbackName(entryName);
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhguCGwKFj9oNCA3KAMlIA==")) + entryName);
                try {
                    int c;
                    File df;
                    File f;
                    if (zipEntry.isDirectory()) {
                        String name = zipEntry.getName();
                        name = name.substring(0, name.length() - 1);
                        f = new File(outputDirectory + File.separator + name);
                        f.mkdirs();
                        continue;
                    }
                    int index = entryName.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxhSVg==")));
                    if (index != -1) {
                        df = new File(outputDirectory + File.separator + entryName.substring(0, index));
                        df.mkdirs();
                    }
                    if ((index = entryName.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")))) != -1) {
                        df = new File(outputDirectory + File.separator + entryName.substring(0, index));
                        df.mkdirs();
                    }
                    f = new File(outputDirectory + File.separator + zipEntry.getName());
                    in = zipFile.getInputStream(zipEntry);
                    out = new FileOutputStream(f);
                    byte[] by = new byte[1024];
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    out.flush();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    HVLog.printException(ex);
                    throw new IOException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BlcjOUZJAwtYEg8rA1cNPUUWJVc=")) + ex.toString());
                }
                finally {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (IOException ex) {
                            HVLog.printException(ex);
                        }
                    }
                    if (out == null) continue;
                    try {
                        out.close();
                    }
                    catch (IOException ex) {
                        HVLog.printException(ex);
                    }
                }
            }
        }
        catch (IOException ex) {
            HVLog.printException(ex);
        }
        finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                }
                catch (IOException ex) {
                    HVLog.printException(ex);
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath, ZipCallback zipCallback) throws IOException {
        FileInputStream in = null;
        try {
            if (!fileOrDirectory.isDirectory()) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                in = new FileInputStream(fileOrDirectory);
                ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());
                zipCallback.callbackName(fileOrDirectory.getName());
                out.putNextEntry(entry);
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
            } else {
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; ++i) {
                    ZipTools.zipFileOrDirectory(out, entries[i], curPath + fileOrDirectory.getName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")), zipCallback);
                }
            }
        }
        catch (IOException ex) {
            HVLog.printException(ex);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    HVLog.printException(ex);
                }
            }
        }
    }

    static {
        SECRET_KEY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKW8kJwFPVjhF"));
        result = 0;
    }

    public static interface UnZipCallback {
        public void callbackName(String var1);
    }

    public static interface ZipCallback {
        public void callbackName(String var1);
    }
}

