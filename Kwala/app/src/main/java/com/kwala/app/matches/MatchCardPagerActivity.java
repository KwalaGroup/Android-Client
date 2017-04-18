package com.kwala.app.matches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class MatchCardPagerActivity extends BaseActivity {
    private static final String TAG = MatchCardPagerActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

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

        String matchId = getIntent().getStringExtra(MATCH_ID_KEY);

        RealmResults<RMatch> matches = RealmQueries.withMainRealm().getMatches();
        int currentItem = 0;
        for (int i = 0; i < matches.size(); i++) {
            RMatch match = matches.get(i);
            if (match.getMatchId().equals(matchId)) {
                currentItem = i;
                break;
            }
        }

        MatchCardPagerAdapter pagerAdapter = new MatchCardPagerAdapter(matches);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentItem);
    }
}
