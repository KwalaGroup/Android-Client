package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kwala.app.R;
import com.kwala.app.enums.Filter;

/**
 * Created by sijaebrown on 4/6/17.
 */

public class FilterCategoryCell extends RelativeLayout {
    private static final String TAG = FilterCategoryCell.class.getSimpleName();

    private Button button;

    public FilterCategoryCell(Context context) {
        super(context);
        initialize();
    }

    public FilterCategoryCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FilterCategoryCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.filter_category_cell, this);

        button = (Button) findViewById(R.id.filter_category_cell_button);
    }

    public void setViewData(Filter filter) {
        button.setText(filter.getDisplayString());
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(l);
    }
}
