package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.qrcode.ui.CaptureActivity;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

public class GatheringActivity extends BaseActivity {
    private final static String TAG = "GatheringActivity";

    private View back;
    private TextView mobileTv;
    private EditText amountEt;
    private Button yesBtn;

    private String amount;
    private OrderPayManager orderPayManager;

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.MSG_GATHER_START:
                showLoadingDialog();
                break;
            case OptMsgConst.MSG_GATHER_SUCCESS:
                dismissLoadingDialog();
                showGatheringSuccessDialog();
                break;
            case OptMsgConst.MSG_GATHER_FAIL:
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
                case R.id.yesBtn:
                    gather();
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
        setContentView(R.layout.activity_gather);
        Resources resource = getResources();
        Configuration c = resource.getConfiguration();
        c.fontScale = 1.0f;
        resource.updateConfiguration(c, resource.getDisplayMetrics());

        back = findViewById(R.id.layout_back);
        mobileTv = (TextView) findViewById(R.id.mobileTv);
        amountEt = (EditText) findViewById(R.id.editText);
        yesBtn = (Button) findViewById(R.id.yesBtn);

    }

    private void setListener(){
        back.setOnClickListener(onSingleClickListener);
        yesBtn.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        orderPayManager = new OrderPayManager(this,baseHandler);
        mobileTv.setText(user.mobile);
    }

    private void gather(){
        amount = amountEt.getText().toString();
        if (Utils.isNull(amount)){
            showToast("请输入正确的金额");
            return;
        }
        if (!Utils.isNumeric(amount)){
            showToast("请输入正确的金额");
            return;
        }
        startActivityForResult(new Intent(this, CaptureActivity.class),13);
    }

    private void showGatheringSuccessDialog(){
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content("收款成功！")
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(1)
                .btnText("好的")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    String qrCode = bundle.getString("qrCodeString");
                    orderPayManager.gather(user.userName,user.id,user.authority,user.ciphertext,user.companyId,user.company,user.pointId,amount,qrCode);
                }
            }
        }
    }
}