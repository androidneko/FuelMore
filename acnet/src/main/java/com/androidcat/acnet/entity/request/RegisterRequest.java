package com.androidcat.acnet.entity.request;

import android.os.Parcel;
import android.os.Parcelable;

public class RegisterRequest extends BaseRequest implements Parcelable{
    public String userName;
    public String password;
    public String vcode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.vcode);
    }

    public RegisterRequest() {
    }

    protected RegisterRequest(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.vcode = in.readString();
    }

    public static final Creator<RegisterRequest> CREATOR = new Creator<RegisterRequest>() {
        @Override
        public RegisterRequest createFromParcel(Parcel source) {
            return new RegisterRequest(source);
        }

        @Override
        public RegisterRequest[] newArray(int size) {
            return new RegisterRequest[size];
        }
    };
}