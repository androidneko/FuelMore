package com.androidcat.acnet.consts;

/**
 * Created by Administrator on 2017/8/4.
 */

public class InterfaceUrl {
    public static final String BASE_URL = "http://120.25.249.105:8081";

    public static final String REGISTER_URL = BASE_URL + "/api/v1/create";
    public static final String LOGIN_URL = BASE_URL + "/api/v1/userLogin";
    public static final String GET_VCODE_URL = BASE_URL + "/api/v1/requestSMS";
    public static final String FAST_LOGIN_URL = BASE_URL + "/api/v1/quickLogin";
    public static final String RESET_PWD_URL = BASE_URL + "/api/v1/user/resetPassword";
    public static final String STATION_LIST_URL = BASE_URL + "/api/v1/getRefuelingPointList";
    public static final String QR_CODE_URL = BASE_URL + "/api/v1/getQrcode";
    public static final String ORDER_LIST_URL = BASE_URL + "/api/v1/getOrderInfoList";
    public static final String ADD_RECHAREGE_URL = BASE_URL + "/api/v1/addRecharge";
    public static final String NEWS_LIST_URL = BASE_URL + "/api/v1/news/getNewsList";

    public static final String USERINFO_URL = BASE_URL + "/api/v1/user/findByUserId";
    public static final String GATHER_URL = BASE_URL + "/api/v1/saveOrderInfo";
    public static final String TRUCK_LICENSE_URL = BASE_URL + "/api/v1/getCardQrcode";


    public static String getUrl(int code){
        switch (code){
            case InterfaceCodeConst.TYPE_REGISTER:
                return REGISTER_URL;
            case InterfaceCodeConst.TYPE_LOGIN:
                return LOGIN_URL;
            case InterfaceCodeConst.TYPE_GET_VERIFY_CODE:
                return GET_VCODE_URL;
            case InterfaceCodeConst.TYPE_FAST_LOGIN:
                return FAST_LOGIN_URL;
            case InterfaceCodeConst.TYPE_GET_STATION_LIST:
                return STATION_LIST_URL;
            case InterfaceCodeConst.TYPE_RESET_PWD:
                return RESET_PWD_URL;
            case InterfaceCodeConst.TYPE_GET_QR_CODE:
                return QR_CODE_URL;
            case InterfaceCodeConst.TYPE_GET_ORDER_LIST:
                return ORDER_LIST_URL;
            case InterfaceCodeConst.TYPE_ADD_RECHARGE:
                return ADD_RECHAREGE_URL;
            case InterfaceCodeConst.TYPE_GET_NEWS_LIST:
                return NEWS_LIST_URL;
            case InterfaceCodeConst.TYPE_GATHER:
                return GATHER_URL;
            case InterfaceCodeConst.TYPE_GET_USERINFO:
                return USERINFO_URL;
            case InterfaceCodeConst.TYPE_GET_TRUCK_LICENSE:
                return TRUCK_LICENSE_URL;
        }
        return "";
    }

}
