package com.androidcat.fuelmore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidcat.acnet.consts.InterfaceUrl;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.News;
import com.androidcat.acnet.entity.response.NewsResponse;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.R;
import com.androidcat.fuelmore.ui.adapter.AdPageAdapter;
import com.androidcat.utilities.listener.OnSingleClickListener;

import java.util.List;
import java.util.Timer;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-19 14:39:10
 * add function description here...
 */
public class HomeActivity extends BaseActivity {

    public static final int MSG_UPDATE_IMAGE = 200;// 表示更新广告的标识
    private final static String TAG = "HomeActivity_Logger";

    private RelativeLayout adView;
    private ViewPager adViewPager;
    private View page;
    private View stationListView;
    private View payView;
    private View rechargeView;
    private View orderListView;
    private View userView;
    private TextView[] indicators;
    private AdPageAdapter adPageAdapter;
    private String[] ads_pics = new String[]{"",""};
    private String[] ads_url = {
            "",
            ""
    };
    private String[] ads_name = new String[]{"1","2"};
    private String[] pics_size = {"smallPic", "middlePic", "bigPic"};
    private int SCROLL_STATE = 0;

    Timer timer = new Timer();
    private int pageNum;
    private int cur_position = 0;// 表示当前广告栏的角标，默认为0
    private int lastPosition = 0;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        setListener();
        initData();
    }

    protected void initLayout() {
        setContentView(R.layout.activity_home);
        adView = (RelativeLayout) findViewById(R.id.adView);
        stationListView = findViewById(R.id.stationListView);
        payView = findViewById(R.id.payView);
        rechargeView = findViewById(R.id.rechargeView);
        orderListView = findViewById(R.id.orderListView);
        userView = findViewById(R.id.userView);
        pageNum = ads_url.length;
        initAd();
    }

    protected void setListener() {
        stationListView.setOnClickListener(onSingleClickListener);
        payView.setOnClickListener(onSingleClickListener);
        rechargeView.setOnClickListener(onSingleClickListener);
        orderListView.setOnClickListener(onSingleClickListener);
        userView.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        userManager = new UserManager(this,baseHandler);
        userManager.getNewsList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            switch (view.getId()){
                case R.id.userView:
                    gotoUserInfo();
                    break;
                case R.id.stationListView:
                    gotoStationList();
                    break;
                case R.id.payView:
                    gotoPayCode();
                    break;
                case R.id.rechargeView:
                    gotoRecharge();
                    break;
                case R.id.orderListView:
                    gotoOrderList();
                    break;
            }
        }
    };

    private void initAd() {
        adView.removeAllViews();
        page = LayoutInflater.from(this).inflate(R.layout.layout_ad_page, null);
        adViewPager = (ViewPager) page.findViewById(R.id.adpage);
        adPageAdapter = new AdPageAdapter(this,adViewPager);
        pageNum = ads_url.length;
        adPageAdapter.setData(pageNum, ads_pics, ads_url, ads_name);
        adViewPager.setAdapter(adPageAdapter);
        ViewGroup.LayoutParams param1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        adView.addView(page, param1);
        LinearLayout indicate = (LinearLayout) page.findViewById(R.id.indicator);
        if (ads_url != null && pageNum > 0) {
            indicators = new TextView[pageNum];
            for (int i = 0; i < pageNum; i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.cycleviewpager_indicator, null);
                indicators[i] = (TextView) view.findViewById(R.id.indicator);
                indicate.addView(view);
            }
            indicators[0].setBackgroundResource(R.mipmap.banner_focus);
        }
        adViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                cur_position = arg0;
                int position = cur_position % pageNum;
                if (lastPosition != position) {
                    for (int i = 0; i < pageNum; i++) {
                        if (i == position) {
                            indicators[i].setBackgroundResource(R.mipmap.banner_focus);
                        } else {
                            indicators[i].setBackgroundResource(R.mipmap.banner_unfocus);
                        }
                    }
                }
                lastPosition = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                SCROLL_STATE = arg0;
            }
        });

        adViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                } else if (action == MotionEvent.ACTION_UP) {
                }
                return false;
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            boolean isExit = intent.getBooleanExtra("isExit",false);
            if (isExit){
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_CIRCLE:
                if (SCROLL_STATE == 0 || SCROLL_STATE == 2) {
                    adViewPager.setCurrentItem(lastPosition + 1, true);
                    if (baseHandler.hasMessages(OptMsgConst.MSG_CIRCLE)) {
                        baseHandler.removeMessages(OptMsgConst.MSG_CIRCLE);
                    }
                    baseHandler.sendEmptyMessageDelayed(OptMsgConst.MSG_CIRCLE, 3000);
                }
                break;
            case OptMsgConst.MSG_GET_NEWS_LIST_FAIL:
                showToast((String) msg.obj);
                break;
            case OptMsgConst.MSG_GET_NEWS_LIST_SUCCESS:
                NewsResponse response = (NewsResponse) msg.obj;
                if (response == null || response.getContent() == null){
                    return;
                }
                List<News> advList = response.getContent().getContent();
                String size = getPicSize();
                pageNum = advList.size();
                ads_pics = new String[pageNum];
                ads_url = new String[pageNum];
                ads_name = new String[pageNum];
                for (int i = 0; i < pageNum; i++) {
                    News news = advList.get(i);
                    ads_url[i] = news.getContent();
                    if (pics_size[0].equals(size)) {
                        ads_pics[i] = InterfaceUrl.BASE_URL + InterfaceUrl.URL_CONTROLLER + news.getImg();
                    } else if (pics_size[1].equals(size)) {
                        ads_pics[i] = InterfaceUrl.BASE_URL + InterfaceUrl.URL_CONTROLLER + news.getImg();
                    } else if (pics_size[2].equals(size)) {
                        ads_pics[i] = InterfaceUrl.BASE_URL + InterfaceUrl.URL_CONTROLLER + news.getImg();
                    }
                    ads_name[i] = news.getTitle();
                }
                if (pageNum > 1){
                    baseHandler.sendEmptyMessageDelayed(OptMsgConst.MSG_CIRCLE, 3000);
                }
                initAd();
                break;
        }
    }

    private String getPicSize() {
        String picSize = pics_size[1];
        DisplayMetrics localDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        if (localDisplayMetrics.densityDpi <= 240) {
            picSize = pics_size[0];
        } else if (localDisplayMetrics.densityDpi <= 320) {
            picSize = pics_size[1];
        } else {
            picSize = pics_size[2];
        }
        return picSize;
    }

    private void gotoStationList(){
        Intent intent = new Intent(this,GasStationListActivity.class);
        startActivity(intent);
    }

    private void gotoOrderList(){
        Intent intent = new Intent(this,OrderListActivity.class);
        startActivity(intent);
    }

    private void gotoPayCode(){
        Intent intent = new Intent(this,MyWebBrowserActivity.class);
        intent.putExtra("action",MyWebBrowserActivity.BIZ_PAY_QRCODE);
        intent.putExtra("title","优惠支付");
        startActivity(intent);
    }

    private void gotoUserInfo(){
        Intent intent = new Intent(this,UserInfoActivity.class);
        startActivity(intent);
    }

    private void gotoRecharge(){
        Intent intent = new Intent(this,RechargeActivity.class);
        startActivity(intent);
    }
}
