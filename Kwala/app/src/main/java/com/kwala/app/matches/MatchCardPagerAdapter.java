package com.kwala.app.matches;

import android.view.ViewGroup;

import com.kwala.app.models.RMatch;
import com.quarkworks.dynamicviewpager.DynamicPagerAdapter;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */

public class MatchCardPagerAdapter extends DynamicPagerAdapter {
    private static final String TAG = MatchCardPagerAdapter.class.getSimpleName();

    private RealmResults<RMatch> matches;

    public MatchCardPagerAdapter(RealmResults<RMatch> matches) {
        this.matches = matches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup container, int position, int viewType) {
        MatchCardView matchCardView = new MatchCardView(container.getContext());
        return new DynamicPagerAdapter.ViewHolder(matchCardView) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MatchCardView matchCardView = (MatchCardView) viewHolder.view;
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
