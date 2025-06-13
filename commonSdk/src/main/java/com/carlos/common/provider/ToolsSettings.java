/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.provider.BaseColumns
 */
package com.carlos.common.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import com.carlos.common.provider.ScorpionProvider;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;

public class ToolsSettings {

    public static final class ServerInfo
    extends Base
    implements BaseDataColumns {
        public static int ACCOUNT_TYPE_PHONE;
        public static int ACCOUNT_TYPE_ID;
        public static final String SERVER_INFO;
        public static final String SERVER_IP;
        public static final String SERVER_PORT;

        public static final Uri getContentUri(String packageName) {
            String URI2 = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmVgU1Oi5SVg==")) + ScorpionProvider.getAUTHORITY(packageName) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MyouKX4zSFo=")) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4ALGUVOD8=")) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwcqKmwVNFo="));
            return Uri.parse((String)String.format(URI2, SERVER_INFO));
        }

        public static Uri getContentUri(String packageName, long id2, boolean notify) {
            return Uri.parse((String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmVgU1Oi5SVg==")) + ScorpionProvider.getAUTHORITY(packageName) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + SERVER_INFO + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) + id2 + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Py5SVg==")) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4ALGUVOD8=")) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwhSVg==")) + notify));
        }

        public static String onCreateTable() {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgACGMwFit9AQo/JBciOG8zBSh+N1RF")));
            return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JisMWGMYMBVLHwoRJztbWn4wAitsNCQgKS4YKmIwDSN/H1kiLzpXBX02MFRgNShADRYiE2cITR9iHDM3JCwuGXoKLCthNzw/IzscKn42BhVmHC8dKT4uCGYwLDV9Hlk7IzpXXWMIRQp1V11F"));
        }

        static {
            SERVER_INFO = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmwjNARjDlk+Ki5SVg=="));
            SERVER_IP = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmwjNARrASRF"));
            SERVER_PORT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmwjNARpHh4qLBhSVg=="));
            ACCOUNT_TYPE_PHONE = 1;
            ACCOUNT_TYPE_ID = 2;
        }
    }

    public static interface BaseDataColumns
    extends BaseColumns {
    }

    public static abstract class Base {
    }
}

