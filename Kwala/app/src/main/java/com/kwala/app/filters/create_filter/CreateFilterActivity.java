package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseToolbarActivity;
import com.kwala.app.enums.FilterCategory;

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

        filtersRecyclerView = (RecyclerView) findViewById(R.id.create_filter_recycler_view);

        final FilterCategory[] filterCategories = FilterCategory.supportedCategories;

        filtersRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Button filterButton = new Button(parent.getContext());
                return new RecyclerView.ViewHolder(filterButton) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Button button = (Button) holder.itemView;
                FilterCategory filterCategory = filterCategories[position];

                button.setText(filterCategory.getDisplayString());
            }

            @Override
            public int getItemCount() {
                return filterCategories.length;
            }
        });

        setTitle("Create Filter");
    }
}
