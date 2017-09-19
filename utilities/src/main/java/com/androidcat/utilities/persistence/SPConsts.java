package com.androidcat.utilities.persistence;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2016-12-12 15:57:34
 * 所有使用sharedPreference保存的变量的key都需要在此声明，严禁使用魔鬼代码保存数据
 */
public class SPConsts {
    /**客户端相关 */
    /////////////////////////// 公共参数 ////////////////////////////////
    public static final String XLISTVIEW_LAST_UPDATE_TIME = "xlistview_last_update_time";

    public static final String VERSION_CODE = "versionCode";
    public static final String LOCAL_URL="local_url";
    public static final String SERVER_URL="server_url";
    public static final String NEED_UPDATE = "isneedupdata";
    public static final String DEVICE_ID = "IMEI";
    public static final String SHOW_GUIDE = "show_guide";
    public static final String WALLET_UPDATE_TIME = "wallet_update_time";

    public static final String KS_CACHE_ORDERS = "ks_cache_orders";

    /**钱包相关 */
    public static final String CITY_CODE = "CITYCODE";
    public static final String CITY_ID = "CityID";
    public static final String CITY_NAME = "CityName";
    public static final String CITY_FLAG = "flag";
    public static final String CARD_ID = "CardID";
    public static final String CARD_DATE = "CardDate";
    public static final String WALLET_BALANCE = "blance";
    public static final String ECASH_ID = "ecashID";
    public static final String ECASH_DATE = "cashDate";
    public static final String RECORD_CODE = "RECORDCODE";
    public static final String COMCODE = "comCode";
    public static final String RECORD_FILE0005 = "Card0005";
    public static final String RECORD_FILE0015 = "Card0015";
    public static final String REVERSE_VALUE1 = "reverse1";
    public static final String REVERSE_VALUE2 = "reverse2";
    public static final String APDU_RESULT = "apduresult";
    public static final String MAPDU_RESULT = "apduresult1";
    public static final String GAC_RETURN = "GAC";
    public static final String AD_CID = "AD_CID";
    public static final String RECHARGE_AMT = "RechargeAmt";
    public static final String TRY_TIME = "tryTime";
    public static final String ORDER_CODE = "orderCode";

    /**接口调用 */
    public static final String ACTIVE_SUCCESS = "ACTIVE_SUCCESS";
    public static final String RECHARGE_SUCCESS = "RECHARGE_SUCCESS";

    /**蓝牙数据读取 */
    public static final String CID = "CID";
    public static final String RESPONSE_DATA = "responseData";
    public static final String FIRMWARE_VER = "FirmwareVer";
    public static final String SE_VERSION = "SeVersion";
    public static final String APP_VERSION = "AppVersion";
    public static final String UPDATE_DATE = "updateDate";
    public static final String CONNECT_TIMES = "connectTimes";
    public static final String SLEEP_START_TIME = "SleepStartTime";
    public static final String SLEEP_END_TIME = "SleepEndTime";
    public static final String APP_LIST = "AppList";
    public static final String BLE_BIND = "BLE_BIND";
    public static final String SLEEP_INDEX = "sleepIndex";
    public static final String UPDATE_HIS_DATE = "updateHisDate";
    public static final String BATTERY = "battery";

    public static final String BLE_BAND = "bleBand";//判断手环绑定解绑


    public static final String INIT_BLANCE = "INIT_BLANCE";

    public static final String INIT_ALARM = "INIT_ALARM"; //闹钟判断手环换绑


    /**用户数据 */
    public static final String USERNAME = "username";
    public static final String NICKNAME = "nikeName";
    public static final String SPORT_TARGET = "step";
    public static final String USER_RANK = "spDataRank";
    public static final String MONTH_STEP = "MonthStep";
    public static final String JPUSH_ID = "JpushRegistrationID";
    public static final String ALC_SHOW = "ALCShow";
    public static final String ABX_SHOW = "ABXShow";
    public static final String USER_EVENT = "EVENT";
    public static final String DOWNLOAD_SUCCESS_SIZE="download_success_size";

    /**指令相关 */
    public static final String APDU_FILE_CODE = "apduFileCode";
    public static final String APDU_FILE_NAME = "apduFileName";
    public static final String APDU_FILE_PATH = "apduFilePath";

    /**签到相关 */
    public static final String SIGNIN_STATE = "signInState";

    /**
     * 心率相关
     */
    public static final String HEART_DATA = "heartData";
    public static final String LOWER_DATA = "lowerBeatDate";
    public static final String HIGHER_DATA = "higherBeatDate";
    public static final String AVERAGE_DATA = "averageBeatDate";

    /**开发模式 */
    public static final String IS_VIBRATE = "is_vibrate";
    public static final String KEY_STEP_TT = "key_step_tt";
    public static final String KEY_STEP_T = "key_step_t";
    public static final String IS_KEY_STEP_TT = "is_key_step_tt";
    public static final String KEY_STEP_ON = "key_step_on";
}