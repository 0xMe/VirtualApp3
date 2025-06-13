/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.net.Uri
 *  android.os.Environment
 *  android.os.Handler
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Images$Thumbnails
 *  android.text.TextUtils
 */
package com.carlos.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

public class FileTools {
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return FileTools.deleteSingleFile(delFile);
        }
        return FileTools.deleteDirectory(delFile);
    }

    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    private static boolean deleteDirectory(String filePath) {
        File[] files;
        File dirFile;
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        if (!(dirFile = new File(filePath)).exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        for (File file : files = dirFile.listFiles()) {
            if (file.isFile() ? !(flag = FileTools.deleteSingleFile(file.getAbsolutePath())) : file.isDirectory() && !(flag = FileTools.deleteDirectory(file.getAbsolutePath()))) break;
        }
        if (!flag) {
            return false;
        }
        return dirFile.delete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean saveAsFileWriter(String filePath, String content) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Bwk/WkYWQj5YXhNTAhtABEctEwJ+N1RF")) + filePath);
        File file = new File(filePath);
        file.deleteOnExit();
        OutputStreamWriter fwriter = null;
        try {
            fwriter = new FileWriter(filePath, false);
            fwriter.write(content);
            boolean bl = true;
            return bl;
        }
        catch (IOException ex) {
            HVLog.printException(ex);
        }
        finally {
            try {
                fwriter.flush();
                fwriter.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String saveBitmap(Bitmap bm, String imageFile) {
        File file;
        String filename = imageFile.substring(imageFile.lastIndexOf(File.separator) + 1);
        String filepath = imageFile.substring(0, imageFile.lastIndexOf(File.separator));
        File path = new File(filepath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if ((file = new File(filepath, filename)).exists()) {
            file.delete();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            return file.getAbsolutePath();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void saveBitmap(final Context context, Bitmap bm, boolean updateAlum) {
        String fileName = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4+LmhSBgJgNDhF"));
        final File file = new File(Environment.getExternalStoragePublicDirectory((String)""), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)out);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (updateAlum) {
            try {
                MediaStore.Images.Media.insertImage((ContentResolver)context.getContentResolver(), (String)file.getAbsolutePath(), (String)fileName, null);
                String[] projection = new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4YPA==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJFo="))};
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4YPHsOHSh0J1RF")), new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OgMLKQ=="))}, null);
                while (cursor.moveToNext()) {
                }
                context.sendBroadcast(new Intent(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42QQpmHBoAJQUYH2ALBl9gHAoALysuAmQxRVVkJSQK")), Uri.parse((String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmhTTCVOJ1RF")) + file.getPath()))));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    String where = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4qP2wFJyhgHgYxKAMlOA==")) + file.getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PQQMVg=="));
                    int i = context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, null);
                    if (i > 0) {
                        // empty if block
                    }
                }
            }, 15000L);
        }
    }

    public static String readFile(String filePath) {
        String content = "";
        try {
            FileInputStream instream = new FileInputStream(filePath);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader((InputStream)instream, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQUqW3pTRVo=")));
                BufferedReader buffreader = new BufferedReader(inputreader);
                char[] chars = new char[1024];
                int len = 0;
                while ((len = inputreader.read(chars)) != -1) {
                    content = content + new String(chars, 0, len);
                }
                ((InputStream)instream).close();
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return content;
    }

    public static int copyDir(String fromFile, String toFile) {
        File root = new File(fromFile);
        if (!root.exists()) {
            return -1;
        }
        File[] currentFiles = root.listFiles();
        File targetDir = new File(toFile);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        for (int i = 0; i < currentFiles.length; ++i) {
            if (currentFiles[i].isDirectory()) {
                FileTools.copyDir(currentFiles[i].getPath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")), toFile + currentFiles[i].getName() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
                continue;
            }
            FileTools.copyFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
        }
        return 0;
    }

    public static String getFileNameByPath(String path, boolean suffix) {
        int end;
        if (path == null) {
            return "";
        }
        int start = path.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
        int n = end = suffix ? path.length() : path.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5SVg==")));
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);
        }
        return "";
    }

    public static String getFileDirByPath(String path) {
        if (path == null) {
            return "";
        }
        int start = 0;
        int end = path.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
        if (start != -1 && end != -1) {
            return path.substring(start, end + 1);
        }
        return "";
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; ++i) {
                temp = oldPath.endsWith(File.separator) ? new File(oldPath + file[i]) : new File(oldPath + File.separator + file[i]);
                if (temp.isFile()) {
                    int len;
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + temp.getName().toString());
                    byte[] b = new byte[5120];
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (!temp.isDirectory()) continue;
                FileTools.copyFolder(oldPath + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + file[i], newPath + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + file[i]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxwnREZJRj5YEF5KAlcdCkdNGxdBX149PQArBhpSHlo=")) + newPath + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFGiRiHjwzKhcLDmkgFi9sJCw6MwQXPg==")) + oldfile.exists());
            if (oldfile.exists()) {
                FileInputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = ((InputStream)inStream).read(buffer)) != -1) {
                    System.out.println(bytesum += byteread);
                    fs.write(buffer, 0, byteread);
                }
                ((InputStream)inStream).close();
            }
        }
        catch (Exception e) {
            HVLog.printException(e);
        }
    }

    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PiobKH8FSFo=")));
        String fileSizeString = "";
        fileSizeString = fileS < 1024L ? df.format((double)fileS) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jj5SVg==")) : (fileS < 0x100000L ? df.format((double)fileS / 1024.0) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JC5SVg==")) : (fileS < 0x40000000L ? df.format((double)fileS / 1048576.0) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwhSVg==")) : df.format((double)fileS / 1.073741824E9) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JS5SVg=="))));
        return fileSizeString;
    }

    public static String getFileNameWithParams(String path, int type) {
        if (TextUtils.isEmpty((CharSequence)path) || type <= 0 || type > 4) {
            throw new RuntimeException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PlsnJBwNMRUGFgsLXDU7Bx87BzQVPy0zEQQ6Vg==")));
        }
        int start = path.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
        if (start != -1) {
            if (type == 1) {
                return path.substring(start + 1);
            }
            if (type == 2) {
                return path.substring(0, start + 1);
            }
            if (type == 3) {
                int index = path.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Mz5SVg==")));
                return path.substring(index + 1);
            }
            if (type == 4) {
                String substring = path.substring(0, start);
                int indexOf = substring.lastIndexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
                if (indexOf != -1) {
                    return substring.substring(indexOf + 1);
                }
                return "";
            }
            return "";
        }
        return "";
    }

    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            if (file.exists()) {
                file.deleteOnExit();
            } else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                ((OutputStream)os).write(buffer, 0, bytesRead);
            }
            ((OutputStream)os).close();
            ins.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

