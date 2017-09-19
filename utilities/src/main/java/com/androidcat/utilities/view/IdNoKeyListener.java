package com.androidcat.utilities.view;

import android.text.InputType;
import android.text.method.NumberKeyListener;

/**
 * Created by Administrator on 2016-1-26.
 */
public class IdNoKeyListener extends NumberKeyListener {

    @Override
    protected char[] getAcceptedChars() {
        return new char[]{ '1', '2', '3', '4', '5', '6', '7', '8','9', '0','X'};
    }

    @Override
    public int getInputType() {
        return InputType.TYPE_CLASS_TEXT;
    }
}
