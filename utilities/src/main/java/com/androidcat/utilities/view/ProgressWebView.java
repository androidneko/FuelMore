package com.androidcat.utilities.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.androidcat.utilities.R;
import com.androidcat.utilities.view.progress.roundcorner.RoundCornerProgressBar;

/**
 * 带进度条的WebView
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    private RoundCornerProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new RoundCornerProgressBar(context, attrs);
        progressbar.setProgressColor(context.getResources().getColor(R.color.orange_one));
        progressbar.setProgressBackgroundColor(context.getResources().getColor(R.color.gpv_line_gray));
        progressbar.setPadding(1);
        progressbar.setReverse(false);
        progressbar.setRadius(0);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 7, 0, 0));

        addView(progressbar);
        //        setWebViewClient(new WebViewClient(){});
        setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
}