package com.androidcat.fuelmore.ui.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.UserInfoContent;
import com.androidcat.acnet.entity.response.UserInfoResponse;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

public class RechargeActivity extends BaseActivity {
    private final static String TAG = "RechargeActivity";

    private View back;
    private TextView mobileTv;
    private TextView balanceTv;
    private TextView pointTv;
    private TextView refuelCountTv;
    private TextView refuelAmountTv;
    private EditText amountEt;
    private Button yesBtn;

    private OrderPayManager orderPayManager;
    private UserManager userManager;

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.MSG_GET_USERINFO_START:
                showLoadingDialog();
                break;
            case OptMsgConst.MSG_GET_USERINFO_SUCCESS:
                dismissLoadingDialog();
                UserInfoResponse response = (UserInfoResponse) msg.obj;
                UserInfoContent content = response.content;
                user.userRealName = content.userRealName;
                user.balance = content.balance;
                user.integral = content.integral;
                user.consumptionCount = content.consumptionCount;
                user.consumptionAmount = content.consumptionAmount;
                SharePreferencesUtil.setObject(user);
                setUserView();
                break;
            case OptMsgConst.MSG_GET_USERINFO_FAIL:
                dismissLoadingDialog();
                showToast((String) msg.obj);
                break;
            case OptMsgConst.MSG_ADD_RECHARGE_START:
                showLoadingDialog();
                break;
            case OptMsgConst.MSG_ADD_RECHARGE_SUCCESS:
                dismissLoadingDialog();
                showRechargeSuccessDialog();
                userManager.getUserInfo(user.id,user.companyId,user.ciphertext);//刷新界面
                break;
            case OptMsgConst.MSG_ADD_RECHARGE_FAIL:
                dismissLoadingDialog();
                showToast((String) msg.obj);
                break;
        }
    }

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            switch (view.getId()){
                case R.id.layout_back:
                    finish();
                    break;
                case R.id.exitBtn:
                    recharge();
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
        setContentView(R.layout.activity_recharge);
        Resources resource = getResources();
        Configuration c = resource.getConfiguration();
        c.fontScale = 1.0f;
        resource.updateConfiguration(c, resource.getDisplayMetrics());

        back = findViewById(R.id.layout_back);
        mobileTv = (TextView) findViewById(R.id.mobileTv);
        balanceTv = (TextView) findViewById(R.id.balanceTv);
        pointTv = (TextView) findViewById(R.id.pointTv);
        refuelCountTv = (TextView) findViewById(R.id.refuelCountTv);
        refuelAmountTv = (TextView) findViewById(R.id.refuelAmountTv);
        amountEt = (EditText) findViewById(R.id.editText);
        yesBtn = (Button) findViewById(R.id.exitBtn);

    }

    private void setListener(){
        back.setOnClickListener(onSingleClickListener);
        yesBtn.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        orderPayManager = new OrderPayManager(this,baseHandler);
        userManager = new UserManager(this,baseHandler);
        mobileTv.setText(user.mobile);
        userManager.getUserInfo(user.id,user.companyId,user.ciphertext);
    }

    private void setUserView(){
        balanceTv.setText(user.balance+"元");
        pointTv.setText(user.integral);
        refuelCountTv.setText(user.consumptionCount+"次");
        refuelAmountTv.setText(user.consumptionCount+"元");
    }

    private void recharge(){
        String amount = amountEt.getText().toString();
        if (Utils.isNull(amount)){
            showToast("请输入正确的金额");
            return;
        }
        if (!Utils.isNumeric(amount)){
            showToast("请输入正确的金额");
            return;
        }
        orderPayManager.addRecharge(user.userName,user.id,user.ciphertext,user.companyId,amount,user.company);
    }

    private void showRechargeSuccessDialog(){
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content("充值申请已提交成功！")
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(1)
                .btnText("好的")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                        dialog.dismiss();
                    }
                });
    }
}