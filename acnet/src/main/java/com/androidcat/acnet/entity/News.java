package com.androidcat.acnet.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-31 13:30:25
 * add function description here...
 */
public class News implements Parcelable{
    private String id;
    private String title;
    private String img;
    private String content;
    private String datetime;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeString(this.content);
        dest.writeString(this.datetime);
        dest.writeParcelable(this.user, flags);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.img = in.readString();
        this.content = in.readString();
        this.datetime = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
