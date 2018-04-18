package com.androidcat.fuelmore.merchant.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.entity.response.TruckLicenseResponse;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.fuelmore.merchant.entity.FuelMoreEvent;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.utilities.view.TimerView;
import com.anroidcat.acwidgets.ClearEditText;

import de.greenrobot.event.EventBus;


public class GatherResultActivity extends BaseActivity {

    private View back;
    private View licenseView;
    private ImageView retIv;
    private TextView retTv;
    private TextView licenseTv;
    private TextView amtTv;
    private TextView payTypeTv;
    private Button okBtn;

    private String qrcode;
    private String amount;
    private OrderPayManager orderPayManager;

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.layout_back:
                    finish();
                    break;
                case R.id.okBtn:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_GET_TRUCK_LICENSE_SUCCESS:
                TruckLicenseResponse response = (TruckLicenseResponse) msg.obj;
                if (response != null && response.content != null && response.content.truck != null){
                    licenseView.setVisibility(View.VISIBLE);
                    licenseTv.setText(response.content.truck);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intLayout();
        setListener();
        initData();
    }

    @SuppressLint("WrongViewCast")
    protected void intLayout() {
        setContentView(R.layout.activity_pay_result);
        licenseView = findViewById(R.id.licenseView);
        retIv = (ImageView) findViewById(R.id.retIv);
        retTv = (TextView) findViewById(R.id.retTv);
        licenseTv = (TextView) findViewById(R.id.licenseTv);
        amtTv = (TextView) findViewById(R.id.amtTv);
        payTypeTv = (TextView) findViewById(R.id.payTypeTv);
        okBtn = (Button) findViewById(R.id.okBtn);
        back = findViewById(R.id.layout_back);
    }

    protected void setListener() {
        orderPayManager = new OrderPayManager(this, baseHandler);
        okBtn.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
    }

    @SuppressLint("NewApi")
    private void initData(){
        qrcode = getIntent().getStringExtra("qrcode");
        amount = getIntent().getStringExtra("amount");
        String payType = SharePreferencesUtil.getValue(SPConsts.PAY_TYPE);
        if (getIntent().getBooleanExtra("success",false)){
            retIv.setBackground(getResources().getDrawable(R.mipmap.refund_success_img));
            retTv.setText("扣款成功!");
            amtTv.setText("支付金额:"+amount+"元");
            payTypeTv.setText("支付方式:"+payType);
            orderPayManager.getTruckLicense(qrcode,true);
        }else {
            retIv.setBackground(getResources().getDrawable(R.mipmap.refund_fail_img));
            retTv.setText("扣款失败!");
            amtTv.setText("支付金额:"+amount+"元");
            payTypeTv.setText("支付方式:"+payType);
        }
    }
}