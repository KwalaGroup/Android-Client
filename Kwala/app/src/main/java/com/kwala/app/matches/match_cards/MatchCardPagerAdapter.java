package com.kwala.app.matches.match_cards;

import android.view.ViewGroup;

import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.models.RMatch;
import com.quarkworks.dynamicviewpager.DynamicPagerAdapter;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */

public class MatchCardPagerAdapter extends DynamicPagerAdapter {
    private static final String TAG = MatchCardPagerAdapter.class.getSimpleName();

    private RealmResults<RMatch> matches;
    private MatchCardView.Listener matchCardListener;

    public MatchCardPagerAdapter(RealmResults<RMatch> matches, BaseActivity activity) {
        this.matches = matches;
        this.matchCardListener = new DefaultMatchCardListener(activity);
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
}
