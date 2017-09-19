package com.androidcat.utilities.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by xuelong on 2016-1-21.
 */
public class PhoneNumTextWatcher implements TextWatcher {
    private EditText mEditText;

    public PhoneNumTextWatcher(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            mEditText.setText(sb.toString());
            mEditText.setSelection(index);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
