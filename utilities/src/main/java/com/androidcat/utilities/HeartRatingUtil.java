package com.androidcat.utilities;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-2-22 19:05:58
 * add function description here...
 */
public class HeartRatingUtil {

    private static final int START_BEATING = 1;
    private static final int STOP_BEATING = 0;

    private ImageView imageView;
    private Handler beatingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_BEATING:
                    playHeartbeatAnimation(imageView);
                    sendEmptyMessageDelayed(START_BEATING, 1000);
                    break;
                case STOP_BEATING:
                    break;
            }
        }
    };

    public HeartRatingUtil(ImageView imageView) {
        this.imageView = imageView;
    }

    public void startBeating() {
        beatingHandler.obtainMessage(START_BEATING).sendToTarget();
    }

    public void stopBeating() {
        if (beatingHandler.hasMessages(START_BEATING)) {
            beatingHandler.removeMessages(START_BEATING);
        }
    }

    public void playHeartbeatAnimation(final ImageView imageView) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));
                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                imageView.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animationSet);
    }

}
