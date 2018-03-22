package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.utilities.qrcode.ui.CaptureActivity;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class GatheringActivity extends BaseActivity {
    private final static String TAG = "GatheringActivity";

    private View back;
    private View payTypeView;
    private TextView mobileTv;
    private TextView payTypeTv;
    private EditText amountEt;
    private Button yesBtn;

    private String amount;
    private String qrCode;
    private OrderPayManager orderPayManager;
    private String[] payTypes = {"余额支付", "线下支付"};
    private OptionsPopupWindow pwOptions;
    private ArrayList<String> options1Items = new ArrayList<String>();

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.MSG_GATHER_START:
                showLoadingDialog();
                break;
            case OptMsgConst.MSG_GATHER_SUCCESS:
                dismissLoadingDialog();
                //showGatheringSuccessDialog();
                gotoGatherResult(true);
                break;
            case OptMsgConst.MSG_GATHER_FAIL:
                dismissLoadingDialog();
                gotoGatherResult(false);
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
                case R.id.payTypeView:
                    backgroundAlpha(1.0f);
                    pwOptions.showAtLocation(yesBtn, Gravity.BOTTOM, 0, 0);
                    String payType = SharePreferencesUtil.getValue(SPConsts.PAY_TYPE,"余额支付");
                    if (!Utils.isNull(payType)) {
                        if ("余额支付".equals(payType)) {
                            pwOptions.setSelectOptions(0);
                        }else if("线下支付".equals(payType)){
                            pwOptions.setSelectOptions(1);
                        }else {
                            pwOptions.setSelectOptions(0);
                        }
                    }
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
        payTypeView = findViewById(R.id.payTypeView);
        mobileTv = (TextView) findViewById(R.id.mobileTv);
        payTypeTv = (TextView) findViewById(R.id.payTypeTv);
        amountEt = (EditText) findViewById(R.id.editText);
        yesBtn = (Button) findViewById(R.id.yesBtn);

    }

    private void setListener(){
        back.setOnClickListener(onSingleClickListener);
        yesBtn.setOnClickListener(onSingleClickListener);
        payTypeView.setOnClickListener(onSingleClickListener);
    }

    private void initData(){
        orderPayManager = new OrderPayManager(this,baseHandler);
        mobileTv.setText(user.mobile);
        String payType = SharePreferencesUtil.getValue(SPConsts.PAY_TYPE,"余额支付");
        payTypeTv.setText(payType);
        //选项选择器
        options1Items.clear();
        pwOptions = new OptionsPopupWindow(this);
        //选项1
        options1Items.add(payTypes[0]);
        options1Items.add(payTypes[1]);
        pwOptions.setPicker(options1Items);
        //设置默认选中的项目
        int selection = Arrays.binarySearch(payTypes,payType);
        pwOptions.setSelectOptions(selection>0?selection:0);

        //监听确定选择按钮
        pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String payType = options1Items.get(options1);
                payTypeTv.setText(payType);
                SharePreferencesUtil.setValue(SPConsts.PAY_TYPE,payType);
            }
        });
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

    private void gotoGatherResult(boolean isSucceed){
        Intent intent = new Intent(this,GatherResultActivity.class);
        intent.putExtra("qrcode",qrCode);
        intent.putExtra("success",isSucceed);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    qrCode = bundle.getString("qrCodeString");
                    orderPayManager.gather(user.userName,user.id,user.authority,user.ciphertext,user.companyId,user.company,user.pointId,amount,qrCode);
                }
            }
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}