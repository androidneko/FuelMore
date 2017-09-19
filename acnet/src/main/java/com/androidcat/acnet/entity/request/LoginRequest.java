package com.androidcat.acnet.entity.request;


public class LoginRequest extends BaseRequest {
    public String userName = null;
    public String passwd = null;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}