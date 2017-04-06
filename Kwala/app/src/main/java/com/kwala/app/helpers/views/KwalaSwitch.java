package com.kwala.app.helpers.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaSwitch extends SwitchCompat {
    private static final String TAG = KwalaSwitch.class.getSimpleName();

    public KwalaSwitch(Context context) {
        super(context);
        initialize();
    }

    public KwalaSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public KwalaSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        int white = ContextCompat.getColor(getContext(), R.color.kWhite);
        int orange = ContextCompat.getColor(getContext(), R.color.kOrange);
        int trackColor = ContextCompat.getColor(getContext(), R.color.switchTrackColor);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_enabled },
                new int[] { }
        };

        setThumbTintList(new ColorStateList(states, new int[] {
                orange,
                white,
                white
        }));

        setTrackTintList(new ColorStateList(states, new int[] {
                trackColor,
                trackColor,
                trackColor
        }));
    }
}
