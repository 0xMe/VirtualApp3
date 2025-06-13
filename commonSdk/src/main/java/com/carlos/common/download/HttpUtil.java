/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.Call
 *  okhttp3.Callback
 *  okhttp3.FormBody
 *  okhttp3.FormBody$Builder
 *  okhttp3.Headers
 *  okhttp3.Headers$Builder
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.Response
 */
package com.carlos.common.download;

import com.kook.librelease.StringFog;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private OkHttpClient mOkHttpClient;
    private static HttpUtil mInstance;
    private static final long CONNECT_TIMEOUT = 60L;
    private static final long READ_TIMEOUT = 60L;
    private static final long WRITE_TIMEOUT = 60L;
    static Headers.Builder builder;

    public void downloadFileByRange(String url, long startIndex, long endIndex, Callback callback) throws IOException {
        Request request = new Request.Builder().header(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ijw+U2AxNFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj0YLGgaLzM=")) + startIndex + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("MwhSVg==")) + endIndex).addHeader(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgqM2ogMC9mEQZF"))).url(url).build();
        this.doAsync(request, callback);
    }

    public void getContentLength(String url, Callback callback) throws IOException {
        Headers headers = builder.set(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uPmgaFithN1RF")), url).build();
        FormBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).headers(headers).build();
        this.doAsync(request, callback);
    }

    private void doAsync(Request request, Callback callback) throws IOException {
        Call call = this.mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    private Response doSync(Request request) throws IOException {
        Call call = this.mOkHttpClient.newCall(request);
        return call.execute();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HttpUtil getInstance() {
        if (null != mInstance) return mInstance;
        Class<HttpUtil> clazz = HttpUtil.class;
        synchronized (HttpUtil.class) {
            if (null != mInstance) return mInstance;
            mInstance = new HttpUtil();
            // ** MonitorExit[var0] (shouldn't be in output)
            return mInstance;
        }
    }

    public HttpUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS);
        this.mOkHttpClient = builder.build();
    }

    static {
        builder = new Headers.Builder();
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguIGwJGiBmHl0oOhciKmozOC9oJzg/IxgAKk4jBitqHlEbOgcML2VSHjNvDjw7JQcuKGoaBgFvVigvIwgDIW9THQJOMxkoKQdXOWkFBSVvJygpKQQEI2AKPCJuClkqLD4qIXVSTAN1IF0eMgQhJXsVSFo=")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmkjSFo=")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc6PW8jJCxiCl0JKj4qPW4KGgRrDQ4fLhc+CWIFND9lJ1RF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg==")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWgaIAZODlE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLyRuN1geIgUlPWEJQDN8MxkbLy4pL2cILy1vVwEdCDo9O24KDStqICMdMzkhDmUjJzFhDVwsOjoMVg==")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnwxPDdoJx4bKggAD2NSHSNnDh49Ly4hJH0FJDV7DwZBDRYbL3UJPBF6IAYAOSoXOGMaIAJgHjBIKAcuXGwgASV/CjM+PCk1MktTBghnHzBBITohJGUVAj1oVjwIJAcuImwkATZmAQobIy4IM3o0DTBOMyc2PF85LXg3MwF8Vw0rIT4+In0FMCx8Iw08MwQpD38zSFo=")));
    }
}

