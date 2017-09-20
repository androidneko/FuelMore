package com.androidcat.acnet.entity.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ValidateCodeRequest extends BaseRequest implements Parcelable{
    public String mobile = null;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
    }

    public ValidateCodeRequest() {
    }

    protected ValidateCodeRequest(Parcel in) {
        this.mobile = in.readString();
    }

    public static final Creator<ValidateCodeRequest> CREATOR = new Creator<ValidateCodeRequest>() {
        @Override
        public ValidateCodeRequest createFromParcel(Parcel source) {
            return new ValidateCodeRequest(source);
        }

        @Override
        public ValidateCodeRequest[] newArray(int size) {
            return new ValidateCodeRequest[size];
        }
    };
}