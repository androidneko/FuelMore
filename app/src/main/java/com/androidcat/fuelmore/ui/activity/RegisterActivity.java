package com.androidcat.fuelmore.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.utilities.view.TimerView;

public class RegisterActivity extends BaseActivity {

    private Button mBtnOK = null;//注册
    private EditText mPhoneNum = null;//手机号
    private EditText mVerifyCode = null; //验证码
    private EditText mPassword = null;
    private TimerView mGetverify = null;//获取验证码
    private View mBack = null; //返回按钮
    private String mUserName = "";
    private UserManager userManager;

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            String phoneTxt = mPhoneNum.getText().toString().trim();
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.btn_ok:
                    if (!checkPhone(phoneTxt)){
                        return;
                    }
                    String verify_code_txt = mVerifyCode.getText().toString().trim();
                    String pwdtxtbefore = mPassword.getText().toString().trim();
                    if (pwdtxtbefore.equals("")) {
                        showToast("密码不能为空");
                        return;
                    }
                    if (pwdtxtbefore.length() < 6 || pwdtxtbefore.length() > 20) {
                        showToast("密码长度为6到20位");
                        return;
                    }
                    if (verify_code_txt.equals("")) {
                        showToast("请输入验证码");
                        return;
                    } else if (verify_code_txt.length() != 4) {
                        showToast("验证码长度为4位");
                        return;
                    }
                    if (Utils.isNetworkAvailable(RegisterActivity.this)) {
                        mUserName = phoneTxt;
                        userManager.register(phoneTxt, pwdtxtbefore, verify_code_txt);
                    } else {
                        showToast("网络不给力");
                    }
                    break;
                case R.id.verify_code_btn:
                    if (checkPhone(phoneTxt)) {
                        userManager.getVerifyCode(mPhoneNum.getText().toString().trim());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intLayout();
        setListener();
    }

    protected void intLayout() {
        setContentView(R.layout.activity_regist);
        mBack = findViewById(R.id.back);
        mPhoneNum = (EditText) findViewById(R.id.ed_phonenum);
        mBtnOK = (Button) findViewById(R.id.btn_ok);
        mVerifyCode = (EditText) findViewById(R.id.ed_verifycode);
        mPassword = (EditText) findViewById(R.id.ed_password);
        mGetverify = (TimerView) findViewById(R.id.verify_code_btn);
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_GET_VERIFYCODE_START:
                showLoadingDialog("正在获取验证码...");
                break;
            case OptMsgConst.MSG_GET_VERIFYCODE_SUCCESS:
                dismissLoadingDialog();
                showToast("获取验证码成功");
                mGetverify.startCountingDown();
                break;
            case OptMsgConst.MSG_GET_VERIFYCODE_FAIL:
                dismissLoadingDialog();
                showToast((String) msg.obj);
                break;
            case OptMsgConst.MSG_REIST_START:
                showLoadingDialog("正在注册...");
                break;
            case OptMsgConst.MSG_REIST_SUCCESS:
                dismissLoadingDialog();
                if (!Utils.isNull(mUserName)) {
                    SharePreferencesUtil.setValue(SPConsts.USERNAME, mUserName);
                }
                showToast("注册成功！");
                finish();
                break;
            case OptMsgConst.MSG_REIST_FAIL:
                dismissLoadingDialog();
                showToast((String) msg.obj);
                mUserName = "";
                break;
        }
    }

    protected void setListener() {
        userManager = new UserManager(RegisterActivity.this, baseHandler);
        mGetverify.setOnClickListener(onClickListener);
        mBtnOK.setOnClickListener(onClickListener);
        mBack.setOnClickListener(onClickListener);
    }

    private boolean checkPhone(String phoneTxt) {
        if (Utils.isNull(phoneTxt) || phoneTxt.length() != 11) {
            showToast("请输入合法的手机号");
            return false;
        }
        //验证手机号是否合法
        if (!checkPhoneNumber(phoneTxt)) {
            showToast("请输入合法的手机号");
            return false;
        }
        return true;
    }
}