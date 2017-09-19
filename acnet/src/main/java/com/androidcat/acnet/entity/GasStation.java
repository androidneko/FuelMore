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
public class GasStation implements Parcelable{

    private String id;
    private String longitude;
    private String latitude;
    private String point_name;
    private String point_adress;
    private String business_time;
    private String tel;
    private String enabled;
    private String imp_path;
    private String create_time;
    private String type1;
    private String unit1;
    private String price1;
    private String type2;
    private String unit2;
    private String price2;
    private String type3;
    private String unit3;
    private String price3;
    private String type4;
    private String unit4;
    private String price4;
    private String type5;
    private String unit5;
    private String price5;
    private String type6;
    private String unit6;
    private String price6;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.point_name);
        dest.writeString(this.point_adress);
        dest.writeString(this.business_time);
        dest.writeString(this.tel);
        dest.writeString(this.enabled);
        dest.writeString(this.imp_path);
        dest.writeString(this.create_time);
        dest.writeString(this.type1);
        dest.writeString(this.unit1);
        dest.writeString(this.price1);
        dest.writeString(this.type2);
        dest.writeString(this.unit2);
        dest.writeString(this.price2);
        dest.writeString(this.type3);
        dest.writeString(this.unit3);
        dest.writeString(this.price3);
        dest.writeString(this.type4);
        dest.writeString(this.unit4);
        dest.writeString(this.price4);
        dest.writeString(this.type5);
        dest.writeString(this.unit5);
        dest.writeString(this.price5);
        dest.writeString(this.type6);
        dest.writeString(this.unit6);
        dest.writeString(this.price6);
    }

    public GasStation() {
    }

    protected GasStation(Parcel in) {
        this.id = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.point_name = in.readString();
        this.point_adress = in.readString();
        this.business_time = in.readString();
        this.tel = in.readString();
        this.enabled = in.readString();
        this.imp_path = in.readString();
        this.create_time = in.readString();
        this.type1 = in.readString();
        this.unit1 = in.readString();
        this.price1 = in.readString();
        this.type2 = in.readString();
        this.unit2 = in.readString();
        this.price2 = in.readString();
        this.type3 = in.readString();
        this.unit3 = in.readString();
        this.price3 = in.readString();
        this.type4 = in.readString();
        this.unit4 = in.readString();
        this.price4 = in.readString();
        this.type5 = in.readString();
        this.unit5 = in.readString();
        this.price5 = in.readString();
        this.type6 = in.readString();
        this.unit6 = in.readString();
        this.price6 = in.readString();
    }

    public static final Creator<GasStation> CREATOR = new Creator<GasStation>() {
        @Override
        public GasStation createFromParcel(Parcel source) {
            return new GasStation(source);
        }

        @Override
        public GasStation[] newArray(int size) {
            return new GasStation[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getPoint_adress() {
        return point_adress;
    }

    public void setPoint_adress(String point_adress) {
        this.point_adress = point_adress;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getImp_path() {
        return imp_path;
    }

    public void setImp_path(String imp_path) {
        this.imp_path = imp_path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

    public String getUnit3() {
        return unit3;
    }

    public void setUnit3(String unit3) {
        this.unit3 = unit3;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getType4() {
        return type4;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }

    public String getUnit4() {
        return unit4;
    }

    public void setUnit4(String unit4) {
        this.unit4 = unit4;
    }

    public String getPrice4() {
        return price4;
    }

    public void setPrice4(String price4) {
        this.price4 = price4;
    }

    public String getType5() {
        return type5;
    }

    public void setType5(String type5) {
        this.type5 = type5;
    }

    public String getUnit5() {
        return unit5;
    }

    public void setUnit5(String unit5) {
        this.unit5 = unit5;
    }

    public String getPrice5() {
        return price5;
    }

    public void setPrice5(String price5) {
        this.price5 = price5;
    }

    public String getType6() {
        return type6;
    }

    public void setType6(String type6) {
        this.type6 = type6;
    }

    public String getUnit6() {
        return unit6;
    }

    public void setUnit6(String unit6) {
        this.unit6 = unit6;
    }

    public String getPrice6() {
        return price6;
    }

    public void setPrice6(String price6) {
        this.price6 = price6;
    }
}
