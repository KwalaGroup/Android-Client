package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwala.app.R;

/**
 * Created by sijaebrown on 4/9/17.
 */

public class FilterInterestCell extends RelativeLayout {
    private static final String TAG = FilterInterestCell.class.getSimpleName();

    private TextView textView;

    public FilterInterestCell(Context context) {
        super(context);
        initialize();
    }

    public FilterInterestCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FilterInterestCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.filter_interest_cell, this);

        textView = (TextView) findViewById(R.id.filter_interest_cell_text);
    }

    public void setViewData(String interest) {
        textView.setText(interest);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        int color = ContextCompat.getColor(getContext(), selected ? R.color.kOrange : R.color.kDarkGray);
        textView.setTextColor(color);
    }
}
