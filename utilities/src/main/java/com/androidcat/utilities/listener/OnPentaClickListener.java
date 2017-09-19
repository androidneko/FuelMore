package com.androidcat.utilities.listener;

import android.view.View;

/**
 * Project: bletravel_new
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-6-14 14:10:13
 * add function description here...
 */
public abstract class OnPentaClickListener implements View.OnClickListener{

    private int count=1;
    private long firClick=0;
    private long secClick=0;
    private long thirClick=0;
    private long forClick=0;
    private long fifClick=0;
    private final int interval=500;

    @Override
    public void onClick(View v) {
        if(1==count){
            count++;
            firClick=System.currentTimeMillis();
        }else if(2==count){
            secClick=System.currentTimeMillis();
            if(secClick-firClick<interval){
                count++;
            }else {
                firClick=secClick;
                count=2;
            }
        }
        else if (3==count){
            thirClick = System.currentTimeMillis();
            if(thirClick-secClick<interval){
                count++;
            }else {
                firClick=thirClick;
                count=2;
            }
        }
        else if (4==count){
            forClick = System.currentTimeMillis();
            if(forClick-thirClick<interval){
                count++;
            }else {
                firClick=forClick;
                count=2;
            }
        }
        else if (5==count){
            fifClick = System.currentTimeMillis();
            if(fifClick-forClick<interval){
                onPentaClick(v);
                count=1;
                firClick=0;
                secClick=0;
                thirClick=0;
                forClick=0;
                fifClick=0;
            }else {
                firClick=fifClick;
                count=2;
            }
        }
    }

    public abstract void onPentaClick(View v);
}
