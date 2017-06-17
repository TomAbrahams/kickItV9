package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Thomas on 5/1/2017.
 */

public class MyTextViewFuture extends android.support.v7.widget.AppCompatTextView {
    public MyTextViewFuture(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        }

public MyTextViewFuture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        }

public MyTextViewFuture(Context context) {
        super(context);
        init();
        }

public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gasalt-Regular.ttf");
        setTypeface(tf ,1);

        }
}

