package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.merchant.AppConst;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.fuelmore.merchant.entity.FuelMoreEvent;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.anroidcat.acwidgets.ClearEditText;

import de.greenrobot.event.EventBus;

/**
 * Created by coolbear on 15/4/17.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private final static int MSG_LOGIN_CANCLE = 0x0004;

    private Button mLogin = null;
    private Button mRegist = null;
    private TextView forget_pwd_btn;
    private ClearEditText usernameTxt;
    private ClearEditText pwdTxt;
    private View fastLoginView;
    private UserManager loginManager;
    private String mUsername;
    private String pwdtxtbefore;

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.fastLoginView:
                    startActivity(new Intent(LoginActivity.this, FastLoginActivity.class));
                    break;
                case R.id.forget_pwd_btn:
                    startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                    break;
                case R.id.vg_login:
                    String nameTxt = usernameTxt.getText().toString().trim();
                    pwdtxtbefore = pwdTxt.getText().toString().trim();
                    if (Utils.isNull(nameTxt)) {
                        showToast("请输入手机号");
                        return;
                    }
                    //验证手机号是否合法
                    if (!checkPhoneNumber(nameTxt)) {
                        showToast("请输入合法的手机号");
                        return;
                    }
                    if (Utils.isNull(pwdtxtbefore)) {
                        showToast("请输入密码");
                        return;
                    }
                    if (pwdtxtbefore.length() < 6) {
                        showToast("密码不能少于6位");
                        return;
                    }
                    String password = pwdtxtbefore;
                    login(nameTxt, password);
                    break;
                case R.id.user_regist:
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intLayout();
        setListener();
        EventBus.getDefault().register(this);
    }

    protected void intLayout() {
        setContentView(R.layout.activity_login);
        mLogin = (Button) findViewById(R.id.vg_login);
        mRegist = (Button) findViewById(R.id.user_regist);
        forget_pwd_btn = (TextView) findViewById(R.id.forget_pwd_btn);
        usernameTxt = (ClearEditText) findViewById(R.id.usernameTxt);
        pwdTxt = (ClearEditText) findViewById(R.id.pwdTxt);
        fastLoginView = findViewById(R.id.fastLoginView);

        mLogin.setBackgroundResource(R.drawable.btn_shape_enable);
        mLogin.setEnabled(false);
    }


    protected void onResume() {
        super.onResume();
        dismissLoadingDialog();
        mUsername = user.userName;
        if (!Utils.isNull(mUsername)) {
            usernameTxt.setText(mUsername);
        }
        pwdTxt.setText("");
    }

    protected void setListener() {
        loginManager = new UserManager(this, baseHandler);
        forget_pwd_btn.setOnClickListener(onClickListener);
        mLogin.setOnClickListener(onClickListener);
        mRegist.setOnClickListener(onClickListener);
        fastLoginView.setOnClickListener(onClickListener);
        pwdTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mLogin.setBackgroundResource(R.drawable.button_selector);
                    mLogin.setEnabled(true);
                } else {
                    mLogin.setBackgroundResource(R.drawable.btn_shape_enable);
                    mLogin.setEnabled(false);
                }
            }
        });
    }

    private void login(final String username, final String pwd) {
        loginManager.login(username, pwd);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (AppConst.IS_MAINACTIVITY_START) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   //注意本行的FLAG设置
                startActivity(intent);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_LOGIN_FAIL:
            case OptMsgConst.FAST_LOGIN_FAIL:
                dismissLoadingDialog();
                if (msg.obj == null || Utils.isNull((String) msg.obj)) {
                    showToast("登录失败");
                } else {
                    showToast((String) msg.obj);
                }
                break;
            case OptMsgConst.MSG_LOGIN_START:
                showLoadingDialog("正在登录...");
                break;
            case OptMsgConst.MSG_LOGIN_SUCCESS:
                dismissLoadingDialog();
                saveUser((LoginResponse) msg.obj);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        cancelToast();
        EventBus.getDefault().unregister(this);
    }

    private void saveUser(LoginResponse loginResponse){
        User user = new User();
        user.id = loginResponse.getContent().getUserId();
        user.userName = loginResponse.getContent().userName;
        user.mobile = loginResponse.getContent().userName;
        user.authority = loginResponse.getContent().getAuthority();
        user.token = loginResponse.getContent().getUserId();
        user.companyId = loginResponse.getContent().getCompanyId();
        user.ciphertext = loginResponse.getContent().getCiphertext();
        user.cipherqrcode = loginResponse.getContent().getCipherqrcode();
        user.pointId = loginResponse.content.pointId;
        // TODO: 2017-8-21 add more properties here
        SharePreferencesUtil.setObject(user);
    }

    public void onEventMainThread(FuelMoreEvent event){
        switch (event.code){
            case FuelMoreEvent.CODE_FINISH_LOGIN:
                finish();
                break;
        }
    }
}