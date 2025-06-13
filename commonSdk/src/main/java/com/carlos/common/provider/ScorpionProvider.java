/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentProviderOperation
 *  android.content.ContentProviderResult
 *  android.content.ContentUris
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.OperationApplicationException
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.database.sqlite.SQLiteQueryBuilder
 *  android.net.Uri
 *  android.text.TextUtils
 *  android.widget.Toast
 *  androidx.annotation.NonNull
 *  androidx.annotation.Nullable
 */
package com.carlos.common.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.carlos.common.provider.ToolsSettings;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.util.ArrayList;

public class ScorpionProvider
extends ContentProvider {
    private static String DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;
    private static final int MATCH_CODE = 100;
    static final String PARAMETER_NOTIFY;
    private static DatabaseHelper mOpenHelper;

    public static String getAUTHORITY(String packageName) {
        String AUTHORITY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXogLClgJywsKQdfDn8aTQRlJCQaLgguCA=="));
        return AUTHORITY;
    }

    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(this.getContext());
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phc+I2gaFj9LVyQvIz0bOngVSFo=")) + uri.toString());
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(args.table);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor result = qb.query(db, projection, args.where, args.args, null, null, sortOrder);
        result.setNotificationUri(this.getContext().getContentResolver(), uri);
        Toast.makeText((Context)this.getContext(), (CharSequence)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4qPmgKLFo=")), (int)1).show();
        return result;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        SqlArguments args = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty((CharSequence)args.where)) {
            return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4cPHojJCZiESw1KQc1Dm4KGgRsJwY5PC4qI2E0Alo=")) + args.table;
        }
        return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4cPHojJCZiESw1KQc1Dm4KGgRsJwY5PC4YCmIKQCo=")) + args.table;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {
        SqlArguments args = new SqlArguments(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = ScorpionProvider.dbInsertAndCheck(mOpenHelper, db, args.table, null, initialValues);
        if (rowId <= 0L) {
            return null;
        }
        uri = ContentUris.withAppendedId((Uri)uri, (long)rowId);
        this.sendNotify(uri);
        return uri;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NonNull
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            ContentProviderResult[] contentProviderResultArray = results;
            return contentProviderResultArray;
        }
        finally {
            db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SqlArguments args = new SqlArguments(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numValues = values.length;
            for (int i = 0; i < numValues; ++i) {
                if (ScorpionProvider.dbInsertAndCheck(mOpenHelper, db, args.table, null, values[i]) >= 0L) continue;
                int n = 0;
                return n;
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        this.sendNotify(uri);
        return values.length;
    }

    private static long dbInsertAndCheck(DatabaseHelper helper, SQLiteDatabase db, String table, String nullColumnHack, ContentValues values) {
        long insertIndex = db.insert(table, nullColumnHack, values);
        return insertIndex;
    }

    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.delete(args.table, args.where, args.args);
        if (count > 0) {
            this.sendNotify(uri);
        }
        return count;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.update(args.table, values, args.where, args.args);
        if (count > 0) {
            this.sendNotify(uri);
        }
        return count;
    }

    private void sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRcMI2gVSFo=")).equals(notify)) {
            this.getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    static {
        PARAMETER_NOTIFY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4ALGUVOD8="));
        DATABASE_NAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki42D28gIC9gJFg2KBcuVg=="));
    }

    public static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;

        SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = (String)url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
            } else {
                if (url.getPathSegments().size() != 2) {
                    throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcLmsVHi9iVyRKOzsbIH4zSFo=")) + url);
                }
                if (!TextUtils.isEmpty((CharSequence)where)) {
                    throw new UnsupportedOperationException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ISxfWGchNyh9JFE7LAgqPX4zMCVvVjw6Khc6DmAjMD9uDjMzOD5SVg==")) + url);
                }
                this.table = (String)url.getPathSegments().get(0);
                this.where = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jy4YPH4VSFo=")) + ContentUris.parseId((Uri)url);
                this.args = null;
            }
        }

        public SqlArguments(Uri url) {
            if (url.getPathSegments().size() != 1) {
                throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcLmsVHi9iVyRKOzsbIH4zSFo=")) + url);
            }
            this.table = (String)url.getPathSegments().get(0);
            this.where = null;
            this.args = null;
        }
    }

    static class DatabaseHelper
    extends SQLiteOpenHelper {
        private final Context mContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.mContext = context;
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ToolsSettings.ServerInfo.onCreateTable());
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

