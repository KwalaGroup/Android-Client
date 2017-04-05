package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaButton extends AppCompatButton {
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

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1.0f : 0.4f);
    }
}
