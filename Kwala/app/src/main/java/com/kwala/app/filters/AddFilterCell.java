package com.kwala.app.filters;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class AddFilterCell extends RelativeLayout {
    private static final String TAG = AddFilterCell.class.getSimpleName();

    private View addMoreButton;

    public AddFilterCell(@NonNull Context context) {
        super(context);
        initialize();
    }

    public AddFilterCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AddFilterCell(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.add_filter_cell, this);

        addMoreButton = findViewById(R.id.add_filter_cell_add_button);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        addMoreButton.setOnClickListener(l);
    }
}
