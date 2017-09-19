package com.androidcat.utilities.view;

import android.text.method.ReplacementTransformationMethod;

/**
 * Created by Administrator on 2016-1-27.
 */
public class AutoUppercaseTransformationMethod extends ReplacementTransformationMethod{

    @Override
    protected char[] getOriginal() {
        char[] lowercaseChars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
        return lowercaseChars;
    }

    @Override
    protected char[] getReplacement() {
        char[] uppercaseChars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        return uppercaseChars;
    }
}
