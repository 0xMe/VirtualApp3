/*
 * Decompiled with CFR 0.152.
 */
package com.kook.network;

import com.kook.network.StringFog;

public final class Constant {
    public static final String UPLOAD_VERSION_V1_0 = StringFog.decrypt("HV5BWw==");
    public static final String UPLOAD_VERSION_V2_0 = StringFog.decrypt("HV1BWw==");

    public static interface API_URL {
        public static final String ENV_DEV = StringFog.decrypt("AxsbGxdNWlYUVEdJXVdBWgNRTV0VVlpARAsOH0wREBVbAxtX");
        public static final String ENV_PROD = StringFog.decrypt("AxsbGxdNWhBaEUcTBAAECEIQEEleDx0dUVdfWBVNEQZZBxodGRkKGQI=");
    }
}

