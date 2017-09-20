package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidcat.fuelmore.merchant.FuelMoreApplication;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SharePreferencesUtil;

public class UserInfoActivity extends BaseActivity {
    private final static String TAG = "SplashActivity";

    private View back;
    private TextView mobileTv;
    private TextView realNameTv;
    private TextView companyTv;
    private Button exitBtn;

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            switch (view.getId()){
                case R.id.layout_back:
                    finish();
                    break;
                case R.id.exitBtn:
                    exitLogin();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private void initView(){
        setContentView(R.layout.activity_user_info);
        Resources resource = getResources();
        Configuration c = resource.getConfiguration();
        c.fontScale = 1.0f;
        resource.updateConfiguration(c, resource.getDisplayMetrics());

        back = findViewById(R.id.layout_back);
        mobileTv = (TextView) findViewById(R.id.mobileTv);
        realNameTv = (TextView) findViewById(R.id.realNameTv);
        companyTv = (TextView) findViewById(R.id.companyTv);
        exitBtn = (Button) findViewById(R.id.exitBtn);

    }

    private void setListener(){
        back.setOnClickListener(onSingleClickListener);
        exitBtn.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        mobileTv.setText(user.mobile);
        realNameTv.setText(user.mobile);
        companyTv.setText(user.company);
    }

    private void exitLogin(){
        user.token = "";
        SharePreferencesUtil.setObject(user);
        FuelMoreApplication.activities.remove(this);
        FuelMoreApplication.exitActivities();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}