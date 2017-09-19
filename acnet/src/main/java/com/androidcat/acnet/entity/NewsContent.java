package com.androidcat.acnet.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-31 13:26:23
 * add function description here...
 */
public class NewsContent implements Parcelable{
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
    private int numberOfElements;
    private String sort;
    private boolean first;
    private boolean last;

    private List<News> content;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<News> getContent() {
        return content;
    }

    public void setContent(List<News> content) {
        this.content = content;
    }

    public NewsContent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPages);
        dest.writeInt(this.totalElements);
        dest.writeInt(this.size);
        dest.writeInt(this.number);
        dest.writeInt(this.numberOfElements);
        dest.writeString(this.sort);
        dest.writeByte(first ? (byte) 1 : (byte) 0);
        dest.writeByte(last ? (byte) 1 : (byte) 0);
        dest.writeTypedList(content);
    }

    protected NewsContent(Parcel in) {
        this.totalPages = in.readInt();
        this.totalElements = in.readInt();
        this.size = in.readInt();
        this.number = in.readInt();
        this.numberOfElements = in.readInt();
        this.sort = in.readString();
        this.first = in.readByte() != 0;
        this.last = in.readByte() != 0;
        this.content = in.createTypedArrayList(News.CREATOR);
    }

    public static final Creator<NewsContent> CREATOR = new Creator<NewsContent>() {
        @Override
        public NewsContent createFromParcel(Parcel source) {
            return new NewsContent(source);
        }

        @Override
        public NewsContent[] newArray(int size) {
            return new NewsContent[size];
        }
    };
}
