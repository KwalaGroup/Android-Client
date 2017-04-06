package com.kwala.app.filters;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.filters.create_filter.CreateFilterActivity;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.models.RFilter;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class FiltersFragment extends Fragment {
    private static final String TAG = FiltersFragment.class.getSimpleName();

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    private RecyclerView filtersRecyclerView;
    private View emptyStateLayout;

    private RealmResults<RFilter> filters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filters_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * Get view references
         */
        filtersRecyclerView = (RecyclerView) view.findViewById(R.id.filters_fragment_recycler_view);
        emptyStateLayout = view.findViewById(R.id.filters_fragment_empty_state_layout);
        View createButton = view.findViewById(R.id.filters_fragment_empty_state_create_button);

        createButton.setOnClickListener(createFilterClickListener);

        /*
         * Set up adapter
         */
        filters = RealmQueries.withMainRealm().getAll(RFilter.class);

        KRealmRecyclerViewAdapter<RFilter> adapter = new KRealmRecyclerViewAdapter<RFilter>(getActivity(), filters, true) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FilterCell filterCell = new FilterCell(parent.getContext());
                return new RecyclerView.ViewHolder(filterCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                FilterCell filterCell = (FilterCell) holder.itemView;
                filterCell.setViewData(getItem(position));
            }
        };

        filtersRecyclerView.setAdapter(adapter);
        resolveLayoutState();
    }

    private void resolveLayoutState() {
        if (getView() == null) {
            return;
        }

        if (filters.isEmpty()) {
            filtersRecyclerView.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            filtersRecyclerView.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Listeners
     */
    private final View.OnClickListener createFilterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = CreateFilterActivity.newIntent(getActivity());
            startActivity(intent);
        }
    };
}
