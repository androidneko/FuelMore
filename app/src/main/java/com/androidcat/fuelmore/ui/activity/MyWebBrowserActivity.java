package com.androidcat.fuelmore.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.androidcat.acnet.consts.InterfaceUrl;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.fuelmore.R;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.view.ProgressWebView;


public class MyWebBrowserActivity extends BaseActivity {
    private static String TAG = "MyWebBrowserActivity";
    public static final String BIZ_PAY_QRCODE = "biz_pay_qrcode";

    private TextView titleTv;
    private View back;
    private View titleView;
    private View failView;
    private ProgressWebView webview;
    private String title = "";
    private String url = "";
    private String action = "";
    private boolean isShowTitle = true;
    private OrderPayManager orderPayManager;

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.MSG_GET_QR_CODE_START:
                showLoadingDialog();
                break;
            case OptMsgConst.MSG_GET_QR_CODE_FAIL:
                dismissLoadingDialog();
                failView.setVisibility(View.VISIBLE);
                showToast("获取二维码失败，请稍候重试");
                break;
            case OptMsgConst.MSG_GET_QR_CODE_SUCCESS:
                dismissLoadingDialog();
                StringContentResponse contentResponse = (StringContentResponse) msg.obj;
                String key = contentResponse.getContent();
                url = InterfaceUrl.BASE_URL + "/qrcode/toQrcodePage?key=" + key;
                webview.loadUrl(url);
                failView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        setupWebView();
        setListener();
        initData();
    }

    protected void initLayout() {
        setContentView(R.layout.activity_webview);
        titleView = findViewById(R.id.titleView);
        failView = findViewById(R.id.failView);
        titleTv = (TextView) findViewById(R.id.tv_title);
        back = findViewById(R.id.back);
        webview = (ProgressWebView) findViewById(R.id.webView);
    }

    private void initData() {
        orderPayManager = new OrderPayManager(this,baseHandler);
        title = getIntent().getExtras().getString("title");
        url = getIntent().getExtras().getString("url");
        action = getIntent().getExtras().getString("action");
        //isShowTitle=getIntent().getExtras().getString("isShowTitle");
        if (isShowTitle) {
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
        if (!Utils.isNull(title)) {
            titleTv.setText(title);
        }else {
            titleTv.setText("详情页面");
        }
        if (BIZ_PAY_QRCODE.equals(action)){
            orderPayManager.requestQrcode(user.userName, user.id, user.companyId,user.company, user.authority, user.ciphertext);
        }else{
            webview.loadUrl(url);
        }
    }

    protected void setListener() {
        back.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        failView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (BIZ_PAY_QRCODE.equals(action)){
                    orderPayManager.requestQrcode(user.userName, user.id, user.companyId,user.company, user.authority, user.ciphertext);
                }
            }
        });
    }

    private void setupWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        //webview.addJavascriptInterface(new Contact(), "contact");
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDomStorageEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        webview.getSettings().setAllowFileAccess(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}