package com.androidcat.fuelmore.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androidcat.fuelmore.R;
import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.log.Logger;
import com.androidcat.utilities.permission.AndPermission;
import com.androidcat.utilities.permission.Permission;
import com.androidcat.utilities.permission.PermissionCodes;
import com.androidcat.utilities.permission.PermissionListener;
import com.androidcat.utilities.permission.PermissionUtils;
import com.androidcat.utilities.permission.Rationale;
import com.androidcat.utilities.permission.RationaleListener;
import com.androidcat.utilities.permission.SettingService;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    private final static String TAG = "SplashActivity";

    private View splashLogoView;
    private View infoView;
    private TextView verInfoTv;
    private Timer timer;
    private TimerTask task;
    private boolean isLaunchFromSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isLaunchFromSetting){
            isLaunchFromSetting = false;
            LogUtil.e(TAG, "您前往设置所需权限并返回，再次检查权限...");
            checkAppPermission();
        }
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
    protected void checkAppPermission() {
        super.checkAppPermission();
        if (AndPermission.hasPermission(this, PermissionUtils.appPermisssions)){
            LogUtil.e(TAG, "您已开启所需全部权限，正常启动..");
            starttask();
        }else {
            AndPermission.with(this)
                    .requestCode(PermissionCodes.PERMISSION_ALL)
                    .permission(Permission.STORAGE,Permission.LOCATION)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(SplashActivity.this, rationale).show();
                        }
                    })
                    .callback(permissionCallback)
                    .start();
        }
    }

    private PermissionListener permissionCallback = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            //Logger.file("您已授权访问");
            for (String per : grantPermissions){
                LogUtil.e(TAG,"您已授权访问:"+per);
            }
            LogUtil.e(TAG,"您已允许所需全部权限，开始启动..");
            starttask();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            for (String per : deniedPermissions){
                //Logger.file("您已拒绝访问:"+per);
                LogUtil.e(TAG,"您已拒绝访问:"+per);
            }
            switch (requestCode){
                case PermissionCodes.PERMISSION_ALL:
                    // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                    if (PermissionUtils.hasDeniedPermission(deniedPermissions)) {
                        // 第三种：自定义dialog样式。
                        SettingService settingService =
                                AndPermission.defineSettingDialog(SplashActivity.this, PermissionCodes.PERMISSION_SETTING);
                        showPermissionSettingDialog(settingService);
                        // 你的dialog点击了确定调用：
                        // settingService.execute();
                        // 你的dialog点击了取消调用：
                        // settingService.cancel();
                    }else {
                        starttask();
                    }
                    break;
            }
        }
    };

    private void showPermissionSettingDialog(final SettingService settingService) {
        String info = "我们需要的一些权限被您拒绝,请您到设置页面手动授权，否则功能无法正常使用！";
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content(info)
                .contentTextColor(getResources().getColor(R.color.text_black))
                .title("")
                .btnText("取消", "确定") //
                .style(NormalDialog.STYLE_TWO)//
                .showAnim(new FlipVerticalSwingEnter())//
                .show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                settingService.cancel();
                Logger.file("您已拒绝存储访问");
                showToast("您必须授权才能正常使用该功能！");
                LogUtil.e(TAG, "您已拒绝了所需权限，带伤启动..");
                starttask();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                settingService.execute();
                isLaunchFromSetting = true;
            }
        });
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