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
public class UserInfoContent implements Parcelable{
    public String userId;
    public String token;
    public String userName;
    public String createTime;
    public String authority;
    public String companyId;
    public String companyName;
    public String userRealName;
    public String integral;
    public String balance;
    public String consumptionAmount;
    public String consumptionCount;

    public UserInfoContent() {
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
        dest.writeString(this.userRealName);
        dest.writeString(this.integral);
        dest.writeString(this.balance);
        dest.writeString(this.consumptionAmount);
        dest.writeString(this.consumptionCount);
    }

    protected UserInfoContent(Parcel in) {
        this.userId = in.readString();
        this.token = in.readString();
        this.userName = in.readString();
        this.createTime = in.readString();
        this.authority = in.readString();
        this.companyId = in.readString();
        this.companyName = in.readString();
        this.userRealName = in.readString();
        this.integral = in.readString();
        this.balance = in.readString();
        this.consumptionAmount = in.readString();
        this.consumptionCount = in.readString();
    }

    public static final Creator<UserInfoContent> CREATOR = new Creator<UserInfoContent>() {
        @Override
        public UserInfoContent createFromParcel(Parcel source) {
            return new UserInfoContent(source);
        }

        @Override
        public UserInfoContent[] newArray(int size) {
            return new UserInfoContent[size];
        }
    };
}
