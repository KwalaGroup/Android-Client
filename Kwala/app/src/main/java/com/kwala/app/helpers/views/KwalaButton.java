package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.kwala.app.R;
import com.kwala.app.helpers.TextViewAttrHelper;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaButton extends AppCompatButton {
    private static final String TAG = KwalaButton.class.getSimpleName();

    public KwalaButton(Context context) {
        super(context);
        initialize(null);
    }

    public KwalaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public KwalaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background));

        setTextColor(ContextCompat.getColor(getContext(), R.color.kWhite));

        setAllCaps(false);

        TextViewAttrHelper.applyAttributes(this, attrs, R.styleable.KwalaButton, R.styleable.KwalaButton_font);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1.0f : 0.4f);
    }
}
