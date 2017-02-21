package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */
public class CreateFilterActivity extends AppCompatActivity {
    private static final String TAG = CreateFilterActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateFilterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity);
    }
}
