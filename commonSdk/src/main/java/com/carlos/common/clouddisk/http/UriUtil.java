/*
 * Decompiled with CFR 0.152.
 */
package com.carlos.common.clouddisk.http;

import com.kook.librelease.StringFog;
import java.util.ArrayList;
import java.util.List;

public class UriUtil {
    public static final List<String> HttpPaths;
    public static final String LOGIN;
    public static final String TASK;
    public static final String UPLOADFILE;
    public static final String SHAREHEAD;
    public static final String DOWNFILEHEAD;
    public static final String DOWNFILEPATH;
    public static final String MYGITHUB = "";

    static {
        LOGIN = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAihsHlkgKi4pKmwVRSQ="));
        TASK = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF"));
        UPLOADFILE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAiFvDl0uIy1WKmwVRSQ="));
        SHAREHEAD = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC4pDm8zQSZuNwYwIyocJWAgQCo="));
        DOWNFILEHEAD = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4uKQglDmk0TCZoNzgaLgcuDn0KRClpJFkcOQgEI2UVNwM="));
        DOWNFILEPATH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4iCWoFNyU="));
        HttpPaths = new ArrayList<String>();
    }
}

