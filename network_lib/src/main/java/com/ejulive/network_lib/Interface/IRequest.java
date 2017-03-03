package com.ejulive.network_lib.Interface;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;


/**
 * 该接口规定所有请求所需的常量和方法
 */
public interface IRequest {


    /**
     * 发送get请求
     * @param url URL
     * @param i responseInterface
     */
    <T>void GET(String url, HttpResultCallback<T> i, Class<T> mClass);

    /**
     * 发送get请求
     * @param url URL
     * @param map map
     * @param i responseInterface
     */
    <T>void GET(String url, Map<String,Object> map, HttpResultCallback<T> i, Class<T> mClass);

    /**
     * 发送get请求
     * @param url
     * @param params String
     * @param i
     * @param mClass
     * @param <T>
     */
    <T>void GET(String url,String params,HttpResultCallback<T> i,Class<T> mClass);

    /**
     * 发送get请求
     * @param url URL
     * @param map map
     * @param time time_out
     * @param i responseInterface
     */
    <T>void GET(String url, Map<String,Object> map, String time, HttpResultCallback<T> i, Class<T> mClass);

    /**
     * 发送post请求
     * @param url
     * @param json
     * @param i
     */
    <T>void POST(String url, JSONObject json, HttpResultCallback<T> i, Class<T> mClass);

    <T>void POST(String url,String params,HttpResultCallback<T> i,Class<T> mClass);

    /**
     * 发送post请求
     * @param url URL
     * @param map map
     * @param i responseInterface
     */
    <T>void POST(String url, Map<String,Object> map, HttpResultCallback<T> i, Class<T> mClass);


    /**
     * 发送post请求
     * @param url URL
     * @param map map
     * @param time time_out
     * @param i responseInterface
     */
    <T>void POST(String url, Map<String,Object> map, String time, HttpResultCallback<T> i, Class<T> mClass);

    /**
     * 下载文件
     */
    void downloadFile(String url);

    /**
     * 上传文件，图片
     */
    void upLoadFile(String url, File file,HttpResultCallback<String> i);
}