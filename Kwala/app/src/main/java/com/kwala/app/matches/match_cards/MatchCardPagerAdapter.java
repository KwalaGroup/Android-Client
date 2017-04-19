package com.kwala.app.matches.match_cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kwala.app.helpers.KwalaDialogBuilder;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.matches.chat.ChatActivity;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.matches.AcceptMatchTask;
import com.quarkworks.dynamicviewpager.DynamicPagerAdapter;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */

public class MatchCardPagerAdapter extends DynamicPagerAdapter {
    private static final String TAG = MatchCardPagerAdapter.class.getSimpleName();

    private RealmResults<RMatch> matches;
    private BaseActivity activity;

    public MatchCardPagerAdapter(RealmResults<RMatch> matches, BaseActivity activity) {
        this.matches = matches;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup container, int position, int viewType) {
        MatchCardView matchCardView = new MatchCardView(container.getContext());
        return new DynamicPagerAdapter.ViewHolder(matchCardView) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MatchCardView matchCardView = (MatchCardView) viewHolder.view;
        matchCardView.setListener(matchCardListener);
        matchCardView.setViewData(matches.get(position));
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    public void updateData(RealmResults<RMatch> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    private final MatchCardView.Listener matchCardListener = new MatchCardView.Listener() {
        @Override
        public void onRejectClicked(final MatchCardView matchCardView) {
            final String matchId = matchCardView.getMatchId();
            if (matchId == null) {
                return;
            }

            KwalaDialogBuilder.with(activity)
                    .setTitle("Reject match")
                    .setMessage("Are you sure you want to reject your match with this user?\n\nThis action is irreversible and you will not be able to match with this person again in the future.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            discardView(matchCardView);
                        }
                    })
                    .show();
        }

        @Override
        public void onAcceptClicked(MatchCardView matchCardView) {
            String matchId = matchCardView.getMatchId();
            if (matchId == null) {
                return;
            }

            new AcceptMatchTask(matchId).start(new Task.Callback<Void, NetworkException>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Do nothing
                }

                @Override
                public void onFailure(NetworkException e) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error accepting match", e);
                }
            });
        }

        @Override
        public void onChatClicked(MatchCardView matchCardView) {
            String matchId = matchCardView.getMatchId();
            if (matchId == null) {
                return;
            }

            Intent intent = ChatActivity.newIntent(activity, matchId);
            activity.startActivity(intent);
        }
    };
}
