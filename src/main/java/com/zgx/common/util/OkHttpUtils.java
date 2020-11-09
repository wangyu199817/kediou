package com.zgx.common.util;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 工具类补充 - OkHttpUtils
 *
 * @author zhangyang
 * @version 0.1
 * create 2019/3/7
 */
public class OkHttpUtils {

    private final static String CHARSET = "UTF-8";
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final ConnectionPool POOL = new ConnectionPool(5, 20, TimeUnit.MINUTES);
    //cookie存储，记录cookie
    private static ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    private static final OkHttpClient okHttpClient = getClient();

    private static ResponseBody doHttp(Request.Builder builder, Map<String, String> headers) {
        if (null != headers) headers.forEach(builder::addHeader);
        try {
            return getClient()
                    .newCall(builder.build())
                    .execute()
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static OkHttpClient getClient() {
        if (null != okHttpClient) return okHttpClient;
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .followRedirects(false)
                .connectionPool(POOL)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
                        if (!cookieStore.containsKey(httpUrl.host())) cookieStore.put(httpUrl.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return null == cookies ? new ArrayList<>() : cookies;
                    }
                }).build();
    }

    public static ResponseBody doGet(String url, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder().url(url).get();
        return doHttp(builder, headers);
    }

    public static ResponseBody doPost(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder(Charset.forName(CHARSET));
        if (null != params) params.forEach(formBodyBuilder::add);
        Request.Builder builder = new Request.Builder().url(url).post(formBodyBuilder.build());
        return doHttp(builder, headers);
    }

    public static ResponseBody doPostFiles(String url, Map<String, String> headers, Map<String, String> params, String filePartName, List<MultipartFile> multipartFiles) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartFiles.forEach(multipartFile -> {
            String fileName = multipartFile.getOriginalFilename();
            try {
                multipartBodyBuilder.addFormDataPart(filePartName, getURLEncode(fileName), RequestBody.create(MultipartBody.FORM, multipartFile.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (null != params) params.forEach(multipartBodyBuilder::addFormDataPart);
        Request.Builder builder = new Request.Builder().url(url).post(multipartBodyBuilder.build());
        return doHttp(builder, headers);
    }

    public static ResponseBody doPostJson(String url, Object subject, Map<String, String> headers) {
        String json = new Gson().toJson(subject);
        Request.Builder builder = new Request.Builder().url(url).post(FormBody.create(JSON_TYPE, json));
        return doHttp(builder, headers);
    }

    private static String getURLEncode(String str) {
        if (null != str) {
            try {
                return URLEncoder.encode(str, CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String resonse2String(ResponseBody response) {
        String str = null;
        if (null != response) {
            try {
                str = response.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static ResponseBody doPostFile(String url, Map<String, String> headers, Map<String, String> params, String filePartName, MultipartFile multipartFile) {
        if (null != multipartFile) {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);
            String fileName = multipartFile.getOriginalFilename();
            try {
                multipartBodyBuilder.addFormDataPart(filePartName, getURLEncode(fileName), RequestBody.create(MultipartBody.FORM, multipartFile.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
            if (null != params) params.forEach(multipartBodyBuilder::addFormDataPart);
            Request.Builder builder = new Request.Builder().url(url).post(multipartBodyBuilder.build());
            return doHttp(builder, headers);
        } else {
            return OkHttpUtils.doPost(url, headers, params);
        }
    }

}
