/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IInterface
 */
package com.lody.virtual.client.hook.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.client.hook.providers.ExternalProviderHook;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class DownloadProviderHook
extends ExternalProviderHook {
    private static final String TAG;
    private static final String COLUMN_NOTIFICATION_PACKAGE;
    private static final String COLUMN_NOTIFICATION_CLASS;
    public static final String COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI;
    public static final String COLUMN_VISIBILITY;
    public static final String COLUMN_DESCRIPTION;
    public static final String COLUMN_FILE_NAME_HINT;

    DownloadProviderHook(IInterface base) {
        super(base);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object ... args) throws Throwable {
        VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRgALWojHiV9DgoNLwcYOWkFGgQ=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4+DmoJIFo=")) + method.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl8HJnsFSFo=")) + Arrays.toString(args));
        return super.invoke(proxy, method, args);
    }

    @Override
    public Uri insert(MethodBox methodBox, Uri url, ContentValues initialValues) throws InvocationTargetException {
        VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRgALWojHiV9DgoNLwcYOWkFGgQ=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKWgaFgZ3MCRF")) + initialValues);
        String notificationPkg = initialValues.getAsString(COLUMN_NOTIFICATION_PACKAGE);
        VLog.e(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRgALWojHiV9DgoNLwcYOWkFGgQ=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOC99JCAgKQdfDmIzJC1+MzxF")) + notificationPkg);
        if (notificationPkg == null) {
            return (Uri)methodBox.call();
        }
        initialValues.put(COLUMN_NOTIFICATION_PACKAGE, VirtualCore.get().getHostPkg());
        initialValues.put(COLUMN_VISIBILITY, Integer.valueOf(1));
        String hint = initialValues.getAsString(COLUMN_FILE_NAME_HINT);
        String replaceHint = hint.replace(notificationPkg, VirtualCore.get().getHostPkg());
        initialValues.put(COLUMN_FILE_NAME_HINT, replaceHint);
        return super.insert(methodBox, url, initialValues);
    }

    @Override
    public Cursor query(MethodBox methodBox, Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, Bundle originQueryArgs) throws InvocationTargetException {
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KgcuM28gDSh3MCQpKAdbPW4KBi9lJx0xPQhSVg==")) + selection + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186P28jPAN3MCRF")) + Arrays.toString(selectionArgs));
        return super.query(methodBox, url, projection, selection, selectionArgs, sortOrder, originQueryArgs);
    }

    static {
        COLUMN_NOTIFICATION_PACKAGE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOC99JCAgKQdfDmozQSlqJzguLhhSVg=="));
        COLUMN_NOTIFICATION_CLASS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4ALGUVOC99JCAgKQdfDm4FODdsJDBF"));
        COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2H2wjAgNjDiwoKAZfMW8YNCxlJCAbLAgAO2IVNF9qDhpF"));
        COLUMN_VISIBILITY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YKWUVFi9gHgYgLQhSVg=="));
        COLUMN_DESCRIPTION = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguKWswFi9hEQozKi0YVg=="));
        COLUMN_FILE_NAME_HINT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBgYCGwFSFo="));
        TAG = DownloadProviderHook.class.getSimpleName();
    }
}

