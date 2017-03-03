package com.ejulive.network_lib;

import android.text.TextUtils;
import android.util.Log;

import com.ejulive.network_lib.Interface.HttpResultCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by XCC on 2017/2/17.
 */

public class RequestContent {

    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TYPE_STREAM = MediaType.parse("application/octet-stream");
    private static final int TIME_OUT = 60 * 1000;
    private static OkHttpClient mOkHttpClient = null;
    private static Gson g = null;
    private static String token;
    private static String channel;
    private static String netWorkName;
    private static String clientId;
    private static String clientVersion;
    private static Headers headers = null;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);  //设置连接超时时间
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);    //设置读取超时时间
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);  //设置写入超时时间
        mOkHttpClient = okHttpClientBuilder.build();

        g = new Gson();
    }

    /**
     * get不带参数
     *
     * @param url
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void get(String url, HttpResultCallback<T> i, Class<T> mClass) {
        logRequestInfo(url, "");
        Request request = createGetRequest(url, "");
        getResponse(request, i, mClass);
    }

    /**
     * get带参数 String
     *
     * @param url
     * @param params
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void get(String url, String params, HttpResultCallback<T> i, Class<T> mClass) {
        logRequestInfo(url, params);
        Request getRequest = createGetRequest(url, params);
        getResponse(getRequest, i, mClass);
    }

    /**
     * get带参数 Map
     *
     * @param url
     * @param map
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void get(String url, Map<String, Object> map, HttpResultCallback<T> i, Class<T> mClass) {
        if (null == map || map.isEmpty()) throw new NullPointerException("map == null");
        logRequestInfo(url, map.toString());
        Request getRequest = createGetRequest(url, map);
        getResponse(getRequest, i, mClass);
    }

    /**
     * post 参数Map封装
     *
     * @param url
     * @param map
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void post(String url, Map<String, Object> map, HttpResultCallback<T> i, Class<T> mClass) {
        if (null == map || map.isEmpty()) throw new NullPointerException("map == null");
        logRequestInfo(url, map.toString());
        Request getRequest = createPostRequest(url, map);
        getResponse(getRequest, i, mClass);
    }

    /**
     * post 参数json类型
     *
     * @param url
     * @param jsonStr
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void post(String url, JSONObject jsonStr, HttpResultCallback<T> i, Class<T> mClass) {
        if (null == jsonStr) throw new NullPointerException("jsonStr == null");
        logRequestInfo(url, jsonStr.toString());
        Request getRequest = createPostJsonRequest(url, jsonStr);
        getResponse(getRequest, i, mClass);
    }

    /**
     * post 参数是String类型
     *
     * @param url
     * @param params
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void post(String url, String params, HttpResultCallback<T> i, Class<T> mClass) {
        logRequestInfo(url, params);
        Request postStringRequest = createPostStringRequest(url, params);
        getResponse(postStringRequest, i, mClass);
    }


    /**
     * post 参数是文件
     *
     * @param url
     * @param file
     * @param i
     * @param mClass
     * @param <T>
     */
    public static <T> void post(String url, File file, HttpResultCallback<T> i, Class<T> mClass) {
        if (null == file) throw new NullPointerException("file == null");
        logRequestInfo(url, file.getName());
        Request getRequest = createPostFileRequest(url, file);
        getResponse(getRequest, i, mClass);
    }

    /**
     * post 上传文件
     *
     * @param url
     * @param file
     * @return
     */
    public static Request createPostFileRequest(String url, File file) {
        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(TYPE_STREAM, file);
        Request request = requestBuilder.url(url).post(requestBody).build();
        return request;
    }


    /**
     * 创建 Post json 请求，
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static Request createPostJsonRequest(String url, JSONObject jsonStr) {
        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(TYPE_JSON, jsonStr.toString());
        Request request = requestBuilder.url(url).post(requestBody).build();
        return request;
    }

    /**
     * post 请求
     *
     * @param url
     * @param params String
     * @return
     */
    public static Request createPostStringRequest(String url, String params) {
        return new Request.Builder()
                .headers(headers)
                .post(postBody(params))
                .url(url)
                .build();
    }

    /**
     * post 请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static Request createPostRequest(String url, Map<String, Object> paramMap) {
        return new Request.Builder()
                .headers(headers)
                .post(postBody(paramMap))
                .url(url)
                .build();
    }

    /**
     * get请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static Request createGetRequest(String url, Map<String, Object> paramMap) {
        String realUrl = url;
        Request.Builder requestBuilder = new Request.Builder();
        StringBuffer sb = new StringBuffer(url);
        if (paramMap != null && !paramMap.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            realUrl = sb.substring(0, sb.length() - 1);
        }
        Request request = requestBuilder.url(realUrl).get().headers(headers).build();
        return request;

    }

    /**
     * get请求数据是 String类型
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, String params) {
        String realUrl = url;
        Request.Builder requestBuilder = new Request.Builder();
        if (!TextUtils.isEmpty(params)) {
            StringBuffer sb = new StringBuffer(url);
            sb.append("?").append(params);
            realUrl = sb.toString();
        }
        return requestBuilder.url(realUrl).get().headers(headers).build();
    }

    private static void logRequestInfo(String url, String params) {
        Log.d("RequestContent", "url-->" + url);
        Log.d("RequestContent", "params-->" + params);
    }

    private static <T> void getResponse(Request request, final HttpResultCallback<T> i, final Class<T> mClass) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                i.onFailure("10010", e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    i.onFailure("10020",response.toString());
                }
                if (response.body() == null)
                    i.onFailure("10030","response.body() == null");
                String str = null;
                try {
                    str = response.body().string();
                    Log.i("RequestContent", "response.nody()-->" + str);
                    if (TextUtils.isEmpty(str)) {
                        i.onFailure("10040", "返回值为空！");
                    } else {
                        JSONObject json = new JSONObject(str);
                        int apistatus = json.optInt("apistatus");
                        if (apistatus == 1) {
                            JSONObject result = json.getJSONObject("result");
                            T t = g.fromJson(result.toString(), mClass);
                            i.onSuccess(t);
                        } else {
                            String errorMsg = json.optString("errorMsg");
                            String errorCode = json.optString("errorCode");
                            i.onFailure(errorCode, errorMsg);
                        }
                    }
                } catch (Exception e) {
                    i.onFailure("10050",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 请求参数
     *
     * @param map
     * @return
     */
    private static RequestBody postBody(Map<String, Object> map) {
        if (null == null || map.isEmpty()) throw new NullPointerException("map == null");
        FormBody.Builder b = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            b.add(entry.getKey(), (String) entry.getValue());
        }

        return b.build();
    }

    /**
     * 请求参数String
     *
     * @param params
     * @return
     */
    private static RequestBody postBody(String params) {
        FormBody.Builder b = new FormBody.Builder();
        if (!TextUtils.isEmpty(params)) {
            String[] split = params.split("&");
            for (String str : split) {
                String[] s = str.split("=");
                b.add(s[0], s[1]);
            }
        }
        return b.build();
    }

    /**
     * 初始化Okhttp
     *
     * @param init 初始化所需参数
     */
    @Deprecated
    public static void initOkHttp(OkHttpInit init) {
        channel = init.getChannel();
        clientId = init.getClientId();
        netWorkName = init.getnetWorkName();
        token = init.getToken();
        clientVersion = init.getClientVersion();
    }

    /**
     * 新方法头部谁都可以去拼装
     *
     * @param str
     */
    public static void initOkHttpHearder(String... str) {
        if (str.length % 2 != 0) {
            throw new IllegalArgumentException("Strring... str不合法！");
        }
        headers = Headers.of(str);
    }


    /**
     * 初始化头信息
     *
     * @return Headers 头信息
     */
    @Deprecated
    private static Headers initHeaders() {
        return Headers.of("Authorization", token, "X-WVersion", "4.0.9-" + clientVersion + "-" + UDID() + "-" + deviceType() + "-" + channel, "X-Client-ID", clientId, "rechaStatus", netWorkName);
    }

    private static String UDID() {
        SecureRandom random = new SecureRandom();
        return (new BigInteger(64, random)).toString(16);
    }

    private static String deviceType() {
        return android.os.Build.MODEL.replace("-", " ");
    }


}
