package com.example.windows8.newef.bean;

/**
 * Created by wangsheng
 * on 2017/9/29.
 */

public class ResponseInfo<T> {
    T data;
    String resultCode;

    @Override
    public String toString() {
        return "ResponseInfo{" +
                ", data=" + data +
                ", code=" + resultCode +
                '}';
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}