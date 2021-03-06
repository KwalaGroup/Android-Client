package com.kwala.app.filters;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.filters.create_filter.CreateFilterActivity;
import com.kwala.app.helpers.KwalaDialogBuilder;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.models.RFilter;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.realm.RealmQueries;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.filters.DeleteFilterTask;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class FiltersFragment extends Fragment {
    private static final String TAG = FiltersFragment.class.getSimpleName();

    private static final int FILTER_CELL = 0;
    private static final int ADD_MORE_CELL = 1;

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

        filters.addChangeListener(new RealmChangeListener<RealmResults<RFilter>>() {
            @Override
            public void onChange(RealmResults<RFilter> element) {
                resolveLayoutState();
            }
        });

        final KRealmRecyclerViewAdapter<RFilter> adapter = new KRealmRecyclerViewAdapter<RFilter>(getActivity(), filters, true) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == FILTER_CELL) {
                    FilterCell filterCell = new FilterCell(parent.getContext());
                    return new RecyclerView.ViewHolder(filterCell) {};
                } else {
                    AddFilterCell addFilterCell = new AddFilterCell(parent.getContext());
                    addFilterCell.setOnClickListener(createFilterClickListener);
                    return new RecyclerView.ViewHolder(addFilterCell) {};
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if (holder.itemView instanceof FilterCell) {
                    FilterCell filterCell = (FilterCell) holder.itemView;
                    final RFilter filter = getItem(position);
                    filterCell.setViewData(getItem(position));

                    filterCell.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (filter == null) {
                                return false;
                            }

                            String filterName = filter.getCategory().getDisplayString();
                            KwalaDialogBuilder.with(getActivity())
                                    .setTitle("Delete filter")
                                    .setMessage("Are you sure you want to delete your " + filterName + " filter? This action cannot be undone.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteFilter(filter);
                                        }
                                    })
                                    .show();

                            return true;
                        }
                    });
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position < getItemCount() - 1) {
                    return FILTER_CELL;
                } else {
                    return ADD_MORE_CELL;
                }
            }

            @Override
            public int getItemCount() {
                return super.getItemCount()+1;
            }
        };

        filtersRecyclerView.setAdapter(adapter);
        resolveLayoutState();
    }

    private void deleteFilter(@Nullable RFilter filter) {
        if (filter == null) {
            return;
        }

        new DeleteFilterTask(filter.getFilterId()).start(new Task.Callback<Void, NetworkException>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Filter deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(NetworkException e) {
                Log.e(TAG, "Error deleting filter", e);
                Toast.makeText(getActivity(), "Error deleting filter", Toast.LENGTH_LONG).show();
            }
        });
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
