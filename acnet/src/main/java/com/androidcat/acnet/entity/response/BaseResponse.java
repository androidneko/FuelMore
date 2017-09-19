package com.androidcat.acnet.entity.response;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-18 16:58:47
 * add function description here...
 */
public class BaseResponse {

    public static final String LOGIN_ERR = "LOGIN_ERR";
    public static final int SUCCESS = 1;

    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
