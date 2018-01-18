package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.fuelmore.merchant.entity.FuelMoreEvent;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.utilities.view.TimerView;
import com.anroidcat.acwidgets.ClearEditText;

import de.greenrobot.event.EventBus;


public class FastLoginActivity extends BaseActivity {

    private LinearLayout layout_back;
    private EditText phone_input;
    private EditText verify_code_input;
    private TimerView verify_code_btn;
    private Button getBtn;

    private UserManager manager;
    private String userName;
    private String verifyCode;

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.layout_back:
                    finish();
                    break;
                case R.id.getBtn:
                    if (!checkUserName()) {
                        return;
                    }
                    String verify_code_txt = verify_code_input.getText().toString().trim();
                    if (Utils.isNull(verify_code_txt)) {
                        showToast("请输入验证码");
                        return;
                    } else if (verify_code_txt.length() != 4) {
                        showToast("验证码长度4位");
                        return;
                    }
                    verifyCode = verify_code_txt;
                    manager.fastLogin(userName,verifyCode);
                    break;
                case R.id.verify_code_btn:
                    if (!checkUserName()) {
                        return;
                    }
                    manager.getVerifyCode(userName);
                    break;
            }
        }
    };

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_GET_VERIFYCODE_START:
                showLoadingDialog("正在获取验证码...");
                break;
            case OptMsgConst.FAST_LOGIN_START:
                showLoadingDialog("正在登录...");
                break;
            case OptMsgConst.MSG_GET_VERIFYCODE_SUCCESS:
                showToast("获取验证码成功！");
                dismissLoadingDialog();
                verify_code_btn.startCountingDown();
                break;
            case OptMsgConst.MSG_GET_VERIFYCODE_FAIL:
            case OptMsgConst.FAST_LOGIN_FAIL:
                showToast((String) msg.obj);
                dismissLoadingDialog();
                break;
            case OptMsgConst.FAST_LOGIN_SUCCESS:
                showToast("登录成功");
                dismissLoadingDialog();
                saveUser((LoginResponse) msg.obj);
                EventBus.getDefault().post(new FuelMoreEvent(FuelMoreEvent.CODE_FINISH_LOGIN));
                Intent intent = new Intent(FastLoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intLayout();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName = user.userName;
        if (!Utils.isNull(userName)) {
            phone_input.setText(userName);
        }
    }

    protected void intLayout() {
        setContentView(R.layout.activity_fast_login);
        verify_code_btn = (TimerView) findViewById(R.id.verify_code_btn);
        phone_input = (ClearEditText) findViewById(R.id.phone_input);
        verify_code_input = (ClearEditText) findViewById(R.id.verify_code_input);
        getBtn = (Button) findViewById(R.id.getBtn);
        layout_back = (LinearLayout) findViewById(R.id.layout_back);
    }

    protected void setListener() {
        manager = new UserManager(this, baseHandler);
        getBtn.setOnClickListener(onClickListener);
        verify_code_btn.setOnClickListener(onClickListener);
        layout_back.setOnClickListener(onClickListener);
    }

    private boolean checkUserName() {
        String phone_txt = phone_input.getText().toString().trim();
        //验证手机号是否合法
        if (!Utils.isMobileNO(phone_txt)) {
            showToast("请输入正确的手机号");
            return false;
        }
        userName = phone_txt;
        return true;
    }

    private void saveUser(LoginResponse loginResponse){
        User user = new User();
        user.id = loginResponse.getContent().getUserId();
        user.userName = loginResponse.getContent().userName;
        user.authority = loginResponse.getContent().getAuthority();
        user.mobile = loginResponse.getContent().userName;
        user.token = loginResponse.getContent().getUserId();
        user.companyId = loginResponse.getContent().getCompanyId();
        user.company = loginResponse.getContent().companyName;
        user.ciphertext = loginResponse.getContent().getCiphertext();
        user.cipherqrcode = loginResponse.getContent().getCipherqrcode();
        user.pointId = loginResponse.content.pointId;
        // TODO: 2017-8-21 add more properties here
        SharePreferencesUtil.setObject(user);
    }
}