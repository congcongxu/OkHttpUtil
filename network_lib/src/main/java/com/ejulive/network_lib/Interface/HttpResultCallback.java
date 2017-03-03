package com.ejulive.network_lib.Interface;

/**
 * Created by XCC on 2017/2/17.
 */

public interface HttpResultCallback<T> {
    void onSuccess(T result);

    void onFailure(String code, String msg);

}
