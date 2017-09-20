package com.androidcat.fuelmore.merchant.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.view.TimerView;
import com.anroidcat.acwidgets.ClearEditText;


public class ForgetPwdActivity extends BaseActivity {

    private LinearLayout layout_back;
    private EditText phone_input;
    private EditText verify_code_input;
    private EditText pwdTxt;
    private EditText pwdTxt2;
    private TimerView verify_code_btn;
    private Button getBtn;

    private UserManager manager;
    private String userName;
    private String verifyCode;
    private String pwdtxtbefore;
    private String pwdtxtcheck;

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
                    pwdtxtbefore = pwdTxt.getText().toString().trim();
                    pwdtxtcheck = pwdTxt2.getText().toString().trim();
                    if (Utils.isNull(verify_code_txt)) {
                        showToast("请输入验证码");
                        return;
                    } else if (verify_code_txt.length() != 4) {
                        showToast("验证码长度4位");
                        return;
                    }
                    verifyCode = verify_code_txt;
                    if (Utils.isNull(pwdtxtbefore)) {
                        showToast("请输入密码");
                        return;
                    }
                    if (!Utils.isNumOrLetter(pwdtxtbefore)) {
                        showToast("密码包含非法字符");
                        return;
                    }
                    if (pwdtxtbefore.length() < 6) {
                        showToast("密码不能少于6位");
                        return;
                    }
                    if (!pwdtxtbefore.equals(pwdtxtcheck)) {
                        showToast("密码输入不一致");
                        return;
                    }
                    manager.resetPwd(userName, pwdtxtcheck, verifyCode);
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
            case OptMsgConst.MSG_RESETPASSWORD_START:
                showLoadingDialog("正在重置密码...");
                break;
            case OptMsgConst.MSG_GET_VERIFYCODE_SUCCESS:
                showToast("获取验证码成功！");
                dismissLoadingDialog();
                verify_code_btn.startCountingDown();
                break;
            case OptMsgConst.MSG_RESETPASSWORD_FAIL:
            case OptMsgConst.MSG_GET_VERIFYCODE_FAIL:
                showToast((String) msg.obj);
                dismissLoadingDialog();
                break;
            case OptMsgConst.MSG_RESETPASSWORD_SUCCESS:
                showToast("密码重置成功");
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
        setContentView(R.layout.activity_forget_pwd);
        verify_code_btn = (TimerView) findViewById(R.id.verify_code_btn);
        phone_input = (ClearEditText) findViewById(R.id.phone_input);
        verify_code_input = (ClearEditText) findViewById(R.id.verify_code_input);
        getBtn = (Button) findViewById(R.id.getBtn);
        pwdTxt = (ClearEditText) findViewById(R.id.pwdTxt);
        pwdTxt2 = (ClearEditText) findViewById(R.id.pwdTxt2);
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

}