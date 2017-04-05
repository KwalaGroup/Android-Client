package com.kwala.app.helpers.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaProgressSpinner extends ProgressBar {
    private static final String TAG = KwalaProgressSpinner.class.getSimpleName();

    public KwalaProgressSpinner(Context context) {
        super(context);
        initialize(null);
    }

    public KwalaProgressSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public KwalaProgressSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {

        @ColorInt int color = ContextCompat.getColor(getContext(), R.color.kWhite);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.KwalaProgressSpinner);

            color = typedArray.getColor(R.styleable.KwalaProgressSpinner_tintColor, color);

            typedArray.recycle();
        }

        setIndeterminate(true);

        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
