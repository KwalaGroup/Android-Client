package com.kwala.app.matches.match_cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.realm.RealmQueries;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.matches.RejectMatchTask;
import com.quarkworks.dynamicviewpager.DynamicPagerAdapter;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class MatchCardPagerActivity extends BaseActivity {
    private static final String TAG = MatchCardPagerActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    private MatchCardPagerAdapter pagerAdapter;
    private RealmResults<RMatch> matches;

    public static Intent newIntent(Context context, String matchId) {
        Intent intent = new Intent(context, MatchCardPagerActivity.class);
        intent.putExtra(MATCH_ID_KEY, matchId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_card_pager_activity);

        /*
         * Get view references
         */
        ViewPager viewPager = (ViewPager) findViewById(R.id.match_card_pager_view_pager);

        /*
         * Set view data
         */
        //Remove the drawable that pops up when you reach either end of the pager
        viewPager.setPageMarginDrawable(null);

        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(30);  //TODO: convert 30dp to pixels

        final String matchId = getIntent().getStringExtra(MATCH_ID_KEY);

        matches = RealmQueries.withMainRealm().getMatches();
        int currentItem = 0;
        for (int i = 0; i < matches.size(); i++) {
            RMatch match = matches.get(i);
            if (match.getMatchId().equals(matchId)) {
                currentItem = i;
                break;
            }
        }

        pagerAdapter = new MatchCardPagerAdapter(matches, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentItem);

        matches.addChangeListener(new RealmChangeListener<RealmResults<RMatch>>() {
            @Override
            public void onChange(RealmResults<RMatch> newResults) {
                matches = newResults;

                if (pagerAdapter != null) {
                    pagerAdapter.updateData(matches);
                }

                if (matches.isEmpty()) {
                    finish();
                }
            }
        });

        pagerAdapter.setCallbacks(new DynamicPagerAdapter.Callbacks() {
            @Override
            public void onDiscardFinished(int position, View view) {
                if (matches != null && matches.isValid() && position >= 0 && position < matches.size()) {
                    RMatch match = matches.get(position);

                    new RejectMatchTask(match.getMatchId()).start(new Task.Callback<Void, NetworkException>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Do nothing
                        }

                        @Override
                        public void onFailure(NetworkException e) {
                            Toast.makeText(getBaseActivity(), "Error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error rejecting match", e);
                        }
                    });

                    if (pagerAdapter != null) {
                        pagerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
}
