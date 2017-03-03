package com.ejulive.network_lib;

/**
 * 初始化请求头信息
 * Created by XCC on 2017/2/27.
 */

public class OkHttpHeadersInit {
    /**
     * 初始化请求头信息
     * @param str
     */
    public static void initOkHttpHeaders(String... str){
        RequestContent.initOkHttpHearder(str);
    }
}
