/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  okhttp3.Call
 *  okhttp3.Callback
 *  okhttp3.FormBody
 *  okhttp3.FormBody$Builder
 *  okhttp3.Headers
 *  okhttp3.Headers$Builder
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.RequestBody
 *  okhttp3.Response
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.carlos.common.ui.utils;

import android.text.TextUtils;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LanzouHelper {
    static Headers.Builder builder = new Headers.Builder();

    public static String getLanZouDownLink(final String filePath, String url) {
        Headers headers = builder.set(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uPmgaFithN1RF")), url).build();
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).post((RequestBody)formBody).headers(headers).build();
        try {
            client.newCall(request).enqueue(new Callback(){

                public void onFailure(Call call, IOException e) {
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        InputStream is = null;
                        FileOutputStream fos = null;
                        is = response.body().byteStream();
                        String path = filePath;
                        File file = new File(path, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguKWwJBjJjASRF")));
                        try {
                            fos = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            long fileSize = response.body().contentLength();
                            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT4YDmgYLC9nNDMi")) + fileSize);
                            long sum = 0L;
                            int porSize = 0;
                            while ((len = is.read(bytes)) != -1) {
                                fos.write(bytes);
                                porSize = (int)((float)(sum += (long)len) * 1.0f / (float)fileSize * 100.0f);
                                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PwMHO35THTN0DVwdPgRWJXskPzN5CgEoOF4IVg==")));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        HVLog.i(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("B1ZcXkMWHzNYAB8CAhkFHQ==")));
                    }
                }
            });
            return null;
        }
        catch (Exception e) {
            HVLog.printException(e);
            return null;
        }
    }

    public static Lanzou getLanZouRealLink(String url) {
        String fullHost = LanzouHelper.getFullHost(url);
        HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LT0uDmoLRSVhJw0i")) + fullHost);
        Headers headers = builder.set(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uPmgaFithN1RF")), url).build();
        String name = null;
        String size = null;
        try {
            Document doc = Jsoup.connect((String)url).get();
            Elements title1 = doc.select(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgYLmEwLAZnDlE/Pgc+DW8aASNsJx4xLl5WJE8nODNrVgI9Ly0MCnUFJAJlESA5MTkiKm4KAiJpJFguKhg+PGgFAiZiIwU8PAQ+Kmg3TAJsHlgrOSk6DmdTOzNlERk0JS5SVg==")));
            name = title1.size() != 0 ? ((Element)title1.get(0)).html() : doc.select(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pi4iCWoFNCZ9DgI7LRhSVg=="))).html();
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+DWhWBzRVN1RF")) + name);
            Elements size1 = doc.select(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwguLGsYQSZ9Dl0/Pgc2PWoFAgRqDjw/IxgAKmwFSFo=")));
            if (size1.size() != 0) {
                size = LanzouHelper.regex(size1.attr(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ACGwFNCZmEVRF"))), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JxgpCGYFMyB0IwU8OQMIGWYnPFdoDQ4xIF9bVg==")));
            }
            Elements realPage = doc.select(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAgiKmsVEis=")));
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uP2oIIDdiJAg1W0QIVg==")) + realPage);
            String realUrl = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB5F")) + fullHost + realPage.attr(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0MOQ==")));
            doc = Jsoup.connect((String)realUrl).get();
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Kj4uP2oINARgUxMeUj5SVg==")) + realUrl);
            Elements select = doc.select(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki42KmUaIAZvJwoZIxcLJWUzGjBvVgYhLRciO2EgNDVvASA9JS5SVg==")));
            Element element = (Element)select.get(1);
            String jshtml = element.html();
            String js = ((Element)select.get(1)).html();
            String jsonData = LanzouHelper.matcherJSON(jshtml);
            String sign = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWojSFo=")), jsonData);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWorBzRVN1RF")) + sign);
            String ves = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKQ==")), jsonData);
            if (TextUtils.isEmpty((CharSequence)ves)) {
                ves = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg=="));
            }
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKUAGG1c=")) + ves);
            String action = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiY=")), jsonData);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiZeK0ZN")) + action);
            String signs = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LghXP2kFMDdmHiBF")), jsonData);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWogFCUZTQJF")) + signs);
            if (TextUtils.isEmpty((CharSequence)signs)) {
                signs = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LghXP2kFMDdmHiBF"));
            }
            String webSignKey = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4uOm8zAi1gNA4/LQhSVg==")), jsonData);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4uOmczAi1gMg4/LR9cJhUVSFo=")) + webSignKey);
            if (TextUtils.isEmpty((CharSequence)webSignKey)) {
                webSignKey = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4uOmczAi1gMg4/LQhSVg=="));
            }
            String webSign = "";
            String apiUrl = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB5F")) + fullHost + LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQcMDg==")), js);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6CWQaFiReK0ZN")) + apiUrl);
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder().add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWojSFo=")), sign).add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2LGUVGiY=")), action).add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YPWogLFo=")), signs).add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4uOm8zAi1gN1RF")), webSign).add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KS4uOm8zAi1gNA4/LQhSVg==")), webSignKey).add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KT4uKQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg=="))).build();
            Request request = new Request.Builder().url(apiUrl).post((RequestBody)formBody).headers(headers).build();
            String dl = null;
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQgcM2kKICt9Jwo/KF4mP28FBit4EVRF")) + response);
                }
                String body = response.body().string();
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4APGlTTVo=")) + body);
                String dom = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgADQ==")), body).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("J18AVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4APGlSICxgJFwi")) + dom);
                String file = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4iCWoFNyU="));
                String domurl = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQcMDg==")), body).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("J18AVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
                HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4APGlSICxgJF0vIz1aIA==")) + domurl);
                dl = LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LRgADQ==")), body).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("J18AVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg=="))) + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My4iCWoFNyU=")) + LanzouHelper.getJsonValue(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KQcMDg==")), body).replace(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("J18AVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("My5SVg==")));
                return new Lanzou(name, size, dl);
            }
            catch (Exception e) {
                HVLog.printException(e);
                return new Lanzou(name, size, dl);
            }
        }
        catch (IOException e) {
            HVLog.printException(e);
            return null;
        }
    }

    public static String matcherJSON(String content) {
        try {
            Pattern pattern = Pattern.compile(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PBgqP2wFJy90ICciP14AGGgIJB5sIgIQIF9XEmQOTCxgHh5F")));
            Matcher matcher = pattern.matcher(content);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+LGszRSthMgIPIisXIA==")) + matcher + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFEjdmHig0KAgtIA==")) + matcher.find() + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsFEjdmHig0KAgtDmkKRSVvDjwALD0uKmZTASx7N1RF")) + matcher.groupCount());
            String group = matcher.group(0);
            String[] strings = group.split("\n");
            String requestData = strings[1];
            int startIndexOf = requestData.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KC5SVg==")));
            int endIndexOf = requestData.indexOf(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LwhSVg==")));
            String substring = requestData.substring(startIndexOf, endIndexOf + 1);
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgIP2wFLCBiAS88KBciLm4nTQNvAQo6KgcMI2AwJz0=")) + substring);
            return substring;
        }
        catch (Exception e) {
            HVLog.printException(e);
            return "";
        }
    }

    private static String getJsonValue(String key, String json) {
        return LanzouHelper.regex(json, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PF4fJH4VSFo=")) + key + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IColOmZTBSBLVxkbJSoHJWMnESh6CgYIPjoMEUkORC54IBkoPSs9IXg2HQU=")));
    }

    private static String getMainHost(String url) {
        Pattern p = Pattern.compile(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PF4fJH4VRQZmESciOilfJmM3My9mIh0bIF9WOWxTRCt4Iw4sKQgmEnUzLARuARo0IBZWJWkgAipvDlkyJ18cOWogHilgJF0eKj0MLmszNARrJAIqLC0EJmMFHgFvDh4vKQciJWggHiBsIx5F")), 2);
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static String regex(String words, String regex) {
        Pattern p = Pattern.compile(regex, 2);
        Matcher matcher = p.matcher(words);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static String getFullHost(String url) {
        Pattern p = Pattern.compile(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("ICsbD3o2HSJ0JVA2ORcqDW8gOCllNAIbLhcqOGAjMCJoHigiLhciI2UzOANuATA2IhgMPX8FSFo=")), 2);
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<String> urls = new ArrayList<String>();
        urls.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB43LwcqI2wJMCRoARoxLD0uKU4wNCpsClkiJBYLDm8gPCN/AQEvJBhSVg==")));
        urls.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8KLzJOIB4uKQcYP2kjMAZoDjw7KTocKH0KRT1sJwoeORgYKWUJGgV8DihBCS1XJGVSESBsEVRF")));
        for (String url : urls) {
            System.out.println(LanzouHelper.getLanZouRealLink(url));
        }
    }

    static {
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAY=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRguIGwJGiBmHl0oOhciKmozOC9oJzg/IxgAKk4jBitqHlEbOgcML2VSHjNvDjw7JQcuKGoaBgFvVigvIwgDIW9THQJOMxkoKQdXOWkFBSVvJygpKQQEI2AKPCJuClkqLD4qIXVSTAN1IF0eMgQhJXsVSFo=")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Jgg2OWgaIAZODDA2Ly1fPmwjMC0=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS1XCW8JGShiHjA+KhciLmkjSFo=")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc6PW8jJCxiCl0JKj4qPW4KGgRrDQ4fLhc+CWIFND9lJ1RF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OghSVg==")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgg2OWgaIAZODlE7Kj06LW4jEis=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KD5eDWMxASRnNB0hIwRWKn9TLyRuN1geIgUlPWEJQDN8MxkbLy4pL2cILy1vVwEdCDo9O24KDStqICMdMzkhDmUjJzFhDVwsOjoMVg==")));
        builder.add(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQc2M28nEhFiJDA2LBhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAImUVHiR9ChEvOjolOnwxPDdoJx4bKggAD2NSHSNnDh49Ly4hJH0FJDV7DwZBDRYbL3UJPBF6IAYAOSoXOGMaIAJgHjBIKAcuXGwgASV/CjM+PCk1MktTBghnHzBBITohJGUVAj1oVjwIJAcuImwkATZmAQobIy4IM3o0DTBOMyc2PF85LXg3MwF8Vw0rIT4+In0FMCx8Iw08MwQpD38zSFo=")));
    }

    public static class Lanzou {
        String name;
        String size;
        String dlLink;

        public Lanzou(String name, String size, String dlLink) {
            this.name = name;
            this.size = size;
            this.dlLink = dlLink;
        }

        public String toString() {
            return com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iz4+DWhTHS0=")) + this.name + '\'' + '\n' + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186KWUaTSt0CjhF")) + this.size + '\'' + '\n' + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186PGoLHi9gNAEdOC5SVg==")) + this.dlLink + '\'' + '\n';
        }

        public String getName() {
            return this.name;
        }

        public String getSize() {
            return this.size;
        }

        public String getDlLink() {
            return this.dlLink;
        }
    }
}

