package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaButton extends Button {
    private static final String TAG = KwalaButton.class.getSimpleName();

    public KwalaButton(Context context) {
        super(context);
        init();
    }

    public KwalaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KwalaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background));

        setTextColor(ContextCompat.getColor(getContext(), R.color.kWhite));

        setAllCaps(false);
    }
}
