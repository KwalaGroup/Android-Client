package com.kwala.app.matches;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.matches.chat.ChatActivity;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class MatchesFragment extends Fragment {
    private static final String TAG = MatchesFragment.class.getSimpleName();

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    private RecyclerView matchesRecyclerView;
    private View normalLayout;
    private View emptyStateLayout;

    private RealmResults<RMatch> matches;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matches_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * Get view references
         */
        matchesRecyclerView = (RecyclerView) view.findViewById(R.id.matches_fragment_recycler_view);
        normalLayout = view.findViewById(R.id.matches_fragment_normal_layout);
        emptyStateLayout = view.findViewById(R.id.matches_fragment_empty_layout);

        /*
         * Set up adapter
         */
        matches = RealmQueries.withMainRealm().getAll(RMatch.class);

        matches.addChangeListener(new RealmChangeListener<RealmResults<RMatch>>() {
            @Override
            public void onChange(RealmResults<RMatch> element) {
                resolveLayoutState();
            }
        });

        KRealmRecyclerViewAdapter<RMatch> adapter = new KRealmRecyclerViewAdapter<RMatch>(getActivity(), matches, true) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MatchCell matchCell = new MatchCell(parent.getContext());
                return new RecyclerView.ViewHolder(matchCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MatchCell matchCell = (MatchCell) holder.itemView;
                final RMatch match = getItem(position);

                matchCell.setViewData(getItem(position));

                matchCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "click");
                        if (match != null) {
                            Intent intent = ChatActivity.newIntent(getActivity(), match.getMatchId());
                            startActivity(intent);
                        }
                    }
                });
            }
        };

        matchesRecyclerView.setAdapter(adapter);
        resolveLayoutState();
    }

    private void resolveLayoutState() {
        if (getView() == null) {
            return;
        }

        if (matches.isEmpty()) {
            normalLayout.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            normalLayout.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }
}
