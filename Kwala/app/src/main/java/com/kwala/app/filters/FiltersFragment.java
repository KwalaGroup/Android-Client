package com.kwala.app.filters;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.helpers.KRealmRecyclerViewAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filters_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView filtersRecyclerView = (RecyclerView) view.findViewById(R.id.filters_fragment_recycler_view);

        RealmResults<RFilter> filters = RealmQueries.withMainRealm().getAll(RFilter.class);

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
    }
}
