package com.kwala.app.matches;

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
import com.kwala.app.enums.MatchState;
import com.kwala.app.helpers.KwalaDialogBuilder;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.matches.chat.ChatActivity;
import com.kwala.app.matches.match_cards.MatchCardPagerActivity;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.realm.RealmQueries;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.matches.AcceptMatchTask;
import com.kwala.app.service.tasks.matches.ListMatchesTask;
import com.kwala.app.service.tasks.matches.RejectMatchTask;

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
    private View loadingLayout;

    private RealmResults<RMatch> matches;
    private boolean networkPending = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matches_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * Get view references
         */
        matchesRecyclerView = (RecyclerView) view.findViewById(R.id.matches_fragment_recycler_view);
        normalLayout = view.findViewById(R.id.matches_fragment_normal_layout);
        emptyStateLayout = view.findViewById(R.id.matches_fragment_empty_layout);
        loadingLayout = view.findViewById(R.id.matches_fragment_loading_layout);

        /*
         * Set up adapter
         */
        matches = RealmQueries.withMainRealm().getMatches();
        if (matches.isEmpty()) {
            networkPending = true;

            new ListMatchesTask().start(new Task.Callback<Void, NetworkException>() {
                @Override
                public void onSuccess(Void aVoid) {
                    networkPending = false;
                    resolveLayoutState();
                }

                @Override
                public void onFailure(NetworkException e) {
                    networkPending = false;
                    resolveLayoutState();
                }
            });
        }

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
                matchCell.setListener(matchCellListener);
                return new RecyclerView.ViewHolder(matchCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MatchCell matchCell = (MatchCell) holder.itemView;
                RMatch match = getItem(position);

                //Show divider if current match state != SUCCESS && above match state == SUCCESS
                boolean showDivider = false;
                if (position > 0 && match != null && match.getMatchState() != MatchState.SUCCESS) {
                    RMatch aboveMatch = getItem(position-1);
                    showDivider = aboveMatch != null && aboveMatch.getMatchState() == MatchState.SUCCESS;
                }

                matchCell.setViewData(getItem(position), showDivider);
            }

            @Override
            public long getItemId(int index) {
                RMatch match = getItem(index);
                return match == null ? index : match.getMatchId().hashCode();
            }
        };

        adapter.setHasStableIds(true);
        matchesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        resolveLayoutState();
    }

    private void resolveLayoutState() {
        if (getView() == null) {
            return;
        }

        if (matches.isEmpty()) {
            normalLayout.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(networkPending ? View.GONE : View.VISIBLE);
            loadingLayout.setVisibility(networkPending ? View.VISIBLE : View.GONE);
        } else {
            normalLayout.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Listeners
     */
    private final MatchCell.Listener matchCellListener = new MatchCell.Listener() {
        @Override
        public void onClick(MatchCell matchCell) {
            String matchId = matchCell.getMatchId();
            if (matchId != null) {
                Intent intent = MatchCardPagerActivity.newIntent(getActivity(), matchId);
                startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(final MatchCell matchCell) {
            final String matchId = matchCell.getMatchId();
            if (matchId == null) {
                return false;
            }

            KwalaDialogBuilder.with(getActivity())
                    .setTitle("Reject match")
                    .setMessage("Are you sure you want to reject your match with this user?\n\nThis action is irreversible and you will not be able to match with this person again in the future.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new RejectMatchTask(matchId).start(new Task.Callback<Void, NetworkException>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Do nothing
                                }

                                @Override
                                public void onFailure(NetworkException e) {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Error rejecting match", e);
                                }
                            });
                        }
                    })
                    .show();
            return true;
        }

        @Override
        public void onChatClicked(MatchCell matchCell) {
            String matchId = matchCell.getMatchId();
            if (matchId != null) {
                Intent intent = ChatActivity.newIntent(getActivity(), matchId);
                startActivity(intent);
            }
        }

        @Override
        public void onHeartClicked(MatchCell matchCell) {
            String matchId = matchCell.getMatchId();
            if (matchId != null) {
                new AcceptMatchTask(matchId).start(new Task.Callback<Void, NetworkException>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Do nothing
                    }

                    @Override
                    public void onFailure(NetworkException e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error accepting match", e);
                    }
                });
            }
        }
    };
}
