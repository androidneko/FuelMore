package com.androidcat.acnet.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidcat.acnet.entity.GasStation;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:17:15
 * add function description here...
 */
public class StationListResponse extends BaseResponse implements Parcelable{

    private List<GasStation> content;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(content);
    }

    public StationListResponse() {
    }

    protected StationListResponse(Parcel in) {
        this.content = in.createTypedArrayList(GasStation.CREATOR);
    }

    public static final Creator<StationListResponse> CREATOR = new Creator<StationListResponse>() {
        @Override
        public StationListResponse createFromParcel(Parcel source) {
            return new StationListResponse(source);
        }

        @Override
        public StationListResponse[] newArray(int size) {
            return new StationListResponse[size];
        }
    };

    public List<GasStation> getContent() {
        return content;
    }

    public void setContent(List<GasStation> content) {
        this.content = content;
    }
}
