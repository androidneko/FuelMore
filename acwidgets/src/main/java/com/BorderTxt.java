package com;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anroidcat.acwidgets.R;
import com.bigkoo.pickerview.lib.DensityUtil;

/**
 * Created by Administrator on 2017-3-14.
 */
public class BorderTxt extends RelativeLayout {
    //不可以点击时，初始颜色
    private int CHECKABLE_FALSE_DEFAULT = getResources().getColor(R.color.gray);
    //可以点击时，初始颜色
    private int CHECKABLE_TRUE_DEFAULT = getResources().getColor(R.color.blue_light);
    //可以点击时，选中时文本颜色
    private int CHECKABLE_TRUE_TXT = getResources().getColor(R.color.blue_light);

    private int TEXT1_COLOR_DEFAULT = getResources().getColor(R.color.black);
    //可以点击时，初始颜色
    private int TEXT2_COLOR_DEFAULT = getResources().getColor(R.color.gray);


    //是否可以点击
    private boolean checkable;


    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
        if(txt2!=null)
            txt2.setText(text2);
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
        if(txt1!=null)
            txt1.setText(text1);
    }

    /**
     * checkable=false时：unCheckedColor代表不可点击时的初始颜色;
     * checkable=true时:unCheckedColor代表可点击时的初始颜色；checkedColor表示选中时的文字颜色
     */

    private int unCheckedColor,checkedColor;
    private String text1, text2,text3;//文本内容
    private float textSize1, textSize2,textSize3;//文字大小
    private int text1Color,text2Color,text3Color; //文字1文字2颜色
    private TextView txt1, txt2,txt3;//文本
    private LayoutParams txt1Params, txt2Params,txt3Params, relParams,linParams;//位置属性
    private RelativeLayout relativeLayout;//中间两行文本防止在此布局中，便于定位
    private LinearLayout linearLayout;//

    private PointF pointF;//存储手指按下的位置
    private Context mContext;//上下文
    private int checkedBackground;
    private int unCheckedBackground;
    private int txt3Background;

    private float LinPaddingLeft,LinPaddingRight,LinPaddingTop,LinPaddingBotom; //内部偏移


    public BorderTxt(Context context) {
        super(context);
    }

    public BorderTxt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BorderTxt(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        pointF = new PointF();//初始化手指按下时的坐标对象
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BorderTxt);
        unCheckedColor = array.getColor(R.styleable.BorderTxt_unCheckedColor, CHECKABLE_TRUE_DEFAULT);
        checkedColor = array.getColor(R.styleable.BorderTxt_checkedColor, CHECKABLE_TRUE_TXT);
        checkable = array.getBoolean(R.styleable.BorderTxt_checkable, false);
        text1 = array.getString(R.styleable.BorderTxt_text1);
        text2 = array.getString(R.styleable.BorderTxt_text2);
        text3 = array.getString(R.styleable.BorderTxt_text3);

        textSize1 = array.getDimensionPixelSize(R.styleable.BorderTxt_textSize1, 14);
        textSize2 = array.getDimensionPixelSize(R.styleable.BorderTxt_textSize2, 10);
        textSize3 = array.getDimensionPixelSize(R.styleable.BorderTxt_textSize3, 8);

        text1Color=array.getColor(R.styleable.BorderTxt_textColor1, TEXT1_COLOR_DEFAULT);
        text2Color=array.getColor(R.styleable.BorderTxt_textColor2, TEXT2_COLOR_DEFAULT);
        text3Color=array.getColor(R.styleable.BorderTxt_textColor3, TEXT2_COLOR_DEFAULT);

        LinPaddingLeft = array.getDimensionPixelSize(R.styleable.BorderTxt_LinPaddingLeft, 14);


        checkedBackground=array.getResourceId(R.styleable.BorderTxt_checkedBackground, R.drawable.recharge_check);
        unCheckedBackground=array.getResourceId(R.styleable.BorderTxt_unCheckedBackground, R.drawable.uncheckedbackground);
        //txt3Background=array.getResourceId(R.styleable.BorderTxt_txt3Background, R.color.transparent);
        //自定义属性获取完之后，及时回收
        array.recycle();
        //初始化背景、文字颜色及事件绑定
        initUi();
        //控件位置布局


        linearLayout = new LinearLayout(mContext);
        linParams = new LayoutParams(DensityUtil.dip2px(mContext,20), DensityUtil.dip2px(mContext,20));
        linParams.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP);
        linearLayout.setLayoutParams(linParams);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(5, 5, 5, 5);
        txt3 = new TextView(mContext);
        txt3.setText(text3);
        //txt2.setTextSize(textSize2);
        txt3.getPaint().setTextSize(textSize3);
        txt3.setTextColor(text3Color);

        txt3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        txt3Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        txt3Params.addRule(RelativeLayout.CENTER_IN_PARENT);
        linearLayout.addView(txt3, txt3Params);
        addView(linearLayout);
        linearLayout.setBackgroundResource(txt3Background);
        relativeLayout = new RelativeLayout(mContext);
        relParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relParams.addRule(CENTER_IN_PARENT);
        relativeLayout.setLayoutParams(relParams);



        txt1 = new TextView(mContext);
        txt1.setId(StringUtil.generateViewId());
        txt1.setText(text1);
        txt1.setTextColor(text1Color);
        //txt1.setTextSize(textSize1);
        txt1.getPaint().setTextSize(textSize1);
        //当第二行文本内容为空时，第一行居中显示，否则，将整体居中
        if(StringUtil.isEmpty(text2)){
            txt1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            txt1Params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }else{
            txt1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            txt1Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            txt1.setGravity(Gravity.CENTER);

            txt2 = new TextView(mContext);
            txt2.setText(text2);
            //txt2.setTextSize(textSize2);
            txt2.getPaint().setTextSize(textSize2);
            txt2.setTextColor(text2Color);

            txt2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            txt2Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            txt2Params.addRule(BELOW, txt1.getId());
            relativeLayout.addView(txt2, txt2Params);
        }
        //初始化文本颜色
        //setTxtColor(text1Color,text2Color);
        relativeLayout.addView(txt1, txt1Params);
        addView(relativeLayout);
        setCheckable(checkable);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        /**
//         * 可以点击时，手指第一次按下时存储此时的位置坐标；
//         * 当手指抬起或者移动与按下时的坐标任意方向上的长度大于三分之一控件长度时，
//         * 控件的press效果失效，并改变文本颜色。
//         */
//        if(checkable){
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                pointF.x = event.getX();
//                pointF.y = event.getY();
//                //按下
//            } else if (event.getAction() ==  MotionEvent.ACTION_UP
//                    || getWidth() / 3 - Math.abs(event.getX() - pointF.x) < 0
//                    || getHeight() / 3 - Math.abs(event.getY() - pointF.y) < 0
//                    ) {
//                //抬起
//                this.setPressed(false);
//                setTxtColor(checkedColor);
////                if(!StringUtil.isEmpty(text2)) {
////                    setTxtColor(text1Color, text2Color);
////                }
////                else
////                {
////                    setTxtColor(text1Color);
////                }
//            }
//        }
//        return false;
//    }




    /**
     * 设置文本颜色
     * @param
     */
    public void setTxtColor(int color1,int color2){
        txt1.setTextColor(color1);
        if(!StringUtil.isEmpty(text2)){
            txt2.setTextColor(color2);
        }
    }

    /**
     * 设置文本颜色
     * @param
     */
    public void setTxtColor(int color){
        txt1.setTextColor(color);
        if(!StringUtil.isEmpty(text2)){
            txt2.setTextColor(color);
        }
    }


    /**
     * 得到是否可以点击
     * @return
     */
    public boolean isCheckable() {
        return checkable;
    }

    /**
     * 设置是否可以点击
     * 并刷新控件状态
     * @param checkable
     */
    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
//        refreshDrawableState();//执行改变逻辑
//        if(!StringUtil.isEmpty(text2)){
//            setTxtColor(text1Color,text2Color);//初始化文字颜色
//        }else {
//            setTxtColor(text1Color);//初始化文字颜色
//        }
        if(checkable)
        {
            setTxtColor(checkedColor);//初始化文字颜色
            this.setBackgroundResource(checkedBackground);
        }else
        {
            this.setBackgroundResource(unCheckedBackground);
            if(!StringUtil.isEmpty(text2))
            {
                setTxtColor(text1Color,text2Color);//初始化文字颜色
            }
        }
    }

    /**
     * 执行控件改变
     */
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        //改变时的切换逻辑
        initUi();
    }

    /**
     * 初始化背景、文字颜色及事件绑定
     */
    private void initUi() {
//        if(checkable){
//            unCheckedColor = CHECKABLE_TRUE_DEFAULT;
//            checkedColor = CHECKABLE_TRUE_TXT;
//            setBackgroundResource(checkedBackground);
//            /**
//             * 绑定点击事件监听，否则点击时无法切换背景，selector效果无效;
//             * 我的理解就是让空间拥有获得焦点的能力，尽管什么都没做
//             */
//        }else{
//            unCheckedColor = CHECKABLE_FALSE_DEFAULT;
//            setBackgroundResource(unCheckedBackground);
//            //setBackgroundResource(R.drawable.custom_border_txt_bg2);
//        }
    }

    public void setCornerBackground(int res)
    {
        if(linearLayout != null)
        {
            linearLayout.setBackgroundResource(res);
        }
    }

    /**
     * 设置折扣字体
     * @param s
     */
    public void setCornerText(String s)
    {
        SpannableString sp = new SpannableString(s);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),0,s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt3.setText(sp);
    }
}
