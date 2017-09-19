package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.RegisterRequest;
import com.androidcat.acnet.entity.response.RegistResponse;
import com.androidcat.acnet.entity.response.StationListResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-1 18:50:44
 * add function description here...
 */
public class GasStationManager extends BaseManager{
    public GasStationManager(Context context,Handler handler){
        super(context,handler);
    }

    public void queryGasStations(final boolean isPullRefreshing){
        post(InterfaceCodeConst.TYPE_GET_STATION_LIST, "", new EntityResponseHandler<StationListResponse>() {
            @Override
            public void onStart(int code) {
                Message msg = new Message();
                msg.obj = isPullRefreshing;
                msg.what = OptMsgConst.MSG_GET_STATION_LIST_START;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_STATION_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StationListResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_STATION_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }
}
