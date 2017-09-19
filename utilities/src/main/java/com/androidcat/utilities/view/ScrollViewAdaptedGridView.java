package com.androidcat.utilities.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-2-15 17:01:08
 * add function description here...
 */
public class ScrollViewAdaptedGridView extends GridView {
    public ScrollViewAdaptedGridView(Context context) {
        super(context);
    }

    public ScrollViewAdaptedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewAdaptedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
