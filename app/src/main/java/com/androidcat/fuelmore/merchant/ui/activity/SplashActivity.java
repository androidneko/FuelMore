package com.androidcat.fuelmore.merchant.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androidcat.fuelmore.merchant.R;
import com.androidcat.utilities.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    private final static String TAG = "SplashActivity";

    private View splashLogoView;
    private View infoView;
    private TextView verInfoTv;
    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        starttask();
    }

    private void initView(){
        setContentView(R.layout.activity_splash);
        Resources resource = getResources();
        Configuration c = resource.getConfiguration();
        c.fontScale = 1.0f;
        resource.updateConfiguration(c, resource.getDisplayMetrics());

        splashLogoView = findViewById(R.id.splashLogoView);
        infoView = findViewById(R.id.infoView);
        verInfoTv = (TextView) findViewById(R.id.verInfoTv);

        animateSplash();
    }

    private void animateSplash(){
        String ver = Utils.getVersionName(this);
        verInfoTv.setText("版本：" + ver);
        splashLogoView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        infoView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void starttask() {
        if (timer == null) {
            timer = new Timer();
        }
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (user != null && !Utils.isNull(user.token)) {
                            Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        stopTimerTask();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        timer.schedule(task, 2000);
    }


    private void stopTimerTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            stopTimerTask();
        }
        return super.onKeyDown(keyCode, event);
    }
}