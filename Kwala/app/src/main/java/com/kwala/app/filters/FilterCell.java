package com.kwala.app.filters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.models.RFilter;

/**
 * @author muchow@hello.com
 */
public class FilterCell extends RelativeLayout {
    private static final String TAG = FilterCell.class.getSimpleName();

    private TextView nameTextView;

    /*
        Constructors
     */
    public FilterCell(Context context) {
        super(context);
        initialize();
    }

    public FilterCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FilterCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.filter_cell, this);

        nameTextView = (TextView) findViewById(R.id.filter_cell_name_text);
    }

    public void setViewData(@Nullable RFilter filter) {
        if (filter == null) {
            nameTextView.setText("");
        } else {
            nameTextView.setText(filter.getCategory());
        }
    }
}
