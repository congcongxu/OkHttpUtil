package com.ejulive.network_lib;

import com.ejulive.network_lib.Interface.IRequest;
import com.ejulive.network_lib.Interface.HttpResultCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;


/**
 * Created by XCC on 2017/2/22.
 */

public class HttpUtil implements IRequest {

    public HttpUtil() {
    }

    private static class HttpUtilHolder {
        static final HttpUtil h = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return HttpUtilHolder.h;
    }

    @Override
    public <T> void GET(String url, final HttpResultCallback<T> i, final Class<T> mClass) {
        RequestContent.get(url, i, mClass);
    }

    @Override
    public <T> void GET(String url, Map<String, Object> map, HttpResultCallback<T> i, Class<T> mClass) {
        RequestContent.get(url, map, i, mClass);
    }

    @Override
    public <T> void GET(String url, String params, HttpResultCallback<T> i, Class<T> mClass) {
        RequestContent.get(url,params,i,mClass);
    }

    @Override
    public <T> void GET(String url, Map<String, Object> map, String time, HttpResultCallback<T> i, Class<T> mClass) {

    }

    @Override
    public <T> void POST(String url, JSONObject json, HttpResultCallback<T> i, Class<T> mClass) {
        RequestContent.post(url,json,i,mClass);
    }

    @Override
    public <T> void POST(String url, String params, HttpResultCallback<T> i, Class<T> mClass) {
        RequestContent.post(url,params,i,mClass);
    }

    @Override
    public <T> void POST(String url, Map<String, Object> map, HttpResultCallback<T> i, Class<T> mClass) {
        RequestContent.post(url, map, i, mClass);
    }


    @Override
    public <T> void POST(String url, Map<String, Object> map, String time, HttpResultCallback<T> i, Class<T> mClass) {

    }



    @Override
    public void downloadFile(String url) {

    }

    @Override
    public void upLoadFile(String url, File file, HttpResultCallback<String> i) {
        RequestContent.post(url,file,i,String.class);
    }
}
