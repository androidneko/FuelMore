package com.androidcat.acnet.entity.request;


public class FastLoginRequest extends BaseRequest {
    public String userName = null;
    public String vcode = null;

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}