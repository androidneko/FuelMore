package com.androidcat.acnet.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-21 18:10:05
 * add function description here...
 */
public class LoginContent implements Parcelable{
    public String userId;
    public String token;
    public String userName;
    public String createTime;
    public String authority;
    public String companyId;
    public String companyName;
    public String cipherqrcode;
    public String ciphertext;
    public String pointId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCipherqrcode() {
        return cipherqrcode;
    }

    public void setCipherqrcode(String cipherqrcode) {
        this.cipherqrcode = cipherqrcode;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public LoginContent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.token);
        dest.writeString(this.userName);
        dest.writeString(this.createTime);
        dest.writeString(this.authority);
        dest.writeString(this.companyId);
        dest.writeString(this.companyName);
        dest.writeString(this.cipherqrcode);
        dest.writeString(this.ciphertext);
        dest.writeString(this.pointId);
    }

    protected LoginContent(Parcel in) {
        this.userId = in.readString();
        this.token = in.readString();
        this.userName = in.readString();
        this.createTime = in.readString();
        this.authority = in.readString();
        this.companyId = in.readString();
        this.companyName = in.readString();
        this.cipherqrcode = in.readString();
        this.ciphertext = in.readString();
        this.pointId = in.readString();
    }

    public static final Creator<LoginContent> CREATOR = new Creator<LoginContent>() {
        @Override
        public LoginContent createFromParcel(Parcel source) {
            return new LoginContent(source);
        }

        @Override
        public LoginContent[] newArray(int size) {
            return new LoginContent[size];
        }
    };
}
