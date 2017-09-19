/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.androidcat.utilities.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.androidcat.utilities.R;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;


public class XListViewExtend extends XListView {
    /**
     * @param context
     */
    public XListViewExtend(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListViewExtend(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        if (isInEditMode()) {
            return;
        }
        // init header view
        mHeaderView = new XListViewHeader(context, R.layout.xlistview_extend_header);
        mHeaderViewContent = (RelativeLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }
}