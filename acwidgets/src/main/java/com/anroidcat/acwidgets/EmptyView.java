package com.anroidcat.acwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

public class EmptyView extends LinearLayout {

    private String mEmptyText;
    private String mFailedText;
    private String mLoadingText;

    private View mBindView;
    private View mEmptyView;

    private View mLoadingView;
    private View mRetImgView;
    private TextView mTextView;

    private Context mContext;

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.emptyView, 0, 0);
        mEmptyText = ta.getString(R.styleable.emptyView_emptyText);
        mFailedText = ta.getString(R.styleable.emptyView_failText);
        mLoadingText = ta.getString(R.styleable.emptyView_loadingText);
        ta.recycle();

        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.emptyview, null);
        mLoadingView = mEmptyView.findViewById(R.id.loadingView);
        mRetImgView = mEmptyView.findViewById(R.id.retImgView);
        mTextView = (TextView) mEmptyView.findViewById(R.id.textTv);

        if (TextUtils.isEmpty(mEmptyText)) mEmptyText = "暂无数据";
        if (TextUtils.isEmpty(mFailedText)) mFailedText = "轻触重试";
        if (TextUtils.isEmpty(mLoadingText)) mLoadingText = "加载中...";
        mEmptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        addView(mEmptyView);
    }

    public void bindView(View view) {
        mBindView = view;
    }

    public void loading() {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        mRetImgView.setVisibility(View.GONE);
        mLoadingView.setVisibility(VISIBLE);
        mTextView.setText(mLoadingText);
    }

    public void success() {
        setVisibility(View.GONE);
        if (mBindView != null) mBindView.setVisibility(View.VISIBLE);
    }

    public void empty() {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        mRetImgView.setVisibility(View.VISIBLE);
        mRetImgView.setBackgroundResource(R.drawable.emptyview_load_empty);
        mLoadingView.setVisibility(GONE);
        mTextView.setText(mEmptyText);
    }

    public void fail() {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        mRetImgView.setVisibility(View.VISIBLE);
        mRetImgView.setBackgroundResource(R.drawable.emptyview_load_failed);
        mLoadingView.setVisibility(GONE);
        mTextView.setText(mFailedText);
    }

    public void viewOnClick(final Object base, final String method,
                            final Object... parameters) {
        mEmptyView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int length = parameters.length;
                Class<?>[] paramsTypes = new Class<?>[length];
                for (int i = 0; i < length; i++) {
                    paramsTypes[i] = parameters[i].getClass();
                }
                try {
                    Method m = base.getClass().getDeclaredMethod(method, paramsTypes);
                    m.setAccessible(true);
                    m.invoke(base, parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
