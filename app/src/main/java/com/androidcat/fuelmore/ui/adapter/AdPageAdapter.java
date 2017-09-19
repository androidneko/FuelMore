package com.androidcat.fuelmore.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidcat.fuelmore.R;
import com.androidcat.utilities.log.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-20 13:21:13
 * add function description here...
 */
public class AdPageAdapter extends PagerAdapter{

    private Context context;    /**
     * The m inflater.
     */
    private LayoutInflater mInflater;
    private ViewPager adViewPager;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String[] ad_pics = null;
    private String[] ad_url = null;
    private String[] ad_name = null;
    private int totalNum;

    /**
     * Instantiates a new ad page adapter.
     *
     * @param context the context
     */
    public AdPageAdapter(Context context,ViewPager viewPager) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        adViewPager = viewPager;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)// 是否緩存都內存中
                .cacheOnDisc(true)// 是否緩存到sd卡上
                .showImageOnLoading(R.drawable.pic_def)
                .showImageForEmptyUri(R.drawable.pic_def)
                .showImageOnFail(R.drawable.pic_def)
                .build();
    }

    public void setData(int pageNum, String[] pics, String[] url, String[] name) {
        totalNum = pageNum;
        ad_pics = pics;
        ad_url = url;
        ad_name = name;
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return totalNum * 2;
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
     */
    // 判断是否有对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
     */
    // 初始化position位置的界面
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        position %= totalNum;
        View view = mInflater.inflate(R.layout.ad_item, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position2 = adViewPager.getCurrentItem() % totalNum;
                if (ad_url != null) {
                        /*Intent intent = new Intent(HomeActivity.this, AdActivity.class);
                        intent.putExtra(EVENT_URL, ad_url[position2]);
                        intent.putExtra(EVENT_TITLE, ad_name[position2]);
                        startActivity(intent);*/
                }
            }
        });
        ImageView imageView = (ImageView) view.findViewById(R.id.ItemImage);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (ad_pics != null) {
            imageLoader.displayImage(ad_pics[position], imageView, options);
        }
        container.addView(view);
        return view;
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup, int, java.lang.Object)
     */
    // 销毁position位置的界面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // finishUpdate表示ViewPager的更新即将完成，假设一共5个界面，
    // 在这个时候我们可以将屏幕0切换为屏幕5，
    // 这样就可以实现从第一个界面直接划到最后一个界面的效果，
        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#finishUpdate(android.view.ViewGroup)
		 */
    // 当下标达到9时，无法继续滑动，此时将其切换到第4屏，则可以实现循环滑动效果
    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        int position = adViewPager.getCurrentItem();
        if (position == totalNum * 2 - 1) {
            position = totalNum - 1;
            Logger.e("finishUpdate executed,position: " + position);
            adViewPager.setCurrentItem(position, false);
        }
    }
}
