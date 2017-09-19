package com.androidcat.fuelmore.entity;

/**
 * Created by Administrator on 2017/8/5.
 */

public class FuelMoreEvent {
    public static final int CODE_FINISH_LOGIN = 300;

    public int code;
    public String action;

    public FuelMoreEvent(int code){
        this.code = code;
        this.action = String.valueOf(code);
    }
}
