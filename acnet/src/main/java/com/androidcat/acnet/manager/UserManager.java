package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.ChangePswRequest;
import com.androidcat.acnet.entity.request.FastLoginRequest;
import com.androidcat.acnet.entity.request.LoginRequest;
import com.androidcat.acnet.entity.request.NewsListRequest;
import com.androidcat.acnet.entity.request.RegisterRequest;
import com.androidcat.acnet.entity.request.ResetPasswordRequest;
import com.androidcat.acnet.entity.request.ValidateCodeRequest;
import com.androidcat.acnet.entity.response.BaseResponse;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.entity.response.NewsResponse;
import com.androidcat.acnet.entity.response.RegistResponse;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-21 17:08:36
 * add function description here...
 */
public class UserManager extends BaseManager {

    public UserManager(Context context, Handler handler){
        super(context,handler);
    }

    public void login(String userName,String pwd,RawResponseHandler rawResponseHandler){
        LoginRequest request = new LoginRequest();
        request.userName = (userName);
        request.setPasswd(pwd);
        post(InterfaceCodeConst.TYPE_LOGIN, getPostJson(request), rawResponseHandler);
    }

    public void login(String userName,String pwd){
        LoginRequest request = new LoginRequest();
        request.userName = (userName);
        request.setPasswd(pwd);
        post(InterfaceCodeConst.TYPE_LOGIN, getPostJson(request), new EntityResponseHandler<LoginResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_LOGIN_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_LOGIN_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, LoginResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_LOGIN_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void fastLogin(String userName,String vcode){
        FastLoginRequest request = new FastLoginRequest();
        request.userName = (userName);
        request.setVcode(vcode);
        post(InterfaceCodeConst.TYPE_FAST_LOGIN, getPostJson(request), new EntityResponseHandler<LoginResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.FAST_LOGIN_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.FAST_LOGIN_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, LoginResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.FAST_LOGIN_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void register(String userName,String pwd,String verifyCode){
        RegisterRequest request = new RegisterRequest();
        request.userName = (userName);
        request.password = (pwd);
        request.vcode = (verifyCode);
        post(InterfaceCodeConst.TYPE_REGISTER, getPostJson(request), new EntityResponseHandler<RegistResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_REIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_REIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, RegistResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_REIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void getVerifyCode(String phoneNum){
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setMobile(phoneNum);
        post(InterfaceCodeConst.TYPE_GET_VERIFY_CODE, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_VERIFYCODE_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_VERIFYCODE_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_VERIFYCODE_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void modifyPwd(String userName,String pwd,String newPwd){
        ChangePswRequest request = new ChangePswRequest();
        request.setUserName(userName);
        request.setPassword(pwd);
        request.setNePassword(newPwd);
        post(InterfaceCodeConst.TYPE_MODIFY_PWD, getPostJson(request), new EntityResponseHandler<BaseResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_CHANGEPSW_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_CHANGEPSW_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BaseResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_CHANGEPSW_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void resetPwd(String phoneNum,String newPwd,String verifyCode){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setMobile(phoneNum);
        request.setPassword(newPwd);
        request.setVcode(verifyCode);
        post(InterfaceCodeConst.TYPE_RESET_PWD, getPostJson(request), new EntityResponseHandler<BaseResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_RESETPASSWORD_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_RESETPASSWORD_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BaseResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_RESETPASSWORD_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void getNewsList(){
        NewsListRequest request = new NewsListRequest();
        request.setStartid("0");
        post(InterfaceCodeConst.TYPE_GET_NEWS_LIST, getPostJson(request), new EntityResponseHandler<NewsResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_NEWS_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_NEWS_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, NewsResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_NEWS_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }
}
