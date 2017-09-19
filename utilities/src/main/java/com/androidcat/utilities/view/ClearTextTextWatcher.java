package com.androidcat.utilities.view;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2016-1-20.
 */
public class ClearTextTextWatcher implements TextWatcher {
    private EditText mEditText;
    private View mClearView;

    public ClearTextTextWatcher(EditText editText, View clearBtn) {
        this.mEditText = editText;
        this.mClearView = clearBtn;
        mClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mClearView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing...
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Debug.asserts(text instanceof Spannable);
        if (s != null && s instanceof Spannable) {
            Spannable spanText = (Spannable) s;
            Selection.setSelection(spanText, s.length());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null && !editable.toString().isEmpty()) {
            mClearView.setVisibility(View.VISIBLE);
        }
    }
}
