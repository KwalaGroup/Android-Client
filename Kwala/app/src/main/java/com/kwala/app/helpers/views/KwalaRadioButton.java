package com.kwala.app.helpers.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.kwala.app.R;
import com.kwala.app.helpers.TextViewAttrHelper;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaRadioButton extends AppCompatRadioButton {
    private static final String TAG = KwalaRadioButton.class.getSimpleName();

    public KwalaRadioButton(Context context) {
        super(context);
        initialize(null);
    }

    public KwalaRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public KwalaRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        TextViewAttrHelper.applyAttributes(this, attrs, R.styleable.KwalaRadioButton, R.styleable.KwalaRadioButton_font);

        /*
         * Create a ColorStateList based off of the theme color
         * and apply is to the thumb and track Drawables.
         */
        int uncheckedColor = ContextCompat.getColor(getContext(), R.color.radioButtonUnchecked);
        int checkedColor = ContextCompat.getColor(getContext(), R.color.radioButtonChecked);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_enabled },
                new int[] { }
        };

        setSupportButtonTintList(new ColorStateList(states, new int[] {
                checkedColor,
                uncheckedColor,
                uncheckedColor
        }));
    }
}
