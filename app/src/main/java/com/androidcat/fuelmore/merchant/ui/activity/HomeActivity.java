package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.listener.OnSingleClickListener;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-19 14:39:10
 * add function description here...
 */
public class HomeActivity extends BaseActivity {
    private final static String TAG = "HomeActivity_Logger";
    private View payView;
    private View orderListView;
    private View userView;

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
        payView = findViewById(R.id.payView);
        orderListView = findViewById(R.id.orderListView);
        userView = findViewById(R.id.userView);
    }

    protected void setListener() {
        payView.setOnClickListener(onSingleClickListener);
        orderListView.setOnClickListener(onSingleClickListener);
        userView.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        userManager = new UserManager(this,baseHandler);
        //userManager.getNewsList();
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
                case R.id.payView:
                    gotoMoney();
                    break;
                case R.id.orderListView:
                    gotoOrderList();
                    break;
            }
        }
    };

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
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_GET_NEWS_LIST_SUCCESS:
                break;
        }
    }

    private void gotoUserInfo(){
        Intent intent = new Intent(this,UserInfoActivity.class);
        startActivity(intent);
    }

    private void gotoMoney(){
        Intent intent = new Intent(this,GatheringActivity.class);
        startActivity(intent);
    }

    private void gotoOrderList(){
        Intent intent = new Intent(this,OrderListActivity.class);
        startActivity(intent);
    }
}
