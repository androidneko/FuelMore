package com.androidcat.acnet.entity.response;

import com.androidcat.acnet.entity.LoginContent;

/**
 * Created by Administrator on 2016/11/18.
 */
public class LoginResponse extends BaseResponse{

    public LoginContent content;

    public LoginContent getContent() {
        return content;
    }

    public void setContent(LoginContent content) {
        this.content = content;
    }
}