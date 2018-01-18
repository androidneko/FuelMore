package com.androidcat.acnet.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-21 17:43:37
 * add function description here...
 */
public class User implements Parcelable{

    public String id;
    public String userName;
    public String mobile;
    public String token;
    public String name;
    public String authority;
    public String company = "未知";
    public String companyId;
    public String ciphertext;
    public String cipherqrcode;
    public String pointId;

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.mobile);
        dest.writeString(this.token);
        dest.writeString(this.name);
        dest.writeString(this.authority);
        dest.writeString(this.company);
        dest.writeString(this.companyId);
        dest.writeString(this.ciphertext);
        dest.writeString(this.cipherqrcode);
        dest.writeString(this.pointId);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.userName = in.readString();
        this.mobile = in.readString();
        this.token = in.readString();
        this.name = in.readString();
        this.authority = in.readString();
        this.company = in.readString();
        this.companyId = in.readString();
        this.ciphertext = in.readString();
        this.cipherqrcode = in.readString();
        this.pointId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
