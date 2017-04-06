package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kwala.app.R;
import com.kwala.app.helpers.TextViewAttrHelper;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaTextView extends AppCompatTextView {
    private static final String TAG = AppCompatTextView.class.getSimpleName();

    public KwalaTextView(Context context) {
        super(context);
        initialize(null);
    }

    public KwalaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public KwalaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        TextViewAttrHelper.applyAttributes(this, attrs, R.styleable.KwalaTextView, R.styleable.KwalaTextView_font);
    }
}
