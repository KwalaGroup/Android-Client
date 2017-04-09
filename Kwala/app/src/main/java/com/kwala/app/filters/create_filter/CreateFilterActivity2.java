package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwala.app.R;
import com.kwala.app.enums.FilterCategory;
import com.kwala.app.helpers.navigation.BaseActivity;

/**
 * Created by sijaebrown on 3/4/17.
 */

public class CreateFilterActivity2 extends BaseActivity {
    private static final String TAG = CreateFilterActivity2.class.getSimpleName();

    private static final String FILTER_CATEGORY_KEY = "filter_category";

    public static Intent newIntent(Context context, FilterCategory filterCategory) {
        Intent intent = new Intent(context, CreateFilterActivity2.class);

        intent.putExtra(FILTER_CATEGORY_KEY, filterCategory.getNetworkString());

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity_2);
        setTitle("Create Filter");

        String filterValue = getIntent().getStringExtra(FILTER_CATEGORY_KEY);
        FilterCategory filterCategory = FilterCategory.fromNetworkString(filterValue);

        Log.d(TAG, filterCategory.name());
    }
}
