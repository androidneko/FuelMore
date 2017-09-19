package com.androidcat.acnet.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:12:29
 * add function description here...
 */
public class OrderInfo implements Parcelable{

    public String id;
    public String orderNo;
    public String pointId;
    public String pointName;
    public String pointType;
    public String pointUnit;
    public String pointPrice;
    public String status;
    public String amountMoney;
    public String createTime;
    public String paid;
    public String consumerId;
    public String consumerName;
    public String consumerUnit;
    public String companyName;
    public String userId;
    public String userName;
    public String companyId;

    public OrderInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.orderNo);
        dest.writeString(this.pointId);
        dest.writeString(this.pointName);
        dest.writeString(this.pointType);
        dest.writeString(this.pointUnit);
        dest.writeString(this.pointPrice);
        dest.writeString(this.status);
        dest.writeString(this.amountMoney);
        dest.writeString(this.createTime);
        dest.writeString(this.paid);
        dest.writeString(this.consumerId);
        dest.writeString(this.consumerName);
        dest.writeString(this.consumerUnit);
        dest.writeString(this.companyName);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.companyId);
    }

    protected OrderInfo(Parcel in) {
        this.id = in.readString();
        this.orderNo = in.readString();
        this.pointId = in.readString();
        this.pointName = in.readString();
        this.pointType = in.readString();
        this.pointUnit = in.readString();
        this.pointPrice = in.readString();
        this.status = in.readString();
        this.amountMoney = in.readString();
        this.createTime = in.readString();
        this.paid = in.readString();
        this.consumerId = in.readString();
        this.consumerName = in.readString();
        this.consumerUnit = in.readString();
        this.companyName = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.companyId = in.readString();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
