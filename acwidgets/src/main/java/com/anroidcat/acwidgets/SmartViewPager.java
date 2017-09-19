package com.anroidcat.acwidgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-4-5 14:45:42
 * add function description here...
 */
public class SmartViewPager extends ViewPager{

        private boolean scrollable = true;
        public SmartViewPager(Context context) {
            super(context);
        }
        public SmartViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public void setScrollable(boolean scrollable) {
            this.scrollable = scrollable;
        }
        @Override
        public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
        }
        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            if(scrollable){
                return super.onTouchEvent(arg0);
            }else {
                return false;
            }
        }
        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            if(scrollable){
                return super.onInterceptTouchEvent(arg0);
            }else {
                return false;
            }
        }
        @Override
        public void setCurrentItem(int item, boolean smoothScroll) {
            super.setCurrentItem(item, smoothScroll);
        }
        @Override
        public void setCurrentItem(int item) {
            super.setCurrentItem(item);
        }
}
