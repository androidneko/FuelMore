package com.androidcat.fuelmore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.fuelmore.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;

/**
 * Created by TuT on 2015/12/6.
 */
public class ChangePswActivity extends BaseActivity {

    private View tvOk;
    private View back;
    private EditText old_pwdTxt;
    private EditText new_pwdTxt1;
    private EditText new_pwdTxt2; //再次确认密码

    private UserManager manager;
    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    String password = old_pwdTxt.getText().toString().trim();
                    String new_password = new_pwdTxt1.getText().toString().trim();
                    String new_password2 = new_pwdTxt2.getText().toString().trim();
                    if (Utils.isNull(password)) {
                        showToast("旧密码不能为空");
                        return;
                    }
                    if (Utils.isNull(new_password)) {
                        showToast("新密码不能为空");
                        return;
                    }
                    if (Utils.isNull(new_password2)) {
                        showToast("确认密码不能为空");
                        return;
                    }
                    if (!Utils.isNumOrLetter(new_password)) {
                        showToast("密码包含非法字符");
                        return;
                    }
                    if (password.length() < 6 || new_password.length() < 6) {
                        showToast("密码不能少于6位");
                        return;
                    }
                    if (password.length() > 20 || new_password.length() > 20) {
                        showToast("密码不能超过20位");
                        return;
                    }
                    if (!new_password2.equals(new_password)) {
                        showToast("新密码与确认密码不一致");
                        return;
                    }
                    if (password.equals(new_password)) {
                        showToast("新密码不能与旧密码相同");
                        return;
                    }
                    final String username = SharePreferencesUtil.getValue(SPConsts.USERNAME);
                    if (Utils.isNull(username)) {
                        //如果用户没登陆
                        showToast("请登录之后查看详信息");
                    } else {
                        manager.modifyPwd(username, password, new_password);
                    }
                    break;
                case R.id.layout_back:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_CHANGEPSW_START:
                showLoadingDialog("正在修改密码...");
                break;
            case OptMsgConst.MSG_CHANGEPSW_SUCCESS:
                dismissLoadingDialog();
                showToast("修改密码成功，请重新登录");
                //SharePreferencesUtil.setObject(new UserEntity());
                startActivity(new Intent(ChangePswActivity.this, LoginActivity.class));
                finish();
                break;
            case OptMsgConst.MSG_CHANGEPSW_FAIL:
                dismissLoadingDialog();
                showToast("修改密码失败");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intLayout();
        setListener();
    }

    protected void intLayout() {
        setContentView(R.layout.activity_change_psw);
        back = findViewById(R.id.layout_back);
        tvOk = findViewById(R.id.btn_ok);
        old_pwdTxt = (EditText) findViewById(R.id.old_pwdTxt);
        new_pwdTxt1 = (EditText) findViewById(R.id.new_pwdTxt1);
        new_pwdTxt2 = (EditText) findViewById(R.id.new_pwdTxt2);
    }

    protected void setListener() {
        manager = new UserManager(this, baseHandler);
        tvOk.setOnClickListener(onSingleClickListener);
        back.setOnClickListener(onSingleClickListener);
    }
}