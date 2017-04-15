package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.enums.Filter;
import com.kwala.app.helpers.navigation.BaseToolbarActivity;

/**
 * @author jacobamuchow@gmail.com
 */
public class CreateFilterActivity extends BaseToolbarActivity {
    private static final String TAG = CreateFilterActivity.class.getSimpleName();

    private RecyclerView filtersRecyclerView;

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateFilterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity);
        setTitle("Create Filter");

        filtersRecyclerView = (RecyclerView) findViewById(R.id.create_filter_recycler_view);

        final Filter[] filterCategories = Filter.supportedCategories;

        filtersRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FilterCategoryCell filterCategoryCell = new FilterCategoryCell(parent.getContext());
                return new RecyclerView.ViewHolder(filterCategoryCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final FilterCategoryCell filterCategoryCell = (FilterCategoryCell) holder.itemView;
                final Filter filter = filterCategories[position];

                filterCategoryCell.setViewData(filter);

                filterCategoryCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Click");
                        Intent intent = CreateFilterActivity2.newIntent(CreateFilterActivity.this, filter);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return filterCategories.length;
            }
        });
    }
}
