package com.effone.hostismeandroid.changeListener;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

@SuppressLint("AppCompatCustomView")
public class CustomAutoCompleteView extends AutoCompleteTextView {

    public CustomAutoCompleteView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }
}