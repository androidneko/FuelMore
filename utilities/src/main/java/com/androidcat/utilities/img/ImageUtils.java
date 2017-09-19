package com.androidcat.utilities.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2016-7-26 17:42:47
 * add function description here...
 */
public class ImageUtils {

    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        PorterDuffColorFilter f = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    public static void setTextDraw(Context context, TextView tv, int color, int pic) {
        tv.setTextColor(context.getResources().getColor(color));
        Drawable drawable = context.getResources().getDrawable(pic);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setTextDrawGrey(Context context, TextView tv, int color, int pic) {
        tv.setTextColor(context.getResources().getColor(color));
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), pic);
        Bitmap bmpGrey = ImageUtils.bitmap2Gray(bmp);
        Drawable drawable = new BitmapDrawable(context.getResources(), bmpGrey);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }
}
