package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseToolbarActivity;

/**
 * @author jacobamuchow@gmail.com
 */
public class CreateFilterActivity extends BaseToolbarActivity {
    private static final String TAG = CreateFilterActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateFilterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity);

        setTitle("Create Filter");
    }
}
