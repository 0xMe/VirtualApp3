/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Request
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.os.Handler
 *  android.os.Looper
 *  okhttp3.Call
 *  okhttp3.CookieJar
 *  okhttp3.MediaType
 *  okhttp3.MultipartBody
 *  okhttp3.MultipartBody$Builder
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.RequestBody
 *  okhttp3.Response
 *  org.jdeferred.Promise
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.select.Elements
 */
package com.carlos.common.clouddisk.http;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import com.carlos.common.App;
import com.carlos.common.clouddisk.http.FileProgressRequestBody;
import com.carlos.common.clouddisk.http.MyCookieJar;
import com.carlos.common.clouddisk.http.OkHttpUtil;
import com.carlos.common.clouddisk.listview.FileItem;
import com.carlos.common.ui.utils.LanzouHelper;
import com.carlos.common.utils.FileTools;
import com.carlos.common.utils.ResponseProgram;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jdeferred.Promise;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HttpWorker {
    private static HttpWorker mHttpWorker;
    private static final MediaType FROM_DATA;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private OkHttpClient mOkHttpClient;
    MyCookieJar cookieJar;

    private HttpWorker() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HttpWorker getInstance() {
        if (mHttpWorker != null) return mHttpWorker;
        Class<OkHttpUtil> clazz = OkHttpUtil.class;
        synchronized (OkHttpUtil.class) {
            if (mHttpWorker != null) return mHttpWorker;
            mHttpWorker = new HttpWorker();
            // ** MonitorExit[var0] (shouldn't be in output)
            return mHttpWorker;
        }
    }

    private boolean LoginSync(String username, String password) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4AKmoVRTdhJBpF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OQgqOXw0ODdPVhpF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQgYPA==")), username), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhcmPA==")), password), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oi5SVg==")))};
        String info = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAihsHlkgKi4pKmwVRSQ=")), rs, new OkHttpUtil.RequestData[0]);
        String url = "";
        JSONObject jsonObject = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + info + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
        url = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcPmozSFo=")));
        if (url.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwpcAkZJWh9YKRshAhxABw==")))) {
            return true;
        }
        if (url.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Bz8VIUNNDwhYEg8rA1cNPQ==")))) {
            MyCookieJar.resetCookies();
            return false;
        }
        MyCookieJar.resetCookies();
        return false;
    }

    public List<FileItem> getFolderInfoSync(String folder_id) throws Exception {
        ArrayList<FileItem> list = new ArrayList<FileItem>();
        list.clear();
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OV4mVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARsJAYw")), folder_id)};
        String response = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
        JSONObject jsonObject = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + response + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
        String folderInfo = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguIGwFSFo=")));
        HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARrDlk+KioIVg==")) + folderInfo);
        JSONArray folderJsonArray = new JSONArray(folderInfo);
        for (int i = 0; i < folderJsonArray.length(); ++i) {
            JSONObject folderObject = folderJsonArray.getJSONObject(i);
            String name = folderObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+DWgVSFo=")));
            String fol_id = folderObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmYzAiw=")));
            list.add(new FileItem(name, 1, fol_id));
        }
        return list;
    }

    public List<FileItem> getFileInfoSync(String folder_id) throws Exception {
        ArrayList<FileItem> list = new ArrayList<FileItem>();
        list.clear();
        for (int page = 1; page < 200; ++page) {
            OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OQhSVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARsJAYw")), folder_id), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgmVg==")), String.valueOf(page))};
            String data2 = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
            JSONObject jsonObject = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + data2 + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
            String text = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguIGwFSFo=")));
            if (text.length() == 2) break;
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject fileObject = jsonArray.getJSONObject(i);
                String name = fileObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+DWgYGjdgHlFF")));
                String file_id = fileObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgqVg==")));
                String fileUrl = this.getFileHrefSync(file_id);
                FileItem item = new FileItem(name, 0, file_id);
                item.setFileUrl(fileUrl);
                item.setTime(fileObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRgYDWgVSFo="))));
                item.setSizes(fileObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YImgVSFo="))));
                item.setDowns(fileObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgALWogLFo="))));
                list.add(item);
            }
        }
        return list;
    }

    public boolean setNewFolderSync(String uri, String folder_name) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[3];
        String[] folder_ids = uri.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwhSVg==")));
        String parent_id = folder_ids[folder_ids.length - 1];
        rs[0] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oj5SVg==")));
        rs[1] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+KmgVBgZsJAYw")), parent_id);
        rs[2] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARsJFk7KgcMVg==")), folder_name);
        String data = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
        String info = "";
        JSONArray jsonArray = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + data + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg==")));
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        info = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcPmozSFo=")));
        return info.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpcG0ZbQjJYAB8CAhkFHQ==")));
    }

    public boolean uploadFileSync(String file_uri, String folder_id, final UpLoadCallbackListener listener) throws Exception {
        OkHttpClient client = OkHttpUtil.getmOkHttpClient2();
        File file = new File(file_uri);
        String[] str = file_uri.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
        String name = str[str.length - 1];
        FileProgressRequestBody fileBody = new FileProgressRequestBody(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWg3PD1vJCMeLi4ACGAOQTBlNF0uKRgYKWsVNDB5NzA/OwgqOm4FLyllJBoxMwNfVg==")), file, new FileProgressRequestBody.ProgressListener(){

            @Override
            public void transferred(double size) {
                listener.Progress(size);
            }
        });
        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YImgVSFo=")), String.valueOf(file.length())).addFormDataPart(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg=="))).addFormDataPart(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARsJAYw")), folder_id).addFormDataPart(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+DWgVSFo=")), name).addFormDataPart(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc6DmozJCxsJDwzKhcMVg==")), name, (RequestBody)fileBody).build();
        Request request = new Request.Builder().url(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAiFvDl0uIy1WKmwVRSQ="))).post((RequestBody)mBody).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxctLGhTIDdsETMgKDotKmMwBT58MDs7IActCU83GiE="))) != -1;
    }

    private boolean deleteFolderSync(String holder_id) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oi5SVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4ADmgFNARsJAYw")), holder_id)};
        String data = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
        String info = "";
        JSONArray jsonArray = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + data + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg==")));
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        info = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcPmozSFo=")));
        return info.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxpcOENNDyxYAB8CAhkFHQ==")));
    }

    private boolean deleteFileSync(String file_id) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OT5SVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgYGi9iEVRF")), file_id)};
        String data = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
        String info = "";
        JSONArray jsonArray = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + data + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg==")));
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        info = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcPmozSFo=")));
        return info.equals(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BxsjKkZJRihZExsw")));
    }

    public long downFileSync(String fileName, String fileId) throws Exception {
        String fileHref = this.getFileHrefSync(fileId);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYAzETAlcBLH43TChrNx4dLhZfCGIKIiodTQ5F")) + fileHref);
        LanzouHelper.Lanzou lanZouRealLink = LanzouHelper.getLanZouRealLink(fileHref);
        return this.downLoadDatabase(lanZouRealLink.getDlLink(), fileName);
    }

    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    private void testkook(String url) throws IOException {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgALWogIARgJCg/Iy4qVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg==")))};
        OkHttpUtil.RequestData[] header = new OkHttpUtil.RequestData[13];
        header[0] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWwaAiVlMwUrKgguPGZTAi1pATgqLAgYCGoKICB6DT89CCkEVg==")));
        header[1] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmknOyhoNApF")));
        header[2] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODFE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLFo=")));
        header[3] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGojNClmHgY1Kj5SVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC4uM28JEjdgHgYuKAhSVg==")));
        header[4] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV10OKAcYM2UzFlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OgM9Kg==")));
        header[5] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV11LLQgmPQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWg3PD1vJCMeLi4ACGAOQTBlNF0uKRgYKWsVNDA=")));
        header[6] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBgAKWwFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+CGkjGgVhIFk5Ki1XVg==")));
        header[9] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgVXDWkzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4AKm8zSFo=")));
        header[10] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgYqMWUzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4+DWhSEiVhNAY9KQcYVg==")));
        header[11] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnw2Ei9lNywcKj01JGgxESN1DSMdPDk9JGcjAgR8IC8uDRgbPXpTATZmJFEdIxguB2gVFgtjAQ01PAQpI39TDT54VllNOwUqAWhTTCNsHhoaLypXG2sFLD1qMxE3Iy0cOWwgTTF/CgEhMzk5CH80RAJ3ClgrMyotOmIFQS5oDgoaPDktD0wkRDZ6N1RF")));
        header[12] = new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IF8IDGgaJAViASggKAc1D30FLAZqEVRF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IBYIQH0KMAZhHyw/IwgMPWoKBlo=")));
        String data = OkHttpUtil.postSyncString(url, rs, header);
        FileTools.saveAsFileWriter(this.getSavePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqDWoJBgZnEQpF")), data);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRg+LGtTTVo=")) + data);
    }

    private String GetDownUri(String fileSecondHref) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgALWogIARgJCg/Iy4qVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg==")))};
        OkHttpUtil.RequestData[] header = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWwaAiVlMwUrKgguPGZTAi1pATgqLAgYCGoKICB6DT89CCkEVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmknOyhoNApF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODFE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGojNClmHgY1Kj5SVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC4uM28JEjdgHgYuKAhSVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV10OKAcYM2UzFlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OgM9Kg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV11LLQgmPQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWg3PD1vJCMeLi4ACGAOQTBlNF0uKRgYKWsVNDA="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBgAKWwFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+CGkjGgVhIFk5Ki1XVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oy0MCWgzAiY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC4pDm8zQSZuNwYwIyocJWAgQVo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ij4uPmgaFithN1RF")), fileSecondHref), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgVXDWkzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4AKm8zSFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgYqMWUzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4+DWhSEiVhNAY9KQcYVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnw2Ei9lNywcKj01JGgxESN1DSMdPDk9JGcjAgR8IC8uDRgbPXpTATZmJFEdIxguB2gVFgtjAQ01PAQpI39TDT54VllNOwUqAWhTTCNsHhoaLypXG2sFLD1qMxE3Iy0cOWwgTTF/CgEhMzk5CH80RAJ3ClgrMyotOmIFQS5oDgoaPDktD0wkRDZ6N1RF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IF8IDGgaJAViASggKAc1D30FLAZqEVRF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IBYIQH0KMAZhHyw/IwgMPWoKBlo=")))};
        String data = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC4pDm8zQSZuNwYwIyocJWAgQCo=")), rs, header);
        FileTools.saveAsFileWriter(this.getSavePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqDWoJBgZnEQpF")), data);
        JSONObject jsonObject = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + data + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
        return jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQcMDg==")));
    }

    public String getSavePath() {
        String path = Build.VERSION.SDK_INT > 29 ? App.getApp().getExternalFilesDir(null).getAbsolutePath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")) : Environment.getExternalStorageDirectory().getPath() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg=="));
        return path;
    }

    private Response getSync(String url) throws IOException {
        if (this.mOkHttpClient == null) {
            this.cookieJar = new MyCookieJar();
            this.mOkHttpClientBuilder = new OkHttpClient.Builder();
            this.mOkHttpClientBuilder.cookieJar((CookieJar)this.cookieJar);
            this.mOkHttpClientBuilder.sslSocketFactory(HttpWorker.createSSLSocketFactory(), (X509TrustManager)new OkHttpUtil.TrustAllCerts());
            this.mOkHttpClientBuilder.hostnameVerifier((HostnameVerifier)new OkHttpUtil.TrustAllHostnameVerifier());
            this.mOkHttpClient = this.mOkHttpClientBuilder.build();
        }
        Request.Builder builder = new Request.Builder().url(url);
        String HONEYCOMB_USERAGENT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnwxOC9lNCgzPzo6XHckOA5sNDA7KQg2IHhTLwR/V1w3JAdeJGoFMyt+Mgo6Iy4HOGMgNC9gHg01IRVXXXpTBS94Hzg7KQgEJ24gLCVnJBo9OQMfD39SASN/Mz8/LSscWGQIQAR+NyQwLC4tOGAzNCljJBEzPxY+PWoaAi9lJx0cOgQbDksbNCRuNCQ7KiotCXwkMwR/VzBF"));
        builder.addHeader(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV11LLQgmPQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWg3PD1vJCMeLi4ACGAOQTBlNF0uKRgYKWsVNDA=")));
        builder.addHeader(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), HONEYCOMB_USERAGENT);
        Request request = builder.get().build();
        Call call = this.mOkHttpClient.newCall(request);
        Response response = call.execute();
        return response;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IRYEAw==")));
            sc.init(null, new TrustManager[]{new OkHttpUtil.TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ssfFactory;
    }

    public String getFileHrefSync(String file_id) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRg+KWUzSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OjkMVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgYGi9iEVRF")), file_id)};
        String uri = "";
        String response = OkHttpUtil.postSyncString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4sLykYI28FNDJlJwYcPC42KWAOAi9sJwo5KT4uO2tSBiRlDjxF")), rs, new OkHttpUtil.RequestData[0]);
        JSONObject jsonObject = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + response + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
        String info = jsonObject.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgcPmozSFo=")));
        JSONObject jsonObject2 = new JSONArray(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IC5SVg==")) + info + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JwhSVg=="))).getJSONObject(0);
        String f_id = jsonObject2.getString(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LTsACWgFSFo=")));
        uri = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC4pDm8zQSZuNwYwIyocJWAgQCo=")) + f_id;
        return uri;
    }

    private String getFileSecondHref(String file_href) throws Exception {
        String data = OkHttpUtil.getSyncString(file_href);
        if (data.contains(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwkFM0YyD1dYEws+OjkXDkdNGxdBX149AEQdX1gBLRVEEBtKAQs3LUFbWlc=")))) {
            throw new IOException();
        }
        Document document = Jsoup.parse((String)data);
        Elements element = document.getElementsByClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgiKn8jSFo=")));
        return element.attr(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0MOQ==")));
    }

    private String GetDownKey(String fileSecondHref) throws Exception {
        String uri = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC4pDm8zQSZuNwYwIyocJWAgQVo=")) + fileSecondHref;
        String data = OkHttpUtil.getSyncString(uri);
        Document document = Jsoup.parse((String)data);
        String str = document.getElementsByTag(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki42KmUaIAY="))).toString().trim();
        int a = str.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LghXP2kFMDdmHiM8PgMlMw==")));
        int b = str.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LisAOQ==")));
        return str.substring(a + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LghXP2kFMDdmHiM8PgMlMw==")).length(), b + 3);
    }

    private String GetDownUri(String fileSecondHref, String sign) throws Exception {
        OkHttpUtil.RequestData[] rs = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgALWogIARgJCg/Iy4qVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWojSFo=")), sign), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg==")))};
        OkHttpUtil.RequestData[] header = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWwaAiVlMwUrKgguPGZTAi1pATgqLAgYCGoKICB6DT89CCkEVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmknOyhoNApF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODFE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGojNClmHgY1Kj5SVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LC4uM28JEjdgHgYuKAhSVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV10OKAcYM2UzFlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OgM9Kg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ji4ACGwFNCZmV11LLQgmPQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGoFAil9AQozKi0XDWg3PD1vJCMeLi4ACGAOQTBlNF0uKRgYKWsVNDA="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBgAKWwFSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+CGkjGgVhIFk5Ki1XVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oy0MCWgzAiY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4tLC45Dm8zQSZuNwYwKTocJWAgQVo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ij4uPmgaFithN1RF")), fileSecondHref), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgVXDWkzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4AKm8zSFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii4uOXobOCtmHig0OgYqMWUzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4+DWhSEiVhNAY9KQcYVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnw2Ei9lNywcKj01JGgxESN1DSMdPDk9JGcjAgR8IC8uDRgbPXpTATZmJFEdIxguB2gVFgtjAQ01PAQpI39TDT54VllNOwUqAWhTTCNsHhoaLypXG2sFLD1qMxE3Iy0cOWwgTTF/CgEhMzk5CH80RAJ3ClgrMyotOmIFQS5oDgoaPDktD0wkRDZ6N1RF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IF8IDGgaJAViASggKAc1D30FLAZqEVRF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IBYIQH0KMAZhHyw/IwgMPWoKBlo=")))};
        return "";
    }

    public String GetDownSecondUri(String downUri) throws Exception {
        String address = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4uKQglDmk0TCZoNzgaLgcuDn0KRClpJFkcOQgEI2UVNwM=")) + downUri;
        String path = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4iCWoFNyU=")) + downUri;
        OkHttpUtil.RequestData[] header = new OkHttpUtil.RequestData[]{new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LgcuLGUFGgRjAQoZ")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4YKHojMwJONCw7KQc2LWozQSZ1NzAcLBhSVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwguLGUFGiw=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSwuBg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+LGUFSFo=")), path), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki42CmgVEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguIGwJGiBmHl0oOhciKmozOC9oJzg/IxgAKk4jBitqHlEbOgcML2VSHjNvDjw7JQcuKGoaBgFvVigvIwgDIW9THQJOMxkoKQdXOWkFBSVvJygpKQQEI2AKPCJuClkqLD4qIXVSTAN1IF0eMgQhJXtTQTVqNFE7LAg2P2wFAiVgMB4pKQc6DmkjASNrDlkqIwg+KmIgLz5qM1ErPAM+DXkOIwR5EVRF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWgaIAZODjA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmknOyhoNApF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWgaIAZODlE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uOXoVOCtmHig0OgdXDWkzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+LmUVPDdmHjBF"))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uOXoVOCtmHig0OggqMWUzGlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4ACGgVSFo="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc6PW8jJCxiCl0zKj4qPW4KGgRrDQ45Lhc+CWIFND9lJ1RF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg=="))), new OkHttpUtil.RequestData(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQc2M28nEjdiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnw2Ei9lNywcKj01JGgxESN1DSMdPDk9JGcjAgR8IC8uDRgbPXpTATZmJFEdIxguB2gVFgtjAQ01PAQpI39TDT54VllNOwUqAWhTTCNsHhoaLypXG2sFLD1qMxE3Iy0cOWwgTTF/CgEgMzk5CH80DQZMClgrMwQ5OmIFQS5oDgoaPDktD0wkRDZ6N1RF")))};
        return OkHttpUtil.getSync(address, header).request().url().toString();
    }

    private long downLoadDatabase(String downSecondUri, String fileName) {
        String[] filename = downSecondUri.split(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwhSVg==")));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse((String)downSecondUri));
        DownloadManager downloadManager = (DownloadManager)App.getApp().getSystemService(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgALWojHiV9DgpF")));
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BwwFOUZbGw9YXh8XA1dAJQ==")));
        return downloadManager.enqueue(request);
    }

    public static Promise<Boolean, Throwable, Void> Login(String username, String password, loginCallbackListener listener) {
        return ResponseProgram.defer().when(() -> {
            try {
                boolean cookiesActivation = MyCookieJar.isCookiesActivation();
                HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4AD2UzAithIiA5LBccLG4gBi9lJx0x")) + cookiesActivation);
                if (cookiesActivation) {
                    return true;
                }
                return HttpWorker.getInstance().LoginSync(username, password);
            }
            catch (Exception e) {
                HVLog.printException(e);
                return false;
            }
        }).done(success -> {
            if (success.booleanValue()) {
                listener.onFinish();
            } else {
                listener.onError(new Exception());
            }
        }).fail(throwable -> listener.onError((Throwable)throwable));
    }

    public static Promise<List<FileItem>, Throwable, Void> UpdatePage(String folder_id, PageUpdatePageCallbackListener listener) {
        return ResponseProgram.defer().when(() -> {
            try {
                return HttpWorker.getInstance().getFolderInfoSync(folder_id);
            }
            catch (Exception e) {
                HVLog.printException(e);
                return null;
            }
        }).done(fileItemList -> {
            if (fileItemList != null) {
                listener.onFinish((List<FileItem>)fileItemList);
            } else {
                listener.onError(new Exception());
            }
        }).fail(throwable -> listener.onError((Throwable)throwable));
    }

    public static void AddFolder(final String uri, final String folder_name, final CRUDCallbackListener listener) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    final Boolean aBoolean = HttpWorker.getInstance().setNewFolderSync(uri, folder_name);
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            if (aBoolean.booleanValue()) {
                                listener.onFinish();
                            } else {
                                listener.onError(new Exception());
                            }
                        }
                    });
                }
                catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public static void FileDown(final String fileName, final String fileId) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    HttpWorker.getInstance().downFileSync(fileName, fileId);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void DeleteFile(final String file_id, final CRUDCallbackListener listener) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    final Boolean aBoolean = HttpWorker.getInstance().deleteFileSync(file_id);
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            if (aBoolean.booleanValue()) {
                                listener.onFinish();
                            } else {
                                listener.onError(new Exception());
                            }
                        }
                    });
                }
                catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public static void DeleteFolder(final String folder_id, final CRUDCallbackListener listener) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    final Boolean aBoolean = HttpWorker.getInstance().deleteFolderSync(folder_id);
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            if (aBoolean.booleanValue()) {
                                listener.onFinish();
                            } else {
                                listener.onError(new Exception());
                            }
                        }
                    });
                }
                catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public static void sendFile(final String file_uri, final String folder_id, final UpLoadCallbackListener listener) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    final boolean aBoolean = HttpWorker.getInstance().uploadFileSync(file_uri, folder_id, listener);
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            if (aBoolean) {
                                listener.onFinish(0);
                            } else {
                                listener.onError(0);
                            }
                        }
                    });
                }
                catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            listener.onError(0);
                            e.printStackTrace();
                        }
                    });
                }
            }
        }).start();
    }

    public static void sendFiles(final List<String> file_uris, final String folder_id, final UpLoadCallbackListener listener) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < file_uris.size(); ++i) {
                    try {
                        final boolean aBoolean = HttpWorker.getInstance().uploadFileSync((String)file_uris.get(i), folder_id, listener);
                        final int finalI = i;
                        new Handler(Looper.getMainLooper()).post(new Runnable(){

                            @Override
                            public void run() {
                                if (aBoolean) {
                                    listener.onFinish(finalI);
                                } else {
                                    listener.onError(finalI);
                                }
                            }
                        });
                        continue;
                    }
                    catch (Exception e) {
                        final int finalI1 = i;
                        new Handler(Looper.getMainLooper()).post(new Runnable(){

                            @Override
                            public void run() {
                                listener.onError(finalI1);
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    static {
        FROM_DATA = MediaType.parse((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwcuDmwFAgJ9ASwgOi0+DWoVPyNrETg/LRhSVg==")));
    }

    public static interface UpLoadCallbackListener {
        public void onError(int var1);

        public void Progress(double var1);

        public void onFinish(int var1);
    }

    public static interface CRUDCallbackListener {
        public void onError(Throwable var1);

        public void onFinish();
    }

    public static interface PageUpdatePageCallbackListener {
        public void onError(Throwable var1);

        public void onFinish(List<FileItem> var1);
    }

    public static interface loginCallbackListener {
        public void onError(Throwable var1);

        public void onFinish();
    }
}

