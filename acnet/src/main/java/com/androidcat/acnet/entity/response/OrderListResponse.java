package com.androidcat.acnet.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidcat.acnet.entity.OrderInfo;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:17:15
 * add function description here...
 */
public class OrderListResponse extends BaseResponse implements Parcelable{

    private List<OrderInfo> content;

    public List<OrderInfo> getContent() {
        return content;
    }

    public void setContent(List<OrderInfo> content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(content);
    }

    public OrderListResponse() {
    }

    protected OrderListResponse(Parcel in) {
        this.content = in.createTypedArrayList(OrderInfo.CREATOR);
    }

    public static final Creator<OrderListResponse> CREATOR = new Creator<OrderListResponse>() {
        @Override
        public OrderListResponse createFromParcel(Parcel source) {
            return new OrderListResponse(source);
        }

        @Override
        public OrderListResponse[] newArray(int size) {
            return new OrderListResponse[size];
        }
    };
}
